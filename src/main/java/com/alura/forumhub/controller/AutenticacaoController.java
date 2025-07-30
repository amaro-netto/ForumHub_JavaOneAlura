package com.alura.forumhub.controller;

import com.alura.forumhub.dto.UsuarioAutenticacaoDTO; // DTO para receber credenciais de login
import com.alura.forumhub.config.security.TokenService; // Importa o TokenService para gerar o token JWT
import jakarta.validation.Valid; // Para validar o DTO de entrada
import org.springframework.beans.factory.annotation.Autowired; // Injeção de dependência
import org.springframework.http.ResponseEntity; // Para construir respostas HTTP
import org.springframework.security.authentication.AuthenticationManager; // Gerenciador de autenticação do Spring Security
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; // Objeto para representar as credenciais de autenticação
import org.springframework.web.bind.annotation.PostMapping; // Mapeia requisições HTTP POST
import org.springframework.web.bind.annotation.RequestBody; // Para mapear o corpo da requisição
import org.springframework.web.bind.annotation.RequestMapping; // Define o caminho base
import org.springframework.web.bind.annotation.RestController; // Anota que é um controller REST

@RestController // Indica que esta classe é um controller REST
@RequestMapping("/login") // Define o caminho base para este controller (ex: /login)
public class AutenticacaoController {

    @Autowired // Injeta o AuthenticationManager, configurado pelo Spring Security
    private AuthenticationManager manager;

    @Autowired // Injeta o TokenService para a geração do token
    private TokenService tokenService;

    // Endpoint para realizar o login de um usuário
    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid UsuarioAutenticacaoDTO dados) {
        // Cria um objeto UsernamePasswordAuthenticationToken com as credenciais do usuário
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());

        // Delega o processo de autenticação ao AuthenticationManager
        // O manager vai chamar o UserDetailsService e o PasswordEncoder configurados para autenticar
        var authentication = manager.authenticate(authenticationToken);

        // Se a autenticação for bem-sucedida, gera o token JWT usando o TokenService
        // O getPrincipal() retorna o objeto UserDetails (que é o nosso Usuario) do usuário autenticado
        var tokenJWT = tokenService.gerarToken((com.alura.forumhub.model.Usuario) authentication.getPrincipal());

        // Retorna 200 OK com o token JWT no corpo da resposta
        return ResponseEntity.ok(new TokenDTO(tokenJWT)); // Envolve o token em um DTO de resposta (próximo passo)
    }

    // Classe interna para representar o DTO de resposta do token (será criada como arquivo separado em /dto)
    public record TokenDTO(String token) {
    }
}