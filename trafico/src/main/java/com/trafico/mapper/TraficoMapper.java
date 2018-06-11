package com.trafico.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.trafico.model.Trafico;

@Mapper
public interface TraficoMapper {
	
	@Select("select ID id, NOMBRE_ASESOR nombreAsesor, COD_ASESOR_BACK codAsesorBack from TRAFICO")
	List<Trafico> findAll();
	
	@Insert("INSERT INTO TRAFICO(NOMBRE_ASESOR,COD_ASESOR_BACK) values(#{nombreAsesor},#{codAsesorBack})")
	@SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Integer.class)
	void insert(Trafico trafico);
}
