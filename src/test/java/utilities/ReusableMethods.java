package utilities;
import com.github.javafaker.Faker;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import pages.AutomationExcercise;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
public class ReusableMethods {

        public static Faker faker = new Faker();
        public static String passWord = faker.internet().password();
        public static String eMail = faker.internet().emailAddress();
        public static void makeRegistration(){
            AutomationExcercise aE = new AutomationExcercise();
            //6. Enter name and email address

            String name = faker.name().firstName();
            aE.signupName.sendKeys(name);
            aE.signupEmail.sendKeys(eMail);

            //7. Click 'Signup' button
            aE.signUpButton.click();

            //8. Verify that 'ENTER ACCOUNT INFORMATION' is visible
            Assert.assertTrue(aE.enterAccountInformation.isDisplayed());

            //9. Fill details: Title, Name, Email, Password, Date of birth
            aE.popUpAdsInEveryPage.click();

            aE.passWord.sendKeys(passWord + Keys.PAGE_DOWN);

            Select selectDay = new Select(aE.daysOfBirth);
            selectDay.selectByValue(ConfigReader.getProperty("selectDay"));

            Select selectMonth = new Select(aE.monthOfBirth);
            selectMonth.selectByValue(ConfigReader.getProperty("selectMonth"));

            Select selectYear = new Select(aE.yearOfBirth);
            selectYear.selectByValue(ConfigReader.getProperty("selectYear"));

            //10. Select checkbox 'Sign up for our newsletter!'

            aE.popUpAdsInEveryPage.click();

            aE.signUpForOurNewsletter.click();

            //11. Select checkbox 'Receive special offers from our partners!'

            aE.receiveSpecialOffersInsideSignPage.click();

            //12. Fill details: First name, Last name, Company, Address, Address2, Country, State, City, Zipcode, Mobile Number

            aE.firstNameInSignPage.sendKeys(name);

            aE.lastNameInSignPage.sendKeys(faker.name().lastName());

            aE.companyInSignPage.sendKeys(faker.company().name());

            aE.address1InSignPage.sendKeys(faker.address().fullAddress());

            aE.address2InSignPage.sendKeys(faker.address().secondaryAddress() + Keys.PAGE_DOWN);

            Select selectCountry = new Select(aE.countryInSignPage);
            selectCountry.selectByValue(ConfigReader.getProperty("selectCountry"));

            ReusableMethods.bekle(3);

            aE.stateInSignPage.sendKeys(faker.address().country());

            aE.cityInSignPage.sendKeys(faker.address().city());

            aE.zipcodeInSignPage.sendKeys(faker.address().zipCode());

            aE.mobileNumberInSignPage.sendKeys(faker.phoneNumber().cellPhone());

            //13. Click 'Create Account button'
            aE.createAccountButtonInSignPage.click();

            //14. Verify that 'ACCOUNT CREATED!' is visible
            Assert.assertTrue(aE.accountCreatedAfterSignPage.isDisplayed());

            //15. Click 'Continue' button
            aE.accountContinueButtonAfterSignPage.click();
            aE.logOutButton.click();
        }
    public static String getScreenshot(String name) throws IOException {
        // naming the screenshot with the current date to avoid duplication
        String date = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        // TakesScreenshot is an interface of selenium that takes the screenshot
        TakesScreenshot ts = (TakesScreenshot) Driver.getDriver();
        File source = ts.getScreenshotAs(OutputType.FILE);
        // full path to the screenshot location
        String target = System.getProperty("user.dir") + "/target/Screenshots/" + name + date + ".png";
        File finalDestination = new File(target);
        // save the screenshot to the path given
        FileUtils.copyFile(source, finalDestination);
        return target;
    }

    //========Switching Window=====//
    public static void switchToWindow(String targetTitle) {
        String origin = Driver.getDriver().getWindowHandle();
        for (String handle : Driver.getDriver().getWindowHandles()) {
            Driver.getDriver().switchTo().window(handle);
            if (Driver.getDriver().getTitle().equals(targetTitle)) {
                return;
            }
        }
        Driver.getDriver().switchTo().window(origin);
    }

    //========Hover Over=====//
    public static void hover(WebElement element) {
        Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(element).perform();
    }

    //==========Return a list of string given a list of Web Element====////
    public static List<String> stringListeCevir(List<WebElement> list) {
        List<String> elemTexts = new ArrayList<>();
        for (WebElement el : list) {
            if (!el.getText().isEmpty()) {
                elemTexts.add(el.getText());
            }
        }
        return elemTexts;
    }

    //========Returns the Text of the element given an element locator==//
    public static List<String> getElementsText(By locator) {
        List<WebElement> elems = Driver.getDriver().findElements(locator);
        List<String> elemTexts = new ArrayList<>();
        for (WebElement el : elems) {
            if (!el.getText().isEmpty()) {
                elemTexts.add(el.getText());
            }
        }
        return elemTexts;
    }
          //   HARD WAIT WITH THREAD.SLEEP
         //   waitFor(5);  => waits for 5 second
    public static void bekle(int saniye) {
        try {
            Thread.sleep(saniye * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //===============Explicit Wait==============//
    public static WebElement waitForVisibility(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOf(element));
    }
    public static WebElement waitForVisibility(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    public static WebElement waitForClickablility(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    public static WebElement waitForClickablility(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    public static void clickWithTimeOut(WebElement element, int timeout) {
        for (int i = 0; i < timeout; i++) {
            try {
                element.click();
                return;
            } catch (WebDriverException e) {
                bekle(1);
            }
        }
    }
    public static void waitForPageToLoad(long timeout) {
        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };
        try {
            System.out.println("Waiting for page to load...");
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
            wait.until(expectation);
        } catch (Throwable error) {
            System.out.println(
                    "Timeout waiting for Page Load Request to complete after " + timeout + " seconds");
        }
    }
    //======Fluent Wait====//
    public static WebElement fluentWait(final WebElement webElement, int timeout) {
        //FluentWait<WebDriver> wait = new FluentWait<WebDriver>(Driver.getDriver()).withTimeout(timeinsec, TimeUnit.SECONDS).pollingEvery(timeinsec, TimeUnit.SECONDS);
        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(Driver.getDriver())
                .withTimeout(Duration.ofSeconds(3))//Wait 3 second each time
                .pollingEvery(Duration.ofSeconds(1));//Check for the element every 1 second
        WebElement element = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return webElement;
            }
        });
        return element;
    }
}