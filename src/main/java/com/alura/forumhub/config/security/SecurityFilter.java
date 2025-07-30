package com.alura.forumhub.config.security; // Pacote de configuração de segurança

import com.alura.forumhub.repository.UsuarioRepository; // Importa o repositório de usuários
import jakarta.servlet.FilterChain; // Interface para a cadeia de filtros
import jakarta.servlet.ServletException; // Exceção de servlet
import jakarta.servlet.http.HttpServletRequest; // Objeto de requisição HTTP
import jakarta.servlet.http.HttpServletResponse; // Objeto de resposta HTTP
import org.springframework.beans.factory.annotation.Autowired; // Injeção de dependência
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; // Objeto para autenticação
import org.springframework.security.core.context.SecurityContextHolder; // Contexto de segurança do Spring
import org.springframework.stereotype.Component; // Anotação para indicar que é um componente do Spring
import org.springframework.web.filter.OncePerRequestFilter; // Garante que o filtro seja executado uma vez por requisição

import java.io.IOException; // Exceção de I/O

@Component // Anotação para que o Spring gerencie este componente
public class SecurityFilter extends OncePerRequestFilter { // Estende OncePerRequestFilter para garantir uma única execução por requisição

    @Autowired // Injeta o TokenService para validar o token
    private TokenService tokenService;

    @Autowired // Injeta o UsuarioRepository para carregar o usuário autenticado
    private UsuarioRepository usuarioRepository;

    // Metodo principal do filtro, executado para cada requisição
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Obtém o token JWT do cabeçalho da requisição
        var tokenJWT = recuperarToken(request);

        // Se um token JWT for encontrado, valida-o e autentica o usuário
        if (tokenJWT != null) {
            var subject = tokenService.getSubject(tokenJWT); // Valida o token e recupera o email (subject)
            var usuario = usuarioRepository.findByEmail(subject); // Busca o usuário pelo email

            // Cria um objeto de autenticação para o Spring Security
            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

            // Define o usuário como autenticado no contexto do Spring Security
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Continua a cadeia de filtros (passa a requisição para o próximo filtro ou para o dispatcher servlet)
        filterChain.doFilter(request, response);
    }

    // Metodo auxiliar para extrair o token do cabeçalho Authorization
    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization"); // Obtém o cabeçalho "Authorization"
        if (authorizationHeader != null) {
            // Verifica se o cabeçalho começa com "Bearer " e retorna apenas o token
            return authorizationHeader.replace("Bearer ", "");
        }
        return null; // Retorna nulo se o cabeçalho não contiver um token
    }
}