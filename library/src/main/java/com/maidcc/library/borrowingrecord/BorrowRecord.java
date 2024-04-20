package com.maidcc.library.borrowingrecord;

import com.maidcc.library.book.Book;
import com.maidcc.library.patron.Patron;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.sql.Timestamp;
import java.util.Date;
@Entity
@Table
public class BorrowRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BORROWRECORD_ID")
    private Long borrowRecordID;

    private Timestamp actionDate;

    private String isBorrowing;

    private String isReturning;

    @ManyToOne
    @JoinColumn(name = "BOOK_ID")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "Patron_ID")
    private Patron patron;

    public BorrowRecord() {
    }

    public BorrowRecord(Long borrowRecordID, Timestamp actionDate, String isBorrowing, String isReturning, Book book, Patron patron) {
        this.borrowRecordID = borrowRecordID;
        this.actionDate = actionDate;
        this.isBorrowing = isBorrowing;
        this.isReturning = isReturning;
        this.book = book;
        this.patron = patron;
    }

    public Long getBorrowRecordID() {
        return borrowRecordID;
    }

    public Timestamp getActionDate() {
        return actionDate;
    }

    public String getIsBorrowing() {
        return isBorrowing;
    }

    public String getIsReturning() {
        return isReturning;
    }

    public Book getBook() {
        return book;
    }

    public Patron getPatron() {
        return patron;
    }

    public void setBorrowRecordID(Long borrowRecordID) {
        this.borrowRecordID = borrowRecordID;
    }

    public void setActionDate(Timestamp actionDate) {
        this.actionDate = actionDate;
    }

    public void setIsBorrowing() {
        this.isBorrowing = "Y";
        this.isReturning = "N";
        int numBorrowed = this.book.getNumberBorrowed()+1;
        this.book.setNumberBorrowed(numBorrowed);
        int numInShelf = this.book.getNumberInShelf()-1;
        if (numInShelf<0) {
            throw new RuntimeException("Borrowing more than we have");
        }
        this.book.setNumberInShelf(numInShelf);
    }

    public void setIsReturning() {
        this.isReturning = "Y";
        this.isBorrowing = "N";
        int numBorrowed = this.book.getNumberBorrowed()-1;
        if (numBorrowed<0) {
            throw new RuntimeException("SomeThing went wrong");
        }
        this.book.setNumberBorrowed(numBorrowed);
        int numInShelf = this.book.getNumberInShelf()+1;
        this.book.setNumberInShelf(numInShelf);
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setPatron(Patron patron) {
        this.patron = patron;
    }
}
