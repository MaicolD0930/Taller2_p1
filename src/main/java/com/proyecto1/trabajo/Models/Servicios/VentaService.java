package com.proyecto1.trabajo.Models.Servicios;

import com.proyecto1.trabajo.Models.Entity.Venta;
import java.util.List;

public interface VentaService {
    List<Venta> findAll();
    Venta findById(Long idVenta);
    Venta save(Venta venta);
    void delete(Long idVenta);
}
