package com.example.examplefeature.services;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import com.vaadin.hilla.crud.CrudRepositoryService;
import com.example.examplefeature.model.Tercero;
import com.example.examplefeature.repositories.TerceroRepository;

@BrowserCallable
@AnonymousAllowed
public class TerceroService extends CrudRepositoryService<Tercero, Long, TerceroRepository> {

}
