package com.proyecto1.trabajo.Models.Servicios;

import com.proyecto1.trabajo.Models.Dao.VentaDao;
import com.proyecto1.trabajo.Models.Entity.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class VentaServiceImpl implements VentaService {

    @Autowired
    private VentaDao ventaDao;

    @Autowired
    private ProductoService productoService;

    @Override
    @Transactional
    public Venta save(Venta venta) {

        if (venta.getFecha() == null) {
            venta.setFecha(LocalDate.now());
        }
        if (venta.getHora() == null) {
            venta.setHora(LocalTime.now());
        }

        // Inicializamos acumuladores en BigDecimal
        BigDecimal subtotalVenta = BigDecimal.ZERO;
        BigDecimal descuentoVenta = BigDecimal.ZERO;
        BigDecimal totalVenta = BigDecimal.ZERO;

        // Recorremos cada detalle
        for (DetalleVenta detalle : venta.getDetalles()) {

            Producto producto = detalle.getProducto();
            int cantidad = detalle.getCantidad();

            // Validar stock
            if (producto.getStock() < cantidad) {
                throw new RuntimeException("No hay suficiente stock para el producto: " + producto.getNombre());
            }

            // Calcular subtotal = precio unitario * cantidad
            BigDecimal subtotal = producto.getVlrUnitario().multiply(BigDecimal.valueOf(cantidad));

            // Calcular descuento según cantidad
            BigDecimal descuento = BigDecimal.ZERO;
            if (cantidad >= 10 && cantidad < 20) {
                descuento = subtotal.multiply(new BigDecimal("0.10")); // 10%
            } else if (cantidad >= 20) {
                descuento = subtotal.multiply(new BigDecimal("0.20")); // 20%
            }

            // Calcular total del detalle
            BigDecimal total = subtotal.subtract(descuento);

            // Seteamos en el detalle
            detalle.setSubtotal(subtotal);
            detalle.setDescuento(descuento);
            detalle.setTotal(total);
            detalle.setVenta(venta);

            // Restar stock
            producto.setStock(producto.getStock() - cantidad);
            productoService.save(producto);

            // Acumular a la venta
            subtotalVenta = subtotalVenta.add(subtotal);
            descuentoVenta = descuentoVenta.add(descuento);
            totalVenta = totalVenta.add(total);
        }

        // Seteamos totales en la venta
        venta.setSubtotal(subtotalVenta);
        venta.setDescuento(descuentoVenta);
        venta.setTotal(totalVenta);

        return ventaDao.save(venta);
    }

    @Override
    @Transactional
    public void delete(Long idVenta) {
        ventaDao.deleteById(idVenta);
    }

    @Override
    public List<Venta> findAll() {
        return (List<Venta>) ventaDao.findAll();
    }


    @Override
    public Venta findById(Long idVenta) {
        return ventaDao.findById(idVenta).orElse(null);
    }
}
