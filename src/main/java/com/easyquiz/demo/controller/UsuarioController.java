package com.easyquiz.demo.controller;

import com.easyquiz.demo.model.Usuario;
import com.easyquiz.demo.repository.UsuarioRepository;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    
    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping()
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    /**
     * Cadastra um novo usuário
     * Valida se já não existe outro usuário com o mesmo email ou telefone
     */
    @PostMapping
    public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario user) {
        if(usuarioRepository.existsByEmail(user.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email já cadastrado");
        }
        else if(usuarioRepository.existsByTelefone(user.getTelefone())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Telefone já cadastrado");
        }
        usuarioRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    /**
     * Atualiza um usuário existente por ID
     * Valida se já não existe outro usuário com o mesmo email ou telefone
     */
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarById(@PathVariable Long id, @RequestBody Usuario user) {
        return usuarioRepository.findById(id)
                .map(usuarioExistente -> {
                    usuarioExistente.setNome(user.getNome());
                    usuarioExistente.setEmail(user.getEmail());
                    usuarioExistente.setTelefone(user.getTelefone());
                    usuarioRepository.save(usuarioExistente);
                    return ResponseEntity.ok(usuarioExistente);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
    