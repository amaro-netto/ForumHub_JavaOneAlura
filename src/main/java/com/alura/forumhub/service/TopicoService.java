package com.alura.forumhub.service; // Pacote onde o serviço está localizado

import com.alura.forumhub.dto.TopicoCadastroDTO; // DTO para cadastro de tópicos
import com.alura.forumhub.dto.TopicoDetalheDTO; // DTO para detalhes de tópicos
import com.alura.forumhub.dto.TopicoAtualizacaoDTO; // DTO para atualização de tópicos
import com.alura.forumhub.model.Curso; // Entidade Curso
import com.alura.forumhub.model.Topico; // Entidade Topico
import com.alura.forumhub.model.Usuario; // Entidade Usuario
import com.alura.forumhub.repository.CursoRepository; // Repositório de Curso
import com.alura.forumhub.repository.TopicoRepository; // Repositório de Topico
import com.alura.forumhub.repository.UsuarioRepository; // Repositório de Usuario
import jakarta.persistence.EntityNotFoundException; // Exceção para entidade não encontrada
import org.springframework.beans.factory.annotation.Autowired; // Anotação para injeção de dependência
import org.springframework.data.domain.Page; // Para paginação de resultados
import org.springframework.data.domain.Pageable; // Para parâmetros de paginação
import org.springframework.stereotype.Service; // Anotação para indicar que é um serviço
import org.springframework.transaction.annotation.Transactional; // Anotação para controle de transações

import java.util.Optional; // Para lidar com retornos que podem ser nulos

@Service // Anota o componente como um serviço do Spring
public class TopicoService {

    @Autowired // Injeta uma instância de TopicoRepository
    private TopicoRepository topicoRepository;

    @Autowired // Injeta uma instância de UsuarioRepository
    private UsuarioRepository usuarioRepository;

    @Autowired // Injeta uma instância de CursoRepository
    private CursoRepository cursoRepository;

    @Transactional // Garante que o método será executado dentro de uma transação de banco de dados
    public TopicoDetalheDTO cadastrarTopico(TopicoCadastroDTO dados) {
        // 1. Validação de unicidade do tópico
        if (topicoRepository.existsByTituloAndMensagem(dados.titulo(), dados.mensagem())) {
            throw new RuntimeException("Já existe um tópico com este título e mensagem.");
        }

        // 2. Busca e validação do autor
        Usuario autor = usuarioRepository.findById(dados.idAutor())
                .orElseThrow(() -> new EntityNotFoundException("Autor não encontrado com o ID: " + dados.idAutor()));

        // 3. Busca e validação do curso
        // Busca por nome, se não existir, cria um novo curso.
        Curso curso = cursoRepository.findByNome(dados.nomeCurso());
        if (curso == null) {
            curso = new Curso(dados.nomeCurso(), "Geral"); // Categoria padrão se o curso for novo
            curso = cursoRepository.save(curso); // Salva o novo curso no banco de dados
        }

        // 4. Criação e persistência do tópico
        Topico topico = new Topico(dados.titulo(), dados.mensagem(), autor, curso);
        topico = topicoRepository.save(topico); // Salva o tópico no banco de dados

        // 5. Retorna o DTO de detalhes do tópico criado
        return new TopicoDetalheDTO(topico);
    }

    // Método para listar todos os tópicos com paginação
    // O Pageable será injetado pelo Spring no Controller
    public Page<TopicoDetalheDTO> listarTodosTopicos(Pageable paginacao) {
        // Busca todos os tópicos paginados e mapeia cada entidade Topico para um TopicoDetalheDTO
        return topicoRepository.findAll(paginacao).map(TopicoDetalheDTO::new);
    }


    // Método para buscar um tópico por ID
    public TopicoDetalheDTO buscarTopicoPorId(Long id) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tópico não encontrado com o ID: " + id));
        return new TopicoDetalheDTO(topico);
    }

    @Transactional // Garante que o método será executado dentro de uma transação de banco de dados
    public TopicoDetalheDTO atualizarTopico(TopicoAtualizacaoDTO dados) {
        // 1. Busca e validação do tópico existente
        Topico topico = topicoRepository.findById(dados.id())
                .orElseThrow(() -> new EntityNotFoundException("Tópico não encontrado com o ID: " + dados.id()));

        // 2. Validação de unicidade para atualização (exclui o próprio tópico)
        // Só valida se título E mensagem forem fornecidos na atualização
        if (dados.titulo() != null && dados.mensagem() != null &&
                topicoRepository.existsByTituloAndMensagemAndIdNot(dados.titulo(), dados.mensagem(), dados.id())) {
            throw new RuntimeException("Já existe outro tópico com este título e mensagem.");
        }

        // 3. Busca e atualização do curso, se fornecido
        Curso cursoParaAtualizar = null;
        if (dados.nomeCurso() != null) {
            cursoParaAtualizar = cursoRepository.findByNome(dados.nomeCurso());
            if (cursoParaAtualizar == null) {
                // Se o novo curso não existir, cria um novo
                cursoParaAtualizar = new Curso(dados.nomeCurso(), "Geral");
                cursoParaAtualizar = cursoRepository.save(cursoParaAtualizar);
            }
        }

        // 4. Atualiza as informações do tópico (titulo, mensagem, curso)
        topico.atualizarInformacoes(dados.titulo(), dados.mensagem(), cursoParaAtualizar);

        // 5. O Spring Data JPA salva (atualiza) o tópico automaticamente ao final da transação
        // se o objeto estiver gerenciado. O .save() explícito pode ser mantido para clareza.
        topico = topicoRepository.save(topico);

        // 6. Retorna o DTO de detalhes do tópico atualizado
        return new TopicoDetalheDTO(topico);
    }

    @Transactional // Garante que o método será executado dentro de uma transação de banco de dados
    public void deletarTopico(Long id) {
        // 1. Busca e validação do tópico existente
        if (!topicoRepository.existsById(id)) {
            throw new EntityNotFoundException("Tópico não encontrado com o ID: " + id);
        }
        // 2. Deleta o tópico
        topicoRepository.deleteById(id);
    }
}