package com.trafico;

import org.apache.ibatis.type.MappedTypes;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.trafico.model.Trafico;

@MappedTypes(Trafico.class)
@MapperScan("com.trafico.mapper")
@SpringBootApplication
public class TraficoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TraficoApplication.class, args);
	}
}
