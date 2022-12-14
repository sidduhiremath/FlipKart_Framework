package com.flipcart.pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.flipcart.utils.CommonMethods;

public class CheckItemAvailabilityPage extends CommonMethods{

	
	public CheckItemAvailabilityPage(){
		super();
		}
	
	@FindBy(xpath="//input[@name='q']")
	WebElement searchbar;
	
	@FindBy(xpath = "//button[@class='L0Z3Pu']")
	WebElement searchicon;
	
	@FindBy(xpath="//a[text()='Flights']")
	WebElement flighttab;
	
	@FindBy(xpath="//div[text()='One Way']")
	WebElement oneway;
	
	@FindBy(xpath = "//div[text()='Round Trip']")
	WebElement roundtrip;
	
//	@FindBy(xpath = "//input[@name='0-departcity']")
//	WebElement fromcity;
	
	@FindBy(name = "0-departcity")
	WebElement fromcity;
	
	@FindBy(name = "0-arrivalcity")
	WebElement tocity;
	
	@FindBy(xpath ="(//span[text()='Mumbai'])[1]")
	WebElement mumbaicity;
	
	@FindBy(xpath= "(//span[contains(text(),'Bang')])[2]")
	WebElement bangalorecity;
	
	@FindBy(xpath="//input[@name='0-datefrom']")
	WebElement departson;
	
	@FindBy(xpath = "//div[text()='Availability']")
	WebElement availabiltydropdown;
	
	@FindBy(xpath = "//div[text()='Include Out of Stock']")
	WebElement instockcheckbox;
	
	@FindBy(xpath = "//div[@class='_1YokD2 _3Mn1Gg']/div[2]/div/div[2]")
	WebElement clickonitem;
	
	@FindBy(xpath = "//div[text()='4★ & above']")
	WebElement customerrating;
	
	@FindBy(xpath = "(//select[@class='_2YxCDZ'])[1]")
	WebElement minprice;
	
	@FindBy(xpath = "//img[@title='Flipkart']")
	WebElement flipkarticon;
	
	public void clickOnSearchBar() throws Exception {
		web_Click(flipkarticon);
		Thread.sleep(3000);
		web_Click(searchbar);
	}
	

	public void enterItemName() throws Exception {
		Thread.sleep(3000);
		web_Send_Keys(searchbar,readFromExcel(2));
		getScreenShot("Entered ItemName");
	}


	public void clickOnSearchIcon() throws Exception {
		Thread.sleep(3000);
		web_Click(searchicon);
		getScreenShot("Item List");
	}
	
	public void clickOnFlights() throws Exception {
		Thread.sleep(3000);
		web_Click(flighttab);
	}
	
	public void selectTripType() throws Exception {
		Thread.sleep(3000);
		if((oneway.isSelected())) {
		web_Click(oneway);
		}
		else {
			Thread.sleep(3000);
			web_Click(roundtrip);
		}
	}
	
	public void selectFromandToCity() throws Exception {
		Thread.sleep(3000);
		web_Click(fromcity);
		Thread.sleep(3000);
		web_Send_Keys(fromcity, "Mumbai");
		web_Click(mumbaicity);
		Thread.sleep(3000);
		web_Click(tocity);
		web_Send_Keys(tocity, "Bang");
		Thread.sleep(3000);
		web_Click(bangalorecity);
		}
	
	public void selectDepartsOnDate() throws Exception {
		Thread.sleep(3000);
		web_Mouse_Hover(departson);
		Thread.sleep(3000);
		web_Click(departson);
		Thread.sleep(3000);
		String getdepartsonvalue=web_Get_Attribute(departson, "value");
		int departsondate = getMaxDaycount(getdepartsonvalue);
		Thread.sleep(3000);
		driver.findElement(By.xpath("//button[text()='"+(departsondate)+"']")).click();
		Thread.sleep(3000);
		getScreenShot("Flight Search");
		driver.navigate().back();
		Thread.sleep(5000);
	}
	
	public void checkItemAvailability() throws Exception {
		Thread.sleep(3000);
		web_Click(availabiltydropdown);
		Thread.sleep(3000);
		if(!(instockcheckbox.isSelected())) {
			System.out.println("Listed only In stock itmes");
		}
		else{
			System.out.println("Listed In and Out of stock itmes");
		}
	}
		
	public void selectCustomerRating() throws Exception {
		Thread.sleep(5000);
		web_Click(customerrating);
	}
	
	public void selectMinPrice() throws Exception{
		Thread.sleep(5000);
		web_Drop_Down(minprice, "₹500");
	}
	
	public void clickOnItem() throws Exception {
		Thread.sleep(2000);
		web_Mouse_Hover(clickonitem);
		Thread.sleep(3000);
		web_Click(clickonitem);
	}
	
}


