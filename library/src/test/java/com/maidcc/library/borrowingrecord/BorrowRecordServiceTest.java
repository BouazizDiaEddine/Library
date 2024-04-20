package com.maidcc.library.borrowingrecord;

import com.maidcc.library.book.BookRepository;
import com.maidcc.library.book.BookService;
import com.maidcc.library.patron.PatronRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class BorrowRecordServiceTest {

        @Autowired
        private BorrowRecordRepository borrowRecordRepository;
        @Autowired
        private BookRepository bookRepository;
        @Autowired
        private PatronRepository patronRepository;


        private BorrowRecordService underTest;

        @BeforeEach
        void setUp() {
            underTest = new BorrowRecordService(borrowRecordRepository,bookRepository,patronRepository);
        }

        @AfterEach
        void tearDown() {
            borrowRecordRepository.deleteAll();
            bookRepository.deleteAll();
            patronRepository.deleteAll();
        }

        @Test
        public void testBorrow(){

        }

        @Test
        public void testReturn(){

        }
}
