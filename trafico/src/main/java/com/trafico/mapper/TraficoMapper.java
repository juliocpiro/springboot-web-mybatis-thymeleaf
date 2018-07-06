package com.trafico.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.trafico.model.Adopcion;
import com.trafico.model.Convertibilidad;
import com.trafico.model.ConvertibilidadRegistro;
import com.trafico.model.Despliegue;
import com.trafico.model.EvolutivoApp;
import com.trafico.model.EvolutivoTotal;
import com.trafico.model.EvolutivoWeb;
import com.trafico.model.Trafico;
import com.trafico.model.Usabilidad;

@Mapper
public interface TraficoMapper {
	
	@Select("select * from trafico")
	List<Trafico> findAll();
	
	@Select("SELECT CONCAT (YEAR(t.fecha_llamada),'-' ,MONTH(t.fecha_llamada)) AS fechaLlamada ,t.canal_venta_agrup as canalVentaAgrup," + 
			"SUM(CASE WHEN t.back='TGESTIONA_APP' and canal_venta_agrup='AGENCIAS' OR canal_venta_agrup='GRANDES SUPERFICIES'  THEN 1 ELSE 0 END) AS app," + 
			"SUM(CASE WHEN t.back='TGESTIONA_CROSS_WEB' THEN 1 ELSE 0 END) AS web," + 
			"SUM(CASE WHEN t.back!='TGESTIONA_CROSS_WEB' AND back!='TGESTIONA_APP' THEN 1 ELSE 0 END) AS otros," + 
			"ROUND((SUM(CASE WHEN t.back='TGESTIONA_APP' THEN 1 ELSE 0 END) *100/ COUNT(1)) ,2) AS usabilidadApp," + 
			"ROUND((SUM(CASE WHEN t.back='TGESTIONA_CROSS_WEB' THEN 1 ELSE 0 END)*100 / COUNT(1)),2) AS usabilidadWeb," + 
			"ROUND(COUNT(1),2) AS total " + 
			"FROM bd_trafico.trafico t " + 
			"WHERE  t.FLAG_DEPENDENCIA='PRINCIPAL' AND t.GESTION='CE' " + 
			"AND YEAR(t.fecha_llamada)!=0 " + 
			"GROUP BY YEAR(t.fecha_llamada), MONTH(t.fecha_llamada), t.canal_venta_agrup ")
	List<Usabilidad> usabilidad();
	
	@Select("SELECT t.canal_venta_agrup as canalVentaAgrup, "+
			"(SELECT COUNT(DISTINCT t1.COD_VENDEDOR_ATIS) FROM trafico t1 " + 
			"	WHERE t1.back='TGESTIONA_APP' AND t1.canal_venta_agrup=t.canal_venta_agrup AND MONTH(t1.FECHA_LLAMADA)=#{mes} AND " + 
			"	t1.FLAG_DEPENDENCIA='PRINCIPAL' AND t1.GESTION='CE' " + 
			"	) AS totalVend, " +
			"(SELECT COUNT(DISTINCT t2.COD_VENDEDOR_ATIS) FROM trafico t2 " + 
			"	WHERE t2.back='TGESTIONA_CROSS_WEB' AND t2.canal_venta_agrup=t.canal_venta_agrup AND MONTH(t2.FECHA_LLAMADA)=#{mes} AND " + 
			"	t2.FLAG_DEPENDENCIA='PRINCIPAL' AND t2.GESTION='CE' " + 
			"	) AS vendWeb, " + 
			"SUM(CASE WHEN t.back='TGESTIONA_APP' AND t.canal_venta_agrup='AGENCIAS' OR t.canal_venta_agrup='GRANDES SUPERFICIES'  THEN 1 ELSE 0 END) AS ventasApp , " + 
			"SUM(CASE WHEN t.back='TGESTIONA_CROSS_WEB' AND t.FLAG_DEPENDENCIA='PRINCIPAL' AND t.GESTION='CE'  THEN 1 ELSE 0 END) AS ventasWeb ," +
			"SUM(CASE WHEN t.back!='TGESTIONA_CROSS_WEB' AND t.back!='TGESTIONA_APP' AND t.FLAG_DEPENDENCIA='PRINCIPAL' AND t.GESTION='CE'  THEN 1 ELSE 0 END) AS ventasOtras " +
			"FROM trafico t " + 
			"WHERE " + 
			" t.FLAG_DEPENDENCIA='PRINCIPAL' AND t.GESTION='CE' " + 
			" AND MONTH(t.FECHA_LLAMADA)=#{mes} " + 
			"GROUP BY  t.canal_venta_agrup")
	List<Adopcion> adopcion(Integer mes);
	
	@Select(" SELECT canal_venta_agrup AS canalVentaAgrup," + 
			"(SELECT COUNT(DISTINCT t1.COD_VENDEDOR_ATIS) FROM trafico t1 " + 
			"	WHERE t1.back='TGESTIONA_APP' AND t1.canal_venta_agrup=t.canal_venta_agrup AND MONTH(t1.FECHA_LLAMADA)=#{mes} AND " + 
			"	t1.FLAG_DEPENDENCIA='PRINCIPAL' AND t1.GESTION='CE' " + 
			"	) AS vendApp, " + 
			"(SELECT COUNT(DISTINCT t2.COD_VENDEDOR_ATIS) FROM trafico t2 " + 
			"	WHERE t2.back='TGESTIONA_CROSS_WEB' AND t2.canal_venta_agrup=t.canal_venta_agrup AND MONTH(t2.FECHA_LLAMADA)=#{mes} AND " + 
			"	t2.FLAG_DEPENDENCIA='PRINCIPAL' AND t2.GESTION='CE' " + 
			"	) AS vendWeb, " + 
			"(SELECT COUNT(DISTINCT t3.COD_VENDEDOR_ATIS) FROM trafico t3 " + 
			"	WHERE t3.back!='TGESTIONA_CROSS_WEB' AND t3.back!='TGESTIONA_APP' AND t3.canal_venta_agrup=t.canal_venta_agrup AND MONTH(t3.FECHA_LLAMADA)=#{mes} AND " + 
			"	t3.FLAG_DEPENDENCIA='PRINCIPAL' AND t3.GESTION='CE' " + 
			"	) AS otros " + 
			"FROM trafico t " + 
			"WHERE t.FLAG_DEPENDENCIA='PRINCIPAL' AND t.GESTION='CE' " + 
			" AND MONTH(t.FECHA_LLAMADA)=#{mes} " + 
			"GROUP BY t.canal_venta_agrup  ")
	List<Despliegue> despliegue(Integer mes);
	
	@Select("SELECT ESTADO_GEST_1 AS estadoGestion, " + 
			"SUM(CASE WHEN back='TGESTIONA_APP' AND canal_venta_agrup='AGENCIAS' OR canal_venta_agrup='GRANDES SUPERFICIES'  THEN 1 ELSE 0 END) AS ventasApp , " + 
			"SUM(CASE WHEN back='TGESTIONA_CROSS_WEB' AND FLAG_DEPENDENCIA='PRINCIPAL' AND GESTION='CE'  THEN 1 ELSE 0 END) AS ventasWeb , " + 
			"SUM(CASE WHEN back!='TGESTIONA_CROSS_WEB' AND back!='TGESTIONA_APP' THEN 1 ELSE 0 END) AS otros " + 
			"FROM trafico " + 
			"WHERE FLAG_DEPENDENCIA='PRINCIPAL' AND GESTION='CE' " + 
			" AND MONTH(FECHA_LLAMADA)=#{mes} " + 
			"AND ESTADO_GEST_1!='' " + 
			"GROUP BY ESTADO_GEST_1")
	List<Convertibilidad> convertibilidad(Integer mes);
	
	@Select("SELECT " + 
			"SUM(CASE WHEN back='TGESTIONA_APP' AND canal_venta_agrup='AGENCIAS' OR canal_venta_agrup='GRANDES SUPERFICIES'  THEN 1 ELSE 0 END) AS ventasApp," + 
			"SUM(CASE WHEN ESTADO_GEST_1='CAIDA' AND back='TGESTIONA_APP' AND canal_venta_agrup='AGENCIAS' OR canal_venta_agrup='GRANDES SUPERFICIES'  THEN 1 ELSE 0 END) AS caidasApp," + 
			"SUM(CASE WHEN back!='TGESTIONA_CROSS_WEB' AND back!='TGESTIONA_APP' AND FLAG_DEPENDENCIA='PRINCIPAL' AND GESTION='CE'  THEN 1 ELSE 0 END) ventasOtras," + 
			"SUM(CASE WHEN ESTADO_GEST_1='CAIDA'  AND back!='TGESTIONA_APP'  THEN 1 ELSE 0 END) AS caidasOtras " + 
			"FROM trafico " + 
			"WHERE FLAG_DEPENDENCIA='PRINCIPAL' AND GESTION='CE' " +  
			" AND MONTH(FECHA_LLAMADA)=#{mes} " + 
			"AND ESTADO_GEST_1!='' ")
	List<ConvertibilidadRegistro> convertibilidadRegistro(Integer mes);
	
	@Select("SELECT MONTH(t.fecha_Llamada) AS mes," + 
			"COUNT(1) AS usabilidad," + 
			"(SELECT COUNT(DISTINCT t1.COD_VENDEDOR_ATIS) FROM trafico t1 " + 
			"WHERE  MONTH(t1.FECHA_LLAMADA)=MONTH(t.FECHA_LLAMADA) AND " + 
			"t1.FLAG_DEPENDENCIA='PRINCIPAL' AND t1.GESTION='CE' " + 
			")  AS despliegue," + 
			"SUM(CASE WHEN t.back='TGESTIONA_APP' AND t.canal_venta_agrup='AGENCIAS' OR t.canal_venta_agrup='GRANDES SUPERFICIES'  THEN 1 ELSE 0 END) AS ventasApp," + 
			"SUM(CASE WHEN t.back!='TGESTIONA_APP' AND t.FLAG_DEPENDENCIA='PRINCIPAL' AND t.GESTION='CE'  THEN 1 ELSE 0 END) AS ventasOtras	" + 
			"FROM trafico t " + 
			"WHERE t.FLAG_DEPENDENCIA='PRINCIPAL' AND t.GESTION='CE' " + 
			"AND MONTH(t.fecha_Llamada)!=0 " + 
			"GROUP BY  MONTH(t.fecha_Llamada) ")
	List<EvolutivoTotal> evolutivoTotal(); 
	
	@Select("SELECT MONTH(t.fecha_Llamada) AS mes," + 
			"SUM(CASE WHEN t.back='TGESTIONA_APP' AND t.canal_venta_agrup='AGENCIAS' OR t.canal_venta_agrup='GRANDES SUPERFICIES'  THEN 1 ELSE 0 END) app," + 
			"SUM(CASE WHEN t.back!='TGESTIONA_APP' THEN 1 ELSE 0 END) otros," + 
			"(SELECT COUNT(DISTINCT t1.COD_VENDEDOR_ATIS) FROM trafico t1 " + 
			"WHERE t1.back='TGESTIONA_APP' AND MONTH(t1.FECHA_LLAMADA)=MONTH(t.FECHA_LLAMADA) AND " + 
			"t1.FLAG_DEPENDENCIA='PRINCIPAL' AND t1.GESTION='CE' " + 
			") AS vendApp," + 
			"(SELECT COUNT(DISTINCT t1.COD_VENDEDOR_ATIS) FROM trafico t1 " + 
			"WHERE  t1.back!='TGESTIONA_APP' AND  MONTH(t1.FECHA_LLAMADA)=MONTH(t.FECHA_LLAMADA) AND " + 
			"t1.FLAG_DEPENDENCIA='PRINCIPAL' AND t1.GESTION='CE' " + 
			")  AS vendOtros," + 
			"SUM(CASE WHEN t.back='TGESTIONA_APP' AND t.canal_venta_agrup='AGENCIAS' OR t.canal_venta_agrup='GRANDES SUPERFICIES'  THEN 1 ELSE 0 END) AS ventasApp," + 
			"SUM(CASE WHEN t.back!='TGESTIONA_APP' AND t.FLAG_DEPENDENCIA='PRINCIPAL' AND t.GESTION='CE'  THEN 1 ELSE 0 END) AS ventasOtras " + 
			"FROM trafico t " + 
			"WHERE t.FLAG_DEPENDENCIA='PRINCIPAL' AND t.GESTION='CE' " + 
			"GROUP BY  MONTH(t.fecha_Llamada)")
	List<EvolutivoApp> evolutivoApp();
	
	@Select("SELECT MONTH(t.fecha_Llamada) AS mes," + 
			"SUM(CASE WHEN t.back='TGESTIONA_CROSS_WEB' THEN 1 ELSE 0 END) web," + 
			"SUM(CASE WHEN t.back!='TGESTIONA_CROSS_WEB' THEN 1 ELSE 0 END) otros," + 
			"(SELECT COUNT(DISTINCT t1.COD_VENDEDOR_ATIS) FROM trafico t1 " + 
			"WHERE t1.back='TGESTIONA_CROSS_WEB' AND MONTH(t1.FECHA_LLAMADA)=MONTH(t.FECHA_LLAMADA) AND " + 
			"t1.FLAG_DEPENDENCIA='PRINCIPAL' AND t1.GESTION='CE' " + 
			") AS vendWeb," + 
			"(SELECT COUNT(DISTINCT t1.COD_VENDEDOR_ATIS) FROM trafico t1 " + 
			"WHERE  t1.back!='TGESTIONA_APP' AND  MONTH(t1.FECHA_LLAMADA)=MONTH(t.FECHA_LLAMADA) AND " + 
			"t1.FLAG_DEPENDENCIA='PRINCIPAL' AND t1.GESTION='CE' " + 
			")  AS vendOtros," + 
			"SUM(CASE WHEN t.back='TGESTIONA_CROSS_WEB' AND t.FLAG_DEPENDENCIA='PRINCIPAL' AND t.GESTION='CE' THEN 1 ELSE 0 END) AS ventasWeb," + 
			"SUM(CASE WHEN t.back!='TGESTIONA_CROSS_WEB' AND t.FLAG_DEPENDENCIA='PRINCIPAL' AND t.GESTION='CE'  THEN 1 ELSE 0 END) AS ventasOtras " + 
			"FROM trafico t " + 
			"WHERE t.FLAG_DEPENDENCIA='PRINCIPAL' AND t.GESTION='CE' " + 
			"GROUP BY  MONTH(t.fecha_Llamada)")
	List<EvolutivoWeb> evolutivoWeb();
	
//	@Insert("INSERT INTO trafico(NOMBRE_ASESOR,COD_ASESOR_BACK) values(#{nombreAsesor},#{codAsesorBack})")
//	@SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Integer.class)
//	void insert(Trafico trafico);
}
