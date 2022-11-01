package com.tum.Banking.controllers.Account;

import com.tum.Banking.dto.AccountRequestDTO;
import com.tum.Banking.service.Account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/account")

public class AccountController {

    @Autowired
    AccountService accountService;


    @GetMapping("")
    public ResponseEntity<?> getAccount(@RequestParam("id") String id) {

        return accountService.getAccountById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@RequestBody AccountRequestDTO accountRequestDTO) {

        return accountService.createAccount(accountRequestDTO);
    }

}
