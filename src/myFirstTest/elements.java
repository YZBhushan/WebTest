package myFirstTest;
import java.util.Set;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.testng.asserts.SoftAssert;

public class elements {

	public static RemoteWebDriver driver;
	public static String parentHandle;
	public static Set<String> handles;
	public static String temp;
	public static SoftAssert sAssert;
	public static String url;
	
	@FindBy(xpath = "//input[@id='user_login']")
	public static WebElement userName; 
	@FindBy(xpath = "//input[@id='user_pass']")
	public static WebElement passwd;
	@FindBy(xpath = "//input[@id='wp-submit']")
	public static WebElement loginBtn;
	@FindBy(xpath = "//div[contains(text(),'Posts')]")
	public static WebElement posts;
	@FindBy(xpath = "//a[@class='page-title-action']")
	public static WebElement adNewPost;
	@FindBy(xpath = "//textarea[@id='post-title-0']")
	public static WebElement title;
	@FindBy(xpath = "//textarea[@class='editor-default-block-appender__content block-editor-default-block-appender__content']")
	public static WebElement editor;
	@FindBy(xpath = "//button[contains(text(),'Normal')]")
	public static WebElement fontType;
	@FindBy(xpath = "//input[@class='components-range-control__number']")
	public static WebElement compRange;
	@FindBy(xpath = "//a[contains(text(),'Preview')]")
	public static WebElement preview;
	@FindBy(xpath = "//h1[@class='entry-title']")
	public static WebElement eTitle;
	@FindBy(xpath = "//button[contains(text(),'Publish')]")
	public static WebElement publish; 
	@FindBy(xpath = "//tr[@id='post-1']//a[@class='row-title']")
	public static WebElement firstPost;
	@FindBy(xpath = "//button[contains(text(),'Update')]")
	public static WebElement update;
	@FindBy(xpath = "//a[contains(text(),'Categories')]")
	public static WebElement categories;
	@FindBy(xpath = "//input[@id='tag-name']")
	public static WebElement newName;
	@FindBy(xpath = "//input[@id='tag-slug']")
	public static WebElement newSlug;
	@FindBy(xpath = "//textarea[@id='tag-description']")
	public static WebElement newTextArea;
	@FindBy(xpath = "//a[contains(text(),'Tags')]")
	public static WebElement tags;
	@FindBy(xpath = "//div[contains(text(),'Pages')]")
	public static WebElement pages;
	@FindBy(xpath = "//div[contains(text(),'Users')]")
	public static WebElement users;
	@FindBy(xpath = "//div[contains(text(),'Comments')]")
	public static WebElement comments;
	
	
	
	

}
