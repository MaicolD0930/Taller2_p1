package com.proyecto1.trabajo.Models.Servicios;

import java.util.List;
import com.proyecto1.trabajo.Models.Entity.Usuario;

public interface UsuarioService {
    List<Usuario> findAll();
    Usuario findById(Long id);
    Usuario save(Usuario usuario);
    void delete(Long id);
}
