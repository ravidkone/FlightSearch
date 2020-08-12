package web.goomo.commonutils;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;

import api.commonUtils.ApiCommon;
import io.restassured.response.Response;


public class WebFlightConnector extends WebCommonUtils{
	
	  
    
	public WebDriver driver;
	String osName;
	
	public static Logger logger = Logger.getLogger(ApiCommon.class);
	public void configLog(){
		 PropertyConfigurator.configure(System.getProperty("user.dir")+"//config//log4j.properties");
	}
	
	
	
	@BeforeClass
	@Parameters("browser")
	public void launchBrowser(@Optional String browser) throws MalformedURLException {
		retrieveProperties();
		configLog();
	   
		osName = System.getProperty("os.name");
		System.out.println(environment);
		System.out.println(common.value("build_number"));
		extent = getExtent();
		browserType = browser;
		if (browserType == null) {
			if (osName.equalsIgnoreCase("linux"))
				System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "//resources//drivers//chromedriver_linux");
			else if (osName.equalsIgnoreCase("windows") ||osName.equalsIgnoreCase("Windows 10"))
				System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "//resources//drivers//chromedriver.exe");
		}
		else 
		{
			
			if (osName.equalsIgnoreCase("linux"))
			{
			    if(browserType.equalsIgnoreCase("chrome")){
			    	if(runOnGrid){
			    		System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "//resources//drivers//chromedriver_linux");
			    	}
			    	else{	
				    System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "//resources//drivers//chromedriver_linux");
			    	}
			    	}
			    else if(browserType.equalsIgnoreCase("firefox")){
			    	System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir") + "//resources//drivers//geckodriver_linux");	
			    }
			    else{
			    	 throw new Error("Sorry we don't support IE...");
			    }
			}
			else if (osName.equalsIgnoreCase("windows")||osName.equalsIgnoreCase("Windows 10")){
				if(browserType.equalsIgnoreCase("chrome")){
					System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "//resources//drivers//chromedriver.exe");
				    }
				    else if(browserType.equalsIgnoreCase("firefox")){
				    	System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir") + "//resources//drivers//geckodriver.exe");	
				    }
				    else{
				    	 throw new Error("Sorry we don't support IE...");
				    }
			}
			else if(osName.equalsIgnoreCase("mac")){
				if(browserType.equalsIgnoreCase("chrome")){
					System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "//resources//drivers//chromedriver.exe");
				    }
				    else if(browserType.equalsIgnoreCase("firefox")){
				    	System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir") + "//resources//drivers//geckodriver.exe");	
				    }
				    else{
				    	 throw new Error("Sorry we don't support IE...");
				    }
			}
		}   
		     if(runOnGrid){
		    	 DesiredCapabilities cap = new DesiredCapabilities();
		    	 if(browser.equalsIgnoreCase("chrome")){
		    		 cap.setBrowserName("chrome");
		    		 ChromeOptions options = new ChromeOptions();
		    	     options.addArguments("--start-maximized");
		    	     cap.setCapability(ChromeOptions.CAPABILITY, options);
		    		 
		    	 }
		    	 else if(browser.equalsIgnoreCase("firefox"))
		    		 cap.setBrowserName("firefox");
		    	 else if(browser.equalsIgnoreCase("safari")) 
		    		 cap.setBrowserName("safari");
		    	 else
		    		 throw new Error("Switch to chrome or firefox");
		    	 System.out.println("RUNNING ON GRID...");
		    	 System.out.println("TO RUN LOCALLY DISABLE GRID FLAG IN PROPERTIES....");
		    	 cap.setPlatform(Platform.LINUX);
		    	 driver = new RemoteWebDriver(new URL("http://"+webGridIp+":4444/wd/hub"),cap);
		    	 
		     }else{
		     driver = getDriver(browserType);
		     }
		    

	}

	
	public void debug(boolean flag, String Message) {
		if (flag) {
			System.out.println(Message);
		} else {
			logger.info(Message);
		}

	}
	
	public WebDriver getDriver(String browserType) {

		if(browserType==null)
			return new ChromeDriver();
		else {
			if (browserType.equalsIgnoreCase("chrome"))
				return new ChromeDriver();
			else if (browserType.equalsIgnoreCase("firefox")) {
				/*ProfilesIni profile = new ProfilesIni();
				FirefoxProfile testprofile = profile.getProfile("default");
				testprofile.setAcceptUntrustedCertificates(true);
				testprofile.setAssumeUntrustedCertificateIssuer(true);*/
				return new FirefoxDriver();

				//return driver;
			} else if (browserType.equalsIgnoreCase("safari"))
				return new SafariDriver();

			return new InternetExplorerDriver();
		}


	}
	public void  getURL(){
        if(environment.equalsIgnoreCase("staging"))
             driver.get(stagingURL);
        else if(environment.equalsIgnoreCase("beta"))
             driver.get(betaURL);
        else if(environment.equalsIgnoreCase("dev"))
        	 driver.get(devURL);
        else if(environment.equalsIgnoreCase("integration"))
        	 driver.get(intURL);
        else if(environment.equalsIgnoreCase("production"))
        	driver.get(productionURL);
       // driver.manage().window().maximize();
   }
	
	public void getURLruleEngineDrools(){
		  if(environment.equalsIgnoreCase("staging"))
			   driver.get(droolsURLStaging);
		  else if(environment.equalsIgnoreCase("dev") || environment.equalsIgnoreCase("integration"))
			  driver.get(droolsURLDev);
	
		  
	}
	
	public void  getURLHolidays(){
        if(environment.equalsIgnoreCase("staging")){
             driver.get(stagingURL+"/holidays");
        }else if(environment.equalsIgnoreCase("beta")) {
             driver.get(betaURL+"/holidays");
        }else {
        	 driver.get(productionURL);
        }
        driver.manage().window().maximize();
	}
	
	public void getURLbackOffice(WebDriver driver) {
		 if(environment.equalsIgnoreCase("staging")){
             driver.get(backofficeURLStaging);
        }else if(environment.equalsIgnoreCase("dev")) {
             driver.get(backofficeURLDev);
        }else if(environment.equalsIgnoreCase("integration")) {
            driver.get(backofficeURLI);
       }
        else {
        	 driver.get(productionURL);
        }
        driver.manage().window().maximize();
    }
	
	public void getURLagentBookingHotel(WebDriver driver) {
		
		 if(environment.equalsIgnoreCase("staging")){
            driver.get(hotelagentbookingURLStaging);
		 }
       driver.manage().window().maximize();
   }
	
	
	public void getURLbackOffice(WebDriver driver,String endPoint) {
		 if(environment.equalsIgnoreCase("staging")){
            driver.get(backofficeURLStaging+endPoint);
       }else if(environment.equalsIgnoreCase("dev")) {
            driver.get(backofficeURLDev+endPoint);
       }else if(environment.equalsIgnoreCase("integration")) {
           driver.get(backofficeURLI+endPoint);
      }
       else {
       	 driver.get(productionURL);
       }
       driver.manage().window().maximize();
   }
	
	public String getURLAirports() {
		 if(environment.equalsIgnoreCase("staging"))
             return airportJsonStaging;
       else if(environment.equalsIgnoreCase("dev")) 
             return airportJsonDev;
       else if(environment.equalsIgnoreCase("integration")) 
             return airportJsonI;
       else 
       	     return "";
      
   }
	
	public void getURLbackOffice() {
        if(environment.equalsIgnoreCase("staging"))
            driver.get(backofficeURLStaging);
        else if ("dev".equalsIgnoreCase(environment)) 
        	driver.get(backofficeURLDev);
        else if ("integration".equalsIgnoreCase(environment)) 
        	driver.get(backofficeURLI);
    
    }
	
	public void closeSession(WebDriver driver){
		  driver.close();
		  driver.quit();
	}
	
	public void  getURLtradeFairs(){
        if(environment.equalsIgnoreCase("staging")){
        	driver.get(stagingURL+"/tradefairs");
        }else if(environment.equalsIgnoreCase("beta")){
        	driver.get(betaURL+"/tradefairs");}    
        else{
             driver.get(productionURL+"/tradefairs");
        }
        	driver.manage().window().maximize();
        }
	
	public void getOWURL(String from,String to,String travelDate,String classType,String adultCount,String childCount,String infantCount){
        String tDate   =travelDate.replace(",","%2C").replace(" ","%20");  
        if(environment.equalsIgnoreCase("staging")){
        	driver.get(stagingURL);
        	maximizeWindow(driver);
            driver.get(stagingURL+"/flights/search/one_way?search_type=one_way&origin="+from+"&destination="+to+"&travel_date="+tDate+"&class_type="+classType.toUpperCase()+"&adult_count="
        		+adultCount+"&child_count="+childCount+"&residentof_india=true&infant_count="+infantCount+"&originAndDestinationSwapped=false&activeIndexMulticity=0");

          System.out.println(stagingURL+flightsOWSearch+from+"&destination="+to+"&travel_date="+tDate+"&class_type="+classType.toUpperCase()+"&adult_count="
          		+adultCount+"&child_count="+childCount+"&residentof_india=true&infant_count="+infantCount);
          //driver.findElement(By.xpath("////button[contains(text(),'SEARCH FLIGHTS')]")).click();
    
        }else if(environment.equalsIgnoreCase("beta")){
        	driver.get(betaURL);
              System.out.println(betaURL+flightsOWSearch+from+"&destination="+to+"&travel_date="+tDate+"&class_type="+classType.toUpperCase()+"&adult_count="
              		+adultCount+"&child_count="+childCount+"&residentof_india=true&infant_count="+infantCount);
             driver.get(betaURL+flightsOWSearch+from+"&destination="+to+"&travel_date="+tDate+"&class_type="+classType.toUpperCase()+"&adult_count="
            		+ ""+adultCount+"&child_count="+childCount+"&residentof_india=true&infant_count="+infantCount);}
        else if(environment.equalsIgnoreCase("dev")){
        	driver.get(devURL);
              System.out.println(devURL+flightsOWSearch+from+"&destination="+to+"&travel_date="+tDate+"&class_type="+classType.toUpperCase()+"&adult_count="
              		+adultCount+"&child_count="+childCount+"&residentof_india=true&infant_count="+infantCount);
             driver.get(devURL+flightsOWSearch+from+"&destination="+to+"&travel_date="+tDate+"&class_type="+classType.toUpperCase()+"&adult_count="
            		+adultCount+"&child_count="+childCount+"&residentof_india=true&infant_count="+infantCount);}
        else if(environment.equalsIgnoreCase("integration")){
        	driver.get(intURL);
              System.out.println(intURL+flightsOWSearch+from+"&destination="+to+"&travel_date="+tDate+"&class_type="+classType.toUpperCase()+"&adult_count="
              		+adultCount+"&child_count="+childCount+"&residentof_india=true&infant_count="+infantCount);
             driver.get(intURL+flightsOWSearch+from+"&destination="+to+"&travel_date="+tDate+"&class_type="+classType.toUpperCase()+"&adult_count="
            		+adultCount+"&child_count="+childCount+"&residentof_india=true&infant_count="+infantCount);}
        else{
        	  driver.get(productionURL);
              System.out.println(productionURL+flightsOWSearch+from+"&destination="+to+"&travel_date="+tDate+"&class_type="+classType.toUpperCase()+"&adult_count="
              		+adultCount+"&child_count="+childCount+"&residentof_india=true&infant_count="+infantCount);
             driver.get(productionURL+flightsOWSearch+from+"&destination="+to+"&travel_date="+tDate+"&class_type="+classType.toUpperCase()+"&adult_count="
            		+adultCount+"&child_count="+childCount+"&residentof_india=true&infant_count="+infantCount);	}
       
       // driver.manage().window().maximize();
	}
	
	public void getRTURL(String from,String to,String departuredate,String returndate,String classType,String adultCount,String childCount,String infantCount){
        String tDate   =departuredate.replace(",","%2C").replace(" ","%20");
        String t1Date  =returndate.replace(",","%2C").replace(" ","%20");
        if(environment.equalsIgnoreCase("staging")){
        	driver.get(stagingURL);
           driver.get(stagingURL+"/flights/search/roundtrip?search_type=roundtrip&origin="+from+"&destination="+to+"&travel_date="+tDate+"&return_date="+t1Date+"&class_type="+classType.toUpperCase()+"&adult_count="
        	+adultCount+"&child_count="+childCount+"&residentof_india=true&infant_count="+infantCount+"&originAndDestinationSwapped=false");
          System.out.println(stagingURL+flightsIntlRTSearch+from+"&destination="+to+"&travel_date="+tDate+"&return_date="+t1Date+"&class_type="+classType.toUpperCase()+"&adult_count="
          		+adultCount+"&child_count="+childCount+"&residentof_india=true&infant_count="+infantCount+"&originAndDestinationSwapped=false");
        }else if(environment.equalsIgnoreCase("beta")){
        	driver.get(betaURL);
              System.out.println(betaURL+flightsIntlRTSearch+from+"&destination="+to+"&travel_date="+tDate+"&return_date="+t1Date+"&class_type="+classType.toUpperCase()+"&adult_count="
              		+adultCount+"&child_count="+childCount+"&residentof_india=true&infant_count="+infantCount);
             driver.get(betaURL+flightsIntlRTSearch+from+"&destination="+to+"&travel_date="+tDate+"&return_date="+t1Date+"&class_type="+classType.toUpperCase()+"&adult_count="
            		+adultCount+"&child_count="+childCount+"&residentof_india=true&infant_count="+infantCount);
        }
        else if(environment.equalsIgnoreCase("dev")){
        	driver.get(devURL);
              System.out.println(devURL+flightsIntlRTSearch+from+"&destination="+to+"&travel_date="+tDate+"&class_type="+classType.toUpperCase()+"&adult_count="
              		+adultCount+"&child_count="+childCount+"&residentof_india=true&infant_count="+infantCount);
             driver.get(devURL+flightsIntlRTSearch+from+"&destination="+to+"&travel_date="+tDate+"&class_type="+classType.toUpperCase()+"&adult_count="
            	+adultCount+"&child_count="+childCount+"&residentof_india=true&infant_count="+infantCount);}
        else if(environment.equalsIgnoreCase("integration")){
        	driver.get(intURL);
              System.out.println(intURL+flightsIntlRTSearch+from+"&destination="+to+"&travel_date="+tDate+"&class_type="+classType.toUpperCase()+"&adult_count="
              		+adultCount+"&child_count="+childCount+"&residentof_india=true&infant_count="+infantCount);
             driver.get(intURL+flightsIntlRTSearch+from+"&destination="+to+"&travel_date="+tDate+"&class_type="+classType.toUpperCase()+"&adult_count="
            		+adultCount+"&child_count="+childCount+"&residentof_india=true&infant_count="+infantCount);}
        else{
        	driver.get(productionURL);
              System.out.println(productionURL+flightsIntlRTSearch+from+"&destination="+to+"&travel_date="+tDate+"&return_date="+t1Date+"&class_type="+classType.toUpperCase()+"&adult_count="
              		+adultCount+"&child_count="+childCount+"&residentof_india=true&infant_count="+infantCount);
             driver.get(productionURL+flightsIntlRTSearch+from+"&destination="+to+"&travel_date="+tDate+"&return_date="+t1Date+"&class_type="+classType.toUpperCase()+"&adult_count="
            		+adultCount+"&child_count="+childCount+"&residentof_india=true&infant_count="+infantCount);       	
        }
         maximizeWindow(driver);
	}
	
	public void getOWURL(WebDriver driver,String from,String to,String travelDate,String classType,String adultCount,String childCount,String infantCount){
        String tDate   =travelDate.replace(",","%2C").replace(" ","%20");  
        if(environment.equalsIgnoreCase("staging")){
        	driver.get(stagingURL);
           driver.get(stagingURL+"/flights/search/one_way?search_type=one_way&origin="+from+"&destination="+to+"&travel_date="+tDate+"&class_type="+classType.toUpperCase()+"&adult_count="
        		+adultCount+"&child_count="+childCount+"&residentof_india=true&infant_count="+infantCount);
          System.out.println(stagingURL+flightsOWSearch+from+"&destination="+to+"&travel_date="+tDate+"&class_type="+classType.toUpperCase()+"&adult_count="
          		+adultCount+"&child_count="+childCount+"&residentof_india=true&infant_count="+infantCount);
          //driver.findElement(By.xpath("////button[contains(text(),'SEARCH FLIGHTS')]")).click();
    
        }else if(environment.equalsIgnoreCase("beta")){
        	driver.get(betaURL);
              System.out.println(betaURL+flightsOWSearch+from+"&destination="+to+"&travel_date="+tDate+"&class_type="+classType.toUpperCase()+"&adult_count="
              		+adultCount+"&child_count="+childCount+"&residentof_india=true&infant_count="+infantCount);
             driver.get(betaURL+flightsOWSearch+from+"&destination="+to+"&travel_date="+tDate+"&class_type="+classType.toUpperCase()+"&adult_count="
            		+adultCount+"&child_count="+childCount+"&residentof_india=true&infant_count="+infantCount);}
        else if(environment.equalsIgnoreCase("dev")){
        	driver.get(devURL);
              System.out.println(devURL+flightsOWSearch+from+"&destination="+to+"&travel_date="+tDate+"&class_type="+classType.toUpperCase()+"&adult_count="
              		+adultCount+"&child_count="+childCount+"&residentof_india=true&infant_count="+infantCount);
             driver.get(devURL+flightsOWSearch+from+"&destination="+to+"&travel_date="+tDate+"&class_type="+classType.toUpperCase()+"&adult_count="
            		+adultCount+"&child_count="+childCount+"&residentof_india=true&infant_count="+infantCount);}
        else if(environment.equalsIgnoreCase("integration")){
        	driver.get(intURL);
              System.out.println(intURL+flightsOWSearch+from+"&destination="+to+"&travel_date="+tDate+"&class_type="+classType.toUpperCase()+"&adult_count="
              		+adultCount+"&child_count="+childCount+"&residentof_india=true&infant_count="+infantCount);
             driver.get(intURL+flightsOWSearch+from+"&destination="+to+"&travel_date="+tDate+"&class_type="+classType.toUpperCase()+"&adult_count="
            		+adultCount+"&child_count="+childCount+"&residentof_india=true&infant_count="+infantCount);}
        else{
        	  driver.get(productionURL);
              System.out.println(productionURL+flightsOWSearch+from+"&destination="+to+"&travel_date="+tDate+"&class_type="+classType.toUpperCase()+"&adult_count="
              		+adultCount+"&child_count="+childCount+"&residentof_india=true&infant_count="+infantCount);
             driver.get(productionURL+flightsOWSearch+from+"&destination="+to+"&travel_date="+tDate+"&class_type="+classType.toUpperCase()+"&adult_count="
            		+adultCount+"&child_count="+childCount+"&residentof_india=true&infant_count="+infantCount);	}
       
        driver.manage().window().maximize();
	}
	
	public void getRTURL(WebDriver driver,String from,String to,String departuredate,String returndate,String classType,String adultCount,String childCount,String infantCount){
        String tDate   =departuredate.replace(",","%2C").replace(" ","%20");
        String t1Date  =returndate.replace(",","%2C").replace(" ","%20");
        if(environment.equalsIgnoreCase("staging")){
        	driver.get(stagingURL);
           driver.get(stagingURL+flightsIntlRTSearch+from+"&destination="+to+"&travel_date="+tDate+"&return_date="+t1Date+"&class_type="+classType.toUpperCase()+"&adult_count="
        		+adultCount+"&child_count="+childCount+"&residentof_india=true&infant_count="+infantCount);
          System.out.println(stagingURL+flightsIntlRTSearch+from+"&destination="+to+"&travel_date="+tDate+"&return_date="+t1Date+"&class_type="+classType.toUpperCase()+"&adult_count="
          		+adultCount+"&child_count="+childCount+"&residentof_india=true&infant_count="+infantCount);
        }else if(environment.equalsIgnoreCase("beta")){
        	driver.get(betaURL);
              System.out.println(betaURL+flightsIntlRTSearch+from+"&destination="+to+"&travel_date="+tDate+"&return_date="+t1Date+"&class_type="+classType.toUpperCase()+"&adult_count="
              		+adultCount+"&child_count="+childCount+"&residentof_india=true&infant_count="+infantCount);
             driver.get(betaURL+flightsIntlRTSearch+from+"&destination="+to+"&travel_date="+tDate+"&return_date="+t1Date+"&class_type="+classType.toUpperCase()+"&adult_count="
            		+adultCount+"&child_count="+childCount+"&residentof_india=true&infant_count="+infantCount);
        }
        else if(environment.equalsIgnoreCase("dev")){
        	driver.get(devURL);
              System.out.println(devURL+flightsIntlRTSearch+from+"&destination="+to+"&travel_date="+tDate+"&class_type="+classType.toUpperCase()+"&adult_count="
              		+adultCount+"&child_count="+childCount+"&residentof_india=true&infant_count="+infantCount);
             driver.get(devURL+flightsIntlRTSearch+from+"&destination="+to+"&travel_date="+tDate+"&class_type="+classType.toUpperCase()+"&adult_count="
            		+adultCount+"&child_count="+childCount+"&residentof_india=true&infant_count="+infantCount);}
        else if(environment.equalsIgnoreCase("integration")){
        	driver.get(intURL);
              System.out.println(intURL+flightsIntlRTSearch+from+"&destination="+to+"&travel_date="+tDate+"&class_type="+classType.toUpperCase()+"&adult_count="
              		+adultCount+"&child_count="+childCount+"&residentof_india=true&infant_count="+infantCount);
             driver.get(intURL+flightsIntlRTSearch+from+"&destination="+to+"&travel_date="+tDate+"&class_type="+classType.toUpperCase()+"&adult_count="
            		+adultCount+"&child_count="+childCount+"&residentof_india=true&infant_count="+infantCount);}
        else{
        	driver.get(productionURL);
              System.out.println(productionURL+flightsIntlRTSearch+from+"&destination="+to+"&travel_date="+tDate+"&return_date="+t1Date+"&class_type="+classType.toUpperCase()+"&adult_count="
              		+adultCount+"&child_count="+childCount+"&residentof_india=true&infant_count="+infantCount);
             driver.get(productionURL+flightsIntlRTSearch+from+"&destination="+to+"&travel_date="+tDate+"&return_date="+t1Date+"&class_type="+classType.toUpperCase()+"&adult_count="
            		+adultCount+"&child_count="+childCount+"&residentof_india=true&infant_count="+infantCount);       	
        }
        driver.manage().window().maximize();
	}
	
	public StringBuffer populateFlightsData(WebDriver driver) throws Exception {
		//flight_journey_timing_section
		List<WebElement> flightDetails = getWebElementList(driver,
				By.xpath("//div[contains(@class,'confirm_flight_section')]"));
		System.out.println("size is"+flightDetails.size());
		System.out.println("flight detaills....."+flightDetails);
		StringBuffer sb = new StringBuffer();
		for (int i = 1; i < flightDetails.size(); i++) {
			JSONObject flights = new JSONObject();
			System.out.println("i is"+i+flightDetails.get(i).getText());
			if(!flightDetails.get(i).getText().contains("|")){
			String flightValues[] = flightDetails.get(i).getText().split(" ");
			System.out.println("flight values....."+flightValues);
			System.out.println("AirlineName...."+flightValues[0]);
			flights.accumulate("AirlineName", flightValues[0].trim());
			System.out.println("class..."+flightValues[1]);
			flights.accumulate("Class", flightValues[1].trim());
			System.out.println("airlinecode"+flightValues[2]);
			flights.accumulate("AirlineCode", flightValues[2].trim());
			System.out.println("airlinecode"+flightValues[3]);
			flights.accumulate("DepartDetails", flightValues[3].trim());
			System.out.println("airlinecode"+flightValues[4]);
			flights.accumulate("DepartDate", flightValues[4].trim());
			System.out.println("airlinecode"+flightValues[5]);
			flights.accumulate("DepartAirport", flightValues[5].trim());
			//flights.accumulate("Duration", flightValues[6].trim());
			//flights.accumulate("ArrivalDetails", flightValues[7].trim());
			//flights.accumulate("ArrivalDate", flightValues[8].trim());
			//flights.accumulate("ArrivalAirport", flightValues[9].trim());

			if (i == 1)
				sb.append(flights + ",");
			else if (i != 1 && i < flightDetails.size() - 1)
				sb.append(flights + ",");
			else
				sb.append(flights);
			}

		}

		// sb.insert(0,"\""+pageName+"\":[");
		sb.insert(0, "[");
		sb.insert(sb.length(), "]");
		System.out.println(sb.toString());
		return sb;
	}
	
	
	
	
	public Map<String, String> populatePriceData(WebDriver driver) throws Exception {
	
		
		Map<String, String> fareDetails = new HashMap<String, String>();
		
		 List<WebElement> breakUps = getWebElementList(driver, By.className("flight_seats_wrap"));
		 if(breakUps!=null){
		 for(WebElement bkup:breakUps){
			       try{
			    	      String key = bkup.findElements(By.tagName("div")).get(0).getText();
			    	      String value = bkup.findElements(By.tagName("div")).get(1).getText();
			    	      //System.out.println(key+" "+value);
			    	      if(key.toLowerCase().contains("base fare") ){
			    	    	    if(key.toLowerCase().contains("adult"))
			    	    	    	fareDetails.put("base_fare_adult", value);
			    	    	    else if(key.toLowerCase().contains("child"))
			    	    	    	fareDetails.put("bare_fare_child", value);
			    	    	    else if(key.toLowerCase().contains("infant"))
			    	    	    	fareDetails.put("bare_fare_infant", value);    
			    	      }
			    	      else if(key.toLowerCase().contains("taxes")){
			    	    	       if(key.toLowerCase().contains("adult"))
			    	    	    	   fareDetails.put("taxes_adult", value);
			    	    	       else if(key.toLowerCase().contains("child"))
				    	    	    	fareDetails.put("taxes_child", value);
				    	    	    else if(key.toLowerCase().contains("infant"))
				    	    	    	fareDetails.put("taxes_infant", value);    
			    	      }
			    	      else if(key.toLowerCase().contains("meals") || key.toLowerCase().contains("baggage")){
			    	    	  if(key.toLowerCase().contains("adult"))
		    	    	    	   fareDetails.put("meals_adult", value);
		    	    	       else if(key.toLowerCase().contains("child"))
			    	    	    	fareDetails.put("meals_child", value);
			    	    	    else if(key.toLowerCase().contains("infant"))
			    	    	    	fareDetails.put("meals_infant", value); 
			    	      }
			    	      else if(key.toLowerCase().contains("deal") || key.toLowerCase().contains("discount") || key.toLowerCase().contains("cashback")){
			    	    	    	fareDetails.put("discount", value); 
			    	      }
			    	      else if(key.toLowerCase().contains("convenience")){
			    	    	  fareDetails.put("convenience", value); 
			    	      }
			       }catch(Exception e){
			    	   
			       }
		 }
		 
	 List<WebElement> otherBreakups = getWebElementList(driver,By.xpath("//ul[contains(@class,'base_fare_ul')]"));
	 if(otherBreakups!=null){
		 for(WebElement otherBrkup : otherBreakups) {
		 try{
		            String key= otherBrkup.findElements(By.tagName("li")).get(0).getText();
		            String value = otherBrkup.findElements(By.tagName("li")).get(1).getText();
		            fareDetails.put(key, value);
		 }catch(Exception e){
			 
		 }
		 }
	  }
	
	 String totalAmount = safeGetText(driver,getElementType(flightsRepo.get("total_amount")));
	 fareDetails.put("total",totalAmount);	                  
	}	
				
			
	return fareDetails;	
	}
	
	
	public void assertDataMap(Map<String,Object> flowDetailMap,String page1,String page2) {
		if (flowDetailMap != null && !(flowDetailMap.isEmpty())) {
			printFlowDetailMap(flowDetailMap);
			
			if(flowDetailMap.containsKey(page1) && flowDetailMap.containsKey(page2)){
				Map<String, String> page1Map = (HashMap) flowDetailMap.get(page1);
				Map<String, String> page2Map = (HashMap) flowDetailMap.get(page2);
			 	
				
				if(!page1.contains("confirmation") && !page2.contains("confirmation")){
					     Set<String> page1Keys = page1Map.keySet();
					     for(String key:page1Keys){
					    	  try{
					    		    String page1Value= page1Map.get(key).replaceAll("\u20B9","").replaceAll(",","");
					    		    String page2Value= page2Map.get(key).replaceAll("\u20B9","").replaceAll(",","");
					    		    if(Integer.parseInt(page1Value)==Integer.parseInt(page2Value)){
					    		    	
					    		    }
					    		    else{
					    		      Assert.assertTrue(page1Value+" and "+page2Value+" are not equals ",Integer.parseInt(page1Value)==Integer.parseInt(page2Value));
					    		    }
					    		    
					    	  }catch(Exception e){
					    		    Reporter.log("Payment Data mismatch at"+page1Map+"-"+page2Map);
					    	  }
					     }
					
					
				}	
				else{
					  System.out.println("Validating Confirmation page");
					    Set<String> page1Keys = page1Map.keySet();
					    int calculatedTotal=0;
					    for(String key:page1Keys){
					    	  try{
					    		  String page1Value = page1Map.get(key).replaceAll("\u20B9","").replaceAll(",","");
					    		  if(!key.contains("discount")||!key.contains("deal")||!key.contains("cashback")){
					    			  calculatedTotal=calculatedTotal+Integer.parseInt(page1Value);
					    		  }
					    		  else{
					    			  calculatedTotal=calculatedTotal-Integer.parseInt(page1Value);
					    		  }
					    		  
					    		  
					    	  }catch(Exception e){
					    		  
					    	  }
					    }
					    
				  Assert.assertTrue("Total Mismatch at "+page1+"-Total ="+page1Map.get("total")+"|"+page2+"-Total ="+page2Map.get("total"), 
						  calculatedTotal==Integer.parseInt(page2Map.get("total")));	    
					  
		
				}
				
			}
			else{
				 System.out.println("Data not available in one of the map for"+page1+" or "+page2);
				 Reporter.log("Data not available in one of the map for"+page1+" or "+page2);
			}
			
		}
	}
	
	public void assertBackOfficeData(Map<String,Object> flowDetailMap,String backOfficePrice){
		if (flowDetailMap != null && !(flowDetailMap.isEmpty())) {
			if (flowDetailMap.containsKey("Confirmation Page")) {
				SoftAssert softAssert = new SoftAssert();
				Map<String, String> page1Map = (HashMap) flowDetailMap.get("Confirmation Page");
				Set<String> page1Keys = page1Map.keySet();
				
				for(String key:page1Keys){
			    	  try{
			    		    if(key.equalsIgnoreCase("total")){
			    		       String page1Value= page1Map.get(key).replaceAll("\u20B9","").replaceAll(",","");
			    		       System.out.println(page1Value+" "+backOfficePrice);
			    		       softAssert.assertEquals(page1Value.trim(), backOfficePrice.trim(),"Final price and backoffice price doesn't match");
			    		       softAssert.assertAll();
			    		      
			    		    }
			    	  }
			    	  catch(Exception e){
			    		  
			    	  }
			}
			}
		}
	}
	
	public String Paymentmode(){
		String paymentType="";
		if(Payment_mode.equalsIgnoreCase("pes"))
			paymentType="pes";
		else if(Payment_mode.equalsIgnoreCase("ccaveneu"))
			paymentType="ccaveneu";
		else
			paymentType="razorpay ";
			
		return paymentType;	
	}
	
	public void assertFlightData(JSONObject flightDetails, String page1Name, String page2Name,ExtentTest test) throws Exception {

		JSONArray page1Arr = flightDetails.getJSONArray(page1Name);
		JSONArray page2Arr = flightDetails.getJSONArray(page2Name);

		for (int i = 0; i < page1Arr.length(); i++) {
			Iterator keys = page1Arr.getJSONObject(i).keys();
			while (keys.hasNext()) {
				String keyValue = keys.next().toString().trim();
				if (page1Arr.getJSONObject(i).getString(keyValue)
						.equals(page2Arr.getJSONObject(i).getString(keyValue))) {
				} else {
				        test.warning(keyValue + "Doesn't match");
				}

			}

		}

	}
	
	
	public void assertFlightDataModifySearch(JSONObject flightDetails, String page1Name, String page2Name,ExtentTest test,SoftAssert softAssert) throws Exception {

		JSONArray page1Arr = flightDetails.getJSONArray(page1Name);
		JSONArray page2Arr = flightDetails.getJSONArray(page2Name);

		for (int i = 0; i < page1Arr.length(); i++) {
			Iterator keys = page1Arr.getJSONObject(i).keys();
			while (keys.hasNext()) {
				String keyValue = keys.next().toString().trim();
				if (page1Arr.getJSONObject(i).getString(keyValue)
						.equals(page2Arr.getJSONObject(i).getString(keyValue))) {
				} else {
					softAssert.assertTrue(true,keyValue+" Doesn't Matches after modify search");
				        logStep(keyValue + "Doesn't match",test);
				}

			}

		}

	}

	public void printFlowDetailMap(Map<String, Object> flowDetailMap) {
		ArrayList<Object> keySetArray;
		Object keyObject;
		if(flowDetailMap != null && !(flowDetailMap.isEmpty())){
		for (int i = 0; i < flowDetailMap.size(); i++) {
			keySetArray = new ArrayList<Object>(flowDetailMap.keySet());
			keyObject = keySetArray.get(i);
			System.out.println("Object: " + keyObject + " value: " + flowDetailMap.get(keyObject));
			Reporter.log("Object: " + keyObject + " value: " + flowDetailMap.get(keyObject));
		}
		}else {
			Reporter.log("FlowdetailMap is empty");
		}
	}
	
	public  Response getCall(String request){
		   Response rep = 	get(request);
		   return rep;
		   
		}
	
/*#########################################API #############################################*/
	
	public String  flightsRequestDataGoomoOW(String travelType,String source,String destination,String date,String classType,String aCount,String cCount,String iCount) throws Exception{
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		String str="";
		if(environment.equalsIgnoreCase("staging") || environment.equalsIgnoreCase("beta"))
		{
			br = new BufferedReader(new FileReader(GOOMO_GETAVAILABILITY_OW_REQUEST_TEMPLATE));
			  while((str=br.readLine())!=null){

			 if (str.contains("origin"))
					str = str.replace(str.split("\\{")[1], "\"airport\":\""+source+"\"");
			 else if (str.contains("destination"))
					str = str.replace(str.split("\\{")[1], "\"airport\":\""+destination+"\"");
				else if (str.contains("travel_date"))
					str = str.replace(str.split(":")[1], "\""+date+"\",");
				else if (str.contains("class_type"))
					str = str.replace(str.split(":")[1], "\""+classType.toUpperCase()+"\",");
				else if (str.contains("adult_count"))
					str = str.replace(str.split(":")[1], aCount+",");
				else if (str.contains("child_count"))
					str = str.replace(str.split(":")[1], cCount+ ",");
				else if (str.contains("infant_count"))
					str = str.replace(str.split(":")[1], iCount);
				sb.append(str);
			  }
			  
		}else{
			throw new SkipException("You are running on live environment");
		}

		return sb.toString();
	}
	
	
	public String  flightsRequestDataGoomoRT(String travelType,String source,String destination,String onwardDate,String returnDate,String classType,String aCount,String cCount,String iCount) throws Exception{
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		String str="";
		if(environment.equalsIgnoreCase("staging") || environment.equalsIgnoreCase("beta"))
		{
			br = new BufferedReader(new FileReader(GOOMO_GETAVAILABILITY_RT_REQUEST_TEMPLATE));
			  while((str=br.readLine())!=null){

			 if (str.contains("origin"))
					str = str.replace(str.split("\\{")[1], "\"airport\":\""+source+"\"");
			 else if (str.contains("destination"))
					str = str.replace(str.split("\\{")[1], "\"airport\":\""+destination+"\"");
				else if (str.contains("travel_date"))
					str = str.replace(str.split(":")[1], "\""+onwardDate+"\",");
				else if (str.contains("return_date"))
					str = str.replace(str.split(":")[1], "\""+returnDate+"\",");
				else if (str.contains("class_type"))
					str = str.replace(str.split(":")[1], "\""+classType.toUpperCase()+"\",");
				else if (str.contains("adult_count"))
					str = str.replace(str.split(":")[1], aCount+",");
				else if (str.contains("child_count"))
					str = str.replace(str.split(":")[1], cCount+ ",");
				else if (str.contains("infant_count"))
					str = str.replace(str.split(":")[1], iCount);
				sb.append(str);
			  }
			  
		}else{
			throw new SkipException("You are running on live environment");
		}

		return sb.toString();
	}
	
	public  Response postCallAuthentication(String request,String contentType,String body){
		 
	     Response rep =null;
	     if(environment.equalsIgnoreCase("staging")){
	      rep= given().auth().basic("goomo","3YLPBxRxHf337DuW").contentType(contentType).body(body).when().post(request);  
	     }
	     else if(environment.equalsIgnoreCase("beta")){
	    	 rep= given().auth().basic("goomo","6RrGwFKEMturG28Q").contentType(contentType).body(body).when().post(request);
	     }
	   
	     return rep;
    }  
	public static void verifyOkAuthenticationResponse(Response rp){
		rp.then().assertThat().statusCode(201);
	}
	
      public String getGoomoSearchTrackId(Response authRep) throws Exception{
		
		JSONObject sid = new JSONObject(authRep.body().asString());
		return sid.getJSONObject("meta").getString("search_track_id");
	}
      
      public  Response getCall(String request,String contentType){
  		return given().contentType(contentType).get(request);
  		
  	}
      
      public boolean checkSearchResultStatus(Response rep) throws Exception{
		   if(rep.body().jsonPath().getInt("meta.missing_connectors_count")==0)
			   return true;
		   
		   return false;
	}
      public Response retrySearchRequest(boolean status,Response response,String searchRequest,String searchId) throws Exception{
  		boolean tmpStatus = status;
  		 Response tmpResponse = response;
  		 int count=0;
  		while(!tmpStatus && count<=40){
  		   tmpResponse = getCall(searchRequest+searchId, "application/json");
  		    if(tmpResponse.body().jsonPath().getInt("meta.missing_connectors_count")==0){
  		    	  tmpStatus = true;
  		    }else{
  		    	 tmpStatus = false;
  		    	 count++;
  		    }
  		    Thread.sleep(1000);
  		    System.out.println("Loading Results......");
  	    }
  		return tmpResponse;
  	}
      
      public static void verifyOkResponse(Response rp){
		  rp.then().assertThat().statusCode(200);
	}
      
      public int getSRPFlightsCount(Response rep) throws Exception{
    	   JSONObject jsonObject = new JSONObject(rep.body().asString());
    	   int ongoingFlights = jsonObject.getJSONArray("ongoing_travel_plans").length();
    	   int returnFlights = jsonObject.getJSONArray("return_travel_plans").length();
    	   return (ongoingFlights+returnFlights);
      }
      
      public String urlGeneratorGoomo(String url){
  	    if(environment.equalsIgnoreCase("staging"))
  	    	 return "https://pre-client-api.goomo.com/v1/"+url;
  	    else if(environment.equalsIgnoreCase("production") )
  	    	//https://client-api.goomo.com/v1/flights
  	    	return "https://client-api.goomo.com/v1/"+url;
  	    else 
  	    	return "";
      }
      
      
      public void skipTest(boolean flag,String message){
    	  if(flag)
    		  throw new SkipException(message);
      }
      
      
      public Map<String,String> populatePriceDataHotel(WebDriver driver){
  		Map<String,String> fareDetails=new HashMap<String,String>();
  	    String hotelName=getWebElement(driver,By.xpath("//div[@class='flight_journey_timing_section']/h4")).getText();
  	    fareDetails.put("HotelName",hotelName);
  	    String CheckIn=getWebElement(driver,By.xpath("//div[@class='content_summary_timing']/div[@class='info_summary_timing']")).getText();
  	    fareDetails.put("CheckInTime", CheckIn);
  		String CheckOut=getWebElement(driver,By.xpath("//div[@class='content_summary_timing']/div[@class='info_summary_timing text_align_right']") ).getText();
  		fareDetails.put("checkOutTime", CheckOut);
  		String PassengerDetails=getWebElement(driver, By.xpath("//p[@class='small_info_room']")).getText();
  		fareDetails.put("Passenger&RoomCount", PassengerDetails);
  		List<WebElement> priceList=getWebElementList(driver,By.xpath("//ul[@class='base_fare_ul']"));
  		for(int i=0;i<priceList.size();i++)
  		{
  			String key=priceList.get(i).findElement(By.xpath("//li[@class='text_align_left']")).getText();
  			String value=priceList.get(i).findElement(By.xpath("//li[@class='text_align_right']")).getText();
  			fareDetails.put(key, value);
  			
  		}
  		String TotalAmount=getWebElement(driver,By.xpath("//p[@class='promo_amount']/span[1]")).getText();
  		String textTotal=getWebElement(driver,By.xpath("//p[@class='promo_amount']/span[2]")).getText();
  		fareDetails.put(textTotal, TotalAmount);
  		
  		return fareDetails;
  	}
     
	
	
}
