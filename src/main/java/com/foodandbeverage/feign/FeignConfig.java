package com.foodandbeverage.feign;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.okhttp.OkHttpClient;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
public class FeignConfig {
  @Bean
  public OkHttpClient okHttpClient() {
    return new OkHttpClient();
  }

  @Bean("foodAndBeverageObjectMapper")
  public ObjectMapper feignObjectMapper() {
    final ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    return objectMapper;
  }

  @Bean
  public SpringEncoder feignEncoder(
      @Qualifier("foodAndBeverageObjectMapper") ObjectMapper objectMapper) {
    HttpMessageConverter<Object> messageConverter =
        new MappingJackson2HttpMessageConverter(objectMapper);
    ObjectFactory<HttpMessageConverters> objectFactory =
        () -> new HttpMessageConverters(messageConverter);
    return new SpringEncoder(objectFactory);
  }
}
