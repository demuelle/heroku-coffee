package com.coffee.coffeeordering.controller;

import com.coffee.coffeeordering.model.Coffee;
import com.coffee.coffeeordering.respository.CoffeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CoffeeController.class)
public class CoffeeControllerTest {
    @MockBean
    private CoffeeRepository repo;

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturn201AndCoffeeOnPost() throws Exception {
        Coffee inputCoffee = new Coffee(null, "arabica", "cat", "medium", "Kenya", new BigInteger("5"), new BigDecimal("11.99"));
        String inputJson = mapper.writeValueAsString(inputCoffee);
        Coffee outputCoffee = new Coffee(123L, "arabica", "cat", "medium", "Kenya", new BigInteger("5"), new BigDecimal("11.99"));
        String outputJson = mapper.writeValueAsString(outputCoffee);

        doReturn(outputCoffee).when(repo).save(inputCoffee);
        mockMvc.perform(post("/coffee")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldReturn200AndCoffeeOnGetById() throws Exception {
        Long id = 123L;
        Coffee outputCoffee = new Coffee(id, "arabica", "cat", "medium", "Kenya", new BigInteger("5"), new BigDecimal("11.99"));
        String outputJson = mapper.writeValueAsString(outputCoffee);

        doReturn(Optional.of(outputCoffee)).when(repo).findById(id);
        mockMvc.perform(get("/coffee/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldRespondWith422WhenGetByNotFoundId() throws Exception {
        Long id = 123L;

        doReturn(Optional.empty()).when(repo).findById(id);
        mockMvc.perform(get("/coffee/{id}", id))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldReturn200AndCoffeeListOnGetAll() throws Exception {
        Coffee outputCoffee = new Coffee(123L, "arabica", "cat", "medium", "Kenya", new BigInteger("5"), new BigDecimal("11.99"));
        List<Coffee> outputList = Arrays.asList(outputCoffee);
        String outputJson = mapper.writeValueAsString(outputList);

        doReturn(outputList).when(repo).findAll();

        mockMvc.perform(get("/coffee"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldReturn204OnDelete() throws Exception {
        mockMvc.perform(delete("/coffee/{id}", 123))
                .andDo(print())
                .andExpect(status().isNoContent());
    }


    @Test
    public void shouldReturn204OnUpdate() throws Exception {
        Long id = 8712L;
        Coffee inputCoffee = new Coffee(id, "arabica", "cat", "medium", "Kenya", new BigInteger("5"), new BigDecimal("11.99"));
        String inputJson = mapper.writeValueAsString(inputCoffee);

        mockMvc.perform(put("/coffee/{id}", id)
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }


    @Test
    public void shouldRespondWith422WhenIdPathVariableDoesNotMatchIdInBodyOfPutRequest() throws Exception {
        Long coffeeBodyId = 123L;
        Long pathVariableId = 124L;
        Coffee inputCoffee = new Coffee(coffeeBodyId, "arabica", "cat", "medium", "Kenya", new BigInteger("5"), new BigDecimal("11.99"));
        String inputJson = mapper.writeValueAsString(inputCoffee);

        mockMvc.perform(put("/coffee/{id}", pathVariableId)
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }


}