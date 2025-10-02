package com.proyecto1.trabajo.Models.Dao;

import org.springframework.data.repository.CrudRepository;
import com.proyecto1.trabajo.Models.Entity.Venta;

public interface VentaDao extends CrudRepository<Venta, Long> {
}
