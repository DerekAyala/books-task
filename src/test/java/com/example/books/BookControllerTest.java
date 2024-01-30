package com.example.books;

import com.example.books.controller.BookController;
import com.example.books.model.Author;
import com.example.books.model.Book;
import com.example.books.model.Genre;
import com.example.books.repository.BookRepository;
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
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRepository bookRepository;

    @Test
    public void testGetAllBooks() throws Exception {
        Genre genre = new Genre(1L, "Fiction");
        Author author = new Author(1L, "John Doe");
        List<Book> books = Arrays.asList(
                new Book(1L, "Book 1", author, genre, 10.99),
                new Book(2L, "Book 2", author, genre, 12.99)
        );

        when(bookRepository.findAll()).thenReturn(books);

        mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(books.size()));
    }

    @Test
    public void testGetBookById() throws Exception {
        Genre genre = new Genre(1L, "Fiction");
        Author author = new Author(1L, "John Doe");
        Book book = new Book(1L, "Fantastic Book", author, genre, 10.99);

        when(bookRepository.findById(eq(1L))).thenReturn(Optional.of(book));

        mockMvc.perform(MockMvcRequestBuilders.get("/books/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(book.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author.id").value(book.getAuthor().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author.name").value(book.getAuthor().getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre.id").value(book.getGenre().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre.name").value(book.getGenre().getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(book.getPrice()));
    }

    @Test
    public void testGetBookById_NotFound() throws Exception {
        when(bookRepository.findById(eq(1L))).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/books/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testCreateBook() throws Exception {
        Genre genre = new Genre(1L, "Fiction");
        Author author = new Author(1L, "John Doe");
        Book bookToCreate = new Book(null, "Fantastic Book", author, genre, 10.99);
        Book createdBook = new Book(1L, "Fantastic Book", author, genre, 10.99);

        when(bookRepository.save(any(Book.class))).thenReturn(createdBook);

        mockMvc.perform(MockMvcRequestBuilders.post("/books")
                        .content(asJsonString(bookToCreate))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(createdBook.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(createdBook.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author.id").value(createdBook.getAuthor().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author.name").value(createdBook.getAuthor().getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre.id").value(createdBook.getGenre().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre.name").value(createdBook.getGenre().getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(createdBook.getPrice()));
    }

    @Test
    public void testUpdateBook() throws Exception {
        Genre genre = new Genre(1L, "Fiction");
        Author author = new Author(1L, "John Doe");
        Book existingBook = new Book(1L, "Fantastic Book", author, genre, 10.99);
        Book updatedBook = new Book(1L, "Amazing Book", author, genre, 12.99);

        when(bookRepository.findById(eq(1L))).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

        mockMvc.perform(MockMvcRequestBuilders.put("/books/1")
                        .content(asJsonString(updatedBook))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(updatedBook.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(updatedBook.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author.id").value(updatedBook.getAuthor().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author.name").value(updatedBook.getAuthor().getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre.id").value(updatedBook.getGenre().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre.name").value(updatedBook.getGenre().getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(updatedBook.getPrice()));
    }

    @Test
    public void testDeleteBook() throws Exception {
        Genre genre = new Genre(1L, "Fiction");
        Author author = new Author(1L, "John Doe");
        Book bookToDelete = new Book(1L,"Fantastic Book", author, genre, 10.99);

        when(bookRepository.findById(eq(1L))).thenReturn(Optional.of(bookToDelete));

        mockMvc.perform(MockMvcRequestBuilders.delete("/books/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    private String asJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}