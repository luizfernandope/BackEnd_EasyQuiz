package com.easyquiz.demo.repository;
import com.easyquiz.demo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByEmail(String email);

    boolean existsByTelefone(String telefone);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByTelefoneAndIdNot(String telefone, Long id);

    Optional<Usuario> findByEmail(String email);
}
