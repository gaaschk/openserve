package org.gsoft.openserv.util.support.converter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class StringToHashMapConverter implements Converter<String, Map<String,Object>> {
	@Resource
 	private ObjectMapper objectMapper;
	
	@Override
	public Map<String, Object> convert(String source) {
		//convert JSON string to Map
		try {
			return objectMapper.readValue(source, new TypeReference<HashMap<String,Object>>(){});
		} catch (IOException e) {
			throw new RuntimeException("JSON Conversion failed");
		}
	}

}
