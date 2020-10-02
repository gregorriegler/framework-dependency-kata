package com.gregorriegler.frameworkdependency;

import com.gregorriegler.frameworkdependency.model.Book;
import com.gregorriegler.frameworkdependency.repository.BookRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LibraryTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    public void clearRepository() {
        bookRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void starts_empty() throws Exception {
        mockMvc.perform(get("/books"))
            .andExpect(status().isOk())
            .andExpect(content().json("[]"));
    }

    @Test
    @WithMockUser(roles = "NOT_A_USER")
    void non_user_cant_access_library() throws Exception {
        mockMvc.perform(get("/books"))
            .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void finds_created_book() throws Exception {
        mockMvc.perform(put("/books/123")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new JSONObject()
                .put("title", "Name of Book")
                .put("author", "Name of Author")
                .toString()
            )
        ).andExpect(status().isNoContent());

        mockMvc.perform(get("/books"))
            .andExpect(status().isOk())
            .andExpect(content().json("[" +
                new JSONObject()
                    .put("isbn", "123")
                    .put("title", "Name of Book")
                    .put("author", "Name of Author")
                    .toString() +
                "]"));
    }

    @Test
    @WithMockUser("guest")
    void guest_cant_create_book() throws Exception {
        mockMvc.perform(put("/books/123")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new JSONObject()
                .put("title", "Name of Book")
                .put("author", "Name of Author")
                .toString())
        ).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "max", roles = "USER")
    void user_can_rate_a_book() throws Exception {
        bookRepository.save(new Book("123", "irrelevant", "irrelevant"));

        mockMvc.perform(post("/books/123/ratings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new JSONObject()
                .put("stars", "FIVE")
                .put("comment", "Rating")
                .toString()))
            .andExpect(status().isCreated());

        mockMvc.perform(get("/books"))
            .andExpect(status().isOk())
            .andExpect(content().json("[" +
                new JSONObject()
                    .put("isbn", "123")
                    .put("title", "irrelevant")
                    .put("author", "irrelevant")
                    .put("ratings", new JSONArray()
                        .put(new JSONObject()
                            .put("user", "max")
                            .put("stars", "FIVE")
                            .put("comment", "Rating")
                        ))
                    .toString() +
                "]"));
    }

    @Test
    @WithMockUser(roles = "NON_USER")
    void non_user_cant_rate_a_book() throws Exception {
        bookRepository.save(new Book("123", "irrelevant", "irrelevant"));

        mockMvc.perform(post("/books/123/ratings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new JSONObject()
                .put("stars", "FIVE")
                .put("comment", "Rating")
                .toString()))
            .andExpect(status().isForbidden());
    }
}
