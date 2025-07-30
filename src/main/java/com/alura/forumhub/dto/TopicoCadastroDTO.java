package com.alura.forumhub.dto; // Pacote onde o DTO está localizado

import jakarta.validation.constraints.NotBlank; // Anotação para validar que o campo não é nulo nem vazio
import jakarta.validation.constraints.NotNull; // Anotação para validar que o campo não é nulo

// DTO para receber os dados de um novo tópico no momento do cadastro
public record TopicoCadastroDTO( // Usando um record (Java 16+) para concisão, que é imutável
                                 @NotBlank(message = "O título não pode estar em branco") // Valida que o título não é nulo, vazio ou só espaços
                                 String titulo,

                                 @NotBlank(message = "A mensagem não pode estar em branco") // Valida que a mensagem não é nula, vazia ou só espaços
                                 String mensagem,

                                 @NotNull(message = "O ID do autor não pode ser nulo") // Valida que o ID do autor não é nulo
                                 Long idAutor, // ID do autor do tópico

                                 @NotBlank(message = "O nome do curso não pode estar em branco") // Valida que o nome do curso não é nulo, vazio ou só espaços
                                 String nomeCurso // Nome do curso ao qual o tópico pertence
) {
}