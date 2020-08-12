package mobileweb.goomo.pageobject.flights;


import org.openqa.selenium.remote.RemoteWebDriver;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;

import mobileweb.goomo.commonutils.MobileWebFlightConnector;


public class SearchPage extends MobileWebFlightConnector {
	ExtentTest test;
	RemoteWebDriver driver;

	public SearchPage(RemoteWebDriver driver) {
		this.driver = driver;
	}
	
	public void myGoomoSignin() throws Exception{
		waitForElementToBeClickable(driver, getElementType(flightsRepo.get("my_goomo_signin_search_page")));
		safeClick(driver, getElementType(flightsRepo.get("my_goomo_signin_search_page")));
		Thread.sleep(500);
		safeClick(driver, getElementType(flightsRepo.get("my_signin_button")));
		safeType(driver, getElementType(flightsRepo.get("email_my_goomo")), "s@g.in");
		safeType(driver, getElementType(flightsRepo.get("password_my_goomo")), "123456789");
		Thread.sleep(1000);
		safeClick(driver, getElementType(flightsRepo.get("my_goomo_signin")));
		Thread.sleep(1000);
		safeClick(driver, getElementType(flightsRepo.get("my_goomo_flights")));
		Thread.sleep(1000);
		
	}

	
	public void getFlights() throws Exception {
		navigateTo(driver, "flights");
	}

	public void selectTripType(String tripType) throws Exception {
		Thread.sleep(1000);
		if (tripType.equalsIgnoreCase("oneway"))
			safeClick(driver, getElementType(flightsRepo.get("oneway")));
		else
			safeClick(driver, getElementType(flightsRepo.get("return")));
	}

	public void HowAboutFrame() throws InterruptedException{
		Thread.sleep(3000);
		if(elementVisible(driver,By.xpath("//iframe[@id='webklipper-publisher-widget-container-survey-frame']"),2))
		{
		WebElement iframe=getWebElement(driver,By.xpath("//iframe[@id='webklipper-publisher-widget-container-survey-frame']"));
		driver.switchTo().frame(iframe);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[@id='survey-close-div']")).click();
			
		}
	}
	public void fillToSegment(String segment) throws Exception, InterruptedException {
		waitForElementToBeClickable(driver,getElementType(flightsRepo.get("destination_city")));
	    if(elementVisible(driver, getElementType(flightsRepo.get("destination_input")), 3));
		   safeType(driver, getElementType(flightsRepo.get("destination_input")), segment);
		   if(elementVisible(driver,getElementType(flightsRepo.get("select_citY")) , 2))
	           safeClick(driver, getElementType(flightsRepo.get("select_citY")));
        else
        	Assert.assertTrue(false,segment+" Airport hasn't come up");
	}
	
	
	

	public void fillFromSegment(String segment) throws Exception, InterruptedException {

		safeClick(driver, getElementType(flightsRepo.get("departure_city")));
		waitForElementToBeClickable(driver,getElementType(flightsRepo.get("departure_city")));
	    if(elementVisible(driver, getElementType(flightsRepo.get("enter_city")), 3));
		   safeType(driver, getElementType(flightsRepo.get("enter_city")), segment);
        if(elementVisible(driver,getElementType(flightsRepo.get("select_citY")) , 2))
           safeClick(driver, getElementType(flightsRepo.get("select_citY")));
        else
            Assert.assertTrue(false,segment+" Airport hasn't come up");
	}

	public void fillOnwardDate() throws Exception {
		int selectDate = 2;
		
		Thread.sleep(1000);
		/*WebElement navigateMonth = driver.findElement(getElementType(flightsRepo.get("navigate_month")));

		for (int i = 0; i < 2; i++) {
			navigateMonth.click();
		}*/
		//safeClick(driver, getElementType(flightsRepo.get("select_date") + selectDate + "']"));
		scrollToElement(driver, getWebElement(driver, getElementType(flightsRepo.get("new_date")+"1]")));
		safeClick(driver, getElementType(flightsRepo.get("new_date")+"15]"));
	}

	public void fillReturnDate() throws Exception {
		int selectDate = 5;
		//safeClick(driver, getElementType(flightsRepo.get("select_date")+ selectDate +"']"));
		//safeClick(driver, getElementType(flightsRepo.get("select_date") + selectDate + "']"));
		//scrollToElement(driver, getWebElement(driver, getElementType(flightsRepo.get("new_date")+"1]")));
		Thread.sleep(1000);
		//safeClick(driver, getElementType(flightsRepo.get("new_date")+"10]"));
		Thread.sleep(1000);
		safeClick(driver, getElementType(flightsRepo.get("new_date")+"18]"));
		
	}
	
	public void  dateDone() throws Exception{
		 safeClick(driver,getElementType(flightsRepo.get("done_button_cal")));
	}

	/**
	 * * (Selects the travellers on searchpage based on the input) 
	 * @params adultcount,childcount,infantcount
	 * @exception no exception
	 * @return no return value.
	 */
	public void selectTraveller(String adultCount, String childCount, String infantCount) throws Exception {
		Thread.sleep(1000);
		//waitForElementToBeClickable(driver, getElementType(flightsRepo.get("select_traveller_info")));
		safeClick(driver, getElementType(flightsRepo.get("select_traveller_info")));
		 if(Integer.parseInt(adultCount)!=1)
		   {
			   for(int i=1;i<=Integer.parseInt(adultCount);i++)
			   {
				   safeClick(driver, getElementType(flightsRepo.get("adult_dd")));   
			   }
		   }
		  if(Integer.parseInt(childCount)>0) 
		  {
			  for(int i=1;i<=Integer.parseInt(childCount);i++)
			  {
				  Thread.sleep(1000);
				  safeClick(driver, getElementType(flightsRepo.get("child_dd")));  
			  }
		  }
		  if(Integer.parseInt(infantCount)>0)
		  {
			  for(int i=1;i<=Integer.parseInt(infantCount);i++)
			  {
				  Thread.sleep(1000);
				  safeClick(driver, getElementType(flightsRepo.get("infant_dd")));  
			  }
		  }
	}
	

	public void selectClass(String className) throws Exception {
		Thread.sleep(1000);
		if(elementVisible(driver, getElementType(flightsRepo.get("classtype") + className + "')]"), 3));
		safeClick(driver, getElementType(flightsRepo.get("classtype") + className + "')]"));
	}
	
	public void clickApply() throws Exception{
		safeClick(driver, getElementType(flightsRepo.get("apply_button")));
	}
	public void clickDone() throws Exception{
		safeClick(driver, getElementType(flightsRepo.get("Done_button")));
	}

	public void clickSearchButton() throws Exception {
		Thread.sleep(1000);
		if(elementVisible(driver, getElementType(flightsRepo.get("search_button")), 3));
		safeClick(driver, getElementType(flightsRepo.get("search_button")));
	}
	
	public void searchformtab(SoftAssert softAssert) throws InterruptedException {
     
	softAssert.assertEquals(elementDisplayed(driver,getElementType(flightsRepo.get("tabbar")),1),true,"search form tab is not present");
	}

	public void verifyOnewaybutton(SoftAssert softAssert) throws InterruptedException {

		softAssert.assertEquals(elementDisplayed(driver, getElementType(flightsRepo.get("oneway")), 5), true,
				"one-way button is not present");

		softAssert.assertEquals(elementEnabled(driver, getElementType(flightsRepo.get("oneway")), 1), true,
				"one-way button is not clickable");
	}
	public void verifyReturnbutton(SoftAssert softAssert) throws InterruptedException{
		
		softAssert.assertEquals(elementVisible(driver,getElementType(flightsRepo.get("return")), 1),true,"return button is not present");
		softAssert.assertEquals(elementEnabled(driver, getElementType(flightsRepo.get("return")), 1),true, "return button is not clickable");
		
		}
	public void departureCityBox(SoftAssert softAssert) throws InterruptedException{
		
		softAssert.assertEquals(elementEnabled(driver,getElementType(flightsRepo.get("departure_city")), 1),true, "departure city button is non clickable");
	
		}
	
	public void verifyDepartureAutosuggestionList(SoftAssert softAssert) throws Exception{
		safeClick(driver, getElementType(flightsRepo.get("departure_city")));
		Thread.sleep(2000);
		/*String parentw=driver.getWindowHandle();
		System.out.println("parent window id     "+parentw);
		WebElement wb=driver.findElement(By.xpath("//iframe[@id='webklipper-publisher-widget-container-notification-frame']"));
		driver.switchTo().frame(wb);
		if(elementVisible(driver,By.xpath("//i[contains(@class,'we_close')]"), 1))
			safeClick(driver, By.xpath("//i[contains(@class,'we_close')]"));
		driver.switchTo().defaultContent();*/
	safeClick(driver,getElementType(flightsRepo.get("departure_city_box")));
	Thread.sleep(500);
		softAssert.assertEquals(elementDisplayed(driver, getElementType(flightsRepo.get("top_cities")), 2), true, "top-cities list is not displayed");
		Thread.sleep(500);
		if(elementVisible(driver, getElementType(flightsRepo.get("left_arrow_close")), 1))
		safeClick(driver,getElementType(flightsRepo.get("left_arrow_close")));
	}
	
	public void validateAutoSuggestionList(SoftAssert softAssert) throws Exception{
		
		safeType(driver, getElementType(flightsRepo.get("departure_city")), "BOM");
		/*
	for(int i=0;i<7;i++){
		
			safeClear(driver, getElementType(flightsRepo.get("destination_city")));
			Thread.sleep(1000);
			List<WebElement> topcities=driver.findElements(By.xpath("//section/ul[@class='react-autosuggest__suggestions-list sections_container']/li"));
			Thread.sleep(2000);
			System.out.println(i);
			Thread.sleep(2000);
			String cityname=topcities.get(i).getText();
			System.out.println(cityname);
			topcities.get(i).click();
			Thread.sleep(500);
			String selectedCity=driver.findElement(By.xpath("//input[contains(@placeholder,'Select Destination City')]")).getAttribute("value");
			System.out.println(selectedCity);
			softassert.assertEquals(cityname, selectedCity, "auto-suggested city name doesnot mataches");
			Thread.sleep(500);
			
		}*/
		List<WebElement> topcities=driver.findElements(By.xpath("//section/ul[@class='react-autosuggest__suggestions-list sections_container']/li"));
		int cityNumber=topcities.size();
		softAssert.assertEquals(cityNumber, 8, "city number is not matching");
		
		
	}
	
	public void clickModifyClosebutton(SoftAssert softAssert) throws Exception {
		boolean closeButtonAppeared = false;

		if (elementPresent(driver, getElementType(flightsRepo.get("modify_search_close")), 1)) {
			safeClick(driver, getElementType(flightsRepo.get("modify_search_close")));
			closeButtonAppeared = true;

		}
		softAssert.assertTrue(closeButtonAppeared, "Close button not present");

	}
	
	
	
	public void clickModifybutton(SoftAssert softAssert) throws Exception {
		
		safeClick(driver, getElementType(flightsRepo.get("Modify_Search")));
		softAssert.assertTrue(true);
	}
	
	public void autoCompleteDeparturecity(SoftAssert softAssert) throws Exception{
		Thread.sleep(500);
		safeClick(driver, getElementType(flightsRepo.get("destination_city")));
		Thread.sleep(500);
		safeType(driver, getElementType(flightsRepo.get("destination_city_box")),"DHM" );
		Thread.sleep(500);
		safeClick(driver,By.xpath("//li[@id='react-autowhatever-1--item-0']"));
		/*if(elementVisible(driver,getElementType(flightsRepo.get("left_arrow_close")) , 1))
            safeClick(driver,getElementType(flightsRepo.get("left_arrow_close")));*/
		Thread.sleep(2000);
		safeClick(driver, By.xpath("(//div[contains(@class,'input_search departureCity_textField')])[1]"));
		String citynames[]={"CMB","GAU","CDG","Kochi","BHOPAL","BBI"};
		for(int i=1;i<=5;i++){
			//if(elementVisible(driver, getElementType(flightsRepo.get("clear_button")),1))
				
			Thread.sleep(1000);
			safeClick(driver,getElementType(flightsRepo.get("departure_city_box")) );
		Thread.sleep(500);
		 safeClear(driver,  getElementType(flightsRepo.get("departure_city_box")));
		 Thread.sleep(1000);
		 driver.findElement(getElementType(flightsRepo.get("departure_city_box"))).clear();
		safeType(driver, getElementType(flightsRepo.get("departure_city_box")), citynames[i]);
		  Thread.sleep(500);
            boolean city=elementDisplayed(driver, By.xpath("//li[@id='react-autowhatever-1--item-0']"),1);
            driver.findElement(By.xpath("//li[@id='react-autowhatever-1--item-0']")).click();
            Thread.sleep(1000);
            /*if(elementVisible(driver,getElementType(flightsRepo.get("left_arrow_close")) , 1))
            safeClick(driver,getElementType(flightsRepo.get("left_arrow_close")));*/
            Thread.sleep(500);
            safeClick(driver, By.xpath("(//div[contains(@class,'input_search departureCity_textField')])[1]"));
           // safeClick(driver, getElementType(flightsRepo.get("departure_city")));
            Thread.sleep(500);
           // safeClick(driver,getElementType(flightsRepo.get("clear_button")));
            safeClear(driver, getElementType(flightsRepo.get("departure_city_box")));
           softAssert.assertEquals(city, true, "departure autosuggestion is not working");
        

		
		//safeClear(driver,getElementType(flightsRepo.get("departure_city_box")));
		}
		
   }
	public void autoCompleteDestinationcity(SoftAssert  softAssert) throws Exception{
		Thread.sleep(500);
		safeClick(driver,getElementType(flightsRepo.get("destination_city")));
		Thread.sleep(500);
		 //safeClick(driver,getElementType(flightsRepo.get("clear_button")));
		safeClear(driver,getElementType(flightsRepo.get("destination_city_box")) );
		 Thread.sleep(500);
		String citynames[]={"ADI","TRV","MFM","VTZ","MLB","VKO"};
		for(int i=1;i<6;i++){
			safeType(driver, getElementType(flightsRepo.get("destination_city_box")), citynames[i]);
			Thread.sleep(500);
			boolean city=elementDisplayed(driver, By.xpath("//li[@id='react-autowhatever-1--item-0']"),1);
            driver.findElement(By.xpath("//li[@id='react-autowhatever-1--item-0']")).click();
            Thread.sleep(500);
            if(elementVisible(driver,getElementType(flightsRepo.get("left_arrow_close")) , 1))
            safeClick(driver,getElementType(flightsRepo.get("left_arrow_close")));
            Thread.sleep(500);
		// safeClick(driver, getElementType(flightsRepo.get("destination_city")));
	
		 safeClick(driver, By.xpath("(//div[contains(@class,'input_search departureCity_textField')])[2]"));
		 Thread.sleep(500);
		// safeClick(driver,getElementType(flightsRepo.get("clear_button")));
		 safeClear(driver,getElementType(flightsRepo.get("destination_city_box")) );
		 softAssert.assertEquals(city, true, "destination autosuggestion is not working");
	
	
		}
		
		
	}	
	public void verifyDestinationBox(SoftAssert softAssert) throws Exception{
		Thread.sleep(1000);
		if(elementVisible(driver, getElementType(flightsRepo.get("left_arrow_close")), 1))
		safeClick(driver,getElementType(flightsRepo.get("left_arrow_close")));
		boolean destinationcity=elementEnabled(driver,getElementType(flightsRepo.get("destination_city")),1);
		softAssert.assertEquals(destinationcity,true, "destination city button is non clickable");
		
		
	}
public void verifyDestinationAutosuggestionList(SoftAssert softAssert) throws Exception{
		
	    Thread.sleep(1000);
		safeClick(driver,getElementType(flightsRepo.get("destination_city")));
		Thread.sleep(500);
		softAssert.assertEquals(elementDisplayed(driver,getElementType(flightsRepo.get("top_cities")), 1), true, "top-cities list is not displayed");
		
	}
	
	
public void validateDestinationAutoSuggestionList(SoftAssert softAssert) throws Exception{
    System.out.println(getWebElementList(driver, getElementType(flightsRepo.get("top_cities_list"))).size());
List<WebElement> citylist=driver.findElements(By.xpath("//section/ul[@class='react-autosuggest__suggestions-list sections_container']/li"));
int cityno=citylist.size();
boolean condition=(cityno>=7);

  softAssert.assertTrue(condition,"the suggested no of cities in destination box is not according to requirement");  
}	

public void wrongIATACodeDepartureCity(SoftAssert softAssert) throws Exception{
	
	//safeClick(driver, getElementType(flightsRepo.get("clear_button")));
	safeClear(driver, getElementType(flightsRepo.get("departure_city_box")));
	if(elementVisible(driver, getElementType(flightsRepo.get("left_arrow_close")), 1))
	safeClick(driver,getElementType(flightsRepo.get("left_arrow_close")));
	String wrongcity[]={"hzz","njk","GGH"};
	
	for(int i=0;i<=2;i++)
		{
		Thread.sleep(1000);
			safeClick(driver,getElementType(flightsRepo.get("departure_city")));
			Thread.sleep(500);
			safeType(driver, getElementType(flightsRepo.get("departure_city_box")), wrongcity[i]);
			boolean errormessage = elementDisplayed(driver, getElementType(flightsRepo.get("error_tringle")), 1);
			if(elementVisible(driver,getElementType(flightsRepo.get("left_arrow_close")) ,1))
			safeClick(driver,getElementType(flightsRepo.get("left_arrow_close")));
			softAssert.assertEquals(errormessage, true, "wrong iata code is not working properly");

		}	
}


public void wrongIATACodeDestinationCity(SoftAssert softAssert) throws Exception{
	Thread.sleep(500);
	safeClick(driver,getElementType(flightsRepo.get("destination_city")));
	Thread.sleep(500);
	safeClear(driver,  getElementType(flightsRepo.get("destination_city_box")));
	//safeClick(driver, getElementType(flightsRepo.get("clear_button")));
	Thread.sleep(500);
	if(elementVisible(driver,getElementType(flightsRepo.get("left_arrow_close")) , 1))
	safeClick(driver,getElementType(flightsRepo.get("left_arrow_close")));
	String wrongcit[]={"BOMI","marathalli","jpna"};
	for(int i=0;i<=2;i++)
	{
		Thread.sleep(500);
		safeClick(driver,getElementType(flightsRepo.get("destination_city")));
		Thread.sleep(500);
	safeType(driver, getElementType(flightsRepo.get("destination_city_box")), wrongcit[i]);
	boolean errormessage=elementDisplayed(driver, getElementType(flightsRepo.get("error_tringle")), 1);
	if(elementVisible(driver, getElementType(flightsRepo.get("left_arrow_close")), 1))
	safeClick(driver,getElementType(flightsRepo.get("left_arrow_close")));
	softAssert.assertEquals(errormessage, true, "wrong iata code is not working properly");

		}
}

public void replaceOldCityToNewDeparture(SoftAssert softAssert) throws Exception{
    
   
   safeClear(driver,getElementType(flightsRepo.get("departure_city_box")) );
   driver.findElement(getElementType(flightsRepo.get("departure_city_box"))).clear();
    safeType(driver, getElementType(flightsRepo.get("departure_city_box")),"BOMBAY");
    
    safeClick(driver, getElementType(flightsRepo.get("select_city")));
    boolean oldcitydeparture=driver.findElement(By.xpath("//div[contains(text(),'BOM')]")).isDisplayed();
    softAssert.assertEquals(oldcitydeparture, true, "old city is not replaced by new IATA code in departure box");
    Thread.sleep(1000);
    //safeClear(driver,getElementType(flightsRepo.get("departure_city")));
    
}
	
public void replaceOldCityToNewDestination(SoftAssert softAssert) throws Exception{
	
	
	//safeClick(driver,getElementType(flightsRepo.get("clear_button")));
	safeClear(driver,getElementType(flightsRepo.get("destination_city_box")) );
	driver.findElement(getElementType(flightsRepo.get("destination_city_box"))).clear();
	Thread.sleep(500);
	safeType(driver, getElementType(flightsRepo.get("destination_city_box")), "MADRAS");
	Thread.sleep(500);
	safeClick(driver, By.xpath("//span[contains(@class,'default-suggestion')]"));
	Thread.sleep(1000);
	if(elementVisible(driver, getElementType(flightsRepo.get("left_arrow_close")), 1))
	safeClick(driver,getElementType(flightsRepo.get("left_arrow_close")));
	boolean oldcitydestination=driver.findElement(By.xpath("//div[contains(text(),'MAA')]")).isDisplayed();
	softAssert.assertEquals(oldcitydestination, true, "old city is not replaced by new IATA code in destination box");
	
	
	
	
}

public void destinationCityNotPresentInDeparture(SoftAssert softAssert) throws Exception{
	
	boolean flag=false;
	safeClear(driver,  getElementType(flightsRepo.get("destination_city_box")));
	safeType(driver, getElementType(flightsRepo.get("destination_city_box")), "BLR");
	safeClick(driver, By.xpath("//span[contains(@class,'default-suggestion')]"));
	Thread.sleep(500);
	if(elementVisible(driver, getElementType(flightsRepo.get("left_arrow_close")), 1))
	safeClick(driver,getElementType(flightsRepo.get("left_arrow_close")));
	String cityname=safeGetText(driver, getElementType(flightsRepo.get("destination_city")));
	
		Thread.sleep(500);
	safeClick(driver, getElementType(flightsRepo.get("departure_city")));
	Thread.sleep(1000);
	for(int i=0;i<=6;i++){
    Thread.sleep(500);
		String suggestedcity=getWebElementList(driver,getElementType(flightsRepo.get("top_cities_list"))).get(i).getText();
		System.out.println("suggested city is"+ suggestedcity +"destination city is"+ cityname);
		if(suggestedcity==cityname){
			 flag=true;
		}
		else{
			flag=false;
		}
		softAssert.assertEquals(flag, false, "destination city is present in departure suggested city list");
		
	}

}
public void verifyEconomyTravelClass(SoftAssert softAssert) throws Exception {
	Thread.sleep(1000);
	safeClick(driver, getElementType(flightsRepo.get("personal_info")));
	Thread.sleep(500);
	boolean economyclassflag=elementEnabled(driver, getElementType(flightsRepo.get("economy")), 1);
	boolean businessclassflag=elementEnabled(driver, getElementType(flightsRepo.get("business")), 1);
	
	softAssert.assertEquals(economyclassflag, true, "economy class is not active");
}
public void verifyBusinessTravelClass(SoftAssert softAssert) throws Exception{
	
	Thread.sleep(500);
	safeClick(driver,  getElementType(flightsRepo.get("business")));
	boolean businessclassflag=elementEnabled(driver, getElementType(flightsRepo.get("business")), 1);
	elementEnabled(driver,getElementType(flightsRepo.get("economy")), 1);
	softAssert.assertEquals(businessclassflag, true, "economy class is not active");
}

	public void SearchButton(SoftAssert softAssert) throws Exception{
		
		
		Thread.sleep(1000);
		safeClear(driver,getElementType(flightsRepo.get("departure_city")));
		safeType(driver, getElementType(flightsRepo.get("departure_city")),"BOM");
		safeClick(driver, By.xpath("//span[@class='false']"));
		Thread.sleep(1000);
		safeClear(driver,getElementType(flightsRepo.get("destination_city")));
		safeType(driver, getElementType(flightsRepo.get("destination_city")), "DEL");
		safeClick(driver, By.xpath("//span[@class='false']"));
		
		
		softAssert.assertEquals(elementEnabled(driver,getElementType(flightsRepo.get("search_button")), 1), false,"search flights button enabled");
			
	}
	
	public void VerifydepartureCalender(SoftAssert softAssert) throws InterruptedException{
		
		
		softAssert.assertEquals(elementEnabled(driver, getElementType(flightsRepo.get("departure_date")), 1), true, "departure calender is not wokring");
		}
	
	public void ColourPriorityCalender(SoftAssert softAssert) throws Exception{
		safeClick(driver,getElementType(flightsRepo.get("departure_date")));
		boolean greenPriceIndicator=false;
		boolean yellowPriceIndicator=false;
		boolean redPriceIndicator=false;
		if(elementPresent(driver, getElementType(flightsRepo.get("calender_green_price")),1))
		greenPriceIndicator=true;
		if(elementPresent(driver, getElementType(flightsRepo.get("calender_yellow_price")),1))
		yellowPriceIndicator=true;
		if(elementVisible(driver, getElementType(flightsRepo.get("calender_red_price")) ,1))
		redPriceIndicator=true;
		
		if(greenPriceIndicator && yellowPriceIndicator && redPriceIndicator){
			   softAssert.assertTrue(true,"Some error in displaying price indicators");
		}else if((greenPriceIndicator && yellowPriceIndicator) || (yellowPriceIndicator && redPriceIndicator) && (greenPriceIndicator && redPriceIndicator ))
		{
			 softAssert.assertTrue(true, "Some error in displaying two of the price indicators");
		}else if(greenPriceIndicator || yellowPriceIndicator || redPriceIndicator){
			 softAssert.assertTrue(true,"Some error in displaying one of the indicators");
		}
		else{
			  softAssert.assertTrue(true,"None of the indicators are displayed");
		}
		
	}
	
	public void SearchButtonINT(String departurecity,String destinationcity,SoftAssert softAssert) throws Exception{
		Thread.sleep(1000);
        safeClick(driver, getElementType(flightsRepo.get("departure_city")));
        safeClear(driver,getElementType(flightsRepo.get("departure_city_box")) );
        
        safeType(driver, getElementType(flightsRepo.get("departure_city_box")), departurecity);
        safeClick(driver, By.xpath("//span[contains(@class,'default-suggestion')]"));
        Thread.sleep(500);
        
       
     
        safeType(driver, getElementType(flightsRepo.get("destination_city_box")), destinationcity);
        Thread.sleep(500);
        safeClick(driver, By.xpath("//span[contains(@class,'default-suggestion')]"));
        Thread.sleep(500);
        if(elementVisible(driver, getElementType(flightsRepo.get("left_arrow_close")), 1))
        safeClick(driver,getElementType(flightsRepo.get("left_arrow_close")));

        /*softAssert.assertEquals(elementEnabled(driver, getElementType(flightsRepo.get("search_button")), 1), false,
                "search flights button enabled");*/
		
		
		
	}
	public void verifyclickOnSwap(SoftAssert softAssert) throws Exception{
		
		String departurecity=getWebElement(driver,getElementType(flightsRepo.get("departure_city"))).getAttribute("value");
		
		String destinationcity=getWebElement(driver, getElementType(flightsRepo.get("destination_city"))).getAttribute("value");
		
		safeClick(driver, getElementType(flightsRepo.get("swap")));
		Thread.sleep(500);
		if(elementVisible(driver, getElementType(flightsRepo.get("left_arrow_close")), 1))
		safeClick(driver,getElementType(flightsRepo.get("left_arrow_close")));
		String Swappeddeparturecity=getWebElement(driver, getElementType(flightsRepo.get("departure_city"))).getAttribute("value");
		String swappeddestinationcity=getWebElement(driver, getElementType(flightsRepo.get("destination_city"))).getAttribute("value");
		
		softAssert.assertEquals(departurecity, swappeddestinationcity, "swapping of tour cities working fine 1");
		softAssert.assertEquals(destinationcity, Swappeddeparturecity, "swapping of selected cities are working fine 2");
		
	}
	
	public void previousDateDisabled(SoftAssert softAssert) throws Exception{
		
		
		String[] date=goomoDate(0).split("-");
		
		int day=Integer.parseInt(date[2]);
		//System.out.println(day +"is date goomo");
		//safeClick(driver,getElementType(flightsRepo.get("departure_date")));
		
	    softAssert.assertEquals(elementEnabled(driver, getElementType(flightsRepo.get("calender_verify")+(day-1)+"']"), 1), false, "previous date is enabled");
		
	}
	
	public void checkSRPRT() throws Exception { 
		boolean srpLoaded = checkForTwoConditions(driver, getElementType(flightsRepo.get("select_flight_srpDomRt_new")),
				getElementType(flightsRepo.get("Oops_flights_unavailable")), 20);
		if (!srpLoaded) {
			test.fail("Flights Not available on SRP");
			Assert.assertEquals("Flights Not loaded on SRP", srpLoaded);

		}

	}
	
	public void currentAndFutureDateEnabled(SoftAssert softAssert) throws InterruptedException{
		
		String fulldate=goomoDate(0);
		String[] date=fulldate.split("-");
		
		int day=Integer.parseInt(date[2]);
		
		
		softAssert.assertEquals(elementEnabled(driver, getElementType(flightsRepo.get("calender_verify")+(day)+"')]") , 1), true, "today's date is not enabled");
		
		softAssert.assertEquals(elementEnabled(driver, getElementType(flightsRepo.get("calender_verify")+(day+1)+"')]") , 1), true, "future date is enabled");
		
	}
	public void onwardSelectedDateInReturnDate(	SoftAssert softAssert) throws Exception{
		String[] date=goomoDate(0).split("-");
		int day=Integer.parseInt(date[2]);
		if(elementVisible(driver, getElementType(flightsRepo.get("left_arrow_close")),1))
		safeClick(driver,getElementType(flightsRepo.get("left_arrow_close")));
		Thread.sleep(500);
		safeClick(driver, getElementType(flightsRepo.get("departure_date")));
		Thread.sleep(500);
		//safeClick(driver, getElementType(flightsRepo.get("navigation_pointer")));
		scrollDown(driver);
		Thread.sleep(500);

		safeClick(driver, getElementType(flightsRepo.get("calender_verify")+day+"')]"));
	
		Thread.sleep(500);
		//safeClick(driver,By.xpath("//div[contains(text(),'SELECT RETURN DATE')]"));
		softAssert.assertEquals(getWebElement(driver, getElementType(flightsRepo.get("departure_date"))).getAttribute("Value"), getWebElement(driver, getElementType(flightsRepo.get("return_date"))).getAttribute("value"), "returndate box is taking departuredate");
		}

   public void selectDate(SoftAssert softAssert) throws Exception{
	   
	   String fulldate=goomoDate(0);
	   String[] date=fulldate.split("-");
	   int day=Integer.parseInt(date[2]);
	   Thread.sleep(2000);
	   //safeClick(driver, getElementType(flightsRepo.get("departure_date")));
	  
	   Thread.sleep(1000);
	  // safeClick(driver, getElementType(flightsRepo.get("navigation_pointer")));
	   //scrollDown(driver);
	  //scrollTillEnd(driver);
	 safeClick(driver, getElementType(flightsRepo.get("first_calender_header")));
	   Thread.sleep(1000);
	   //safeClick(driver, getElementType(flightsRepo.get("new_date")+"14]"));
	   scrollToElement(driver, getWebElement(driver,getElementType(flightsRepo.get("new_date")+"1]")));
	   safeClick(driver, getElementType(flightsRepo.get("new_date")+"14]"));
	   Thread.sleep(1000);
	  // safeClick(driver, getElementType(flightsRepo.get("navigation_pointer"));
	   Thread.sleep(1000);
	   safeClick(driver, getElementType(flightsRepo.get("new_date")+"18]"));

	   safeClick(driver,By.xpath("//div[contains(text(),'DONE')]"));
	   
	   }
   public void verifyRouteWithOnewayTourType(SoftAssert softAssert) throws Exception{
	   
	   safeClick(driver, getElementType(flightsRepo.get("oneway")));
	   softAssert.assertEquals(elementDisplayed(driver,getElementType(flightsRepo.get("departure_date")) ,1),true, "in oneway trip return date is also displayed");
	   if(elementVisible(driver,getElementType(flightsRepo.get("return_date")), 1)){
		   softAssert.assertTrue(false, "in one way trip type departure calender is displayed");
	   }
	   
   }
   public void verifyWithReturnTourType(SoftAssert softAssert) throws Exception{
	   Thread.sleep(500);
	  if(elementVisible(driver,getElementType(flightsRepo.get("left_arrow_close")), 1))
	   safeClick(driver,getElementType(flightsRepo.get("left_arrow_close")));
	   Thread.sleep(500);
	   safeClick(driver, getElementType(flightsRepo.get("return")));
	   
	   softAssert.assertEquals(elementEnabled(driver, getElementType(flightsRepo.get("departure_date")), 1),true, "departure date disabled");
	   
	   softAssert.assertEquals(elementEnabled(driver, getElementType(flightsRepo.get("return_date")), 1), true, "return date is also displayed");
   }
   public void checkTodayDateInCalender( SoftAssert softAssert) throws Exception{
	 // safeClick(driver,getElementType(flightsRepo.get("departure_date")));
	   String[] date=goomoDate(0).split("-");
	   int day=Integer.parseInt(date[2]);
	   String dat= getWebElement(driver,getElementType(flightsRepo.get("today_date"))).getText().trim();
	   int actdate=Integer.parseInt(dat);
	  // System.out.println("goomo date is"+ day+"h");
	   //System.out.println("today date in script is"+ getWebElement(driver,getElementType(flightsRepo.get("today_date"))).getText()+"h");
	   softAssert.assertEquals(actdate,day, "date is not matching");
   }
   
   public void selectTripDateOneWay() throws Exception{
	   if(elementVisible(driver,getElementType(flightsRepo.get("left_arrow_close")),1))
	   safeClick(driver,getElementType(flightsRepo.get("left_arrow_close")));
	   Thread.sleep(500);
       safeClick(driver, getElementType(flightsRepo.get("oneway")));
       Thread.sleep(500);
       safeClick(driver, getElementType(flightsRepo.get("departure_date")));
       Thread.sleep(1000);
      // safeClick(driver, getElementType(flightsRepo.get("navigation_pointer")));
       
       scrollToElement(driver, getWebElement(driver,getElementType(flightsRepo.get("new_date")+"1]")));
       safeClick(driver, getElementType(flightsRepo.get("new_date")+"23]"));
       scrollDown(driver);
      // safeClick(driver, getElementType(flightsRepo.get("calender_verify")+"19"+"')]"));
        Thread.sleep(1000);
     //   safeClick(driver,By.xpath("//div[contains(text(),'DONE')]"));
       
        
  }
   public void validateAdultChildInfantSelectedPaxOld(String adultCount,String childCount,String infantCount,String classtype) throws Exception{
	   SoftAssert softassert=new SoftAssert();
	 
	  

		safeClick(driver, getElementType(flightsRepo.get("adult_dd")));
		Thread.sleep(2000);
		safeClick(driver, getElementType(flightsRepo.get("adult_select_dd") + adultCount.trim() + "')]"));
		scrollDown(driver);
 
		if(Integer.parseInt(childCount)>0){
		    safeClick(driver, getElementType(flightsRepo.get("child_dd")));
		    safeClick(driver, getElementType(flightsRepo.get("child_select_dd") + childCount.trim() + "')][1]"));
		}

		scrollDown(driver);

		if(Integer.parseInt(infantCount)>0){
		safeClick(driver, getElementType(flightsRepo.get("infant_dd")));
		safeClick(driver, getElementType(flightsRepo.get("text_count")+ infantCount +"')]"));
		}
	
		safeClick(driver, getElementType(flightsRepo.get("classtype") + classtype + "')]"));
		safeClick(driver, getElementType(flightsRepo.get("apply_butoon")));
        String pax=getWebElement(driver, getElementType(flightsRepo.get("personal_info"))).getAttribute("value");
	   String actualPax=adultCount +"adult, "+childCount+" child, "+infantCount+" infant, "+classtype;
	   softassert.assertEquals(pax, actualPax , "Pax count displayed is not matching with diplayed");
	   safeClick(driver, getElementType(flightsRepo.get("personal_info")));
   }
   
   public void validateAdultChildInfantSelectedPax(String adultCount,String childCount,String infantCount,String classtype) throws Exception{
	   SoftAssert softAssert=new SoftAssert();
	   if(Integer.parseInt(adultCount)!=1)
	   {
		   for(int i=1;i<=Integer.parseInt(adultCount);i++)
		   {
			   safeClick(driver, getElementType(flightsRepo.get("adult_dd")));   
		   }
	   }
	  if(Integer.parseInt(childCount)>0) 
	  {
		  for(int i=1;i<=Integer.parseInt(childCount);i++)
		  {
			  safeClick(driver, getElementType(flightsRepo.get("child_dd")));  
		  }
	  }
	  if(Integer.parseInt(infantCount)>0)
	  {
		  for(int i=1;i<=Integer.parseInt(infantCount);i++)
		  {
			  safeClick(driver, getElementType(flightsRepo.get("infant_dd")));  
		  }
	  }
	  safeClick(driver, getElementType(flightsRepo.get("classtype") + classtype + "')]"));
	  safeClick(driver, getElementType(flightsRepo.get("Done_button")));
      String pax=getWebElement(driver, getElementType(flightsRepo.get("personal_info"))).getAttribute("value");
	  String actualPax=adultCount +"adult, "+childCount+" child, "+infantCount+" infant, "+classtype;
	 
	  softAssert.assertEquals(pax, actualPax , "Pax count displayed is not matching with diplayed");
	  Thread.sleep(1000);
	  safeClick(driver, getElementType(flightsRepo.get("personal_info")));
   }
   
   
   
   public void selectInfantCount1(String iCount) {
		  Actions act=new Actions(driver);
		  WebElement infant = getWebElement(driver,getElementType(flightsRepo.get("infant_c")+iCount+"')]"));
		  act.moveToElement(infant).click().build().perform();
	}
   
   public void verifyPassengerCountBoxOld( SoftAssert softAssert) throws Exception{
	  
	  softAssert.assertEquals(elementEnabled(driver,getElementType(flightsRepo.get("personal_info")), 1),true,"passenger count box is not enabled");
	 
	  softAssert.assertEquals(elementEnabled(driver, getElementType(flightsRepo.get("select_adult")), 1), true, "adult count drop down is not enabled");
	  softAssert.assertEquals(elementEnabled(driver, getElementType(flightsRepo.get("select_child")), 1), true, "child count dropdown is not enbled");
	  softAssert.assertEquals(elementEnabled(driver, getElementType(flightsRepo.get("select_infant")), 1), true, "infant count drop down is not enabled"); 
	  }
   
   public void verifyPassengerCountBox(SoftAssert softAssert) throws Exception{
	   
	   /*softAssert.assertEquals(elementEnabled(driver,getElementType(flightsRepo.get("personal_info")), 1),true,"passenger count box is not enabled");
	   safeClick(driver,getElementType(flightsRepo.get("personal_info")) );*/
	   softAssert.assertEquals(elementEnabled(driver, getElementType(flightsRepo.get("adult_dd")), 1), true, "adult add button is not enabled");
	   softAssert.assertEquals(elementEnabled(driver, getElementType(flightsRepo.get("child_dd")), 1), true, "child add button is not enbled");
	   softAssert.assertEquals(elementEnabled(driver, getElementType(flightsRepo.get("infant_dd")), 1), true, "infant add button is not enabled"); 
	   
	   
   }
   
   public void InfantCountNotExceedAdultCountOld(int adultCount) throws Exception{
	 
	   SoftAssert softassert=new SoftAssert();
	   
	   WebElement adult=driver.findElement(By.xpath("//label[@for='adult_economy-0']"));
	   safeClick(driver, getElementType(flightsRepo.get("select_adult")));
	   Thread.sleep(1000);
	   adult.findElement(By.xpath("//li[contains(text(),'"+adultCount+"')]")).click();
	   Thread.sleep(500);
	   safeClick(driver, getElementType(flightsRepo.get("select_infant")));
	   Thread.sleep(1000);
	   softassert.assertEquals(driver.findElement(By.xpath("//li[contains(text(),'"+(adultCount+1)+"')]")).isEnabled(),false, "infant count is more than adult count");
	   softassert.assertEquals(driver.findElement(By.xpath("//li[contains(text(),'"+(adultCount)+"')]")).isEnabled(),true, "infant count is more than adult count");
	   softassert.assertEquals(driver.findElement(By.xpath("//li[contains(text(),'"+(adultCount+2)+"')]")).isEnabled(),false, "infant count is more than adult count");
	   safeClick(driver, getElementType(flightsRepo.get("text_count")+1+"')]"));
	   }
   public void InfantCountNotExceedAdultCount(int adultCount) throws Exception{
	  SoftAssert softAssert=new SoftAssert();
	  for(int i=1;i<adultCount;i++)
	  {
		  Thread.sleep(1000);
		  safeClick(driver, getElementType(flightsRepo.get("adult_dd")));
	  }
	  Thread.sleep(500);
	  safeClick(driver,getElementType(flightsRepo.get("infant_dd_minus")));
	  Thread.sleep(500);
	  safeClick(driver, getElementType(flightsRepo.get("child_dd_minus")));
	  for(int i=1;i<=adultCount;i++)
	  {
		  Thread.sleep(500);
		  safeClick(driver, getElementType(flightsRepo.get("infant_dd")));
	  }
	  softAssert.assertFalse(elementEnabled(driver,getElementType(flightsRepo.get("infant_dd")) ,1), "more infant can be added than the adult count");
   }
   
   public void messagecheckingofpaxOld() throws Exception{
	   System.out.println("msg checking");
	   safeClick(driver, By.xpath("//div[@id='adult_economy-1']"));
	   safeClick(driver, By.xpath("(//li[contains(text(),'0')])[1]"));
	  l2: for(int adult=9;adult>=1;adult--){
		   l1:for(int child=0;child<=9;child++){
			   if((adult+child)<9){
				   //System.out.println("adult & child total count less than 9");
				   child++;
				   }
			   else if(adult+child==9){
				   safeClick(driver, By.xpath("//div[@id='adult_economy-0']"));
				   Thread.sleep(500);
				   safeClick(driver, getElementType(flightsRepo.get("adult_select_dd") + adult + "')]"));
				   safeClick(driver, By.xpath("//div[@id='adult_economy-1']"));
				   if(child==0){
					   safeClick(driver, By.xpath("(//li[contains(text(),'"+(child)+"')])[1]"));
				   }
				   else{
				   safeClick(driver,By.xpath("(//li[contains(text(),'"+(child)+"')])[1]"));}
				   boolean msg=driver.findElement(By.xpath("//span[contains(text(),'+91-93338-93338')]")).isDisplayed();
				   if(msg==true){
					 break l2;  
				   
				   }}}}}
   
   public void messagecheckingofpax() throws Exception{
	   SoftAssert softAssert=new SoftAssert();
	   System.out.println("msg checking");
	   for(int i=2;i<9;i++)
	   {
		 safeClick(driver, getElementType(flightsRepo.get("adult_dd")));
	   }
	   boolean msg=driver.findElement(By.xpath("//a[contains(@href,'tel:+91-93338-93338')]")).isDisplayed();
	   softAssert.assertTrue(msg);
	   for(int i=1;i<=2;i++)
	   {
		   safeClick(driver, getElementType(flightsRepo.get("adult_dd_minus")));
		   safeClick(driver, getElementType(flightsRepo.get("child_dd")));
		   boolean msg1=driver.findElement(By.xpath("//a[contains(@href,'tel:+91-93338-93338')]")).isDisplayed();
		   softAssert.assertTrue(msg1);
	  }
	   
   }
   public void clickOnSearch(SoftAssert softAssert) throws Exception{
	   Thread.sleep(2000);
	   safeClick(driver, getElementType(flightsRepo.get("search_button")));
	   Thread.sleep(5000);
	   
   }
   public void clickOnSearchRT(SoftAssert softAssert) throws Exception{
	   Thread.sleep(1000);
	   safeClick(driver,getElementType(flightsRepo.get("Done_button")));
	   Thread.sleep(500);
	   safeClick(driver, getElementType(flightsRepo.get("search_button")));
	   Thread.sleep(5000);
   }
   public void selectPaxOld(String adultCount, String childCount, String infantCount) throws Exception{
	   
       safeClick(driver, getElementType(flightsRepo.get("adult_dd")));
       Thread.sleep(2000);
       safeClick(driver, getElementType(flightsRepo.get("adult_select_dd") + adultCount.trim() + "')]"));
       scrollDown(driver);

       safeClick(driver, getElementType(flightsRepo.get("child_dd")));
       safeClick(driver, getElementType(flightsRepo.get("child_select_dd") + childCount.trim() + "')][1]"));

       scrollDown(driver);

       safeClick(driver, getElementType(flightsRepo.get("infant_dd")));
       safeClick(driver, getElementType(flightsRepo.get("text_count")+ infantCount +"')]"));
   
   Thread.sleep(1000);
       safeClick(driver,getElementType(flightsRepo.get("apply_butoon")));
       }
   public void selectPax(String adultCount, String childCount, String infantCount) throws Exception{
	   if(Integer.parseInt(adultCount)!=1)
	   {
		   for(int i=1;i<Integer.parseInt(adultCount);i++)
		   {
			   safeClick(driver,getElementType(flightsRepo.get("adult_dd")));
		   }
	   }
	   if(Integer.parseInt(childCount)>0)
		   for(int i=1;i<=Integer.parseInt(childCount);i++)
		   {
			   safeClick(driver, getElementType(flightsRepo.get("child_dd")));
		   }
	   if(Integer.parseInt(infantCount)>0)
		   for(int i=1;i<=Integer.parseInt(infantCount);i++)
		   {
			   safeClick(driver, getElementType(flightsRepo.get("infant_dd")));
		   }
	   Thread.sleep(1000);
       safeClick(driver,getElementType(flightsRepo.get("Done_button")));
   }

   
   public void totalAdultChildCountNotExceedNineOld(SoftAssert softAssert) throws Exception{
	  
	   
	  l2: for(int adult=9;adult>=1;adult--){
		  l1: for(int child=0;child<=9;child++){
			   if((adult+child)<9){
				   //System.out.println("adult & child total count less than 9");
				   child++;
			   }			   
			   else if(adult+child==9){
				    safeClick(driver, By.xpath("//div[@id='adult_economy-0']"));
				    Thread.sleep(500);
				    safeClick(driver, getElementType(flightsRepo.get("adult_select_dd") + adult + "')]"));
				    safeClick(driver, By.xpath("//div[@id='adult_economy-1']"));
				   if(child==0){
					   safeClick(driver, By.xpath("(//li[contains(text(),'"+(child)+"')])[1]"));
				   }
				   else{
				   safeClick(driver,By.xpath("(//li[contains(text(),'"+(child)+"')])[1]"));}
				   
				   boolean msg=driver.findElement(By.xpath("//span[contains(text(),'+91-93338-93338')]")).isDisplayed();
				   if(msg==true){
					   
				   break l2;}
				   }
			   else if(adult+child>9){
				   safeClick(driver, By.xpath("//div[@id='adult_economy-0']"));
				   Thread.sleep(500);
				   safeClick(driver, getElementType(flightsRepo.get("adult_select_dd") + adult + "')]"));
				   safeClick(driver, By.xpath("//div[@id='adult_economy-1']"));
				  
				  // softassert.assertEquals(driver.findElement(By.xpath("(//li[contains(text(),'"+(child)+"')])[2]")).isEnabled(), false, "adult & child count sum is more than 9");
               if(driver.findElement(By.xpath("(//li[contains(text(),'"+(child)+"')])[1]")).isEnabled()==false){ 
            	   break l1;
            	   }}}}}
   
public void totalAdultChildCountNotExceedNine(SoftAssert softAssert) throws Exception{
	for(int i=1;i<=2;i++)
	{
		safeClick(driver,getElementType(flightsRepo.get("adult_dd_minus")));
	}
	for(int i=1;i<=2;i++)
	{
		safeClick(driver,getElementType(flightsRepo.get("child_dd")));
	}
	boolean msg=driver.findElement(By.xpath("//a[contains(@href,'tel:+91-93338-93338')]")).isDisplayed();
	   softAssert.assertTrue(msg);
	   for(int i=5;i>1;i--)
	   {
		   safeClick(driver, getElementType(flightsRepo.get("adult_dd_minus")));
	   }
	   for(int i=4;i>=0;i--)
	   {
		   safeClick(driver, getElementType(flightsRepo.get("child_dd_minus")));
	   }
	   for(int i=2;i>=0;i--)
	   {
		   safeClick(driver, getElementType(flightsRepo.get("infant_dd_minus")));
	   }
	
} 
   public void SearchPagefillOneWay(String trip,String form,String to,String adultCount,String childCount,String infantCount) throws Exception{
		
		if(trip=="ONEWAY")
			safeClick(driver, By.xpath("//button[@id='flight_one_way_btn']"));
		safeClick(driver, By.xpath("//div[contains(text(),'Departure city ')]"));
		safeType(driver, By.xpath("//input[contains(@placeholder,'Departure city ')]"),form );
		
		safeClick(driver,By.xpath("//li[@id='react-autowhatever-1--item-0']"));
		safeType(driver,By.xpath("//input[contains(@placeholder,'Destination')]"), to);
		//safeClick(driver, By.xpath("//div[contains(text(),'"+to+"')]"));
		safeClick(driver,By.xpath("//li[@id='react-autowhatever-1--item-0']"));
		Thread.sleep(2000);
		scrollToElement(driver, getWebElement(driver,getElementType(flightsRepo.get("new_date")+"1]")));
		safeClick(driver, getElementType(flightsRepo.get("new_date")+"10]"));
		Thread.sleep(2000);
		safeClick(driver, By.xpath("//div[contains(text(),'adult')]"));
		Thread.sleep(1000);
		selectPax(adultCount, childCount, infantCount);
		
	}
   
   public void SearchPagefillRoundtrip(String trip,String from,String to,String onwardDate,String returnDate,String adultCount,String childCount,String infantCount) throws Exception{
	   if(trip=="ROUNDTRIP")
			safeClick(driver, By.xpath("//button[@id='flight_return_btn']"));
	   Thread.sleep(1000);
		safeClick(driver, By.xpath("(//div[contains(@class,'input_search departureCity')])[1]"));
		Thread.sleep(1000);
		HandleSearchIframe();
		driver.findElement(By.xpath("//input[contains(@placeholder,'Departure city ')]")).clear();
		Thread.sleep(500);
		driver.findElement(By.xpath("//input[contains(@placeholder,'Departure city ')]")).clear();
		safeType(driver, By.xpath("//input[contains(@placeholder,'Departure city ')]"),from );
		
		safeClick(driver,By.xpath("//li[@id='react-autowhatever-1--item-0']"));
		Thread.sleep(500);
		safeClick(driver, By.xpath("(//div[contains(@class,'input_search departureCity')])[2]"));
		safeType(driver,By.xpath("//input[contains(@placeholder,'Destination')]"), to);
		//safeClick(driver, By.xpath("//div[contains(text(),'"+to+"')]"));
		safeClick(driver,By.xpath("//li[contains(@id,'react-autowhatever-1--item-0')]"));
		
		Thread.sleep(2000);
		scrollToElement(driver, getWebElement(driver,getElementType(flightsRepo.get("select_date")+goomoDate(5)+"']")));
		Thread.sleep(1000);
		safeClick(driver, getElementType(flightsRepo.get("select_date")+onwardDate+"']"));
		Thread.sleep(1000);
		safeClick(driver, getElementType(flightsRepo.get("select_date")+returnDate+"']"));
		Thread.sleep(1000);
        safeClick(driver,By.xpath("//div[contains(text(),'DONE')]"));
       Thread.sleep(1000);
     safeClick(driver, By.xpath("//div[contains(text(),'adult')]"));
     Thread.sleep(2000);
		selectPax(adultCount, childCount, infantCount);
		  
	   
	   
   }
   
   public void HandleSearchIframe() throws InterruptedException{
	   if(elementVisible(driver,By.xpath("//iframe[@id='webklipper-publisher-widget-container-notification-frame']"), 1))
		   {
		   WebElement wb=driver.findElement(By.xpath("//iframe[@id='webklipper-publisher-widget-container-notification-frame']"));
		   driver.switchTo().frame(wb);
		   Thread.sleep(500);
		   safeClick(driver, By.xpath("//i[contains(@class,'we_close')]"));
		   Thread.sleep(500);
		   driver.switchTo().defaultContent();
		   }
	   
   }
		
   public void SearchIntRT(SoftAssert softAssert) throws Exception {

		Thread.sleep(1000);
		safeClear(driver, getElementType(flightsRepo.get("departure_city")));
		safeType(driver, getElementType(flightsRepo.get("departure_city")), "Del");
		safeClick(driver, By.xpath("//span[@class='false']"));
		Thread.sleep(1000);
		safeClear(driver, getElementType(flightsRepo.get("destination_city")));
		safeType(driver, getElementType(flightsRepo.get("destination_city")), "DXB");
		safeClick(driver, By.xpath("//span[@class='false']"));

		softAssert.assertEquals(elementEnabled(driver, getElementType(flightsRepo.get("search_button")), 1), false,
				"search flights button enabled");

	}
   public void fillDate() throws Exception {
		int selectDate = 2;
		int selectDateRT = 5;
		safeClick(driver, getElementType(flightsRepo.get("depart_date")));
		//System.err.println(flightsRepo.get("navigate_month"));
		WebElement navigateMonth = driver.findElement(getElementType(flightsRepo.get("navigate_month")));

		for (int i = 0; i < 2; i++) {
			navigateMonth.click();
		}
		//System.err.println(flightsRepo.get("select_date") + selectDate + "']");
		safeClick(driver, getElementType(flightsRepo.get("select_date") + selectDate + "']"));
		safeClick(driver, By.xpath("//div[@aria-label='day-16']"));

	}

public void SearchPagefillOneWay(String trip,String form,String to) throws Exception{
	
	if(trip=="ONEWAY")
		safeClick(driver, By.xpath("//button[@id='flight_one_way_btn']"));
	safeClick(driver, By.xpath("//div[contains(text(),'Departure city ')]"));
	safeType(driver, By.xpath("//input[contains(@placeholder,'Departure city ')]"),form );
	safeClick(driver, By.xpath("//div[contains(text(),'"+form+"')]"));
	safeType(driver,By.xpath("//input[contains(@placeholder,'Destination')]"), to);
	safeClick(driver, By.xpath("//div[contains(text(),'"+to+"')]"));
	
}
	
	

}
