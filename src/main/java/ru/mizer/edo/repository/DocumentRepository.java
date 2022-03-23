package ru.mizer.edo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mizer.edo.model.entity.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {


    Page<Document> findByIsDoneFalseOrderByDateCreateDesc(Pageable pageable);

    Page<Document> findByIsDoneTrueOrderByDateLastEditDesc(Pageable pageable);
}
