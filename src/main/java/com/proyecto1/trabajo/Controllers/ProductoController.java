package com.proyecto1.trabajo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import com.proyecto1.trabajo.Models.Dao.ProductoDao;
import com.proyecto1.trabajo.Models.Entity.Producto;

@Controller
@RequestMapping("/p")
public class ProductoController {

    @Autowired
    private ProductoDao productoDao;

    // Listado
    @GetMapping("/listado")
    public String listar(Model model) {
        model.addAttribute("productos", productoDao.findAll());
        return "ListarProducto";
    }

    // Mostrar formulario para crear
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("producto", new Producto());
        return "FormProducto";
    }

    // Guardar (crear o actualizar)
    @PostMapping("/guardar")
    public String guardarProducto(@Valid @ModelAttribute("producto") Producto producto, BindingResult result) {
        if (result.hasErrors()) {
            return "FormProducto";
        }
        productoDao.save(producto);
        return "redirect:/p/listado";
    }

    // Editar
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Producto producto = productoDao.findById(id).orElse(null);
        if (producto == null) {
            return "redirect:/p/listado";
        }
        model.addAttribute("producto", producto);
        return "FormProducto";
    }

    // Eliminar
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        productoDao.deleteById(id);
        return "redirect:/p/listado";
    }
}
