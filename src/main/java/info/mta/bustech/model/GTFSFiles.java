package info.mta.bustech.model;

import info.mta.bustech.core.config.GTFSConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import au.com.bytecode.opencsv.CSVReader;

public class GTFSFiles {
	ApplicationContext ctx = new AnnotationConfigApplicationContext(GTFSConfig.class);
	private GTFSConfig Config = ctx.getBean(GTFSConfig.class);

	public GTFSFiles() {
	}

	public HashMap<String,ArrayList<String>>  List(){
		ArrayList<String> CSVList = new ArrayList<String>();
		ArrayList<String> ZIPList = new ArrayList<String>();
		HashMap<String,ArrayList<String>> List = new HashMap<String,ArrayList<String>>();

		File folder = new File(Config.getPath());
		if (folder.exists()) {

			File[] listOfFiles = folder.listFiles();

			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					String filename = listOfFiles[i].getName();
					String extension = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
					if(extension.equals("zip"))
					{
						ZIPList.add(filename);
					}else if(extension.equals("csv"))
					{
						CSVList.add(filename);
					}
				} 
			}
		}
		List.put("zip", ZIPList);
		List.put("csv",CSVList);
		return List;
	}
}

