package Suites;

import Common.BaseTest;
import Pages.CurrencyConverterPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by Alexander on 03.02.2016.
 */
@RunWith(Parameterized.class)
public class CurrencyConverterVerification extends BaseTest {

    @Test
    public void currencyAmountShouldCorrespondToRate() {
        CurrencyConverterPage.fillInputByDesiredCurrencyName(currency);
        CurrencyConverterPage.confirmChosenCurrency();
        CurrencyConverterPage.assertThatAmountCorrespondsToRate();
    }

    @Test
    public void rateShouldntChangeOnWeekends() {
        CurrencyConverterPage.fillInputByDesiredCurrencyName(currency);
        CurrencyConverterPage.confirmChosenCurrency();
        CurrencyConverterPage.clickOnCalendar();
        CurrencyConverterPage.choosePreviousMonth();
        CurrencyConverterPage.chooseFriday();
        CurrencyConverterPage.clickOnCalendar();
        CurrencyConverterPage.chooseWeekend();
        CurrencyConverterPage.assertThatRateValueHaventChanged();
    }

    private String currency;

    @Parameterized.Parameters
    public static Collection testData() {
        return Arrays.asList(new Object[][]{
                {"USD"},
                {"EUR"},
                {"UAH"},
                {"GBP"},
                {"CNY"}
        });
    }

    public CurrencyConverterVerification(String currency) {
        this.currency = currency;
    }
}
