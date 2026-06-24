package com.example.examplefeature.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.examplefeature.model.Facultad;

@Repository
public interface FacultadRepository extends JpaRepository<Facultad, Long>, JpaSpecificationExecutor<Facultad> {
    Slice<Facultad> findAllBy(Pageable pageable);
}
