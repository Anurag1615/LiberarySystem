package com.Library.main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.Library.main.Repository.BookRepository;
import com.Library.main.entity.Book;
import com.Library.main.service.BookService;

@SpringBootTest
class LibrarySystemApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    private Book book;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setIsbn("1234567890");
        book.setPublishedDate(LocalDate.now());
    }

    @Test
    void testCreateBook() {
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        
        Book createdBook = bookService.createBook(book);
        
        assertNotNull(createdBook);
        assertEquals("Test Book", createdBook.getTitle());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testGetBookById() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        
        Book foundBook = bookService.getBookById(1L);
        
        assertNotNull(foundBook);
        assertEquals("Test Book", foundBook.getTitle());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void testGetBookByIdNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        
        Book foundBook = bookService.getBookById(1L);
        
        assertNull(foundBook);
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateBook() {
        when(bookRepository.existsById(1L)).thenReturn(true);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        
        book.setTitle("Updated Book Title");
        Book updatedBook = bookService.updateBook(1L, book);
        
        assertNotNull(updatedBook);
        assertEquals("Updated Book Title", updatedBook.getTitle());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testUpdateBookNotFound() {
        when(bookRepository.existsById(1L)).thenReturn(false);
        
        Book updatedBook = bookService.updateBook(1L, book);
        
        assertNull(updatedBook);
        verify(bookRepository, times(0)).save(book);
    }

    @Test
    void testDeleteBook() {
        when(bookRepository.existsById(1L)).thenReturn(true);
        
        boolean result = bookService.deleteBook(1L);
        
        assertTrue(result);
        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteBookNotFound() {
        when(bookRepository.existsById(1L)).thenReturn(false);
        
        boolean result = bookService.deleteBook(1L);
        
        assertFalse(result);
        verify(bookRepository, times(0)).deleteById(1L);
    }

    @Test
    void testListBooks() {
        when(bookRepository.findAll()).thenReturn(List.of(book));
        
        List<Book> books = bookService.listBooks();
        
        assertNotNull(books);
        assertEquals(1, books.size());
        assertEquals("Test Book", books.get(0).getTitle());
        verify(bookRepository, times(1)).findAll();
    }

}
