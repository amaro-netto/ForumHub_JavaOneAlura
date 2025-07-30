package com.alura.forumhub.config.security; // Pacote de configuração de segurança

import com.alura.forumhub.repository.UsuarioRepository; // Importa o repositório de usuários
import org.springframework.beans.factory.annotation.Autowired; // Injeção de dependência
import org.springframework.security.core.userdetails.UserDetails; // Interface para detalhes do usuário
import org.springframework.security.core.userdetails.UserDetailsService; // Interface para carregar detalhes do usuário
import org.springframework.security.core.userdetails.UsernameNotFoundException; // Exceção para usuário não encontrado
import org.springframework.stereotype.Service; // Anotação para indicar que é um serviço

@Service // Indica ao Spring que esta classe é um serviço
public class AutenticacaoService implements UserDetailsService { // Implementa UserDetailsService do Spring Security

    @Autowired // Injeta o repositório de usuários
    private UsuarioRepository usuarioRepository;

    // Metodo principal da interface UserDetailsService, responsável por carregar o usuário pelo username (email)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca o usuário no banco de dados pelo email
        // O método findByEmail retorna UserDetails porque a entidade Usuario implementa UserDetails
        UserDetails usuario = usuarioRepository.findByEmail(username);

        // Se o usuário não for encontrado, lança uma exceção
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado com o e-mail: " + username);
        }

        // Retorna os detalhes do usuário encontrado
        return usuario;
    }
}