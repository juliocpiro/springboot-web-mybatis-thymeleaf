package com.trafico.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.trafico.model.Despliegue;
import com.trafico.model.Adopcion;
import com.trafico.model.Convertibilidad;
import com.trafico.model.Usabilidad;
import com.trafico.model.Trafico;

@Mapper
public interface TraficoMapper {
	
	@Select("select * from trafico")
	List<Trafico> findAll();
	
	@Select("SELECT CONCAT (YEAR(t.fecha_llamada),'-' ,MONTH(t.fecha_llamada)) AS fechaLlamada ,t.canal_venta_agrup as canalVentaAgrup," + 
			"SUM(CASE WHEN t.back='TGESTIONA_APP' and canal_venta_agrup='AGENCIAS' OR canal_venta_agrup='GRANDES SUPERFICIES'  THEN 1 ELSE 0 END) AS app," + 
			"SUM(CASE WHEN t.back='TGESTIONA_CROSS_WEB' THEN 1 ELSE 0 END) AS web," + 
			"SUM(CASE WHEN t.back!='TGESTIONA_CROSS_WEB' AND back!='TGESTIONA_APP' THEN 1 ELSE 0 END) AS otros," + 
			"(SUM(CASE WHEN t.back='TGESTIONA_APP' THEN 1 ELSE 0 END) / COUNT(1)) AS usabilidadApp," + 
			"(SUM(CASE WHEN t.back='TGESTIONA_CROSS_WEB' THEN 1 ELSE 0 END) / COUNT(1)) AS usabilidadWeb," + 
			"COUNT(1) AS total " + 
			"FROM bd_trafico.trafico t " + 
			"WHERE  t.FLAG_DEPENDENCIA='PRINCIPAL' AND t.GESTION='CE' " + 
			"AND YEAR(t.fecha_llamada)!=0 " + 
			"GROUP BY YEAR(t.fecha_llamada), MONTH(t.fecha_llamada), t.canal_venta_agrup ")
	List<Usabilidad> usabilidad();
	
	@Select("SELECT canal_venta_agrup as canalVentaAgrup, "
			+"IF ( back='TGESTIONA_APP' AND canal_venta_agrup='AGENCIAS' OR canal_venta_agrup='GRANDES SUPERFICIES' ,COUNT( DISTINCT COD_VENDEDOR_ATIS) , 0) AS totalVend, " +
			"IF ( back='TGESTIONA_CROSS_WEB' ,COUNT( DISTINCT COD_VENDEDOR_ATIS) , 0) AS vendWeb, " +
			"SUM(CASE WHEN back='TGESTIONA_APP' AND canal_venta_agrup='AGENCIAS' OR canal_venta_agrup='GRANDES SUPERFICIES'  THEN 1 ELSE 0 END) AS ventasApp , " + 
			"SUM(CASE WHEN back='TGESTIONA_CROSS_WEB' AND FLAG_DEPENDENCIA='PRINCIPAL' AND GESTION='CE'  THEN 1 ELSE 0 END) AS ventasWeb ," +
			"SUM(CASE WHEN back!='TGESTIONA_CROSS_WEB' AND back!='TGESTIONA_APP' AND FLAG_DEPENDENCIA='PRINCIPAL' AND GESTION='CE'  THEN 1 ELSE 0 END) AS ventasOtras " +
			"FROM trafico " + 
			"WHERE " + 
			" FLAG_DEPENDENCIA='PRINCIPAL' AND GESTION='CE' " + 
			"GROUP BY  canal_venta_agrup")
	List<Adopcion> adopcion();
	
	@Select(" SELECT canal_venta_agrup as canalVentaAgrup, "+
			"IF ( back='TGESTIONA_APP' AND canal_venta_agrup='AGENCIAS' OR canal_venta_agrup='GRANDES SUPERFICIES' ,COUNT( DISTINCT COD_VENDEDOR_ATIS) , 0) AS vendApp, " + 
			"IF ( back='TGESTIONA_CROSS_WEB' ,COUNT( DISTINCT COD_VENDEDOR_ATIS) , 0) AS vendWeb, " +
			"IF ( back!='TGESTIONA_CROSS_WEB' AND back!='TGESTIONA_APP' ,COUNT( DISTINCT COD_VENDEDOR_ATIS) ,0) AS otros, " +
			" COUNT( DISTINCT COD_VENDEDOR_ATIS) AS total  "+
			" FROM trafico " + 
			" WHERE FLAG_DEPENDENCIA='PRINCIPAL' AND GESTION='CE' " + 
			" GROUP BY  canal_venta_agrup ")
	List<Despliegue> despliegue();
	
	@Select("SELECT ESTADO_GEST_1 AS estadoGestion, " + 
			"SUM(CASE WHEN back='TGESTIONA_APP' AND canal_venta_agrup='AGENCIAS' OR canal_venta_agrup='GRANDES SUPERFICIES'  THEN 1 ELSE 0 END) AS ventasApp , " + 
			"SUM(CASE WHEN back='TGESTIONA_CROSS_WEB' AND FLAG_DEPENDENCIA='PRINCIPAL' AND GESTION='CE'  THEN 1 ELSE 0 END) AS ventasWeb , " + 
			"SUM(CASE WHEN back!='TGESTIONA_CROSS_WEB' AND back!='TGESTIONA_APP' THEN 1 ELSE 0 END) AS otros " + 
			"FROM trafico " + 
			"WHERE FLAG_DEPENDENCIA='PRINCIPAL' AND GESTION='CE' " + 
			"AND ESTADO_GEST_1!='' " + 
			"GROUP BY ESTADO_GEST_1")
	List<Convertibilidad> convertibilidad();
	
	@Insert("INSERT INTO trafico(NOMBRE_ASESOR,COD_ASESOR_BACK) values(#{nombreAsesor},#{codAsesorBack})")
	@SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Integer.class)
	void insert(Trafico trafico);
}
