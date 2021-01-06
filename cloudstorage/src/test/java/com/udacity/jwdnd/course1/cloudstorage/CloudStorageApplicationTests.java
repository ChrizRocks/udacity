package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import sun.rmi.runtime.Log;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private static WebDriver driver;

	public String baseURL;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}

	@AfterAll
	static void afterAll(){
		driver.quit();
		driver=null;
	}

	@BeforeEach
	public void beforeEach() {
		baseURL = "http://localhost:"+this.port;
	}

	@AfterEach
	public void afterEach() {

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


	@Test
	public void testUserSignupAndLogin() throws InterruptedException {
		String username = "ChrizRocks";
		String password = "veryBadPassword012345";
		String firstname = "Christian";
		String lastname = "Rathgeber";

		driver.get(baseURL+"/signup");

		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(firstname,lastname,username,password);

		assertEquals("You successfully signed up! Please continue to the login page.",driver.findElement(By.id("success-msg")).getText());
		//Thread.sleep(3000);
		driver.get(baseURL+"/login");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username,password);
		//Thread.sleep(3000);
		assertEquals("Home",driver.getTitle());
		driver.findElement(By.id("logoutButton")).submit();
		//Thread.sleep(3000);
		//driver.get(baseURL+"/home");
		assertNotEquals("Home",driver.getTitle());
		//driver.get(baseURL+"/login");
		driver.get(baseURL+"/home");
		Thread.sleep(3000);
		assertNotEquals("Home",driver.getTitle());
		//Thread.sleep(3000);
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
