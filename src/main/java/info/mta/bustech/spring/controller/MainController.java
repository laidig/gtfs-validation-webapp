package info.mta.bustech.spring.controller;

import info.mta.bustech.model.GTFSContainer;
import info.mta.bustech.model.GTFSFiles;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController { 
	
    @RequestMapping("/")
    public String Main(Model model) {
      	model.addAttribute("GTFSList",new GTFSFiles().List());
        return "main";
    }
    
    @RequestMapping("/graph")
    public String BarGraph(Model model) {
//    	System.out.println("Testing");
//    	GTFSContainer GTFS = new GTFSContainer();
//    	System.out.println("Testing2");
//		GTFS.Build("/gtfs_latest/GTFS_MTABC.zip");
//    	System.out.println("Testing3");
//      	model.addAttribute("GTFSList",new GTFSFiles().List());
        return "bargraph";
    }

}
