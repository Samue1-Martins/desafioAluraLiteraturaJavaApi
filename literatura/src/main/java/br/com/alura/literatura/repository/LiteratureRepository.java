package br.com.alura.literatura.repository;

import br.com.alura.literatura.model.Authors;
import br.com.alura.literatura.model.Literature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LiteratureRepository extends JpaRepository<Literature, Long> {
    Optional<Literature> findByTitleContainingIgnoreCase(String nameBook);

    @Query("SELECT l FROM Literature l JOIN l.authorsList a WHERE a.birthYear <= :searchYear AND a.deathYear >= :searchYear")
    List<Literature> findByBirthYearAndDeathYear(int searchYear);

    @Query("SELECT l FROM Literature l JOIN l.languages lang WHERE lang = :searchBooksByLanguage")
    List<Literature> findByBookLanguage(String searchBooksByLanguage);
}
