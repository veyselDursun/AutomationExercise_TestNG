package tests;

import com.aventstack.extentreports.ExtentReports;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AutomationExcercise;
import utilities.Driver;
import utilities.ReusableMethods;
import utilities.TestBaseRapor;

public class TestCase_02 extends TestBaseRapor {
    @Test
    public void test01(){

        extentTest=extentReports.createTest("Test02","Smoke test");
        AutomationExcercise aE = new AutomationExcercise();
        //Test Case 2: Login User with correct email and password

        //2. Navigate to url 'http://automationexercise.com'
        Driver.getDriver().get("http://automationexercise.com");

        //3. Verify that home page is visible successfully
        Assert.assertTrue(aE.isHomePageVisible.isDisplayed());
        extentTest.pass("home page is visible successfully");

        //4. Click on 'Signup / Login' button
        aE.signUpLogInButton.click();

        //5. Verify 'Login to your account' is visible
        Assert.assertTrue(aE.loginToYourAccountTextElement.isDisplayed());
        extentTest.pass("'Login to your account' is visible");
        ReusableMethods.makeRegistration();
        
        //6. Enter correct email address and password
        aE.loginToYourAccountWiaEmail.sendKeys(ReusableMethods.eMail);
        aE.loginToYourAccountWiaPassword.sendKeys(ReusableMethods.passWord);

        //7. Click 'login' button
        aE.loginToYourAccountWiaLoginButton.click();

        //8. Verify that 'Logged in as username' is visible
        Assert.assertTrue(aE.loggedInAsUsername.isDisplayed());
        extentTest.pass("'Logged in as username' is visible");

        //9. Click 'Delete Account' button
        aE.deleteAccount.click();

        //10. Verify that 'ACCOUNT DELETED!' is visible
        Assert.assertTrue(aE.accountDeletedAfterClickDeleteAccount.isDisplayed());
        extentTest.pass("'ACCOUNT DELETED!' is visible");
        Driver.closeDriver();

    }
}