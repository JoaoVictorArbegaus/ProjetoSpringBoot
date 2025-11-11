package com.vithx.cadastro_usuario.busines;

import com.vithx.cadastro_usuario.infrastructure.entitys.Usuario;
import com.vithx.cadastro_usuario.infrastructure.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public void salvarUsuario(Usuario usuario) {
        repository.saveAndFlush(usuario); //fecha a conexao
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        return repository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("Email não encontrado!")
        );
    }

    public void deletarUsuarioPorEmail(String email) {
        repository.deleteByEmail(email);
    }

    public void atualizarUsuarioPorId(Integer id, Usuario usuario){
        Usuario usuarioSalvo = repository.findById(id).orElseThrow(() ->
                new RuntimeException("Id nao encontrado"));

        Usuario usuarioAtualizado = Usuario.builder()
                .email(usuario.getEmail() != null ? usuario.getEmail() : usuarioSalvo.getEmail())//se o objeto recebido contem nome/email ele atualiza
                .nome(usuario.getNome() != null ? usuario.getNome() : usuarioSalvo.getNome())    //se nao ele pega do ja existente
                .id(usuarioSalvo.getId())
                .build();
    }

}
