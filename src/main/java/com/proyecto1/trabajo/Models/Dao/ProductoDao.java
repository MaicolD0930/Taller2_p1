package com.proyecto1.trabajo.Models.Dao;

import org.springframework.data.repository.CrudRepository;
import com.proyecto1.trabajo.Models.Entity.Producto;

public interface ProductoDao extends CrudRepository<Producto, Long> { }

