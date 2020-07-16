package com.qa.hubspot.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.hubspot.base.BasePage;
import com.qa.hubspot.utils.Constants;
import com.qa.hubspot.utils.ElementUtil;

public class HomePage extends BasePage{
	
	private WebDriver driver;
	ElementUtil elementUtil;
	
	By header = By.cssSelector("h1.dashboard-selector__title");
	By accountName = By.cssSelector("span.account-name ");
	
	By contactsPrimaryLink = By.id("nav-primary-contacts-branch");
	By contactsSecondaryLink = By.id("nav-secondary-contacts");
	
	public HomePage(WebDriver driver){
		this.driver = driver;
		elementUtil = new ElementUtil(this.driver);
	}
	
	public String getHomePageTitle(){
		return elementUtil.doGetPageTitleWithIsTitle(10, Constants.HOME_PAGE_TITLE);
	}
	
	public String getHomePageHeader(){
		if(elementUtil.doIsDisplayed(header))
			return elementUtil.doGetText(header);
		return null;
	}
	
	public String getLoggedInAccountName(){
		if(elementUtil.doIsDisplayed(accountName))
			return elementUtil.doGetText(accountName);
		return null;
	}
	
	public ContactsPage goToContactsPage(){
		clickOnContacts();
		return new ContactsPage(driver);
	}
	
	private void clickOnContacts(){
		elementUtil.waitForElementPresent(contactsPrimaryLink, 10);
		elementUtil.doClick(contactsPrimaryLink);
		elementUtil.clickWhenReady(contactsSecondaryLink, 5);
	}

}
