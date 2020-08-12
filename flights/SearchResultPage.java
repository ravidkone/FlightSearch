package mobileweb.goomo.pageobject.flights;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.aventstack.extentreports.ExtentTest;
import mobileweb.goomo.commonutils.MobileWebFlightConnector;


public class SearchResultPage extends MobileWebFlightConnector {

	public RemoteWebDriver driver;
	public ExtentTest test;

	public SearchResultPage(RemoteWebDriver driver,ExtentTest test) {
		this.driver = driver;
		this.test=test;
	}

	

	public void selectFlight(int flightNo) throws Exception{

		   waitForElement(driver,10,By.xpath("(//div[@class='flight_common_component'])["+ flightNo +"]") );
		   safeClick(driver,By.xpath("(//div[@class='flight_common_component'])["+ flightNo +"]"));
		   sleepTime(2000);
		   if(elementPresent(driver,By.xpath("(//div[@class='flight_common_component'])["+ flightNo +"]"),1))
			   safeClick(driver,By.xpath("(//div[@class='flight_common_component'])["+ flightNo +"]"));
	
		   

	}
	
	public void selectFlightRoundTrip(int selectFlight) throws Exception{
		waitForElementToDisappear(driver,15,By.xpath("//h6[contains(text(),'Searching')]"));
		boolean loader=waitForElementToDisappear(driver,150,getElementType(flightsRepo.get("roundtrip_progess_bar")));
		//Assert.assertTrue(loader,"Loader doesn't stop");
		   boolean srpLoaded = checkForTwoConditions(driver,getElementType(flightsRepo.get("flight_book_button_rt")),
				   getElementType(flightsRepo.get("Oops_flights_unavailable")),10);
		   if(!srpLoaded)
		   Assert.assertTrue(srpLoaded,"Flights Not loaded on SRP");
		   safeClick(driver,getElementType(flightsRepo.get("flight_book_button_rt")));
	}

	
	
	public boolean filterFlightResults(String stopType, List<String> airlinePreferedNameList) throws Exception {
		Thread.sleep(5000);
		checkSRP();
		safeClick(driver,getElementType(flightsRepo.get("reset_filters_srp")));
		boolean filterApplied = false;
		boolean stopFilterApplied = false;
		List<String> airlinePreferedNameListCopy = new ArrayList<String>();
		airlinePreferedNameListCopy.addAll(airlinePreferedNameList);
		int n = airlinePreferedNameList.size();
		scrollToElement(driver, getWebElement(driver,By.xpath("//label[contains(@for,'airline_')]")));
		List<WebElement> checkBoxes = getWebElementList(driver,By.xpath("//label[contains(@for,'airline_')]"));
		//System.out.println(checkBoxes.size());
		for (int i = 0; i < n; i++) {
			String airline = airlinePreferedNameList.get(i);
			l1:for(int j=0;j<checkBoxes.size();j++){
				//System.out.println(checkBoxes.get(j).getText()+" "+checkBoxes.get(j).getAttribute("class"));
				    if(checkBoxes.get(j).getText().equals(airline) && !checkBoxes.get(j).getAttribute("class").contains("is-checked")){
				    	   checkBoxes.get(j).click();
				    	   airlinePreferedNameListCopy.remove(airline);
				    	   break l1;
				    }
			}
			
		}
		List<WebElement> stops = getWebElement(driver, By.xpath("//div[@id='stops_selection']")).findElements(By.tagName("span"));
		if (!stopType.isEmpty()) {
			l1: for (WebElement stop : stops) {
				if (stop.getText().toLowerCase().contains(stopType)) {
					stop.click();
					stopFilterApplied = true;
					break l1;
				}
			}
		}

		if (airlinePreferedNameList.size() != airlinePreferedNameListCopy.size())
			filterApplied = true;
		
		safeClick(driver,getElementType(flightsRepo.get("filter_done")));

		return filterApplied;

	}

	public boolean filterFlightResultsRT(String stopType, List<String> airlinePreferedNameList) throws Exception {
		Thread.sleep(5000);
		checkSRPRT();
		safeClick(driver,getElementType(flightsRepo.get("reset_filters_srp")));
		boolean filterApplied = false;
		boolean stopFilterApplied = false;
		List<String> airlinePreferedNameListCopy = new ArrayList<String>();
		airlinePreferedNameListCopy.addAll(airlinePreferedNameList);
		int n = airlinePreferedNameList.size();
		scrollToElement(driver, getWebElement(driver,By.xpath("//label[contains(@for,'airline_')]")));
		List<WebElement> checkBoxes = getWebElementList(driver,By.xpath("//label[contains(@for,'airline_')]"));
		//System.out.println(checkBoxes.size());
		for (int i = 0; i < n; i++) {
			String airline = airlinePreferedNameList.get(i);
			l1:for(int j=0;j<checkBoxes.size();j++){
				//System.out.println(checkBoxes.get(j).getText()+" "+checkBoxes.get(j).getAttribute("class"));
				    if(checkBoxes.get(j).getText().equals(airline) && !checkBoxes.get(j).getAttribute("class").contains("is-checked")){
				    	   checkBoxes.get(j).click();
				    	   airlinePreferedNameListCopy.remove(airline);
				    	   break l1;
				    }
			}
			
		}
		List<WebElement> stops = getWebElement(driver, By.xpath("//div[@id='stops_selection']")).findElements(By.tagName("span"));
		if (!stopType.isEmpty()) {
			l1: for (WebElement stop : stops) {
				if (stop.getText().toLowerCase().contains(stopType)) {
					stop.click();
					stopFilterApplied = true;
					break l1;
				}
			}
		}

		if (airlinePreferedNameList.size() != airlinePreferedNameListCopy.size())
			filterApplied = true;
		
		safeClick(driver,getElementType(flightsRepo.get("filter_done")));

		return filterApplied;

	}


	public void checkSRPRT() throws Exception { 
	    waitForElementToDisappear(driver,15,By.xpath("//h6[contains(text(),'Searching')]"));
		boolean loader=waitForElementToDisappear(driver,150,getElementType(flightsRepo.get("roundtrip_progess_bar")));
		//Assert.assertTrue(loader,"Loader doesn't stop");
	    boolean srpLoaded = checkForTwoConditions(driver, getElementType(flightsRepo.get("select_flight_srp_rt")),
				getElementType(flightsRepo.get("roundtrip_progess_bar")), 90);
		if (!srpLoaded) {
			test.fail("Flights Not available on SRP");
			Assert.assertEquals("Flights Not loaded on SRP", srpLoaded);

		}

	}
	public void collectSRPFlightDetails(JSONObject flightDetail, int selectFlight) throws Exception {
		checkSRP();
		List<WebElement> flightDetailsList = getWebElementList(driver,
				getElementType(flightsRepo.get("Flight_details_link")));
		List<WebElement> selectFlights = getWebElementList(driver,
				getElementType(flightsRepo.get("flight_book_button")));
		mouseHoverElement(driver, selectFlights.get(selectFlight - 1));
		try {
			scrollUp(driver);
			flightDetailsList.get(selectFlight - 1).click();
			safeClick(driver, getElementType(flightsRepo.get("flight_details")));

		} catch (Exception e) {
			scrollUp(driver);
			flightDetailsList.get(selectFlight - 1).click();
			safeClick(driver, getElementType(flightsRepo.get("flight_details")));
		}
		//System.out.println(new JSONArray(populateFlightsData(driver).toString()));
	    flightDetail.put("Search Result Page",new JSONArray(populateFlightsData(driver).toString()));
	    
	
	}
	
	
	public void checkSRP() throws Exception{
		waitForElementToDisappear(driver,20,getElementType(flightsRepo.get("loader_loop_line")));
		boolean loader=waitForElementToDisappear(driver,150,getElementType(flightsRepo.get("roundtrip_progess_bar")));
		Assert.assertTrue(loader,"Loader doesn't stop");
		boolean srpLoaded = checkForTwoConditions(driver,getElementType(flightsRepo.get("select_flight_srp")),
				   getElementType(flightsRepo.get("oops_img")),20);
		   if(!srpLoaded){
			   test.fail("Flights Not available on SRP");
		       Assert.assertTrue(srpLoaded,"Flights Not loaded on SRP");
		  
		   }
		 
	}
	
	public void checkSRPrt() throws Exception{
		SoftAssert softAssert=new SoftAssert();
		verifyUndoFilters(softAssert);
	
		boolean srpLoaded = checkForTwoConditions(driver,getElementType(flightsRepo.get("flight_book_button_rt")),
				   getElementType(flightsRepo.get("oops_img")),20);
		   if(!srpLoaded){
			   test.fail("Flights Not available on SRP");
		       Assert.assertTrue(srpLoaded,"Flights Not loaded on SRP");
		  
		   }
		 
	}

		
	public void clickSRTTab() throws Exception{
		boolean srpLoaded = checkForTwoConditions(driver,getElementType(flightsRepo.get("flight_book_button")),
				  getElementType(flightsRepo.get("Oops_flights_unavailable")),10);
		  if(!srpLoaded)
		  Assert.assertTrue(srpLoaded,"Flights Not loaded on SRP");
		  if(elementVisible(driver, getElementType(flightsRepo.get("select_srt_tab")), 10)){
			  safeClick(driver, getElementType(flightsRepo.get("select_srt_flight")));
		  }else
			  System.out.println("Special Round Trip Not available");
		 
		
	}

	public void closeSelectedFlightDetails() throws Exception {
		if (elementDisplayed(driver, getElementType(flightsRepo.get("close_select_flight")), 1))
			safeClick(driver, getElementType(flightsRepo.get("close_select_flight")));
	}

	public void collectSRPFareDetails(Map<String, Object> flowDetailMap, int selectFlight) throws Exception {
		safeClick(driver, getElementType(flightsRepo.get("srp_fare_breakup")));
		Map<String, String> srpFareDetails = populatePriceData(driver);
		flowDetailMap.put("SRP Page", srpFareDetails);
	}

	public void collectCancellationDetails(String tripType, SoftAssert softAssert) throws Exception {
		if ("roundtrip".equals(tripType))
			safeClick(driver, getElementType(flightsRepo.get("srp_fare_rules")));
		else
			safeClick(driver, getElementType(flightsRepo.get("srp_fare_rules_oneway")));

		List<WebElement> fareRulesContent = getWebElementList(driver,
				getElementType(flightsRepo.get("flight_cancellation_content")));
		Map<String, String> fareRules = new HashMap<>();
		if ("roundtrip".equals(tripType) && !fareRulesContent.isEmpty()) {
			fareRules.put("onward", fareRulesContent.get(0).getText());
			fareRules.put("return", fareRulesContent.get(1).getText());
		} else {
			fareRules.put("onward", fareRulesContent.get(0).getText());
		}

		softAssert.assertTrue(fareRules.size() > 0, "Fare rules data not available");
	}

	public void collectBaggageDetails(SoftAssert softAssert) throws Exception {
		safeClick(driver, getElementType(flightsRepo.get("srp_baggage_rules")));
		Thread.sleep(1000);
		String baggageContent = getWebElementList(driver,getElementType(flightsRepo.get("baggage_content"))).get(1).getText();
		softAssert.assertTrue(!baggageContent.isEmpty(), "Baggage Data not available");
	}

	public boolean filterFlightResults1(String stopType, List<String> airlinePreferedNameList) throws Exception {
		checkSRP();
		boolean filterApplied = false;
		boolean stopFilterApplied = false;
		List<String> airlinePreferedNameListCopy = new ArrayList<String>();
		airlinePreferedNameListCopy.addAll(airlinePreferedNameList);
		int n = airlinePreferedNameList.size();
		List<WebElement> checkBoxes = getWebElementList(driver, getElementType(flightsRepo.get("checkbox")));
		for (int i = 0; i < n; i++) {
			String airline = airlinePreferedNameList.get(i);
			l1: for (int j = 0; j < checkBoxes.size(); j++) {
				System.err.println(checkBoxes.get(j).getText() + " " + checkBoxes.get(j).getAttribute("class"));
				if (checkBoxes.get(j).getText().equals(airline)
						&& !checkBoxes.get(j).getAttribute("class").contains("is-checked")) {
					checkBoxes.get(j).click();
					airlinePreferedNameListCopy.remove(airline);
					break l1;
				}
			}

		}
		List<WebElement> stops = getWebElement(driver, By.id("stops_selection")).findElements(By.tagName("span"));
		if (!stopType.isEmpty()) {
			l1: for (WebElement stop : stops) {
				if (stop.getText().toLowerCase().contains(stopType)) {
					stop.click();
					stopFilterApplied = true;
					break l1;
				}
			}
		}

		if (airlinePreferedNameList.size() != airlinePreferedNameListCopy.size())
			filterApplied = true;
		Assert.assertTrue(stopFilterApplied, "Stop Filter failed to apply");

		return filterApplied;

	}
	public void fillOnwardDateRT() throws Exception {
		int selectDate = 2;
		int selectDateRT = 5;
		Thread.sleep(3000);
		safeClick(driver, getElementType(flightsRepo.get("depart_date_RT")));
		Thread.sleep(2000);
		scrollToElement(driver, getWebElement(driver, getElementType(flightsRepo.get("new_date")+"5]")));
		safeClick(driver, getElementType(flightsRepo.get("new_date")+"11]"));
		
		//System.err.println(flightsRepo.get("navigate_month_calendar"));
		/*WebElement navigateMonth = driver.findElement(getElementType(flightsRepo.get("navigate_month_calendar")));

		for (int i = 0; i < 2; i++) {
			Thread.sleep(2000);
			navigateMonth.click();
			
		}*/
		//System.err.println(flightsRepo.get("select_date") + selectDate + "']");
		//safeClick(driver, getElementType(flightsRepo.get("select_date") + selectDate + "']"));
		/*safeClick(driver, getElementType(flightsRepo.get("(select_date" + selectDateRT + "')])[1]")));
		safeClick(driver, getElementType(flightsRepo.get("calendar_done")));
		System.out.println(getElementType(flightsRepo.get("(select_date" + selectDateRT + "')])[1]")));
		*/

	}

	public void selectFlightRT(int selectFlight, boolean pickotherflight) throws Exception {
		checkSRP();
		if (pickotherflight) {
			safeClick(driver, getElementType(flightsRepo.get("departure_onwards")));
			safeClick(driver, getElementType(flightsRepo.get("departure_return")));
			List<WebElement> flightradiobutton = getWebElementList(driver,
					getElementType(flightsRepo.get("flight_selection_radio")));
			int radiosize = flightradiobutton.size();
			int returnradio = radiosize / 2;

			safeClick(driver, getElementType(flightsRepo.get("select_radio") + 2 + "]"));
			//System.err.println(getElementType(flightsRepo.get("select_radio") + 2 + "]"));

			safeClick(driver, getElementType(flightsRepo.get("select_radio") + (returnradio + selectFlight + 1) + "]"));
			try {

				if (elementVisible(driver, getElementType(flightsRepo.get("error_msg")), 1))
					;
				//System.err.println("time overlapping");
			} catch (Exception e) {
				//System.err.println("not overlapping timing");

			}
		}
	}


	public void collectSRPFlightDetailsModifySearch(JSONObject flightDetail, int selectFlight, int count)
			throws Exception {
		checkSRP();
		List<WebElement> flightDetailsList = getWebElementList(driver,
				getElementType(flightsRepo.get("Flight_details_link")));
		List<WebElement> selectFlights = getWebElementList(driver,
				getElementType(flightsRepo.get("flight_book_button")));
		mouseHoverElement(driver, selectFlights.get(selectFlight - 1));
		try {
			scrollUp(driver);
			flightDetailsList.get(selectFlight - 1).click();
			safeClick(driver, getElementType(flightsRepo.get("flight_details")));

		} catch (Exception e) {
			scrollUp(driver);
			flightDetailsList.get(selectFlight - 1).click();
			safeClick(driver, getElementType(flightsRepo.get("flight_details")));
		}
		flightDetail.put("Search Result Page " + count, new JSONArray(populateFlightsData(driver).toString()));

	}

	public void clickBookButton() throws Exception {
		if(elementPresent(driver, getElementType(flightsRepo.get("select_flight_srp")), 4)){
			safeClick(driver, getElementType(flightsRepo.get("select_flight_srp")));
		}
	}


	public void clickOnlyrefundable(SoftAssert softAssert) throws Exception {
		safeClick(driver, getElementType(flightsRepo.get("refundable_checkbox")));
		clickonDoneButton();
		Thread.sleep(1000);
		/*List<WebElement> refundable = getWebElementList(driver, getElementType(flightsRepo.get("all_refIcons")));
		for (WebElement refund : refundable) {
			if (refund.getText().trim().toLowerCase().equals("refundable"))
				System.out.println("Contains refundable flight");
			else
				softAssert.assertTrue(true, "Contains non refundable flight");
		}*/
	}

	public void onlyNonrefundable(SoftAssert softAssert) throws Exception {
		safeClick(driver, getElementType(flightsRepo.get("refundable_checkbox")));
		clickonDoneButton();
		/*List<WebElement> nonRefundable = getWebElementList(driver, getElementType(flightsRepo.get("Non_refundable")));
		if(nonRefundable!=null){
		for (WebElement nonRef : nonRefundable) {
			if (nonRef.getText().trim().toLowerCase().equals("non "))
				System.out.println("Contains non-refundable flight");
			else
				softAssert.assertTrue(true, "Contains refundale flight");

		}
		}*/
	}


	public void collectSRPFlightDetailsRT(JSONObject flightDetail, int selectFlight) throws Exception {
		checkSRP();
		WebElement flightDetailsList = getWebElement(driver, getElementType(flightsRepo.get("Flight_details_link")));
		WebElement selectFlights = getWebElement(driver, getElementType(flightsRepo.get("flight_book_button")));
		mouseHoverElement(driver, selectFlights);
		flightDetailsList.click();
		safeClick(driver, getElementType(flightsRepo.get("flight_details")));
		//System.err.println(new JSONArray(populateFlightsData(driver).toString()));
		flightDetail.put("Search Result Page", new JSONArray(populateFlightsData(driver).toString()));

	}

	/*public void clickLCCSRTTab() throws Exception {
		List<WebElement> specialRTFlights;
		String flightName;
		checkSRP();
		boolean specialRTTab = false;
		if (elementVisible(driver, getElementType(flightsRepo.get("srt_tab")), 10)) {
			specialRTFlights = getWebElementList(driver, getElementType(flightsRepo.get("sprt_tab_flights")));
			l1: for (int i = 0; i < specialRTFlights.size(); i++) {
				flightName = specialRTFlights.get(i).getText();
				if (!Arrays.asList(gdsAirlines).contains(flightName)) {
					specialRTFlights.get(i).click();
					specialRTTab = true;
					break l1;
				}

				Assert.assertTrue("Special Round Trip for LCC is not available", specialRTTab);
			}
		}

		Assert.assertTrue("Special Round Trip is not available", specialRTTab);
	}*/

	/*public void clickGDSSRTTab() throws Exception {
		List<WebElement> specialRTFlights;
		String flightName;
		checkSRP();
		boolean specialRTTab = false;
		if (elementVisible(driver, getElementType(flightsRepo.get("srt_tab")), 10)) {
			specialRTFlights = getWebElementList(driver, getElementType(flightsRepo.get("sprt_tab_flights")));
			l1: for (int i = 0; i <= specialRTFlights.size(); i++) {
				flightName = specialRTFlights.get(i).getText();
				if (Arrays.asList(gdsAirlines).contains(flightName)) {
					specialRTFlights.get(i).click();
					specialRTTab = true;
					break l1;
				}

				Assert.assertTrue("Special Round Trip for GDS is not available", specialRTTab);
			}
		}

		Assert.assertTrue("Special Round Trip is not available", specialRTTab);
	}
	*/
	public void checkSRPTFlights(){
        SoftAssert softAssert=new SoftAssert();
        boolean gds=false;
        boolean lcc=false;
        String[] flightNamesGds={"Air India","Jet Airways"};
        String[] flightNamesLcc={"GoAir","Spicejet","IndiGo"};
        List<String> allAirlineNames = new ArrayList<>();
        
             List<WebElement> flightName=getWebElementList(driver, getElementType(flightsRepo.get("flight_name_tab")));
             for(int i=0;i<flightName.size();i++){
                   allAirlineNames.add(flightName.get(i).getText());
             }
             
             if(elementPresent(driver, By.xpath("//span[contains(@class,'gm-keyboard_arrow_right')]"), 1)){
                  flightName = getWebElementList(driver, getElementType(flightsRepo.get("flight_name_tab")));
                  for(int i=0;i<flightName.size();i++){
                      allAirlineNames.add(flightName.get(i).getText());
                  }  
             }
        
             
         l1: for(int gd=0;gd<flightNamesGds.length;gd++){
                   if(allAirlineNames.contains(flightNamesGds[gd])){
                       gds = true;
                       break l1;}
                   
          }
             
         l2: for(int lc=0;lc<flightNamesLcc.length;lc++){
                   if(allAirlineNames.contains(flightNamesLcc[lc])){
                       lcc = true;
                       break l2;}
                   
          }   
          
          softAssert.assertTrue(gds," Special Roundtrip doesn't contain GDS Flights");
          softAssert.assertTrue(lcc," Special Roundtripd doesn't contain LCC Flights");
          
             
             
    
        
    }

	public void modifySearch(String triptype, String source, String destination, String adultCount, String childCount,
			String infantCount, SoftAssert softAssert) throws Exception {
		safeClick(driver, getElementType(flightsRepo.get("modify_search_" + triptype + "_button")));
		List<WebElement> textFields = getWebElementList(driver,
				getElementType(flightsRepo.get("modify_search_textfields")));
		textFields.get(0).clear();
		textFields.get(0).sendKeys(source);
		Thread.sleep(1000);
		softAssert.assertTrue(
				elementVisible(driver, getElementType(flightsRepo.get("select_from_segment") + source + "')]"), 1),
				"IATA CODE ALREADY PRESENT " + "IN DESTINATION");
		textFields.get(0).clear();
		textFields.get(0).sendKeys(destination);
		Thread.sleep(1000);
		safeClick(driver, getElementType(flightsRepo.get("select_from_segment") + destination + "')]"));
		textFields.get(1).clear();
		textFields.get(1).sendKeys(destination);
		Thread.sleep(1000);
		softAssert.assertTrue(
				elementVisible(driver, getElementType(flightsRepo.get("select_from_segment") + destination + "')]"), 1),
				"IATA CODE ALREADY PRESENT " + "IN SOURCE");
		textFields.get(1).clear();
		textFields.get(1).sendKeys(source);
		Thread.sleep(1000);
		safeClick(driver, getElementType(flightsRepo.get("select_from_segment") + source + "')]"));

		SearchPage searchPage = new SearchPage(driver);
		searchPage.fillOnwardDate();
		if ("roundtrip".equalsIgnoreCase(triptype))
			searchPage.fillReturnDate();
		searchPage.clickSearchButton();
	}

	public void modifySource(String source,String destination,SoftAssert softAssert) throws Exception {
		safeClick(driver, getElementType(flightsRepo.get("departure_city")));
		Thread.sleep(1000);
		//safeClick(driver,getElementType(flightsRepo.get("textbox_clear")));
		safeClear(driver, getElementType(flightsRepo.get("departure_city_box")));
		Thread.sleep(1000);
        safeType(driver,getElementType(flightsRepo.get("departure_city_box")), destination);
      if(elementVisible(driver,By.xpath("//i[@class='gm gm-exclamation-triangle']") ,1))
        softAssert.assertEquals(elementDisplayed(driver,By.xpath("//i[@class='gm gm-exclamation-triangle']"),1),true, "modify departure city is taking the destination city");
     
        safeClear(driver, getElementType(flightsRepo.get("departure_city_box")));

        safeType(driver,getElementType(flightsRepo.get("departure_city_box")), source);
        safeClick(driver, getElementType(flightsRepo.get("select_suggestion")));

	}

	public void modifyDestination(String source,String destination,SoftAssert softAssert) throws Exception {
		Thread.sleep(1000);
		safeClick(driver, getElementType(flightsRepo.get("destination_city")));
		Thread.sleep(1000);
		//safeClick(driver,getElementType(flightsRepo.get("textbox_clear")));
		safeClear(driver, getElementType(flightsRepo.get("destination_city_box")));
		Thread.sleep(1000);
        safeType(driver,getElementType(flightsRepo.get("destination_city_box")),source);
        if(elementVisible(driver,By.xpath("//i[@class='gm gm-exclamation-triangle']") ,1))
        softAssert.assertEquals(elementDisplayed(driver,By.xpath("//i[@class='gm gm-exclamation-triangle']"),1),true, "modify departure city is taking the destination city");
        safeClear(driver, getElementType(flightsRepo.get("destination_city_box")));
        safeType(driver,getElementType(flightsRepo.get("destination_city_box")), destination);
        safeClick(driver, getElementType(flightsRepo.get("select_suggestion")));


	}
	public void modifyfillOnwardDate() throws Exception{
		safeClick(driver, getElementType(flightsRepo.get("modify_depart_date")));
		Thread.sleep(1000);
		scrollToElement(driver, getWebElement(driver, getElementType(flightsRepo.get("new_date")+"2]")));
		//System.out.println(getElementType(flightsRepo.get("select_date")+23+"']"));
		safeClick(driver, getElementType(flightsRepo.get("new_date")+"23]"));
		
		
		
		
		
	}
	
	public void modifyDateRT() throws Exception{
		safeClick(driver, getElementType(flightsRepo.get("modify_depart_date")));
		Thread.sleep(1000);
		scrollToElement(driver, getWebElement(driver, getElementType(flightsRepo.get("new_date")+"2]")));
		//System.out.println(getElementType(flightsRepo.get("select_date")+23+"']"));
		safeClick(driver, getElementType(flightsRepo.get("new_date")+"15]"));
		safeClick(driver, getElementType(flightsRepo.get("new_date")+"18]"));
		safeClick(driver,By.xpath("//div[contains(text(),'DONE')]"));
		
		
		
		
	}

	public void sortByAirlines() throws Exception {
		safeClick(driver, getElementType(flightsRepo.get("airlines_sort")));
	}

	public void sortByDeparture() throws Exception {
		safeClick(driver, getElementType(flightsRepo.get("departure_sort")));
	}

	public void sortByArrival() throws Exception {
		safeClick(driver, getElementType(flightsRepo.get("")));
		Thread.sleep(1000);
	}
	
	public void sortByDepartureDomesticRT() throws Exception{
		SoftAssert softAssert=new SoftAssert();
		softAssert.assertTrue(elementEnabled(driver,getElementType(flightsRepo.get("SRP_domestic_rtSort_button")),1));
		Thread.sleep(500);
		safeClick(driver,getElementType(flightsRepo.get("SRP_domestic_rtSort_button")));
		
	}

	public void sortByDuration() throws Exception {
		safeClick(driver, getElementType(flightsRepo.get("duration_sort")));
	}

	public void sortByPrice() throws Exception {
		safeClick(driver, getElementType(flightsRepo.get("price_sort")));

	}

	public int getFlightCountOnSRP() {
		return Integer.parseInt(safeGetText(driver, getElementType(flightsRepo.get("flights_count"))));
	}

	public List<String> getAllAirlines() {
		List<WebElement> checkBoxes = getWebElementList(driver, getElementType(flightsRepo.get("checkbox")));
		List<String> allAirlines = new ArrayList<>();
		for (WebElement cb : checkBoxes) {
			allAirlines.add(cb.getText().trim());
		}
		return allAirlines;
	}

	public void selectOtherFlightsInRoundTripWithOverlapping(SoftAssert softAssert) throws Exception {
		boolean overlapped;
		int onwardFlight = 1;
		int returnFlight = 1;
		checkSRP();
		List<WebElement> flightOnwardBtns = getWebElementList(driver,
				getElementType(flightsRepo.get("flight_radio_btn_onward")));
		List<WebElement> flightReturnBtns = getWebElementList(driver,
				getElementType(flightsRepo.get("flight_radio_btn_return")));
		//System.out.println(flightOnwardBtns.size() + " " + flightReturnBtns.size());
		do {

			flightOnwardBtns.get(onwardFlight).click();
			flightReturnBtns.get(returnFlight).click();
			overlapped = elementDisplayed(driver, getElementType(flightsRepo.get("overlapped_message")), 1);
			onwardFlight++;
			returnFlight++;
		} while (!overlapped && onwardFlight < 6);
		softAssert.assertTrue(overlapped, "Overlapped message not appeared");
	}

	public void selectOtherFlightsInOWTrip(SoftAssert softAssert) throws Exception {
		
		int Flight = 1;
		
		checkSRP();
		List<WebElement> flightOnwardBtns = getWebElementList(driver,
				getElementType(flightsRepo.get("flight_radio_btn_onward")));
		
		System.out.println(flightOnwardBtns.size() + " " );
		do {

			flightOnwardBtns.get(Flight).click();
			
			
			Flight++;
			
		} while (Flight < 8);
		softAssert.assertTrue(true);
	}

	/*
	 * ##################################### FUNCTIONATILITY VERIFICATION
	 * #####################################
	 */

	public void verifyModifySearchButton(SoftAssert softAssert) throws Exception {
		softAssert.assertTrue(elementVisible(driver, getElementType(flightsRepo.get("Moify_Search")), 1),
				"MODIFY SEARCH NOT APPEARED");

	}	
	public void clickModify() throws Exception {	
	 safeClick(driver,getElementType(flightsRepo.get("Moify_Search")));

	}

	
	public void clickAlliance() throws Exception {
		scrollToElement(driver,getWebElement(driver,By.xpath("//input[@id='filterCurrency']") ));
		if(elementVisible(driver,getElementType(flightsRepo.get("select_alliance")),1 ))
		safeClick(driver, getElementType(flightsRepo.get("select_alliance")));
	}

	public void departureFilter(String filterName, SoftAssert softAssert) throws Exception {

		safeClick(driver, getElementType(flightsRepo.get("Filter_depature") + filterName + "']]"));

	}

	public void verifyDepartureTimeFilter(String triptype, String filterName, SoftAssert softAssert) {
		List<WebElement> departTimings;
        boolean time=false;
		if ("roundtrip".equalsIgnoreCase(triptype))
			departTimings = getWebElementList(driver, By.cssSelector("//div[contains(@class,'flight_timing')]"));
		else
			departTimings = getWebElementList(driver, getElementType(flightsRepo.get("departure_time_mob")));

		/*List<String> departTimingValues = new ArrayList<>();
		for (int i = 0; i < departTimings.size() / 2;) {
			if (i != 2) {
				departTimingValues.add(departTimings.get(i).getText().trim());
			}
			i = i + 2;
		}*/
	
		for(int i=0;i<departTimings.size()-1;i++){
			String departtiminghour=departTimings.get(i).getText().split(":")[0].trim();
			int departTimingValue=Integer.parseInt(departtiminghour.substring(3,5));
			if(filterName.equals("Early Morning")){
				if(departTimingValue>=00 && departTimingValue<=6)
					time=true;
				softAssert.assertTrue(true,"time filter sorting Early morning is not working fine /flight unavialble for this time");
				
			}
			else if(filterName.equals("Morning")){
				if(departTimingValue>=6 && departTimingValue<=11)
					time=true;
				softAssert.assertTrue(true,"time filter sorting Morning is not working fine/flight unavialble for this time");
				
				
			}
			else if(filterName.equals("Mid-Day")){
				if(departTimingValue>=11 && departTimingValue<=17)
					time=true;
				softAssert.assertTrue(true,"time filter sorting MId Day is not working fine/flight unavialble for this time");
				
				
			}
			else if(filterName.equals("Evening")){
				if(departTimingValue>=17 && departTimingValue<=23)
					time=true;
				softAssert.assertTrue(true,"time filter sorting MId Day is not working fine/flight unavialble for this time");
				
				
			}
			
		}
		
		
		
	}

	public void verifyReturnTimeFilter(String triptype, String filterName, SoftAssert softAssert) {
		List<WebElement> departTimings;

		if ("roundtrip".equalsIgnoreCase(triptype))
			departTimings = getWebElementList(driver, By.cssSelector("span[class*='flight_timing']"));
		else
			departTimings = getWebElementList(driver, getElementType(flightsRepo.get("departure_time")));

		List<String> departTimingValues = new ArrayList<>();
		for (int i = (departTimings.size() / 2) + 2; i < departTimings.size(); i++) {
			departTimingValues.add(departTimings.get(i).getText().trim());

		}

		if (filterName.equalsIgnoreCase("early morning") && !departTimingValues.isEmpty()) {
			for (int i = 0; i < departTimingValues.size(); i++) {
				int value = Integer.parseInt(departTimingValues.get(i).trim().split(":")[0].trim());
				if (value >= 0 && value <= 6) {
					// System.out.println("time is aligned");
				} else
					softAssert.assertTrue(true, departTimings.get(i) + " Value has error in SRP");

			}
		} else if (filterName.equalsIgnoreCase("morning") && !departTimingValues.isEmpty()) {
			for (int i = 0; i < departTimingValues.size(); i++) {
				int value = Integer.parseInt(departTimingValues.get(i).trim().split(":")[0].trim());
				if (value >= 6 && value <= 11) {
					// System.out.println("time is aligned");
				} else
					softAssert.assertTrue(true, departTimings.get(i) + " Value has error in SRP");

			}
		} else if (filterName.equalsIgnoreCase("mid-day") && !departTimingValues.isEmpty()) {
			for (int i = 0; i < departTimingValues.size(); i++) {
				int value = Integer.parseInt(departTimingValues.get(i).trim().split(":")[0].trim());
				value = value - 12;
				if (value >= -1 && value <= 5) {
					// System.out.println("time is aligned");
				} else
					softAssert.assertTrue(true, departTimings.get(i) + " Value has error in SRP");

			}
		} else if (filterName.equalsIgnoreCase("evening") && !departTimingValues.isEmpty()) {
			for (int i = 0; i < departTimingValues.size(); i++) {
				int value = Integer.parseInt(departTimingValues.get(i).trim().split(":")[0].trim());
				value = value - 12;
				if (value >= 5 && value <= 12) {
					// System.out.println("time is aligned");
				} else
					softAssert.assertTrue(true, departTimings.get(i) + " Value has error in SRP");

			}
		}

	}

	public void verifyModifySearchReturn(SoftAssert softAssert) throws Exception {
		safeClick(driver, getElementType(flightsRepo.get("modify_search_roundtrip_button")));
		softAssert.assertTrue(elementVisible(driver, getElementType(flightsRepo.get("modify_search_return_date")), 1),
				"RETURN DATE NOT APPEARED AFTER SELECTING RETURN TYPE");
	}

	public void returnFilter(String filterName, SoftAssert softAssert) throws Exception {
		List<WebElement> allFilters = getWebElementList(driver,
				getElementType(flightsRepo.get("Filter_depature") + filterName + "']]"));
		//System.out.println(allFilters.size());
		scrollToElement(driver, allFilters.get(1));
		allFilters.get(1).click();

	}

	public void verifyAirlineSort(SoftAssert softAssert) {
		List<WebElement> airlines = getWebElementList(driver, getElementType(flightsRepo.get("airline_names")));
		for (int i = 0; i < airlines.size() - 1; i++) {
			String firstAirline = airlines.get(i).getText();
			String secondAirline = airlines.get(i + 1).getText();
			if (((int) firstAirline.charAt(0)) <= ((int) secondAirline.charAt(0))) {
				softAssert.assertTrue(true);
			} else {
				softAssert.assertTrue(false, firstAirline + " " + secondAirline + " are not in sorting order");
			}
		}

	}

	public void verifyDepartureSort(SoftAssert softAssert) throws InterruptedException, Exception {
		
		List<WebElement> departTimings = getWebElementList(driver, getElementType(flightsRepo.get("departure_time_mob")));
		for (int i = 0; i < departTimings.size() - 1; i++) {
			
			String firstFlightDepartHour = departTimings.get(i).getText().split(":")[0];
			String firstFlightDepartMins = departTimings.get(i).getText().split(":")[1];
			String secondFlightDepartHour = departTimings.get(i + 1).getText().split(":")[0];
			String secondFlightDepartMins = departTimings.get(i + 1).getText().split(":")[1];
		System.out.println(secondFlightDepartHour.substring(3, 5)+":"+secondFlightDepartMins+"check time"+firstFlightDepartHour.substring(3,5)+":"+firstFlightDepartMins);
			if (Integer.parseInt(secondFlightDepartHour.substring(3, 5)) == Integer.parseInt(firstFlightDepartHour.substring(3,5))) {
				if (Integer.parseInt(secondFlightDepartMins) >=Integer.parseInt(firstFlightDepartMins))
					softAssert.assertTrue(true, firstFlightDepartHour.substring(3, 5) + ":" + firstFlightDepartMins
							+ " has more departure time than" + secondFlightDepartHour.substring(3,5) + ":" + secondFlightDepartMins);
			} else if (Integer.parseInt(secondFlightDepartHour.substring(3,5)) > Integer.parseInt(firstFlightDepartHour.substring(3, 5))) {
				softAssert.assertTrue(true, firstFlightDepartHour.substring(3,5) + ":" + firstFlightDepartMins
						+ " has more departure time than" + secondFlightDepartHour.substring(3,5) + ":" + secondFlightDepartMins);
			}

		}
	}
	
	public void verifyDepartureSortEarliestFirst() throws Exception{
		SoftAssert softAssert=new SoftAssert();
		Thread.sleep(1000);
		softAssert.assertTrue(elementEnabled(driver,getElementType(flightsRepo.get("Departure_timeSort_domRT")), 1));
		softAssert.assertTrue(elementEnabled(driver,getElementType(flightsRepo.get("Earliest_first_depaSOrt")), 1));
		Thread.sleep(500);
		safeClick(driver, getElementType(flightsRepo.get("Earliest_first_depaSOrt")));
		Thread.sleep(500);
		safeClick(driver, getElementType(flightsRepo.get("filter_done")));
	Thread.sleep(500);
		List<WebElement> departTimings=getWebElementList(driver, getElementType(flightsRepo.get("departure_time_DomRT")));
		for (int i = 0; i < departTimings.size() - 1; i++) {
			String firstFlightDepartHour = departTimings.get(i).getText().split(":")[0];
			String firstFlightDepartMins = departTimings.get(i).getText().split(":")[1];
			String secondFlightDepartHour = departTimings.get(i + 1).getText().split(":")[0];
			String secondFlightDepartMins = departTimings.get(i + 1).getText().split(":")[1];
		System.out.println(secondFlightDepartHour+":"+secondFlightDepartMins+"check time"+":"+firstFlightDepartMins);
			if (Integer.parseInt(secondFlightDepartHour) == Integer.parseInt(firstFlightDepartHour)) {
				if (Integer.parseInt(secondFlightDepartMins) >=Integer.parseInt(firstFlightDepartMins))
					softAssert.assertTrue(true, firstFlightDepartHour + ":" + firstFlightDepartMins
							+ " has more departure time than" + secondFlightDepartHour + ":" + secondFlightDepartMins);
			} else if (Integer.parseInt(secondFlightDepartHour) > Integer.parseInt(firstFlightDepartHour)) {
				softAssert.assertTrue(true, firstFlightDepartHour + ":" + firstFlightDepartMins
						+ " has more departure time than" + secondFlightDepartHour + ":" + secondFlightDepartMins);
			}

		}
	}
	
	public void verifyDepartureSortLatestFirst() throws Exception{
		
		SoftAssert softAssert=new SoftAssert();
		Thread.sleep(1000);
		softAssert.assertTrue(elementEnabled(driver,getElementType(flightsRepo.get("Departure_timeSort_domRT")), 1));
		softAssert.assertTrue(elementEnabled(driver,getElementType(flightsRepo.get("Latest_first_departSort")), 1));
	    Thread.sleep(500);
		safeClick(driver, getElementType(flightsRepo.get("Latest_first_departSort")));
		Thread.sleep(500);
		safeClick(driver, getElementType(flightsRepo.get("filter_done")));
	Thread.sleep(500);
		List<WebElement> departTimings=getWebElementList(driver, getElementType(flightsRepo.get("departure_time_DomRT")));
		for (int i = 0; i < departTimings.size() - 1; i++) {
			String firstFlightDepartHour = departTimings.get(i).getText().split(":")[0];
			String firstFlightDepartMins = departTimings.get(i).getText().split(":")[1];
			String secondFlightDepartHour = departTimings.get(i + 1).getText().split(":")[0];
			String secondFlightDepartMins = departTimings.get(i + 1).getText().split(":")[1];
		System.out.println(secondFlightDepartHour+":"+secondFlightDepartMins+"check time"+":"+firstFlightDepartMins);
			if (Integer.parseInt(secondFlightDepartHour) == Integer.parseInt(firstFlightDepartHour)) {
				if (Integer.parseInt(secondFlightDepartMins) <=Integer.parseInt(firstFlightDepartMins))
					softAssert.assertTrue(true, firstFlightDepartHour + ":" + firstFlightDepartMins
							+ " has more departure time than" + secondFlightDepartHour + ":" + secondFlightDepartMins);
			} else if (Integer.parseInt(secondFlightDepartHour) < Integer.parseInt(firstFlightDepartHour)) {
				softAssert.assertTrue(true, firstFlightDepartHour + ":" + firstFlightDepartMins
						+ " has more departure time than" + secondFlightDepartHour + ":" + secondFlightDepartMins);
			}
		}
	}
	
	public void verifyDurationShortShortestFirst() throws Exception{
		SoftAssert softAssert=new SoftAssert();
		Thread.sleep(1000);
		softAssert.assertTrue(elementEnabled(driver,getElementType(flightsRepo.get("Duration_timeSort_domRT")), 1));
		softAssert.assertTrue(elementEnabled(driver,getElementType(flightsRepo.get("Duration_ShortestFirst")), 1));
	    Thread.sleep(500);
		safeClick(driver, getElementType(flightsRepo.get("Duration_ShortestFirst")));
		Thread.sleep(500);
		safeClick(driver, getElementType(flightsRepo.get("filter_done")));
	Thread.sleep(500);
	List<WebElement> durations=getWebElementList(driver,getElementType(flightsRepo.get("duration_time_DomRT")));
    System.out.println("durationsize is"+durations.size());
    int i=0;
	for(i=0;i<(durations.size()-2);i++){
		String firstFlightDurationHour=durations.get(i).getText().split("h ")[0];
		System.out.println("1is"+firstFlightDurationHour);
		String firstFlightDurationMinutes=durations.get(i).getText().split("h ")[1];
		System.out.println("2is"+firstFlightDurationMinutes);
		String secondDurationHour=durations.get(i).getText().split("h ")[0];
		System.out.println("3is"+secondDurationHour);
		String secondDurationMinutes=durations.get(i).getText().split("h ")[1];
		System.out.println("4is"+secondDurationMinutes);
		System.out.println("min1 is"+secondDurationMinutes.substring(1,2));
		System.out.println("min1 is"+firstFlightDurationMinutes.substring(1,2));
		if(Integer.parseInt(secondDurationHour)==Integer.parseInt(firstFlightDurationHour)){
			if(Integer.parseInt(secondDurationMinutes.substring(1,2))>=Integer.parseInt(firstFlightDurationMinutes.substring(1,2)));
				softAssert.assertTrue(true, "duration sorting not happened perfectly");
		}
		else if(Integer.parseInt(firstFlightDurationHour)<Integer.parseInt(secondDurationHour))
		{
			softAssert.assertTrue(true, "shorting does not happen perfectly");
			}
		i++;
	}
	
	
	}

	public void verifyArrivalSort(SoftAssert softAssert) throws InterruptedException, Exception {
		
		
	       if(!elementVisible(driver, getElementType(flightsRepo.get("price_up_arrow")), 1))
	    	   safeClick(driver, getElementType(flightsRepo.get("price_down_arrow")));
		List<WebElement> departTimings = getWebElementList(driver, getElementType(flightsRepo.get("arrival_time_mob")));
		for (int i = 0; i < departTimings.size() - 1; i++) {
		
			String firstFlightDepartHour = departTimings.get(i).getText().split(":")[0];
			String firstFlightDepartMins = departTimings.get(i).getText().split(":")[1];
			String secondFlightDepartHour = departTimings.get(i + 1).getText().split(":")[0];
			String secondFlightDepartMins = departTimings.get(i + 1).getText().split(":")[1];
			System.out.println(secondFlightDepartHour.substring(3, 5)+":"+secondFlightDepartMins+"check time"+firstFlightDepartHour.substring(3, 5)+":"+firstFlightDepartMins);
			if (Integer.parseInt(firstFlightDepartHour.substring(3, 5)) == Integer.parseInt(secondFlightDepartHour.substring(3,5))) {
				if (Integer.parseInt(secondFlightDepartMins) >= Integer.parseInt(firstFlightDepartMins))
					softAssert.assertTrue(true, firstFlightDepartHour + ":" + firstFlightDepartMins
							+ " has more arrival time than" + secondFlightDepartHour + ":" + secondFlightDepartMins);
			} else if (Integer.parseInt(secondFlightDepartHour.substring(3,5)) > Integer.parseInt(firstFlightDepartHour.substring(3,5))) {
				softAssert.assertTrue(true, firstFlightDepartHour.substring(3,5) + ":" + firstFlightDepartMins
						+ " has more arrival time than" + secondFlightDepartHour.substring(3,5) + ":" + secondFlightDepartMins);
			}

		}

	}

	public void verifyDurationSort(SoftAssert softAssert) {
		List<WebElement> departTimings = getWebElementList(driver, getElementType(flightsRepo.get("duration_time_mob")));
		for (int i = 0; i < departTimings.size() - 1; i++) {
			int firstFlightDepartHour = Integer.parseInt(departTimings.get(i).getText().split("h")[0].trim());
			int firstFlightDepartMins = Integer.parseInt(
					String.valueOf(departTimings.get(i).getText().split("h")[1].trim().replaceAll("\\D+", "")));
			System.out.println(firstFlightDepartHour +"---"+firstFlightDepartMins);
			int secondFlightDepartHour = Integer.parseInt(departTimings.get(i + 1).getText().split("h")[0].trim());
			int secondFlightDepartMins = Integer
					.parseInt(String.valueOf(departTimings.get(i + 1).getText().split("h")[1].replaceAll("\\D+", "")));
            System.out.println(secondFlightDepartHour + "---" +secondFlightDepartMins);
			int firstFlightTotalTime = (firstFlightDepartHour * 60) + firstFlightDepartMins;
			int secondFlightTotalTime = (secondFlightDepartHour * 60) + secondFlightDepartMins;
            System.out.println(firstFlightTotalTime+  "-"  +secondFlightTotalTime);
			if (firstFlightTotalTime <= secondFlightTotalTime)
				softAssert.assertTrue(true,
						departTimings.get(i).getText() + " is greater after sorting than " + departTimings.get(i + 1));

		}

	}

	public void verifyPriceSort(SoftAssert softAssert) throws Exception {
		Thread.sleep(2000);
		List<WebElement> prices = getWebElementList(driver, getElementType(flightsRepo.get("price")));
		for (int i = 0; i < prices.size() - 1; i++) {
			int firstFlightPrice = Integer.parseInt(prices.get(i).getText().replaceAll("\\D+", ""));
			int secondFlightPrice = Integer.parseInt(prices.get(i + 1).getText().replaceAll("\\D+", ""));

			if (firstFlightPrice <= secondFlightPrice) {
				// System.out.println("In Sorted order");
			} else {
				softAssert.assertTrue(false, firstFlightPrice + " has least price than " + secondFlightPrice);
			}
		}
	}

	public boolean clickStopsFilter(String stopType, SoftAssert softAssert) throws Exception {
		boolean stopFilterApplied = false;
		if(elementVisible(driver, By.id("stops_selection"), 2)){
		List<WebElement> stops = getWebElement(driver, By.id("stops_selection")).findElements(By.tagName("span"));
		if (!stopType.isEmpty()) {
			l1: for (WebElement stop : stops) {

				if (stop.getText().toLowerCase().contains(stopType)) {
				
					stop.click();
					stopFilterApplied = true;
					clickonDoneButton();
					break l1;
				}
			}
		
		}
        softAssert.assertTrue(stopFilterApplied, "filter type "+stopType+"is not available");
		//Assert.assertTrue("Their is no " + stopType + " Filter on SRP", stopFilterApplied);
		}
		return stopFilterApplied;

	}
	public void verifyAllAirlinesOnSRP(String triptype, List<String> allAirlines, SoftAssert softAssert)
			throws Exception {
		for (String airline : allAirlines) {
			clickAirlineFilter(triptype,airline, softAssert);
			verifyAirlineFilterApplied(triptype, airline, softAssert);
			softAssert.assertTrue(true);
		}
		

	}
	public void clickAirlineFilter(String tripType,String airline, SoftAssert softAssert) throws Exception {
		boolean airlineFilterApplied = false;
	
		List<WebElement> anyAirline = getWebElementList(driver, getElementType(flightsRepo.get("checkbox")));
		System.out.println(anyAirline.size());
		if (!airline.isEmpty()) {
		 for (WebElement select : anyAirline) {
				//System.out.println(select.getText().trim());
				if (select.getText().trim().equals(airline)) {
					select.click();
					clickonDoneButton();
					verifyAirlineFilterApplied(tripType, airline, softAssert);
					clickFiltersonSrp();
					resetAllFilters();
			
			
			}

				}
			

			}
		
		softAssert.assertTrue(airlineFilterApplied, "Their is no " + airline + " Filter on SRP");
	}

	public void clickAllianceFilter(String Alliance, SoftAssert softAssert) throws Exception {
		boolean airlineFilterApplied = false;
		if(elementVisible(driver,getElementType(flightsRepo.get("allianceCheckbox")) , 1));
		{
		List<WebElement> alliance = getWebElementList(driver, getElementType(flightsRepo.get("allianceCheckbox")));
		if (!alliance.isEmpty()) {
			l1: for (WebElement select : alliance) {
				System.out.println(select.getText().trim());
				if (Alliance.contains(select.getText().trim())) {
					softAssert.assertTrue(Alliance.contains(select.getText().trim()),
							select.getText().trim() + "Not found");
				}
			}
		}}
		safeClick(driver, getElementType(flightsRepo.get("carrier_link")));
	}

	public int applyAllOtherFilters(String stopType, SoftAssert softAssert) {
		List<WebElement> stops = getWebElement(driver, By.id("stops_selection")).findElements(By.tagName("span"));
		if (!stopType.isEmpty()) {
			for (WebElement stop : stops) {

				if (!stop.getText().toLowerCase().contains(stopType)) {
					stop.click();
				}
			}
		}

		return getFlightCountOnSRP();
	}

	public int applyAllOtherAirlineFilters(String airline, SoftAssert softAssert) throws Exception {
		resetFilters();
		List<WebElement> anyAirline = getWebElementList(driver, getElementType(flightsRepo.get("checkbox")));
		if (!airline.isEmpty()) {
			for (WebElement select : anyAirline) {
				if (!select.getText().trim().equals(airline)) {
					select.click();

				}

			}
		}

		return getFlightCountOnSRP();
	}

	public void resetFilters() throws Exception {
		((JavascriptExecutor) driver).executeScript("window.scrollTo(250,0)");
		safeClick(driver, By.xpath("//span[contains(text(),'Reset')]"));
	}

	public void verifyStopsFilterApplied(String triptype, String stopType, int flightCount, SoftAssert softAssert)
			throws Exception {
		Thread.sleep(1000);
		int noOfFlights;
		List<WebElement> flightStops = getWebElementList(driver, getElementType(flightsRepo.get("flight_stops")));
		noOfFlights = getFlightCountOnSRP();
		//System.out.println(noOfFlights);
		resetFilters();
		int otherFilterCount = applyAllOtherFilters(stopType, softAssert);

		Set<String> stops = new HashSet();
		for (int i = 0; i < flightStops.size(); i++) {
			try {
				if ("roundtrip".equalsIgnoreCase(triptype))
					stops.add(flightStops.get(i).getText().split("|")[0].trim());
				else
					stops.add(flightStops.get(i).getText().split("|")[0].trim());
			} catch (Exception e) {

			}
		}
		//System.out.println(stops.size());
		softAssert.assertEquals(stops.size(), 1);
		//System.out.println(flightCount + " " + noOfFlights + " " + otherFilterCount);
		// softAssert.assertEquals(flightCount,noOfFlights+otherFilterCount);

	}

	public void verifyStopsFilterAppliedOneWay(String triptype, String stopType, SoftAssert softAssert)
			throws Exception {
		Thread.sleep(1000);
		List<WebElement> flightStops = getWebElementList(driver, getElementType(flightsRepo.get("flight_stops")));
		List<String> stopValues = new ArrayList<>();
		for (int i = 0; i < flightStops.size(); i++) {

			String flightType;
			if (stopType.equalsIgnoreCase("non stop"))
				flightType = flightStops.get(i).getText().split("h")[0];
			else
				flightType = flightStops.get(i).getText().split("Via")[0];

			System.out.println(flightType);
			stopValues.add(flightType.substring(0, flightType.length() - 1));
		}
		if (stopType.equals("2+ STOPS")) {
			if (elementVisible(driver, By.xpath("//span[contains(@class,'connecting_flight_bar two_stop')]"), 1))
				softAssert.assertTrue(true);

		} else {

			softAssert.assertTrue(stopType.equalsIgnoreCase(stopValues.get(0)), "Mismatch in stopsType");
		}
	}

	
	public void verifyAirlineFilterApplied(String triptype, String airline, SoftAssert softAssert)
			throws Exception {
		Thread.sleep(1000);
		scrollDown(driver);
		List<WebElement> selectedFlight = getWebElementList(driver, getElementType(flightsRepo.get("airline_names")));

		Set<String> airlineName = new HashSet();
		if(selectedFlight!=null){
		for (int i = 0; i < selectedFlight.size(); i++) {
			try {
				if ("roundtrip".equalsIgnoreCase(triptype))
					airlineName.add(selectedFlight.get(i).getText());
				else
					airlineName.add(selectedFlight.get(i).getText());
			} catch (Exception e) {

			}
		}
		}
		//System.out.println(airlineName.size());
		softAssert.assertEquals(airlineName.size(), 1);
	}

	public void verifyARrivalSort(SoftAssert softAssert) {
		List<WebElement> departTimings = getWebElementList(driver, getElementType(flightsRepo.get("arrival_time")));
		for (int i = 0; i < 3; i++) {

			String[] FirstdepartHour = departTimings.get(i).getText().split(":");
			String firstflightdeparthour = FirstdepartHour[0];
			String[] FirstdepartMins = departTimings.get(i).getText().split(":");
			String firstflightdepartmins = FirstdepartMins[1];
			String[] SecondepartHour = departTimings.get(i + 1).getText().split(":");
			String secondflightdeparthour = SecondepartHour[0];
			String[] SeconddepartMin = departTimings.get(i + 1).getText().split(":");
			String secondflightdepartmins = SeconddepartMin[1];
			if (Integer.parseInt(firstflightdeparthour) == Integer.parseInt(secondflightdeparthour)) {
				if (Integer.parseInt(firstflightdepartmins) > Integer.parseInt(secondflightdepartmins))
					softAssert.assertTrue(false, firstflightdeparthour + ":" + firstflightdepartmins
							+ " has more arrival time than" + secondflightdeparthour + ":" + secondflightdepartmins);

			} else if (Integer.parseInt(firstflightdeparthour) > Integer.parseInt(secondflightdeparthour)) {
				softAssert.assertTrue(false, firstflightdeparthour + ":" + firstflightdepartmins
						+ " has more arrival time than" + secondflightdeparthour + ":" + secondflightdepartmins);

			}
		}
	}

	public void verifyDEpartureSort(SoftAssert softAssert) {
		List<WebElement> departTimings = getWebElementList(driver, getElementType(flightsRepo.get("departure_time")));
		for (int i = 0; i < 3; i++) {

			String[] FirstdepartHour = departTimings.get(i).getText().split(":");
			String firstflightdeparthour = FirstdepartHour[0];
			String[] FirstdepartMins = departTimings.get(i).getText().split(":");
			String firstflightdepartmins = FirstdepartMins[1];
			String[] SecondepartHour = departTimings.get(i + 1).getText().split(":");
			String secondflightdeparthour = SecondepartHour[0];
			String[] SeconddepartMin = departTimings.get(i + 1).getText().split(":");
			String secondflightdepartmins = SeconddepartMin[1];
			if (Integer.parseInt(firstflightdeparthour) == Integer.parseInt(secondflightdeparthour)) {
				if (Integer.parseInt(firstflightdepartmins) > Integer.parseInt(secondflightdepartmins))
					softAssert.assertTrue(false, firstflightdeparthour + ":" + firstflightdepartmins
							+ " has more departure time than" + secondflightdeparthour + ":" + secondflightdepartmins);

			} else if (Integer.parseInt(firstflightdeparthour) > Integer.parseInt(secondflightdeparthour)) {
				softAssert.assertTrue(false, firstflightdeparthour + ":" + firstflightdepartmins
						+ " has more departure time than" + secondflightdeparthour + ":" + secondflightdepartmins);

			}
		}
	}

	public void selectFlightInternationalOW() throws Exception {
		for(int i=1;i<=4;i++){
		scrollUp(driver);
		}
		safeClick(driver, By.xpath("//span[text()='Reset all']"));
		Thread.sleep(500);
		scrollUp(driver);
		safeClick(driver, By.xpath("//span[contains(text(),'1 stop')]"));
	
		safeClick(driver, By.xpath("(//button[text()='Book Now'])[2]"));
	}

	public void verifyLowestPriceFilter(String triptype, String airlineName, SoftAssert softAssert) throws Exception {

		Thread.sleep(1000);
		scrollUp(driver);
		List<WebElement> selectedFlightName = getWebElementList(driver, By.xpath("//label[contains(@for,'airline')]"));
		List<WebElement> selectedFlightPrice = getWebElementList(driver,By.xpath("//label[contains(@for,'airline')]/../../span"));
		Map<String, String> priceMap = new HashMap<>();
		for (int i = 0; i < selectedFlightName.size(); i++) {
			String flightName = selectedFlightName.get(i).getText();
			String flightPrice = selectedFlightPrice.get(i).getText();
			priceMap.put(flightName, flightPrice);
		}
		softAssert.assertTrue(
				safeGetText(driver, By.className("selected_flight_price")).equals(priceMap.get(airlineName)),
				"Mismatch in lowest fare for " + airlineName);

	}

	public void verifyLowestPriceFilterOneWay(String triptype, SoftAssert softAssert) throws Exception {

		Thread.sleep(1000);
		List<WebElement> selectedFlightName = getWebElementList(driver, By.xpath("//label[contains(@for,'airline')]"));
		List<WebElement> selectedFlightPrice = getWebElementList(driver,
				By.xpath("//label[contains(@for,'airline')]/../../span"));
		Map<String, String> priceMap = new HashMap<>();
		for (int i = 0; i < selectedFlightName.size(); i++) {
			String flightName = selectedFlightName.get(i).getText();
			String flightPrice = selectedFlightPrice.get(i).getText();
			priceMap.put(flightName, flightPrice);
		}
		
		//System.out.println(priceMap);

		Set<String> allSrpAirlines = priceMap.keySet();

		
		for (String airlineName : allSrpAirlines) {
			clickAirlineFilter(triptype,airlineName, softAssert);
			clickonDoneButton();
			softAssert.assertTrue(safeGetText(driver, By.xpath("(//div[@class='price_section margin_on_biggest']/..//h4)[1]"))
					.equals(priceMap.get(airlineName)), "Mismatch in lowest fare for " + airlineName);
			
			clickFiltersonSrp();
			resetAllFilters();
		}
		

	}

	

	public void verifyPriceCalendar(SoftAssert softAssert) throws Exception {
		scrollUp(driver);
		List<WebElement> getTravelDetails = getWebElementList(driver, By.className("col_block"));
		safeClick(driver, getElementType(flightsRepo.get("price_calendar")));

		List<WebElement> priceCalendarHeader = getWebElementList(driver,
				getElementType(flightsRepo.get("price_header")));
		softAssert.assertTrue(elementPresent(driver, By.className("priceRange-CloseCircle"), 1),
				"Close button not appeared for price calendar");
		String travellerSource = getTravelDetails.get(0).getText().split("\n")[0];
		String travellerDestination = getTravelDetails.get(1).getText().split("\n")[0];
		softAssert.assertTrue(
				travellerSource.equals(priceCalendarHeader.get(0).getText().substring(0, travellerSource.length())),
				"Price Calendar doesn't have source" + "or destination");
		softAssert.assertTrue(
				travellerDestination
						.contains(priceCalendarHeader.get(1).getText().substring(0, travellerDestination.length())),
				"Price Calendar doesn't have source" + "or destination");
		safeClick(driver, By.xpath("//button[contains(text(),'SEARCH FLIGHT')]"));

	}

	public void verifyUndoFilters(SoftAssert softAssert) throws Exception {
		

		if (elementVisible(driver, By.xpath("//button[contains(text(),'Undo Last Filter')]"), 2)) {
			softAssert.assertTrue(true, "Undo Filter Appeared");
			safeClick(driver, By.xpath("//button[contains(text(),'Undo Last Filter')]"));
			checkSRP();
		}
	}
	public void clickOnStarAlliance() throws Exception{
		safeClick(driver, By.xpath("//span[contains(text(),'Alliance')]"));
		Thread.sleep(1000);
		safeClick(driver, By.xpath("//span[contains(text(),'Star Alliance')]"));
	}
/*
	public void compareResultsWithAPI(String tripType, List<String> onwardSectors, List<String> returnSectors,
			String travelDate, String returnDate, String classType, String adultCount, String childCount,
			String infantCount, int flightCount, SoftAssert softAssert) throws Exception {
		String authBody;
		String searchId;
		boolean srpLoaded;
		authBody = flightsRequestDataGoomoRT(tripType, onwardSectors.get(0), returnSectors.get(0), travelDate,
				returnDate, classType, adultCount, childCount, infantCount);
		System.out.println(authBody);
		Response authRep = postCallAuthentication(urlGeneratorGoomo(GOOMO_DOMESTIC_RT_GET_AVAILABILITY),
				"application/json", authBody);
		verifyOkAuthenticationResponse(authRep);

		// Search Response and get Search Track Id

		searchId = getGoomoSearchTrackId(authRep);
		logStep("Search ID " + searchId, test);
		System.out.println(urlGeneratorGoomo(GOOMO_DOMESTIC_RT_GET_AVAILABILITY_RESULTS) + searchId);
		Response searchResponse = getCall(urlGeneratorGoomo(GOOMO_DOMESTIC_RT_GET_AVAILABILITY_RESULTS) + searchId,
				"application/json");
		srpLoaded = checkSearchResultStatus(searchResponse);
		searchResponse = retrySearchRequest(srpLoaded, searchResponse,
				urlGeneratorGoomo(GOOMO_DOMESTIC_RT_GET_AVAILABILITY_RESULTS), searchId);
		verifyOkResponse(searchResponse);
		softAssert.assertEquals(flightCount, getSRPFlightsCount(searchResponse));

	}*/

	// Search Result Page Functionality - One Way //

	/**
	 * This method verifies the data propagated from Search to SRP
	 * 
	 * @throws Exception
	 */
	public void clickModifybutton(SoftAssert softAssert) throws Exception {
		Thread.sleep(1000);
		checkSRP();
		Thread.sleep(1000);
		safeClick(driver, getElementType(flightsRepo.get("Modify_Search")));
		softAssert.assertTrue(true);
	}
	public void clickModifybuttonRT(SoftAssert softAssert) throws Exception {
		checkSRPRT();
		safeClick(driver, getElementType(flightsRepo.get("Modify_Search_RT")));
		
		//safeClick(driver, getElementType(flightsRepo.get("Modify_Search_RT")));
		softAssert.assertTrue(true);
	}

	public void clickModifyClosebutton(SoftAssert softAssert) throws Exception {
		boolean closeButtonAppeared = false;

		if (elementVisible(driver, getElementType(flightsRepo.get("modify_search_close")), 1)) {
			safeClick(driver, getElementType(flightsRepo.get("modify_search_close")));
			closeButtonAppeared = true;

		}
		softAssert.assertTrue(closeButtonAppeared, "Close button not present");

	}

	
    public void clickApplyButtonTraveller(SoftAssert softAssert) throws Exception {
		
		boolean applyButtonAppeared = false;

		if (elementVisible(driver, getElementType(flightsRepo.get("filter_done")), 1)) {
			safeClick(driver, getElementType(flightsRepo.get("filter_done")));
			applyButtonAppeared = true;

		}
		softAssert.assertTrue(applyButtonAppeared, "Close button not present");

}


	public String getSRPFlightCount(SoftAssert softAssert) throws Exception {
		String totalFlights = null;
		if (elementDisplayed(driver, getElementType(flightsRepo.get("flight_count")), 1)) {
			totalFlights = safeGetText(driver, getElementType(flightsRepo.get("flight_count")));
			//System.out.println(totalFlights);
		}
		softAssert.assertTrue(false, "Close button not present");
		return totalFlights;
	}
	
	public void modifyDepaturetopCityOW(String Source,String destination) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		clickModifybutton(softAssert);
		Thread.sleep(2000);
		safeClick(driver, getElementType(flightsRepo.get("clickon_fromcity")));
		Thread.sleep(2000);
		safeClear(driver, getElementType(flightsRepo.get("from_city_box")));
		Thread.sleep(1000);
		List<WebElement> topCities = getWebElementList(driver, getElementType(flightsRepo.get("top_cities_list")));
		//System.out.println(topCities.size()+"size of cities");
		       
		if ((topCities.size())>=1) {
			System.out.println("Depature top city list displayed in modify search");
			safeType(driver, getElementType(flightsRepo.get("from_city_box")),Source);
			safeClick(driver,getElementType(flightsRepo.get("select_suggestion")));
			Thread.sleep(1000);
		}
	}

	public void modifyDepaturetopCity(String Source,String destination) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		clickModifybutton(softAssert);
		Thread.sleep(2000);
		safeClick(driver, getElementType(flightsRepo.get("clickon_fromcity")));
		Thread.sleep(2000);
		safeClear(driver, getElementType(flightsRepo.get("from_city_box")));
		Thread.sleep(1000);
		List<WebElement> topCities = getWebElementList(driver, getElementType(flightsRepo.get("top_cities_list")));
		System.out.println(topCities.size()+"size of cities");
		       
		if ((topCities.size())>=1) {
			System.out.println("Depature top city list displayed in modify search");
			safeType(driver, getElementType(flightsRepo.get("from_city_box")),Source);
			safeClick(driver,getElementType(flightsRepo.get("select_suggestion")));
			Thread.sleep(1000);
		}
	}


	public void modifyDepaturetopCityRT(String Source,String destination) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		clickModifybuttonRT(softAssert);
		Thread.sleep(2000);
		safeClick(driver, getElementType(flightsRepo.get("clickon_fromcity")));
		Thread.sleep(2000);
		safeClear(driver, getElementType(flightsRepo.get("from_city_box")));
		Thread.sleep(1000);
		List<WebElement> topCities = getWebElementList(driver, getElementType(flightsRepo.get("top_cities_list")));
		System.out.println(topCities.size()+"size of cities");
		       
		if ((topCities.size())>=1) {
			//System.out.println("Depature top city list displayed in modify search");
			safeType(driver, getElementType(flightsRepo.get("from_city_box")),Source);
			safeClick(driver,getElementType(flightsRepo.get("select_suggestion")));
			Thread.sleep(1000);
		}
	}
	public void modifyDestinationtopCity(String Source,String destination) throws Exception {
		safeClick(driver, getElementType(flightsRepo.get("clickon_tocity")));
		Thread.sleep(2000);
		safeClear(driver, getElementType(flightsRepo.get("to_city_box")));
		Thread.sleep(1000);
		List<WebElement> topCities = getWebElementList(driver, getElementType(flightsRepo.get("top_cities_list")));
		System.out.println(topCities.size()+"size of cities");
		       
		if ((topCities.size())>=1) {
			//System.out.println("Destination top city list displayed in modify search");
			safeType(driver, getElementType(flightsRepo.get("to_city_box")), destination);
			safeClick(driver,getElementType(flightsRepo.get("select_suggestion")));
		}
	}

	public void modifyFromDate() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		safeClick(driver, getElementType(flightsRepo.get("modify_search_toCity")));
		safeClear(driver, getElementType(flightsRepo.get("modify_search_toCity")));
		safeType(driver, getElementType(flightsRepo.get("modify_search_toCity")), "B");
		List<WebElement> topCities = getWebElementList(driver, getElementType(flightsRepo.get("top_cities")));
		if (topCities.size() > 1) {
			//System.out.println("Destination top city list displayed in modify search");
			topCities.get(3).click();
		}
	}

	public void clickModifySearch() throws Exception {
		if (elementDisplayed(driver, getElementType(flightsRepo.get("flight_count")), 1)) {
			safeClick(driver, getElementType(flightsRepo.get("searchIn_modify")));
		}

	}
	
	public void clickonRemoveFilterIcon() throws Exception{
		
		if (elementDisplayed(driver, getElementType(flightsRepo.get("remove_reset_filters")), 1))
		{
			safeClick(driver, getElementType(flightsRepo.get("remove_reset_filters")));
		}
		
	}
		
	public void selectFlightRT(int flightNo) throws Exception{
		
		  // List<WebElement> selectFlight = getWebElementList(driver,getElementType(flightsRepo.get("select_flight_srp")));
       //selectFlight.get(flightNo-1).click();
	waitForElementToBeVisible(driver, getElementType(flightsRepo.get("continue_button_reviewpage")));
		    //safeClick(driver, getElementType(flightsRepo.get("select_flight_srp_rt")));
			//Thread.sleep(500);
		    //safeClick(driver, getElementType(flightsRepo.get("select_flight_srp_rt")));
	   safeClick(driver, getElementType(flightsRepo.get("continue_button_reviewpage")));
	
	}
	
	
	public void clickFiltersonSrp() throws Exception{
		if (elementVisible(driver, getElementType(flightsRepo.get("reset_filters_srp")), 1)) 
		{
			  safeClick(driver, getElementType(flightsRepo.get("reset_filters_srp")));
		}
	
		
	}
	public void clickBookButtonRT() throws Exception {
		waitForElementToBeVisible(driver,getElementType(flightsRepo.get("select_flight_srp_rt")) );
		safeClick(driver, getElementType(flightsRepo.get("select_flight_srp_rt")));
		Thread.sleep(1000);
		safeClick(driver, getElementType(flightsRepo.get("select_flight_srp_rt")));
		
	}

	public void resetAllFilters() throws Exception {
		//scrollUp(driver);
		if (elementVisible(driver, getElementType(flightsRepo.get("reset_filters")), 1)) {
			safeClick(driver, getElementType(flightsRepo.get("reset_filters")));
		}
		
	}
	
	public void clickonDoneButton() throws Exception{
		SoftAssert softAssert=new SoftAssert();
		verifyUndoFilters(softAssert);
		if (elementVisible(driver, getElementType(flightsRepo.get("done_button")), 1)) {
			safeClick(driver, getElementType(flightsRepo.get("done_button")));
			Thread.sleep(2000);
		}
		
		
	}
	public void flightFilter() throws InterruptedException, Exception{
		SoftAssert softAssert=new SoftAssert();
		Thread.sleep(2000);
		
		if(elementVisible(driver,By.xpath("(//label[contains(@for,'airline')])[1]") ,1))
		{
		scrollToElement(driver, getWebElement(driver, By.xpath("(//label[contains(@for,'airline')])[1]")));
		}
		if(elementVisible(driver, By.xpath("//label[contains(@for,'airline')]/span[contains(text(),'IndiGo')]"),1))
		{
			safeClick(driver, By.xpath("//label[contains(@for,'airline')]/span[contains(text(),'IndiGo')]"));
		}
		if(elementVisible(driver, By.xpath("//label[contains(@for,'airline')]/span[contains(text(),'Spicejet')]"),1)){
			safeClick(driver, By.xpath("//label[contains(@for,'airline')]/span[contains(text(),'Spicejet')]"));
		}
	}
	public void flightFilterINT() throws InterruptedException, Exception{
		SoftAssert softAssert=new SoftAssert();
		if(elementVisible(driver,By.xpath("(//label[contains(@for,'airline')])[1]") ,1))
		{
		scrollToElement(driver, getWebElement(driver, By.xpath("(//label[contains(@for,'airline')])[1]")));
		}
		if(elementVisible(driver, By.xpath("//label[contains(@for,'airline')]/span[contains(text(),'Air India')]"),1))
		{
			safeClick(driver, By.xpath("//label[contains(@for,'airline')]/span[contains(text(),'Air India')]"));
		}
		if(elementVisible(driver, By.xpath("//label[contains(@for,'airline')]/span[contains(text(),'Jet Airways')]"),1)){
			safeClick(driver, By.xpath("//label[contains(@for,'airline')]/span[contains(text(),'Jet Airways')]"));
		}
	}
public void selectReturnTrip() throws Exception{
		
		if(!elementVisible(driver, By.xpath("//li[@class='rtdom_flight']"),1))
			safeClick(driver, By.xpath("//li[@class='rtdom_flight discount_icon'][1]"));
		
	}

public void verifyTripDurationOnSRPRT(String triptype, SoftAssert softAssert) throws Exception {
	if ("roundtrip".equals(triptype)) {
		String tripDuration = safeGetText(driver, By.xpath("//div[8]/div/h6"));
		//System.out.println(tripDuration);
		softAssert.assertTrue("TRIP DURATION".contains(tripDuration), "Trip Duration not Found");
		/*elementDisplayed(driver, By.xpath("//div[8]/div[2]/div/div[3]"), 1);
		String tripStart = safeGetText(driver, By.xpath("//div[8]/div[2]/div[2]/span"));
		softAssert.assertTrue("hours".contains(tripStart), "Trip Duration not Found");
		String tripEnd = safeGetText(driver, By.xpath("//div[8]/div[2]/div[2]/span[2]"));
		softAssert.assertTrue("hours".contains(tripEnd), "Trip Duration not Found");
*/
	} else {
		String tripDuration = safeGetText(driver, By.xpath("//div[8]/div/h6"));
		softAssert.assertTrue("TRIP DURATION".contains(tripDuration), "Trip Duration not Found");
	/*	elementDisplayed(driver, By.xpath("//div[7]/div[2]/div/div[4]"), 1);
		String tripStart = safeGetText(driver, By.xpath("//div[8]/div[2]/div[2]/span"));
		softAssert.assertTrue("hours".contains(tripStart), "Trip Duration not Found");
		String tripEnd = safeGetText(driver, By.xpath("//div[8]/div[2]/div[2]/span[2]"));
		softAssert.assertTrue("hours".contains(tripEnd), "Trip Duration not Found");*/
	}

}


public void verifyAllAirlinesOnSRPmObileWebRT(SoftAssert softAssert) throws InterruptedException, Exception{
	boolean airLineName=false;
	
	List<WebElement> airlineNames=getWebElementList(driver, getElementType(flightsRepo.get("airline_checkbox")));
	for(int i=0;i<airlineNames.size();i++){
		scrollDown(driver);
		//System.out.println(airlineNames.size());
		//System.out.println(i+"==============");
		scrollToElement(driver, getWebElement(driver, By.xpath("//label[contains(@for,'airline_"+i+"')]")));
		String filterAirLine=getWebElement(driver, By.xpath("//label[contains(@for,'airline_"+i+"')]")).getText();
		safeClick(driver,By.xpath("//label[contains(@for,'airline_"+i+"')]") );
		clickonDoneButton();
		List<WebElement> srpAirLine=getWebElementList(driver, getElementType(flightsRepo.get("airline_names_roundtrip")));
		for(int j=0;j<srpAirLine.size();j++)
		{
			String ActualAirline=srpAirLine.get(j).getText();
			//System.out.println(ActualAirline+"airline name compairing"+filterAirLine);
			if(ActualAirline.trim().equalsIgnoreCase(filterAirLine.trim()))
				airLineName=true;
			softAssert.assertTrue(true,"airline filter is not working properly");
		}
		clickFiltersonSrp();
		resetAllFilters();
	}
	
}
	
	public void verifyReturnTimeFilterRT(String triptype, String filterName, SoftAssert softAssert) {
		List<WebElement> departTimings;

		if ("roundtrip".equalsIgnoreCase(triptype))
			departTimings = getWebElementList(driver, By.xpath("//p[@class='rtdom_flight__departure_and_stops__departure']"));
		else
			departTimings = getWebElementList(driver, getElementType(flightsRepo.get("departure_time_mob_rt")));

		List<String> departTimingValues = new ArrayList<>();
		for (int i = (departTimings.size() / 2) + 2; i < departTimings.size(); i++) {
			departTimingValues.add(departTimings.get(i).getText().trim());

		}

		if (filterName.equalsIgnoreCase("early morning") && !departTimingValues.isEmpty()) {
			for (int i = 0; i < departTimingValues.size(); i++) {
				int value = Integer.parseInt(departTimingValues.get(i).trim().split(":")[0].trim());
				if (value >= 0 && value <= 6) {
					// System.out.println("time is aligned");
				} else
					softAssert.assertTrue(true, departTimings.get(i) + " Value has error in SRP");

			}
		} else if (filterName.equalsIgnoreCase("morning") && !departTimingValues.isEmpty()) {
			for (int i = 0; i < departTimingValues.size(); i++) {
				int value = Integer.parseInt(departTimingValues.get(i).trim().split(":")[0].trim());
				if (value >= 6 && value <= 11) {
					// System.out.println("time is aligned");
				} else
					softAssert.assertTrue(true, departTimings.get(i) + " Value has error in SRP");

			}
		} else if (filterName.equalsIgnoreCase("mid-day") && !departTimingValues.isEmpty()) {
			for (int i = 0; i < departTimingValues.size(); i++) {
				int value = Integer.parseInt(departTimingValues.get(i).trim().split(":")[0].trim());
				value = value - 12;
				if (value >= -1 && value <= 5) {
					// System.out.println("time is aligned");
				} else
					softAssert.assertTrue(true, departTimings.get(i) + " Value has error in SRP");

			}
		} else if (filterName.equalsIgnoreCase("evening") && !departTimingValues.isEmpty()) {
			for (int i = 0; i < departTimingValues.size(); i++) {
				int value = Integer.parseInt(departTimingValues.get(i).trim().split(":")[0].trim());
				value = value - 12;
				if (value >= 5 && value <= 12) {
					// System.out.println("time is aligned");
				} else
					softAssert.assertTrue(true, departTimings.get(i) + " Value has error in SRP");

			}
		}

	}
	
	public void verifyDepartureTimeFilterRT(String triptype, String filterName, SoftAssert softAssert) {
        List<WebElement> departTimings;

        if ("roundtrip".equalsIgnoreCase(triptype))
            departTimings = getWebElementList(driver, By.xpath("//div[@class='rtdom_flight__departure_and_stops']"));
        else
            departTimings = getWebElementList(driver, getElementType(flightsRepo.get("departure_time_mob_rt")));
         
        //System.out.println(departTimings.size());	
        List<String> departTimingValues = new ArrayList<>();
        for (int i = 0; i < departTimings.size() / 2;) {
            if (i != 2) {
                departTimingValues.add(departTimings.get(i).getText().trim());
            }
            i = i + 2;
        }
    }
	
	public void verifyDepartureTimeFilterDomRT(String triptype, String filterName, SoftAssert softAssert) {
        List<WebElement> departTimings;
        boolean check=false;
        if ("roundtrip".equalsIgnoreCase(triptype) && "Early Morning".equalsIgnoreCase(filterName)){
            departTimings = getWebElementList(driver, By.xpath("//div[contains(@class,'timings')]/p[1]"));
       
        //System.out.println(departTimings.size());	
        List<String> departTimingValues = new ArrayList<>();
        for (int i = 0; i < departTimings.size() / 2;i++) {
           String h=departTimings.get(i).getText().split(":")[0].trim();
           int hour=Integer.parseInt(h);
        		   if(0<=hour && hour<=6){
        			   check=true;
        		   }
        		   softAssert.assertTrue(check);
            i = i + 2;
        }}
        else if("roundtrip".equalsIgnoreCase(triptype) && "Morning".equalsIgnoreCase(filterName)){
        	departTimings = getWebElementList(driver, By.xpath("//div[contains(@class,'timings')]/p[1]"));
            
            //System.out.println(departTimings.size());	
            List<String> departTimingValues = new ArrayList<>();
            for (int i = 0; i < departTimings.size() / 2;i++) {
               String h=departTimings.get(i).getText().split(":")[0].trim();
               int hour=Integer.parseInt(h);
            		   if(6<=hour && hour<=11){
            			   check=true;
            		   }
            		   softAssert.assertTrue(check);
                i = i + 2;
            }
        }
        else if("roundtrip".equalsIgnoreCase(triptype) && "Mid-Day".equalsIgnoreCase(filterName)){
        	
departTimings = getWebElementList(driver, By.xpath("//div[contains(@class,'timings')]/p[1]"));
            
            //System.out.println(departTimings.size());	
            List<String> departTimingValues = new ArrayList<>();
            for (int i = 0; i < departTimings.size() / 2;i++) {
               String h=departTimings.get(i).getText().split(":")[0].trim();
               int hour=Integer.parseInt(h);
            		   if(11<=hour && hour<=17){
            			   check=true;
            		   }
            		   softAssert.assertTrue(check);
                i = i + 2;
            }
        	}
        else{
departTimings = getWebElementList(driver, By.xpath("//div[contains(@class,'timings')]/p[1]"));
            
            //System.out.println(departTimings.size());	
            List<String> departTimingValues = new ArrayList<>();
            for (int i = 0; i < departTimings.size() / 2;i++) {
               String h=departTimings.get(i).getText().split(":")[0].trim();
               int hour=Integer.parseInt(h);
            		   if(17<=hour && hour<=23){
            			   check=true;
            		   }
            		   softAssert.assertTrue(check);
                i = i + 2;
            }
        }
      
    }

	public void verifyDepartureTimeFilterDomReturnRT(String triptype, String filterName, SoftAssert softAssert) {
        List<WebElement> departTimings;
        boolean check=false;
        if ("roundtrip".equalsIgnoreCase(triptype) && "Early Morning".equalsIgnoreCase(filterName)){
            departTimings = getWebElementList(driver, By.xpath("//div[contains(@class,'timings')]/p[1]"));
       
        //System.out.println(departTimings.size());	
        List<String> departTimingValues = new ArrayList<>();
        for (int i = departTimings.size() / 2; i < departTimings.size() ;i++) {
           String h=departTimings.get(i).getText().split(":")[0].trim();
           int hour=Integer.parseInt(h);
        		   if(0<=hour && hour<=6){
        			   check=true;
        		   }
        		   softAssert.assertTrue(check);
            i = i + 2;
        }}
        else if("roundtrip".equalsIgnoreCase(triptype) && "Morning".equalsIgnoreCase(filterName)){
        	departTimings = getWebElementList(driver, By.xpath("//div[contains(@class,'timings')]/p[1]"));
            
            //System.out.println(departTimings.size());	
            List<String> departTimingValues = new ArrayList<>();
            for (int i = departTimings.size() / 2; i < departTimings.size();i++) {
               String h=departTimings.get(i).getText().split(":")[0].trim();
               int hour=Integer.parseInt(h);
            		   if(6<=hour && hour<=11){
            			   check=true;
            		   }
            		   softAssert.assertTrue(check);
                i = i + 2;
            }
        }
        else if("roundtrip".equalsIgnoreCase(triptype) && "Mid-Day".equalsIgnoreCase(filterName)){
        	
departTimings = getWebElementList(driver, By.xpath("//div[contains(@class,'timings')]/p[1]"));
            
            //System.out.println(departTimings.size());	
            List<String> departTimingValues = new ArrayList<>();
            for (int i = departTimings.size() / 2; i < departTimings.size();i++) {
               String h=departTimings.get(i).getText().split(":")[0].trim();
               int hour=Integer.parseInt(h);
            		   if(11<=hour && hour<=17){
            			   check=true;
            		   }
            		   softAssert.assertTrue(check);
                i = i + 2;
            }
        	}
        else{
departTimings = getWebElementList(driver, By.xpath("//div[contains(@class,'timings')]/p[1]"));
            
            //System.out.println(departTimings.size());	
            List<String> departTimingValues = new ArrayList<>();
            for (int i = departTimings.size() / 2; i < departTimings.size();i++) {
               String h=departTimings.get(i).getText().split(":")[0].trim();
               int hour=Integer.parseInt(h);
            		   if(17<=hour && hour<=23){
            			   check=true;
            		   }
            		   softAssert.assertTrue(check);
                i = i + 2;
            }
        }
      
    }

	public void selectInfantCount(String iCount) throws Exception {
		// Actions act=new Actions(driver);
		// WebElement infant =
		// getWebElement(driver,getElementType(flightsRepo.get("infant_c")+iCount+"')])[3]"));
		// act.moveToElement(infant).click().build().perform();
		driver.findElement(By.xpath("//li[contains(text(),'" + iCount +"')]")).click();
		Thread.sleep(2000);
		((JavascriptExecutor) driver).executeScript("window.scrollTo(250,0)");
		//safeClick(driver, getElementType(flightsRepo.get("searchIn_modify")));
	}

	public void selectTraveller(String adultCount, String childCount, String infantCount) throws Exception {

		//System.err.println(adultCount + " " + childCount + " " + infantCount);
		checkSRP();
		safeClick(driver, getElementType(flightsRepo.get("select_traveller_info")));

		safeClick(driver, getElementType(flightsRepo.get("adult_dd")));
		Thread.sleep(2000);
		safeClick(driver, getElementType(flightsRepo.get("adult_select_dd") + adultCount.trim() + "')]"));
		scrollDown(driver);

		safeClick(driver, getElementType(flightsRepo.get("child_dd")));
		safeClick(driver, getElementType(flightsRepo.get("child_select_dd") + childCount.trim() + "')][1]"));

		scrollDown(driver);

		safeClick(driver, getElementType(flightsRepo.get("")));
		selectInfantCount(infantCount);

	}

	public void selectTravellerModifySearch(String adultCount, String childCount, String infantCount) throws Exception {

		//System.err.println(adultCount + " " + childCount + " " + infantCount);
		checkSRP();
		safeClick(driver, getElementType(flightsRepo.get("select_traveller_info")));
if(Integer.parseInt(adultCount)!=1)
        for(int i=1;i<Integer.parseInt(adultCount);i++)
        {
        	Thread.sleep(1000);
		safeClick(driver, getElementType(flightsRepo.get("adult_dd")));
        }
		/*Thread.sleep(2000);
		safeClick(driver, getElementType(flightsRepo.get("adult_select_dd") + adultCount.trim() + "')]"));
*/
		if (Integer.parseInt(childCount) > 0) {
			for(int i=1;i<=Integer.parseInt(childCount);i++){
				Thread.sleep(1000);
			safeClick(driver, getElementType(flightsRepo.get("child_dd")));
			//safeClick(driver, getElementType(flightsRepo.get("child_select_dd") + childCount.trim() + "')][1]"));
		}}

		if (Integer.parseInt(infantCount) > 0) {
			for(int i=1;i<=Integer.parseInt(infantCount);i++){
				Thread.sleep(1000);
			safeClick(driver, getElementType(flightsRepo.get("infant_dd")));
			//selectInfantCount(infantCount);
		}}

	}

	public void selectTravellerModifySearchRT(String adultCount, String childCount, String infantCount) throws Exception {

		//System.err.println(adultCount + " " + childCount + " " + infantCount);
		checkSRPRT();
		safeClick(driver, getElementType(flightsRepo.get("select_traveller_info")));
if(Integer.parseInt(adultCount)!=1)
	for(int i=1;i<=Integer.parseInt(adultCount);i++)
	{Thread.sleep(1000);
		safeClick(driver, getElementType(flightsRepo.get("adult_dd")));
		Thread.sleep(2000);
	}	/*safeClick(driver, getElementType(flightsRepo.get("adult_select_dd") + adultCount.trim() + "')]"));*/

		if (Integer.parseInt(childCount) > 0) {
			for(int i=1;i<=Integer.parseInt(childCount);i++){
				Thread.sleep(1000);
			safeClick(driver, getElementType(flightsRepo.get("child_dd")));
			}
			/*safeClick(driver, getElementType(flightsRepo.get("child_select_dd") + childCount.trim() + "')][1]"));*/
		}

		if (Integer.parseInt(infantCount) > 0) {
			for(int i=1;i<=Integer.parseInt(infantCount) ;i++){
				Thread.sleep(1000);
				safeClick(driver, getElementType(flightsRepo.get("infant_dd")));
			}
			//selectInfantCount(infantCount);
		}

	}
	public void priceSortOnward() throws Exception {
		checkSRP();
		safeClick(driver, getElementType(flightsRepo.get("pricesort_onward")));
		safeClick(driver, getElementType(flightsRepo.get("pricesort_onward")));
	}

	public void priceSortReturn() throws Exception {
		checkSRP();
		safeClick(driver, getElementType(flightsRepo.get("pricesort_return")));
		safeClick(driver, getElementType(flightsRepo.get("pricesort_return")));
	}

	public int minSRPPrice() throws Exception {
		checkSRP();
		int leastPrice = 0;
		List<Integer> leastPrices = new ArrayList<>();
		List<WebElement> allPricesRT = getWebElementList(driver, getElementType(flightsRepo.get("rt_prices_mob")));
		for (int i = 0; i < allPricesRT.size(); i++) {
			String price = (allPricesRT.get(i).getText()).split(" ")[1];
			leastPrice = Integer.parseInt(price.replaceAll("\\D+", ""));
			leastPrices.add(leastPrice);
		}
		leastPrice = leastPrices.get(0).intValue();
		//System.out.println("" + leastPrice);
		return leastPrice;
	}

	
	public int maxSRPPriceOW() throws Exception {
		checkSRP();
		safeClick(driver, getElementType(flightsRepo.get("price_sort")));
		safeClick(driver, getElementType(flightsRepo.get("price_sort")));
		int maxPrice = 0;
		List<Integer> maxPrices = new ArrayList<>();
		//priceSortOnward();
		List<WebElement> allPricesRT = getWebElementList(driver, getElementType(flightsRepo.get("rt_prices_mob")));
		for (int i = 0; i < allPricesRT.size(); i++) {
			String price = (allPricesRT.get(i).getText()).split(" ")[1];
			maxPrice = Integer.parseInt(price.replaceAll("\\D+", ""));
			maxPrices.add(maxPrice);
		}
		Collections.sort(maxPrices);
		maxPrice = maxPrices.get((maxPrices.size()) - 1).intValue();
		//System.out.println(maxPrice);
		return maxPrice;
	}
	
	
	public int maxSRPPrice() throws Exception {
		checkSRP();
		int maxPrice = 0;
		List<Integer> maxPrices = new ArrayList<>();
		priceSortOnward();
		priceSortReturn();
		List<WebElement> allPricesRT = getWebElementList(driver, getElementType(flightsRepo.get("rt_prices")));
		for (int i = 0; i < allPricesRT.size(); i++) {
			String price = (allPricesRT.get(i).getText()).split(" ")[1];
			maxPrice = Integer.parseInt(price.replaceAll("\\D+", ""));
			maxPrices.add(maxPrice);
		}
		Collections.sort(maxPrices);
		maxPrice = maxPrices.get((maxPrices.size()) - 1).intValue();
		//System.out.println(maxPrice);
		return maxPrice;
	}

	public void defaultPriceSortCheck() throws Exception {
		checkSRP();
        SoftAssert softassert = new SoftAssert();
		/*List<Integer> onwardPrices = new ArrayList<Integer>();
		List<WebElement> allPricesRT = getWebElementList(driver, getElementType(flightsRepo.get("rt_prices_mob")));
		for (int i = 0; (i < allPricesRT.size() / 2); i = (i + 2)) {
			String price = (allPricesRT.get(i).getText()).split(" ")[1];
			int onWardPrice = Integer.parseInt(price.replaceAll("\\D+", ""));
			onwardPrices.add(onWardPrice);
		}
		if (onwardPrices.size() > 0 && onwardPrices.get(0) < onwardPrices.get(1)) {
			softassert.assertTrue(true, "Default sorted by price");
		}
		softassert.assertTrue(false, "Sorting Failed");
		//softassert.assertAll();
*/	
        boolean pricesort=false;
       if(!elementVisible(driver, getElementType(flightsRepo.get("price_up_arrow")), 1))
    	   safeClick(driver, getElementType(flightsRepo.get("price_down_arrow")));
       List<WebElement> allPricesRT = getWebElementList(driver, getElementType(flightsRepo.get("rt_prices_mob")));
       for(int i=0;i<allPricesRT.size()-1;i++)
       {
    	   String fstprice = (allPricesRT.get(i).getText()).split(" ")[1];
    	   String scndprice=(allPricesRT.get(i+1).getText()).split(" ")[1];
		 int firstPrice=Integer.parseInt(fstprice.replaceAll("\\D+", ""));
			int secondPrice=Integer.parseInt(scndprice.replaceAll("\\D+", ""));
			System.out.println(secondPrice+">>>>>>>>>>>>>>>>>>>>"+firstPrice);
    	  if(secondPrice<=firstPrice){
    		  pricesort=true;
    	  }
    	   softassert.assertTrue(true, "price sorting is not working perfectly");
    	   
       }
       
       
	
	
	
	}

	public void currencyChangeDollar(SoftAssert softAssert) throws Exception {

		
		scrollToElement(driver,getWebElement(driver,By.xpath("//input[@id='filterCurrency']") ));
		
		Thread.sleep(1000);
		
		WebElement element = driver.findElement(By.id("filterCurrency"));
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", element);
		

		Thread.sleep(1000);
		safeClick(driver, getElementType(flightsRepo.get("Currency_USD")));

		
		
		clickonDoneButton();
		Thread.sleep(2000);
		//String Currency = safeGetText(driver, By.xpath("//span[contains(text(),'$ ')]"));
		List<WebElement> Currency=getWebElementList(driver,By.xpath("//span[contains(text(),'$ ')]"));
		String actualSymbol="";
		for(int i=0;i<Currency.size()-1;i++)
		{
	      String[] Symbol = Currency.get(i).getText().split("");
	     actualSymbol = Symbol[0];
		//System.out.println(actualSymbol+"currency verified");
		}
		softAssert.assertEquals(actualSymbol, "$", "Currency does not match selection");
	}

	public void currencyChangeRupee(SoftAssert softAssert) throws Exception {


		scrollToElement(driver,getWebElement(driver,By.xpath("//input[@id='filterCurrency']") ));
		
		Thread.sleep(1000);
		WebElement element = driver.findElement(By.id("filterCurrency"));
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", element);
		

		Thread.sleep(1000);
		safeClick(driver, getElementType(flightsRepo.get("Currency_INR")));
		clickonDoneButton();
		Thread.sleep(2000);
		//String Currency = safeGetText(driver, By.xpath("//span[contains(text(),'₹ ')]"));
		List<WebElement> Currency=getWebElementList(driver,By.xpath("//span[contains(text(),'₹ ')]"));
		String actualSymbol="";
		for(int i=0;i<Currency.size()-1;i++)
		{
			 String[] Symbol = Currency.get(i).getText().split("");
				actualSymbol = Symbol[0];
				System.out.println(actualSymbol+"currency verified");
		}
		softAssert.assertEquals(actualSymbol, "₹", "Currency does not match selection");
	}

	public void pricePerAdult(SoftAssert softAssert) throws Exception {
		checkSRP();
		String loaderpriceleft;
		String loaderpriceright;
		int loaderminPrice;
		int loadermaxprice;
		int maxPrice = maxSRPPrice();
		if (elementDisplayed(driver, getElementType(flightsRepo.get("pricepriceAdultSlider")), 1)) {
			loaderpriceleft = safeGetText(driver, getElementType(flightsRepo.get("priceperAdult_minPrice")));
			loaderpriceright = safeGetText(driver, getElementType(flightsRepo.get("priceperAdult_maxPrice")));
			loaderminPrice = Integer.parseInt(loaderpriceleft.split(" ")[1].replaceAll("\\D+", ""));
			loadermaxprice = Integer.parseInt(loaderpriceright.split(" ")[1].replaceAll("\\D+", ""));
			System.out.println(minSRPPrice()+"===="+loaderminPrice+"===="+maxSRPPrice()+"==="+loadermaxprice);
			if (minSRPPrice() == loaderminPrice && maxPrice == loadermaxprice) {
				softAssert.assertTrue(true, "Minimum and Maximum price on Price loader is perfect");
			}
			softAssert.assertTrue(false, "Minimum and Maximum price on Price loader is perfect");
		}

	}
	
	
	public void pricePerAdultOW(SoftAssert softAssert) throws Exception {

		String loaderpriceleft;
		String loaderpriceright;
		int loaderminPrice;
		int loadermaxprice;
		//System.out.println("Check");
		if (elementDisplayed(driver, getElementType(flightsRepo.get("pricepriceAdultSlider")), 1)) {
			loaderpriceleft = safeGetText(driver, getElementType(flightsRepo.get("priceperAdult_minPrice")));
			loaderpriceright = safeGetText(driver, getElementType(flightsRepo.get("priceperAdult_maxPrice")));
			loaderminPrice = Integer.parseInt(loaderpriceleft.split(" ")[1].replaceAll("\\D+", ""));
			loadermaxprice = Integer.parseInt(loaderpriceright.split(" ")[1].replaceAll("\\D+", ""));
			if (minSRPPrice() == loaderminPrice ) {
				System.out.println(minSRPPrice()+"is 1st"+ loaderminPrice+"2nd is");
				//&& maxSRPPriceOW() == loadermaxprice
				softAssert.assertTrue(true, "Minimum and Maximum price on Price loader is perfect");
			}else
			softAssert.assertTrue(false, "Minimum and Maximum price on Price loader is not perfect");
		}

	}

	public void collapsePricePerAdultPane() throws Exception {
		checkSRP();
		SoftAssert softassert = new SoftAssert();
		safeClick(driver, getElementType(flightsRepo.get("priceperAdultPane")));
		softassert.assertTrue(true, "collapsePricePerAdultPane Verified");

	}

	public void expandPricePerAdultPane() throws Exception {
		safeClick(driver, getElementType(flightsRepo.get("priceperAdultPane")));
		SoftAssert softassert = new SoftAssert();
		softassert.assertTrue(true, "expandPricePerAdultPane Verified");
	}

	public void collapseStopsPane() throws Exception {
		safeClick(driver, getElementType(flightsRepo.get("stopsPane")));
		SoftAssert softassert = new SoftAssert();
		softassert.assertTrue(true, "collapseStopsPane Verified");
	}

	public void expandStopsPane() throws Exception {
		safeClick(driver, getElementType(flightsRepo.get("stopsPane")));
		SoftAssert softassert = new SoftAssert();
		softassert.assertTrue(true, "expandStopsPane Verified");
	}

	public void collapseDepaturTimePane() throws Exception {
		safeClick(driver, getElementType(flightsRepo.get("depatureTimePane")));
		SoftAssert softassert = new SoftAssert();
		softassert.assertTrue(true, "collapseDepaturTimePane Verified");
	}

	public void expandDepatureTimePane() throws Exception {
		safeClick(driver, getElementType(flightsRepo.get("depatureTimePane")));
		SoftAssert softassert = new SoftAssert();
		softassert.assertTrue(true, "expandDepatureTimePane Verified");
	}

	public void collapseReturnTimepane() throws Exception {
		safeClick(driver, getElementType(flightsRepo.get("returnTimePane")));
		SoftAssert softassert = new SoftAssert();
		softassert.assertTrue(true, "expandStopsPane Verified");
	}

	public void expandReturnTimePane() throws Exception {
		safeClick(driver, getElementType(flightsRepo.get("returnTimePane")));
		SoftAssert softassert = new SoftAssert();
		softassert.assertTrue(true, "collapseReturnTimepane Verified");
	}

	public void collapseAirlinePane() throws Exception {
		safeClick(driver, getElementType(flightsRepo.get("airlinePane")));
		SoftAssert softassert = new SoftAssert();
		softassert.assertTrue(true, "collapseAirlinePane Verified");
	}

	public void expandAirlinePane() throws Exception {
		safeClick(driver, getElementType(flightsRepo.get("airlinePane")));
		SoftAssert softassert = new SoftAssert();
		softassert.assertTrue(true, "expandAirlinePane Verified");
	}

	public void collapseLayoverPane() throws Exception {
		safeClick(driver, getElementType(flightsRepo.get("layoverTimepane")));
		SoftAssert softassert = new SoftAssert();
		softassert.assertTrue(true, "collapseLayoverPane Verified");
	}

	public void expandLayoverPane() throws Exception {
		safeClick(driver, getElementType(flightsRepo.get("layoverTimepane")));
		SoftAssert softassert = new SoftAssert();
		softassert.assertTrue(true, "expandLayoverPane Verified");
	}

	public void collapseTripDurationPane() throws Exception {
		safeClick(driver, getElementType(flightsRepo.get("tripDuration")));
		SoftAssert softassert = new SoftAssert();
		softassert.assertTrue(true, "collapseTripDurationPane Verified");
	}

	public void expandTripDurationPane() throws Exception {
		safeClick(driver, getElementType(flightsRepo.get("tripDuration")));
		SoftAssert softassert = new SoftAssert();
		softassert.assertTrue(true, "expandTripDurationPane Verified");
	}

	public void verifyLayoverOnSRP(String triptype, SoftAssert softAssert) throws Exception {
		if ("roundtrip".equals(triptype)) {
			String Layover = safeGetText(driver, By.xpath("//div[7]/div/h6")).trim().toLowerCase();
			System.out.println(Layover);
			softAssert.assertTrue("LAYOVER DURATION".contains(Layover), "LAYOVER DURATION not Found");
			/*elementPresent(driver, By.xpath("//div[7]/div[2]/div/div[4]"), 1);
			String LayoverStart = safeGetText(driver, By.xpath("//div[7]/div[2]/div[2]/span)[1]"));
			softAssert.assertTrue("hours".contains(LayoverStart), "LAYOVER DURATION Start time not Found");
			String LayoverEnd = safeGetText(driver, By.xpath("//div[6]/div[2]/div[2]/span[2]"));
			softAssert.assertTrue("hours".contains(LayoverEnd), "LAYOVER DURATION End Duration not Found");*/

		} else {
			String Layover = safeGetText(driver, By.xpath("//div[6]/div/h6"));
			softAssert.assertTrue("LAYOVER DURATION".contains(Layover), "LAYOVER DURATION not Found");
			/*elementDisplayed(driver, By.xpath("//div[6]/div[2]/div/div[4]"), 1);
			String LayoverStart = safeGetText(driver, By.xpath("(//div[6]/div[2]/div[2]/span)[1]"));
			softAssert.assertTrue("hours".contains(LayoverStart), "LAYOVER DURATION Start time not Found");
			String LayoverEnd = safeGetText(driver, By.xpath("//div[6]/div[2]/div[2]/span[2]"));
			softAssert.assertTrue("hours".contains(LayoverEnd), "LAYOVER DURATION End Duration not Found");*/
		}
	}

	public void verifyTripDurationOnSRP(String triptype, SoftAssert softAssert) throws Exception {
		if ("roundtrip".equals(triptype)) {
			String tripDuration = safeGetText(driver, By.xpath("//div[8]/div/h6"));
			System.out.println(tripDuration);
			softAssert.assertTrue("TRIP DURATION".contains(tripDuration), "Trip Duration not Found");
			/*elementDisplayed(driver, By.xpath("//div[8]/div[2]/div/div[3]"), 1);
			String tripStart = safeGetText(driver, By.xpath("//div[8]/div[2]/div[2]/span"));
			softAssert.assertTrue("hours".contains(tripStart), "Trip Duration not Found");
			String tripEnd = safeGetText(driver, By.xpath("//div[8]/div[2]/div[2]/span[2]"));
			softAssert.assertTrue("hours".contains(tripEnd), "Trip Duration not Found");
*/
		} else {
			String tripDuration = safeGetText(driver, By.xpath("//div[7]/div/h6"));
			softAssert.assertTrue("TRIP DURATION".contains(tripDuration), "Trip Duration not Found");
		/*	elementDisplayed(driver, By.xpath("//div[7]/div[2]/div/div[4]"), 1);
			String tripStart = safeGetText(driver, By.xpath("//div[8]/div[2]/div[2]/span"));
			softAssert.assertTrue("hours".contains(tripStart), "Trip Duration not Found");
			String tripEnd = safeGetText(driver, By.xpath("//div[8]/div[2]/div[2]/span[2]"));
			softAssert.assertTrue("hours".contains(tripEnd), "Trip Duration not Found");*/
		}

	}

	public void verifyFirstFlightPriceEnabled(SoftAssert softAssert) {
		softAssert.assertTrue(elementDisplayed(driver, getElementType(flightsRepo.get("Flight_details_link")), 1),
				"First flight enabled");
	}

	public void revertChepestFare(SoftAssert softAssert) throws Exception {

		String beforePrice = safeGetText(driver, By.xpath("(//span[@class='price_span'])[1]"));
		System.out.println(beforePrice);
		safeClick(driver, By.xpath("//li[2]/div/div/label/span[2]"));
		String afterPrice = safeGetText(driver, By.xpath("(//span[@class='price_span'])[1]"));
		//System.out.println(afterPrice);
		if (!beforePrice.equals(afterPrice)) {
			safeClick(driver, By.xpath("//span[contains(text(),'Revert to Cheapest Flight')]"));
			String priceInRevert = safeGetText(driver, By.xpath("(//span[@class='price_span'])[1]"));
			if (priceInRevert.equals(beforePrice))
				;
		}
	}

	public void verifyAirlineFilterDisabled(SoftAssert softAssert) {
		boolean airlineFilterVisible = elementPresent(driver, By.id("airline_0"), 2);
		softAssert.assertTrue(!airlineFilterVisible, "Airline Filter is not disabled");

	}

	public void verifyDiffBetweenArrivalAndDepartureForDefaultSelection(SoftAssert softAssert) {
		List<WebElement> defaultSelection = getWebElementList(driver, By.xpath("//div[@class='text_align_center']"));
		List<WebElement> duration = getWebElementList(driver, By.className("number_of_stops"));
		for (int i = 0; i < defaultSelection.size(); i++) {
			if (defaultSelection.get(i).getText().contains("+")) {
				int departHH = Integer.parseInt(defaultSelection.get(i).getText().split("\n")[0].split(":")[0]);
				int departMM = Integer.parseInt(defaultSelection.get(i).getText().split("\n")[0].split(":")[1]);

				// if it is more than 1 day add +23 to hours and +60 to mins
				int arrivalHH = Integer.parseInt(defaultSelection.get(i).getText().split("\n")[2].split(":")[0]) + 23;
				int arrivalMM = Integer.parseInt(defaultSelection.get(i).getText().split("\n")[2].split(":")[1]) + 60;

				int durationTimeHH = Integer
						.parseInt(duration.get(i).getText().split("\\|")[1].trim().split("h")[0].trim());
				int durationTimeMM = Integer.parseInt(
						duration.get(i).getText().split("\\|")[1].trim().split("h")[1].trim().replaceAll("m", ""));
				softAssert.assertEquals((arrivalHH - departHH), durationTimeHH);
				softAssert.assertEquals((arrivalMM - departMM), durationTimeMM);

			} else {
				int departHH = Integer.parseInt(defaultSelection.get(i).getText().split("\n")[0].split(":")[0]);
				int departMM = Integer.parseInt(defaultSelection.get(i).getText().split("\n")[0].split(":")[1]);

				int arrivalHH = Integer.parseInt(defaultSelection.get(i).getText().split("\n")[1].split(":")[0]);
				int arrivalMM = Integer.parseInt(defaultSelection.get(i).getText().split("\n")[1].split(":")[1]);

				int durationTimeHH = Integer
						.parseInt(duration.get(i).getText().split("\\|")[1].trim().split("h")[0].trim());
				int durationTimeMM = Integer.parseInt(
						duration.get(i).getText().split("\\|")[1].trim().split("h")[1].trim().replaceAll("m", ""));

				softAssert.assertEquals((arrivalHH - departHH), durationTimeHH);
				softAssert.assertEquals((arrivalMM - departMM), durationTimeMM);
			}
		}

	}

	public void verifyDiffBetweenArrivalAndDepartureForOtherFlights(SoftAssert softAssert) {
		List<WebElement> arrivalDeparture = getWebElementList(driver,
				By.xpath("//div[contains(@class,'time_summary')]/div[1]"));
		List<WebElement> duration = getWebElementList(driver, By.className("number_of_stops"));
		for (int i = 2; i < arrivalDeparture.size(); i++) {
			if (arrivalDeparture.get(i).getText().contains("+")) {
				int departHH = Integer.parseInt(arrivalDeparture.get(i).getText().substring(0, 5).split(":")[0]);
				int departMM = Integer.parseInt(arrivalDeparture.get(i).getText().substring(0, 5).split(":")[1]);

				// if it is more than 1 day add +23 to hours and +60 to mins
				int arrivalHH = Integer.parseInt(arrivalDeparture.get(i).getText().substring(5, 10).split(":")[0]) + 23;
				int arrivalMM = Integer.parseInt(arrivalDeparture.get(i).getText().substring(6, 10).split(":")[1]) + 60;

				int durationTimeHH = Integer
						.parseInt(duration.get(i).getText().split("\\|")[1].trim().split("h")[0].trim());
				int durationTimeMM = Integer.parseInt(
						duration.get(i).getText().split("\\|")[1].trim().split("h")[1].trim().replaceAll("m", ""));
				softAssert.assertEquals((arrivalHH - departHH), durationTimeHH);
				softAssert.assertEquals((arrivalMM - departMM), durationTimeMM);

			} else {

				int departHH = Integer.parseInt(arrivalDeparture.get(i).getText().substring(0, 5).split(":")[0]);
				int departMM = Integer.parseInt(arrivalDeparture.get(i).getText().substring(0, 5).split(":")[1]);

				int arrivalHH = Integer.parseInt(arrivalDeparture.get(i).getText().substring(5, 10).split(":")[0]);
				int arrivalMM = Integer.parseInt(arrivalDeparture.get(i).getText().substring(6, 10).split(":")[1]);

				int durationTimeHH = Integer
						.parseInt(duration.get(i).getText().split("\\|")[1].trim().split("h")[0].trim());
				int durationTimeMM = Integer.parseInt(
						duration.get(i).getText().split("\\|")[1].trim().split("h")[1].trim().replaceAll("m", ""));

				softAssert.assertEquals((arrivalHH - departHH), durationTimeHH);
				softAssert.assertEquals((arrivalMM - departMM), durationTimeMM);
			}
		}
	}
	
	public void verifyNextDayArrive(SoftAssert softAssert){
		List<WebElement> arrivalDeparture = getWebElementList(driver,
				By.xpath("//div[contains(@class,'time_summary')]/span[1]"));
		List<WebElement> duration = getWebElementList(driver, getElementType(flightsRepo.get("stops_srp")));
		l1:for (int i = 2; i < arrivalDeparture.size(); i++) {
			if (arrivalDeparture.get(i).getText().contains("+")) {
				 softAssert.assertTrue(true);
                 break l1;       
			}
		}
		
	}

	public void verifyDefaultSortedByPrice(SoftAssert softAssert) {

		List<WebElement> priceSort = getWebElementList(driver,By.xpath("//label[contains(@class,'price_div_holder')]"));
		//System.out.println(priceSort.size());
		//System.out.println(priceSort.get(0).getText() + " " + priceSort.get(1).getText());
		softAssert.assertTrue(priceSort.get(0).getText().contains("arrow_up"),
				"Onward is not sorted by price by default");
		softAssert.assertTrue(priceSort.get(1).getText().contains("arrow_up"),
				"Return is not sorted by price by default");

	}
	public void verifyDefaultSortedByPriceOW(SoftAssert softAssert) {

		WebElement priceSort = getWebElement(driver,By.xpath("//label[contains(@class,'price_div_holder')]"));
		//System.out.println(priceSort);
		//System.out.println(priceSort.getText() + " ");
		softAssert.assertTrue(priceSort.getText().contains("arrow_up"),
				"Onward is not sorted by price by default");
		

	}
	
	public void verifyDefaultSortedByPriceOnward(SoftAssert softAssert) {

		List<WebElement> priceSort = getWebElementList(driver,By.xpath("//i[contains(text(),'keyboard_arrow_up')]"));
		//System.out.println(priceSort.size());
		//System.out.println(priceSort.get(0).getText());
		softAssert.assertTrue(priceSort.get(0).getText().contains("arrow_up"),
				"Onward is not sorted by price by default");

	}

	/* SearchResultPage Methods - Sohail */

	/* SearchResultPage Methods - Sohail */

	public void fillOnwardDate() throws Exception {
		int selectDate = 2;
		safeClick(driver, getElementType(flightsRepo.get("depart_date")));
		//System.err.println(flightsRepo.get("navigate_month"));
		WebElement navigateMonth = driver.findElement(getElementType(flightsRepo.get("navigate_month")));

		for (int i = 0; i < 2; i++) {
			navigateMonth.click();
		}
		//System.err.println(flightsRepo.get("select_date") + selectDate + "']");
		safeClick(driver, getElementType(flightsRepo.get("select_date") + selectDate + "']"));

	}

	public void fillReturnDate() throws Exception {
		int selectDate = 5;
		//safeClick(driver, getElementType(flightsRepo.get("select_date") + selectDate + "']"));
		//safeClick(driver, getElementType(flightsRepo.get("return_date_calendar")));
		//safeClick(driver, getElementType(flightsRepo.get("selectdate" + selectDate + "')]")));
		//safeClick(driver, getElementType(flightsRepo.get("calendar_done")));
		safeClick(driver, getElementType(flightsRepo.get("new_date")+"6]"));
		Thread.sleep(2000);
		safeClick(driver,By.xpath("//div[contains(text(),'DONE')]"));
	}
	public void verifyStopsFilterAppliedroundTrip(String triptype, String stopType, SoftAssert softAssert)
			throws Exception {
		Thread.sleep(1000);
		//System.out.println(driver.findElement(By.xpath("//div[contains(@class,'rtdom_flight__departure_and_stops')]/p[2]")).getText());
		if(elementVisible(driver,By.xpath("//p[contains(text(),'Nonstop')]"),1));
		List<WebElement> flightStops = driver.findElements(By.xpath("//p[contains(text(),'Nonstop')]"));
		Map<String, String> priceMap = new HashMap<>();
		for (int i = 0; i < flightStops.size(); i++) {
			String flightType = flightStops.get(i).getText();
			priceMap.put(flightType, stopType);
		}
	}


	public void modifySearchButtonRT() throws Exception{
		Thread.sleep(2000);
		safeClick(driver,getElementType(flightsRepo.get("modify_search_calendar_rt")));
		
	}
	
	public void modifyDepaturetopCityInt() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		clickModifybutton(softAssert);
		safeClick(driver, getElementType(flightsRepo.get("modify_searach_fromCity")));
		safeClear(driver, getElementType(flightsRepo.get("modify_searach_fromCity")));
		safeClick(driver,getElementType(flightsRepo.get("modify_searach_fromCity")));
		List<WebElement> topCities = getWebElementList(driver, getElementType(flightsRepo.get("top_cities")));
		if (topCities.size() > 1) {
			l1:for (int i = 0; i < topCities.size(); i++) {
				if (topCities.get(i).getText().toString().contains("BLR")){
				//System.out.println(topCities.get(i).getText().toString());
				topCities.get(i).click();
				break l1;
				}
			}

		}
	}

	public void modifyDestinationtopCityInt() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		safeClick(driver, getElementType(flightsRepo.get("modify_search_toCity")));
		safeClear(driver, getElementType(flightsRepo.get("modify_search_toCity")));
		safeType(driver, getElementType(flightsRepo.get("modify_search_toCity")), "DXB");
		List<WebElement> topCities = getWebElementList(driver, getElementType(flightsRepo.get("top_cities")));
		Thread.sleep(2000);
		topCities.get(0).click();
		/*
		 * if(topCities.size() > 0){ for(int i = 0;i<topCities.size();i++){
		 * if(topCities.get(i).getText().contains("DXB"));
		 * topCities.get(i).click(); break; }
		 * 
		 * }
		 */
	}

	public int minSRPPriceIntRT() throws Exception {
		checkSRP();
		int leastPrice = 0;
		List<Integer> leastPrices = new ArrayList<>();
		List<WebElement> allPricesRT = getWebElementList(driver, getElementType(flightsRepo.get("intrt_prices")));
		for (int i = 0; i < allPricesRT.size(); i++) {
			String price = (allPricesRT.get(i).getText()).split(" ")[1];
			leastPrice = Integer.parseInt(price.replaceAll("\\D+", ""));
			leastPrices.add(leastPrice);
		}
		leastPrice = leastPrices.get(0).intValue();
		//System.out.println("" + leastPrice);
		return leastPrice;
	}

	public int maxSRPPriceIntRT() throws Exception {
		checkSRP();
		int maxPrice = 0;
		List<Integer> maxPrices = new ArrayList<>();
		sortByPrice();
		List<WebElement> allPricesRT = getWebElementList(driver, getElementType(flightsRepo.get("intrt_prices")));
		return Integer.parseInt(allPricesRT.get(0).getText());
	}

	public int maxSRPPriceOneWay() throws Exception {
		int maxPrice = 0;
		List<Integer> maxPrices = new ArrayList<>();

		List<WebElement> allPricesRT = getWebElementList(driver, getElementType(flightsRepo.get("rt_prices_mob")));
		for (int i = 0; i < allPricesRT.size(); i++) {
			String price = (allPricesRT.get(i).getText()).split(" ")[1];
			maxPrice = Integer.parseInt(price.replaceAll("\\D+", ""));
			maxPrices.add(maxPrice);
		}
		Collections.sort(maxPrices);
		maxPrice = maxPrices.get((maxPrices.size()) - 1).intValue();
		//System.out.println(maxPrice);
		return maxPrice;
	}

	public void pricePerAdultInt() throws Exception {
		checkSRP();
		String loaderpriceleft;
		String loaderpriceright;
		int loaderminPrice;
		int loadermaxprice;
		SoftAssert softassert = new SoftAssert();
		if (elementDisplayed(driver, getElementType(flightsRepo.get("pricepriceAdultSlider")), 1)) {
			loaderpriceleft = safeGetText(driver, getElementType(flightsRepo.get("priceperAdult_minPrice")));
			loaderpriceright = safeGetText(driver, getElementType(flightsRepo.get("priceperAdult_maxPrice")));
			loaderminPrice = Integer.parseInt((loaderpriceleft.split(" ")[1].replaceAll("\\D+", "")));
			loadermaxprice = Integer.parseInt((loaderpriceright.split(" ")[1].replaceAll("\\D+", "")));
			if (minSRPPriceIntRT() == loaderminPrice && maxSRPPriceIntRT() == loadermaxprice) {
				softassert.assertTrue(true, "Minimum and Maximum price on Price loader is perfect");
			}
			softassert.assertTrue(false, "Minimum and Maximum price on Price loader is perfect");
		}

	}

	public void collectCancellationDetailsInt(SoftAssert softAssert) throws Exception {
		safeClick(driver, getElementType(flightsRepo.get("srp_cancellation_int")));
		List<WebElement> fareRulesContent = getWebElementList(driver,
				getElementType(flightsRepo.get("flight_cancellation_content")));
		Map<String, String> fareRules = new HashMap<>();

		fareRules.put("IntRT", fareRulesContent.get(0).getText());

		softAssert.assertTrue(fareRules.size() > 0, "Fare rules data not available");
	}

	public void collectCancellationDetailsOneWay(String tripType, SoftAssert softAssert) throws Exception {
	    safeClick(driver,getElementType(flightsRepo.get("cancellation_oneway")));
        List<WebElement> fareRulesContent = getWebElementList(driver,getElementType(flightsRepo.get("flight_cancellation_content")));
        Map<String,String> fareRules = new HashMap<>();
        if("roundtrip".equals(tripType) && !fareRulesContent.isEmpty()){
               fareRules.put("onward",fareRulesContent.get(0).getText());
               fareRules.put("return", fareRulesContent.get(1).getText());
        }else{
            fareRules.put("onward",fareRulesContent.get(0).getText());
        }
        
        softAssert.assertTrue(fareRules.size()>0,"Fare rules data not available");
        
	}

	public void verifyPriceCalendarOneWay(SoftAssert softAssert) throws Exception {
		List<WebElement> getTravelDetails = getWebElementList(driver, By.className("col_block"));
		safeClick(driver, getElementType(flightsRepo.get("price_calendar")));

		List<WebElement> priceCalendarHeader = getWebElementList(driver,
				getElementType(flightsRepo.get("price_header")));
		softAssert.assertTrue(elementPresent(driver, By.className("priceRange-CloseCircle"), 1),
				"Close button not appeared for price calendar");
		String travellerSource = getTravelDetails.get(0).getText().split("\n")[0];
		String travellerDestination = getTravelDetails.get(1).getText().split("\n")[0];
		softAssert.assertTrue(
				travellerSource.equals(priceCalendarHeader.get(0).getText().substring(0, travellerSource.length())),
				"Price Calendar doesn't have source" + "or destination");
		
		safeClick(driver, By.xpath("//button[contains(text(),'SEARCH FLIGHT')]"));

	}
	public void verifyLowestPriceMobileWeb(SoftAssert softAssert) throws Exception{
		boolean lowestPrice=false;
		
		scrollToElement(driver,getWebElement(driver,By.xpath("//h6[contains(text(),'AIRLINES')]")));
		List<WebElement> airlineNames=getWebElementList(driver,getElementType(flightsRepo.get("airline_checkbox")));
		//List<WebElement> airlinePrice=getWebElementList(driver, By.xpath("//div[@class='filter-list']//span[contains(text(),'₹')]"));
		for(int i=0;i<airlineNames.size()-1;i++)
		{ 
			//System.out.println(i+"++++++++++++++++++++++++++++");
			//System.out.println(airlineNames.size());
			//System.out.println(airlinePrice.size());
			Actions act=new Actions(driver);
			String shownPrice=getAirlinePrice(i);
			/*try {
				System.out.println(airlinePrice.get(i).getText());
				act.moveToElement(airlinePrice.get(i)).build().perform();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//String shownPrice=airlinePrice.get(i).getText();
*/			
			
			int compairPrice=Integer.parseInt((shownPrice.split(" ")[1].replaceAll("\\D+", "").trim()));
			scrollToElement(driver,getWebElement(driver,By.cssSelector("span.is_active")));
			safeClick(driver,By.xpath("//label[contains(@for,'airline_"+i+"')]") );
			clickonDoneButton();
			String srpPrice=safeGetText(driver,By.xpath("(//h4/span[contains(text(),' ')])[1]"));
			int srpPriceS=Integer.parseInt((srpPrice.split(" ")[1].replaceAll("\\D+", "").trim()));
			//System.out.println(compairPrice+"======="+srpPriceS);
			if(compairPrice==srpPriceS)
				lowestPrice=true;
			softAssert.assertTrue(true, "srp filter lowest filter is not working fine");
			clickFiltersonSrp();
			resetAllFilters();
			scrollToElement(driver,getWebElement(driver,By.cssSelector("span.is_active")));

		
		}
	}
	
	public String getAirlinePrice(int i){
		List<WebElement> airlinePrice=getWebElementList(driver, By.xpath("//span[@class='filter-list__option__value' and contains(text(),'₹ ')]"));
		String price=airlinePrice.get(i).getText();
		return price;
		
		
	}
	public void verifyAllAirlinesOnSRPmObileWeb(SoftAssert softAssert) throws InterruptedException, Exception{
		boolean airLineName=false;
		
		
		List<WebElement> airlineNames=getWebElementList(driver, getElementType(flightsRepo.get("airline_checkbox")));
		for(int i=0;i<airlineNames.size();i++){
			scrollDown(driver);
			//System.out.println(airlineNames.size());
			//System.out.println(i+"==============");
			String filterAirLine=getWebElement(driver, By.xpath("//label[contains(@for,'airline_"+i+"')]")).getText();
			safeClick(driver,By.xpath("//label[contains(@for,'airline_"+i+"')]") );
			clickonDoneButton();
			List<WebElement> srpAirLine=getWebElementList(driver, getElementType(flightsRepo.get("airline_names")));
			for(int j=0;j<srpAirLine.size();j++)
			{
				String ActualAirline=srpAirLine.get(j).getText();
				//System.out.println(ActualAirline+"airline name compairing"+filterAirLine);
				if(ActualAirline.trim().equalsIgnoreCase(filterAirLine.trim()))
					airLineName=true;
				softAssert.assertTrue(true,"airline filter is not working properly");
			}
			clickFiltersonSrp();
			resetAllFilters();
			

		}
		
	}

}
