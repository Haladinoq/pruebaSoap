package com.pruebasoap.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.datatype.XMLGregorianCalendar;

import java.util.Date;


/**
 * The persistent class for the empleado database table.
 * 
 */
@Entity
@Table(name="empleado")
@NamedQuery(name="Empleado.findAll", query="SELECT e FROM Empleado e")
public class Empleado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_empleado")
	private int idEmpleado;

	private String apellidos;

	private String cargo;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_nacimiento")
	private Date fechaNacimiento;

	@Temporal(TemporalType.DATE)
	@Column(name="fecini_compania")
	private Date feciniCompania;

	private String nombres;

	@Column(name="numero_documento")
	private String numeroDocumento;

	private float salario;

	@Column(name="tipo_documento")
	private String tipoDocumento;

	public Empleado() {
	}
	
	public Empleado(String nombres,String apellidos,String tipoDoc,String numeroDoc,XMLGregorianCalendar fechaNacimiento,XMLGregorianCalendar fechaIniComp,String cargo,double salario) {
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.tipoDocumento = tipoDoc;
		this.numeroDocumento = numeroDoc;
		this.fechaNacimiento = fechaNacimiento.toGregorianCalendar().getTime();
		this.feciniCompania = fechaIniComp.toGregorianCalendar().getTime();
		this.cargo = cargo;
		this.salario = (float)salario;
	}


	public int getIdEmpleado() {
		return this.idEmpleado;
	}

	public void setIdEmpleado(int idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getCargo() {
		return this.cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public Date getFechaNacimiento() {
		return this.fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Date getFeciniCompania() {
		return this.feciniCompania;
	}

	public void setFeciniCompania(Date feciniCompania) {
		this.feciniCompania = feciniCompania;
	}

	public String getNombres() {
		return this.nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getNumeroDocumento() {
		return this.numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public float getSalario() {
		return this.salario;
	}

	public void setSalario(float salario) {
		this.salario = salario;
	}

	public String getTipoDocumento() {
		return this.tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

}