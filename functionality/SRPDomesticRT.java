package web.goomo.tests.flights.functionality;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
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

public class SRPDomesticRT extends WebFlightConnector {

	Map<String, Object> flowDetailMap = new HashMap<>();
	Map<String, Object> flightDetailMap = new HashMap<>();
	JSONObject flightDetails = new JSONObject();
	boolean pickOtherFlights;
	int retryCountFlight = 0;
	boolean filtersApplied;
	ExtentTest test;
	boolean testFail = true;
	boolean flightsAvailable = false;
	static int attempts = 0;
	static int paxcount=1;

	static int methodCount = 0;

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
	
	
	
	@Test(priority = 34,dataProvider="domesticRT",dataProviderClass=web.goomo.tests.flights.functionality.Dataproviders.class)
	public void srpModifySearchCheckCloseButton(String tripType, List<String> onwardSectors, List<String> returnSectors,
			String departureDate,String returnDate, String classType, String adultCount, String childCount, String infantCount,
			
			Map<String, Object> mealBaggage,List<String> airlinePreferedNameList) throws Exception {
		//driver.get("http://10.100.44.95:3000");
		getRTURL(onwardSectors.get(0),returnSectors.get(0),departureDate, returnDate, classType, adultCount, childCount, infantCount);
		search=new SearchPage(driver);

		//getURL();
		maximizeWindow(driver);
		//search.searchFormOneWay("ROUNDTRIP",onwardSectors.get(0),returnSectors.get(0), adultCount, childCount, infantCount);
		

		search.HowAboutPopUp();
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.popupHandle();
		String totalCount = searchResultPage.getSRPFlightCount(softAssert);
		//driver.navigate().refresh();
		//searchResultPage.clickModifybutton(softAssert);
		//searchResultPage.clickModifyClosebutton(softAssert);
		
	}

	@Test(priority = 35,dataProvider="domesticRT",dataProviderClass=web.goomo.tests.flights.functionality.Dataproviders.class)
	public void srpModifySearchCheckSameCityInSource(String tripType, List<String> onwardSectors, List<String> returnSectors,
			String departureDate,String returnDate, String classType, String adultCount, String childCount, String infantCount,
			Map<String, Object> mealBaggage,List<String> airlinePreferedNameList) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.clickModifybutton(softAssert);
		searchResultPage.modifySourceRT(onwardSectors.get(0),returnSectors.get(0), softAssert);
		searchResultPage.clickModifyClosebutton(softAssert);
	
	}

	@Test(priority = 36,dataProvider="domesticRT",dataProviderClass=web.goomo.tests.flights.functionality.Dataproviders.class)
	public void srpModifySearchCheckSameCityInDestination(String tripType, List<String> onwardSectors, List<String> returnSectors,
			String departureDate,String returnDate, String classType, String adultCount, String childCount, String infantCount,
			Map<String, Object> mealBaggage,List<String> airlinePreferedNameList) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.clickModifybutton(softAssert);
		searchResultPage.modifyDestinationRT(onwardSectors.get(0),returnSectors.get(0), softAssert);
		// searchResultPage.clickModifyClosebutton(softAssert);
		
	}

	@Test(priority = 37)
	public void modifyWithDateChange() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage=new SearchResultPage(driver, test);
		searchResultPage.fillOnwardDate();
		searchResultPage.fillReturnDate();
		softAssert.assertAll();

	}

	@Test(priority = 38)
	public void changeTraveler() throws Exception {

		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.selectTravellerModifySearch("2", "0", "0");
		searchResultPage.clickModifyClosebutton(softAssert);
		softAssert.assertAll();

	}

	@Test(priority = 39)
	public void modifyWithNoChange() throws Exception {

		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		String totalCount = searchResultPage.getSRPFlightCount(softAssert);
		searchResultPage.clickModifybutton(softAssert);
		searchResultPage.clickModifyClosebutton(softAssert);
		String countAfterModify = searchResultPage.getSRPFlightCount(softAssert);
	}

	@Test(priority = 40)
	public void modifyDepatureTopCityList() throws Exception {
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.modifyDepaturetopCity();

	}

	@Test(priority = 41)
	public void modifyDestinationTopCityList() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.modifyDestinationtopCity();
		searchResultPage.clickModifyClosebutton(softAssert);
		softAssert.assertAll();

	}

	@Test(priority = 42)
	public void priceSort() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.defaultPriceSortCheck();
		softAssert.assertTrue(true);
		softAssert.assertAll();
		
	}

	/*@Test(priority = 43)
	public void srpPriceCalendar() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.verifyPriceCalendar(softAssert);
		softAssert.assertAll();

	}
*/
	@Test(priority = 44)
	public void verifyCurrencyChangeDollar() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.currencyChangeDollarRT(softAssert);
		softAssert.assertAll();
		}

	@Test(priority = 45)
	public void verifyCurrencyChangeINR() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.currencyChangeRupeeRT(softAssert);
		softAssert.assertAll();

	}

	@Test(priority = 46)
    public void sortByprice() throws Exception {
		
        SoftAssert softAssert = new SoftAssert();
        searchResultPage = new SearchResultPage(driver, test);
        searchResultPage.priceSortOnward();
        searchResultPage.priceSortReturn();
        softAssert.assertAll();
    }

  @Test(priority = 47)
    public void sortByArrival() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        searchResultPage = new SearchResultPage(driver, test);
        searchResultPage.arrivalSortOnward();
        searchResultPage.arrivalSortReturn();
        softAssert.assertAll();
    }

   @Test(priority = 48)
    public void sortByDepature() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        searchResultPage = new SearchResultPage(driver, test);
        searchResultPage.depatureSortOnward();
        searchResultPage.depatureSortReturn();
        softAssert.assertAll();
    }

   @Test(priority = 49)
    public void sortByAirlines() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        searchResultPage = new SearchResultPage(driver, test);
        searchResultPage.airlineSortOnward();
        searchResultPage.airlineSortReturn();
        softAssert.assertAll();
    }
	
	
	
	@Test(priority = 50)
	public void resetAllFilters() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.resetAllFilters();
		searchResultPage.CloseWebEngage(softAssert);
		softAssert.assertTrue(true, "All Filters reset");
		softAssert.assertAll();
	}

	@Test(priority = 51)
	public void minFareRT() throws Exception {
	    SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.minSRPPriceRT();
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	@Test(priority = 52)
	public void maxFareRT() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.maxSRPPriceRT();
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	
	@Test(priority = 54)
	public void srpOnlyNonRefundable() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.checkSRP();
		searchResultPage.onlyNonrefundable(softAssert);
		softAssert.assertAll();

	}

	
	@Test(priority = 55)
	public void srpFilterNonStop() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		int flightCount = searchResultPage.getFlightCountOnSRP();
		searchResultPage.clickStopsFilter("nonstop", softAssert);
		searchResultPage.verifyStopsFilterApplied("roundtrip", "nonstop", flightCount, softAssert);
		searchResultPage.resetAllFilters();
		softAssert.assertAll();

	}

	@Test(priority = 57)
	public void srpFilter1Stop() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		int flightCount = searchResultPage.getFlightCountOnSRP();
		searchResultPage.clickStopsFilter("1 stop", softAssert);
		searchResultPage.verifyStopsFilterApplied("roundtrip", "1 stop", flightCount, softAssert);
		searchResultPage.resetAllFilters();
		softAssert.assertAll();
	}

	@Test(priority = 58)
	public void srpFilterTwoStop() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		int flightCount = searchResultPage.getFlightCountOnSRP();
		searchResultPage.clickStopsFilter("2+ stops", softAssert);
		searchResultPage.verifyStopsFilterApplied("roundtrip", "2+ stops", flightCount, softAssert);
		softAssert.assertAll();

	}

	@Test(priority = 59)
	public void srpFilterdepEarlyMorning() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.resetFilters();
		searchResultPage.departureFilter("Early Morning", softAssert);
		searchResultPage.verifyDepartureTimeFilterDomestic("roundtrip", "Early Morning", softAssert);
		softAssert.assertAll();
	}

	@Test(priority = 60)
	public void srpFilterdepMorning() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.resetFilters();
		searchResultPage.departureFilter("Morning", softAssert);
		searchResultPage.verifyDepartureTimeFilterDomestic("roundtrip", "Morning", softAssert);
		softAssert.assertAll();
	}

	@Test(priority = 61)
	public void srpFilterdepMidDay() throws Exception {
	
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.resetFilters();
		searchResultPage.departureFilter("Mid-Day", softAssert);
		searchResultPage.verifyDepartureTimeFilterDomestic("roundtrip", "Mid-Day", softAssert);
		softAssert.assertAll();
	}

	@Test(priority = 62)
	public void srpFilterdepEvening() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.resetFilters();
		searchResultPage.departureFilter("Evening", softAssert);
		searchResultPage.verifyDepartureTimeFilterDomestic("roundtrip", "Evening", softAssert);
		softAssert.assertAll();
	}

	@Test(priority = 63)
	public void srpFilterRetEarlyMorning() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.resetFilters();
		searchResultPage.returnFilter("Early Morning", softAssert);
		searchResultPage.verifyReturnTimeFilterDomestic("roundtrip", "Early Morning", softAssert);
		
	}

	@Test(priority = 64)
	public void srpFilterRetMorning() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.resetFilters();
		searchResultPage.returnFilter("Morning", softAssert);
		searchResultPage.verifyReturnTimeFilterDomestic("roundtrip", "Morning", softAssert);
		softAssert.assertAll();
	}

	@Test(priority = 65)
	public void srpFilterRetMidDay() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.resetFilters();
		searchResultPage.returnFilter("Mid-Day", softAssert);
		searchResultPage.verifyReturnTimeFilterDomestic("roundtrip", "Mid-Day", softAssert);
		softAssert.assertAll();
	}

	@Test(priority = 66)
	public void srpFilteRetEvening() throws Exception {
	
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.resetFilters();
		searchResultPage.returnFilter("Evening", softAssert);
		searchResultPage.verifyReturnTimeFilterDomestic("roundtrip", "Evening", softAssert);
		softAssert.assertAll();
	}

	@Test(priority = 67)
	public void srpFilterAirlines() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.verifyAllAirlinesOnSRP("roundtrip", searchResultPage.getAllAirlines(), softAssert);
 
	}

	
	/*@Test(priority = 69)
	public void srpFilterLowestPrice() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage.verifyLowestPriceFilter("roundtrip",searchResultPage.getAllAirlines().get(0), softAssert);
		searchResultPage.resetAllFilters();
	
	}*/

	/*@Test(priority = 70)
	public void srpLayoverDuration() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage.verifyLayoverOnSRP("roundtrip", softAssert);
		searchResultPage.resetFilters();
		
	}

	@Test(priority = 71)
	public void srptripDuration() throws Exception {
		
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.clickAirlineFilter(searchResultPage.getAllAirlines().get(0), softAssert);
		searchResultPage.verifyLowestPriceFilter("round", searchResultPage.getAllAirlines().get(0), softAssert);
		searchResultPage.verifyTripDurationOnSRPRT("roundtrip", softAssert);
		searchResultPage.resetFilters();
	}

	@Test(priority = 72)
	public void srpfirstFlightenabled() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage.verifyFirstFlightPriceEnabled(softAssert);
		softAssert.assertAll();
	}
*/
	@Test(priority = 73)
	public void srpVerifyFlightDetailsForNonStop() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.resetFilters();
		searchResultPage.clickStopsFilter("nonstop", softAssert);
		searchResultPage.collectSRPFlightDetailsDomestic(flightDetails);
		softAssert.assertAll();
	}

	@Test(priority = 74)
	public void srpVerifyFareBreakupDetailsForNonStop() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.collectSRPFareDetails(flowDetailMap);
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	@Test(priority = 75)
	public void srpVerifyCancellationDetailsForNonStop() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.collectCancellationDetails("roundtrip", softAssert);
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	@Test(priority = 76)
	public void srpVerifyBaggageDetailsForNonStop() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.collectBaggageDetails(softAssert);
	}

	@Test(priority = 77)
	public void srpVerifyFlightDetailsFor1Stop() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.closeSelectedFlightDetails();
		searchResultPage.resetFilters();
		searchResultPage.clickStopsFilter("1 stop", softAssert);
		searchResultPage.collectSRPFlightDetailsDomestic(flightDetails);
	}

	@Test(priority = 78)
	public void srpVerifyFareBreakupDetailsFor1Stop() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.collectSRPFareDetails(flowDetailMap);
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	@Test(priority = 79)
	public void srpVerifyCancellationDetailsFor1Stop() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.collectCancellationDetails("roundtrip", softAssert);
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	@Test(priority = 80)
	public void srpVerifyBaggageDetailsFor1Stop() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.collectBaggageDetails(softAssert);
	}

	@Test(priority = 81)
	public void srpVerifyFlightDetailsFor2Stop() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.closeSelectedFlightDetails();
		searchResultPage.resetFilters();
		searchResultPage.clickStopsFilter("2+ stops", softAssert);
		searchResultPage.collectSRPFlightDetailsDomestic(flightDetails);
		softAssert.assertAll();
	}

	@Test(priority = 82)
	public void srpVerifyFareBreakupDetailsFor2Stop() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.collectSRPFareDetails(flowDetailMap);
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	@Test(priority = 83)
	public void srpVerifyCancellationDetailsFor2Stop() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.collectCancellationDetails("roundtrip", softAssert);
		softAssert.assertTrue(true);
		softAssert.assertAll();
	}

	@Test(priority = 84)
	public void srpVerifyBaggageDetailsFor2Stop() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		searchResultPage = new SearchResultPage(driver, test);
		searchResultPage.collectBaggageDetails(softAssert);
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