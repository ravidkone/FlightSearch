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
import web.goomo.pageobject.flights.FlightReviewPage;
import web.goomo.pageobject.flights.MyGoomoPage;
import web.goomo.pageobject.flights.PaymentConfirmationPage;
import web.goomo.pageobject.flights.PaymentPage;
import web.goomo.pageobject.flights.PrePaymentPage;
import web.goomo.pageobject.flights.SearchPage;
import web.goomo.pageobject.flights.SearchResultPage;
import web.goomo.pageobject.flights.SignInPage;
import web.goomo.pageobject.flights.TravellerDetailsPage;

public class SRPInternationalOW extends WebFlightConnector {
	///
	Map<String, Object> flowDetailMap = new HashMap<>();
	Map<String, Object> flightDetailMap = new HashMap<>();
	JSONObject flightDetails = new JSONObject();
	boolean pickOtherFlights;
	int retryCountFlight = 0;
	boolean filtersApplied;
	ExtentTest test;
	boolean testFail = true;
	static int attempts = 0;
	static int paxCount = 3;
	int selectFlight = 3;
	static int methodCount = 0;
	boolean flightsAvailable = false;

	boolean nonStop = false;
	boolean Stop1 = false;
	boolean Stop2 = false;

	MyGoomoPage myGoomoPage;
	SearchPage search;
	SearchResultPage searchResultPage;
	FlightReviewPage reviewPage;
	SignInPage signPage;
	TravellerDetailsPage travellerDetails;
	PrePaymentPage prepayment;
	PaymentPage payment;
	PaymentConfirmationPage confirmationPage;
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
	
	@Test(priority = 35,dataProvider = "internationalOW", dataProviderClass = Dataproviders.class)
	public void changeTraveler(String tripType,List<String> onwardSectors,List<String> returnSectors,String travelDate,String classType,String adultCount,
			String childCount,String infantCount,Map<String,Object> mealData) throws Exception {
       
	getOWURL(onwardSectors.get(0),returnSectors.get(0), travelDate, classType, adultCount, childCount, infantCount);
		search=new SearchPage(driver);
		//getURL();
		//search.searchFormOneWay("ONEWAY",onwardSectors.get(0),returnSectors.get(0),adultCount, childCount, infantCount);

		maximizeWindow(driver);
		
		search.HowAboutPopUp();
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.popupHandle();
		searchResultPage.clickModifySearch();
		searchResultPage.selectTravellerModifySearch("2", "0", "0");
		searchResultPage.clickModifyClosebutton(softAssert);
		softAssert.assertAll();

	}
   
	@Test(priority = 36)
	public void modifyWithNoChange() throws Exception {

		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		String totalCount = searchResultPage.getSRPFlightCount(softAssert);
		searchResultPage.clickModifybutton(softAssert);
		searchResultPage.clickModifyClosebutton(softAssert);
		String countAfterModify = searchResultPage.getSRPFlightCount(softAssert);
		// softAssert.assertAll();
	}

	@Test(priority = 37)
	public void modifyDepatureTopCityList() throws Exception {

		searchResultPage = new SearchResultPage(driver, test);
		SoftAssert softAssert = new SoftAssert();
		searchResultPage.modifyDepaturetopCity();
        softAssert.assertAll();
	}

	@Test(priority = 38)
	public void modifyDestinationTopCityList() throws Exception {

		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.modifyDestinationtopCity();
		searchResultPage.clickModifyClosebutton(softAssert);
		softAssert.assertAll();

	}

	@Test(priority = 39)
	public void priceSort() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.defaultPriceSortCheckOW();
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	/*@Test(priority = 40)
	public void srpPriceCalendar() throws Exception {

		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.verifyPriceCalendarOneWay(softAssert);
		softAssert.assertAll();

	}*/

	@Test(priority = 41)
	public void sortByDuration() throws Exception {

		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.sortByDuration();
		searchResultPage.verifyDurationSort(softAssert);
		softAssert.assertAll();
	}

	@Test(priority = 43)
	public void sortByArrival() throws Exception {

		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.sortByArrival();
		searchResultPage.verifyARrivalSort(softAssert);
		softAssert.assertAll();
	}

	@Test(priority = 42)
	public void sortByDepature() throws Exception {

		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.sortByDeparture();
		searchResultPage.verifyDEpartureSort(softAssert);
		softAssert.assertAll();
	}

	@Test(priority = 44)
	public void sortByAirlines() throws Exception {

		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.sortByAirlines();
		searchResultPage.verifyAirlineSort(softAssert);
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	@Test(priority = 45)
	public void resetAllFilters() throws Exception {

		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.resetAllFilters();
		softAssert.assertTrue(true, "All Filters reset");
	}

	@Test(priority = 46)
	public void verifyCurrencyChangeDollar() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.CloseWebEngage(softAssert);
		searchResultPage.currencyChangeDollar(softAssert);
		softAssert.assertAll();

	}

	@Test(priority = 47)
	public void verifyCurrencyChangeINR() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.currencyChangeRupee(softAssert);
		softAssert.assertAll();

	}

	@Test(priority = 48)
	public void minFareRT() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.minSRPPrice();
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	@Test(priority = 49)
	public void maxFareRT() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.maxSRPPriceOneWay();
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	/*@Test(priority = 50)
	public void priceperAdult() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.pricePerAdult(softAssert);
		softAssert.assertAll();
	}*/

	
	@Test(priority = 52)
	public void srpFilterNonStop() throws Exception {

		SoftAssert softAssert = new SoftAssert();
		int flightCount = searchResultPage.getFlightCountOnSRP();
		searchResultPage.clickStopsFilter("nonstop", softAssert);
		searchResultPage.verifyStopsFilterAppliedOneWay("oneway","nonstop", flightCount, softAssert);
		searchResultPage.resetAllFilters();
		 softAssert.assertTrue(true);
		 softAssert.assertAll();

	}

	@Test(priority = 53)
	public void srpFilter1Stop() throws Exception {

		SoftAssert softAssert = new SoftAssert();
		int flightCount = searchResultPage.getFlightCountOnSRP();
		searchResultPage.clickStopsFilter("1 stop", softAssert);
		searchResultPage.verifyStopsFilterAppliedOneWay("oneway", "1 stop", flightCount, softAssert);
		searchResultPage.resetAllFilters();
		 softAssert.assertTrue(true);
		 softAssert.assertAll();
	}

	@Test(priority = 54)
	public void srpFilterTwoStop() throws Exception {

		SoftAssert softAssert = new SoftAssert();
		int flightCount = searchResultPage.getFlightCountOnSRP();
		searchResultPage.clickStopsFilter("2+ stops", softAssert);
		searchResultPage.verifyStopsFilterAppliedOneWay("oneway", "2+ stops", flightCount, softAssert);
		searchResultPage.resetAllFilters();
		softAssert.assertTrue(true);
		 softAssert.assertAll();

	}

	@Test(priority = 55)
	public void srpFilterdepEarlyMorning() throws Exception {

		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.resetFilters();
		searchResultPage.departureFilter("Early Morning", softAssert);
		searchResultPage.verifyDepartureTimeFilter("oneway", "Early Morning", softAssert);
		
	}

	@Test(priority = 56)
	public void srpFilterdepMorning() throws Exception {

		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.resetFilters();
		searchResultPage.departureFilter("Morning", softAssert);
		searchResultPage.verifyDepartureTimeFilter("oneway", "Morning", softAssert);
		
	}

	@Test(priority = 57)
	public void srpFilterdepMidDay() throws Exception {

		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.resetFilters();
		searchResultPage.departureFilter("Mid-Day", softAssert);
		searchResultPage.verifyDepartureTimeFilter("oneway", "Mid-Day", softAssert);
		
	}

	@Test(priority = 58)
	public void srpFilterdepEvening() throws Exception {

		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.resetFilters();
		searchResultPage.departureFilter("Evening", softAssert);
		searchResultPage.verifyDepartureTimeFilter("oneway", "Evening", softAssert);
		
	}

	/*@Test(priority = 59)
	public void srpFilterAirlines() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.verifyAllAirlinesOnSRP("oneway", searchResultPage.getAllAirlines(), softAssert);
	}
*/
	/*@Test(priority = 60)
	public void srpFilterAlliance() throws Exception {

		SoftAssert softAssert = new SoftAssert();
		searchResultPage.clickAlliance();
		searchResultPage.clickAllianceFilter("No Alliance Star Alliance", softAssert);
		
	}*/

	/*@Test(priority = 61)
	public void srpLowestPrice() throws Exception {

		SoftAssert softAssert = new SoftAssert();
		searchResultPage.resetAllFilters();
		searchResultPage.verifyLowestPriceFilterOneWay("oneway", softAssert);
		searchResultPage.resetFilters();
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}*/

	@Test(priority = 62)
	public void srpLayoverDuration() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage.scrollDown(driver);
		searchResultPage.verifyLayoverOnSRP("oneway", softAssert);
		searchResultPage.resetFilters();
	}

	@Test(priority = 63)
	public void srptripDuration() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage.scrollDown(driver);
		searchResultPage.verifyTripDurationOnSRP("oneway", softAssert);
		searchResultPage.resetFilters();
	}

	

	@Test(priority = 65)
	public void srpVerifyFlightDetailsForNonStop() throws Exception {

		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.resetFilters();
		nonStop = searchResultPage.clickStopsFilter("non-stop", softAssert);
		
		searchResultPage.clickStopsFilter("nonstop", softAssert);
		searchResultPage.collectSRPFlightDetails(flightDetails, 1);
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	@Test(priority = 66)
	public void srpVerifyFareBreakupDetailsForNonStop() throws Exception {
		
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.collectSRPFareDetails(flowDetailMap);
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	@Test(priority = 67)
	public void srpVerifyCancellationDetailsForNonStop() throws Exception {
		
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.collectCancellationDetailsOneWay("oneway", softAssert);
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	@Test(priority = 68)
	public void srpVerifyBaggageDetailsForNonStop() throws Exception {
		
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.collectBaggageDetails(softAssert);
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	@Test(priority = 69)
	public void srpVerifyFlightDetailsFor1Stop() throws Exception {

		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.closeSelectedFlightDetails();
		searchResultPage.resetFilters();
		Stop1 = searchResultPage.clickStopsFilter("1 stop", softAssert);
		
		searchResultPage.clickStopsFilter("1 stop", softAssert);
		searchResultPage.collectSRPFlightDetails(flightDetails, 1);
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	@Test(priority = 70)
	public void srpVerifyFareBreakupDetailsFor1Stop() throws Exception {
		
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.collectSRPFareDetails(flowDetailMap);
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	@Test(priority = 71)
	public void srpVerifyCancellationDetailsFor1Stop() throws Exception {
		
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.collectCancellationDetailsOneWay("oneway", softAssert);
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	@Test(priority = 72)
	public void srpVerifyBaggageDetailsFor1Stop() throws Exception {
		
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.collectBaggageDetails(softAssert);
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	@Test(priority = 73)
	public void srpVerifyFlightDetailsFor2Stop() throws Exception {

		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.closeSelectedFlightDetails();
		searchResultPage.resetFilters();
		Stop2 = searchResultPage.clickStopsFilter("2+ stops", softAssert);
		
		searchResultPage.clickStopsFilter("2+ stops", softAssert);
		searchResultPage.collectSRPFlightDetails(flightDetails, 1);
		softAssert.assertAll();
	}

	@Test(priority = 74)
	public void srpVerifyFareBreakupDetailsFor2Stop() throws Exception {
		
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.collectSRPFareDetails(flowDetailMap);
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	@Test(priority = 75)
	public void srpVerifyCancellationDetailsFor2Stop() throws Exception {
		
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.collectCancellationDetailsOneWay("oneway", softAssert);
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	@Test(priority = 76)
	public void srpVerifyBaggageDetailsFor2Stop() throws Exception {
		
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.collectBaggageDetails(softAssert);
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}
	

	@Test(priority = 77)
	public void srpVerifyCollapseButtonInFlightDetails() throws Exception {

		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.closeSelectedFlightDetails();
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	/*@Test(priority = 78)
	public void srpVerifyDefaultPriceSorting() {

		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.verifyDefaultSortedByPriceOW(softAssert);
		softAssert.assertAll();
		

	}*/

	
	/* * @Test(priority=79) public void srpVerifyOnChangingDefaultSelectionFlightCountNoChanged() throws
	 * Exception { SoftAssert softAssert =new SoftAssert(); searchResultPage =
	 * new SearchResultPage(driver, test); int countFlightsSRPBefore =
	 * searchResultPage.getFlightCountOnSRP();
	 * searchResultPage.selectOtherFlightsInOWTrip(softAssert); int
	 * countFlightsSRPAfter = searchResultPage.getFlightCountOnSRP();
	 * softAssert.assertEquals(countFlightsSRPBefore,
	 * countFlightsSRPAfter,"Flight count doesn't match after changing the default selection"
	 * );
	 * 
	 * softAssert.assertTrue(true);
	 * 
	 * }*/
	 

    @Test(priority = 80)
	public void srpVerifyDefaultSelectedPriceAndFlightDetails() throws Exception {

		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.collectSRPFlightDetails(flightDetails, 1);
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	@Test(priority = 81)
	public void srpCheckHandlingOfUndoFilter() throws Exception {

		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.departureFilter("Early Morning", softAssert);
		searchResultPage.departureFilter("Mid-Day", softAssert);
		searchResultPage.departureFilter("Evening", softAssert);
		searchResultPage.verifyUndoFilters(softAssert);
		softAssert.assertAll();
	}

	/*@Test(priority = 82)
	public void srpVerifyNextDayArrival() throws Exception {

		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.verifyDiffBetweenArrivalAndDepartureForOtherFlightsInternational(softAssert);
		softAssert.assertTrue(true);
		softAssert.assertAll();

	}*/

/*	@Test(priority = 83)
	public void selectFlight() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage.selectFlightInternationalOW();
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}*/
	
	@AfterMethod
	public void takeScreenShot(ITestResult result) throws Exception {
		screenshot(result, driver);
		test.addScreenCaptureFromPath(screenShotPath);
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
