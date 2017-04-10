package cn.blmdz.hunt.webc.converter;

import java.io.IOException;
import java.nio.charset.Charset;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.common.base.Charsets;

import cn.blmdz.hunt.common.util.JsonMapper;
import lombok.Getter;
import lombok.Setter;

public class JsonMessageConverter extends AbstractHttpMessageConverter<Object> {
	public static final Charset DEFAULT_CHARSET = Charsets.UTF_8;
	@Getter
	private ObjectMapper objectMapper = JsonMapper.nonEmptyMapper().getMapper();
	@Setter
	private boolean prefixJson = false;

	public JsonMessageConverter() {
		super(new MediaType[] {
				new MediaType("application", "json", DEFAULT_CHARSET),
				new MediaType("application", "javascript", DEFAULT_CHARSET) });
	}

	public void setObjectMapper(ObjectMapper objectMapper) {
		Assert.notNull(objectMapper, "ObjectMapper must not be null");
		this.objectMapper = objectMapper;
	}

	public boolean canRead(Class<?> clazz, MediaType mediaType) {
		JavaType javaType = getJavaType(clazz);
		return objectMapper.canDeserialize(javaType) && canRead(mediaType);
	}

	public boolean canWrite(Class<?> clazz, MediaType mediaType) {
		return objectMapper.canSerialize(clazz) && canWrite(mediaType);
	}

	protected boolean supports(Class<?> clazz) {
		throw new UnsupportedOperationException();
	}

	protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {
		JavaType javaType = getJavaType(clazz);

		try {
			return objectMapper.readValue(inputMessage.getBody(), javaType);
		} catch (IOException e) {
			throw new HttpMessageNotReadableException("Could not read JSON: " + e.getMessage(), e);
		}
	}

	protected void writeInternal(Object object, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		JsonEncoding encoding = getJsonEncoding(outputMessage.getHeaders().getContentType());
		JsonGenerator jsonGenerator = objectMapper.getFactory()
				.createGenerator(outputMessage.getBody(), encoding);

		try {
			if (prefixJson) {
				jsonGenerator.writeRaw("{} && ");
			}

			Object result;
			if (object instanceof JSONPObject) {
				JSONPObject jsonP = (JSONPObject) object;
				result = new JSONPObject(jsonP.getFunction(), jsonP.getValue());
			} else {
				result = object;
			}

			objectMapper.writeValue(jsonGenerator, result);
		} catch (IOException e) {
			throw new HttpMessageNotWritableException("Could not write JSON: " + e.getMessage(), e);
		}
	}

	protected JavaType getJavaType(Class<?> clazz) {
		return objectMapper.constructType(clazz);
	}

	protected JsonEncoding getJsonEncoding(MediaType contentType) {
		if (contentType != null && contentType.getCharSet() != null) {
			Charset charset = contentType.getCharSet();

			for (JsonEncoding encoding : JsonEncoding.values()) {
				if (charset.name().equals(encoding.getJavaName()))
					return encoding;
			}
		}

		return JsonEncoding.UTF8;
	}
}
