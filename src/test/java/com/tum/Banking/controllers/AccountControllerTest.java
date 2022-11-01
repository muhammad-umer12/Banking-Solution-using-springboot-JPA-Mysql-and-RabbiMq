package com.tum.Banking.controllers;

import org.junit.runner.RunWith;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tum.Banking.dto.AccountRequestDTO;
import com.tum.Banking.service.Account.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)



public class AccountControllerTest {

    @MockBean
    private AccountService accountService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createAccountTest() throws Exception {

        AccountRequestDTO request = new AccountRequestDTO();
        request.setCountry("USA");
        List<String> currency = new ArrayList<>();
        currency.add("USD");
        request.setCurrency(currency);
        request.setCustomerId(1);

        this.mockMvc
                .perform(
                        MockMvcRequestBuilders.post("http://localhost:8091/api/v1/account/create").contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(request))
                                .accept(MediaType.APPLICATION_JSON)

                )
                .andExpect(status().isOk());



    }

    @Test
    public void getAccountTest() throws Exception {

        this.mockMvc
                .perform(
                        MockMvcRequestBuilders.get("http://localhost:8091/api/v1/account?id=89b67788-eb8e-409d-9e7c-3939fd7f76b")

                ).andExpect(status().isOk());

        // .andExpect(jsonPath("$[0].id").exists());

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
