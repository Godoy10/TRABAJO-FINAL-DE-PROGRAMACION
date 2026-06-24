package com.example.examplefeature.model;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="facturas")
public class Factura {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_factura")
	private Long id;
	
	@CreationTimestamp
	@Column(name = "fecha_factura", nullable = false, updatable = false)
	private LocalDateTime fecha;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_tercero", nullable = false)
	private Tercero tercero;
	
	@Column(name = "numero") 
	private Integer numero;
		
	@OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonManagedReference 
	private List<Item> listaItems;
	
	public void addItem(Item item) {
        listaItems.add(item);
        item.setFactura(this); 
    }

    public void removeItem(Item item) {
        listaItems.remove(item);
        item.setFactura(null);
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public Tercero getTercero() {
		return tercero;
	}

	public void setTercero(Tercero tercero) {
		this.tercero = tercero;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public List<Item> getListaItems() {
		return listaItems;
	}

	public void setListaItems(List<Item> listaItems) {
		this.listaItems = listaItems;
	}
	
}
