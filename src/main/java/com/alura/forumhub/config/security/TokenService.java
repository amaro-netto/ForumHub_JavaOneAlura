package com.alura.forumhub.config.security; // Pacote de configuração de segurança

import com.alura.forumhub.model.Usuario; // Importa a entidade Usuario
import com.auth0.jwt.JWT; // Importa a classe JWT da biblioteca Auth0
import com.auth0.jwt.algorithms.Algorithm; // Importa Algorithm para definir o algoritmo de assinatura
import com.auth0.jwt.exceptions.JWTCreationException; // Exceção para erros na criação do token
import com.auth0.jwt.exceptions.JWTVerificationException; // Exceção para erros na verificação do token
import org.springframework.beans.factory.annotation.Value; // Para injetar valores de propriedades (application.properties)
import org.springframework.stereotype.Service; // Anotação para indicar que é um serviço

import java.time.Instant; // Para trabalhar com pontos no tempo (data/hora)
import java.time.LocalDateTime; // Para trabalhar com data e hora
import java.time.ZoneOffset; // Para definir o fuso horário

@Service // Indica ao Spring que esta classe é um serviço
public class TokenService {

    @Value("${api.security.token.secret}") // Injeta o valor da propriedade 'api.security.token.secret' do application.properties
    private String secret; // Chave secreta para assinar e verificar o token JWT

    // Metodo para gerar o token JWT para um usuário
    public String gerarToken(Usuario usuario) {
        try {
            // Define o algoritmo de assinatura com a chave secreta
            var algoritmo = Algorithm.HMAC256(secret);

            // Constrói e assina o token JWT
            return JWT.create()
                    .withIssuer("API ForumHub") // Define o emissor do token
                    .withSubject(usuario.getEmail()) // Define o "assunto" do token (quem ele representa) como o email do usuário
                    .withExpiresAt(dataExpiracao()) // Define a data de expiração do token
                    .sign(algoritmo); // Assina o token com o algoritmo e a chave secreta
        } catch (JWTCreationException exception) {
            // Lança uma exceção se houver um erro na criação do token
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }

    // Metodo para validar o token JWT e retornar o "subject" (email do usuário)
    public String getSubject(String tokenJWT) {
        try {
            // Define o algoritmo de verificação com a mesma chave secreta usada para assinar
            var algoritmo = Algorithm.HMAC256(secret);

            // Constrói e verifica o token
            return JWT.require(algoritmo)
                    .withIssuer("API ForumHub") // Verifica se o emissor do token corresponde
                    .build() // Constrói o verificador
                    .verify(tokenJWT) // Verifica o token recebido
                    .getSubject(); // Retorna o "subject" (email do usuário) do token verificado
        } catch (JWTVerificationException exception) {
            // Lança uma exceção se o token for inválido, expirado, ou não puder ser verificado
            throw new RuntimeException("Token JWT inválido ou expirado!", exception);
        }
    }

    // Metodo privado para calcular a data de expiração do token (2 horas a partir de agora)
    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")); // Define expiração para 2h no fuso horário de SP
    }
}