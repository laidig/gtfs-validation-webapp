package info.mta.bustech.core.config;

import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
 
/**
 * Loads properties from a file called 
 * or default.properties if APP_ENV is not set.
 */
@Configuration
@PropertySource("classpath:config.properties")
public class ConfigProperties {
 
  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }
}