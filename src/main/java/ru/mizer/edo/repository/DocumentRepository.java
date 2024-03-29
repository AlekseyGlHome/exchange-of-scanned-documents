package ru.mizer.edo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.mizer.edo.model.entity.Document;
import ru.mizer.edo.model.entity.User;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {


    Page<Document> findByIsDoneFalseOrderByDateCreateDesc(Pageable pageable);

    Page<Document> findByIsDoneTrueOrderByDateLastEditDesc(Pageable pageable);

    Page<Document> findByIsDoneFalseAndAutorIdOrderByDateCreateDesc(int id, Pageable pageable);

    Page<Document> findByIsDoneTrueAndAutorIdOrderByDateLastEditDesc(int id, Pageable pageable);

    @Query("select count(d) from Document d where d.autor.id = :userId or d.userLastChange.id = :userId")
    Long countByAutorOrUserLastChange(@Param("userId") int userId);
}
