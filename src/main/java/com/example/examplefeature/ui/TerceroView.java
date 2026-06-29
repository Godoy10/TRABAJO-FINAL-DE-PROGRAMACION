package com.example.examplefeature.ui;

import com.example.base.ui.MainLayout;
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
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "tercero", layout = MainLayout.class)
@PageTitle("Gestión de Terceros")
@Menu(order = 1, icon = "vaadin:users")
@PermitAll
public class TerceroView extends VerticalLayout {

    private final TerceroRepository terceroRepository;

    private Grid<Tercero> grid = new Grid<>(Tercero.class, false);
    
    // Formulario
    private TextField nombre = new TextField("Nombre");
    private TextField cuit = new TextField("CUIT");
    private TextField direccion = new TextField("Dirección");

    // Botones transaccionales
    private Button btnGuardar = new Button("Guardar");
    private Button btnEliminar = new Button("Eliminar");
    private Button btnNuevo = new Button("Limpiar / Nuevo");

    // Contrato de datos
    private Binder<Tercero> binder = new Binder<>(Tercero.class);
    private Tercero terceroActual;

    @Autowired
    public TerceroView(TerceroRepository terceroRepository) {
        this.terceroRepository = terceroRepository;

        setSizeFull();
        setPadding(true);

        add(new H2("Gestión de Terceros"));
        
        HorizontalLayout layoutPrincipal = new HorizontalLayout();
        layoutPrincipal.setWidthFull();

        configurarGrid();
        VerticalLayout layoutFormulario = configurarFormulario();
        
        // Reglas de ancho exactas a PagoView
        layoutFormulario.setWidth("30%");
        layoutFormulario.setMinWidth("300px");
        
        layoutPrincipal.add(grid, layoutFormulario);
        layoutPrincipal.setFlexGrow(1, grid); 

        add(layoutPrincipal);
        expand(layoutPrincipal);
    }

    private void configurarGrid() {
        // Usamos lambdas para seguridad en tiempo de compilación
        grid.addColumn(Tercero::getNombre).setHeader("Nombre").setAutoWidth(true);
        grid.addColumn(Tercero::getCuitl).setHeader("CUIT");
        grid.addColumn(Tercero::getDireccion).setHeader("Dirección");

        grid.setItems(terceroRepository.findAll());
        
        grid.asSingleSelect().addValueChangeListener(event -> editarTercero(event.getValue()));
    }

    private VerticalLayout configurarFormulario() {
        // --- CONTRATO DE BINDING ---
        binder.forField(nombre).bind(Tercero::getNombre, Tercero::setNombre);
        binder.forField(cuit).bind(Tercero::getCuitl, Tercero::setCuitl);
        binder.forField(direccion).bind(Tercero::getDireccion, Tercero::setDireccion);

        // --- ESTILOS DE BOTONES ---
        btnGuardar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnGuardar.setWidthFull();
        btnEliminar.addThemeVariants(ButtonVariant.LUMO_ERROR);
        btnEliminar.setWidthFull();
        btnNuevo.setWidthFull();

        // --- LISTENERS ---
        btnGuardar.addClickListener(e -> guardarTercero());
        btnEliminar.addClickListener(e -> eliminarTercero());
        btnNuevo.addClickListener(e -> limpiarFormulario());

        VerticalLayout form = new VerticalLayout(nombre, cuit, direccion, btnGuardar, btnEliminar, btnNuevo);
        form.setPadding(false); 
        
        // Obligamos a los campos a usar el 100% de su contenedor (el 30% de la pantalla)
        nombre.setWidthFull();
        cuit.setWidthFull();
        direccion.setWidthFull();

        return form;
    }

    // --- LÓGICA DE NEGOCIO ---

    private void editarTercero(Tercero tercero) {
        if (tercero == null) {
            limpiarFormulario();
        } else {
            terceroActual = tercero;
            binder.readBean(terceroActual);
        }
    }

    private void limpiarFormulario() {
        terceroActual = new Tercero(); 
        binder.readBean(terceroActual);
        grid.deselectAll(); 
    }

    private void guardarTercero() {
        if (terceroActual == null) {
            terceroActual = new Tercero();
        }
        try {
            binder.writeBean(terceroActual);
            terceroRepository.save(terceroActual);
            grid.setItems(terceroRepository.findAll());
            limpiarFormulario();
        } catch (Exception e) {
            System.err.println("Error de validación al guardar: " + e.getMessage());
        }
    }

    private void eliminarTercero() {
        if (terceroActual != null && terceroActual.getId() != null) {
            terceroRepository.delete(terceroActual);
            grid.setItems(terceroRepository.findAll());
            limpiarFormulario();
        }
    }
}