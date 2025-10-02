package com.proyecto1.trabajo.Models.Servicios;

import java.util.List;
import com.proyecto1.trabajo.Models.Entity.Producto;

public interface ProductoService {
    List<Producto> findAll();
    Producto findById(Long id);
    Producto save(Producto producto);
    void delete(Long id);
}
