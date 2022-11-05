package com.flipcart.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;

import com.flipcart.pages.ApplicationSignInPage;
import com.flipcart.pages.CheckItemAvailabilityPage;
import com.flipcart.pages.MoveItemToCartPage;
import com.flipcart.pages.ProceedToCheckoutPage;

public class TestBase {
	public static WebDriver driver;
	public static Properties property;
	public static FileInputStream fis;
	public static File file;
	public static FileOutputStream fout;
	public static Long PAGE_LOAD_TIMEOUT = 20L;
	public static Long IMPLICIT_WAIT = 10l;
	public static JavascriptExecutor executor;

	public static ApplicationSignInPage signinpage;
	public static CheckItemAvailabilityPage checkavailability;
	public static MoveItemToCartPage movetocart;
	public static ProceedToCheckoutPage checkout;

	public TestBase() {
		try {
			property = new Properties();
			FileInputStream ip = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\main\\java\\com\\flipcart\\config\\config.properties");
			property.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void initialization() {
		String browserName = property.getProperty("Browsername");
		try {

			if (browserName.equals("chrome")) {
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")
						+ "\\src\\main\\java\\com\\flipcart\\resource\\chromedriver.exe");
				driver = new ChromeDriver();
			} else if (browserName.equals("FF")) {
				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + "\\src\\main\\java\\com\\flipcart\\resource\\gickodriver.exe");
				driver = new FirefoxDriver();
			} else {
				System.out.println("Invalid Browser");
			}

			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().setScriptTimeout(3L, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
			driver.get(property.getProperty("Url"));
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			signinpage = PageFactory.initElements(driver, ApplicationSignInPage.class);
			checkavailability = PageFactory.initElements(driver, CheckItemAvailabilityPage.class);
			movetocart = PageFactory.initElements(driver, MoveItemToCartPage.class);
			checkout = PageFactory.initElements(driver, ProceedToCheckoutPage.class);
		}

	}

	public String readFromExcel(int rown) throws Exception {
		file = new File(System.getProperty("user.dir") + "\\src\\main\\java\\com\\flipcart\\testdata\\Controller.xlsx");
		FileInputStream fis = new FileInputStream(file);
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
		String cellStringValue = null;
		int rowCount = sheet.getPhysicalNumberOfRows();

		if (!(rown >= rowCount)) {
			XSSFRow row = sheet.getRow(rown);
			XSSFCell cell = row.getCell(1);
			cellStringValue = cellValue(cell);
		} else {
			System.out.println("No data found");
		}
		xssfWorkbook.close();
		return cellStringValue;
	}

	public static String cellValue(XSSFCell cell) {
		switch (cell.getCellType()) {
		case NUMERIC:
			return String.valueOf(cell.getNumericCellValue());
		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		case STRING:
			return cell.getStringCellValue();
		default:
			return cell.getStringCellValue();
		}
	}

	public void getScreenShot(String vString) throws IOException {
		String formattedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern(" ddMMYYYY_HHmmss"));
		TakesScreenshot ts = ((TakesScreenshot) driver);
		File source = ts.getScreenshotAs(OutputType.FILE);
		File DestFile = new File(System.getProperty("user.dir") + "\\ScreenShots\\" + vString + formattedDate + ".png");
		FileUtils.copyFile(source, DestFile);

	}

	public int getMaxDaycount(String getdepartsonvalue) {
		String selectmonth = null;
		if (Character.isDigit(getdepartsonvalue.charAt(1))) {
			selectmonth = getdepartsonvalue.substring(3, 6);
		} else {
			selectmonth = getdepartsonvalue.substring(2, 5);
		}
		int i = 31, day = 0;
		String months[] = { "Jan", "Mar", "May", "Jul", "Aug", "Oct", "Dec" };// Months with 31 days
		for (int j = 0; j < months.length; j++) {
			if ((selectmonth.equalsIgnoreCase(months[j]))) {
				day = i;
				break;
			} else {
				day = i - 1;
			}
		}
		LocalDate currentdate = LocalDate.now();
		int departsondate = currentdate.getDayOfMonth();
		if (departsondate < day) {
			departsondate = departsondate + 1;
		}
		return departsondate;
	}

}
