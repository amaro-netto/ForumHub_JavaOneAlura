package com.alura.forumhub.model; // Pacote onde a classe Usuario está localizada

import jakarta.persistence.*; // Anotações JPA para persistência
import lombok.AllArgsConstructor; // Lombok para construtor com todos os argumentos
import lombok.EqualsAndHashCode; // Lombok para equals e hashCode
import lombok.Getter; // Lombok para getters
import lombok.NoArgsConstructor; // Lombok para construtor sem argumentos
import org.springframework.security.core.GrantedAuthority; // Parte do Spring Security para papéis/perfis
import org.springframework.security.core.authority.SimpleGrantedAuthority; // Implementação de GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails; // Interface principal do Spring Security para detalhes do usuário

import java.util.Collection; // Coleção de objetos
import java.util.List; // Lista de objetos

@Table(name = "usuarios") // Define o nome da tabela no banco de dados
@Entity(name = "Usuario") // Declara que esta classe é uma entidade JPA
@Getter // Gera métodos getters para todos os campos
@NoArgsConstructor // Gera construtor sem argumentos (exigido pelo JPA)
@AllArgsConstructor // Gera construtor com todos os argumentos
@EqualsAndHashCode(of = "id") // Gera equals e hashCode baseados no campo 'id'
public class Usuario implements UserDetails { // Implementa UserDetails para integração com Spring Security

    @Id // Marca o campo 'id' como chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Estratégia de geração de valor para a chave primária
    private Long id; // Identificador único do usuário

    private String nome; // Nome completo do usuário

    @Column(unique = true) // Garante que o email seja único no banco de dados
    private String email; // Endereço de e-mail do usuário, usado para login

    private String senha; // Senha do usuário (será armazenada criptografada)

    // Métodos da interface UserDetails para integração com Spring Security:

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Por enquanto, vamos retornar uma autoridade simples para todos os usuários.
        // Em um projeto mais complexo, você poderia ter diferentes perfis (ROLE_ADMIN, ROLE_USER, etc.)
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return senha; // Retorna a senha do usuário
    }

    @Override
    public String getUsername() {
        return email; // Usa o email como nome de usuário para autenticação
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Indica que a conta do usuário não está expirada
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Indica que a conta do usuário não está bloqueada
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Indica que as credenciais do usuário não estão expiradas
    }

    @Override
    public boolean isEnabled() {
        return true; // Indica que o usuário está habilitado
    }
}