package com.utad.examen.controller;

import com.utad.examen.model.Producto;
import com.utad.examen.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/v1/productos")
public class ProductoController {

    private final ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }


    // 1 - Crear un producto con todos sus campos
    @PostMapping
    public ResponseEntity<Producto> save(@RequestBody Producto producto) {
        Producto newProducto = productoService.save(producto);
        return ResponseEntity.ok(newProducto);
    }

    // 2- Actualizar producto mediante su id
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto productoActualizado) {
        Optional<Producto> productoOptional = productoService.getProducto(id);

        if (productoOptional.isPresent()) {
            Producto producto = productoOptional.get();

            // Actualizar campos si se proporcionan en el cuerpo de la solicitud
            if (productoActualizado.getName() != null) {
                producto.setName(productoActualizado.getName());
            }
            if (productoActualizado.getDescription() != null) {
                producto.setDescription(productoActualizado.getDescription());
            }
            if (productoActualizado.getPrice() != null) {
                producto.setPrice(productoActualizado.getPrice());
            }
            if (productoActualizado.getCategory() != null) {
                producto.setCategory(productoActualizado.getCategory());
            }
            if (productoActualizado.getStock() != null) {
                producto.setStock(productoActualizado.getStock());
            }
            // Guardar la serie actualizada en la base de datos
            Producto productoActualizadoEnBD = productoService.save(producto);
            return ResponseEntity.ok(productoActualizadoEnBD);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 3- Eliminar un producto pasando el ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Optional<Producto>> delete(@PathVariable Long id) {
        Optional<Producto> productoOptional = productoService.getProducto(id);
        if (productoOptional.isPresent()) {
            productoService.delete(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    // 4- Listar todos los productos
    @GetMapping
    public ResponseEntity<List<Producto>> getAll() {
        List<Producto> listProductos = productoService.getProductos();
        return ResponseEntity.ok(listProductos);
    }

    // 5- Filtrar por los productos que hay en stock
    @GetMapping("/stock")
    public ResponseEntity<List<Producto>> getProductoWithStock() {
        List<Producto> listProductos = productoService.getProductos();

        // Filtrar la lista para quedarse solo con los productos que tienen stock > 0
        List<Producto> productosConStock = listProductos.stream()
                .filter(producto -> producto.getStock() > 0)
                .collect(Collectors.toList());

        return ResponseEntity.ok(productosConStock);
    }

    // 6- Filtrar producto por nombre

    @GetMapping("/search")
    public ResponseEntity<List<Producto>> searchProductos(@RequestParam String name) {
        List<Producto> productos = productoService.searchByName(name);
        return ResponseEntity.ok(productos);
    }


}
