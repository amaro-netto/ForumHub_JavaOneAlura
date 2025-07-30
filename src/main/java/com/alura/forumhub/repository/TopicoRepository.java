package com.alura.forumhub.repository; // Pacote onde a interface TopicoRepository está localizada

import com.alura.forumhub.model.Topico; // Importa a entidade Topico
import org.springframework.data.jpa.repository.JpaRepository; // Importa a interface base do Spring Data JPA

// TopicoRepository estende JpaRepository para fornecer operações CRUD prontas para a entidade Topico
// JpaRepository<T, ID> -> T é o tipo da entidade (Topico), ID é o tipo da chave primária (Long)
public interface TopicoRepository extends JpaRepository<Topico, Long> {

    // Método customizado para verificar se um tópico com o mesmo título e mensagem já existe,
    // excluindo o próprio tópico caso esteja sendo atualizado.
    // Usado para garantir a unicidade de tópicos.
    boolean existsByTituloAndMensagem(String titulo, String mensagem);

    // Método customizado para verificar se um tópico com o mesmo título e mensagem já existe,
    // mas ignorando um determinado ID. Isso é útil para validações de atualização.
    boolean existsByTituloAndMensagemAndIdNot(String titulo, String mensagem, Long id);

    // Método customizado para encontrar um tópico pelo título, ignorando maiúsculas/minúsculas.
    Topico findByTituloIgnoreCase(String titulo);
}