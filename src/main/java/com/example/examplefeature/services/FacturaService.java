package com.example.examplefeature.services;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.examplefeature.model.Factura;
import com.example.examplefeature.model.Facultad;
import com.example.examplefeature.repositories.FacturaRepository;
import com.example.examplefeature.repositories.FacultadRepository;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import com.vaadin.hilla.crud.CrudRepositoryService;

@BrowserCallable
@AnonymousAllowed
public class FacturaService extends CrudRepositoryService<Factura, Long, FacturaRepository> {

	  @Autowired
	    private FacultadRepository facultadRepository;

	    public List<Facultad> findAll() {
	        return facultadRepository.findAll();
	    }
}
