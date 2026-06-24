package com.example.examplefeature.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "terceros")
public class Tercero {

	public enum SituacionIVA {
		MONOTRIBUTO("Monotributo"),
		RESPONSABLE_INSCRIPTO("Responsable Inscripto"),
		CONSUMIDOR_FINAL("Consumidor Final");

		private final String descripcion;

		SituacionIVA(String descripcion) {
			this.descripcion = descripcion;
		}

		public String getDescripcion() {
			return descripcion;
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_tercero")
	private Long id;

	@Column(name = "nombre")
	private String nombre;
	@Column(name = "cuitl")
	private String cuitl;
	@Enumerated(EnumType.STRING)
	@Column(name = "sitiva", nullable = false)
	@NotNull(message="Lsituacion IVA es obligatoria")
	private SituacionIVA sitiva;
	@Column(name = "direccion")
	private String direccion;
	@Column(name = "localidad")
	private String localidad;
	@Column(name = "provincia")
	private String provincia;
	@Column(name = "telefonos")
	private String telefonos;
	@Column(name = "saldo_apertura")
	private BigDecimal saldo_apertura;
	@Column(name = "tipo_saldo")
	private String tipo_saldo;
	
	@OneToMany(mappedBy = "tercero", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Factura> listaTerceros;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCuitl() {
		return cuitl;
	}

	public void setCuitl(String cuitl) {
		this.cuitl = cuitl;
	}

	public SituacionIVA getSitiva() {
		return sitiva;
	}

	public void setSitiva(SituacionIVA sitiva) {
		this.sitiva = sitiva;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getTelefonos() {
		return telefonos;
	}

	public void setTelefonos(String telefonos) {
		this.telefonos = telefonos;
	}
}
