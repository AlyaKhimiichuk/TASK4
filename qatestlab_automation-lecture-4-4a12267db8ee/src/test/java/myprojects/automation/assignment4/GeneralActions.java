package myprojects.automation.assignment4;


import myprojects.automation.assignment4.model.ProductData;
import myprojects.automation.assignment4.utils.Properties;
import myprojects.automation.assignment4.utils.logging.CustomReporter;


import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.TestException;

/**
 * Contains main script actions that may be used in scripts.
 */
public class GeneralActions {
    private WebDriver driver;
    private WebDriverWait wait;

    public GeneralActions(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 30);
    }

    /**
     * Logs in to Admin Panel.
     * @param login
     * @param password
     */
    private By emailField = By.id("email");
	private By passwordField = By.id("passwd");
	private By loginButton = By.name("submitLogin");
	
	
    public void login(String login, String password) {
        // TODO implement logging in to Admin Panel
    	CustomReporter.logAction("User login in to Admin Panel");
    	driver.navigate().to(Properties.getBaseAdminUrl());
    	driver.findElement(emailField).sendKeys(login);
    	driver.findElement(passwordField).sendKeys(password);
		driver.findElement(loginButton).click();
       // throw new UnsupportedOperationException();
    }

    
	public void createProduct(ProductData newProduct) {
        // TODO implement product creation scenario
		CustomReporter.logAction("Product creation scenario");
		String name = newProduct.getName();
		driver.findElement(By.id("form_step1_name_1")).sendKeys(name);
		
		String qty = newProduct.getQty().toString();
		driver.findElement(By.xpath("//li[@id='tab_step3']")).click();
		driver.findElement(By.id("form_step3_qty_0")).sendKeys(qty);
        //throw new UnsupportedOperationException();
		
		String price = newProduct.getPrice();
		driver.findElement(By.xpath("//li[@id='tab_step2']")).click();
		driver.findElement(By.id("form_step2_price")).sendKeys(price);
    }

    /**
     * Waits until page loader disappears from the page
     */
    public void waitForContentLoad(By locator) {
        // TODO implement generic method to wait until page content is loaded
    	CustomReporter.logAction("Waiting until page content is loaded");
    
    	try {
    		wait.until(ExpectedConditions.presenceOfElementLocated(locator)); 
    	    } catch (TimeoutException e) {
    	        System.out.println("Timed out after default time out");
    	    } catch (TestException e) {
    	        System.out.println("Unexpected error occurred, environment error");
    	        e.printStackTrace();
    	    }
    	}
   }

