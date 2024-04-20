package com.maidcc.library.borrowingrecord;

import com.maidcc.library.book.Book;
import com.maidcc.library.book.BookRepository;
import com.maidcc.library.book.BookService;
import com.maidcc.library.patron.Patron;
import com.maidcc.library.patron.PatronRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Service
public class BorrowRecordService {

    private final static Logger LOGGER = LoggerFactory.getLogger(BorrowRecordService.class);
    private final BorrowRecordRepository borrowRecordRepository;
    private final BookRepository bookRepository;
    private final PatronRepository patronRepository;

    @Autowired
    public BorrowRecordService(BorrowRecordRepository borrowRecordRepository, BookRepository bookRepository, PatronRepository patronRepository) {
        this.borrowRecordRepository = borrowRecordRepository;
        this.bookRepository = bookRepository;
        this.patronRepository = patronRepository;
    }

    @Transactional
    public BorrowRecord borrowBook(long bookId,long patronId) {

        Optional<Patron> patron= patronRepository.findById(patronId);
        Optional<Book> book = bookRepository.findById(bookId);

        if(patron.isPresent() && book.isPresent()) {
            BorrowRecord lastBorrowRecord = borrowRecordRepository.findTopByPatronAndBookOrderByActionDateDesc(patron.get(),book.get());
            if (lastBorrowRecord == null || lastBorrowRecord.getIsReturning().equals("Y")) {
                try {
                    BorrowRecord borrowRecord= new BorrowRecord();
                    borrowRecord.setBook(book.get());
                    borrowRecord.setPatron(patron.get());
                    borrowRecord.setIsBorrowing();
                    borrowRecord.setActionDate(Timestamp.from(Instant.now()));
                    LOGGER.info("Borrowing book id :"+bookId+" for Patron id : "+patronId);
                    return borrowRecordRepository.save(borrowRecord);
                } catch (EmptyResultDataAccessException e) {
                    LOGGER.error("Invalid data: " + e.getMessage());
                    throw new IllegalArgumentException("Invalid data: " + e.getMessage());
                }
            } else {
                LOGGER.error("You already borrowed the book on : " + lastBorrowRecord.getActionDate() + " and you haven't returned it yet");
                throw new IllegalArgumentException("You already borrowed the book on : " + lastBorrowRecord.getActionDate() + " and you haven't returned it yet");
            }
        }else {
            LOGGER.error("Book or Patron do not exist : bood id "+bookId+" - patron id "+patron );
            throw new IllegalArgumentException("Book or Patron do not exist");
        }
    }

    @Transactional
    public BorrowRecord returnBook(Long bookId,Long patronId) {

        Optional<Patron> patron= patronRepository.findById(patronId);
        Optional<Book> book = bookRepository.findById(bookId);

        if(patron.isPresent() && book.isPresent()) {
            BorrowRecord lastBorrowRecord = borrowRecordRepository.findTopByPatronAndBookOrderByActionDateDesc(patron.get(),book.get());
            if (lastBorrowRecord == null) {
                LOGGER.error("You don't have this book in your borrowed list");
                throw new IllegalArgumentException("You don't have this book in your borrowed list");
            } else if (lastBorrowRecord.getIsReturning().equals("Y")) {
                LOGGER.error("You have already returned this book on : " + lastBorrowRecord.getActionDate());
                throw new IllegalArgumentException("You have already returned this book on : " + lastBorrowRecord.getActionDate());
            } else {
                try {
                    BorrowRecord borrowRecord= new BorrowRecord();
                    borrowRecord.setBook(book.get());
                    borrowRecord.setPatron(patron.get());
                    borrowRecord.setIsReturning();
                    borrowRecord.setActionDate(Timestamp.from(Instant.now()));
                    LOGGER.info("Returning book id :"+bookId+" from Patron id : "+patronId);
                    return borrowRecordRepository.save(borrowRecord);
                } catch (EmptyResultDataAccessException e) {
                    LOGGER.error("Invalid data: " + e.getMessage());
                    throw new IllegalArgumentException("Invalid data: " + e.getMessage());
                }
            }
        }else {
            LOGGER.error("Book or Patron do not exist : bood id "+bookId+" - patron id "+patron );
            throw new IllegalArgumentException("Book or Patron do not exist");
        }
    }

}
