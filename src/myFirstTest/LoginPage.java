package myFirstTest;

import static org.testng.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginPage extends elements {
	
	private static Logger log=Logger.getLogger(LoginPage.class);

	@BeforeTest
	public void initUtil() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		PageFactory.initElements(driver, elements.class);
		sAssert = new SoftAssert();
		url = "https://s1.demo.opensourcecms.com/wordpress/wp-admin/";
	}

	// @AfterTest
	public void cleanUp() {
		driver.quit();
	}

	@Test
	public void TC01() throws InterruptedException {
		driver.get("https://s1.demo.opensourcecms.com/wordpress/wp-login.php");
		Thread.sleep(1000);
		driver.manage().window().maximize();

		userName.click();
		userName.sendKeys("opensourcecms");
		passwd.click();
		passwd.sendKeys("opensourcecms");
		loginBtn.click();

		Assert.assertTrue(driver.getTitle().contains("Dashboard"), "User can't login");

	}

	@Test(priority = -1)
	public void TC02() {
		driver.get("https://s1.demo.opensourcecms.com/wordpress/wp-login.php");
		driver.manage().window().maximize();
		userName.click();
		userName.sendKeys("opensourcecms");
		passwd.click();
		passwd.sendKeys("123123123");
		loginBtn.click();
		Assert.assertFalse(driver.getTitle().contains("Dashboard"), "User logged in using Incorrect Details");
	}

	@Test
	public void TC03() {
		driver.get(url);
		if (!posts.isDisplayed())
			driver.findElement(By.xpath("//span[@class='collapse-button-icon']")).click();
		posts.click();
		adNewPost.click();
		driver.findElement(By.xpath("//button[@aria-label=\"Disable tips\"]")).click();
		title.sendKeys("Testing Shatstra" + System.currentTimeMillis());
		editor.click();
		driver.findElement(By
				.xpath("//p[@class='block-editor-rich-text__editable editor-rich-text__editable wp-block-paragraph']"))
				.sendKeys("Hellloooo........");
		fontType.click();
		driver.findElement(By.xpath("//span[contains(text(),'Medium')]")).click();
		compRange.click();
		compRange.clear();
		compRange.sendKeys("25");
	}

	@Test
	public void TC04() throws InterruptedException {
		String ttl = title.getText();
		parentHandle = driver.getWindowHandle();
		preview.click();
		handles = driver.getWindowHandles();
		for (String window : handles) {
			if (!window.equals(parentHandle))
				driver.switchTo().window(window);
		}
		if (!eTitle.getText().equals(ttl))
			Reporter.log("Incorrect preview in Post");
		driver.close();
		driver.switchTo().window(parentHandle);
		publish.click();
		// driver.findElement(By.xpath("//button[@aria-label=\"Disable
		// tips\"]")).click();
		driver.findElement(By.xpath(
				"//body/div[@id='wpwrap']/div[@id='wpcontent']/div[@id='wpbody']/div[@id='wpbody-content']/div/div[@id='editor']/div/div/div/div/div/div/div/div/button[1]"))
				.click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[contains(text(),'Post published.')]//a[contains(text(),'View Post')]"))
				.click();
		Assert.assertTrue(eTitle.getText().equals(ttl), "Incorrect Post published");
	}

	@Test
	public void TC05() throws InterruptedException {
		driver.get(url);
		if (!posts.isDisplayed())
			driver.findElement(By.xpath("//span[@class='collapse-button-icon']")).click();
		posts.click();

		firstPost.click();
		// title.click();
		title.sendKeys(Keys.CONTROL + "a");
		title.sendKeys(Keys.DELETE);
		title.sendKeys("Testing Shatstra" + System.currentTimeMillis());
		String ttl = title.getText();
		update.click();
		Thread.sleep(2000);
		parentHandle = driver.getWindowHandle();
		preview.click();

		handles = driver.getWindowHandles();
		for (String window : handles) {
			if (!window.equals(parentHandle))
				driver.switchTo().window(window);
		}
		Assert.assertTrue(eTitle.getText().equals(ttl), "Incorrect preview in Post");
		driver.close();
		driver.switchTo().window(parentHandle);
	}

	@Test
	public void TC06() throws InterruptedException {
		driver.get(url);
		if (!posts.isDisplayed())
			driver.findElement(By.xpath("//span[@class='collapse-button-icon']")).click();
		posts.click();
		categories.click();
		newName.clear();
		temp = "First Test" + System.currentTimeMillis();

		newName.sendKeys(temp);

		newSlug.sendKeys("TestingShastra.com");
		Select select = new Select(driver.findElement(By.xpath("//select[@id='parent']")));
		select.selectByValue("1");
		newTextArea.click();
		newTextArea.sendKeys("Its a demo test category.");
		driver.findElement(By.xpath("//input[@id='submit']")).click();
		Thread.sleep(500);
		List<WebElement> l1 = driver.findElements(By.xpath("//tbody[@id='the-list']//tr"));
		Boolean flag = false;

		for (int i = 1; i <= l1.size(); i++) {

			if (driver.findElement(By.xpath("//tr[" + i + "]//td[1]//strong[1]//a[1]")).getText().substring(2)
					.equals(temp)) {
				flag = true;
			}
		}
		Assert.assertTrue(flag);
	}

	@Test
	public void TC07() {
		driver.get(url);
		if (!posts.isDisplayed())
			driver.findElement(By.xpath("//span[@class='collapse-button-icon']")).click();
		posts.click();
		categories.click();
		String t = driver.findElement(By.xpath("//tr[1]//td[1]//strong[1]//a[1]")).getText();
		driver.findElement(By.xpath("//input[@id='tag-search-input']")).sendKeys(t);
		driver.findElement(By.xpath("//input[@id='search-submit']")).click();
		Assert.assertEquals(driver.findElement(By.xpath("//a[@class='row-title']")).getText(), t,
				"Category cannot be Searched with correct input");

	}

	@Test
	public void TC08() {
		driver.get(url);
		if (!posts.isDisplayed())
			driver.findElement(By.xpath("//span[@class='collapse-button-icon']")).click();
		posts.click();
		categories.click();
		// String
		// t=driver.findElement(By.xpath("//tr[20]//td[1]//strong[1]//a[1]")).getText();
		driver.findElement(By.xpath("//input[@id='tag-search-input']")).sendKeys("Bhushan");
		driver.findElement(By.xpath("//input[@id='search-submit']")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//td[contains(text(),'No categories found.')]")).isDisplayed(),
				"Category Searched with incorrect input");

	}

	@Test
	public void TC09() throws InterruptedException {
		driver.get(url);
		if (!posts.isDisplayed())
			driver.findElement(By.xpath("//span[@class='collapse-button-icon']")).click();
		posts.click();
		tags.click();
		String t = "Test Demo" + System.currentTimeMillis();
		newName.sendKeys(t);

		newSlug.sendKeys("TestingShastra.com");
		newTextArea.click();
		newTextArea.sendKeys("Its a demo test category.");
		driver.findElement(By.xpath("//input[@id='submit']")).click();

		Thread.sleep(500);
		List<WebElement> l1 = driver.findElements(By.xpath("//tbody[@id='the-list']//tr"));
		Boolean flag = false;

		for (int i = 1; i <= l1.size(); i++) {

			if (driver.findElement(By.xpath("//tr[" + i + "]//td[1]//strong[1]//a[1]")).getText().equals(t)) {
				flag = true;
			}
		}
		Assert.assertTrue(flag);

	}

	@Test
	public void TC10() {
		driver.get(url);
		if (!posts.isDisplayed())
			driver.findElement(By.xpath("//span[@class='collapse-button-icon']")).click();
		posts.click();
		tags.click();
		String t = driver.findElement(By.xpath("//tr[1]//td[1]//strong[1]//a[1]")).getText();
		driver.findElement(By.xpath("//input[@id='tag-search-input']")).sendKeys(t);
		driver.findElement(By.xpath("//input[@id='search-submit']")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//a[@class='row-title']")).getText().equals(t),
				"Tag cannot be Searched with correct input");
	}

	@Test
	public void TC11() {
		driver.get(url);
		if (!posts.isDisplayed())
			driver.findElement(By.xpath("//span[@class='collapse-button-icon']")).click();
		posts.click();
		tags.click();
		driver.findElement(By.xpath("//input[@id='tag-search-input']")).sendKeys("Bhushan");
		driver.findElement(By.xpath("//input[@id='search-submit']")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//td[contains(text(),'No tags found.')]")).isDisplayed(),
				"Tag Searched with incorrect input");

	}

	@Test
	public void TC12() {
		driver.get(url);
		if (!posts.isDisplayed())
			driver.findElement(By.xpath("//span[@class='collapse-button-icon']")).click();
		posts.click();
		tags.click();
		String t = driver.findElement(By.xpath("//tr[1]//td[1]//strong[1]//a[1]")).getText();
		driver.findElement(By.xpath("//input[@id='tag-search-input']")).sendKeys(t);
		driver.findElement(By.xpath("//input[@id='search-submit']")).click();
		sAssert.assertTrue(driver.findElement(By.xpath("//a[@class='row-title']")).getText().equals(t),
				"Tag cannot be Searched with correct input");

		driver.findElement(By.xpath("//tr[1]//th[1]//input[1]")).click();
		driver.findElement(By.xpath("//span[@class='delete']")).click();

		driver.switchTo().alert().accept();
		driver.findElement(By.xpath("//input[@id='search-submit']")).click();
		sAssert.assertTrue(driver.findElement(By.xpath("//td[contains(text(),'No tags found.')]")).isDisplayed(),
				"Tag not deleted");
		sAssert.assertAll();
	}

	@Test
	public void TC13() {
		driver.get(url);
		if (!posts.isDisplayed())
			driver.findElement(By.xpath("//span[@class='collapse-button-icon']")).click();
		posts.click();
		categories.click();
		String t = driver.findElement(By.xpath("//tr[2]//td[1]//strong[1]//a[1]")).getText();
		driver.findElement(By.xpath("//tr[2]//td[1]//strong[1]//a[1]")).click();
		driver.findElement(By.xpath("//a[@class='delete']")).click();
		driver.switchTo().alert().accept();
		driver.findElement(By.xpath("//input[@id='tag-search-input']")).sendKeys(t);
		driver.findElement(By.xpath("//input[@id='search-submit']")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//td[contains(text(),'No categories found.')]")).isDisplayed(),
				"Category not Deleted");
	}

	@Test
	public void TC14() throws InterruptedException {
		driver.get(url);
		if (!posts.isDisplayed())
			driver.findElement(By.xpath("//span[@class='collapse-button-icon']")).click();
		posts.click();
		driver.findElement(By.xpath("//tr[1]//th[1]//input[1]")).click();
		driver.findElement(By.xpath("//span[3]//a[1]")).click();
		Assert.assertTrue(
				driver.findElement(By.xpath("//div[@id='message']//p")).getText().contains("moved to the Trash"),
				"Post not deleted");
	}

	@Test
	public void TC15() {
		driver.get(url);
		pages.click();
		String t = driver.findElement(By.xpath("//tr[1]//td[1]//strong[1]//a[1]")).getText();
		driver.findElement(By.xpath("//tr[1]//td[1]//strong[1]//a[1]")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//textarea[@placeholder='Add title']")).getText().equals(t),
				"Incorrect page opend");
	}

	@Test
	public void TC16() {
		driver.get(url);
		pages.click();
		String t = driver.findElement(By.xpath("//tr[1]//td[1]//strong[1]//a[1]")).getText();
		driver.findElement(By.xpath("//input[@id='post-search-input']")).sendKeys(t);
		driver.findElement(By.xpath("//input[@id='search-submit']")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//a[@class='row-title']")).getText().equals(t),
				"Page not searched");
	}

	@Test
	public void TC17() throws InterruptedException {
		driver.get(url);
		pages.click();
		adNewPost.click();
		// if(driver.findElement(By.xpath("//button[@aria-label=\"Disable
		// tips\"]")).isEnabled())
		// driver.findElement(By.xpath("//button[@aria-label=\"Disable
		// tips\"]")).click();
		String ttl = "Testing Shatstra" + System.currentTimeMillis();
		title.sendKeys(ttl);
		editor.click();
		driver.findElement(By
				.xpath("//p[@class='block-editor-rich-text__editable editor-rich-text__editable wp-block-paragraph']"))
				.sendKeys("Hellloooo........");
		fontType.click();
		driver.findElement(By.xpath("//span[contains(text(),'Medium')]")).click();
		compRange.click();
		compRange.clear();
		compRange.sendKeys("25");

		parentHandle = driver.getWindowHandle();
		preview.click();
		handles = driver.getWindowHandles();
		for (String window : handles) {
			if (!window.equals(parentHandle))
				driver.switchTo().window(window);
		}

		sAssert.assertTrue(eTitle.getText().equalsIgnoreCase(ttl), "Incorrect preview in Page");
		driver.close();
		driver.switchTo().window(parentHandle);
		publish.click();
		// driver.findElement(By.xpath("//button[@aria-label=\"Disable
		// tips\"]")).click();
		driver.findElement(By.xpath(
				"//body/div[@id='wpwrap']/div[@id='wpcontent']/div[@id='wpbody']/div[@id='wpbody-content']/div/div[@id='editor']/div/div/div/div/div/div/div/div/button[1]"))
				.click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[contains(text(),'Page published.')]//a[contains(text(),'View Page')]"))
				.click();

		sAssert.assertTrue(eTitle.getText().equalsIgnoreCase(ttl), "Incorrect Page published");
		sAssert.assertAll();
	}

	// @Test
	public void TC18() {
		driver.get(url);
		pages.click();

	}

	@Test
	public void TC19() {
		driver.get(url);
		String t = driver.findElement(By.xpath(
				"//body/div[@id='wpwrap']/div[@id='wpcontent']/div[@id='wpadminbar']/div[@id='wp-toolbar']/ul[@id='wp-admin-bar-top-secondary']/li[@id='wp-admin-bar-my-account']/a[1]"))
				.getText();
		driver.findElement(By.xpath(
				"//body/div[@id='wpwrap']/div[@id='wpcontent']/div[@id='wpadminbar']/div[@id='wp-toolbar']/ul[@id='wp-admin-bar-top-secondary']/li[@id='wp-admin-bar-my-account']/a[1]"))
				.click();
		Assert.assertTrue(t.contains(driver.findElement(By.xpath("//input[@id='user_login']")).getText()),
				"Incorrect user displayed");
	}

	@Test
	public void TC20() throws InterruptedException {
		driver.get(url);
		users.click();
		adNewPost.click();
		temp = "Bhushan" + System.currentTimeMillis();
		driver.findElement(By.xpath("//input[@id='user_login']")).sendKeys(temp);
		driver.findElement(By.xpath("//input[@id='email']"))
				.sendKeys("Abc" + System.currentTimeMillis() + "@gmail.com");
		driver.findElement(By.xpath("//input[@id='first_name']")).sendKeys("Abc" + System.currentTimeMillis());
		driver.findElement(By.xpath("//input[@id='last_name']")).sendKeys("Xyz" + System.currentTimeMillis());
		driver.findElement(By.xpath("//button[contains(text(),'Show password')]")).click();
		Thread.sleep(500);
		driver.findElement(By.xpath("//input[@name='pass1-text']")).click();
		driver.findElement(By.xpath("//input[@name='pass1-text']")).clear();
		driver.findElement(By.xpath("//input[@name='pass1-text']")).sendKeys("Abc@123456987654123");
		Select select = new Select(driver.findElement(By.xpath("//select[@id='role']")));
		select.selectByValue("administrator");
		driver.findElement(By.xpath("//input[@name='createuser']")).click();

		List<WebElement> l1 = driver.findElements(By.xpath("//tbody[@id='the-list']//tr"));
		Boolean flag = false;
		for (int i = 1; i <= l1.size(); i++)
			if (driver.findElement(By.xpath("//tr[" + i + "]//td[1]//strong[1]//a[1]")).getText().equals(temp))
				flag = true;

		Assert.assertTrue(flag, "User can't created");
	}

	@Test
	public void TC21() throws InterruptedException {
		driver.get("https://s1.demo.opensourcecms.com/wordpress/wp-login.php");
		Thread.sleep(500);
		userName.clear();
		userName.sendKeys("opensourcecms");
		passwd.click();
		passwd.sendKeys("opensourcecms");
		loginBtn.click();
		Assert.assertTrue(driver.getTitle().contains("Dashboard"), "User can't login with correct details");
	}

	@Test
	public void TC22() throws InterruptedException {
		driver.get("https://s1.demo.opensourcecms.com/wordpress/wp-login.php");
		Thread.sleep(500);
		userName.clear();
		userName.sendKeys("123132132");
		passwd.click();
		passwd.sendKeys("Abc@123");
		loginBtn.click();
		Assert.assertFalse(driver.getTitle().contains("Dashboard"), "User can login with incorrect details");

	}

	// @Test
	public void TC23() {
		driver.get(url);
		users.click();

	}

	@Test
	public void TC24() {
		driver.get(url);
		comments.click();
		driver.findElement(By.xpath("//td[@class='author column-author']//strong")).click();

		Assert.assertEquals(driver.findElement(By.xpath("//h1[contains(text(),'Comments')]")).getText(), "Comments");

	}

	@Test
	public void TC25() {
		driver.get("http://www.goibibo.com");
		
		log.info("url opened http://www.goibibo.com");
		if(log.isInfoEnabled())
			System.out.println("INFO enabled");
		else
			System.out.println("INFO not enabled");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		log.info("Wait for 10 second");
		driver.findElement(By.id("gosuggest_inputSrc")).sendKeys("Mumbai (BOM)");
		log.info("Source city entered as Mumbai (BOM)");
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		log.info("Wait for 2 seconds");
		driver.findElement(By.xpath("//li//span[contains(text(),'Mumbai')]")).click();
		log.info("Click on city from displayed list");
		driver.close();
	}

}
