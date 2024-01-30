package com.example.books;

import com.example.books.controller.GenreController;
import com.example.books.model.Genre;
import com.example.books.repository.GenreRepository;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(GenreController.class)
public class GenreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GenreRepository genreRepository;

    @Test
    public void testGetAllGenres() throws Exception {
        Genre genre1 = new Genre(1L, "Fiction");
        Genre genre2 = new Genre(2L, "Mystery");
        List<Genre> genres = Arrays.asList(genre1, genre2);

        when(genreRepository.findAll()).thenReturn(genres);

        mockMvc.perform(MockMvcRequestBuilders.get("/genres"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(genres.size()));
    }

    @Test
    public void testGetGenreById() throws Exception {
        Genre genre = new Genre(1L, "Fiction");

        when(genreRepository.findById(eq(1L))).thenReturn(Optional.of(genre));

        mockMvc.perform(MockMvcRequestBuilders.get("/genres/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(genre.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(genre.getName()));
    }

    @Test
    public void testGetGenreById_NotFound() throws Exception {
        when(genreRepository.findById(eq(1L))).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/genres/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testCreateGenre() throws Exception {
        Genre genreToCreate = new Genre(null, "Fiction");
        Genre createdGenre = new Genre(1L, "Fiction");

        when(genreRepository.save(any(Genre.class))).thenReturn(createdGenre);

        mockMvc.perform(MockMvcRequestBuilders.post("/genres")
                        .content(asJsonString(genreToCreate))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(createdGenre.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(createdGenre.getName()));
    }

    @Test
    public void testUpdateGenre() throws Exception {
        Genre existingGenre = new Genre(1L, "Fiction");
        Genre updatedGenre = new Genre(1L, "Mystery");

        when(genreRepository.findById(eq(1L))).thenReturn(Optional.of(existingGenre));
        when(genreRepository.save(any(Genre.class))).thenReturn(updatedGenre);

        mockMvc.perform(MockMvcRequestBuilders.put("/genres/1")
                        .content(asJsonString(updatedGenre))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(updatedGenre.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(updatedGenre.getName()));
    }

    @Test
    public void testDeleteGenre() throws Exception {
        Genre genreToDelete = new Genre(1L, "Fiction");

        when(genreRepository.findById(eq(1L))).thenReturn(Optional.of(genreToDelete));

        mockMvc.perform(MockMvcRequestBuilders.delete("/genres/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    private String asJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}
