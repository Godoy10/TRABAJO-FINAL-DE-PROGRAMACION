package com.example.examplefeature.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexWrap;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("") // La ruta vacía define a esta vista como tu página de inicio (home)
@AnonymousAllowed
public class HomeView extends VerticalLayout {

    public HomeView() {
        // Estilos para centrar el contenido (equivalente a tu flex y alignItems en React)
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setPadding(true);
        setSpacing(true);
        setSizeFull();

        // Elementos visuales
        H2 titulo = new H2("Sistema de Gestión");
        Paragraph subtitulo = new Paragraph("Seleccione el módulo con el que desea operar:");

        // Layout horizontal para los botones
        FlexLayout menuBotones = new FlexLayout();
        menuBotones.setFlexWrap(FlexWrap.WRAP);
        menuBotones.setJustifyContentMode(JustifyContentMode.CENTER);
        menuBotones.setAlignItems(Alignment.CENTER); // Para que también se alineen verticalmente
        menuBotones.getStyle().set("gap", "10px"); // Agregamos un poco de espacio entre botones
        menuBotones.setJustifyContentMode(JustifyContentMode.CENTER);

        // Botones de navegación (equivalente a tus <Link> de React)
        Button btnFacturas = new Button("Módulo de Facturas", e -> getUI().ifPresent(ui -> ui.navigate("facturas")));
        btnFacturas.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_LARGE);

        Button btnTerceros = new Button("Módulo de Terceros", e -> getUI().ifPresent(ui -> ui.navigate("tercero")));
        btnTerceros.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_LARGE);

        Button btnFacultades = new Button("Módulo de Facultades", e -> getUI().ifPresent(ui -> ui.navigate("facultad")));
        btnFacultades.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_LARGE);
        
        Button btnPagos = new Button("Módulo de Pagos", e -> getUI().ifPresent(ui -> ui.navigate("pago")));
        btnPagos.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_LARGE);

        menuBotones.add(btnFacturas, btnTerceros, btnFacultades, btnPagos);

        // Agregamos todo al layout principal
        add(titulo, subtitulo, menuBotones);
    }
}