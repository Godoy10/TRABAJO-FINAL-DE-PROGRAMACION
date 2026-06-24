package com.example.examplefeature.services;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import com.vaadin.hilla.crud.CrudRepositoryService;
import com.example.examplefeature.model.Facultad;
import com.example.examplefeature.repositories.FacultadRepository;

@BrowserCallable
@AnonymousAllowed
public class FacultadService extends CrudRepositoryService<Facultad, Long, FacultadRepository> {

}