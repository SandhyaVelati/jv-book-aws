package com.northcoders.jokemanagerapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.northcoders.jokemanagerapi.model.Joke;
import com.northcoders.jokemanagerapi.service.JokeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static com.northcoders.jokemanagerapi.model.Joke.JokeCategories.PROGRAMMING;
import static com.northcoders.jokemanagerapi.model.Joke.JokeCategories.PUN;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@AutoConfigureMockMvc
@SpringBootTest
class JokeControllerTests {

    @Mock
    private JokeServiceImpl mockJokeServiceImpl;

    @InjectMocks
    private JokeController jokeController;

    @Autowired
    private MockMvc mockMvcController;

    private ObjectMapper mapper;

    @BeforeEach
    public void setup() {
        mockMvcController = MockMvcBuilders.standaloneSetup(jokeController).build();
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        //mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    @DisplayName("GET  /jokes")
    void getJokes() throws Exception {

        //Arrange
        List<Joke> jokes = new ArrayList<>();
        jokes.add(new Joke(1L, "Why did the developer leave their job?", "Because they didn't get arrays.", Joke.JokeCategories.PROGRAMMING, true, Instant.now(), Instant.now()));
        jokes.add(new Joke(2L, "A polar bear walks into a bar and says to the bartender I’ll have a rum …………………. and coke.The bartender asks, What’s with the big pause?", "The bear shrugs. I was born with them", Joke.JokeCategories.PUN, false, Instant.now(), Instant.now()));
        jokes.add(new Joke(3L, "The bartender says you mean a martini?", "The Roman replies no, if I wanted a double I would have asked for one.", Joke.JokeCategories.PUN, true, Instant.now(), Instant.now()));

        when(mockJokeServiceImpl.getAllJokes()).thenReturn(jokes);
        //Act
        this.mockMvcController.perform(
                MockMvcRequestBuilders.get("/api/v1/jokes"))

        //Assert
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].setupLine").value("Why did the developer leave their job?"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].punchLine").value("Because they didn't get arrays."))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].category").value(Joke.JokeCategories.PROGRAMMING))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].funny").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].punchLine").value("The bear shrugs. I was born with them"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].id").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].punchLine").value("The Roman replies no, if I wanted a double I would have asked for one."));
    }

    @Test
    @DisplayName("POST /jokes")
//    What should be mocked, and why?
//    The various happy path tests, when POST is valid
//    The various sad path tests (various possible invalid payloads, POSTing an existing id)
    void postJoke() throws Exception{
        Instant now = Instant.now();
        //Arrange
        Joke joke1 = Joke.builder()
                .id(1L)
                .isFunny(true)
                .setupLine("Why did the developer leave their job?")
                .punchLine("Because they didn't get arrays.")
                .category(PROGRAMMING)
                .createdAt(now)
                .modifiedAt(now)
                .build();
        //act
        when(mockJokeServiceImpl.addJokeItem(any(Joke.class))).thenReturn(joke1);
        //assert
        this.mockMvcController.perform(
                        MockMvcRequestBuilders.post("/api/v1/jokes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(joke1))) // includes response body
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.funny").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.setupLine").value("Why did the developer leave their job?"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category").value("PROGRAMMING"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.punchLine").value("Because they didn't get arrays."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt").value(joke1.getCreatedAt().getEpochSecond()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.modifiedAt").value(joke1.getModifiedAt().getEpochSecond()));
        verify(mockJokeServiceImpl,times(1)).addJokeItem(any(Joke.class));
    }
}