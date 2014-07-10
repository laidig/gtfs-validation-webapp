package info.mta.bustech.spring.controller;

import java.util.ArrayList;
import java.util.HashMap;

import info.mta.bustech.model.GTFSContainer;
import info.mta.bustech.model.GTFSFiles;




import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class MainController { 
	GTFSContainer GTFS = new GTFSContainer();
	@RequestMapping(value="",method = RequestMethod.GET)
    public String Main(Model model) {
		HashMap<String,ArrayList<String>> List = new GTFSFiles().List();
	
		model.addAttribute("GTFSzip",List.get("zip"));
      	model.addAttribute("GTFScsv",List.get("csv"));
        return "main";
    }
    
	@RequestMapping(value="graph",method = RequestMethod.GET)
    public String BarGraph(Model model) {
		GTFS.GTFScsv("google_transit_brooklyn.csv");
        return "bargraph";
    }

	@RequestMapping(value="build",method = RequestMethod.POST,headers="Accept=application/json")
    public String TripData(Model model) {
    	System.out.println("Testing");
    	
    	System.out.println("Testing2");
		GTFS.Build("google_transit_brooklyn.zip");
    	System.out.println("Testing3");
//      	model.addAttribute("GTFSList",new GTFSFiles().List());
        return "bargraph";
    }

}
