package com.proyecto1.trabajo.Models.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @Column(name = "id_producto")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;
    
    @NotBlank(message = "El nombre del producto es obligatorio")
    private String nombre;

    @Size(max = 255, message = "La descripción no puede superar los 255 caracteres")
    @Column(length = 255)
    private String descripcion;

    @NotNull(message = "El valor unitario es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El valor debe ser mayor que 0")
    @Digits(integer = 8, fraction = 2, message = "El valor debe tener máximo 8 enteros y 2 decimales")
    @Column(precision = 10, scale = 2)
    private BigDecimal vlrUnitario;

    @Min(value = 0, message = "El stock no puede ser negativo")
    private int stock;

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getVlrUnitario() {
        return vlrUnitario;
    }

    public void setVlrUnitario(BigDecimal vlrUnitario) {
        this.vlrUnitario = vlrUnitario;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

}

