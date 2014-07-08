package info.mta.bustech.urlhelper;

import javax.servlet.http.HttpServletRequest;

public class asset {

	public static String JS(String path, HttpServletRequest request)
	{
		String ctx = request.getContextPath();
		return "<script language='javascript' type='text/javascript' src="+ctx+"/resources/assets/js/"+path+"></script>";
	}
	
	public static String CSS(String path, HttpServletRequest request)
	{
		String ctx = request.getContextPath();
		return "<link rel='stylesheet' type='text/css' href="+ctx+"/resources/assets/css/"+path+">";
	}
	
}
