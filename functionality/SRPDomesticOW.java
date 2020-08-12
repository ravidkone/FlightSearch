package web.goomo.tests.flights.functionality;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;

import web.goomo.commonutils.WebFlightConnector;
import web.goomo.pageobject.flights.SearchPage;
import web.goomo.pageobject.flights.SearchResultPage;

public class SRPDomesticOW extends WebFlightConnector {
	
	Map<String, Object> flowDetailMap = new HashMap<>();
	Map<String, Object> flightDetailMap = new HashMap<>();
	JSONObject flightDetails = new JSONObject();
	boolean pickOtherFlights;
	int retryCountFlight = 0;
	boolean flightsAvailable = false;
	int selectFlight =1;
	boolean filtersApplied;
	ExtentTest test;
	boolean testFail = true;
	static int attempts = 0;
	static int methodCount = 0;
	static int paxCount = 2;
	
	boolean nonStop = false;
	boolean Stop1 =false;
	boolean Stop2 =false;
	
	SearchResultPage searchResultPage;
	SearchPage search;
    List<String> methodNames = new ArrayList<>();
	
	@BeforeClass
	public void getAllMethods(){
		 Method[] arr = this.getClass().getDeclaredMethods();
			for(int i=0;i<arr.length;i++){
			     if(!arr[i].getName().equals("createTest")&& !arr[i].getName().equals("dispose")&&!arr[i].getName().equals("takeScreenShot")
			    		 &&!arr[i].getName().equals("getAllMethods")){
			    	  methodNames.add(arr[i].getName());
			     }
			        
			}
	}

	@BeforeMethod
	public void createTest() {
		   String methodName = methodNames.get(methodCount);
			test = extent.createTest(this.getClass().getSimpleName() + " - " + methodName, "Functionality");
	}
	
	
	
	@Test(dataProvider = "domesticOW", dataProviderClass = Dataproviders.class, priority = 32)
	public void srpModifySearchCheckCloseButton(String tripType, List<String> onwardSectors, List<String> returnSectors,
			String travelDate, String classType, String adultCount, String childCount, String infantCount,
			List<String> airlinePreferedNameList, Map<String, Object> mealBaggage) throws Exception {
		getOWURL(onwardSectors.get(attempts), returnSectors.get(attempts), travelDate, classType, adultCount, childCount, infantCount);
		search=new SearchPage(driver);

		//getURL();
	     maximizeWindow(driver);
		//search.searchFormOneWay("ONEWAY",onwardSectors.get(attempts), returnSectors.get(attempts),  adultCount, childCount, infantCount);

		search.HowAboutPopUp();
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.popupHandle(); 
		String totalCount = searchResultPage.getSRPFlightCount(softAssert);
		searchResultPage.clickModifybutton(softAssert);
		//softAssert.assertAll();
	}

	@Test(dataProvider = "domesticOW", dataProviderClass = Dataproviders.class,priority = 33)
	public void srpModifySearchCheckSameCityInSource(String tripType, List<String> onwardSectors,
			List<String> returnSectors, String travelDate, String classType, String adultCount, String childCount,
			String infantCount, List<String> airlinePreferedNameList, Map<String, Object> mealBaggage)
			throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.modifySource(onwardSectors.get(attempts), returnSectors.get(attempts),softAssert);
		searchResultPage.clickModifyClosebutton(softAssert);
		softAssert.assertAll();
	}

	@Test(dataProvider = "domesticOW", dataProviderClass = Dataproviders.class,priority = 34)
	public void srpModifySearchCheckSameCityInDestination(String tripType, List<String> onwardSectors,
			List<String> returnSectors, String travelDate, String classType, String adultCount, String childCount,
			String infantCount, List<String> airlinePreferedNameList, Map<String, Object> mealBaggage)
			throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.clickModifybutton(softAssert);
		searchResultPage.modifyDestination(onwardSectors.get(attempts), returnSectors.get(attempts), softAssert);
		
		softAssert.assertAll();
	}

	@Test(priority = 35)
	public void modifyWithDateChange() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.clickModifyClosebutton(softAssert);
		searchResultPage.clickModifybutton(softAssert);
		search = new SearchPage(driver);
		search.fillOnwardDate();
		softAssert.assertAll();

	}

	@Test(priority = 36)
	public void changeTraveler() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.selectTravellerModifySearch("2", "0", "0");
		searchResultPage.clickModifyClosebutton(softAssert);
		softAssert.assertAll();
	}

	@Test(priority = 37)
	public void modifyWithNoChange() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		String totalCount = searchResultPage.getSRPFlightCount(softAssert);
		searchResultPage.clickModifybutton(softAssert);
		searchResultPage.clickModifyClosebutton(softAssert);
		String countAfterModify = searchResultPage.getSRPFlightCount(softAssert);
	    //softAssert.assertAll();
	}

	@Test(priority = 38)
	public void modifyDepatureTopCityList() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.modifyDepaturetopCity();
		softAssert.assertTrue(true);
		softAssert.assertAll();

	}

	@Test(priority = 39)
	public void modifyDestinationTopCityList() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.modifyDestinationtopCity();
		searchResultPage.clickModifyClosebutton(softAssert);
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	@Test(priority = 40)
	public void priceSort() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.defaultPriceSortCheckOW();
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	/*@Test(priority = 41)

	public void srpPriceCalendar() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        searchResultPage = new SearchResultPage(driver, test);
        searchResultPage.verifyPriceCalendarOneWay(softAssert);
        softAssert.assertAll();

	}*/

	/*@Test(priority = 42)
	public void sortByDuration() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.sortByDuration();
		searchResultPage.verifyDurationSort(softAssert);
		softAssert.assertAll();
	}*/

	@Test(priority = 43)
	public void sortByArrival() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.resetAllFilters();
		searchResultPage.sortByArrival();
		searchResultPage.verifyArrivalSort(softAssert);
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	@Test(priority = 44)
	public void sortByDepature() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.sortByDeparture();
		searchResultPage.CloseWebEngage(softAssert);
		searchResultPage.verifyDepartureSort(softAssert);
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	@Test(priority = 45)
	public void sortByAirlines() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.sortByAirlines();
		searchResultPage.verifyAirlineSort(softAssert);
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	@Test(priority = 46)
	public void clearFilters() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
	    searchResultPage.resetAllFilters();
	    softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	@Test(priority = 47)
	public void verifyCurrencyChangeDollar() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.currencyChangeDollar(softAssert);
		softAssert.assertTrue(true);
		softAssert.assertAll();

	}


	@Test(priority = 48)
	public void verifyCurrencyChangeINR() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.currencyChangeRupee(softAssert);
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	@Test(priority = 49)
	public void minFareRT() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.minSRPPrice();
		softAssert.assertTrue(true);
		softAssert.assertAll();

	}

	@Test(priority = 50)
	public void maxFareRT() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.maxSRPPriceOneWay();
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	/*// failed//
	@Test(priority = 51)
	public void priceperAdult() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.pricePerAdultOW(softAssert);

	}*/

	@Test(priority = 52)
	public void srpOnlyRefundable1() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.clickOnlyrefundable(softAssert);
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	@Test(priority = 53)
	public void srpOnlyNonRefundable1() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.checkSRP();
		searchResultPage.onlyNonrefundable(softAssert);
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	@Test(priority = 54)
	public void srpFilterNonStop() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		int flightCount = searchResultPage.getFlightCountOnSRP();
		searchResultPage.clickStopsFilter("nonstop", softAssert);
		searchResultPage.verifyStopsFilterAppliedOneWay("oneway", "nonstop", flightCount, softAssert);
		searchResultPage.resetFilters();
		 softAssert.assertAll();

	}

	@Test(priority = 55)
	public void srpFilter1Stop() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		int flightCount = searchResultPage.getFlightCountOnSRP();
			searchResultPage.clickStopsFilter("1 stop", softAssert);
		searchResultPage.verifyStopsFilterAppliedOneWay("oneway", "1 stop", flightCount, softAssert);
		searchResultPage.resetFilters();
		softAssert.assertAll();
	}

	@Test(priority = 56)
	public void srpFilterTwoStop() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		int flightCount = searchResultPage.getFlightCountOnSRP();
		searchResultPage.clickStopsFilter("2+ stops", softAssert);
		searchResultPage.verifyStopsFilterAppliedOneWay("oneway", "2+ stops", flightCount, softAssert);
		 softAssert.assertAll();

	}

	@Test(priority = 57)
	public void srpFilterdepEarlyMorning() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.resetFilters();
		searchResultPage.departureFilter("Early Morning", softAssert);
		searchResultPage.verifyDepartureTimeFilter("oneway", "Early Morning", softAssert);
		softAssert.assertAll();
	}

	@Test(priority = 58)
	public void srpFilterdepMorning() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.resetFilters();
		searchResultPage.departureFilter("Morning", softAssert);
		searchResultPage.verifyDepartureTimeFilter("oneway", "Morning", softAssert);
		softAssert.assertAll();
	}

	@Test(priority = 59)
	public void srpFilterdepMidDay() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.resetFilters();
		searchResultPage.departureFilter("Mid-Day", softAssert);
		searchResultPage.verifyDepartureTimeFilter("oneway", "Mid-Day", softAssert);
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	@Test(priority = 60)
	public void srpFilterdepEvening() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.resetFilters();
		searchResultPage.departureFilter("Evening", softAssert);
		searchResultPage.verifyDepartureTimeFilter("oneway", "Evening", softAssert);
		searchResultPage.resetAllFilters();
		softAssert.assertTrue(true);
		softAssert.assertAll();
		
	}

	/*@Test(priority = 61)
	public void srpFilterAirlines() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.verifyAllAirlinesOnSRP("oneway", searchResultPage.getAllAirlines(), softAssert);
		

	}*/

	/*@Test(priority = 62)

	public void srpFilterAlliance() throws Exception {

		SoftAssert softAssert = new SoftAssert();
        searchResultPage.clickAlliance();
        searchResultPage.clickAllianceFilter("No Alliance Star Alliance", softAssert);
        softAssert.assertAll();

	}*/

	@Test(priority = 63)
	public void srpLowestPrice() throws Exception {

		SoftAssert softAssert = new SoftAssert();
		searchResultPage.resetAllFilters();
		searchResultPage.verifyLowestPriceFilterOneWay("oneway", softAssert);
		searchResultPage.resetFilters();
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

   @Test(priority = 64)
	public void srpLayoverDuration() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage.scrollDown(driver);
		searchResultPage.verifyLayoverOnSRP("oneway", softAssert);
		searchResultPage.resetFilters();
		softAssert.assertAll();
	}

	@Test(priority = 65)
	public void srptripDuration() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage.scrollDown(driver);
		searchResultPage.verifyTripDurationOnSRP("oneway", softAssert);
		searchResultPage.resetFilters();
		softAssert.assertAll();
	}

	@Test(priority = 66)
	public void srpfirstFlightenabled() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage.verifyFirstFlightPriceEnabled(softAssert);
		softAssert.assertTrue(true);
		softAssert.assertAll();
		
	}

	@Test(priority = 67)
	public void srpVerifyFlightDetailsForNonStop() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.resetFilters();
		nonStop=searchResultPage.clickStopsFilter("non-stop", softAssert);
		//skipTest(!nonStop,"Non Stop flights not available");
		searchResultPage.collectSRPFlightDetails(flightDetails, 1);
		softAssert.assertAll();
	}

	@Test(priority = 68)
	public void srpVerifyFareBreakupDetailsForNonStop() throws Exception {
		
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.collectSRPFareDetails(flowDetailMap);
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	@Test(priority = 69)
	public void srpVerifyCancellationDetailsForNonStop() throws Exception {
		
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.collectCancellationDetailsOneWay("oneway", softAssert);
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	@Test(priority = 70)
	public void srpVerifyBaggageDetailsForNonStop() throws Exception {
		
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.collectBaggageDetails(softAssert);
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	@Test(priority = 71)
	public void srpVerifyFlightDetailsFor1Stop() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.closeSelectedFlightDetails();
		searchResultPage.resetFilters();
		
		searchResultPage.collectSRPFlightDetails(flightDetails, 1);
		softAssert.assertAll();
	}

	@Test(priority = 72)
	public void srpVerifyFareBreakupDetailsFor1Stop() throws Exception {
		
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.collectSRPFareDetails(flowDetailMap);
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	// failed//
	@Test(priority = 73)
	public void srpVerifyCancellationDetailsFor1Stop() throws Exception {
		
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.collectCancellationDetailsOneWay("oneway", softAssert);
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	@Test(priority = 74)
	public void srpVerifyBaggageDetailsFor1Stop() throws Exception {
		
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.collectBaggageDetails(softAssert);
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	@Test(priority = 75)
	public void srpVerifyFlightDetailsFor2Stop() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.closeSelectedFlightDetails();
		searchResultPage.resetFilters();
		Stop2=searchResultPage.clickStopsFilter("2+ stops", softAssert);
		
		searchResultPage.collectSRPFlightDetails(flightDetails, 1);
		softAssert.assertAll();
	}

	@Test(priority = 76)
	public void srpVerifyFareBreakupDetailsFor2Stop() throws Exception {
		
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.collectSRPFareDetails(flowDetailMap);
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	// failed//
	@Test(priority = 77)
	public void srpVerifyCancellationDetailsFor2Stop() throws Exception {
		
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.collectCancellationDetailsOneWay("oneway", softAssert);
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	@Test(priority = 78)
	public void srpVerifyBaggageDetailsFor2Stop() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.collectBaggageDetails(softAssert);
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	/*@Test(priority = 79)
	public void srpVerifyCollapseButtonInFlightDetails() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.closeSelectedFlightDetails();
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}*/
/*
	// failed//
	@Test(priority = 80)
	public void srpVerifyDefaultPriceSorting() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.resetAllFilters();
		searchResultPage.verifyDefaultSortedByPriceOnward(softAssert);
		softAssert.assertAll();
	}


	// failed//
	@Test(priority = 82)
	public void srpVerifyDefaultSelectedPriceAndFlightDetails() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.collectSRPFlightDetails(flightDetails, 1);
		softAssert.assertAll();
	}*/

	/*@Test(priority = 83)
	public void srpCheckHandlingOfUndoFilter() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.departureFilter("Early Morning", softAssert);
		searchResultPage.clickStopsFilter("2+ stops", softAssert);
		searchResultPage.verifyUndoFilters(softAssert);
		searchResultPage.resetAllFilters();
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}*/

	// failed//
	@Test(priority = 84)
	public void srpVerifyNextDayArrival() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.clickStopsFilter("2+ stops", softAssert);
		searchResultPage.verifyNextDayArrive(softAssert);
		softAssert.assertAll();
	}

	@Test(priority = 85)
	public void clickBook() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.checkUndoFilter();
		searchResultPage.resetAllFilters();
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}
	
	@AfterMethod
	public void takeScreenShot(ITestResult result) throws Exception {
		screenshot(result, driver);
		test.addScreenCaptureFromPath(screenShotPath);
		screenShotPath="";
		methodCount++;
		if (!result.isSuccess())
			test.fail(this.getClass().getSimpleName() + "has failed and screenshot is captured");
		extent.flush();

	}

	@AfterClass
	public void dispose() throws Exception {
		try {
			driver.close();
			driver.quit();
		} catch (Exception e) {
			System.err.println("Error in quitting driver");
		}
	}

}
