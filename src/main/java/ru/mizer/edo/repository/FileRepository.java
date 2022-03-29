package ru.mizer.edo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.mizer.edo.model.entity.FilesPath;

import java.util.Collection;

public interface FileRepository extends JpaRepository<FilesPath, Integer> {

    Collection<FilesPath> findByDoc_Id(@Param("docId") int docId);
}
