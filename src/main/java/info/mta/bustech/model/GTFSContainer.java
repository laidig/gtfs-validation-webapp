package info.mta.bustech.model;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.onebusaway.gtfs.impl.GtfsDaoImpl;
import org.onebusaway.gtfs.impl.GtfsRelationalDaoImpl;
import org.onebusaway.gtfs.model.AgencyAndId;
import org.onebusaway.gtfs.serialization.GtfsReader;
import org.onebusaway.gtfs.services.GtfsMutableRelationalDao;

import com.conveyal.gtfs.service.CalendarDateVerificationService;
import com.conveyal.gtfs.service.impl.GtfsStatisticsService;


public class GTFSContainer {

	
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
		System.out.println("location");
        File gtfsFile = new File(Location);
	//	File gtfsFile = new File("GTFS_MTABC.OLD.zip");
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

}
