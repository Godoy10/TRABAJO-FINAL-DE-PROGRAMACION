package com.example.examplefeature.ui;

import com.example.base.ui.MainLayout;
import com.example.examplefeature.model.MetodoPago;
import com.example.examplefeature.model.Pago;
import com.example.examplefeature.model.Tercero;
import com.example.examplefeature.repositories.PagoRepository;
import com.example.examplefeature.repositories.TerceroRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "pago", layout = MainLayout.class)
@PageTitle("Pagos a Proveedores")
@Menu(order = 3, icon = "vaadin:money") 
@PermitAll
public class PagoView extends VerticalLayout {

    private final PagoRepository pagoRepository;
    private final TerceroRepository terceroRepository;

    private Grid<Pago> grid = new Grid<>(Pago.class, false);
    
    // Campos del formulario
    private ComboBox<Tercero> comboTercero = new ComboBox<>("Seleccionar Tercero");
    private DatePicker fechaPago = new DatePicker("Fecha de Pago");
    private NumberField monto = new NumberField("Monto de Pago");
    private ComboBox<MetodoPago> comboMetodo = new ComboBox<>("Método de Pago");

 
    private Binder<Pago> binder = new Binder<>(Pago.class);
   
    private Pago pagoActual;
    
    // Botones
    private Button btnGuardar = new Button("Guardar Pago");
    private Button btnEliminar = new Button("Eliminar Pago");
    private Button btnNuevo = new Button("Limpiar / Nuevo");

    @Autowired
    public PagoView(PagoRepository pagoRepository, TerceroRepository terceroRepository) {
        this.pagoRepository = pagoRepository;
        this.terceroRepository = terceroRepository;

        setSizeFull();
        setPadding(true);

        add(new H2("Gestión de Pagos"));
        
        // El contenedor maestro que divide la pantalla (Izquierda/Derecha)
        HorizontalLayout layoutPrincipal = new HorizontalLayout();
        layoutPrincipal.setWidthFull();

        // 1. Configuramos y agregamos la Grilla (Izquierda)
        configurarGrid();
        
        // 2. Configuramos y agregamos el Formulario (Derecha)
        VerticalLayout layoutFormulario = configurarFormulario();
        
        // Reglas de ancho: El formulario ocupa el 30%, la grilla toma el resto
        layoutFormulario.setWidth("30%");
        layoutFormulario.setMinWidth("300px");
        
        layoutPrincipal.add(grid, layoutFormulario);
        layoutPrincipal.setFlexGrow(1, grid); // La grilla absorbe el espacio sobrante

        add(layoutPrincipal);
        expand(layoutPrincipal);
    }

    private void configurarGrid() {
        grid.addColumn(pago -> pago.getTercero() != null ? pago.getTercero().getNombre() : "Sin Tercero")
            .setHeader("Proveedor").setAutoWidth(true);
        grid.addColumn(Pago::getFechaPago).setHeader("Fecha");
        grid.addColumn(Pago::getMonto).setHeader("Monto");
        grid.addColumn(Pago::getMetodoPago).setHeader("Método");

        grid.setItems(pagoRepository.findAll());
        
        grid.asSingleSelect().addValueChangeListener(event -> editarPago(event.getValue()));
    }
    
    private void editarPago(Pago pago) {
        if (pago == null) {
            limpiarFormulario();
        } else {
            pagoActual = pago;
            binder.readBean(pagoActual); // El Binder lee el objeto y llena los campos automáticamente
        }
    }

    private void limpiarFormulario() {
        pagoActual = new Pago(); // Creamos un pago en blanco
        binder.readBean(pagoActual); // Vaciamos los campos
        grid.deselectAll(); // Quitamos la selección azul de la grilla
    }

    private void guardarPago() {
        if (pagoActual == null) {
            pagoActual = new Pago();
        }
        
        try {
            // 1. El Binder intenta escribir los datos de los campos hacia el objeto Java
            binder.writeBean(pagoActual);
            
            // 2. Si todo está correcto, guardamos en la base de datos
            pagoRepository.save(pagoActual);
            
            // 3. Recargamos la grilla y limpiamos
            grid.setItems(pagoRepository.findAll());
            limpiarFormulario();
            
        } catch (Exception e) {
            // Si hay un error (ej. dejaron un campo vacío), cae aquí
            System.err.println("Error al guardar: " + e.getMessage());
        }
    }

    private void eliminarPago() {
        if (pagoActual != null && pagoActual.getId() != null) {
            pagoRepository.delete(pagoActual);
            grid.setItems(pagoRepository.findAll());
            limpiarFormulario();
        }
    }

    private VerticalLayout configurarFormulario() {
        // Carga de combos
        comboTercero.setItems(terceroRepository.findAll());
        comboTercero.setItemLabelGenerator(Tercero::getNombre);
        comboMetodo.setItems(MetodoPago.values()); 

        // Estilos de botones
        btnGuardar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnGuardar.setWidthFull();
        btnEliminar.addThemeVariants(ButtonVariant.LUMO_ERROR);
        btnEliminar.setWidthFull();
        btnNuevo.setWidthFull();

        // Agrupamos los componentes verticalmente
        VerticalLayout form = new VerticalLayout(
            comboTercero, 
            fechaPago, 
            monto, 
            comboMetodo, 
            btnGuardar, 
            btnEliminar, 
            btnNuevo
        );
        
        form.setPadding(false); // Quitamos padding extra para alinear bien
        
       
        comboTercero.setWidthFull();
        fechaPago.setWidthFull();
        monto.setWidthFull();
        comboMetodo.setWidthFull();

        binder.forField(comboTercero).bind(Pago::getTercero, Pago::setTercero);
        binder.forField(fechaPago).bind(Pago::getFechaPago, Pago::setFechaPago);
        binder.forField(monto).bind(Pago::getMonto, Pago::setMonto);
        binder.forField(comboMetodo).bind(Pago::getMetodoPago, Pago::setMetodoPago);

        btnGuardar.addClickListener(e -> guardarPago());
        btnEliminar.addClickListener(e -> eliminarPago());
        btnNuevo.addClickListener(e -> limpiarFormulario());
        
        return form;
    }
}