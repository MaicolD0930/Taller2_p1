package com.proyecto1.trabajo.Controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.proyecto1.trabajo.Models.Dao.VentaDao;
import com.proyecto1.trabajo.Models.Dao.UsuarioDao;
import com.proyecto1.trabajo.Models.Dao.ProductoDao;
import com.proyecto1.trabajo.Models.Entity.DetalleVenta;
import com.proyecto1.trabajo.Models.Entity.Venta;
import com.proyecto1.trabajo.Models.Entity.Producto;

@Controller
@RequestMapping("/v")
public class VentaController {

    @Autowired
    private VentaDao ventaDao;

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private ProductoDao productoDao;

    // Listado
    @GetMapping("/listado")
    public String listar(Model model) {
        model.addAttribute("ventas", ventaDao.findAll());
        return "ListarVenta";
    }

    // Nueva venta
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("venta", new Venta());
        model.addAttribute("usuarios", usuarioDao.findAll());  
        model.addAttribute("productos", productoDao.findAll()); 
        return "FormVenta";
    }

    // Guardar venta con múltiples productos
    @PostMapping("/guardar")
    public String guardarVenta(@RequestParam("usuarioId") Long usuarioId,
                               @RequestParam("productoId") List<Long> productoIds,
                               @RequestParam("cantidad") List<Integer> cantidades,
                               Model model) {

        var usuario = usuarioDao.findById(usuarioId).orElse(null);
        if (usuario == null) {
            model.addAttribute("error", "Usuario no encontrado");
            model.addAttribute("usuarios", usuarioDao.findAll());
            model.addAttribute("productos", productoDao.findAll());
            return "FormVenta";
        }

        Venta venta = new Venta();
        venta.setUsuario(usuario);
        venta.setFecha(java.time.LocalDate.now());
        venta.setHora(java.time.LocalTime.now());

        List<DetalleVenta> detalles = new ArrayList<>();
        BigDecimal totalVenta = BigDecimal.ZERO;
        BigDecimal totalDescuento = BigDecimal.ZERO;

        
        for (int i = 0; i < productoIds.size(); i++) {
            Long idProd = productoIds.get(i);
            Integer cantidad = cantidades.get(i);

            Producto producto = productoDao.findById(idProd).orElse(null);
            if (producto == null) continue;

            // Validación stock
            if (producto.getStock() < cantidad) {
                model.addAttribute("error", "La cantidad solicitada de " + producto.getNombre() +
                        " supera el stock disponible (" + producto.getStock() + ")");
                model.addAttribute("usuarios", usuarioDao.findAll());
                model.addAttribute("productos", productoDao.findAll());
                return "FormVenta";
            }

            BigDecimal precioUnitario = producto.getVlrUnitario();
            BigDecimal subtotal = precioUnitario.multiply(BigDecimal.valueOf(cantidad));

            // Descuento según cantidad
            BigDecimal descuento = calcularDescuento(cantidad, subtotal);
            BigDecimal total = subtotal.subtract(descuento);

            // Crear detalle
            DetalleVenta detalle = new DetalleVenta();
            detalle.setVenta(venta);
            detalle.setProducto(producto);
            detalle.setCantidad(cantidad);
            detalle.setSubtotal(subtotal);
            detalle.setDescuento(descuento);
            detalle.setTotal(total);

            detalles.add(detalle);

            // Acumular totales de venta
            totalVenta = totalVenta.add(subtotal);
            totalDescuento = totalDescuento.add(descuento);

            // Restar stock
            producto.setStock(producto.getStock() - cantidad);
            productoDao.save(producto);
        }

        venta.setDetalles(detalles);
        venta.setSubtotal(totalVenta);
        venta.setDescuento(totalDescuento);
        venta.setTotal(totalVenta.subtract(totalDescuento));

        ventaDao.save(venta);

        return "redirect:/v/listado";
    }

    // Descuentos
    private BigDecimal calcularDescuento(Integer cantidad, BigDecimal subtotal) {
        double porcentaje = 0.0;
        if (cantidad >= 10 && cantidad <= 20) porcentaje = 0.01;
        else if (cantidad >= 21 && cantidad <= 30) porcentaje = 0.03;
        else if (cantidad >= 31 && cantidad <= 40) porcentaje = 0.04;
        else if (cantidad >= 41 && cantidad <= 50) porcentaje = 0.05;
        else if (cantidad > 50) porcentaje = 0.10;

        return subtotal.multiply(BigDecimal.valueOf(porcentaje));
    }

    @GetMapping("/factura/{id}")
    public String factura(@PathVariable Long id, Model model) {
        var venta = ventaDao.findById(id).orElse(null);
        if (venta == null) {
            return "redirect:/v/listado";
        }
        model.addAttribute("venta", venta);
        return "FacturaVenta";
    }


    // Eliminar
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        var venta = ventaDao.findById(id).orElse(null);
        if (venta != null) {
            for (DetalleVenta detalle : venta.getDetalles()) {
                var producto = detalle.getProducto();
                producto.setStock(producto.getStock() + detalle.getCantidad());
                productoDao.save(producto);
            }
            ventaDao.delete(venta);
        }
        return "redirect:/v/listado";
    }

}
