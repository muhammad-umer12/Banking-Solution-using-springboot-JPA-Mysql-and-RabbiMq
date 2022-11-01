package com.tum.Banking.service.QueueService;

import com.tum.Banking.BankingApplication;
import com.tum.Banking.dto.TransactionResponseDTO;
import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TransactionMessageSenderService {


    private final RabbitTemplate rabbitTemplate;

    public TransactionMessageSenderService(final RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(TransactionResponseDTO transactionResponseDTO) {
        //final var message = new CustomMessage("Hello there!", new Random().nextInt(50), false);

        rabbitTemplate.convertAndSend(BankingApplication.EXCHANGE_NAME, BankingApplication.ROUTING_KEY, transactionResponseDTO);
    }
}
