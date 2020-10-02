package com.gregorriegler.frameworkdependency;

import com.gregorriegler.frameworkdependency.model.LibraryRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LibraryTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LibraryRepository libraryRepository;

    @BeforeEach
    void clearLibrary() {
        libraryRepository.clear();
    }

    @Test
    @WithMockUser("guest")
    void starts_empty() throws Exception {
        mockMvc.perform(get("/library"))
            .andExpect(status().isOk())
            .andExpect(content().json("[]"));
    }

    @Test
    @WithMockUser(username = "manager", roles = "MANAGER")
    void finds_created_book() throws Exception {
        mockMvc.perform(put("/library/123")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new JSONObject()
                .put("title", "Name of Book")
                .put("author", "Name of Author")
                .toString()
            )
        ).andExpect(status().isNoContent());

        mockMvc.perform(get("/library"))
            .andExpect(status().isOk())
            .andDo(print())
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
        mockMvc.perform(put("/library/123")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new JSONObject()
                .put("title", "Name of Book")
                .put("author", "Name of Author")
                .toString())
        ).andExpect(status().isForbidden());
    }
}
