package br.com.anteros.jsondoc.springmvc.scanner;

import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.jsondoc.core.util.JSONDocUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Sets;

import br.com.anteros.core.utils.ReflectionUtils;
import br.com.anteros.jsondoc.core.annotation.DocApiMethod;

public class Spring4JSONDocScanner extends AbstractSpringJSONDocScanner {

	@Override
	public Set<Class<?>> jsondocControllers() {
		Set<Class<?>> jsondocControllers = reflections.getTypesAnnotatedWith(Controller.class, true);
		jsondocControllers.addAll(reflections.getTypesAnnotatedWith(RestController.class, true));
		return jsondocControllers;
	}

	@Override
	public Set<Method> jsondocMethods(Class<?> controller) {
		Set<Method> annotatedMethods = new LinkedHashSet<Method>();
		Method[] allDeclaredMethods = ReflectionUtils.getAllDeclaredMethods(controller);
		for (Method method : allDeclaredMethods) {
			if (method.isAnnotationPresent(DocApiMethod.class) || method.isAnnotationPresent(RequestMapping.class)) {
				annotatedMethods.add(method);
			}
		}
		return annotatedMethods;
	}

	@Override
	public Set<Class<?>> jsondocObjects(List<String> packages) {
		Set<Class<?>> candidates = Sets.newHashSet();
		Set<Class<?>> subCandidates = Sets.newHashSet();
		Set<Class<?>> elected = Sets.newHashSet();
		for (Class<?> controller : jsondocControllers) {
			for (Method method : jsondocMethods(controller)) {
				buildJSONDocObjectsCandidates(candidates, method.getReturnType(), method.getGenericReturnType());
				Integer requestBodyParameterIndex = JSONDocUtils.getIndexOfParameterWithAnnotation(method,
						RequestBody.class);
				if (requestBodyParameterIndex != -1) {
					candidates.addAll(buildJSONDocObjectsCandidates(candidates,
							method.getParameterTypes()[requestBodyParameterIndex],
							method.getGenericParameterTypes()[requestBodyParameterIndex]));
				}
			}
		}

		// This is to get objects' fields that are not returned nor part of the
		// body request of a method, but that are a field
		// of an object returned or a body of a request of a method
		for (Class<?> clazz : candidates) {
			appendSubCandidates(clazz, subCandidates);
		}

		candidates.addAll(subCandidates);

		for (Class<?> clazz : candidates) {
			if (clazz.getPackage() != null) {
				for (String pkg : packages) {
					if (clazz.getPackage().getName().contains(pkg)) {
						elected.add(clazz);
					}
				}
			}
		}

		return elected;

	}

}
