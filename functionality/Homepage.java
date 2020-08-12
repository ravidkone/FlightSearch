package web.goomo.tests.flights.functionality;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
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

public class Homepage extends WebFlightConnector{
	
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
	
	
	@Test(priority = 1)
	public void searchformVerification() throws Exception {
		//getURL();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("test-type");
		options.addArguments("disable-popup-blocking");
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		//WebDriver driver = new ChromeDriver(capabilities);
		getURL();
		maximizeWindow(driver);
		SoftAssert softAssert = new SoftAssert();
		search = new SearchPage(driver);
		search.HowAboutPopUp();
		search.searchformtab(softAssert);
		softAssert.assertAll();
	}
	
	@Test(priority = 2)
	public void SearchFormOneWayButtonVerification() throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		search.verifyOnewaybutton(softAssert);
		softAssert.assertAll();
	}
	
	
	@Test(priority = 3)
	public void VerifydepartureCity() throws Exception {

		SoftAssert softAssert = new SoftAssert();
		search.departureCityBox(softAssert);
		softAssert.assertAll();
	}

	@Test(priority = 4)
	public void verifyDepartureAutoSuggestionList() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		search.verifyDepartureAutosuggestionList(softAssert);
		softAssert.assertAll();
	}

	@Test(priority = 5)
	public void AutoCompleteDepartureCity() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		search.autoCompleteDeparturecity(softAssert);
		softAssert.assertAll();
	}

	@Test(priority = 6)
	public void ReplaceOldCityToNewCity() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		search.replaceOldCityToNewDeparture(softAssert);
		softAssert.assertAll();
	}

	@Test(priority = 7)
	public void VerifydestinationCity() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		search.verifyDestinationBox(softAssert);
		softAssert.assertAll();
	}

	@Test(priority = 8)
	public void verifyDestinationAutosuggestionList() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		search.verifyDestinationAutosuggestionList(softAssert);
		softAssert.assertAll();
	}

	@Test(priority = 9)
	public void validateDestinationAutoSUggestionList() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		search.validateDestinationAutoSuggestionList(softAssert);
		softAssert.assertAll();
	}

	@Test(priority = 10)
	public void replaceOldCityToNewDestination() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		search.replaceOldCityToNewDestination(softAssert);
		softAssert.assertAll();
	}

	@Test(priority = 11)
	public void autoCompleteDEstinationCity() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		search.autoCompleteDestinationcity(softAssert);
		softAssert.assertAll();
	}

	@Test(priority = 12)
	public void departureCityNotPresentInDestination() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		search.destinationCityNotPresentInDeparture(softAssert);
		softAssert.assertAll();

	}
	
	@Test(priority = 13)
	public void verifyRouteWithReturnDateField() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		search.verifyRouteWithOnewayTourType(softAssert);
		search.verifyWithReturnTourType(softAssert);
		softAssert.assertAll();
	}

	@Test(priority = 14)
	public void verifyDepartureCityCalender() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		search.VerifydepartureCalender(softAssert);
		softAssert.assertAll();
	}

	@Test(priority = 15)
	public void colourPriceCalender() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		search.ColourPriorityCalender(softAssert);
		softAssert.assertAll();
	}

	@Test(priority = 16)
	public void previousDateDisabled() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		search.previousDateDisabled(softAssert);
		softAssert.assertAll();
	}

	@Test(priority = 17)
	public void checkTodayDateInCalender() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		search.checkTodayDateInCalender(softAssert);
		softAssert.assertAll();
	}
	
	@Test(priority = 18)
	public void currentAndFutureDateEnabled() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		search.currentAndFutureDateEnabled(softAssert);
		search.selectDate(softAssert);
		softAssert.assertAll();
		
	}
	

	@Test(priority = 19)
	public void SearchFormReturnButtonVerification() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		search.verifyReturnbutton(softAssert);
		softAssert.assertAll();
	}


	@Test(priority = 20)
	public void wrongIATACodeDepartureCity() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		search.wrongIATACodeDepartureCity(softAssert);
		softAssert.assertAll();
	}

	@Test(priority = 21)
	public void wrongIATACodeDestinationCity() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		search.wrongIATACodeDestinationCity(softAssert);
		softAssert.assertAll();
	}

	@Test(priority = 22)
	public void VerifySearchInputBoxes() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		search.SearchButton(softAssert);
		softAssert.assertAll();
	}

	@Test(priority = 23)
	public void CheckSwappedCity() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		search.verifyclickOnSwap(softAssert);
		softAssert.assertAll();

	}


	@Test(priority = 24)
	public void verifyEconomytravelClass() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		search.verifyEconomyTravelClass(softAssert);
		softAssert.assertAll();
	}

	@Test(priority = 25)
	public void verifyBusinessTravelClass() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		search.verifyBusinessTravelClass(softAssert);
		softAssert.assertAll();
	}

	@Test(priority = 26)
	public void pax() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		search.verifyPassengerCountBox(softAssert);
		softAssert.assertAll();
	}

	@Test(priority = 27)
	public void validateAdultChildInfantSelectedPax() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		search.validateAdultChildInfantSelectedPax("1", "1", "1", "Economy");
		softAssert.assertAll();
	}

	@Test(priority = 28)
	public void infantCountNotExceedAdultCount() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		search.InfantCountNotExceedAdultCount(2);
		softAssert.assertAll();
	}

	@Test(priority = 29)
	public void messageAfterPaxCross9() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		search.messagecheckingofpax();
		softAssert.assertAll();
	}

	@Test(priority = 30)
	public void totalAdultChildCountNotExceedLimit() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		search.totalAdultChildCountNotExceedNine(softAssert);
		softAssert.assertAll();
	}
	
	@Test(priority =31)
	public void paybackTab(){
		
		SoftAssert softAssert = new SoftAssert();
		search.pabackTab(softAssert);
		softAssert.assertAll();				
		
	}
	

	@Test(priority =32)
	public void paybackforgotBtn() throws Exception{
		
		SoftAssert softAssert = new SoftAssert();
		search.paynbackforgotButton(softAssert);
		softAssert.assertAll();	
			
	}
	
	@Test(priority =33)
	public void paybackCheckNowButton() throws Exception{
		
		SoftAssert softAssert = new SoftAssert();
		search.paybackCheckNwBtn(softAssert);
		softAssert.assertAll();	
			
	}
	
	
	
	@Test(priority =34)
	public void paybackErrorMsg() throws Exception{
		
		SoftAssert softAssert = new SoftAssert();
		search.UnfilledFieldErrorMsg(softAssert);
		softAssert.assertAll();	
	
	}
	
	
	@Test(priority =35)
	public void paybackIncorrectNo() throws Exception{
		
		SoftAssert softAssert = new SoftAssert();
		search.verifyInCorrectNo(softAssert);
		softAssert.assertAll();	
	
	}
	
	
	
	@Test(priority =36)
	public void paybackSignIn() throws Exception{
		
		SoftAssert softAssert = new SoftAssert();
		search.verifyPaybackSignIn(softAssert);
		softAssert.assertAll();	
	}
	

	@Test(priority =37)
	public void paybackChangeBtn() throws Exception{
		
		SoftAssert softAssert = new SoftAssert();
		search.verifypaybackChangeBtn(softAssert);
		softAssert.assertAll();	
	}
	
	@Test(priority=38)
	public void TabCredentailsVerification() throws InterruptedException{
		SoftAssert softAssert = new SoftAssert();
		search.ValidateTabsData(softAssert);
		softAssert.assertAll();	
		
	}
	
	@Test(priority = 39)
	public void clickOnSearchButton() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		search.clickOnSearch(softAssert);
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
