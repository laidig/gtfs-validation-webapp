package info.mta.bustech.core.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(ConfigProperties.class)
public class GTFSConfig {

	@Value("${gtfs.path}") 
	private String GTFSDir;
    
	@Bean
    public String getPath() {
        return GTFSDir;
    }
	
}
