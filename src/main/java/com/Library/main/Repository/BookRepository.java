package com.Library.main.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Library.main.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
