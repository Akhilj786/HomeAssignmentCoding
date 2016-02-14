import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;

import org.testng.Assert;

public class WalmartLabsHwTest {

	WalmartHwAssignment test;

	String username, password, welcomeMessage, pageTitle;
	String[] keywords, products, additionalSearches;

	Properties properties;
	InputStream input;

	@BeforeClass
	public void beforeClass() {
		try {
			System.out
					.println("--------------------------------------Loading Login Properties---------------------------");
			input = new FileInputStream(
					"./properties/loginProperties.properties");
			properties = new Properties();
			properties.load(input);
			username = properties.getProperty("username");
			password = properties.getProperty("password");

			System.out
					.println("--------------------------------------Loading Search Properties---------------------------");
			input = new FileInputStream(
					"./properties/searchProperties.properties");
			properties.load(input);
			keywords = properties.getProperty("keywords").toString().split(",");
			products = properties.getProperty("products").toString().split(",");
			additionalSearches = properties.getProperty("additionalSearches")
					.toString().split(",");
			welcomeMessage = properties.getProperty("welcomeMessage");
			pageTitle = properties.getProperty("pageTitle");
			System.out
					.println("--------------------------------Loading Runtime Properties Completed----------------------");

		} catch (Exception ex) {
			System.out
					.println("Failed to load Runtime Properties with the following exception: "
							+ ex.toString());
			System.exit(0);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					System.out
							.println("Failed to close the inputStream with the following exception: "
									+ e.toString());
				}
			}
		}
	}

	@BeforeMethod
	public void beforeMethod() {
		test = new WalmartHwAssignment();
	}

	@Test
	public void TestCase1() {
		try {
			System.out
					.println("--------------------------------TESTCASE1 EXECUTION STARTED--------------------------");
			test.loadURL("https://www.walmart.com/account/", pageTitle);
			test.login(username, password, welcomeMessage);
			test.searchProduct(keywords[0]);
			String productid = test.selectProduct(products[0],
					additionalSearches[0]);
			test.addToCart();

			Assert.assertEquals(test.getCartItemsCount(), 1);
			Assert.assertEquals(test.findElementByHref(productid), true);
		} catch (Exception ex) {
			System.out
					.println("Test could not be completed due to the following error: ");
			System.out.println(ex.toString());
		}

	}

	@Test
	public void TestCase2() {
		try {
			System.out
					.println("--------------------------------TESTCASE2 EXECUTION STARTED--------------------------");
			test.loadURL("https://www.walmart.com/account/", pageTitle);
			test.login(username, password, welcomeMessage);
			test.searchProduct(keywords[1]);
			String productid = test.selectProduct(products[1],
					additionalSearches[1]);
			test.addToCart();

			Assert.assertEquals(test.getCartItemsCount(), 1);
			Assert.assertEquals(test.findElementByHref(productid), true);
		} catch (Exception ex) {
			System.out
					.println("Test could not be completed due to the following error: ");
			System.out.println(ex.toString());
		}

	}

	@Test
	public void TestCase3() {
		try {
			System.out
					.println("--------------------------------TESTCASE3 EXECUTION STARTED--------------------------");
			test.loadURL("https://www.walmart.com/account/", pageTitle);
			test.login(username, password, welcomeMessage);
			test.searchProduct(keywords[2]);
			String productid = test.selectProduct(products[2],
					additionalSearches[2]);
			test.addToCart();

			Assert.assertEquals(test.getCartItemsCount(), 1);
			Assert.assertEquals(test.findElementByHref(productid), true);
		} catch (Exception ex) {
			System.out
					.println("Test could not be completed due to the following error: ");
			System.out.println(ex.toString());
		}

	}

	@Test
	public void TestCase4() {
		try {
			System.out
					.println("--------------------------------TESTCASE4 EXECUTION STARTED--------------------------");
			test.loadURL("https://www.walmart.com/account/", pageTitle);
			test.login(username, password, welcomeMessage);
			test.searchProduct(keywords[3]);
			String productid = test.selectProduct(products[3],
					additionalSearches[3]);
			test.addToCart();

			Assert.assertEquals(test.getCartItemsCount(), 1);
			Assert.assertEquals(test.findElementByHref(productid), true);
		} catch (Exception ex) {
			System.out
					.println("Test could not be completed due to the following error: ");
			System.out.println(ex.toString());
		}

	}

	@Test
	public void TestCase5() {
		try {
			System.out
					.println("--------------------------------TESTCASE5 EXECUTION STARTED--------------------------");
			test.loadURL("https://www.walmart.com/account/", pageTitle);
			test.login(username, password, welcomeMessage);
			test.searchProduct(keywords[4]);
			String productid = test.selectProduct(products[4],
					additionalSearches[4]);
			test.addToCart();

			Assert.assertEquals(test.getCartItemsCount(), 1);
			Assert.assertEquals(test.findElementByHref(productid), true);
		} catch (Exception ex) {
			System.out
					.println("Test could not be completed due to the following error: ");
			System.out.println(ex.toString());
		}
	}

	@AfterMethod
	public void afterMethod() {
		try {
			test.closeWebDriver();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		System.out
				.println("-------------------------------------------------------------------------------------");
	}

}
