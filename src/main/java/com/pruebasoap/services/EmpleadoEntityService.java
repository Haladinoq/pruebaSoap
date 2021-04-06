package com.pruebasoap.services;

import java.util.List;

import com.pruebasoap.entity.Empleado;
public interface EmpleadoEntityService { 
	public Empleado getEntityById(long id);
	public Empleado getEntityByNumDoc(String  numero_documento);
	public List<Empleado> getAllEntities();
	public Empleado addEntity(Empleado entity);
	public boolean updateEntity(Empleado entity);
	public boolean deleteEntityById(long id);

}
