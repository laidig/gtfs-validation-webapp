package info.mta.bustech.model;

import info.mta.bustech.core.config.GTFSConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
	//	System.out.println(reader.getEntityStore());

		try {
			reader.run();
		} catch (IOException e) {
			e.printStackTrace();
		}

		gtfsStats = new GtfsStatisticsService(gtfsMDao);
		cdvs = new CalendarDateVerificationService(gtfsMDao);
		List<AgencyAndId> ServiceID = gtfsMDao.getAllServiceIds();
		HashMap<String,Integer> Header = new HashMap<String,Integer>();
		int index = 1;
		ArrayList<String> CSVData = new ArrayList<String>();
		CSVData.add("date");
		for(AgencyAndId IDs: ServiceID){
			
			String[] Depot = IDs.getId().split("_");
			String DepotTemp = "trips_";
			
			if(IDs.getAgencyId().equals("MTABC"))
			{
				Depot = Depot[0].split("-");
				DepotTemp = DepotTemp + Depot[1];
			}else{
				DepotTemp = DepotTemp + Depot[0];
			}
			

			if(!Header.containsKey(DepotTemp))
			{
				CSVData.add(DepotTemp);
				Header.put(DepotTemp, index);
				//System.out.println(DepotTemp);
				index++;
			}
			
		}
		
		Calendar start = Calendar.getInstance();
		start.setTime(gtfsStats.getCalendarServiceRangeStart());
		Calendar end = Calendar.getInstance();
		end.setTime(gtfsStats.getCalendarServiceRangeEnd());

		String[] CSVArray = new String[CSVData.size()];
		CSVArray = CSVData.toArray(CSVArray);
		
		ArrayList<String[]> CSVList = new ArrayList<String[]>();
		CSVList.add(CSVArray);
		for (Date date = start.getTime(); !start.after(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
			
			CSVArray = new String[CSVData.size()];
			Arrays.fill(CSVArray, "0");
			String DateFormated = new SimpleDateFormat("MM-dd").format(date);
			CSVArray[0] = DateFormated;
			
			for(AgencyAndId ServiceIDs: cdvs.getServiceIdsForDate().get(date))
			{
				String[] Depot = ServiceIDs.getId().split("_");
				String DepotTemp = "trips_";
				
				if(ServiceIDs.getAgencyId().equals("MTABC"))
				{
					Depot = Depot[0].split("-");
					DepotTemp = DepotTemp + Depot[1];
				}else{
					DepotTemp = DepotTemp + Depot[0];
				}
			//	System.out.println(DepotTemp);
				
				if(Header.containsKey(DepotTemp))
				{	
				
					int CurrentTrips  = 0;
					int DepotIndex = Header.get(DepotTemp);
					int Previous = Integer.parseInt(CSVArray[DepotIndex]);

					try{
						CurrentTrips  = cdvs.getTripCountsForAllServiceIDs().get(ServiceIDs);
					}catch (Exception e) {
						System.out.println("Date: "+CSVArray[0]+" No trips for service ID "+ServiceIDs);				
					}
					CSVArray[DepotIndex] = Integer.toString( Previous + CurrentTrips);
				}
				
			}
			
			CSVList.add(CSVArray);
		}

		generateCsvFile(Config.getRealPath()+Config.getCSVPath()+Location.replace("zip", "csv"), CSVList);
	}

	private static void generateCsvFile(String sFileName, ArrayList<String[]> CSVList)
	{
		try
		{
			
			File fileTemp = new File(sFileName);
			if (fileTemp.exists()){
			    fileTemp.delete();
			}
			FileWriter writer = new FileWriter(sFileName);
			
			for(String[] CSVData : CSVList)
			{
				int index = CSVData.length-1;
				for(String value : CSVData)
				{
					writer.append(value);
					if(index == 0)
						writer.append('\n');
					else
						writer.append(',');
					
					index--;
				}
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
			reader = new CSVReader(new FileReader(Config.getRealPath()+Config.getCSVPath()+File));

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
