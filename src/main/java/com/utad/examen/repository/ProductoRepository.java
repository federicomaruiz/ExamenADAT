package com.utad.examen.repository;

import com.utad.examen.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto,Long> {

    // 6- Filtrar por nombre
    List<Producto> findByNameContainingIgnoreCase(String name);

}
