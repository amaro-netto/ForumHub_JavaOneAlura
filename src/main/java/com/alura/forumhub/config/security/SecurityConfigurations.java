package com.alura.forumhub.config.security; // Pacote de configuração de segurança

import org.springframework.beans.factory.annotation.Autowired; // NOVO IMPORT
import org.springframework.context.annotation.Bean; // Para declarar métodos que produzem beans
import org.springframework.context.annotation.Configuration; // Anotação para indicar classe de configuração
import org.springframework.http.HttpMethod; // Tipos de métodos HTTP
import org.springframework.security.authentication.AuthenticationManager; // Gerenciador de autenticação
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration; // Configuração de autenticação
import org.springframework.security.config.annotation.web.builders.HttpSecurity; // Para configurar a segurança web
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; // Ativa a segurança web do Spring
import org.springframework.security.config.http.SessionCreationPolicy; // Política de criação de sessão
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Codificador de senhas
import org.springframework.security.crypto.password.PasswordEncoder; // Interface para codificação de senhas
import org.springframework.security.web.SecurityFilterChain; // Cadeia de filtros de segurança
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; // NOVO IMPORT

@Configuration // Indica ao Spring que esta é uma classe de configuração
@EnableWebSecurity // Habilita o módulo de segurança web do Spring
public class SecurityConfigurations {

    @Autowired // Injeta o SecurityFilter (agora ele é um componente do Spring)
    private SecurityFilter securityFilter; // CAMPO ADICIONADO

    // Metodo para configurar a cadeia de filtros de segurança HTTP
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable()) // Desabilita a proteção CSRF, pois a API REST não usa sessões com cookies
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Define política de sessão como STATELESS (sem estado), ideal para APIs REST com JWT
                .authorizeHttpRequests(req -> { // Configura as regras de autorização para requisições HTTP
                    req.requestMatchers(HttpMethod.POST, "/login").permitAll(); // Permite acesso a /login via POST sem autenticação
                    req.requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll(); // Permite acesso à documentação da API (Swagger UI) sem autenticação
                    req.anyRequest().authenticated(); // Todas as outras requisições exigem autenticação
                })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) // LINHA ADICIONADA: Adiciona o SecurityFilter antes do filtro de autenticação de usuário/senha
                .build(); // Constrói e retorna a cadeia de filtros
    }

    // Metodo para expor o AuthenticationManager como um Bean, permitindo injetá-lo em outros componentes
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // Metodo para configurar o PasswordEncoder (codificador de senhas)
    // Usamos BCryptPasswordEncoder, que é um algoritmo robusto para hashing de senhas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}