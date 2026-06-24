package com.example.examplefeature.services;
import com.example.examplefeature.model.Factura;
import com.example.examplefeature.repositories.FacturaRepository;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import com.vaadin.hilla.crud.CrudRepositoryService;

@BrowserCallable
@AnonymousAllowed
public class FacturaService extends CrudRepositoryService<Factura, Long, FacturaRepository> {

}
