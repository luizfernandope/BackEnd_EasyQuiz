package com.easyquiz.demo.controller;
import com.easyquiz.demo.model.LogCadastro;
import com.easyquiz.demo.model.Usuario;
import com.easyquiz.demo.repository.UsuarioRepository;
import com.easyquiz.demo.service.EmailService;
import com.easyquiz.demo.repository.LogCadastroRepository;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;


@RestController()
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final EmailService emailService;
    private final LogCadastroRepository logCadastroRepository;

    public UsuarioController(UsuarioRepository usuarioRepository, EmailService emailService, LogCadastroRepository logCadastroRepository) {
        this.usuarioRepository = usuarioRepository;
        this.emailService = emailService;
        this.logCadastroRepository = logCadastroRepository;
    }

    @GetMapping("/listar")
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obterUsuarioPorId(@PathVariable Integer id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/cadastrar/{id}")
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario, @PathVariable Integer id) {
        // Verifica se o admin existe
        Optional<Usuario> adminOpt = usuarioRepository.findById(id);
        if (adminOpt.isEmpty()) {
            System.out.println("Admin com id " + id + " não encontrado.");
            return ResponseEntity.notFound().build();
        }
        Usuario admin = adminOpt.get();
        if(!admin.getTipo().equals("ADMIN"))    
        { 
            System.out.println("Usuário com id " + id + " não é um admin.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        //cria uma senha aleatória para o usuário, com 16 caracteres
        String senhaAleatoria = UUID.randomUUID().toString().substring(0, 16).replace("-", "");
        usuario.setSenha(senhaAleatoria);

        usuario.setCriadoEm(LocalDateTime.now());
        Usuario novoUsuario = usuarioRepository.save(usuario);
        System.out.println("Usuario cadastrado com id: " + novoUsuario.getId());

        // Envia email com a senha para o usuário
        String textoEmail = "Sua senha é: " + senhaAleatoria;
        String assuntoEmail = "Senha de Acesso - EasyQuiz";
        //envia o email para o usuário com a senha (EmailService é injetado pelo Spring)
        emailService.enviarEmailSimples(usuario.getEmail(), assuntoEmail, textoEmail);

        LogCadastro log = new LogCadastro();
        log.setAdmin(admin);
        log.setProfessor(novoUsuario);
        log.setDataHora(LocalDateTime.now());
        //o novoUsuario já tem o id gerado pelo banco de dados, temos que usar ele para o log
        System.out.println("Registrando log de cadastro: Admin ID " + admin.getId() + ", Professor ID " + novoUsuario.getId());
        logCadastroRepository.save(log);
        System.out.println("Cadastrado log de novo usuário de id: " + novoUsuario.getId() + " pelo admin id: " + admin.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Integer id, @RequestBody Usuario usuarioAtualizado) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setNome(usuarioAtualizado.getNome());
                    usuario.setEmail(usuarioAtualizado.getEmail());
                    Usuario usuarioSalvo = usuarioRepository.save(usuario);
                    return ResponseEntity.ok(usuarioSalvo);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PutMapping("/admUpdate/{id}")
    public ResponseEntity<Usuario> atualizarUsuario_Admin(@PathVariable Integer id, @RequestBody Usuario usuarioAtualizado) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setNome(usuarioAtualizado.getNome());
                    usuario.setEmail(usuarioAtualizado.getEmail());
                    usuario.setTipo(usuarioAtualizado.getTipo());
                    Usuario usuarioSalvo = usuarioRepository.save(usuario);
                    return ResponseEntity.ok(usuarioSalvo);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/mudarSenha/{id}")
    public ResponseEntity<Usuario> atualizarSenha(@PathVariable Integer id, @RequestBody String novaSenha) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setSenha(novaSenha);
                    Usuario usuarioSalvo = usuarioRepository.save(usuario);
                    return ResponseEntity.ok(usuarioSalvo);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //metodo para fazer login
    @PostMapping("/login") //post para não expor credenciais na url
    public ResponseEntity<Usuario> login(@RequestBody Map<String, String> credenciais) {
        String email = credenciais.get("email");
        String senha = credenciais.get("senha");

        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (usuario.getSenha().equals(senha)) {
                return ResponseEntity.ok(usuario);
            } else {
                System.out.println("Senha incorreta");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } else {
            System.out.println("Usuário não encontrado");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


}