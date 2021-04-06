package com.pruebasoap.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.pruebasoap.entity.Empleado;

@Repository
public interface EmpleadoEntityRepository extends CrudRepository <Empleado, Long>{

	public Empleado findByNumeroDocumento(String numero_documento);
}
