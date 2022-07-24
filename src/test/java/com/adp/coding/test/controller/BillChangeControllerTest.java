package com.adp.coding.test.controller;

import com.adp.coding.test.model.request.ChangeBillRequest;
import com.adp.coding.test.service.BillChangeService;
import com.adp.coding.test.validator.BillChangeValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value= BillChangeController.class)
@AutoConfigureMockMvc
public class BillChangeControllerTest {

    private final String CHANGE_BILL_URI = "/api/bill/change";

   // @Autowired
    MockMvc mockMvc;

    @MockBean
    BillChangeService billChangeService;

    @MockBean
    BillChangeValidator billChangeValidator;

    @BeforeEach
    void setup() {
        BillChangeController billChangeController = new BillChangeController(billChangeService,billChangeValidator);
        this.mockMvc = MockMvcBuilders.standaloneSetup(billChangeController)
                .setValidator(billChangeValidator)
                .build();
        when(billChangeValidator.supports(any())).thenReturn(true);
    }


    @Test
    public void changeBill() throws Exception{
        ChangeBillRequest changeBillRequest = new ChangeBillRequest();
        changeBillRequest.setBill(10);
        mockMvc.perform(MockMvcRequestBuilders.post(CHANGE_BILL_URI)
              //  .header("","")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(changeBillRequest))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    public static String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
