package com.Library.main.Controller;



import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Library.main.entity.Book;
import com.Library.main.service.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Book Management", description = "CRUD operations for managing books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(summary = "Create a new book", description = "Adds a new book to the library")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Book successfully created"),
        @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        return ResponseEntity.status(201).body(bookService.createBook(book));
    }

    @Operation(summary = "Get a book by ID", description = "Retrieves a book based on its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Book found"),
        @ApiResponse(responseCode = "404", description = "Book not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        return (book != null) ? ResponseEntity.ok(book) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Update a book", description = "Updates the details of an existing book")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Book successfully updated"),
        @ApiResponse(responseCode = "404", description = "Book not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        Book updatedBook = bookService.updateBook(id, book);
        return (updatedBook != null) ? ResponseEntity.ok(updatedBook) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete a book", description = "Deletes a book by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Book successfully deleted"),
        @ApiResponse(responseCode = "404", description = "Book not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        boolean deleted = bookService.deleteBook(id);
        return (deleted) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @Operation(summary = "List all books", description = "Retrieves a list of all books in the library")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Books retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<Book>> listBooks() {
        return ResponseEntity.ok(bookService.listBooks());
    }
}
