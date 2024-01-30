package com.example.books;

import com.example.books.controller.AuthorController;
import com.example.books.model.Author;
import com.example.books.repository.AuthorRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(AuthorController.class)
public class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorRepository authorRepository;

    @Test
    public void testGetAllAuthors() throws Exception {
        Author author1 = new Author("John Doe");
        Author author2 = new Author("Jane Smith");
        List<Author> authors = Arrays.asList(author1, author2);

        when(authorRepository.findAll()).thenReturn(authors);

        mockMvc.perform(MockMvcRequestBuilders.get("/authors"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(authors.size()));
    }

    @Test
    public void testGetAuthorById() throws Exception {
        Author author = new Author("John Doe");

        when(authorRepository.findById(eq(1L))).thenReturn(Optional.of(author));

        mockMvc.perform(MockMvcRequestBuilders.get("/authors/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(author.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(author.getName()));
    }

    @Test
    public void testGetAuthorById_NotFound() throws Exception {
        when(authorRepository.findById(eq(1L))).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/authors/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    // Similar tests for create, update, and delete methods
    @Test
    public void testCreateAuthor() throws Exception {
        Author authorToCreate = new Author(null, "John Doe");
        Author createdAuthor = new Author(1L, "John Doe");

        when(authorRepository.save(any(Author.class))).thenReturn(createdAuthor);

        mockMvc.perform(MockMvcRequestBuilders.post("/authors")
                        .content(asJsonString(authorToCreate))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(createdAuthor.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(createdAuthor.getName()));
    }

    @Test
    public void testUpdateAuthor() throws Exception {
        Author existingAuthor = new Author(1L, "John Doe");
        Author updatedAuthor = new Author(1L, "Jane Smith");

        when(authorRepository.findById(eq(1L))).thenReturn(Optional.of(existingAuthor));
        when(authorRepository.save(any(Author.class))).thenReturn(updatedAuthor);

        mockMvc.perform(MockMvcRequestBuilders.put("/authors/1")
                        .content(asJsonString(updatedAuthor))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(updatedAuthor.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(updatedAuthor.getName()));
    }

    @Test
    public void testDeleteAuthor() throws Exception {
        Author authorToDelete = new Author(1L, "John Doe");

        when(authorRepository.findById(eq(1L))).thenReturn(Optional.of(authorToDelete));

        mockMvc.perform(MockMvcRequestBuilders.delete("/authors/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    private String asJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}