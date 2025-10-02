package com.proyecto1.trabajo.Models.Dao;

import org.springframework.data.repository.CrudRepository;
import com.proyecto1.trabajo.Models.Entity.Usuario;

public interface UsuarioDao extends CrudRepository<Usuario, Long> {
}
