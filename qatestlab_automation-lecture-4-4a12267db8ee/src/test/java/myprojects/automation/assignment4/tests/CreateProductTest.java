package myprojects.automation.assignment4.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import myprojects.automation.assignment4.BaseTest;
import myprojects.automation.assignment4.model.ProductData;
import myprojects.automation.assignment4.utils.Properties;
import myprojects.automation.assignment4.utils.logging.CustomReporter;

public class CreateProductTest extends BaseTest {
	
	
	
	@DataProvider(name="loginInputs")
	public Object[][]getData(){
		return new String[][] {
			{"webinar.test@gmail.com", "Xcg7299bnSmMuRLp9ITw"}
		};
	}

    @Test(dataProvider="loginInputs")
    public void createNewProduct(String login, String password) {
    	CustomReporter.logAction("Srarted 1test - createNewProduct ");
    	actions.login(login, password);
    	WebElement catalogLinkElement = driver.findElement(By.id("subtab-AdminCatalog"));
		Actions builder = new Actions(driver);
    	builder.moveToElement(catalogLinkElement).build().perform();
    	
    	CustomReporter.logAction("Add 'товары' subtab");
    	driver.findElement(By.xpath("//li[@id='subtab-AdminProducts']")).click();
    	actions.waitForContentLoad(By.id("page-header-desc-configuration-add"));
    	
    	CustomReporter.logAction("Click add button");
		driver.findElement(By.id("page-header-desc-configuration-add")).click();
		actions.waitForContentLoad(By.id("form_step1_name_1"));
		
		
		ProductData newProduct = ProductData.generate();
		actions.createProduct(newProduct);
		
		/*String newName = newProduct.getName();
		String newQty = newProduct.getQty().toString();
		String newPrice = newProduct.getPrice();
		*/
		
	
		
		CustomReporter.logAction("Activate new product");
		driver.findElement(By.cssSelector(".switch-input")).click();
    	actions.waitForContentLoad(By.xpath("//div[@id='growls']"));
    	
 
    	CustomReporter.logAction("Click submit button");
    	driver.findElement(By.id("submit"));
    	actions.waitForContentLoad(By.linkText("Настройки обновлены."));
    	CustomReporter.logAction("Finished 1test - createNewProduct ");
    
        
    }
    
   @Test(dependsOnMethods = {"createNewProduct"})
    public void VerifyNewProduct() {
	    
		CustomReporter.logAction("Srarted 2test - VerifyNewProduct ");

		CustomReporter.logAction("Open wesite main page");
		driver.navigate().to(Properties.getBaseUrl());
		
		CustomReporter.logAction("Open the 'Все товары' link ");
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		// jse.executeScript("window.scrollBy(0, 200);");
		WebElement linkAllProducts = driver.findElement(By.partialLinkText("Все товары"));
		jse.executeScript("arguments[0].scrollIntoView(true);", linkAllProducts);
		linkAllProducts.click();
		
		CustomReporter.logAction("Assert that new item added to the product  list");
		jse.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//a[contains(text(), 'New Product')]")));
		boolean newItem = driver.findElement(By.xpath("//a[contains(text(), 'New Product')]")).isDisplayed();
		Assert.assertTrue(newItem, "New item was not added in the list");
		
		CustomReporter.logAction("Asserts that details of added item is matched");
		driver.findElement(By.xpath("//a[contains(text(), 'New Product')]")).click();
		
		
		// Cannot find the proper way how to implement recently generated Product Data into assertions =( - will be grateful for the possible solution 
		
		/*String newName = newProduct.getName();
		String newQty = newProduct.getQty().toString();
		String newPrice = newProduct.getPrice();
		*/
	
		
		/*Assert.assertEquals(driver.getTitle(), newName, "Product Name is not matched");
		Assert.assertEquals(driver.findElement(By.cssSelector(".product-quantities>span")).getText(), newQty + " Товары",  "Product Qty is not matched");
		Assert.assertEquals(driver.findElement(By.cssSelector(".current-price>span")).getText(), newPrice + " ₴", "Product Price is not matched");*/
		
		CustomReporter.logAction("Finished 2test - VerifyNewProduct ");
		
		
   }
    

    // TODO implement logic to check product visibility on website
}
