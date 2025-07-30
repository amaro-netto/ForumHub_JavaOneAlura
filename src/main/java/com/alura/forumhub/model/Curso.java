package com.alura.forumhub.model; // Pacote onde a classe Curso está localizada

import jakarta.persistence.*; // Anotações JPA para persistência
import lombok.AllArgsConstructor; // Lombok para construtor com todos os argumentos
import lombok.EqualsAndHashCode; // Lombok para equals e hashCode
import lombok.Getter; // Lombok para getters
import lombok.NoArgsConstructor; // Lombok para construtor sem argumentos

@Table(name = "cursos") // Define o nome da tabela no banco de dados
@Entity(name = "Curso") // Declara que esta classe é uma entidade JPA
@Getter // Gera métodos getters para todos os campos
@NoArgsConstructor // Gera construtor sem argumentos (exigido pelo JPA)
@AllArgsConstructor // Gera construtor com todos os argumentos
@EqualsAndHashCode(of = "id") // Gera equals e hashCode baseados no campo 'id'
public class Curso {

    @Id // Marca o campo 'id' como chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Estratégia de geração de valor para a chave primária
    private Long id; // Identificador único do curso

    private String nome; // Nome do curso (ex: "Java e Spring Boot")

    private String categoria; // Categoria do curso (ex: "Programação", "Front-end")

    // Construtor para facilitar a criação de novos objetos Curso (sem o ID, que é gerado automaticamente)
    public Curso(String nome, String categoria) {
        this.nome = nome;
        this.categoria = categoria;
    }
}