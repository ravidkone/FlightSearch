package web.goomo.tests.flights.functionality;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;

import web.goomo.commonutils.WebFlightConnector;
import web.goomo.pageobject.flights.AddOnsPage;
import web.goomo.pageobject.flights.FlightReviewPage;
import web.goomo.pageobject.flights.MyGoomoPage;
import web.goomo.pageobject.flights.PaymentConfirmationPage;
import web.goomo.pageobject.flights.PaymentPage;
import web.goomo.pageobject.flights.PrePaymentPage;
import web.goomo.pageobject.flights.SearchPage;
import web.goomo.pageobject.flights.SearchResultPage;
import web.goomo.pageobject.flights.SignInPage;
import web.goomo.pageobject.flights.TravellerDetailsPage;

public class SRPInternationalRT extends WebFlightConnector {
	Map<String, Object> flowDetailMap = new HashMap<>();
	Map<String, Object> flightDetailMap = new HashMap<>();
	JSONObject flightDetails = new JSONObject();
	int retryCountFlight = 0;
	boolean pickOtherFlights;
	boolean flightsAvailable = false;
	int selectFlight =1;
	boolean filtersApplied;
	ExtentTest test;
	boolean testFail = true;
	static int attempts = 0;
	static int paxCount = 2;

	static int methodCount = 0;
	
	boolean nonStop = false;
	boolean Stop1 =false;
	boolean Stop2 =false;

	SearchPage search;
	SearchResultPage searchResultPage;
	FlightReviewPage reviewPage;
	SignInPage signPage;
	TravellerDetailsPage travellerDetails;
	PrePaymentPage prepayment;
	PaymentPage payment;
	PaymentConfirmationPage confirmationPage;
	AddOnsPage addOns;
	MyGoomoPage myGoomoPage;
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
	
	@Test(priority = 33,dataProvider = "internationalRTF", dataProviderClass = Dataproviders.class,retryAnalyzer=web.goomo.commonutils.Retry.class)
	public void searchRequest(String tripType, List<String> onwardSectors, List<String> returnSectors,
			String travelDate, String returnDate, String classType, String adultCount, String childCount,
			String infantCount, Map<String, Object> mealBaggage) throws Exception{
		//getRTURL(onwardSectors.get(attempts), returnSectors.get(attempts), travelDate, returnDate, classType, adultCount, childCount, infantCount);
       getURL();
       search=new SearchPage(driver);
       search.searchFormRoundTrip("ROUNDTRIP","BOM", "DXB", adultCount, childCount, infantCount);
		maximizeWindow(driver);
        
		search.HowAboutPopUp();
		driver.navigate().refresh();
	    searchResultPage = new SearchResultPage(driver, test);
	    searchResultPage.checkSRP();
	    
       

	}
	
	/*@Test(priority = 34,dependsOnMethods="searchRequest")
	public void verifyModifyCloseButton() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage.verifyModifySearchButton(softAssert);
		softAssert.assertAll();
	}

	@Test(priority = 35,dependsOnMethods="searchRequest")
	public void modifyWithNoChange() throws Exception {

		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		String totalCount = searchResultPage.getSRPFlightCount(softAssert);
		
		searchResultPage.clickModifybutton(softAssert);
		Thread.sleep(2000);
		searchResultPage.clickModifyClosebutton(softAssert);
		String countAfterModify = searchResultPage.getSRPFlightCount(softAssert);
		softAssert.assertEquals(true, "Results are verified");


	}*/

	@Test(priority = 37,dataProvider = "internationalRTF", dataProviderClass = Dataproviders.class)
	public void modifyDepatureTopCityListInt(String tripType, List<String> onwardSectors, List<String> returnSectors,
			String travelDate, String returnDate, String classType, String adultCount, String childCount,
			String infantCount, Map<String, Object> mealBaggage) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.modifyDepaturetopCityInt(onwardSectors.get(0));
		softAssert.assertAll();
	}

	@Test(priority = 38,dataProvider = "internationalRTF", dataProviderClass = Dataproviders.class)
	public void modifyDestinationTopCityListInt(String tripType, List<String> onwardSectors, List<String> returnSectors,
			String travelDate, String returnDate, String classType, String adultCount, String childCount,
			String infantCount, Map<String, Object> mealBaggage) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.modifyDestinationtopCityInt(returnSectors.get(0));
		searchResultPage.clickModifySearch();
		softAssert.assertAll();
	
	}

	@Test(priority = 39)
	public void modifyDateChange() throws Exception {

		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.checkSRP();
		searchResultPage.verifyModifySearchButton(softAssert);
		searchResultPage.clickModify();
		searchResultPage.fillOnwardDate();
		searchResultPage.fillReturnDate();
		searchResultPage.clickModifySearch();
	
	}


	@Test(priority = 41)
	public void verifyCurrencyChangeDollar() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		scrollTillTop(driver);
		
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.CloseWebEngage(softAssert);
		searchResultPage.checkSRP();
		searchResultPage.currencyChangeDollar(softAssert);
		softAssert.assertAll();

	}

	@Test(priority = 42)
	public void verifyCurrencyChangeINR() throws Exception {
		scrollTillTop(driver);
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.currencyChangeRupee(softAssert);
		searchResultPage.CloseWebEngage(softAssert);
		softAssert.assertAll();

	}

	@Test(priority = 43)
	public void priceSort() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		scrollTillTop(driver);
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.sortByPrice();
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	@Test(priority = 44)
	public void airlineSort() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		scrollTillTop(driver);
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.sortByAirlines();
	    softAssert.assertTrue(true);
		softAssert.assertAll();
	
	}

	@Test(priority = 45)
	public void depatureSort() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		scrollTillTop(driver);
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.sortByDeparture();
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	@Test(priority = 46)
	public void arrivalSort() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		scrollTillTop(driver);
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.sortByArrival();
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	@Test(priority = 47)
	public void durationSort() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		scrollTillTop(driver);
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.sortByDuration();
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	/*@Test(priority = 48)
	public void priceperAdult() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		scrollTillTop(driver);
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.pricePerAdultInt();
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}*/

	@Test(priority = 49)
	public void collapsePPApane() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		scrollTillTop(driver);
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.collapsePricePerAdultPane();
		softAssert.assertTrue(true);
		softAssert.assertAll();

	}

	@Test(priority = 50)
	public void expandPPApane() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		scrollTillTop(driver);
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.expandPricePerAdultPane();
		softAssert.assertTrue(true);
		softAssert.assertAll();

	}

	@Test(priority = 51)
	public void collapseStopsPane() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		scrollTillTop(driver);
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.collapseStopsPane();
		softAssert.assertTrue(true);
		softAssert.assertAll();

	}

	@Test(priority = 52)
	public void expandStopsPane() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		scrollTillTop(driver);
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.expandStopsPane();
		softAssert.assertTrue(true);
		softAssert.assertAll();

	}

	@Test(priority = 53)
	public void collapseDepTimePane() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		scrollTillTop(driver);
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.collapseDepaturTimePane();
		softAssert.assertTrue(true);
		softAssert.assertAll();

	}

	@Test(priority = 54)
	public void expandDepTimepane() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		scrollTillTop(driver);
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.expandDepatureTimePane();
		softAssert.assertTrue(true);
		softAssert.assertAll();


	}

	@Test(priority = 55)
	public void collapseRetTimepane() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		scrollTillTop(driver);
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.collapseReturnTimepane();
		softAssert.assertTrue(true);
		softAssert.assertAll();


	}

	@Test(priority = 56)
	public void expandRetTimepane() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		scrollTillTop(driver);
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.expandReturnTimePane();
		softAssert.assertTrue(true);
		softAssert.assertAll();

	}

	@Test(priority = 57)
	public void collapseAirlineApane() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		scrollDown(driver);
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.collapseAirlinePane();
		softAssert.assertTrue(true);
		softAssert.assertAll();

	}

	@Test(priority = 58)
	public void expandAirlineApane() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.expandAirlinePane();
		softAssert.assertTrue(true);
		softAssert.assertAll();
		

	}

	@Test(priority = 59)
	public void collapseLayoverApane() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.collapseLayoverPane();
		softAssert.assertTrue(true);
		softAssert.assertAll();

	}

	@Test(priority = 60)
	public void expandlayoverApane() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.expandLayoverPane();
		softAssert.assertTrue(true);
		softAssert.assertAll();

	}


/*	@Test(priority = 61)
	public void collapseTripDurationApane() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.collapseTripDurationPane();
		softAssert.assertTrue(true);
		softAssert.assertAll();
		

	}

	@Test(priority = 62)
	public void expandTripDurationApane() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.expandTripDurationPane();
		softAssert.assertTrue(true);
		softAssert.assertAll();

	}*/


	
	@Test(priority = 64)
	public void srpFilterdepEarlyMorning() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.resetFilters();
		searchResultPage.departureFilter("Early Morning", softAssert);
		searchResultPage.verifyDepartureTimeFilter("roundtrip", "Early Morning", softAssert);
		//softAssert.assertAll();
	
	}

	@Test(priority = 65)
	public void srpFilterdepMorning() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.resetFilters();
		searchResultPage.departureFilter("Morning", softAssert);
		searchResultPage.verifyDepartureTimeFilter("roundtrip", "Morning", softAssert);
		//softAssert.assertAll();
		
	}

	@Test(priority = 66)
	public void srpFilterdepMidDay() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.resetFilters();
		searchResultPage.departureFilter("Mid-Day", softAssert);
		searchResultPage.verifyDepartureTimeFilter("roundtrip", "Mid-Day", softAssert);
		//softAssert.assertAll();
	
	}

	@Test(priority = 67)
	public void srpFilterdepEvening() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.resetFilters();
		searchResultPage.departureFilter("Evening", softAssert);
		searchResultPage.verifyDepartureTimeFilter("roundtrip", "Evening", softAssert);
		//softAssert.assertAll();
		
	}

	@Test(priority = 68)
	public void srpFilterRetEarlyMorning() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.resetFilters();
		searchResultPage.returnFilter("Early Morning", softAssert);
		searchResultPage.verifyReturnTimeFilter("roundtrip", "Early Morning", softAssert);
		
		
	}

	@Test(priority = 69)
	public void srpFilterRetMorning() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.resetFilters();
		searchResultPage.returnFilter("Morning", softAssert);
		searchResultPage.verifyReturnTimeFilter("roundtrip", "Morning", softAssert);
		//softAssert.assertAll();
		
	}

	@Test(priority = 70)
	public void srpFilterRetMidDay() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.resetFilters();
		searchResultPage.returnFilter("Mid-Day", softAssert);
		searchResultPage.verifyReturnTimeFilter("roundtrip", "Mid-Day", softAssert);
		//softAssert.assertAll();
		
	}

	@Test(priority = 71)
	public void srpFilteRetEvening() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.resetFilters();
		searchResultPage.returnFilter("Evening", softAssert);
		searchResultPage.verifyReturnTimeFilter("roundtrip", "Evening", softAssert);
		//softAssert.assertAll();
		
	}

	@Test(priority = 72)
	public void srpFilterAirlines() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		scrollTillTop(driver);
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.resetAllFilters();
		//searchResultPage.verifyAllAirlinesOnSRP("roundtrip", searchResultPage.getAllAirlines(), softAssert);

	}

	

	

	/*@Test(priority = 75)
	public void srpPriceCalendar() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		scrollTillTop(driver);
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.verifyPriceCalendar(softAssert);
		softAssert.assertAll();

	}*/

	@Test(priority = 76)
	public void srpCheckHandlingOfUndoFilter() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		scrollTillTop(driver);
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.departureFilter("Early Morning", softAssert);
		searchResultPage.clickStopsFilter("2+ stops", softAssert);
		searchResultPage.verifyUndoFilters(softAssert);
		searchResultPage.resetAllFilters();
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	@Test(priority = 77)
	public void srpVerifyFlightDetailsForNonStop() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.resetFilters();
		nonStop=searchResultPage.clickStopsFilter("non-stop", softAssert);
		//skipTest(!nonStop,"Non Stop flights not available");
		searchResultPage.collectSRPFlightDetailsIntRT(flightDetails,1);
		softAssert.assertTrue(true);
		System.out.println("44");
	}

	@Test(priority = 78)
	public void srpVerifyFareBreakupDetailsForNonStop() throws Exception {
		//skipTest(!nonStop,"Non Stop flights not available");
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.collectSRPFareDetails(flowDetailMap);
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	@Test(priority = 79)
	public void srpVerifyCancellationDetailsForNonStop() throws Exception {
		//skipTest(!nonStop,"Non Stop flights not available");
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.collectCancellationDetailsInt(softAssert);
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	@Test(priority = 80)
	public void srpVerifyBaggageDetailsForNonStop() throws Exception {
		//skipTest(!nonStop,"Non Stop flights not available");
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.collectBaggageDetails(softAssert);
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	@Test(priority = 81)
	public void srpVerifyFlightDetailsFor1Stop() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.closeSelectedFlightDetails();
		searchResultPage.resetFilters();
		Stop1=searchResultPage.clickStopsFilter("1 stop", softAssert);
		//skipTest(!Stop1,"1Stop Flights not avaiable");
		searchResultPage.collectSRPFlightDetailsIntRT(flightDetails,1);
		softAssert.assertAll();
		
	}

	@Test(priority = 82)
	public void srpVerifyFareBreakupDetailsFor1Stop() throws Exception {
		//skipTest(!Stop1,"1Stop Flights not avaiable");
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.collectSRPFareDetails(flowDetailMap);
		softAssert.assertTrue(true);
		System.out.println("49");
	}

	@Test(priority = 83)
	public void srpVerifyCancellationDetailsFor1Stop() throws Exception {
		//skipTest(!Stop1,"1Stop Flights not avaiable");
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.collectCancellationDetailsInt(softAssert);
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	@Test(priority = 84)
	public void srpVerifyBaggageDetailsFor1Stop() throws Exception {
		//skipTest(!Stop1,"1Stop Flights not avaiable");
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.collectBaggageDetails(softAssert);
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	@Test(priority = 85)
	public void srpVerifyFlightDetailsFor2Stop() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.closeSelectedFlightDetails();
		searchResultPage.resetFilters();
		
		Stop2=searchResultPage.clickStopsFilter("2+ stops", softAssert);
		//skipTest(!Stop2,"2Stop Flights not avaiable");
		searchResultPage.collectSRPFlightDetailsIntRT(flightDetails,1);
		softAssert.assertAll();
	}

	@Test(priority = 86)
	public void srpVerifyFareBreakupDetailsFor2Stop() throws Exception {
		//skipTest(!Stop2,"2Stop Flights not avaiable");
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.collectSRPFareDetails(flowDetailMap);
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	@Test(priority = 87)
	public void srpVerifyCancellationDetailsFor2Stop() throws Exception {
	   //	skipTest(!Stop2,"2Stop Flights not avaiable");
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.collectCancellationDetailsInt(softAssert);
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	@Test(priority = 88)
	public void srpVerifyBaggageDetailsFor2Stop() throws Exception {
		//skipTest(!Stop2,"2Stop Flights not avaiable");
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.collectBaggageDetails(softAssert);
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	
	@Test(priority = 90)
	public void getSRPFlightDetails() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.collectSRPFlightDetailsRT(flightDetails);
		softAssert.assertTrue(true);
		softAssert.assertAll();

	}

	@Test(priority = 91)
	public void selectFlight() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		//searchResultPage.clickBookButton();
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

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
