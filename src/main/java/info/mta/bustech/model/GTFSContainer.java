package info.mta.bustech.model;

import info.mta.bustech.core.config.GTFSConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.onebusaway.gtfs.impl.GtfsRelationalDaoImpl;
import org.onebusaway.gtfs.model.AgencyAndId;
import org.onebusaway.gtfs.serialization.GtfsReader;
import org.onebusaway.gtfs.services.GtfsMutableRelationalDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import au.com.bytecode.opencsv.CSVReader;

import com.conveyal.gtfs.service.CalendarDateVerificationService;
import com.conveyal.gtfs.service.impl.GtfsStatisticsService;


public class GTFSContainer {

	ApplicationContext ctx = new AnnotationConfigApplicationContext(GTFSConfig.class);
	private GTFSConfig Config = ctx.getBean(GTFSConfig.class);
	
	public GTFSContainer()
	{
		System.out.println("START");
	}
	
	public void Build(String Location) {
		GtfsMutableRelationalDao gtfsMDao = null;
		GtfsStatisticsService gtfsStats = null;
		CalendarDateVerificationService cdvs = null;
		
		GtfsReader reader = new GtfsReader();
		gtfsMDao = new GtfsRelationalDaoImpl();
        File gtfsFile = new File(Config.getPath()+Location);

        try {
			reader.setInputLocation(gtfsFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        try {
			reader.setInputLocation(gtfsFile);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        
    	reader.setEntityStore(gtfsMDao);
    	System.out.println(reader.getEntityStore());

    	try {
			reader.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	gtfsStats = new GtfsStatisticsService(gtfsMDao);
    	cdvs = new CalendarDateVerificationService(gtfsMDao);
    	
    	Calendar start = Calendar.getInstance();
    	start.setTime(gtfsStats.getCalendarServiceRangeStart());
    	Calendar end = Calendar.getInstance();
    	end.setTime(gtfsStats.getCalendarServiceRangeEnd());

    	for (Date date = start.getTime(); !start.after(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
    			
    			System.out.println(cdvs.getServiceIdsForDate().get(date));
    			for(AgencyAndId ServiceIDs: cdvs.getServiceIdsForDate().get(date))
    			{
    				System.out.println(cdvs.getTripCountsForAllServiceIDs().get(ServiceIDs));
    			}
    	}
    	
	}

	public void  GTFScsv(String File){
		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(Config.getPath()+File));

			String[] nextLine;
			try {
				
				nextLine = reader.readNext();
				for(String row : nextLine)
				{
					System.out.println(row);
				}
				
				while ((nextLine = reader.readNext()) != null) {
					if (nextLine != null) {
						
						for(String row : nextLine)
						{
							System.out.println(row);
						}
						
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
