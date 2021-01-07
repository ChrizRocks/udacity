package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import sun.rmi.runtime.Log;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {


	@LocalServerPort
	private int port;

	private static WebDriver driver;

	public String baseURL;
	public static String username;
	public static String password;
	public static String firstname;
	public static String lastname;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		username = "ChrizRocks";
		password = "veryBadPassword012345";
		firstname = "Christian";
		lastname = "Rathgeber";
	}

	@AfterAll
	static void afterAll(){
		driver.quit();
	}

	@BeforeEach
	public void beforeEach() {
		//driver = new ChromeDriver();
		baseURL = "http://localhost:"+this.port;
	}

	@AfterEach
	public void afterEach() {
		//driver.quit();
		//driver=null;
	}

	@Test
	public void getLoginPage() {
		driver.get(baseURL +"/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test //Write a test that verifies that an unauthorized user can only access the login and signup pages.
	public void testUnauthorizedUserAccess(){
		driver.get(baseURL+"/home");
		assertEquals("Login",driver.getTitle());
		driver.get(baseURL+"/upload");
		assertEquals("Login",driver.getTitle());
		driver.get(baseURL+"/credential/delete/");
		assertEquals("Login",driver.getTitle());
		driver.get(baseURL+"/addCredentials");
		assertEquals("Login",driver.getTitle());
	}

	/**
	 * Has to be run independently, since other Test already does the signup.
	 * @throws InterruptedException
	 */
	@Test
	public void testUserSignupAndLogin() throws InterruptedException {
		driver.get(baseURL+"/signup");

		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(firstname,lastname,username,password);
		List<WebElement> elementsList = driver.findElements(By.id("error-msg"));
		if(elementsList.size()==0){
			assertEquals("You successfully signed up! Please continue to the login page.",driver.findElement(By.id("success-msg")).getText());
		}else {
			assertEquals("The username already exists.",driver.findElement(By.id("error-msg")).getText());
		}
		//Thread.sleep(3000);
		driver.get(baseURL+"/login");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username,password);
		//Thread.sleep(3000);
		assertEquals("Home",driver.getTitle());
		driver.findElement(By.id("logoutButton")).submit();

		//Thread.sleep(1000);
		//driver.get(baseURL+"/home");
		assertNotEquals("Home",driver.getTitle());
		//driver.get(baseURL+"/login");
		driver.get(baseURL+"/home");
		//Thread.sleep(3000);
		assertNotEquals("Home",driver.getTitle());
		//Thread.sleep(3000);
	}

	/**
	 *  Write Tests for Note Creation, Viewing, Editing, and Deletion.
	 *
	 *     Write a test that creates a note, and verifies it is displayed.
	 *     Write a test that edits an existing note and verifies that the changes are displayed.
	 *     Write a test that deletes a note and verifies that the note is no longer displayed.
	 */

//	public void signupAndLogin(){
//		driver.get(baseURL+"/signup");
//		SignupPage signupPage = new SignupPage(driver);
//		signupPage.signup(firstname,lastname,username,password);
//		driver.get(baseURL+"/login");
//		LoginPage loginPage = new LoginPage(driver);
//		loginPage.login(username,password);
//	}

	@Test
	public void testNoteCreation() throws InterruptedException {
		driver.get(baseURL+"/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(firstname,lastname,username,password);
		driver.get(baseURL+"/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username,password);
		//Thread.sleep(2000);
		//HomePage homePage = new HomePage();
		//homePage.navNotes(driver);
		//homePage.createNote(driver,"To-Do","-write seleniumtests");
//		signupAndLogin();
//		String allWindowHandles = driver.getWindowHandles().toString();
//		Set<String> allCapturesWindowHandles;
//		String currentWindowHandle = null;
//		System.out.println("###########################");
//		System.out.println(allWindowHandles);
//		System.out.println("###########################");
//		currentWindowHandle = driver.getWindowHandle();
//		//allCapturesWindowHandles.add(currentWindowHandle);
//		Thread.sleep(2000);
//		System.out.println("\nFirst Tab Title: " + driver.getTitle());
//		System.out.println("First Tab URL: "+driver.getCurrentUrl());

//		String oldTab = driver.getWindowHandle();
//
//		System.out.println("BraaaaaaAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA: "+oldTab);
		driver.findElement(By.id("nav-notes-tab")).click();

		Thread.sleep(3000);
		//driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElement(By.id("newNoteButton")).click();

		//Thread.sleep(2000);
		driver.findElement(By.id("note-title")).sendKeys("Title");
		driver.findElement(By.id("note-description")).sendKeys("Note description");
		driver.findElement(By.id("saveNoteButton")).click();
		driver.get(baseURL+"/home");
		//Thread.sleep(2000);
		driver.findElement(By.id("nav-notes-tab")).click();
	//	Thread.sleep(3000);

//		assertEquals(driver.findElement(By.id("note-title")),"Title");
//		assertEquals(driver.findElement(By.id("note-description")),"Note description");
//		ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
//		System.out.println("#######################################");
//		System.out.println(newTab.size());
//		System.out.println("#######################################");
//		newTab.remove(oldTab);
//		driver.switchTo().window(newTab.get(0));
//		Thread.sleep(3000);
		//driver.findElement(By.id("nav-notes-tab")).submit();
		//driver.findElement(By.id(""))
		//Thread.sleep(6000);
//		id="nav-notes-tab
	}

//	@Test
//	public void testReRegister() throws InterruptedException {
//		String username = "ChrizRocks";
//		String password = "veryBadPassword012345";
//		String firstname = "Christian";
//		String lastname = "Rathgeber";
//
//		driver.get(baseURL+"/signup");
//
//		SignupPage signupPage = new SignupPage(driver);
//		signupPage.signup(firstname,lastname,username,password);
//		Thread.sleep(3000);
//		assertEquals("The username already exists.",driver.findElement(By.id("error-msg")).getText());
//	}

}
