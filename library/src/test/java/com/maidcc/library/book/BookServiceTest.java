package com.maidcc.library.book;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class BookServiceTest {


    @Autowired
    private BookRepository bookRepository;

    private BookService underTest;

    @BeforeEach
    void setUp() {
        underTest = new BookService(bookRepository);
    }

    @AfterEach
    void tearDown() {
        bookRepository.deleteAll();
    }

    @Test
    public void testGetAllBooks() throws JsonProcessingException {
        Book callOfTheWild = new Book(
                1L,
                "Call of the wild",
                "Jack London",
                new Timestamp(System.currentTimeMillis()),
                "123123",
                10,
                10);
        Book lookingForAlaska = new Book(
                2L,
                "Looking For Alaska",
                "John Green",
                new Timestamp(System.currentTimeMillis()),
                "321321",
                10,
                10);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        System.out.println(ow.writeValueAsString(callOfTheWild));

        bookRepository.saveAll(Arrays.asList(callOfTheWild, lookingForAlaska));

        List<Book> books = underTest.getAllBooks();

        assertEquals(2, books.size());
    }

    @Test
    public void testGetBookById() {
        Book callOfTheWild = new Book(
                1L,
                "Call of the wild",
                "Jack London",
                new Timestamp(System.currentTimeMillis()),
                "123123",
                10,
                10);
        bookRepository.save(callOfTheWild);

        Optional<Book> result = underTest.getBook(1L);

        assertEquals(1L, result.get().getBookId());
    }

    @Test
    public void testSaveBook() {
        Book callOfTheWild = new Book(
                1L,
                "Call of the wild",
                "Jack London",
                new Timestamp(System.currentTimeMillis()),
                "123123",
                10,
                10);

        underTest.saveBook(callOfTheWild);
        Book result = bookRepository.getReferenceById(1L);
        assertEquals(1L, result.getBookId());
    }


    @Test
    public void testDeleteBook() {
        Book callOfTheWild = new Book(
                1L,
                "Call of the wild",
                "Jack London",
                new Timestamp(System.currentTimeMillis()),
                "123123",
                10,
                10);

        underTest.deleteBook(1L);
        Book result = bookRepository.getReferenceById(1L);
    }

    @Test
    public void testUpdateBook() {
        Book callOfTheWild = new Book(
                1L,
                "Call of the wild",
                "Jack London",
                new Timestamp(System.currentTimeMillis()),
                "123123",
                10,
                10);

        Book callOfTheWildV2 = new Book(
                1L,
                "Call of the wildV2",
                "Jack London junior",
                new Timestamp(System.currentTimeMillis()),
                "123123",
                10,
                10);

        underTest.updateBook(1L,callOfTheWildV2);
        Book result = bookRepository.getReferenceById(1L);
      //  assertTrue();
    }


}
