package com.tum.Banking.controllers.Transaction;

import com.tum.Banking.dto.TransactionRequestDTO;
import com.tum.Banking.service.Account.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/create")
    public ResponseEntity<?> createTransaction(@RequestBody TransactionRequestDTO transactionRequestDTO) {

        return transactionService.createTransaction(transactionRequestDTO);
    }


    @GetMapping("/find_by_account_id")
    public ResponseEntity<?> getTransaction(@RequestParam("id") String id) {

        return transactionService.getAllTransactions(id);
    }
}
