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
	
	@Select("SELECT CONCAT (YEAR(t.fecha_llamada),'-' ,MONTH(t.fecha_llamada)) AS fecha ,t.canal_venta_agrup as canalVentaAgrup," + 
			"SUM(CASE WHEN t.back='TGESTIONA_APP' THEN 1 ELSE 0 END) AS app," + 
			"SUM(CASE WHEN t.back='TGESTIONA_CROSS_WEB' THEN 1 ELSE 0 END) AS web," + 
			"SUM(CASE WHEN t.back!='TGESTIONA_CROSS_WEB' AND back!='TGESTIONA_APP' THEN 1 ELSE 0 END) AS otros," + 
			"(SUM(CASE WHEN t.back='TGESTIONA_APP' THEN 1 ELSE 0 END) / COUNT(1)) AS usabilidadApp," + 
			"(SUM(CASE WHEN t.back='TGESTIONA_CROSS_WEB' THEN 1 ELSE 0 END) / COUNT(1)) AS usabilidadWeb," + 
			"COUNT(1) AS total " + 
			"FROM bd_trafico.trafico t" + 
			"WHERE  t.FLAG_DEPENDENCIA='PRINCIPAL' AND t.GESTION='CE' " + 
			"AND YEAR(t.fecha_llamada)!=0 " + 
			"GROUP BY YEAR(t.fecha_llamada), MONTH(t.fecha_llamada), t.canal_venta_agrup ")
	List<ResumenHistorico> resumenHistorico();
	
	@Select("SELECT canal_venta_agrup as canalVentaAgrup, COUNT( DISTINCT COD_VENDEDOR_ATIS) AS totalVend, " +
			"SUM(CASE WHEN back='TGESTIONA_APP' AND FLAG_DEPENDENCIA='PRINCIPAL' AND GESTION='CE'  THEN 1 ELSE 0 END) AS ventasApp , " + 
			"SUM(CASE WHEN back!='TGESTIONA_APP' AND FLAG_DEPENDENCIA='PRINCIPAL' AND GESTION='CE'  THEN 1 ELSE 0 END) AS ventasWeb " + 
			"FROM trafico " + 
			"WHERE canal_venta_agrup='AGENCIAS' OR canal_venta_agrup='GRANDES SUPERFICIES' " + 
			"AND FLAG_DEPENDENCIA='PRINCIPAL' AND GESTION='CE' " + 
			"GROUP BY  canal_venta_agrup")
	List<ResumenGeneral> resumenGeneral();
	
	@Select(
			"SELECT canal_venta_agrup, COUNT( DISTINCT COD_VENDEDOR_ATIS) AS total " + 
			"FROM trafico " + 
			"WHERE canal_venta_agrup='AGENCIAS' OR canal_venta_agrup='GRANDES SUPERFICIES' " + 
			"AND FLAG_DEPENDENCIA='PRINCIPAL' AND GESTION='CE' " + 
			"GROUP BY  canal_venta_agrup " + 
			"UNION " + 
			"SELECT canal_venta_agrup, COUNT( DISTINCT COD_VENDEDOR_ATIS) AS total " + 
			"FROM trafico " + 
			"WHERE canal_venta_agrup!='AGENCIAS' AND canal_venta_agrup!='GRANDES SUPERFICIES' " + 
			"AND FLAG_DEPENDENCIA='PRINCIPAL' AND GESTION='CE' " + 
			"GROUP BY  canal_venta_agrup ")
	List<Convertibilidad> convertibilidad();
	
	@Insert("INSERT INTO trafico(NOMBRE_ASESOR,COD_ASESOR_BACK) values(#{nombreAsesor},#{codAsesorBack})")
	@SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Integer.class)
	void insert(Trafico trafico);
}
