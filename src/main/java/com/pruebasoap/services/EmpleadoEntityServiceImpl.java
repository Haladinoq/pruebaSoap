package com.pruebasoap.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pruebasoap.entity.Empleado;
import com.pruebasoap.repository.EmpleadoEntityRepository;

@Service
@Transactional
public class EmpleadoEntityServiceImpl implements EmpleadoEntityService {
	
	private EmpleadoEntityRepository repository;

    public EmpleadoEntityServiceImpl() {

    }

    @Autowired
    public EmpleadoEntityServiceImpl(EmpleadoEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public Empleado getEntityById(long id) {
        return this.repository.findById(id).get();
    }

    @Override
    public Empleado getEntityByNumDoc(String numero_documento) {
        return this.repository.findByNumeroDocumento(numero_documento);
    }

    @Override
    public List < Empleado > getAllEntities() {
        List < Empleado > list = new ArrayList < > ();
        repository.findAll().forEach(e -> list.add(e));
        return list;
    }

    @Override
    public Empleado addEntity(Empleado entity) {
        try {
            return this.repository.save(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public boolean updateEntity(Empleado entity) {
        try {
            this.repository.save(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteEntityById(long id) {
        try {
            this.repository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }

    }
	

}
