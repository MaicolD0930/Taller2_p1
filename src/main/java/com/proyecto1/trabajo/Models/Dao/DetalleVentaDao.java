package com.proyecto1.trabajo.Models.Dao;

import org.springframework.data.repository.CrudRepository;
import com.proyecto1.trabajo.Models.Entity.DetalleVenta;

public interface DetalleVentaDao extends CrudRepository<DetalleVenta, Long> {
}
