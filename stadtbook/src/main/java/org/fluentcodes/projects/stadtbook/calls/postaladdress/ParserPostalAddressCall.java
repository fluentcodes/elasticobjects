package org.fluentcodes.projects.stadtbook.calls.postaladdress;

import java.util.logging.Level;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.LoggerFactory;

public abstract class ParserPostalAddressCall extends PostalAddressCall {
  static {
    System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
    System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http", "info");
  }
  private WebDriver driver;
  public ParserPostalAddressCall() {
    super();
  }


  WebDriver getDriver() {
    return driver;
  }

  void closeDriver() {
    if (driver != null) {
      driver.close();
    }
  }

  public void initDriver() {
    closeDriver();
    FirefoxProfile profile = new FirefoxProfile();
    //profile.setPreference('javascript.enabled', false);
    FirefoxOptions options = new FirefoxOptions();
    options.addPreference("network.proxy.type", 0);
    options.setProfile(profile);
    options.setLogLevel(FirefoxDriverLogLevel.WARN);
    org.slf4j.Logger log = LoggerFactory.getLogger(ParserPostalAddressCall.class);
    System.setProperty("webdriver.gecko.driver", "/Users/werner/.cargo/bin/geckodriver");

    this.driver = new FirefoxDriver(options);
    ((FirefoxDriver)driver).setLogLevel(Level.INFO);
    driver.get("about:config");
    Actions act = new Actions(driver);
    act.sendKeys(Keys.RETURN).sendKeys("javascript.enabled").perform();
    /** try {
     Thread.sleep(1000);
     } catch (InterruptedException e) {
     e.printStackTrace();
     }
     act.sendKeys(Keys.TAB).sendKeys(Keys.RETURN).sendKeys(Keys.F5).perform();*/
  }
}
