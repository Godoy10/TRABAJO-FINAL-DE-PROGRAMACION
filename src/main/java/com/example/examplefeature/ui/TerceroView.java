package com.example.examplefeature.ui;

import com.example.examplefeature.model.Tercero;
import com.example.examplefeature.repositories.TerceroRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

@Route("tercero")
@AnonymousAllowed
public class TerceroView extends VerticalLayout {

    private final TerceroRepository repository;
    private Grid<Tercero> grid = new Grid<>(Tercero.class, false);
    
   
    private TextField nombre = new TextField("Nombre");
    private TextField cuit = new TextField("CUIT");
    private TextField direccion = new TextField("Dirección");

    private Binder<Tercero> binder = new Binder<>(Tercero.class);
    private Tercero terceroActual;

    @Autowired
    public TerceroView(TerceroRepository repository) {
        this.repository = repository;
        add(new H2("Gestión de Terceros"));

        configurarGrid();
        
        HorizontalLayout layoutPrincipal = new HorizontalLayout(grid, crearFormulario());
        layoutPrincipal.setSizeFull();
        grid.setWidth("60%");
        
        add(layoutPrincipal);
        
        binder.bindInstanceFields(this);
        actualizarGrid();
    }

    private void configurarGrid() {
    		grid.addColumn("nombre").setHeader("Nombre");
   		grid.addColumn("cuit").setHeader("CUIT");
        grid.asSingleSelect().addValueChangeListener(e -> editar(e.getValue()));
    }

    private VerticalLayout crearFormulario() {
        Button btnGuardar = new Button("Guardar", e -> guardar());
        btnGuardar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        
        VerticalLayout form = new VerticalLayout(nombre, cuit, direccion, btnGuardar);
        form.setWidth("40%");
        return form;
    }

    private void editar(Tercero t) {
        terceroActual = t;
        binder.readBean(t);
    }

    private void guardar() {
        if (terceroActual == null) terceroActual = new Tercero();
        try {
            binder.writeBean(terceroActual);
            repository.save(terceroActual);
            actualizarGrid();
        } catch (Exception ignored) {}
    }

    private void actualizarGrid() {
        grid.setItems(repository.findAll());
    }
}