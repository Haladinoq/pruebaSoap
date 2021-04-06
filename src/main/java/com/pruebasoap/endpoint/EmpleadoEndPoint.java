package com.pruebasoap.endpoint;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.pruebasoap.empleado_ws.AddEmpleadoRequest;
import com.pruebasoap.empleado_ws.AddEmpleadoResponse;
import com.pruebasoap.empleado_ws.DeleteEmpleadoRequest;
import com.pruebasoap.empleado_ws.DeleteEmpleadoResponse;
import com.pruebasoap.empleado_ws.EmpleadoType;
import com.pruebasoap.empleado_ws.GetAllEmpleadosRequest;
import com.pruebasoap.empleado_ws.GetAllEmpleadosResponse;
import com.pruebasoap.empleado_ws.GetEmpleadoByIdRequest;
import com.pruebasoap.empleado_ws.GetEmpleadoByIdResponse;
import com.pruebasoap.empleado_ws.ServiceStatus;
import com.pruebasoap.empleado_ws.Tiempo;
import com.pruebasoap.empleado_ws.UpdateEmpleadoRequest;
import com.pruebasoap.empleado_ws.UpdateEmpleadoResponse;
import com.pruebasoap.entity.Empleado;
import com.pruebasoap.services.EmpleadoEntityService;

@Endpoint
public class EmpleadoEndPoint { 
	
	public static final String NAMESPACE_URI = "http://www.pruebasoap.com/empleado-ws";
	

	private EmpleadoEntityService service;

	public EmpleadoEndPoint() {

	}

	@Autowired
	public EmpleadoEndPoint(EmpleadoEntityService service) {
		this.service = service;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getEmpleadoByIdRequest")
	@ResponsePayload
	public GetEmpleadoByIdResponse getEmpleadoById(@RequestPayload GetEmpleadoByIdRequest request) {
		GetEmpleadoByIdResponse response = new GetEmpleadoByIdResponse();
		Empleado empleadoEntity = service.getEntityById(request.getEmpleadoId());
		EmpleadoType empleadoType = new EmpleadoType();
		BeanUtils.copyProperties(empleadoEntity, empleadoType);
		response.setEmpleadoType(empleadoType);
		return response;

	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllEmpleadosRequest")
	@ResponsePayload
	public GetAllEmpleadosResponse getAllEmpleados(@RequestPayload GetAllEmpleadosRequest request) {
		GetAllEmpleadosResponse response = new GetAllEmpleadosResponse();
		List<EmpleadoType> empleadoTypeList = new ArrayList<EmpleadoType>();
		List<Empleado> empleadoEntityList = service.getAllEntities();
		for (Empleado entity : empleadoEntityList) {
			EmpleadoType empleadoType = new EmpleadoType();
			BeanUtils.copyProperties(entity, empleadoType);
			empleadoTypeList.add(empleadoType);
		}
		response.getEmpleadoType().addAll(empleadoTypeList);

		return response;

	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "addEmpleadoRequest")
	@ResponsePayload
	public AddEmpleadoResponse addEmpleado(@RequestPayload AddEmpleadoRequest request) {
		AddEmpleadoResponse response = new AddEmpleadoResponse();
		EmpleadoType newEmpleadoType = new EmpleadoType();
		Tiempo newtiempoVinculacion= diferenciaFechas(request.getFechaIniComp());
		Tiempo newEdad=diferenciaFechas(request.getFechaNacimiento());
		ServiceStatus serviceStatus = new ServiceStatus();
		

		Empleado newEmpleadoEntity = new Empleado(request.getNombres(), request.getApellidos(),request.getTipoDoc(),request.getNumeroDoc(),request.getFechaNacimiento(),request.getFechaIniComp(),request.getCargo(),request.getSalario());
		Empleado savedEmpleadoEntity = service.addEntity(newEmpleadoEntity);

		if (savedEmpleadoEntity == null) {
			serviceStatus.setStatusCode("CONFLICT");
			serviceStatus.setMessage("Exception while adding Entity");
		} else {

			BeanUtils.copyProperties(request, newEmpleadoType);
			serviceStatus.setStatusCode("SUCCESS");
			serviceStatus.setMessage("Empleado agregado correctamente");
		}

		response.setEmpleadoType(newEmpleadoType);
		response.setServiceStatus(serviceStatus);
		response.setTiempoVinculacion(newtiempoVinculacion);
		response.setEdad(newEdad);
		return response;

	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateEmpleadoRequest")
	@ResponsePayload
	public UpdateEmpleadoResponse updateEmpleado(@RequestPayload UpdateEmpleadoRequest request) {
		UpdateEmpleadoResponse response = new UpdateEmpleadoResponse();
		ServiceStatus serviceStatus = new ServiceStatus();
		// 1. Find if employed available
		Empleado empleadoFromDB = service.getEntityByNumDoc(request.getNumeroDoc());
		
		if(empleadoFromDB == null) {
			serviceStatus.setStatusCode("NOT FOUND");
			serviceStatus.setMessage("Empleado = " + request.getNumeroDoc() + " not found");
		}else {
			
			// 2. Get updated employed information from the request
			empleadoFromDB.setNombres(request.getNombres());
			empleadoFromDB.setApellidos(request.getApellidos());
			empleadoFromDB.setTipoDocumento(request.getTipoDoc());
			empleadoFromDB.setNumeroDocumento(request.getNumeroDoc());
			empleadoFromDB.setFechaNacimiento(request.getFechaNacimiento().toGregorianCalendar().getTime());
			empleadoFromDB.setFeciniCompania(request.getFechaIniComp().toGregorianCalendar().getTime());
			empleadoFromDB.setCargo(request.getCargo());
			empleadoFromDB.setSalario((float)request.getSalario());
			
			// 3. update the employed in database
			
			boolean flag = service.updateEntity(empleadoFromDB);
			
			if(flag == false) {
				serviceStatus.setStatusCode("CONFLICT");
				serviceStatus.setMessage("Exception while updating Entity=" + request.getNumeroDoc());;
			}else {
				serviceStatus.setStatusCode("SUCCESS");
				serviceStatus.setMessage("Content updated Successfully");
			}
			
			
		}
		
		response.setServiceStatus(serviceStatus);
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteEmpleadoRequest")
	@ResponsePayload
	public DeleteEmpleadoResponse deleteEmpleado(@RequestPayload DeleteEmpleadoRequest request) {
		DeleteEmpleadoResponse response = new DeleteEmpleadoResponse();
		ServiceStatus serviceStatus = new ServiceStatus();

		boolean flag = service.deleteEntityById(request.getEmpleadoId());

		if (flag == false) {
			serviceStatus.setStatusCode("FAIL");
			serviceStatus.setMessage("Exception while deletint Entity id=" + request.getEmpleadoId());
		} else {
			serviceStatus.setStatusCode("SUCCESS");
			serviceStatus.setMessage("Content Deleted Successfully");
		}

		response.setServiceStatus(serviceStatus);
		return response;
	} 
	
	
	public Tiempo diferenciaFechas(XMLGregorianCalendar fecIni) {  
		
		Tiempo tiempoDiferencia=new Tiempo();		
		LocalDate fechaInicial = fecIni.toGregorianCalendar().toZonedDateTime().toLocalDate();
		LocalDate today=LocalDate.now(); 
		
		Period periodo = Period.between(fechaInicial, today);
		
		tiempoDiferencia.setAnio(periodo.getYears());
		tiempoDiferencia.setMeses(periodo.getMonths());
		tiempoDiferencia.setDias(periodo.getDays()); 
		
		return tiempoDiferencia;
	}


}
