package com.example.books;

import com.example.books.model.Author;
import com.example.books.model.Book;
import com.example.books.model.Genre;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BookTest {

    @Test
    public void testBookModel() {
        Genre genre = new Genre(1L, "Fiction");
        Author author = new Author(1L, "John Doe");
        Book book = new Book(1L,"Fantastic Book", author, genre, 10.99, 5);

        Assertions.assertEquals(1L, book.getId());
        Assertions.assertEquals("Fantastic Book", book.getTitle());
        Assertions.assertEquals(author, book.getAuthor());
        Assertions.assertEquals(genre, book.getGenre());
        Assertions.assertEquals(10.99, book.getPrice());

        Book newBook = new Book();
        newBook.setTitle("Amazing Book");
        newBook.setAuthor(author);
        newBook.setGenre(genre);
        newBook.setPrice(12.99);

        Assertions.assertEquals("Amazing Book", newBook.getTitle());
        Assertions.assertEquals(author, newBook.getAuthor());
        Assertions.assertEquals(genre, newBook.getGenre());
        Assertions.assertEquals(12.99, newBook.getPrice());
    }
}
