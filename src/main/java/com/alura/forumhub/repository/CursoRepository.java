package com.alura.forumhub.repository; // Pacote onde a interface CursoRepository está localizada

import com.alura.forumhub.model.Curso; // Importa a entidade Curso
import org.springframework.data.jpa.repository.JpaRepository; // Importa a interface base do Spring Data JPA

// CursoRepository estende JpaRepository para fornecer operações CRUD prontas para a entidade Curso
// JpaRepository<T, ID> -> T é o tipo da entidade (Curso), ID é o tipo da chave primária (Long)
public interface CursoRepository extends JpaRepository<Curso, Long> {

    // Método customizado para buscar um curso pelo nome.
    // Pode ser útil para associar tópicos a cursos existentes.
    Curso findByNome(String nome);
}