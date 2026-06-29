package com.example.examplefeature.ui;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.base.ui.MainLayout;
import com.example.examplefeature.model.Factura;
import com.example.examplefeature.model.Tercero;
import com.example.examplefeature.repositories.FacturaRepository;
import com.example.examplefeature.repositories.TerceroRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

@Route(value = "facturas", layout = MainLayout.class)
@PageTitle("Facturas")
@Menu(order = 2, icon = "vaadin:invoice") // Esto le pone el iconito automático en el menú
@PermitAll
public class FacturaView extends VerticalLayout {

    // Componentes visuales
    private Grid<Factura> grid = new Grid<>(Factura.class, false);
    private DateTimePicker fecha;
    private TextField numero;
    private ComboBox<Tercero> comboTercero;
    private Button btnGuardar;

    private final FacturaRepository facturaRepository;
    private final TerceroRepository terceroRepository;

    @Autowired
    public FacturaView(FacturaRepository facturaRepository, TerceroRepository terceroRepository) {
        this.facturaRepository = facturaRepository;
        this.terceroRepository = terceroRepository;
        setSizeFull();
        setPadding(true);

        add(new H2("Gestión de Facturas"));
        
        HorizontalLayout layoutPrincipal = new HorizontalLayout();
        layoutPrincipal.setWidthFull();

        configurarGrid();
        
        VerticalLayout layoutFormulario = configurarFormulario();
        
        layoutFormulario.setWidth("30%"); 
        layoutFormulario.setMinWidth("300px"); 

        layoutPrincipal.add(grid, layoutFormulario);
        
        layoutPrincipal.setFlexGrow(1, grid); 

        add(layoutPrincipal);
        
        expand(layoutPrincipal);
      
        grid.setItems(facturaRepository.findAll());
        comboTercero.setItems(terceroRepository.findAll());
    }

  
        
    

    private void configurarGrid() {
        grid.setSizeFull();
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_NO_BORDER);

        // --- ACÁ DEFINIMOS LAS COLUMNAS ORDENADAS Y LIMPIAS ---
        grid.addColumn(Factura::getId).setHeader("ID").setWidth("100px").setFlexGrow(0);
        
        // Formateamos la fecha para que se vea linda
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        grid.addColumn(factura -> factura.getFecha() != null ? factura.getFecha().format(formatter) : "")
            .setHeader("Fecha").setAutoWidth(true);
            
        grid.addColumn(Factura::getNumero).setHeader("Número").setAutoWidth(true);
        
        // Desglosamos el Tercero para que muestre solo el nombre y no todo el chorizo de texto
        grid.addColumn(factura -> {
            if (factura.getTercero() != null) {
                return factura.getTercero().getNombre();
            }
            return "Sin Cliente asignado";
        }).setHeader("Cliente / Tercero").setAutoWidth(true);
    }

    private VerticalLayout configurarFormulario() {
        FormLayout form = new FormLayout();
        
        fecha = new DateTimePicker("Fecha y Hora");
        
        numero = new TextField("Número de Factura");
       
        
        comboTercero = new ComboBox<>("Cliente / Tercero");
        comboTercero.setItemLabelGenerator(Tercero::getNombre);
        comboTercero.setPlaceholder("Seleccione un cliente...");

        form.add(fecha, numero, comboTercero);

        btnGuardar = new Button("Guardar Factura");
        btnGuardar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnGuardar.setWidthFull();
        
        btnGuardar.addClickListener(e -> {
            try {

                Factura nuevaFactura = new Factura();
                
                nuevaFactura.setFecha(fecha.getValue()); 
                // Ojo acá: convertimos el texto del TextField a número (Integer)
                nuevaFactura.setNumero(Integer.valueOf(numero.getValue()));
                nuevaFactura.setTercero(comboTercero.getValue());
                             
                facturaRepository.save(nuevaFactura);

                grid.setItems(facturaRepository.findAll());
                                
                fecha.clear();
                numero.clear();
                comboTercero.clear();
                
                // 6. Tiramos un cartelito verde de éxito
                com.vaadin.flow.component.notification.Notification.show("¡Factura guardada con éxito!")
                        .addThemeVariants(com.vaadin.flow.component.notification.NotificationVariant.LUMO_SUCCESS);
                
            } catch (NumberFormatException ex) {
                // Si en el campo "Número" escriben letras o lo dejan vacío, atajamos el error
                com.vaadin.flow.component.notification.Notification.show("Error: El número de factura debe ser válido.")
                        .addThemeVariants(com.vaadin.flow.component.notification.NotificationVariant.LUMO_ERROR);
            } catch (Exception ex) {
                // Cualquier otro error raro cae acá
                com.vaadin.flow.component.notification.Notification.show("Error al guardar la factura. Revisá que todos los datos estén completos.")
                        .addThemeVariants(com.vaadin.flow.component.notification.NotificationVariant.LUMO_ERROR);
            }
        });

        // Metemos el form y el botón en un contenedor vertical
        VerticalLayout panelDerecho = new VerticalLayout(form, btnGuardar);
        panelDerecho.setPadding(false);
        return panelDerecho;
    }
}