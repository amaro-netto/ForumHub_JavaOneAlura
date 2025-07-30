package com.alura.forumhub.controller; // Pacote onde o controller está localizado

import com.alura.forumhub.dto.TopicoCadastroDTO; // DTO para cadastro de tópicos
import com.alura.forumhub.dto.TopicoDetalheDTO; // DTO para detalhes de tópicos
import com.alura.forumhub.dto.TopicoAtualizacaoDTO; // DTO para atualização de tópicos
import com.alura.forumhub.service.TopicoService; // Serviço de tópicos
import jakarta.validation.Valid; // Anotação para ativar validação em DTOs
import org.springframework.beans.factory.annotation.Autowired; // Injeção de dependência
import org.springframework.data.domain.Page; // Para retornar página de resultados
import org.springframework.data.domain.Pageable; // Para receber parâmetros de paginação
import org.springframework.data.web.PageableDefault; // Para definir valores padrão de paginação
import org.springframework.http.ResponseEntity; // Para construir respostas HTTP
import org.springframework.web.bind.annotation.*; // Anotações para mapeamento de requisições web
import org.springframework.web.util.UriComponentsBuilder; // Para construir URIs para respostas 201 Created

import java.net.URI; // Representa uma URI

@RestController // Anota que esta classe é um controller REST
@RequestMapping("/topicos") // Define o caminho base para todos os endpoints deste controller
public class TopicoController {

    @Autowired // Injeta uma instância de TopicoService
    private TopicoService topicoService;

    // Endpoint para cadastrar um novo tópico (POST /topicos)
    @PostMapping // Mapeia requisições HTTP POST para este método
    public ResponseEntity<TopicoDetalheDTO> cadastrar(
            @RequestBody @Valid TopicoCadastroDTO dados, // Recebe o corpo da requisição e valida (JSON para DTO)
            UriComponentsBuilder uriBuilder // Injetado pelo Spring para construir a URI de retorno
    ) {
        TopicoDetalheDTO topicoDetalhe = topicoService.cadastrarTopico(dados); // Chama o serviço para cadastrar

        // Constrói a URI do novo recurso criado para retornar no cabeçalho Location
        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topicoDetalhe.id()).toUri();

        // Retorna 201 Created com o DTO do tópico criado e a URI do recurso
        return ResponseEntity.created(uri).body(topicoDetalhe);
    }

    // Endpoint para listar todos os tópicos (GET /topicos)
    // Suporta paginação (ex: /topicos?size=10&page=0&sort=dataCriacao,desc)
    @GetMapping
    public ResponseEntity<Page<TopicoDetalheDTO>> listar(
            @PageableDefault(size = 10, sort = {"dataCriacao"}) Pageable paginacao // Define valores padrão para paginação
    ) {
        // Chama o serviço para listar os tópicos paginados e retorna uma página de DTOs
        Page<TopicoDetalheDTO> pagina = topicoService.listarTodosTopicos(paginacao);
        return ResponseEntity.ok(pagina); // Retorna 200 OK com a página de tópicos
    }

    // Endpoint para buscar um único tópico por ID (GET /topicos/{id})
    @GetMapping("/{id}") // Mapeia requisições GET com um ID na URL
    public ResponseEntity<TopicoDetalheDTO> detalhar(@PathVariable Long id) { // Extrai o ID da URL
        TopicoDetalheDTO topicoDetalhe = topicoService.buscarTopicoPorId(id); // Chama o serviço para buscar
        return ResponseEntity.ok(topicoDetalhe); // Retorna 200 OK com os detalhes do tópico
    }

    // Endpoint para atualizar um tópico existente (PUT /topicos)
    @PutMapping
    public ResponseEntity<TopicoDetalheDTO> atualizar(@RequestBody @Valid TopicoAtualizacaoDTO dados) { // Recebe DTO de atualização
        TopicoDetalheDTO topicoDetalhe = topicoService.atualizarTopico(dados); // Chama o serviço para atualizar
        return ResponseEntity.ok(topicoDetalhe); // Retorna 200 OK com os detalhes atualizados
    }

    // Endpoint para deletar um tópico (DELETE /topicos/{id})
    @DeleteMapping("/{id}") // Mapeia requisições DELETE com um ID na URL
    public ResponseEntity<Void> excluir(@PathVariable Long id) { // Extrai o ID da URL
        topicoService.deletarTopico(id); // Chama o serviço para deletar
        return ResponseEntity.noContent().build(); // Retorna 204 No Content (sucesso sem corpo de resposta)
    }
}