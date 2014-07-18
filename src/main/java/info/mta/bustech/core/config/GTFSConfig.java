package info.mta.bustech.core.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
@Import(ConfigProperties.class)
public class GTFSConfig {

	@Value("${gtfs.path}") 
	private String GTFSDir;
    	
	@Value("${gtfs.csv}") 
	private String GTFSCSV;
	
	@Bean
    public String getPath() {
        return GTFSDir;
    }
	
	@Bean
    public String getCSVPath() {
        return GTFSCSV;
    }
	
	public String getRealPath(){
		
		Resource rsrc = new ClassPathResource("");
		String Path = "";
		 try {
			 Path = rsrc.getFile().getAbsolutePath();
	
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}
		return Path;
	}
}
