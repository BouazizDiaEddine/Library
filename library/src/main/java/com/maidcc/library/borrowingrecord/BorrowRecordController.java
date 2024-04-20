package com.maidcc.library.borrowingrecord;

import com.maidcc.library.book.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BorrowRecordController {

    private final BorrowRecordService borrowRecordService;
    @Autowired
    public BorrowRecordController(BorrowRecordService borrowRecordService) {
        this.borrowRecordService = borrowRecordService;
    }


    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<?> borrowing(@PathVariable Long bookId,@PathVariable Long patronId) {
        try {
            return ResponseEntity.ok(borrowRecordService.borrowBook(bookId,patronId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<?> returning(@PathVariable Long bookId,@PathVariable Long patronId) {
        try {
            return ResponseEntity.ok(borrowRecordService.returnBook(bookId,patronId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
