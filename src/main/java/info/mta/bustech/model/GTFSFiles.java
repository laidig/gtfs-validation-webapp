package info.mta.bustech.model;

import info.mta.bustech.core.config.GTFSConfig;

import java.io.File;
import java.util.ArrayList;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class GTFSFiles {
	ApplicationContext ctx = new AnnotationConfigApplicationContext(GTFSConfig.class);
	private GTFSConfig Config = ctx.getBean(GTFSConfig.class);
	
	public GTFSFiles() {
	}

	public ArrayList<String> List(){
		ArrayList<String> List = new ArrayList<String>();
		File folder = new File(Config.getPath());
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				String filename = listOfFiles[i].getName();
				String extension = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
				if(extension.equals("zip"))
				{
					List.add(filename);
				}
			} 
		}
		return List;
	}
}
