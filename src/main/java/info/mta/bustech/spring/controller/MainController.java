package info.mta.bustech.spring.controller;

import info.mta.bustech.model.GTFSContainer;
import info.mta.bustech.model.GTFSFiles;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class MainController { 
	
	@RequestMapping(value="",method = RequestMethod.GET)
    public String Main(Model model) {
      	model.addAttribute("GTFSList",new GTFSFiles().List());
        return "main";
    }
    
	@RequestMapping(value="graph",method = RequestMethod.GET)
    public String BarGraph(Model model) {
        return "bargraph";
    }
    
	@RequestMapping(value="tripData",method = RequestMethod.POST,headers="Accept=application/json")
    public String TripData(Model model) {
    	System.out.println("Testing");
    	GTFSContainer GTFS = new GTFSContainer();
    	System.out.println("Testing2");
		GTFS.Build("/gtfs_latest/google_transit_brooklyn.zip");
    	System.out.println("Testing3");
//      	model.addAttribute("GTFSList",new GTFSFiles().List());
        return "bargraph";
    }

}
