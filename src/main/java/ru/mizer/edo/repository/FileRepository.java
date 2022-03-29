package ru.mizer.edo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mizer.edo.model.entity.FilesPath;

public interface FileRepository extends JpaRepository<FilesPath, Integer> {
}
