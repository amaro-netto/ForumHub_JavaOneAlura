package com.alura.forumhub.dto; // Pacote onde o DTO está localizado

import com.alura.forumhub.model.Topico; // Importa a entidade Topico
import com.alura.forumhub.model.StatusTopico; // Importa o enum StatusTopico
import java.time.LocalDateTime; // Importa para trabalhar com data e hora

// DTO para retornar informações resumidas de um tópico em listagens
public record TopicoListagemDTO( // Usando um record para concisão e imutabilidade
                                 Long id, // ID do tópico
                                 String titulo, // Título do tópico
                                 String mensagem, // Mensagem/conteúdo do tópico (pode ser truncada em listagens)
                                 LocalDateTime dataCriacao, // Data e hora de criação do tópico
                                 StatusTopico status, // Status atual do tópico
                                 String autorNome, // Nome do autor do tópico
                                 String cursoNome // Nome do curso ao qual o tópico pertence
) {
    // Construtor que recebe um objeto Topico e extrai as informações para o DTO de listagem
    public TopicoListagemDTO(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getStatus(),
                topico.getAutor().getNome(), // Pega o nome do autor
                topico.getCurso().getNome() // Pega o nome do curso
        );
    }
}