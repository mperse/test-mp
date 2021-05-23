package databox.importer.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonObjectMapper extends ObjectMapper {
	private static final long serialVersionUID = 1L;

	public JsonObjectMapper() {
		super();
		this.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
		this.setVisibility(PropertyAccessor.FIELD, Visibility.NONE);
		this.setSerializationInclusion(Include.NON_NULL);
	}
}