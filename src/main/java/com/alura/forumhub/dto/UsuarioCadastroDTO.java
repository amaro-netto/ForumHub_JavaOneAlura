package com.alura.forumhub.dto; // Pacote onde o DTO está localizado

import jakarta.validation.constraints.Email; // Anotação para validar formato de e-mail
import jakarta.validation.constraints.NotBlank; // Anotação para validar que o campo não é nulo nem vazio
import jakarta.validation.constraints.Size; // Anotação para validar o tamanho da String

// DTO para receber os dados de um novo usuário no momento do cadastro
public record UsuarioCadastroDTO( // Usando um record para concisão e imutabilidade
                                  @NotBlank(message = "O nome não pode estar em branco") // Valida que o nome não é nulo, vazio ou só espaços
                                  String nome,

                                  @NotBlank(message = "O e-mail não pode estar em branco") // Valida que o e-mail não é nulo, vazio ou só espaços
                                  @Email(message = "Formato de e-mail inválido") // Valida que o e-mail tem um formato válido
                                  String email,

                                  @NotBlank(message = "A senha não pode estar em branco") // Valida que a senha não é nula, vazia ou só espaços
                                  @Size(min = 6, max = 20, message = "A senha deve ter entre 6 e 20 caracteres") // Valida o tamanho da senha
                                  String senha
) {
}