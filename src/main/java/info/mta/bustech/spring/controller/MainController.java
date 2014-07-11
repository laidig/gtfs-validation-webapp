package info.mta.bustech.spring.controller;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import info.mta.bustech.model.GTFSContainer;
import info.mta.bustech.model.GTFSFiles;











import org.json.JSONArray;
import org.json.JSONObject;
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

	@RequestMapping(value="graph",method = RequestMethod.POST)
	public String BarGraph(Model model ,HttpServletRequest request) {

		String[] paramValues = request.getParameterValues("GTFSs");
		
		ArrayList<JSONObject> Graph = new ArrayList<JSONObject>();
		
		for(String values: paramValues)
		{	
			Graph.add(new JSONObject(GTFS.GTFScsv(values)));
		}

		model.addAttribute("Files",paramValues);
		model.addAttribute("GraphJSON",Graph);
		return "bargraph";
	}

	@RequestMapping(value="build",method = RequestMethod.POST,headers="Accept=application/json")
	public String TripData(Model model,HttpServletRequest request) {
	
		String[] paramValues = request.getParameterValues("GTFSs");
		
		for(String values: paramValues)
		{	
			GTFS.Build(values);
			System.out.println("Building");
		}
		
		System.out.println("Completed");

		return null;
	}

}
