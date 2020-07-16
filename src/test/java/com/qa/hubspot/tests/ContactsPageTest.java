package com.qa.hubspot.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.hubspot.base.BaseTest;
import com.qa.hubspot.pages.ContactsPage;
import com.qa.hubspot.pages.HomePage;
import com.qa.hubspot.utils.Constants;
import com.qa.hubspot.utils.ExcelUtil;

public class ContactsPageTest extends BaseTest {

	HomePage homePage;
	ContactsPage contactsPage;

	@BeforeClass
	public void contactsPageSetup() {
		homePage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		contactsPage = homePage.goToContactsPage();
	}

	@Test(priority = 1)
	public void verifyContactsPageTitleTest() {
		String title = contactsPage.getContactsPageTitle();
		System.out.println("contacts page title is: " + title);
		Assert.assertEquals(title, Constants.CONTACTS_PAGE_TITLE);
	}

	@Test(priority = 2)
	public void verifyContactsPageHeader() {
		String headerValue = contactsPage.getContactsPageHeader();
		System.out.println("contacts page header is : " + headerValue);
		Assert.assertEquals(headerValue, Constants.CONTACTS_PAGE_HEADER);
	}

	
	@DataProvider()
	public Object[][] getContactsTestData(){
		Object data[][] = ExcelUtil.getTestData(Constants.CONTACTS_SHEET_NAME);
		return data;
	}
	
	@Test(priority = 3, dataProvider = "getContactsTestData", enabled=false)
	public void createNewContactTest(String emailid, String firstname, String lastname, String jobtitle) {
		contactsPage.createContact(emailid, firstname, lastname, jobtitle);
	}

}
