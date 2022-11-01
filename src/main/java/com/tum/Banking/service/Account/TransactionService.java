package com.tum.Banking.service.Account;


import com.google.gson.Gson;
import com.tum.Banking.dto.TransactionRequestDTO;
import com.tum.Banking.dto.TransactionResponseDTO;
import com.tum.Banking.model.Account.AccountBalance;
import com.tum.Banking.model.Account.Currency;
import com.tum.Banking.model.Account.CustomerAccount;
import com.tum.Banking.model.Account.Transaction;
import com.tum.Banking.repository.Account.AccountBalanceRepository;
import com.tum.Banking.repository.Account.AccountRepository;
import com.tum.Banking.repository.Account.CurrencyRepository;
import com.tum.Banking.repository.Account.TransactionRepository;
import com.tum.Banking.service.QueueService.TransactionMessageSenderService;
import com.tum.Banking.service.Utility.UtilService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class TransactionService {

    @Autowired
    TransactionMessageSenderService transactionMessageSenderService;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UtilService utilService;

    @Autowired
    CurrencyRepository currencyRepository;

    @Autowired
    AccountBalanceRepository accountBalanceRepository;

    @Transactional
    public ResponseEntity<?> createTransaction(TransactionRequestDTO transactionRequest)
    {


        String error="";
        try {
            double newBalance = 0;
            CustomerAccount account = accountRepository.findById(transactionRequest.getAccountId());
            if (account == null) {
                error = "Account does not exist of id " + transactionRequest.getAccountId();
                throw new Exception(error);
            }

            if (transactionRequest.getDescription().isEmpty()) {
                // return description missing
                error = "Description missing";
                throw new Exception(error);
            }

            if (transactionRequest.getAmount() < 0) {
                /// invalid amount
                error = "Invalid amount";
                throw new Exception(error);
            }
            List<String> currency = new ArrayList<>();
            currency.add(transactionRequest.getCurrency());
            if (!utilService.validateCurrency(currency)) {
                //// return currency error
                error = "Invalid currency";
                throw new Exception(error);
            }

            if (!transactionRequest.getTransactionDirection().equals("IN") && !transactionRequest.getTransactionDirection().equals("OUT")) {
                /// return invalid transaction error
                error = "Invalid transaction direction";
                throw new Exception(error);
            }

            if (transactionRequest.getTransactionDirection().equals("OUT")) {
                Currency currencyObj = currencyRepository.findCurrencyByName(transactionRequest.getCurrency());
                AccountBalance currenctBalance = accountBalanceRepository.findByAccountAndCurrencyId(transactionRequest.getAccountId(), currencyObj.getId());

                double balance = currenctBalance.getBalance();
                if (balance - transactionRequest.getAmount() < 0) {
                    /// return insufficient amount error
                    error = "You have insufficient funds to complete this transaction";
                    throw new Exception(error);
                }
                AccountBalance updatedObj = new AccountBalance(currenctBalance);
                updatedObj.setBalance(balance - transactionRequest.getAmount());
                accountBalanceRepository.save(updatedObj);
                newBalance = balance - transactionRequest.getAmount();


            }
            if (transactionRequest.getTransactionDirection().equals("IN")) {
                Currency currencyObj = currencyRepository.findCurrencyByName(transactionRequest.getCurrency());
                AccountBalance currenctBalance = accountBalanceRepository. findByAccountAndCurrencyId(transactionRequest.getAccountId(), currencyObj.getId());

                double balance = currenctBalance.getBalance();

                AccountBalance updatedObj = new AccountBalance(currenctBalance);
                updatedObj.setBalance(balance + transactionRequest.getAmount());
                accountBalanceRepository.save(updatedObj);
                newBalance = balance + transactionRequest.getAmount();

            }
            Transaction transaction = addTransaction(transactionRequest, newBalance);

            TransactionResponseDTO response = new TransactionResponseDTO();
            response.setTransactionId(transaction.getId());
            response.setDescription(transaction.getDescription());
            response.setTransactionDirection(transaction.getTransactionDirection());
            response.setCurrency(transaction.getCurrency());
            response.setAmount(transaction.getAmount());
            response.setBalance(newBalance);
            response.setAccountId(transactionRequest.getAccountId());

            Gson gson = new Gson();
            String result = gson.toJson(response);

            try {
                transactionMessageSenderService.sendMessage(response);
            }
            catch(Exception e)
            {
                return new ResponseEntity<String>(
                        new JSONObject("{'success':  false,'message':'Transaction fail because RabbitMq is not working'  }").toString(), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<String>(
                    new JSONObject("{'success':  true,'data':" + result + "  }").toString(), HttpStatus.OK
            );

        }
        catch(Exception e)
        {
            String message = e.getMessage().toString();
            return new ResponseEntity<String>(
                    new JSONObject("{'success':  false,'message':" + message + "  }").toString(), HttpStatus.BAD_REQUEST
            );
        }

    }

    public String generateId()
    {
        UUID id = UUID.randomUUID();
        Transaction transaction = transactionRepository.findById(id.toString());

        while(transaction!= null)
        {
            id = UUID.randomUUID();
            transaction = transactionRepository.findById(id.toString());
        }

        return id.toString();

    }

    @Transactional
    public Transaction addTransaction(TransactionRequestDTO transactionRequest,double balance)
    {
        Transaction transaction = new Transaction();
        transaction.setBalance(balance);
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setCurrency(transactionRequest.getCurrency());
        transaction.setAccountId(transactionRequest.getAccountId());
        transaction.setDescription(transactionRequest.getDescription());
        transaction.setTransactionDirection(transactionRequest.getTransactionDirection());
        transaction.setId(generateId());
        Transaction createdTransaction =  transactionRepository.save(transaction);
        return createdTransaction;

    }

    public  ResponseEntity<?> getAllTransactions(String accountId)
    {
        try{

            String error="";

            CustomerAccount account = accountRepository.findById(accountId);
            if(account == null)
            {
                throw  new Exception("Account does not exist of id "+ accountId);
            }
            List<Transaction> transactionList = transactionRepository.getTransactionByAccount(accountId);

            List<TransactionResponseDTO> response =  new ArrayList<>();
            for(Transaction transaction : transactionList)
            {
                TransactionResponseDTO obj = new TransactionResponseDTO();
                obj.setTransactionId(transaction.getId());
                obj.setTransactionDirection(transaction.getTransactionDirection());
                obj.setBalance(transaction.getBalance());
                obj.setCurrency(transaction.getCurrency());
                obj.setDescription(transaction.getDescription());
                obj.setAmount(transaction.getAmount());
                response.add(obj);
            }
            Gson gson = new Gson();
            String result = gson.toJson(response);


            return new ResponseEntity<String>(
                    new JSONObject("{'success':  true,'data':" + result + "  }").toString(), HttpStatus.OK
            );
        }
        catch(Exception e)
        {
            String message = e.getMessage().toString();
            return new ResponseEntity<String>(
                    new JSONObject("{'success':  false,'message':" + message + "  }").toString(), HttpStatus.BAD_REQUEST
            );
        }
    }



}
