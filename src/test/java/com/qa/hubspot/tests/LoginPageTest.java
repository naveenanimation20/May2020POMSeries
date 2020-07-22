package com.qa.hubspot.tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.hubspot.base.BaseTest;
import com.qa.hubspot.testlisteners.TestAllureListener;
import com.qa.hubspot.utils.Constants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

@Epic("Epic - 101 : design login page features...")
@Feature("US - 201: desgin login page title, sign up link and login form modules...")
@Listeners(TestAllureListener.class)
public class LoginPageTest extends BaseTest{

	// TestNG -- unit testing framework
	// PreConditions ---> Test Cases(steps) (Act vs Exp) -- Assertions ---> Tear
	// Down
	// @BeforeTest ---> @Test --Assertions --> @AfterTest
	// launchBrowser, url --- > title test --> close the browser

	@Description("verify sign up link on login page...")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority=1)
	public void verifySignUpLinkTest() {
		Assert.assertEquals(loginPage.isSignUpLinkExist(), true);
	}

	@Description("verify login page title login page...")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority=2)
	public void verifyLoginPageTitleTest() {
		System.out.println("running login page title test...");
		String title = loginPage.getLoginPageTitle();
		System.out.println("Login page title is: " + title);
		Assert.assertEquals(title, Constants.LOGIN_PAGE_TITLE);
	}

	@Description("verify user is able to login page...")
	@Severity(SeverityLevel.BLOCKER)
	@Test(priority=3)
	public void loginTest() {
		loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	

}
