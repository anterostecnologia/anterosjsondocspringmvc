package br.com.anteros.jsondoc.springmvc.scanner;

import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.anteros.core.utils.ReflectionUtils;
import br.com.anteros.jsondoc.core.annotation.RestApiMethod;

public class Spring3JSONDocScanner extends AbstractSpringJSONDocScanner {
	
	@Override
	public Set<Class<?>> jsondocControllers() {
		Set<Class<?>> jsondocControllers = reflections.getTypesAnnotatedWith(Controller.class, true); 
		return jsondocControllers;
	}
	

	@Override
	public Set<Method> jsondocMethods(Class<?> controller) {
		Set<Method> annotatedMethods = new LinkedHashSet<Method>();
		Method[] allDeclaredMethods = ReflectionUtils.getAllDeclaredMethods(controller);
		for (Method method : allDeclaredMethods) {
			if(method.isAnnotationPresent(RestApiMethod.class) || method.isAnnotationPresent(RequestMapping.class)) {
				annotatedMethods.add(method);
			}
		}
		return annotatedMethods;
	}
	
}
