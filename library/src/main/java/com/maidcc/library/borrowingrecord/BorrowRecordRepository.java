package com.maidcc.library.borrowingrecord;

import com.maidcc.library.book.Book;
import com.maidcc.library.patron.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BorrowRecordRepository extends JpaRepository<BorrowRecord,Long> {
    BorrowRecord findTopByPatronAndBookOrderByActionDateDesc(Patron patron, Book book);
}
