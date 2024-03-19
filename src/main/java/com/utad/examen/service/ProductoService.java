package com.utad.examen.service;

import com.utad.examen.model.Producto;
import com.utad.examen.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    @Autowired
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    // 1- Guardar un producto con todos sus datos
    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    // 2- Actualizar producto por ID (uso la funcion para recuperar el producto por ID)
    public Optional<Producto> getProducto(Long id) {
        return productoRepository.findById(id);
    }

    // 3- Eliminar un producto pasando el ID
    public void delete(Long id) {
        productoRepository.deleteById(id);
    }


    // 4- Listar todos los productos
    public List<Producto> getProductos() {
        return productoRepository.findAll();
    }


    // 6- Filtrar buscando por nombre
    public List<Producto> searchByName(String name) {
        return productoRepository.findByNameContainingIgnoreCase(name);
    }


}
