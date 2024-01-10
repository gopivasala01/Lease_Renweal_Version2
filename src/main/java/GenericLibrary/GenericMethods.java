package GenericLibrary;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import ExtractData.DatabaseClass;
import io.github.bonigarcia.wdm.WebDriverManager;
import mainPackage.AppConfig;
import mainPackage.Locators;
import mainPackage.RunnerClass;

public class GenericMethods {

	public static String currentTime;
	public static Logger logger;
	
	public static boolean login()
	{
		try
		{
		RunnerClass.downloadFilePath = AppConfig.downloadFilePath;
		Map<String, Object> prefs = new HashMap<String, Object>();
	    // Use File.separator as it will work on any OS
	    prefs.put("download.default_directory",
	    		RunnerClass.downloadFilePath);
        // Adding cpabilities to ChromeOptions
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--remote-allow-origins=*");
		//WebDriverManager.chromedriver().setup();
		WebDriverManager.chromedriver().clearDriverCache().setup();
        RunnerClass.driver= new ChromeDriver(options);
		RunnerClass.driver.manage().window().maximize();
        RunnerClass.driver.get(AppConfig.URL);
        RunnerClass.driver.findElement(Locators.username).sendKeys(AppConfig.username); 
        RunnerClass.driver.findElement(Locators.password).sendKeys(AppConfig.password);
        RunnerClass.driver.findElement(Locators.signInButton).click();
        RunnerClass.actions = new Actions(RunnerClass.driver);
        RunnerClass.js = (JavascriptExecutor)RunnerClass.driver;
        RunnerClass.driver.manage().timeouts().implicitlyWait(2,TimeUnit.SECONDS);
        RunnerClass.wait = new WebDriverWait(RunnerClass.driver, Duration.ofSeconds(2));
        logger.info("login successful");
        try
        {
        if(RunnerClass.driver.findElement(Locators.loginError).isDisplayed())
        {
        	logger.error("Login Error/Failed");
		    RunnerClass.failedReason = RunnerClass.failedReason+","+ "Login Error/Failed";
			return false;
        }

        }
        catch (TimeoutException t) {
			 WebDriverManager.chromedriver().clearDriverCache().setup();
			 RunnerClass.failedReason = RunnerClass.failedReason + "," + "TimeOut Error";
			return false;
			
		}
        catch(Exception e) {}
        RunnerClass.driver.manage().timeouts().implicitlyWait(100,TimeUnit.SECONDS);
        RunnerClass.wait = new WebDriverWait(RunnerClass.driver, Duration.ofSeconds(100));
        return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("Login failed");
		    RunnerClass.failedReason = RunnerClass.failedReason+","+ "Login failed";
			return false;
		}
	}
	
	public static boolean closeDriver() {
		 try
		  {
			  RunnerClass.driver.quit();
			  logger.info("Browser closed");
		  }
		 catch (TimeoutException t) {
			 WebDriverManager.chromedriver().clearDriverCache().setup();
			 RunnerClass.failedReason = RunnerClass.failedReason + "," + "TimeOut Error";
			return false;
			
		}
		  catch(Exception e1) {
			  logger.error("Browser closing failed");
			  e1.printStackTrace();
			  return false;
		  }
		return true;
	}
	
	
	public static File getLastModified() throws Exception
	{
		
	    File directory = new File(AppConfig.downloadFilePath);
	    File[] files = directory.listFiles(File::isFile);
	    long lastModifiedTime = Long.MIN_VALUE;
	    File chosenFile = null;

	    if (files != null)
	    { 
	        for (File file : files)
	        {
	            if (file.lastModified() > lastModifiedTime)
	            {
	                chosenFile = file;
	                lastModifiedTime = file.lastModified();
	            }
	        }
	    }

	    return chosenFile;
	}
	
	
	
	public static boolean downloadLeaseAgreement() {
		try {
			DatabaseClass.intermittentPopUp(RunnerClass.driver);
			RunnerClass.driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
	        RunnerClass.wait = new WebDriverWait(RunnerClass.driver, Duration.ofSeconds(2));
			RunnerClass.driver.findElement(Locators.notesAndDocs).click();
			Thread.sleep(2000);
			List<WebElement> documents = RunnerClass.driver.findElements(Locators.documentsList);
			boolean checkLeaseAgreementAvailable = false;
			 
			for(int i =0;i<documents.size();i++)
			{
				for(int j=0;j<AppConfig.LeaseAgreementFileNames.length;j++)
				{
				 if(documents.get(i).getText().startsWith(AppConfig.LeaseAgreementFileNames[j])&&!documents.get(i).getText().contains("Termination")&&!documents.get(i).getText().contains("_Mod"))//&&documents.get(i).getText().contains(AppConfig.getCompanyCode(RunnerClass.company)))
				 {
				 	documents.get(i).click();
				 	logger.info(documents.get(i).getText());
					checkLeaseAgreementAvailable = true;
					break;
				 }
				}
				if(checkLeaseAgreementAvailable == true)
					break;
			}
			if(checkLeaseAgreementAvailable==false)
			{
				logger.error("Unable to download Lease Agreement");
			    RunnerClass.failedReason =  RunnerClass.failedReason+","+ "Unable to download Lease Agreement";
				return false;
			}
		 
		}
		catch (TimeoutException t) {
				 WebDriverManager.chromedriver().clearDriverCache().setup();
				 RunnerClass.failedReason = RunnerClass.failedReason + "," + "TimeOut Error";
				return false;
				
		}
		catch(Exception e) {
			logger.error("Unable to download Lease Agreement");
		    RunnerClass.failedReason =  RunnerClass.failedReason+","+ "Unable to download Lease Agreement";
			return false;
		}
		
			 
		
		return true;
		
	}
	
	
	
	
	public static String convertDate(String dateRaw) throws Exception
	{
		try
		{
		SimpleDateFormat format1 = new SimpleDateFormat("MMMM dd, yyyy");
	    SimpleDateFormat format2 = new SimpleDateFormat("MM/dd/yyyy");
	    Date date = format1.parse(dateRaw.trim().replaceAll(" +", " "));
	    logger.info(format2.format(date));
		return format2.format(date).toString();
		}
		catch(Exception e)
		{
			try
			{
			SimpleDateFormat format1 = new SimpleDateFormat("MMMM dd yyyy");
		    SimpleDateFormat format2 = new SimpleDateFormat("MM/dd/yyyy");
		    Date date = format1.parse(dateRaw.trim().replaceAll(" +", " "));
		    logger.info(format2.format(date));
			return format2.format(date).toString();
			}
			catch(Exception e2)
			{
			  if(dateRaw.trim().replaceAll(" +", " ").split(" ")[1].contains("st")||dateRaw.trim().replaceAll(" +", " ").split(" ")[1].contains("nd")||dateRaw.trim().replaceAll(" +", " ").split(" ")[1].contains("th"))
				  dateRaw = dateRaw.trim().replaceAll(" +", " ").replace("st", "").replace("nd", "").replace("th", "");
			  try
				{
				SimpleDateFormat format1 = new SimpleDateFormat("MMMM dd yyyy");
			    SimpleDateFormat format2 = new SimpleDateFormat("MM/dd/yyyy");
			    Date date = format1.parse(dateRaw.trim().replaceAll(" +", " "));
			    logger.info(format2.format(date));
				return format2.format(date).toString();
				}
				catch(Exception e3)
				{
					try
					{
					SimpleDateFormat format1 = new SimpleDateFormat("MMMM dd,yyyy");
				    SimpleDateFormat format2 = new SimpleDateFormat("MM/dd/yyyy");
				    Date date = format1.parse(dateRaw.trim().replaceAll(" +", " "));
				    logger.info(format2.format(date));
					return format2.format(date).toString();
					}
					catch(Exception e4)
					{
						try
						{
						SimpleDateFormat format1 = new SimpleDateFormat("MMMM dd.yyyy");
					    SimpleDateFormat format2 = new SimpleDateFormat("MM/dd/yyyy");
					    Date date = format1.parse(dateRaw.trim().replaceAll(" +", " "));
					    logger.info(format2.format(date));
						return format2.format(date).toString();
						}
						catch(Exception e5)
						{
							try
							{
							SimpleDateFormat format1 = new SimpleDateFormat("M/dd/yyyy");
						    SimpleDateFormat format2 = new SimpleDateFormat("MM/dd/yyyy");
						    Date date = format1.parse(dateRaw.trim().replaceAll(" +", " "));
						    logger.info(format2.format(date));
							return format2.format(date).toString();
							}
							catch(Exception e6)
							{
							
					return "";
							}
					}
				}
			}
		}
	}
	} 
	
	    public static String firstDayOfMonth(String date,int month) throws Exception 
	    {
	    	//String string = "02/05/2014"; //assuming input
	        DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	        Date dt = sdf .parse(date);
	        Calendar c = Calendar.getInstance();
	        c.setTime(dt);
	        //if(portfolioType=="MCH")
	        c.add(Calendar.MONTH, month);  //adding a month directly - gives the start of next month.
	        //else c.add(Calendar.MONTH, 2);
	        c.set(Calendar.DAY_OF_MONTH, 01);
	        String firstDate = sdf.format(c.getTime());
	        //logger.info("First date " + firstDate);
	        return firstDate;
	    }
	    public static String getCurrentDateTime()
	    {
	    	currentTime ="";
	    	 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
			 LocalDateTime now = LocalDateTime.now();  
			// System.out.println(dtf.format(now));
			 currentTime = dtf.format(now);
			 return currentTime;
	    }
	    public static String lastDateOfTheMonth(String date) throws Exception
	    {
	    	//String date =RunnerClass.convertDate("January 1, 2023");
	    	LocalDate lastDayOfMonth = LocalDate.parse(date, DateTimeFormatter.ofPattern("M/dd/yyyy"))
	    	       .with(TemporalAdjusters.lastDayOfMonth());
	    	String newDate = new SimpleDateFormat("MM/dd/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(lastDayOfMonth.toString()));
	    	 //logger.info("Last Date of Month "+newDate);
	    	return newDate;
	    }
	    public static String monthDifference(String date1, String date2) throws Exception
	    {
	    	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
	        Date firstDate = sdf.parse(date1);
	        Date secondDate = sdf.parse(date2);

	        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
	                .appendPattern("MM/dd/yyyy")
	                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
	                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
	               // .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
	                .toFormatter();
	        
           String x =  Duration.between( LocalDate.parse(date1,formatter),  LocalDate.parse(date2,formatter)).toString();
			return "";
	    }
	    
		public static boolean compareBeforeDates(String date1, String date2)
		{
			try
			{
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				logger.info(sdf.parse(date1).before(sdf.parse(date2)));
				if(sdf.parse(date1).before(sdf.parse(date2)))
					return true;
				else return false;
			}
			catch(Exception e)
			{
			return false;
			}
		}
	    
	    
	    public static String getCurrentDate()
	    {
	    	currentTime ="";
	    	 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");  
			 LocalDateTime now = LocalDateTime.now();  
			// System.out.println(dtf.format(now));
			 currentTime = dtf.format(now);
			 return currentTime;
	    }
	    public static boolean onlyDigits(String str)
	    {
			str = str.replace(",", "").replace(".", "").trim();
			if(str=="")
				return false;
			int numberCount =0;
	        for (int i = 0; i < str.length(); i++) 
	        {
	            if (Character.isDigit(str.charAt(i))) 
	            {
	            	numberCount++;
	            	//return true;
	            }
	        }
	        if(numberCount==str.length())
	        return true;
	        else
	        return false;
	    }

	    
	    public static void handleAlerts() throws InterruptedException {
	        try {
	        	 Thread.sleep(1000);
	            Alert alert = RunnerClass.driver.switchTo().alert();
	            alert.accept();
	        } catch (NoAlertPresentException ignored) {
	            // Alert not present, continue with the rest of the code
	        }
	    }
	    
	    public static WebElement findElementWithWait(By locator) {
	        try {
	            return RunnerClass.wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	        } catch (Exception e)
	        {
	            return null;
	        }
	    }
	    public static void generateLogs() {
	    	try {
	    		File directoryPath = new File(AppConfig.logFilePath);
	    		LocalDate todayDate = LocalDate.now();
	    		LocalDate yesterdayDate = todayDate.minusDays(1);
	    		LocalDate dayBeforeYesterdayDate = todayDate.minusDays(2);
		    	String currentDate = todayDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		    	String yesterdayDateString = yesterdayDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		    	String dayBeforeYesterdayDateString = dayBeforeYesterdayDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		        //List of all files and directories
		        File[] files = directoryPath.listFiles();
		        for(File file:files) {
		          if(file.getName().contains(currentDate) || file.getName().contains(yesterdayDateString) || file.getName().contains(dayBeforeYesterdayDateString)) {
		        	  continue;
		          }
		          else {
		        	  file.delete();
		          }
		        }
	    	}
	    	catch(Exception e) {
	    		System.out.print("Error in deleting log file");
	    	}
	    	 // creates pattern layout
	        PatternLayout layout = new PatternLayout();
	        String conversionPattern = "%-7p %d [%t] %c %x - %m%n";
	        layout.setConversionPattern(conversionPattern);
	 
	        // creates console appender
	        ConsoleAppender consoleAppender = new ConsoleAppender();
	        consoleAppender.setLayout(layout);
	        consoleAppender.activateOptions();
	 
	        // creates file appender
	        FileAppender fileAppender = new FileAppender(); 
	        fileAppender.setFile(AppConfig.logFilePath+"\\"+"PWLog - "+LocalDate.now()+".txt");
	        fileAppender.setLayout(layout);
	        fileAppender.activateOptions();
	 
	        // configures the root logger
	        Logger rootLogger = Logger.getRootLogger();
	        rootLogger.setLevel(Level.DEBUG);
	        rootLogger.addAppender(consoleAppender);
	        rootLogger.addAppender(fileAppender);
	 
	        // creates a custom logger and log messages
	        logger = Logger.getLogger(RunnerClass.class);
	    }
	    
	
}
