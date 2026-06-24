package com.example.examplefeature.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.examplefeature.model.Tercero;

@Repository
public interface TerceroRepository extends JpaRepository<Tercero, Long>, JpaSpecificationExecutor<Tercero> {
    Slice<Tercero> findAllBy(Pageable pageable);
}
