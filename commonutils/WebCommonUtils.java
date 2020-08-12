package web.goomo.commonutils;

import static api.commonServices.CachedProperties.instance;
import static org.testng.Assert.assertTrue;           

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import api.commonServices.CachedProperties;


public class WebCommonUtils {

	static boolean recordFlag = false;
	protected static boolean makePaymentFlag = false;
	protected static String environment = "";
	static String browserType = "";
	public static String stagingURL = "";
	public static String productionURL = "";
	public static String betaURL = "";
	public static String devURL = "";
	public static String intURL = "";
	public static String perfURL = "";
	public static String Payment_mode = "";
	static String flightsOWSearch = "";
	static String flightsRTSearch = "";
	static String flightsIntlRTSearch = "";
	protected static String backofficeURLStaging;
	protected static String hotelagentbookingURLStaging;
	protected static String backofficeURLDev;
	protected static String backofficeURLI;
	protected static String airportJsonStaging;
	protected static String airportJsonDev;
	protected static String airportJsonI;

	static String droolsURLStaging;
	static String droolsURLDev;
	static boolean runOnGrid;
	static String webGridIp;
	protected static ExtentReports extent;
	ExtentHtmlReporter htmlReporter;
	String filePath = "./reports/WEB-REPORT.html";
	protected String screenShotPath = "";
	public CachedProperties common = instance();

	protected static Map<String, String> flightsRepo = getObjectRepo("repo_flights.csv", "flights");
	protected static Map<String, String> hotelRepo = getObjectRepo("repo_hotels_web.csv", "hotels");
	protected static Map<String, String> trainsRepo = getObjectRepo("repo_trains.csv", "trains");
	protected static Map<String, String> commonProperties = new HashMap<>();
	protected static final String gdsAirlines[] = new String[] { "Bangkok Airways", "Jet Airways", "JetKonnect",
			"Air India", "Kingfisher", "Air India Express", "Air Arabia", "British Airways", "Cathay Pacific",
			"Egypt Air", "Emirates", "Ethiopian Air", "Etihad Airways", "Gulf Air", "Kenya Airways", "Kuwait Airways",
			"Lufthansa", "Malaysia Airlines", "Multiple Carriers", "Myanmar Intl", "Oman Av", "Qantas Airways",
			"Qatar Airways", "Royal Jordanian", "SAS", "SilkAir", "Saudi Arabian Air", "Singapore Air", "South African",
			"SriLankan Airlines", "Swiss Intl Air", "Thai Airways", "Turkish Airlines", "Virgin Atlantic", "Air Asia",
			"All Nippon" };
	public static final String GOOMO_GETAVAILABILITY_RT_REQUEST_TEMPLATE = System.getProperty("user.dir")
			+ "//resources//dataJsons//flights//Goomo_GetAvailability_RT_Request_Template.json";
	public static final String GOOMO_GETAVAILABILITY_OW_REQUEST_TEMPLATE = System.getProperty("user.dir")
			+ "//resources//dataJsons//flights//Goomo_GetAvailability_OW_Request_Template.json";

	public static final String GOOMO_DOMESTIC_OW_GET_AVAILABILITY = "flights/one_way/search";
	public static final String GOOMO_DOMESTIC_OW_GET_AVAILABILITY_RESULTS = "flights/one_way/";
	public static final String GOOMO_DOMESTIC_OW_GET_FLIGHT_DETAILS = "flights/one_way_details";

	public static final String GOOMO_DOMESTIC_RT_GET_AVAILABILITY = "flights/roundtrip/search";
	public static final String GOOMO_DOMESTIC_RT_GET_AVAILABILITY_RESULTS = "flights/domestic_roundtrip/";
	public static final String GOOMO_INTERNATIONAL_RT_GET_AVAILABILITY_RESULTS = "flights/international_roundtrip/";

	public void retrieveProperties() {

		Properties prop = new Properties();
		InputStream input = null;

		try {
			File file = new File(".");
			input = new FileInputStream(file.getCanonicalPath() + "/resources/common.properties");
			// load a properties file
			prop.load(input);
			System.err.println(prop.getProperty("record"));
			recordFlag = Boolean.parseBoolean(prop.getProperty("record"));

			System.out.println(prop.getProperty("makepayment_flag"));
			makePaymentFlag = Boolean.parseBoolean((prop.getProperty("makepayment_flag")));
			environment = prop.getProperty("environment");
			browserType = prop.getProperty("browser");
			runOnGrid = Boolean.parseBoolean(prop.getProperty("runongrid"));

			droolsURLStaging = prop.getProperty("DROOL_RULE_ENGINE_STAGING_URL");
			droolsURLDev = prop.getProperty("DROOL_RULE_ENGINE_DEV_URL");

			backofficeURLStaging = prop.getProperty("BACKOFFICE_STAGING_URL");
			hotelagentbookingURLStaging = prop.getProperty("AGENT_BACKOFFICE_STAGING_URL");
			backofficeURLDev = prop.getProperty("BACKOFFICE_DEV_URL");
			backofficeURLI = prop.getProperty("BACKOFFICE_INTEGRATION_URL");

			airportJsonStaging = prop.getProperty("STAGING_AIRPORT_JSON");
			airportJsonDev = prop.getProperty("DEV_AIRPORT_JSON");
			airportJsonI = prop.getProperty("INTEGRATION_AIRPORT_JSON");

			stagingURL = prop.getProperty("STAGING_URL");
			betaURL = prop.getProperty("BETA_URL");
			productionURL = prop.getProperty("PRODUCTION_URL");
			devURL = prop.getProperty("DEV_URL");
			intURL = prop.getProperty("INT_URL");
			perfURL =prop.getProperty("PERF_URL");

			flightsOWSearch = prop.getProperty("FLIGHTS_OW_SEARCH");
			flightsRTSearch = prop.getProperty("FLIGHTS_RT_SEARCH");
			flightsIntlRTSearch = prop.getProperty("FLIGHTS_INTL_RT_SEARCH");
			webGridIp = prop.getProperty("WEB_GRID_IP");
			Payment_mode = prop.getProperty("payment_mode");

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public String getData(String key) {
		Properties prop = new Properties();
		InputStream input = null;
		String value = null;

		try {
			File file = new File(".");
			input = new FileInputStream(file.getCanonicalPath() + "//resources//testdata.properties");
			// load a properties file
			prop.load(input);
			value = prop.getProperty(key);

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;

	}

	public void safeClick(WebDriver driver, By by) {
		boolean elementPresentFlag = false;
		if (elementPresentFlag = elementVisible(driver, by, 5)) {
			driver.findElement(by).click();
		} else {
			assertTrue(elementPresentFlag, "Elements : " + String.valueOf(by) + " not present ");
		}
	}

	public void safeClear(WebDriver driver, By by) throws Exception {
		boolean elementPresentFlag = false;
		if (elementPresentFlag = elementVisible(driver, by, 10)) {
			driver.findElement(by).clear();
		} else {
			assertTrue(elementPresentFlag, "Elements : " + String.valueOf(by) + " not present ");
		}
	}

	public void safeType(WebDriver driver, By by, String text) {
		boolean elementPresentFlag = false;
		if (elementVisible(driver, by, 10)) {
			driver.findElement(by).sendKeys(text);
		} else {
			assertTrue(elementPresentFlag, "Element : " + String.valueOf(by) + " not present ");
		}
	}

	public void maximizeWindow(WebDriver driver) {
		try {
			driver.manage().window().maximize();
		} catch (Exception e) {

		}
	}

	public void mouseHoverElement(WebDriver driver, WebElement element) {
		Actions builder = new Actions(driver);
		builder.moveToElement(element).build().perform();
	}

	public boolean elementVisible(WebDriver driver, By by, int time) {
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		boolean elementNotVisibleFlag = false;
		for (int i = 0; i < time; i++) {
			try {
				if (driver.findElement(by).isDisplayed()) {
					System.out.println("Element displayed" + by);
					elementNotVisibleFlag = true;
					break;
				}

			} catch (Exception e) {
				System.out.println("Element : " + by + " not Visible");
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		return elementNotVisibleFlag;
	}

	public boolean elementEnabled(WebDriver driver, By by, int time) throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		boolean elementNotVisibleFlag = false;
		for (int i = 0; i < time; i++) {
			try {
				if (driver.findElement(by).isEnabled()) {
					System.out.println("Element displayed" + by);
					elementNotVisibleFlag = true;
					break;
				}

			} catch (Exception e) {
				System.out.println("Element : " + by + " not Visible");
			}
			Thread.sleep(1000);
		}
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		return elementNotVisibleFlag;
	}

	public boolean elementPresent(WebDriver driver, By by, int time) {
		boolean elementPresentFlag = false;
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		List<WebElement> elements = new ArrayList<WebElement>();
		for (int i = 0; i < time; i++) {
			try {
				WebElement we = null;
				if ((we = driver.findElement(by)) != null) {
					System.out.println("Element Present" + by);
					elementPresentFlag = true;
					break;
				}
			} catch (Exception e) {
			}

			// TODO check with is displayed

		}
		return elementPresentFlag;
	}

	public boolean elementDisplayed(WebDriver driver, By by, int time) {
		boolean elementDisplayed = false;
		l1: for (int i = 0; i < time; i++) {
			try {
				WebElement we;
				if ((we = driver.findElement(by)) != null) {
					if (we.isDisplayed()) {
						elementDisplayed = true;
						break l1;
					}

				}
			} catch (Exception e) {
				System.err.println("Unable to fetch the element");
			}
			try {
				Thread.sleep(500);
			} catch (Exception e) {
				System.err.println("Error in element sleep");
			}

		}
		return elementDisplayed;
	}

	public List<WebElement> getWebElementList(WebDriver driver, By by) {
		boolean elementPresentFlag = false;
		if (elementPresentFlag = elementPresent(driver, by, 5)) {
			return driver.findElements(by);
		}
		return null;
	}

	public WebElement getWebElement(WebDriver driver, By by) {
		boolean elementPresentFlag = false;
		if (elementPresentFlag = elementPresent(driver, by, 5)) {
			return driver.findElement(by);
		}
		return null;

	}

	public void scrollTillTop(WebDriver driver) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("window.scrollTo(0, -document.body.scrollHeight)");
	}

	public void scrollToRight(WebDriver driver) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("window.scrollBy(1000,0)", "");
	}

	public void scrollTillEnd(WebDriver driver) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	public void scrollToElement(WebDriver driver, WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
	}

	public boolean checkForTwoConditions(WebDriver driver, By eleToAppear, By eleNotToAppear, int time) {
		boolean elementNotVisibleFlag = false;
		l1: for (int i = 0; i < time; i++) {
			try {
				if (elementVisible(driver, eleToAppear, 1)) {
					System.out.println("Element displayed" + eleToAppear);
					elementNotVisibleFlag = true;
					break l1;
				} else if (elementVisible(driver, eleNotToAppear, 1)) {
					System.out.println("Exception has generated " + eleNotToAppear);
					elementNotVisibleFlag = false;
					break l1;
				}

			} catch (Exception e) {
				System.out.println("Element : " + eleToAppear + " not Visible");
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {

			}
		}
		return elementNotVisibleFlag;

	}
	
	public boolean checkForMultipleConditions(WebDriver driver, By eleToAppear, By eleNotToAppear1,By eleNotToAppear2, int time) {
		boolean elementNotVisibleFlag = false;
		l1: for (int i = 0; i < time; i++) {
			try {
				if (elementVisible(driver, eleToAppear, 1)) {
					System.out.println("Element displayed" + eleToAppear);
					elementNotVisibleFlag = true;
					break l1;
				} else if (elementVisible(driver, eleNotToAppear1, 1)) {
					System.out.println("Exception has generated " + eleNotToAppear1);
					elementNotVisibleFlag = false;
					break l1;
				}
				else if (elementVisible(driver, eleNotToAppear2, 1)) {
					System.out.println("Exception has generated " + eleNotToAppear2);
					elementNotVisibleFlag = false;
					break l1;
				}

			} catch (Exception e) {
				System.out.println("Element : " + eleToAppear + " not Visible");
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {

			}
		}
		return elementNotVisibleFlag;

	}

	public void scrollDownMin(WebDriver driver) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,150)", "");
	}

	public void scrollDown(WebDriver driver) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,250)", "");

	}

	public void scrollUp(WebDriver driver) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,-250)", "");
	}

	public boolean waitForElement(WebDriver driver, int time, By by) {

		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

		boolean elementActiveFlag = false;
		long timerNow = new Date().getTime();
		int i;
		for (i = 0; (new Date().getTime() - timerNow) / 1000 <= time;) {
			if (elementPresent(driver, by, 1)) {
				elementActiveFlag = true;
				break;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;

		}
		System.out.println((new Date().getTime() - timerNow) / 1000 + " seconds taken for " + by + "  to load");
		if (!elementActiveFlag) {
			System.out.println("Element By " + by + " Not Loaded in" + time + "Seconds");
		}
		return elementActiveFlag;
	}

	public boolean waitForElementToDisappear(WebDriver driver, int time, By by) {

		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

		boolean elementActiveFlag = false;
		long timerNow = new Date().getTime();
		int i;
		for (i = 0; (new Date().getTime() - timerNow) / 1000 <= time;) {
			if (!elementPresent(driver, by, 1)) {
				elementActiveFlag = true;
				break;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;

		}
		System.out.println((new Date().getTime() - timerNow) / 1000 + " seconds taken for " + by + "  to load");
		if (!elementActiveFlag) {
			System.out.println("Element By " + by + " Not Loaded in" + time + "Seconds");
		}
		return elementActiveFlag;
	}

	public static By getElementType(String keyValue) {
		System.out.println("keyvalue " + keyValue);
		String key = keyValue.split(":")[0];
		String value = keyValue.split(":")[1];
		System.out.println(key + " " + value);

		if ("xpath".equalsIgnoreCase(key))
			return By.xpath(value);
		else if (key.equalsIgnoreCase("id"))
			return By.id(value);
		else if (key.equalsIgnoreCase("css"))
			return By.cssSelector(value);
		else if (key.equalsIgnoreCase("classname") || key.equalsIgnoreCase("class"))
			return By.className(value);
		else if (key.equalsIgnoreCase("linktext"))
			return By.linkText(value);
		else if (key.equalsIgnoreCase("partiallinktext"))
			return By.partialLinkText(value);
		else if (key.equalsIgnoreCase("name"))
			return By.name(value);
		else if (key.equalsIgnoreCase("tag") || key.equalsIgnoreCase("tagname"))
			return By.tagName(value);
		else
			return null;

	}

	public static Map<String, String> getObjectRepo(String fileName, String repoName) {
		Map<String, String> tmpRepo;
		tmpRepo = getRepoType(repoName);
		if (tmpRepo == null) {
			BufferedReader br;
			tmpRepo = new HashMap<>();
			try {
				br = new BufferedReader(
						new FileReader(System.getProperty("user.dir") + "//resources//objectrepo//" + fileName));
				String str;
				while ((str = br.readLine()) != null) {
					if (!str.startsWith("####")) {
						String key;
						String value;
						key = str.split(";")[0];
						value = str.split(";")[1];
						tmpRepo.put(key, value);
					}

				}

			} catch (FileNotFoundException ffe) {
				System.out.println(ffe);
			} catch (IOException ie) {
				System.out.println(ie);
			}

			finally {

			}

			return tmpRepo;
		} else {
			return tmpRepo;
		}

	}

	public static Map<String, String> getRepoType(String repoName) {
		if (repoName.equalsIgnoreCase("flights"))
			return trainsRepo;
		 else if (repoName.equalsIgnoreCase("hotels")) 
			 return hotelRepo;
		else
			return trainsRepo;
	}

	public static String goomoDate(int days) {
		Calendar c = new GregorianCalendar();
		c.add(Calendar.DATE, +days);
		Date s = c.getTime();
		return new SimpleDateFormat("yyyy-MM-dd").format(s);

	}

	public static String goomoPassportDate(int days) {
		Calendar c = new GregorianCalendar();
		c.add(Calendar.DATE, +days);
		Date s = c.getTime();
		return new SimpleDateFormat("dd-MM-yyyy").format(s);

	}

	public static String infantDob(int days) {
		Calendar c = new GregorianCalendar();
		c.add(Calendar.DATE, -days);
		Date s = c.getTime();
		return new SimpleDateFormat("dd-MM-yyyy").format(s);

	}

	public static String infantDobCurrentDate(int days) {
		Calendar c = new GregorianCalendar();
		c.add(Calendar.DATE, -days);
		Date s = c.getTime();
		return new SimpleDateFormat("dd-MM-yyyy").format(s).split("-")[0];

	}

	public int generateRandomNumber(int max, int min) {
		Random rn = new Random();
		int range = max - min + 1;
		return rn.nextInt(range) + min;

	}

	public void screenshot(ITestResult result, WebDriver driver) throws Exception {

		String testName = result.getName();
		File file = new File(".");
		String filename = testName + ".png";
		String filepath = file.getCanonicalPath() + "/ScreenShots/Web/" + putLogDate() + filename;
		if (common.value("application_level").equalsIgnoreCase("1"))
			screenShotPath = "job/Automation/" + common.value("build_number") + "/artifact/ScreenShots/Web/"
					+ putLogDate() + filename;
		else if (common.value("application_level").equalsIgnoreCase("0"))
			screenShotPath = "job/Regress1/job/ui_regress/job/master/" + common.value("build_number")
					+ "/artifact/ScreenShots/Web/" + putLogDate() + filename;
		else
			screenShotPath = System.getProperty("user.dir") + "/ScreenShots/Web/" + putLogDate() + filename;
		try {
			WebDriver augmentedDriver = new Augmenter().augment(driver);
			File screenshotFile = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenshotFile, new File(filepath));
			File reportFile = new File(filepath);
			reportLogScreenshot(reportFile, filename);
		} catch (Exception e) {
			Reporter.log("Unable to get the screenshot");
		}
	}

	public void screenshotAnyCase(ITestResult result, WebDriver driver) throws Exception {

		String testName = result.getName();
		File file = new File(".");
		String filename = testName + ".png";
		String filepath = file.getCanonicalPath() + "/ScreenShots/Web/" + putLogDate() + filename;
		if (common.value("application_level").equalsIgnoreCase("1"))
			screenShotPath = "job/Automation/" + common.value("build_number") + "/artifact/ScreenShots/Web/"
					+ putLogDate() + filename;
		else if (common.value("application_level").equalsIgnoreCase("0"))
			screenShotPath = "job/Regress1/job/ui_regress/job/master/" + common.value("build_number")
					+ "/artifact/ScreenShots/Web/" + putLogDate() + filename;
		else
			screenShotPath = System.getProperty("user.dir") + "/ScreenShots/Web/" + putLogDate() + filename;
		try {
			WebDriver augmentedDriver = new Augmenter().augment(driver);
			File screenshotFile = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenshotFile, new File(filepath));
			File reportFile = new File(filepath);
			reportLogScreenshot(reportFile, filename);
		} catch (Exception e) {
			Reporter.log("Unable to get the screenshot");
		}
	}

	public String screenshotStepWise(WebDriver driver, String step) throws Exception {

		String path;
		File file = new File(".");
		String filename = step + ".png";
		String filepath = file.getCanonicalPath() + "/ScreenShots/Web/" + putLogDate() + filename;
		if (common.value("application_level").equalsIgnoreCase("1"))
			path = "/job/Automation/" + common.value("build_number") + "/artifact/ScreenShots/Web/" + putLogDate()
					+ filename;
		else if (common.value("application_level").equalsIgnoreCase("0"))
			path = "/job/Regress1/job/ui_regress/job/master/" + common.value("build_number")
					+ "/artifact/ScreenShots/Web/" + putLogDate() + filename;
		else
			path = System.getProperty("user.dir") + "/ScreenShots/Web/" + putLogDate() + filename;
		try {
			WebDriver augmentedDriver = new Augmenter().augment(driver);
			File screenshotFile = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenshotFile, new File(filepath));
		} catch (Exception e) {
			Reporter.log("Unable to get the screenshot");
		}

		return path;
	}

	protected void reportLogScreenshot(File file, String fileName) {
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		String absolute = file.getAbsolutePath();
		if (common.value("application_level").equalsIgnoreCase("1"))
			absolute = " /job/Automation/" + common.value("build_number") + "/artifact/ScreenShots/Web/" + putLogDate()
					+ fileName;
		else if (common.value("application_level").equalsIgnoreCase("0"))
			absolute = "/job/Regress1/job/ui_regress/job/master/" + common.value("build_number")
					+ "/artifact/ScreenShots/Web/" + putLogDate() + fileName;
		else
			absolute = System.getProperty("user.dir") + "/ScreenShots/Web/" + putLogDate() + fileName;
		screenShotPath = absolute;
		Reporter.log(
				"<style>table {border-collapse: collapse;}th, td {border: 1px solid #ccc;padding: 10px;text-align: left;} "
						+ "tr:nth-child(even) { background-color: #eee;}tr:nth-child(odd) { background-color: #fff; } </style>"
						+ "<table width=\"100%\"> <tr>" + " <td align=\"left\">" + "<a href=\"" + absolute
						+ "\"><p align=\"left\">" + fileName.replace(".png", "") + "</p>" + "<br>(Zoom in)</br></td>"
						+ " <td>" + "<p><img width=\"300\" height=\"400\" src=\"" + absolute + "\" alt=\"screenshot at "
						+ new Date() + "\"/></p></a><br />" + "</td>" + "</tr>" + "</table>");
	}

	public String putLogDate() {
		Calendar c = new GregorianCalendar();
		c.add(Calendar.DATE, +0);
		Date s = c.getTime();
		return new SimpleDateFormat("ddMMMyyyy_hh--").format(s);

	}

	public String safeGetText(WebDriver driver, By by) {
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		if (elementPresent(driver, by, 10)) {
			return driver.findElement(by).getText();
		}
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		return "";
	}

	public void navigateTo(WebDriver driver, String productName) {
		if("flights".equalsIgnoreCase(productName))
		  safeClick(driver, getElementType(trainsRepo.get("flights")));
		else if ("hotels".equalsIgnoreCase(productName))
		  safeClick(driver, getElementType(hotelRepo.get("hotels")));
		else
		  safeClick(driver, getElementType(trainsRepo.get("trains")));
	}

	// EXTENT REPORTS
	public ExtentReports getExtent() {
		if (extent != null)
			return extent; // avoid creating new instance of html file
		extent = new ExtentReports();

		extent.attachReporter(getHtmlReporter());
		return extent;
	}

	private ExtentHtmlReporter getHtmlReporter() {

		htmlReporter = new ExtentHtmlReporter(filePath);
		htmlReporter.loadXMLConfig("./config/config.xml");

		// make the charts visible on report open
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setDocumentTitle("GOOMO WEB Automation report");
		htmlReporter.config().setReportName("Regression Cycle");

		// Append the existing report
		htmlReporter.setAppendExisting(false);
		Locale.setDefault(Locale.ENGLISH);
		return htmlReporter;
	}

	public ExtentTest createTest(String name, String description, ExtentTest test) {
		if (test == null) {
			test = extent.createTest(name, description);
			return test;
		} else
			return test;
	}

	public void logStep(String message, ExtentTest test) {
		test.log(Status.INFO, message);
	}

	public void waitForElementToBeVisible(WebDriver driver, By by) {

		WebDriverWait wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.presenceOfElementLocated(by));
	}

	public boolean waitForElementAttributeChange(WebDriver driver, int time, By by, String text) {

		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

		boolean elementActiveFlag = false;
		long timerNow = new Date().getTime();
		int i;
		for (i = 0; (new Date().getTime() - timerNow) / 1000 <= time;) {
			System.out.println(getWebElement(driver, by).getAttribute("class"));
			if (getWebElement(driver, by).getAttribute("class").contains(text)) {
				elementActiveFlag = true;
				break;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;

		}
		System.out.println((new Date().getTime() - timerNow) / 1000 + " seconds taken for " + by + "  to load");
		if (!elementActiveFlag) {
			System.out.println("Element By " + by + " Not Loaded in" + time + "Seconds");
		}
		return elementActiveFlag;
	}

	/*
	 * ######################################################## BACKOFFICE
	 * VERIFICATION ####################################################
	 */

	String cookieValue = "WZRK_G=360a6f1796764e61a4dd36666efb43e8; _ga=GA1.2.1126633605.1493898589; cto_lwid=31e3cb82-3f2c-4beb-925d-041e7cd82300;"
			+ " ajs_user_id=null; ajs_group_id=null; browser.timezone=Asia/Kolkata; _gid=GA1.2.1530131912.1523862395; "
			+ "AMCVS_1BFE9458597F085E0A495E56%40AdobeOrg=1; s_campaign=undefined%7Cundefined%7Cundefined; s_cvp=%5B%5B%27undefined%257"
			+ "Cundefined%257Cundefined%27%2C%271523945881331%27%5D%5D; s_cc=true; s_sq=%5B%5BB%5D%5D; AMCV_1BFE9458597F085E0A495E56%40AdobeOrg"
			+ "=-1891778711%7CMCIDTS%7C17640%7CMCMID%7C48772949775004948062863715816699120187%7CMCAAMLH-1524467187%7C3%7CMCAAMB-1524636200%7CRK"
			+ "hpRz8krg2tLO6pguXWp5olkAcUniQYPHaMWWgdJ3xzPWQmdj0y%7CMCOPTOUT-1524038600s%7CNONE%7CMCAID%7CNONE%7CMCSYNCSOP%7C411-17645%7CvVersion"
			+ "%7C2.4.0; s_ptc=0.38%5E%5E600%5E%5E0.05%5E%5E0.14%5E%5E0.04%5E%5E0.12%5E%5E7.50%5E%5E0.01%5E%5E7.89; gttc=1524032012684; s_nr=1524"
			+ "032016390-Repeat; tp=1906; s_ppv=flights%253Asearch-results%253Aoneway%2C100%2C30%2C1906; _gentelella_on_rails_session=a3AzWjBvUjJ"
			+ "ZbTFqMlpoaFBtZi9aNFRUTlRqSExKYU5qS2dvWGlPYXBENGpZcmhNMklkSCthUWVYeXpwWVgvbGJML3YrMXluVHJsWmErelYrZGVaakMzK1p5bHNqd0xjSmNBRVR0K000N"
			+ "FdaQ3pLQkpCWFdGcmpYWmNlcVdZeXBBYytiNTZKS1dpaU5XVHhGWkhJbll4dmkyNjZ5bVRpanJqenBBNThuaFhqcmpFYUVIUkhtV0JGUVFVUFZzaGRpcC8yVlBtS29YejFD"
			+ "NEZ5emlEekQwL25sZ1gybU14M2ZTd2JZRFByWGVvdGZxb1lhUkFzK3ZIM29WOWYvNTB5dGJ6dHRmdndEUkZJNHVnZzRMdUJOYmx0OGUyVCt6bTVyOU5FR1Yvc3VYMFJEOG"
			+ "Y3bWN2NGptaDdkdzdiSTFWWXM5ZFB2Qk5UYm91RHNHZnB1SFhiaFdRPT0tLTJXYm9JY21jSmUrSmJMbzllSUp0NXc9PQ%3D%3D--9fbe62d3fddfd78cdb76384719306a5"
			+ "c42b5735e";

	public String getTripInfo(String tripId) throws Exception {

		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("goomo", "goomoturbo");
		provider.setCredentials(AuthScope.ANY, credentials);

		String url = "https://pre-backoffice.goomo.team/flights/bookings?utf8=%E2%9C%93&search=" + tripId + "&button=";

		HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
		HttpGet request = new HttpGet(url + 1);
		request.addHeader("Accept", "*/*");
		request.addHeader("authorization", "Basic Z29vbW86Z29vbW90dXJibw==");
		request.addHeader("User-Agent",
				"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36");
		request.addHeader("cookie", cookieValue);
		HttpResponse response = client.execute(request);

		int responseCode = response.getStatusLine().getStatusCode();
		System.out.println(responseCode);
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		StringBuilder result = new StringBuilder();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		Document doc = Jsoup.parse(result.toString());
		Elements elements = doc.getElementsByTag("tbody").get(1).getElementsByTag("tr");

		Elements tdTags = elements.get(0).getElementsByTag("td");
	    return tdTags.get(7).text().split("INR")[0];

	}
	
	public void sleepTime(int milliSecs) {
		   try {
			    Thread.sleep(milliSecs);
		   }catch(Exception e) {
			   
		   }
	}


}
