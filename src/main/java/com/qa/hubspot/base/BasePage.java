package com.qa.hubspot.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.hubspot.utils.OptionsManager;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BasePage {

	public WebDriver driver;
	public Properties prop;
	public OptionsManager optionsManager;

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

	/**
	 * This method is used to initialize the driver on the basis of given
	 * browser name
	 * 
	 * @param browserName
	 * @return driver
	 */
	public WebDriver init_driver(Properties prop) {

		String browserName = prop.getProperty("browser").trim();

		System.out.println("browser name is : " + browserName);
		if (driver == null) {
			optionsManager = new OptionsManager(prop);
			if (browserName.equalsIgnoreCase("chrome")) {
				WebDriverManager.chromedriver().setup();

				if (Boolean.parseBoolean(prop.getProperty("remote"))) {
					init_remoteWebDriver(browserName);
				} else {
					tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
				}
			} else if (browserName.equalsIgnoreCase("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				if (Boolean.parseBoolean(prop.getProperty("remote"))) {
					init_remoteWebDriver(browserName);
				} else {
					tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
				}

			} else if (browserName.equalsIgnoreCase("safari")) {
				WebDriverManager.getInstance(SafariDriver.class).setup();
				// driver = new SafariDriver();
				tlDriver.set(new SafariDriver());
			} else {
				System.out.println(browserName + " is not found, please pass the correct browser....");
			}

			getDriver().manage().deleteAllCookies();
			getDriver().manage().window().maximize();
			getDriver().manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

			getDriver().get(prop.getProperty("url"));

			return getDriver();
		} else {
			return getDriver();
		}
	}

	/**
	 * Run on remote machine - hub
	 * 
	 * @return
	 */
	public void init_remoteWebDriver(String browserName) {

		if (browserName.equalsIgnoreCase("chrome")) {
			DesiredCapabilities cap = DesiredCapabilities.chrome();
			cap.setCapability(ChromeOptions.CAPABILITY, optionsManager.getChromeOptions());
			try {
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), cap));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		} else if (browserName.equalsIgnoreCase("firefox")) {
			DesiredCapabilities cap = DesiredCapabilities.firefox();
			cap.setCapability(FirefoxOptions.FIREFOX_OPTIONS, optionsManager.getFirefoxOptions());
			try {
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), cap));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

		}

	}

	public static synchronized WebDriver getDriver() {
		return tlDriver.get();
	}

	/**
	 * @author NaveenKhunteta this method is used to initialize the properties
	 *         from config.properties file
	 * @return prop
	 */
	public Properties init_prop() {
		prop = new Properties();
		String path = null;
		String env = null;

		try {
			env = System.getProperty("env");
			System.out.println("Running on Environment: ---->" + env);
			if (env == null) {
				path = "./src/main/java/com/qa/hubspot/config/config.properties";
			} else {
				switch (env) {
				case "qa":
					path = "./src/main/java/com/qa/hubspot/config/qa.config.properties";
					break;
				case "dev":
					path = "./src/main/java/com/qa/hubspot/config/dev.config.properties";
					break;
				case "stage":
					path = "./src/main/java/com/qa/hubspot/config/stage.config.properties";
					break;
				default:
					System.out.println("Please pass the correct env value....");
					break;
				}
			}
			FileInputStream ip = new FileInputStream(path);
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;
	}

	// take screenshot:
	public String getScreenshot() {
		File src = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/screenshots/" + System.currentTimeMillis() + ".png";
		File destination = new File(path);
		try {
			FileUtils.copyFile(src, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}

}
