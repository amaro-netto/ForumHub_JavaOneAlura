package com.alura.forumhub.model; // Pacote onde a classe Topico está localizada

import jakarta.persistence.*; // Importa anotações JPA para persistência
import lombok.*; // Importa anotações do Lombok para gerar código automaticamente
import java.time.LocalDateTime; // Para trabalhar com data e hora

@Table(name = "topicos") // Define o nome da tabela no banco de dados
@Entity(name = "Topico") // Declara que esta classe é uma entidade JPA
@Getter // Anotação do Lombok para gerar automaticamente os métodos getters para todos os campos
@NoArgsConstructor // Anotação do Lombok para gerar um construtor sem argumentos
@AllArgsConstructor // Anotação do Lombok para gerar um construtor com todos os argumentos
@EqualsAndHashCode(of = "id") // Anotação do Lombok para gerar equals e hashCode baseados no campo 'id'
public class Topico {

    @Id // Anotação que indica que este campo é a chave primária da entidade
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Estratégia de geração de valor para a chave primária (auto-incremento)
    private Long id; // Identificador único do tópico

    private String titulo; // Título do tópico

    private String mensagem; // Conteúdo da mensagem do tópico

    private LocalDateTime dataCriacao; // Data e hora de criação do tópico

    @Enumerated(EnumType.STRING) // Indica que a enumeração StatusTopico será mapeada como String no DB
    private StatusTopico status; // Status do tópico (ativo, resolvido, etc.)

    @ManyToOne(fetch = FetchType.LAZY) // Relacionamento muitos-para-um com Usuario (um usuário pode ter muitos tópicos)
    @JoinColumn(name = "autor_id") // Coluna na tabela 'topicos' que faz referência ao ID do autor
    private Usuario autor; // Usuário que criou o tópico

    @ManyToOne(fetch = FetchType.LAZY) // Relacionamento muitos-para-um com Curso (um curso pode ter muitos tópicos)
    @JoinColumn(name = "curso_id") // Coluna na tabela 'topicos' que faz referência ao ID do curso
    private Curso curso; // Curso ao qual o tópico pertence

    // Construtor para criar um tópico com dados iniciais, definindo a data de criação e status padrão
    public Topico(String titulo, String mensagem, Usuario autor, Curso curso) {
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.dataCriacao = LocalDateTime.now(); // Define a data de criação como o momento atual
        this.status = StatusTopico.NAO_RESPONDIDO; // Status inicial do tópico
        this.autor = autor;
        this.curso = curso;
    }

    // Metodo para atualizar as informações do tópico, agora recebendo o objeto Curso diretamente
    public void atualizarInformacoes(String titulo, String mensagem, Curso curso) {
        // Verifica e atualiza o título se um novo for fornecido
        if (titulo != null) {
            this.titulo = titulo;
        }
        // Verifica e atualiza a mensagem se uma nova for fornecida
        if (mensagem != null) {
            this.mensagem = mensagem;
        }
        // Verifica e atualiza o curso se um novo objeto Curso for fornecido
        if (curso != null) {
            this.curso = curso;
        }
    }

    // Metodo para marcar o tópico como resolvido
    public void marcarComoResolvido() {
        this.status = StatusTopico.SOLUCIONADO; // Correção: para SOLUCIONADO
    }

    // Metodo para marcar o tópico como não respondido
    public void marcarComoNaoRespondido() {
        this.status = StatusTopico.NAO_RESPONDIDO;
    }
}