package br.com.anteros.jsondoc.springmvc.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;

import br.com.anteros.jsondoc.springmvc.controller.JSONDocController;


public abstract class AnterosJSONDocConfiguration {
	
	public abstract String[] packagesSourceModelAndController();
	
	public abstract String versionApi();
	
	public abstract String basePath();

	@Bean
	public JSONDocController getDocController(){
		if (packagesSourceModelAndController()!=null){
		JSONDocController result = new JSONDocController(versionApi(), basePath(), Arrays.asList(packagesSourceModelAndController()));
		result.setPlaygroundEnabled(true);
		return result;
		}
		return null;
	}
	
}
