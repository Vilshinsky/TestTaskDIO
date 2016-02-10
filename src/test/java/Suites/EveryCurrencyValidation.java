package Suites;

import Common.BaseTest;
import Common.Teh;
import Pages.CurrencyConverterPage;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * Created by Alexander on 10.02.2016.
 */
public class EveryCurrencyValidation extends BaseTest {

    @Test
    public void everyCurrencyShouldCorrespondToRate1() {
        CurrencyConverterPage.everyCurrencyMatcher();
    }

}
