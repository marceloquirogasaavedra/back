package com.examen02.examen02.service;

import com.examen02.examen02.DTO.SucursalDTO;
import com.examen02.examen02.model.Sucursal;
import com.examen02.examen02.repository.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SucursalService {

    @Autowired
    private SucursalRepository sucursalRepository;

    // Listar todas las sucursales
    public ResponseEntity<List<Sucursal>> findAll() {
        List<Sucursal> sucursales = sucursalRepository.findAll();
        return new ResponseEntity<>(sucursales, HttpStatus.OK);
    }

    // Guardar una nueva sucursal
    public ResponseEntity<Sucursal> save(SucursalDTO sucursalDTO) {
        Sucursal sucursal = new Sucursal();
        sucursal.setNombre(sucursalDTO.getNombre());
        sucursal.setDireccion(sucursalDTO.getDireccion());

        Sucursal savedSucursal = sucursalRepository.save(sucursal);
        return new ResponseEntity<>(savedSucursal, HttpStatus.CREATED);
    }

    // Modificar una sucursal existente
    public ResponseEntity<Sucursal> update(Long id, SucursalDTO sucursalDTO) {
        Optional<Sucursal> optionalSucursal = sucursalRepository.findById(id);

        if (optionalSucursal.isPresent()) {
            Sucursal sucursal = optionalSucursal.get();
            sucursal.setNombre(sucursalDTO.getNombre());
            sucursal.setDireccion(sucursalDTO.getDireccion());

            Sucursal updatedSucursal = sucursalRepository.save(sucursal);
            return new ResponseEntity<>(updatedSucursal, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar una sucursal
    public ResponseEntity<Void> delete(SucursalDTO sucursalDTO) {
        Optional<Sucursal> optionalSucursal = sucursalRepository.findByNombre(sucursalDTO.getNombre());
        if (optionalSucursal.isPresent()) {
            sucursalRepository.deleteById(optionalSucursal.get().getId());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}