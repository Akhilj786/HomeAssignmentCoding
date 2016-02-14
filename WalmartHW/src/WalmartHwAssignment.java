import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Keys;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;

import com.google.common.base.Function;

public class WalmartHwAssignment {
	static int TIME_TO_WAIT = 120;
	static int productSelectionAttempts = 0;

	static WebDriver driver;

	/*
	 * --------------------------------------------------------------------------
	 * ---------------------------------------------------- Custom Exception
	 * Class
	 * --------------------------------------------------------------------
	 * ----------------------------------------------------------
	 */
	private class HWAssignmentException extends Exception {
		public HWAssignmentException(String msg) {
			super(msg);
		}
	}

	/*
	 * --------------------------------------------------------------------------
	 * ---------------------------------------------------- List of all the
	 * search categories
	 * --------------------------------------------------------
	 * ----------------------------------------------------------------------
	 */
	String[] searchCategories = { "Auto & Tires", "Baby", "Beauty", "Books",
			"CellPhones", "Clothing", "ELectronics", "Food",
			"Gifts & Registry", "Health", "Home", "Home Improvement",
			"Household Essentials", "Jewelry", "Movies", "Music", "Office",
			"Party & Occassions", "Patio & Garden", "Pets", "Pharmacy",
			"PhotoCenter", "Sports & Outdoors", "Toys", "VideoGames", };

	// Flag that determines if the search string is a search Category
	boolean categoryFlag;

	/* Constructor to load WebDriver */
	WalmartHwAssignment() {
		System.setProperty("webdriver.chrome.driver",
				"./web_browser_drivers/chromedriver.exe");
		driver = new ChromeDriver();
		categoryFlag = false;
	}

	/*
	 * --------------------------------------------------------------------------
	 * --------------------------------------------------- Purpose : Loads a URL
	 * and verifies if the loaded web page has expected title Input Parameters :
	 * String appURL - URL of the web page to be loaded String expectedTitle -
	 * expected title of the web page to be loaded Return Parameters: N/A
	 * --------
	 * ------------------------------------------------------------------
	 * ----------------------------------------------------
	 */

	public void loadURL(String appURL, String expectedTitle)
			throws HWAssignmentException {
		driver.get(appURL);
		driver.manage().window().maximize();
		String actualTitle = driver.getTitle();

		if (expectedTitle.equals(actualTitle)) {
			System.out.println("Verification Successful - WebPage with url <"
					+ appURL + "> is successfully loaded");
		} else {
			throw new HWAssignmentException(
					"Verification Failed - Webpage Load Failed - Expected Page Title not found");

		}

	}

	/*
	 * --------------------------------------------------------------------------
	 * --------------------------------------------------- Purpose : Log in to
	 * web page Input Parameters : String username_text - username value as
	 * String String password_text - password value as String Return Parameters:
	 * N/A
	 * ----------------------------------------------------------------------
	 * --------------------------------------------------------
	 */

	public void login(String username_text, String password_text,
			String welcomeMessageText) throws HWAssignmentException {

		WebElement username = fluentWait(By.id("login-username"));
		username.clear();
		username.sendKeys(username_text);

		WebElement password = fluentWait(By.id("login-password"));
		password.clear();
		password.sendKeys(password_text);
		password.sendKeys(Keys.RETURN);

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		try {
			WebElement welcomeMessage = fluentWait(By
					.className("recent-orders-heading"));
			if (welcomeMessage.getText().contains(welcomeMessageText)) {
				System.out.println("Login attempt with username <"
						+ username_text + "> Successful.");

			} else {
				throw new HWAssignmentException(
						"Login attempt failed. Invalid Credentials !");
			}
		} catch (Exception ex) {
			throw new HWAssignmentException(
					"Login attempt failed with exception: " + ex.toString());
		}
	}

	/*
	 * --------------------------------------------------------------------------
	 * --------------------------------------------------- Purpose : Searches
	 * for a product Input Parameters : String productName - product to be
	 * searched Return Parameters: N/A
	 * ------------------------------------------
	 * --------------------------------
	 * ----------------------------------------------------
	 */

	public void searchProduct(String productName) throws HWAssignmentException {

		WebElement searchBox = fluentWait(By.name("query"));

		for (String category : searchCategories) {
			if (category.equalsIgnoreCase(productName)) {
				categoryFlag = true;
			}
		}

		try {
			searchBox.clear();
			searchBox.sendKeys(productName);
			searchBox.sendKeys(Keys.RETURN);
			System.out.println("Product results for search <" + productName
					+ "> are fetched");
			productSelectionAttempts = 0;
		} catch (StaleElementReferenceException serException) {
			searchBox = fluentWait(By.name("query"));
			searchBox.clear();
			searchBox.sendKeys(productName);
			searchBox.sendKeys(Keys.RETURN);
			System.out.println("Product results for search <" + productName
					+ "> are fetched");
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		} catch (Exception ex) {
			throw new HWAssignmentException(
					"Product search failed with exception: " + ex.toString());
		}
	}

	

	public String selectProduct(String selectedProductName,
			String searchInProdName) throws HWAssignmentException {
		String productId = "";
		WebElement element = null;
		try {
			if (categoryFlag) {
				categoryFlag = false;
				searchProduct(selectedProductName);
			}

			List<WebElement> entries = fluentWaitforList(By
					.className("js-product-title"));
			for (WebElement entry : entries) {
				element = entry;
				if (entry.getText().toString().toLowerCase()
						.contains(searchInProdName.toLowerCase())
						&& entry.getText().toString().toLowerCase()
								.contains(selectedProductName.toLowerCase())) {
					productId = entry
							.getAttribute("href")
							.toString()
							.substring(
									entry.getAttribute("href").toString()
											.lastIndexOf("/") + 1);
					entry.click();
					break;
				}
			}

			if (!productId.equals("")) {
				System.out.println("Product selection successful");
				return productId;
			}

			else
				throw new HWAssignmentException(
						"No product found with keywords specified");
		} catch (TimeoutException ex) {
			if (productSelectionAttempts <= 2)
				return selectProduct(selectedProductName, searchInProdName);
			else
				throw new HWAssignmentException(
						"Product selection attempts exceeded limit: "
								+ ex.toString());

		} catch (StaleElementReferenceException serException) {
			
			return selectProduct(selectedProductName, searchInProdName);
		} catch (Exception ex) {
			throw new HWAssignmentException(
					"Product selection failed with exception: " + ex.toString());
		}
	}

	/*
	 * --------------------------------------------------------------------------
	 * --------------------------------------------------- Purpose : Adds the
	 * selected product to Shopping Cart Input Parameters : N/A Return
	 * Parameters: N/A
	 * ----------------------------------------------------------
	 * --------------------------------------------------------------------
	 */

	public void addToCart() throws HWAssignmentException {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		try {
			WebElement addToCartButton = fluentWait(By
					.cssSelector("#WMItemAddToCartBtn"));
			String buttonText = "Add to Cart";
			if (buttonText.equals(addToCartButton.getText())) {
				System.out
						.println("Waiting for the product page to load completely");
				waitForLoad(driver);
				addToCartButton.click();
				System.out.println("'Add to cart' button click initiated");
			} else {
				throw new HWAssignmentException(
						"Failed to add product : 'Add to Cart' button not found");
			}

		} catch (Exception ex) {
			throw new HWAssignmentException(
					"Failed to add product to cart with exception: "
							+ ex.toString());
		}
	}

	/*
	 * --------------------------------------------------------------------------
	 * --------------------------------------------------- Purpose : Returns the
	 * number of items in the Shopping Cart Input Parameters : N/A Return
	 * Parameters: integer - number of items in the cart
	 * ------------------------
	 * --------------------------------------------------
	 * ----------------------------------------------------
	 */

	public int getCartItemsCount() {
		WebElement totalCartItemsString = fluentWait(By
				.cssSelector("#PACCheckoutBtn"));
		try {
			return Integer.parseInt(totalCartItemsString.getText().substring(
					11, 12));
		} catch (Exception ex) {
			return 0;
		}
	}

	/*
	 * --------------------------------------------------------------------------
	 * --------------------------------------------------- Purpose : Finds the
	 * presence of an element with a specific href Input Parameters : String
	 * elementHref - href of the element to be searched Return Parameters:
	 * boolean - true if element found, false if not found
	 * ----------------------
	 * ----------------------------------------------------
	 * ----------------------------------------------------
	 */

	public boolean findElementByHref(String elementHref)
			throws HWAssignmentException {
		try {
			waitForLoad(driver);
			WebElement element = fluentWait(By.cssSelector("a[href='/ip/"
					+ elementHref + "']"));
			return true;
		} catch (Exception ex) {
			return false;
		}

	}

	/*
	 * --------------------------------------------------------------------------
	 * --------------------------------------------------- Purpose : Locates a
	 * web element in web page Input Parameters : By locator - By.Id ,
	 * By.cssSelector, By.XPath etc along with their respective params Return
	 * Parameters: WebElement - Web Element Located
	 * ------------------------------
	 * --------------------------------------------
	 * ----------------------------------------------------
	 */

	public static WebElement fluentWait(final By locator) {
		WebElement element = null;
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(TIME_TO_WAIT, TimeUnit.SECONDS)
				.pollingEvery(5, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class,
						StaleElementReferenceException.class);

		element = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return driver.findElement(locator);
			}
		});

		return element;
	}

	/*
	 * --------------------------------------------------------------------------
	 * --------------------------------------------------- Purpose : Locates a
	 * list web element in web page Input Parameters : By locator - By.ClassName
	 * Return Parameters: WebElement - Web Element Located
	 * ----------------------
	 * ----------------------------------------------------
	 * ----------------------------------------------------
	 */

	public static List<WebElement> fluentWaitforList(final By locator) {
		List<WebElement> elements;
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(TIME_TO_WAIT, TimeUnit.SECONDS)
				.pollingEvery(5, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class,
						StaleElementReferenceException.class);

		elements = wait.until(new Function<WebDriver, List<WebElement>>() {
			public List<WebElement> apply(WebDriver driver) {
				return driver.findElements(locator);
			}
		});

		return elements;
	}

	/*
	 * --------------------------------------------------------------------------
	 * --------------------------------------------------- Purpose : Closes the
	 * web driver Input Parameters : N/A Return Parameters: N/A
	 * ------------------
	 * --------------------------------------------------------
	 * ----------------------------------------------------
	 */

	public void closeWebDriver() throws HWAssignmentException {
		try {
			driver.close();
		} catch (Exception ex) {
			throw new HWAssignmentException(
					"Failed to close web driver with exception: "
							+ ex.toString());
		}
	}

	/*
	 * --------------------------------------------------------------------------
	 * --------------------------------------------------- Purpose : Waits for
	 * the web page to complete loading Input Parameters : driver object Return
	 * Parameters: N/A
	 * ----------------------------------------------------------
	 * --------------------------------------------------------------------
	 */

	public void waitForLoad(WebDriver driver) {
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript(
						"return document.readyState").equals("complete");
			}
		};
		WebDriverWait wait = new WebDriverWait(driver, TIME_TO_WAIT);
		wait.until(pageLoadCondition);
	}

	/*
	 * --------------------------------------------------------------------------
	 * --------------------------------------------------- Purpose : Waits for
	 * the web page for a certain period of time unconditionally Input
	 * Parameters : int seconds - number of seconds to wait Return Parameters:
	 * N/A
	 * ----------------------------------------------------------------------
	 * --------------------------------------------------------
	 */

	public void implicitWait(int seconds) {
		driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
		System.out.println("Implicit delay of " + seconds
				+ " seconds on the webpage");
	}

}
