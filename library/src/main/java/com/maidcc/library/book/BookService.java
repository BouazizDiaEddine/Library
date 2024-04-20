package com.maidcc.library.book;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {


    private final static Logger LOGGER = LoggerFactory.getLogger(BookService.class);
    private final BookRepository bookRepository;
    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository =bookRepository;
    }

    @Transactional(readOnly = true)
    List<Book> getAllBooks(){
        LOGGER.info("getting all Books");
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    Optional<Book> getBook(Long id){
        LOGGER.info("Getting book with id : "+id);
        return bookRepository.findById(id);
    }


    public Book saveBook(Book book) {
        try {
            LOGGER.info("Saving book : "+book.toString());
            return bookRepository.save(book);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error("Invalid data: " + e.getMessage());
            throw new IllegalArgumentException("Invalid data: " + e.getMessage());
        }
    }


    public void deleteBook(Long id) {
        if(!bookRepository.existsById(id)) {
            LOGGER.error("Book with ID " + id + " not found");
            throw new IllegalArgumentException("Book with id " + id + " not found");
        }
        LOGGER.info("deleting book with id "+id);
        bookRepository.deleteById(id);
    }

    public Book updateBook(long id, Book book) {
        try {
            Optional<Book> optionalUpdateBook = bookRepository.findById(id);
            if(optionalUpdateBook.isPresent()) {
                Book updateBook = optionalUpdateBook.get();
                updateBook.setTitle(book.getTitle());
                updateBook.setAuthor(book.getAuthor());
                updateBook.setIsbn(book.getIsbn());
                updateBook.setNumberBorrowed(book.getNumberBorrowed());
                updateBook.setNumberInShelf(book.getNumberInShelf());
                updateBook.setPublicationYear(book.getPublicationYear());

                LOGGER.info("Updating book with id : "+id+" to : "+updateBook.toString() );
                bookRepository.save(updateBook);
                return updateBook;
            }else {
                LOGGER.error("Book with ID " + id + " not found");
                throw new IllegalArgumentException("Book with ID " + id + " not found");
            }
        } catch (DataIntegrityViolationException e) {
            LOGGER.error("Invalid data: " + e.getMessage());
            throw new IllegalArgumentException("Invalid data: " + e.getMessage());
        }
    }
}
