package com.example.book2.repository;

import com.example.book2.domain.Book;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    //비관적 락
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select b from Book b where b.id = :bookId")
    Optional<Book> findByIdForUpdate(Long bookId);

    //원자적update문
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
        update Book b
        set b.quantity = b.quantity - :quantity
        where b.id = :bookId
          and b.quantity >= :quantity
    """)
    int decreaseQuantity(Long bookId, long quantity);

    //테스트를 통해서 두 방법 중 하나를 택하자.
}
