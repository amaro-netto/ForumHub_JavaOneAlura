package com.alura.forumhub.repository; // Pacote onde a interface UsuarioRepository está localizada

import com.alura.forumhub.model.Usuario; // Importa a entidade Usuario
import org.springframework.data.jpa.repository.JpaRepository; // Importa a interface base do Spring Data JPA
import org.springframework.security.core.userdetails.UserDetails; // Importa UserDetails para o método de busca

// UsuarioRepository estende JpaRepository para fornecer operações CRUD prontas para a entidade Usuario
// JpaRepository<T, ID> -> T é o tipo da entidade (Usuario), ID é o tipo da chave primária (Long)
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Método customizado para buscar um usuário pelo email.
    // Este método é crucial para o Spring Security, pois a interface UserDetailsService
    // precisa de um método para carregar os detalhes do usuário pelo "username" (que será o email aqui).
    UserDetails findByEmail(String email);
}