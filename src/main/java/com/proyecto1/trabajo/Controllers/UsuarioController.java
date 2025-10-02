package com.proyecto1.trabajo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import com.proyecto1.trabajo.Models.Dao.UsuarioDao;
import com.proyecto1.trabajo.Models.Entity.Usuario;

@Controller
@RequestMapping("/u")
public class UsuarioController {

    @Autowired
    private UsuarioDao usuarioDao;

    // Listado
    @GetMapping("/listado")
    public String listar(Model model) {
        model.addAttribute("usuarios", usuarioDao.findAll());
        return "ListarUsuario";
    }

    // Mostrar formulario para crear
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "FormUsuario";
    }

    // Guardar (crear o actualizar)
    @PostMapping("/guardar")
    public String guardarUsuario(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "FormUsuario";
        }
        usuarioDao.save(usuario);
        return "redirect:/u/listado";
    }

    // Mostrar formulario para editar
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioDao.findById(id).orElse(null);
        if (usuario == null) {
            return "redirect:/u/listado";
        }
        model.addAttribute("usuario", usuario);
        return "FormUsuario";
    }

    // Eliminar
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        usuarioDao.deleteById(id);
        return "redirect:/u/listado";
    }
}
