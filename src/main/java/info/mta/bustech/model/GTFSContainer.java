package info.mta.bustech.model;

import info.mta.bustech.core.config.GTFSConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.onebusaway.gtfs.impl.GtfsRelationalDaoImpl;
import org.onebusaway.gtfs.model.AgencyAndId;
import org.onebusaway.gtfs.serialization.GtfsReader;
import org.onebusaway.gtfs.services.GtfsMutableRelationalDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import com.conveyal.gtfs.service.CalendarDateVerificationService;
import com.conveyal.gtfs.service.impl.GtfsStatisticsService;


public class GTFSContainer {

	ApplicationContext ctx = new AnnotationConfigApplicationContext(GTFSConfig.class);
	private GTFSConfig Config = ctx.getBean(GTFSConfig.class);

	public GTFSContainer(){

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

	private static void generateCsvFile(String sFileName, String[] Data)
	{
		try
		{
			FileWriter writer = new FileWriter(sFileName);

			int index = Data.length-1;
			for(String value : Data)
			{
				writer.append(value);
				if(index == 0)
					writer.append('\n');
				else
					writer.append(',');
			}

			//generate whatever data you want

			writer.flush();
			writer.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		} 
	}

	@SuppressWarnings("rawtypes")
	public HashMap<String,ArrayList>  GTFScsv(String File){
		CSVReader reader = null;

		HashMap<String,ArrayList> Trips = new HashMap<String,ArrayList>();
		TwoDimentionalArrayList<Integer> TripCount = new TwoDimentionalArrayList<Integer>();
		try {
			reader = new CSVReader(new FileReader(Config.getPath()+File));

			String[] nextLine;
			try {
				ArrayList<String> Depot = new ArrayList<String>();
				ArrayList<String> Dates = new ArrayList<String>();

				int index = 0;
				nextLine = reader.readNext();
				for(String row : nextLine)
				{
					if(index != 0)
					{
						Depot.add(row.replace("trips_", ""));

					}
					index++;
				}
				Trips.put("Depots",Depot);

				while ((nextLine = reader.readNext()) != null) {
					if (nextLine != null) {

						index = 0;
						for(String row : nextLine)
						{
							if(index != 0)
							{
								TripCount.addToInnerArray(index-1, Integer.parseInt(row));
							}else{
								Dates.add(row);
							}
							index++;
						}

					}
					Trips.put("Dates",Dates);
					Trips.put("TripCount",TripCount);
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
		return Trips;
	}

}

class TwoDimentionalArrayList<T> extends ArrayList<ArrayList<T>> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void addToInnerArray(int index, T element) {
		while (index >= this.size()) {
			this.add(new ArrayList<T>());
		}
		this.get(index).add(element);
	}

	public void addToInnerArray(int index, int index2, T element) {
		while (index >= this.size()) {
			this.add(new ArrayList<T>());
		}

		ArrayList<T> inner = this.get(index);
		while (index2 >= inner.size()) {
			inner.add(null);
		}

		inner.set(index2, element);
	}
}
