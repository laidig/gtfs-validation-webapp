package info.mta.bustech.model;

import org.junit.Test;


import junit.framework.Assert;
import junit.framework.TestCase;

public class GTFSContainerTest extends TestCase {
	 
	static GTFSContainer GTFS = null;

	@Test
	public void test(){
		GTFS = new GTFSContainer();
		GTFS.Build("/gtfs_latest/GTFS_MTABC.zip");
		Assert.assertEquals(75,75);
	}
	
	
}
