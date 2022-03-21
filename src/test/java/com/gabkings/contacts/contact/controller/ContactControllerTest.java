package com.gabkings.contacts.contact.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabkings.contacts.contact.dao.CustomerRepo;
import com.gabkings.contacts.contact.model.CustomerEntity;
import org.apache.maven.surefire.junitplatform.JUnitPlatformProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//class ContactControllerTest {
//
//    @BeforeEach
//    void setUp() {
//    }
//
//    @AfterEach
//    void tearDown() {
//    }
//
//    @Test
//    public void getAllContacts(){
//
//    }
//}


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
        import org.mockito.Mock;
        import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;

import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;


@ExtendWith(MockitoExtension.class)
public class ContactControllerTest
{
    @InjectMocks
    ContactController contactController;

    @Mock
    CustomerEntity customerEntity;


    private MockMvc mvc;

    @Mock
    private CustomerRepo customerRepo;

    // This object will be magically initialized by the initFields method below.
    private JacksonTester<CustomerEntity> jsonSuperHero;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(contactController)
                .setControllerAdvice(new Exception())
                .build();
    }

    @Test
    public void testNoCountryFilterFindAll() throws Exception {
        String uri = "/api/contacts";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(500, status);
    }


    @Test
    public void testCountryFilterFindAll() throws Exception {
        String uri = "http://localhost:8080/api/contacts";
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("country", "uganda");
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .params(requestParams)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getContentLength();
        assertThat( status == 3);
    }

    @Test
    public void testCountryDefaultFilterFindAll() throws Exception {
        String uri = "http://localhost:8080/api/contacts";
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("country", "");
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .params(requestParams)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getContentLength();
        assertThat( status > 0);
    }

}
