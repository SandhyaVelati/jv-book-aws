package com.northcoders.jokemanagerapi.service;

import com.northcoders.jokemanagerapi.model.Joke;
import com.northcoders.jokemanagerapi.repository.JokeItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Equals;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;


@DataJpaTest
class JokeServiceTest {

    @Mock
    private JokeItemRepository mockJokeItemRepository;

    @InjectMocks
    private JokeServiceImpl jokesServiceImpl;

    @Test
    @DisplayName("getJokeItems responds with a list of all jokes")
    void testGetAllJokes() {
        //Arrange
        List<Joke> jokes = new ArrayList<>();
        jokes.add(new Joke(1L, "Why did the developer leave their job?", "Because they didn't get arrays.", Joke.JokeCategories.PROGRAMMING, true, Instant.now(), Instant.now()));
        jokes.add(new Joke(2L, "A polar bear walks into a bar and says to the bartender I’ll have a rum …………………. and coke.The bartender asks, What’s with the big pause?", "The bear shrugs. I was born with them", Joke.JokeCategories.PUN, false, Instant.now(), Instant.now()));
        jokes.add(new Joke(3L, "The bartender says you mean a martini?", "The Roman replies no, if I wanted a double I would have asked for one.", Joke.JokeCategories.PUN, true, Instant.now(), Instant.now()));
        //Act
        when(mockJokeItemRepository.findAll()).thenReturn(jokes);
        //assert
        List<Joke> allJokes = jokesServiceImpl.getAllJokes();
        assertNotNull(jokes);
        assertEquals(3,jokes.size());
        assertThat(allJokes,is(equalTo(jokes)));
    }
    @Test
    @DisplayName("test to add a joke successfully")
    void testAddJokeItem(){
        //Arrange

        Joke joke = Joke.builder()
                .category(Joke.JokeCategories.PUN)
                .isFunny(true)
                .setupLine("The bartender says you mean a martini?")
                .punchLine("The Roman replies no, if I wanted a double I would have asked for one.")
//                .createdAt(Instant.now())
//                .modifiedAt(Instant.now())
                .build();
        when(mockJokeItemRepository.save(any(Joke.class))).thenReturn(joke);
        //act
        Joke joke1 = jokesServiceImpl.addJokeItem(joke);
        assertThat(joke1,is(equalTo(joke)));
    }
}