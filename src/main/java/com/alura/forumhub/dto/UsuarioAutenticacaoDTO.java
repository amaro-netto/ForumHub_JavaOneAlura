package com.alura.forumhub.dto; // Pacote onde o DTO está localizado

import jakarta.validation.constraints.Email; // Anotação para validar formato de e-mail
import jakarta.validation.constraints.NotBlank; // Anotação para validar que o campo não é nulo nem vazio

// DTO para receber as credenciais de autenticação do usuário (login)
public record UsuarioAutenticacaoDTO( // Usando um record para concisão e imutabilidade
                                      @NotBlank(message = "O e-mail não pode estar em branco") // Valida que o e-mail não é nulo, vazio ou só espaços
                                      @Email(message = "Formato de e-mail inválido") // Valida que o e-mail tem um formato válido
                                      String email,

                                      @NotBlank(message = "A senha não pode estar em branco") // Valida que a senha não é nulo, vazio ou só espaços
                                      String senha
) {
}