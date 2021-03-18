package uk.co.boothen.cloud.stream.api;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import uk.co.boothen.cloud.wiremock.server.model.Book;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldMakeRequest() throws Exception {

        String expectedJson = objectMapper.writeValueAsString(List.of(new Book().title("some title")));

        mockMvc.perform(MockMvcRequestBuilders.get("/book"))
               .andExpect(status().is2xxSuccessful())
               .andExpect(content().json(expectedJson));
    }
}