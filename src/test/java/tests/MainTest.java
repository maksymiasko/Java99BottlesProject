package tests;

import testData.TestData;
import base.BaseTest;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.start.StartPage;

import java.util.ArrayList;
import java.util.List;

public class MainTest extends BaseTest {

    @Test
    public void testH1LogoHeader() {
        final String EXPECTED_H1_HEADER = "99 Bottles of Beer";

        String actualResultH1Header = openBaseURL().getH1LogoHeaderText();

        Assert.assertEquals(actualResultH1Header, EXPECTED_H1_HEADER);
    }

    @Test
    public void testH2LogoHeader() {
        final String EXPECTED_H2_HEADER = "one program in 1500 variations";

        String actualResultH2Header = openBaseURL().getH2LogoHeaderText();

        Assert.assertEquals(actualResultH2Header, EXPECTED_H2_HEADER);
    }

    @Test
    public void testTopMenuURLs_AreTheSameAs_FooterMenuURLs() {
        StartPage startPage = new StartPage(getDriver());

        List<String> mainMenuURLList = List.of(
                openBaseURL().getURL(),
                startPage.clickBrowseLanguagesMenu().getURL(),
                startPage.clickSearchLanguagesMenu().getURL(),
                startPage.clickTopListsMenu().getURL(),
                startPage.clickGuestbookMenu().getURL(),
                startPage.clickSubmitNewLanguageMenu().getURL());

        List<String> mainMenuFooterURLList = List.of(
                openBaseURL().getURL(),
                startPage.clickBrowseLanguagesFooterMenu().getURL(),
                startPage.clickSearchLanguagesFooterMenu().getURL(),
                startPage.clickTopListFooterMenu().getURL(),
                startPage.clickGuestBookFooterMenu().getURL(),
                startPage.clickSubmitNewLanguageFooterMenu().getURL());

        Assert.assertEquals(mainMenuURLList, mainMenuFooterURLList);
    }

    @Test
    public void testNavigationMenuLinksAmountAndTexts() {
        final int expectedAmount = 6;
        final String[] expectedTexts = {"Start", "Browse Languages", "Search Languages", "Top Lists", "Guestbook", "Submit new Language"};

        openBaseURL();
        StartPage startPage = new StartPage(getDriver());

        int actualAmount = startPage.getMenuLinksAmount();
        List<String> actualText = startPage.getMenuTextsInLowerCase();

        Assert.assertEquals(actualAmount, expectedAmount);
        for (int i = 0; i < actualText.size(); i++) {
            Assert.assertEquals(actualText.get(i), expectedTexts[i].toLowerCase());
        }
    }

    @Test(dataProviderClass = TestData.class, dataProvider = "MainTestData")
    public void testTopMenusNavigateToCorrespondingPages(
            int index, String menuText, String href, String url, String title) {

        StartPage startPage = openBaseURL();

        List<WebElement> topMenus = startPage.getTopMenuLinks();

        String oldURL = startPage.getURL();
        String oldTitle = startPage.getTitle();

        String actualUrl = startPage.clickMenu(index, topMenus).getURL();
        String actualTitle = startPage.clickMenu(index, topMenus).getTitle();

        if (index != 0) {
            Assert.assertNotEquals(actualUrl, oldURL);
            Assert.assertNotEquals(actualTitle, oldTitle);
        }
        Assert.assertEquals(actualUrl, url);
        Assert.assertEquals(actualTitle, title);
    }

    @Test(dataProviderClass = TestData.class, dataProvider = "MainTestData")
    public void testFooterMenusNavigateToCorrespondingPages(
            int index, String menuText, String href, String url, String title) {

        StartPage startPage = openBaseURL();

        List<WebElement> footerMenus = startPage.getFooterMenuLinks();

        String oldURL = startPage.getURL();
        String oldTitle = startPage.getTitle();

        String actualUrl = startPage.clickMenu(index, footerMenus).getURL();
        String actualTitle = startPage.clickMenu(index, footerMenus).getTitle();

        if (index != 0) {
            Assert.assertNotEquals(actualUrl, oldURL);
            Assert.assertNotEquals(actualTitle, oldTitle);
        }
        Assert.assertEquals(actualUrl, url);
        Assert.assertEquals(actualTitle, title);
    }
}
