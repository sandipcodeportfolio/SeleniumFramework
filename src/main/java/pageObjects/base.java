package pageObjects;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class base {

	public static WebDriver driver;
	public Properties prop;

	public WebDriver initializeDriver() throws IOException {

		prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"/src/main/java/resources/data.properties");
		//System.out.println(System.getProperty("user.dir")+"/src/main/java/resources/data.properties");
		System.out.println("Base is Working");
		prop.load(fis);
		String browserName = prop.getProperty("browser");

		if (browserName.equals("chrome")) {
			// setup Chrome WebDriver
			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"/src/main/java/resources/chromedriver");
			ChromeOptions options = new ChromeOptions();
			//options.addArguments("--headless");
			options.setPageLoadStrategy(PageLoadStrategy.NONE);
			driver = new ChromeDriver(options);
			System.out.println("Driver Intialize");

		} else if (browserName.equals("firefox")) {
			// setup Firefox WebDriver
			System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"/src/main/java/resources/geckodriver.exe");
			driver = new FirefoxDriver();

		} else if (browserName.equals("IE")) {
			// setup IE WebDriver
			driver = new InternetExplorerDriver();

		}

		driver.manage().window().maximize();
		// Add implicit wait time
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		return driver;
	}

	public void getScreenshot(String result) throws IOException {
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src, new File(System.getProperty("user.dir")+"/src/main/screenshots/" + result + "screenshot.png"));
	}

}
