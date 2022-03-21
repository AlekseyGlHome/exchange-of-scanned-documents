package ru.mizer.edo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mizer.edo.model.entity.History;

@Repository
public interface HistoryRepository extends JpaRepository<History, Integer> {
}
