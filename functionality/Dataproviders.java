package web.goomo.tests.flights.functionality;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.DataProvider;

public class Dataproviders {
	
	private Dataproviders(){
		
	}
	
	@DataProvider(name="domesticOW")
	public static Object[][] domesticOW() {
		List<String> airlinePreferedNameList = new ArrayList<>();
		airlinePreferedNameList.add("Air India");
		airlinePreferedNameList.add("JetAirways");
		
		Map<String,Object> mealBaggageData = new HashMap<>();
		mealBaggageData.put("mealFlag", true);
		mealBaggageData.put("mealNumber", "1");
		mealBaggageData.put("baggageFlag", true);
		mealBaggageData.put("baggageNumber", "1");
		
		List<String> onwardSectors = new ArrayList<>();
		onwardSectors.add("BLR");
		onwardSectors.add("DEL");
		List<String> returnSectors = new ArrayList<>();
		returnSectors.add("BBI");
		returnSectors.add("PNQ");
		
		
		return new Object[][] { { "oneway", onwardSectors,returnSectors, goomoDate(30), "Economy", "1", "1","1",airlinePreferedNameList,mealBaggageData} };
	}
	
	
	@DataProvider(name="domesticOWBook")
	public static Object[][] domesticOWBook() {
		List<String> airlinePreferedNameList = new ArrayList<>();
		airlinePreferedNameList.add("Air India");
		airlinePreferedNameList.add("JetAirways");
		
		Map<String,Object> mealBaggageData = new HashMap<>();
		mealBaggageData.put("mealFlag", true);
		mealBaggageData.put("mealNumber", "1");
		mealBaggageData.put("baggageFlag", true);
		mealBaggageData.put("baggageNumber", "1");
		
		List<String> onwardSectors = new ArrayList<>();
		onwardSectors.add("BLR");
		onwardSectors.add("BOM");
		List<String> returnSectors = new ArrayList<>();
		returnSectors.add("DEL");
		returnSectors.add("DEL");
		
		
		return new Object[][] { { "oneway", onwardSectors,returnSectors, goomoDate(30), "Economy", "1", "1","1",airlinePreferedNameList,mealBaggageData} };
	}
	

	

	@DataProvider(name="domesticRT")
	public static Object[][] domesticRT() {
		List<String> airlinePreferedNameList = new ArrayList<>();
		airlinePreferedNameList.add("Air India");
		Map<String,Object> mealBaggageData = new HashMap<>();
		mealBaggageData.put("mealFlagOngoing", true);
		mealBaggageData.put("mealNumberOngoing", "2");
		mealBaggageData.put("baggageFlagOngoing", true);
		mealBaggageData.put("baggageNumberOngoing", "2");
		mealBaggageData.put("mealFlagReturn", true);
		mealBaggageData.put("mealNumberReturn", "2");
		mealBaggageData.put("baggageFlagReturn", true);
		mealBaggageData.put("baggageNumberReturn", "2");
		
		
		List<String> onwardSectors = new ArrayList<>();
		onwardSectors.add("BLR");
		onwardSectors.add("BOM");
		List<String> returnSectors = new ArrayList<>();
		returnSectors.add("PNQ");
		returnSectors.add("GAU");

	
		

		
		return new Object[][] { { "roundtrip",onwardSectors, returnSectors, goomoDate(30),goomoDate(35), "Economy", "1", "1", "1",mealBaggageData,airlinePreferedNameList} };
	}
	
	
	@DataProvider(name="domesticRTBook")
	public static Object[][] domesticRTBook() {
		List<String> airlinePreferedNameList = new ArrayList<>();
		airlinePreferedNameList.add("Air India");
		Map<String,Object> mealBaggageData = new HashMap<>();
		mealBaggageData.put("mealFlagOngoing", true);
		mealBaggageData.put("mealNumberOngoing", "2");
		mealBaggageData.put("baggageFlagOngoing", true);
		mealBaggageData.put("baggageNumberOngoing", "2");
		mealBaggageData.put("mealFlagReturn", true);
		mealBaggageData.put("mealNumberReturn", "2");
		mealBaggageData.put("baggageFlagReturn", true);
		mealBaggageData.put("baggageNumberReturn", "2");
		
		
		List<String> onwardSectors = new ArrayList<>();
		onwardSectors.add("DEL");
		onwardSectors.add("BOM");
		List<String> returnSectors = new ArrayList<>();
		returnSectors.add("BOM");
		returnSectors.add("DEL");

	
		

		
		return new Object[][] { { "roundtrip",onwardSectors, returnSectors, goomoDate(30),goomoDate(35), "Economy", "1", "1", "1",mealBaggageData,airlinePreferedNameList} };
	}
	
	
	
	@DataProvider(name="internationalOW")
	public static Object[][] internationalOW() {
		List<String> airlinePreferedNameList = new ArrayList<>();
		airlinePreferedNameList.add("Air India");
		Map<String,Object> mealBaggageData = new HashMap<>();
		mealBaggageData.put("mealFlag", true);
		mealBaggageData.put("mealNumber", "2");
		mealBaggageData.put("baggageFlag", true);
		mealBaggageData.put("baggageNumber", "2");
		
		List<String> onwardSectors = new ArrayList<String>();
		onwardSectors.add("BLR");
		onwardSectors.add("DEL");
		List<String> returnSectors = new ArrayList<String>();
		returnSectors.add("DXB");
		returnSectors.add("DXB");
		
		
		
		return new Object[][] { { "oneway",onwardSectors,returnSectors, goomoDate(30), "Economy", "1", "1", "1",mealBaggageData} };
	}
	
	

	
	
	@DataProvider(name="internationalRT")
	public static Object[][] internationalRT() {
		
		Map<String,Object> mealBaggageData = new HashMap<>();
		mealBaggageData.put("mealFlagOngoing", true);
		mealBaggageData.put("mealNumberOngoing", "2");
		mealBaggageData.put("baggageFlagOngoing", true);
		mealBaggageData.put("baggageNumberOngoing", "2");
		mealBaggageData.put("mealFlagReturn", true);
		mealBaggageData.put("mealNumberReturn", "2");
		mealBaggageData.put("baggageFlagReturn", true);
		mealBaggageData.put("baggageNumberReturn", "2");
		
		List<String> onwardSectors = new ArrayList<>();
		onwardSectors.add("BOM");
		onwardSectors.add("DEL");
		List<String> returnSectors = new ArrayList<>();
		returnSectors.add("DXB");
		returnSectors.add("DXB");
		
		return new Object[][] { { "roundtrip",onwardSectors, returnSectors, goomoDate(30),goomoDate(35), "Economy", "1", "1", "1",mealBaggageData} };
	}
	
	@DataProvider(name="internationalRTF")
	public static Object[][] internationalRTF() {
		
		Map<String,Object> mealBaggageData = new HashMap<>();
		mealBaggageData.put("mealFlagOngoing", true);
		mealBaggageData.put("mealNumberOngoing", "2");
		mealBaggageData.put("baggageFlagOngoing", true);
		mealBaggageData.put("baggageNumberOngoing", "2");
		mealBaggageData.put("mealFlagReturn", true);
		mealBaggageData.put("mealNumberReturn", "2");
		mealBaggageData.put("baggageFlagReturn", true);
		mealBaggageData.put("baggageNumberReturn", "2");
		
		List<String> onwardSectors = new ArrayList<>();
		onwardSectors.add("BLR");
		onwardSectors.add("BOM");
		List<String> returnSectors = new ArrayList<>();
		returnSectors.add("DXB");
		returnSectors.add("DXB");
		
		return new Object[][] { { "roundtrip",onwardSectors, returnSectors, goomoDate(30),goomoDate(35), "Economy", "1", "1", "0",mealBaggageData} };
	}
	
	public static String goomoDate(int days){
		Calendar c = new GregorianCalendar();
		c.add(Calendar.DATE, +days);
		Date s = c.getTime();
	    return  new SimpleDateFormat("yyyy-MM-dd").format(s);
		
	}
	

}
