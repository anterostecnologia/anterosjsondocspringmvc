package br.com.anteros.jsondoc.springmvc.scanner;

import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

public class Spring4JSONDocScanner extends AbstractSpringJSONDocScanner {
	
	@Override
	public Set<Class<?>> jsondocControllers() {
		Set<Class<?>> jsondocControllers = reflections.getTypesAnnotatedWith(Controller.class, true);
		jsondocControllers.addAll(reflections.getTypesAnnotatedWith(RestController.class, true));
		return jsondocControllers;
	}
	
}
