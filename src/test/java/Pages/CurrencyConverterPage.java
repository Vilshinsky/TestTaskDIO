package Pages;

import Common.Teh;
import com.google.common.base.Predicate;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander on 03.02.2016.
 */
public class CurrencyConverterPage {

    public static String inputBaseCurrency = "//*[@id='base_currency_input']";
    public static String hoveredValueBaseCurrency = "//div[contains(@class,'list_item_hover')]/span[3]";
    public static String inputBaseAmount = "//*[@id='base_amount_input']";
    public static String valueAverageRate = "//*[@id='bidAskBidAvg']";

    public static String buttonCalendar = "//*[@id='end_date_input']";
    public static String buttonPevMonth = "//*[contains(@class,'navLastMonth')]";
    public static String buttonFriday = "//tr[2]/td[6]";
    public static String buttonSaturday = "//tr[2]/td[7]";
    public static String anyWeekendDayInCalendar = "//td[contains(@class,'calendarWeekend') " +
            "and @class!='calendarWeekendInvalid' and @class!='calendarWeekendInvalid calendarNextMonth']";

    public static ArrayList<String> getListOfCurrencies() {
        ArrayList<String> currencies = new ArrayList<String>();
        List<WebElement> elements = Teh.driver.findElements(By.xpath("//*[@id='scroll-innerBox-1']/div/span[3]"));
        for(int i = 0; i <= elements.size() - 1; i++) {
            currencies.add(elements.get(i).getText());
        }
        return currencies;
    }

    public static void waitPreloaderDisappearing() {
        for(int i = 0; i < 300; i++) {
            if(Teh.waitXpathElement("//*[@id='preloader']").isDisplayed()) {
                Teh.waitMsec(100);
            }
            break;
        }
    }

    public static void everyCurrencyMatcher() {
        Teh.waitXpathElement("//*[@id='scroll-innerBox-2']/div[230]/span[2]");
        List<WebElement> elements = Teh.driver.findElements(By.xpath("//*[@id='scroll-innerBox-2']/div/span[2]"));
        for(WebElement element : elements) {
            String currency = element.getAttribute("innerHTML");
            String initial = Teh.waitXpathElement(valueAverageRate).getText();
            Teh.waitXpathElement(inputBaseCurrency).sendKeys(currency);
            confirmChosenCurrency();
            String amount = Teh.waitXpathElement(inputBaseAmount).getAttribute("value");
            String rate = Teh.waitXpathElement(valueAverageRate).getText();
            for(int i = 0; i < 50; i++) {
                if(!initial.equals(rate)) {
                    break;
                }
                Teh.waitMsec(100);
                rate = Teh.waitXpathElement(valueAverageRate).getText();
            }
            Assert.assertEquals("Currency: " + currency + ". Rate: " + rate + ". Amount: " + amount + ".", rate, amount);
        }
    }

    public static void fillInputByDesiredCurrencyName(String currency) {
        Teh.waitXpathElement(inputBaseCurrency).sendKeys(currency);
    }
    public static void confirmChosenCurrency() {
        Teh.waitXpathElement(hoveredValueBaseCurrency).click();
        waitPreloaderDisappearing();
    }
    public static void assertThatAmountCorrespondsToRate() {
        String amount = Teh.waitXpathElement(inputBaseAmount).getAttribute("value");
        String rate = Teh.waitXpathElement(valueAverageRate).getText();
        Assert.assertEquals("Rate: " + rate + " Amount: " + amount, rate, amount);
    }

    public static void clickOnCalendar() {
        Teh.waitXpathElement(buttonCalendar).click();
        waitPreloaderDisappearing();
    }
    public static void choosePreviousMonth() {
        Teh.waitXpathElement(buttonPevMonth).click();
        waitPreloaderDisappearing();
    }
    private static String initial;
    public static void chooseFriday() {
        Teh.waitXpathElement(buttonFriday).click();
        waitPreloaderDisappearing();
        initial = Teh.waitXpathElement(inputBaseAmount).getAttribute("value");
    }
    private static String current;
    public static void chooseWeekend() {
        Teh.waitXpathElement(buttonSaturday).click();
        waitPreloaderDisappearing();
        for(int i = 0; i < 300; i++) {
            if(Teh.waitXpathElement("//span[contains(text(),'Friday')]").isDisplayed()) {
                break;
            }
            Teh.waitMsec(100);
        }
        current = Teh.waitXpathElement(inputBaseAmount).getAttribute("value");
    }
    public static void assertThatRateValueHaventChanged() {
        Assert.assertEquals(initial,current);
        System.out.println(initial + ", " + current);
    }
}
