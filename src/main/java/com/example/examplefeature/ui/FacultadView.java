package com.example.examplefeature.ui;

import com.example.base.ui.MainLayout;
import com.example.examplefeature.model.Facultad;
import com.example.examplefeature.repositories.FacultadRepository; 

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import jakarta.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "facultad", layout = MainLayout.class)
@PageTitle("Gestión de Facultades")
@Menu(order = 4, icon = "vaadin:institution") 
@PermitAll
public class FacultadView extends VerticalLayout {

    // Inyectamos el Repositorio directamente para evitar el conflicto con Hilla
    private final FacultadRepository repository;

    private Grid<Facultad> grid = new Grid<>(Facultad.class, false);

    // ATENCIÓN: El nombre de estas variables es idéntico a tus atributos de Facultad.java. No los cambies.
    private TextField nombre = new TextField("Nombre de la Facultad");
    private TextField direccion = new TextField("Dirección");
    private TextField cuit = new TextField("CUIT");
    private IntegerField sucursal = new IntegerField("Número de Sucursal");
    private TextField telefonos = new TextField("Teléfonos");
    private TextField correo = new TextField("Correo Electrónico");
    private Checkbox defecto = new Checkbox("¿Es sede por defecto?");

    private Button btnGuardar = new Button("Guardar");
    private Button btnEliminar = new Button("Eliminar");
    private Button btnCancelar = new Button("Cancelar");

    private Binder<Facultad> binder = new Binder<>(Facultad.class);
    private Facultad facultadActual;

    @Autowired
    public FacultadView(FacultadRepository repository) {
        this.repository = repository;

        add(new H2("Gestión de Facultades"), new H4("C.R.U.D. Nativo en Java"));

        configurarGrid();
        
        HorizontalLayout layoutPrincipal = new HorizontalLayout(grid, crearPanelFormulario());
        layoutPrincipal.setSizeFull();
        grid.setWidth("65%");
        
        add(layoutPrincipal);
        setSizeFull();

        // El motor de Vaadin 25 lee las variables visuales y las mapea con los atributos automáticamente
        binder.bindInstanceFields(this);

        actualizarGrid();
    }

    private void configurarGrid() {
        // Seleccionamos qué columnas mostrar para no saturar la tabla visualmente
        grid.addColumn(Facultad::getId).setHeader("ID").setAutoWidth(true);
        grid.addColumn(Facultad::getNombre).setHeader("Nombre").setAutoWidth(true);
        grid.addColumn(Facultad::getDireccion).setHeader("Dirección").setAutoWidth(true);
        grid.addColumn(Facultad::getCuit).setHeader("CUIT").setAutoWidth(true);
        
        grid.asSingleSelect().addValueChangeListener(evento -> editarFacultad(evento.getValue()));
    }

    private VerticalLayout crearPanelFormulario() {
        btnGuardar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnEliminar.addThemeVariants(ButtonVariant.LUMO_ERROR);

        btnGuardar.addClickListener(e -> guardarFacultad());
        btnEliminar.addClickListener(e -> eliminarFacultad());
        btnCancelar.addClickListener(e -> limpiarFormulario());

        HorizontalLayout botones = new HorizontalLayout(btnGuardar, btnEliminar, btnCancelar);
        
        // Empaquetamos todos los campos detectados en tu entidad
        VerticalLayout panel = new VerticalLayout(
            nombre, direccion, cuit, sucursal, telefonos, correo, defecto, botones
        );
        panel.setWidth("35%");
        return panel;
    }

    private void actualizarGrid() {
        // Al usar el Repositorio, findAll() es un comando nativo garantizado
        grid.setItems(repository.findAll());
    }

    private void editarFacultad(Facultad facultad) {
        if (facultad == null) {
            limpiarFormulario();
        } else {
            facultadActual = facultad;
            binder.readBean(facultadActual);
        }
    }

    private void guardarFacultad() {
        if (facultadActual == null) {
            facultadActual = new Facultad();
        }
        try {
            binder.writeBean(facultadActual);
            repository.save(facultadActual);
            actualizarGrid();
            limpiarFormulario();
            Notification.show("Facultad guardada exitosamente en la base de datos.", 3000, Notification.Position.BOTTOM_END);
        } catch (Exception e) {
            Notification.show("Rechazado: Verifica que los campos sean correctos.");
        }
    }

    private void eliminarFacultad() {
        if (facultadActual != null && facultadActual.getId() != null) {
            repository.delete(facultadActual);
            actualizarGrid();
            limpiarFormulario();
            Notification.show("Registro eliminado de PostgreSQL.");
        }
    }

    private void limpiarFormulario() {
        facultadActual = null;
        binder.readBean(null);
        grid.deselectAll();
    }
}