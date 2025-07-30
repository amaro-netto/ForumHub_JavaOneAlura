package com.alura.forumhub.dto; // Pacote onde o DTO está localizado

import jakarta.validation.constraints.NotNull; // Anotação para validar que o campo não é nulo
import jakarta.validation.constraints.Size; // Anotação para validar o tamanho da String

// DTO para receber os dados de um tópico no momento da atualização
public record TopicoAtualizacaoDTO( // Usando um record para concisão e imutabilidade
                                    @NotNull(message = "O ID do tópico não pode ser nulo para atualização") // O ID é obrigatório para saber qual tópico atualizar
                                    Long id, // ID do tópico a ser atualizado

                                    @Size(max = 255, message = "O título deve ter no máximo 255 caracteres") // Valida o tamanho máximo do título
                                    String titulo, // Novo título do tópico (opcional na atualização)

                                    @Size(max = 1000, message = "A mensagem deve ter no máximo 1000 caracteres") // Valida o tamanho máximo da mensagem
                                    String mensagem, // Nova mensagem do tópico (opcional na atualização)

                                    String nomeCurso // Novo nome do curso (opcional na atualização) - pode ser usado para buscar e associar um novo curso
) {
}