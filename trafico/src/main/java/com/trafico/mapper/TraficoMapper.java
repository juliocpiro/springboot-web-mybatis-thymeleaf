package com.trafico.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.trafico.model.Convertibilidad;
import com.trafico.model.ResumenGeneral;
import com.trafico.model.ResumenHistorico;
import com.trafico.model.Trafico;

@Mapper
public interface TraficoMapper {
	
	@Select("select * from trafico")
	List<Trafico> findAll();
	
	@Select("SELECT t.fecha_llamada as fechaLlamada, " + 
			"t.canal_venta_agrup as canalVentaAgrup, " + 
			"SUM(CASE WHEN back='TGESTIONA_APP' THEN 1 ELSE 0 END) AS app, " + 
			"SUM(CASE WHEN back='TGESTIONA_CROSS_WEB' THEN 1 ELSE 0 END) AS web, " + 
			"SUM(CASE WHEN back!='TGESTIONA_CROSS_WEB' AND back!='TGESTIONA_APP' THEN 1 ELSE 0 END) AS otros, " + 
			"(SUM(CASE WHEN back='TGESTIONA_APP' THEN 1 ELSE 0 END) / COUNT(1)) AS usabilidadApp, " + 
			"(SUM(CASE WHEN back='TGESTIONA_CROSS_WEB' THEN 1 ELSE 0 END) / COUNT(1)) AS usabilidadWeb, " + 
			"COUNT(1) AS total " + 
			"FROM bd_trafico.trafico t " + 
			"WHERE FLAG_DEPENDENCIA='PRINCIPAL' " + 
			"GROUP BY  t.canal_venta_agrup")
	List<ResumenHistorico> resumenHistorico();
	
	@Select("SELECT t.canal_venta_agrup as canalVentaAgrup, " + 
			"SUM(CASE WHEN canal_venta_agrup='AGENCIAS' OR canal_venta_agrup='GRANDES SUPERFICIES' THEN 1 ELSE 0 END) AS vendeApp, " + 
			"SUM(CASE WHEN canal_venta_agrup!='AGENCIAS' AND canal_venta_agrup!='GRANDES SUPERFICIES' THEN 1 ELSE 0 END) AS vendeWeb, " + 
			"COUNT(1) AS total " + 
			"FROM bd_trafico.trafico t " + 
			"WHERE FLAG_DEPENDENCIA='PRINCIPAL' " + 
			"GROUP BY  t.canal_venta_agrup")
	List<ResumenGeneral> resumenGeneral();
	
	@Select("SELECT canal_venta_agrup as canalVentaAgrup, " + 
			"(SUM(CASE WHEN canal_venta_agrup='AGENCIAS' OR canal_venta_agrup='GRANDES SUPERFICIES' THEN 1 ELSE 0 END) + " + 
			"SUM(CASE WHEN canal_venta_agrup!='AGENCIAS' AND canal_venta_agrup!='GRANDES SUPERFICIES' THEN 1 ELSE 0 END)) AS total " + 
			"FROM trafico " + 
			"WHERE FLAG_DEPENDENCIA='PRINCIPAL'" + 
			"GROUP BY  canal_venta_agrup")
	List<Convertibilidad> convertibilidad();
	
	@Insert("INSERT INTO trafico(NOMBRE_ASESOR,COD_ASESOR_BACK) values(#{nombreAsesor},#{codAsesorBack})")
	@SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Integer.class)
	void insert(Trafico trafico);
}
