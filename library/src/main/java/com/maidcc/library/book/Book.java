package com.maidcc.library.book;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.maidcc.library.borrowingrecord.BorrowRecord;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;


import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

@Entity
@Table
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOOK_ID")
    private Long bookId;
    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title must be less than 100 characters")
    private String title;
    @Size(max = 100, message = "Author name must be less than 100 characters")
    private String author;
    private Timestamp publicationYear;
    @NotBlank(message = "Title is required")
    @Size(max = 6,min =6 ,message = "Wrong ISBN")
    @Column(unique = true)
    private String isbn;
    @Min(value = 0, message = "We apologize, We ran out of copies of this book")
    private int numberInShelf;
    @Min(value = 0, message = "numberBorrowed can not be less than 0")
    private int numberBorrowed;

    @OneToMany(mappedBy = "book",cascade = CascadeType.REMOVE)
    //@JsonIgnore
    private Set<BorrowRecord> borrowRecords;

    public Book(Long bookId, String title, String author, Timestamp publicationYear, String isbn, int numberInShelf, int numberBorrowed) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.isbn = isbn;
        this.numberInShelf = numberInShelf;
        this.numberBorrowed = numberBorrowed;
    }

    public Book() {
    }

    public Long getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Timestamp getPublicationYear() {
        return publicationYear;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getNumberInShelf() {
        return numberInShelf;
    }

    public int getNumberBorrowed() {
        return numberBorrowed;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublicationYear(Timestamp publicationYear) {
        this.publicationYear = publicationYear;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setNumberInShelf(int numberInShelf) {
        this.numberInShelf = numberInShelf;
    }

    public void setNumberBorrowed(int numberBorrowed) {
        this.numberBorrowed = numberBorrowed;
    }
}
