package com.example.examplefeature.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.examplefeature.model.Pago;

public interface PagoRepository extends JpaRepository<Pago, Long> {
}

