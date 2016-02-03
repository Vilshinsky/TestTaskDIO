package Common;

import com.google.common.base.Predicate;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Teh {
    public static WebDriver driver;

//Setup and quit of web driver

    public static void runDriver(String browser) {
        if (browser.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\chromedriver.exe");
            driver = new ChromeDriver();
        } else if (browser.equals("firefox")) {
            driver = new FirefoxDriver();
        } else if (browser.equals("ie")) {
            System.setProperty("webdriver.ie.driver", "C:\\Selenium\\IEDriverServer.exe");
            driver = new InternetExplorerDriver();
        }
    }

    public static void runDriverAndGo(String browser, int width, int height, String link) {
        runDriver(browser);
        setResolution(width, height);
        get(link);
    }

    public static void runDriverFullscreen(String browser) {
        runDriver(browser);
        setFullscreen();
    }

    public static void runDriverFullscreenAndGo(String browser, String link) {
        runDriver(browser);
        setFullscreen();
        get(link);
    }

    public static void droneDriver(String browser) {
        if (browser.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", "/usr/bin/google-chrome");
            driver = new ChromeDriver();
        } else if (browser.equals("firefox")) {
            System.setProperty("webdriver.firefox.driver", "/usr/bin/firefox");
            driver = new FirefoxDriver();
        }
    }

    public static void setResolution(int width, int height) {
        driver.manage().window().setSize(new Dimension(width, height));
    }

    public static void setFullscreen() {
        driver.manage().window().maximize();
    }

    public static void get(String url) {
        driver.get(url);
    }

    public static void quit() {
        driver.quit();
    }


    //Basic actions on page
    public static void scrollPage(int coord) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollBy(0," + coord + ")");
    }

    public static void waitSec(long seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void waitMsec(long msec) {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void waitJqueryInactive() {
        WebDriverWait waiter = new WebDriverWait(driver, 30, 500);
        waiter.until(new Predicate<WebDriver>() {
            public boolean apply(WebDriver input) {
                return (Boolean) ((JavascriptExecutor)driver).executeScript("return jQuery.active == 0");
            }
        });
    }

    public static boolean isElementPresent(By locator) {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        List<WebElement> elements = driver.findElements(locator);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        return elements.size() > 0 && elements.get(0).isDisplayed();
    }

    public static WebElement waitIdElement(String selector) {
        for (int i = 0; i < 300; i++) {
            if (driver.findElements(By.id(selector)).size() > 0) {
                for (int c = 0; c < 300; c++) {
                    if (driver.findElement(By.id(selector)).isDisplayed() &&
                            driver.findElement(By.id(selector)).isEnabled()) {
                        break;
                    }
                    waitMsec(100);
                }
                break;
            }
            waitMsec(100);
        }
        return driver.findElement(By.id(selector));
    }

    public static WebElement waitXpathElement(String selector) {
        for (int i = 0; i < 300; i++) {
            if (driver.findElements(By.xpath(selector)).size() > 0) {
                break;
            }
            waitMsec(100);
        }
        WebDriverWait waiter = new WebDriverWait(driver, 30, 500);
        waiter.until(new Predicate<WebDriver>() {
            public boolean apply(WebDriver input) {
                return (Boolean) ((JavascriptExecutor)driver).executeScript("return jQuery.active == 0");
            }
        });
        return driver.findElement(By.xpath(selector));
    }

    public static WebElement longWaitXpathElement(String selector) {
        for (int i = 0; i < 3000; i++) {
            if (driver.findElements(By.xpath(selector)).size() > 0) {
                break;
            }
            waitMsec(100);
        }
        return driver.findElement(By.xpath(selector));
    }

    public static WebElement waitCssElement(String selector) {
        for (int i = 0; i < 300; i++) {
            if (driver.findElements(By.cssSelector(selector)).size() > 0) {
                for (int c = 0; c < 300; c++) {
                    if (driver.findElement(By.cssSelector(selector)).isDisplayed() &&
                            driver.findElement(By.cssSelector(selector)).isEnabled()) {
                        break;
                    }
                    waitMsec(100);
                }
                break;
            }
            waitMsec(100);
        }
        return driver.findElement(By.cssSelector(selector));
    }

    public static WebElement waitIdElementNotExist(String selector) {
        for (int i = 0; i < 300; i++) {
            if (driver.findElements(By.id(selector)).size() == 0) {
                break;
            }
            waitMsec(100);
        }
        return null;
    }

    public static WebElement scrollXpathElement(String selector) {
        for (int i = 0; i < 300; i++) {
            if (driver.findElements(By.xpath(selector)).size() > 0) {
                for (int c = 0; c < 300; c++) {
                    if (driver.findElement(By.xpath(selector)).isDisplayed()) {
                        break;
                    }
                    scrollPage(50);
                    waitMsec(100);
                }
            }
        }
        return driver.findElement(By.xpath(selector));
    }

    public static boolean waitXpathElementNotExist(String selector) {
        for (int i = 0; i < 300; i++) {
            if (driver.findElements(By.xpath(selector)).size() == 0) {
                break;
            }
            waitMsec(100);
        }
        return true;
    }

    public static WebElement waitCssElementNotExist(String selector) {
        for (int i = 0; i < 300; i++) {
            if (driver.findElements(By.cssSelector(selector)).size() == 0) {
                break;
            }
            waitMsec(100);
        }
        return null;
    }

    public static void moveToIdElement(String selector) {
        new Actions(driver).moveToElement(driver.findElement(By.id(selector)));
    }

    public static void moveToXpathElement(String selector) {
        new Actions(driver).moveToElement(driver.findElement(By.xpath(selector)));
    }

    public static void moveToCssElement(String selector) {
        new Actions(driver).moveToElement(driver.findElement(By.cssSelector(selector)));
    }

    public static void moveToIdElementAndClickInCenter(String selector) {
        int width = Integer.parseInt(Teh.waitXpathElement(selector).getCssValue("width")) / 2;
        int height = Integer.parseInt(Teh.waitXpathElement(selector).getCssValue("height")) / 2;
        new Actions(driver).moveToElement(driver.findElement(By.id(selector)), width, height).click().build().perform();
    }

    public static void moveToXpathElementAndClickInCenter(String selector) {
        int width = Integer.parseInt(Teh.waitXpathElement(selector).getCssValue("width")) / 2;
        int height = Integer.parseInt(Teh.waitXpathElement(selector).getCssValue("height")) / 2;
        new Actions(driver).moveToElement(driver.findElement(By.xpath(selector)), width, height).click().build().perform();
    }

    public static void moveToCssElementAndClickInCenter(String selector) {
        int width = Integer.parseInt(Teh.waitXpathElement(selector).getCssValue("width")) / 2;
        int height = Integer.parseInt(Teh.waitXpathElement(selector).getCssValue("height")) / 2;
        new Actions(driver).moveToElement(driver.findElement(By.cssSelector(selector)), width, height).click().build().perform();
    }

    public static int stringToInteger(String selector) {
        int newInt = Integer.parseInt(Teh.waitXpathElement(selector).getText());
        return newInt;
    }

    //Wait until file is downloaded
    public static void fileIsDonwloaded(String path) {
        FluentWait<File> waiter = new FluentWait<File>(new File(path));
        waiter.withTimeout(30, TimeUnit.SECONDS);
        waiter.pollingEvery(500, TimeUnit.MILLISECONDS);
        waiter.until(new Predicate<File>() {
            @Override
            public boolean apply(File input) {
                return input.exists() && input.length() > 0;
            }
        });
    }

    //Alert handler and windows switcher
    public static boolean checkIsAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException ex) {
            return false;
        }
    }

    public static void handleAlert() {
        if (checkIsAlertPresent()) {
            waitMsec(800);
            Alert alert = driver.switchTo().alert();
            alert.accept();
        }
    }

    public void getNewWindow(String xpath) {
        String originalWindow = driver.getWindowHandle();
        final Set<String> oldWindowsSet = driver.getWindowHandles();
        waitXpathElement(xpath).click();
        String newWindow = (new WebDriverWait(driver, 30))
                .until(new ExpectedCondition<String>() {
                           public String apply(WebDriver driver) {
                               Set<String> newWindowsSet = driver.getWindowHandles();
                               newWindowsSet.removeAll(oldWindowsSet);
                               return newWindowsSet.size() > 0 ?
                                       newWindowsSet.iterator().next() : null;
                           }
                       }
                );
        driver.switchTo().window(newWindow);
        System.out.println("New window title: " + driver.getTitle());
        driver.close();
        driver.switchTo().window(originalWindow);
        System.out.println("Old window title: " + driver.getTitle());
    }

    //Output files generation
    public static void takeScreenshot(String commonname) {
        try {
            File scrFile =
                    ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new
                    File("C:\\Selenium\\Screenshots\\" + commonname + generateActualDateString("yyyyMMdd")
                    + "\\" + generateActualDateString("yyyyMMddHHmmss") + ".jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Date format examples: "yyyyMMdd", "MM.dd.yyyy", "yyyyMMddHHmmss"
    public static String generateActualDateString(String dateformat) {
        DateFormat dateFormat = new SimpleDateFormat(dateformat);
        Date date = new Date();
        String FinalDate = dateFormat.format(date);
        return FinalDate;
    }


    //Attaching files
    public static void setClipboardData(String path) {
        StringSelection stringSelection = new StringSelection(path);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
    }

    public void attachFile(String path) {
        setClipboardData(path);
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    //Actions with dropdown
    public static void selectIndexInDropdownXpath(String selector, int number) {
        waitXpathElement(selector);
        WebElement select = Teh.waitXpathElement(selector);
        Select sel = new Select(select);
        sel.selectByIndex(number);
    }

    public static void selectVisibleTextInDropdownXpath(String selector, String text) {
        waitXpathElement(selector);
        WebElement select = Teh.waitXpathElement(selector);
        Select sel = new Select(select);
        sel.selectByVisibleText(text);
    }

    public static String generateStringValue() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String FinalDate = dateFormat.format(date);
        return FinalDate;
    }
}
