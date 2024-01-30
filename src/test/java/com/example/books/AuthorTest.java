package com.example.books;

import com.example.books.model.Author;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AuthorTest {

    @Test
    public void testAuthorModel() {
        Author author = new Author(1L,"John Doe");

        Assertions.assertEquals(1L, author.getId());
        Assertions.assertEquals("John Doe", author.getName());

        author.setName("Jane Smith");

        Assertions.assertEquals("Jane Smith", author.getName());
    }
}
