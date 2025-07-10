package br.com.alura.literatura.repository;

import br.com.alura.literatura.model.Literature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LiteratureRepository extends JpaRepository<Literature, Long> {
}
