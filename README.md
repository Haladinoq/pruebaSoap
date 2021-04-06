# pruebaSoap

Proyecto soap que realiza la insercion de los datos del empleado en la base de datos.

El script de la base de datos es el siguiente 

-- prueba_soap.empleado definition

CREATE TABLE `empleado` (
  `id_empleado` int(11) NOT NULL AUTO_INCREMENT,
  `nombres` varchar(100) DEFAULT NULL,
  `apellidos` varchar(100) DEFAULT NULL,
  `tipo_documento` varchar(100) DEFAULT NULL,
  `numero_documento` varchar(100) DEFAULT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  `fecini_compania` date DEFAULT NULL,
  `cargo` varchar(100) DEFAULT NULL,
  `salario` double DEFAULT NULL,
  PRIMARY KEY (`id_empleado`),
  KEY `id_empleado` (`id_empleado`),
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4;
