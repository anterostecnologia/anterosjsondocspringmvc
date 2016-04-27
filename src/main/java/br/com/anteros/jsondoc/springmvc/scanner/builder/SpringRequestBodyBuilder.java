package br.com.anteros.jsondoc.springmvc.scanner.builder;

import java.lang.reflect.Method;

import org.jsondoc.core.util.JSONDocType;
import org.jsondoc.core.util.JSONDocTypeBuilder;
import org.jsondoc.core.util.JSONDocUtils;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.anteros.jsondoc.core.pojo.ApiBodyObjectDoc;

public class SpringRequestBodyBuilder {

	public static ApiBodyObjectDoc buildRequestBody(Method method) {
		Integer index = JSONDocUtils.getIndexOfParameterWithAnnotation(method, RequestBody.class);
		if (index != -1) {
			ApiBodyObjectDoc apiBodyObjectDoc = new ApiBodyObjectDoc(JSONDocTypeBuilder.build(new JSONDocType(), method.getParameterTypes()[index], method.getGenericParameterTypes()[index]));
			return apiBodyObjectDoc;
		}
		
		return null;
	}
	
}
