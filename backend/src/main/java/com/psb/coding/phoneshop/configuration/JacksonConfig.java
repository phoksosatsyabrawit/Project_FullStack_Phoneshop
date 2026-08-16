package com.psb.coding.phoneshop.configuration;

//@Configuration
/*
public class JacksonConfig implements WebMvcConfigurer {

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm[:ss]");

	@Bean
	@Primary
	public ObjectMapper mapper() {
		JavaTimeModule module = new JavaTimeModule();
		module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(formatter));
		module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));
		return new ObjectMapper()
				.registerModule(module)
				.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
				.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}
	
	@SuppressWarnings("removal")
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(new MappingJackson2HttpMessageConverter(mapper()));
	}
}*/
