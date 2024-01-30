package com.example.books;

import com.example.books.model.Genre;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GenreTest {

    @Test
    public void testGenreModel() {
        Genre genre = new Genre(1L, "Fiction");

        Assertions.assertEquals(1L, genre.getId());
        Assertions.assertEquals("Fiction", genre.getName());

        genre.setName("Mystery");

        Assertions.assertEquals("Mystery", genre.getName());
    }
}

