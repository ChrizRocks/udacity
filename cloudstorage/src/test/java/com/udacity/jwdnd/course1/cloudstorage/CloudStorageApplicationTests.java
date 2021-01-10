package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {


	@LocalServerPort
	private int port;

	private static WebDriver driver;
	private WebDriverWait wait;

	public String baseURL;
	public static String username;
	public static String password;
	public static String firstname;
	public static String lastname;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
		driver = null;
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
		driver = new ChromeDriver();
		baseURL = "http://localhost:"+this.port;
		wait = new WebDriverWait(driver,10);
	}

	@AfterEach
	public void afterEach() {
		if (driver != null) {
			driver.quit();
		}
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
	public void testUserSignupAndLogin() {
		driver.get(baseURL+"/signup");

		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(firstname,lastname,username,password);
		List<WebElement> elementsList = driver.findElements(By.id("error-msg"));
		if(elementsList.size()==0){
			assertEquals("You successfully signed up! Please continue to the login page.",driver.findElement(By.id("success-msg")).getText());
		}else {
			assertEquals("The username already exists.",driver.findElement(By.id("error-msg")).getText());
		}

		driver.get(baseURL+"/login");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username,password);
		assertEquals("Home",driver.getTitle());

		((JavascriptExecutor) driver).executeScript("arguments[0].click();",driver.findElement(By.id("logoutButton")));
		assertNotEquals("Home",driver.getTitle());

		driver.get(baseURL+"/home");
		assertNotEquals("Home",driver.getTitle());
	}

	@Test
	public void testNoteCreationAndEdit() {
		driver.get(baseURL+"/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(firstname,lastname,username,password);

		driver.get(baseURL+"/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username,password);

		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("nav-notes-tab"))));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();",driver.findElement(By.id("nav-notes-tab")));
		assertEquals("By.id: nav-notes-tab",By.id("nav-notes-tab").toString());

		((JavascriptExecutor) driver).executeScript("arguments[0].click();",driver.findElement(By.id("newNoteButton")));
		((JavascriptExecutor) driver).executeScript("arguments[0].value='" + "Title" + "';", driver.findElement(By.id("note-title")));
		((JavascriptExecutor) driver).executeScript("arguments[0].value='" + "Note description" + "';", driver.findElement(By.id("note-description")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("saveNoteButton")));

		driver.get(baseURL+"/home");
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("nav-notes-tab"))));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();",driver.findElement(By.id("nav-notes-tab")));

		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("editButton"))));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();",driver.findElement(By.id("editButton")));

		assertEquals("Title",driver.findElement(By.id("noteTitleId")).getAttribute("innerHTML"));
		assertEquals("Note description",driver.findElement(By.id("noteTextId")).getAttribute("innerHTML"));

		((JavascriptExecutor) driver).executeScript("arguments[0].value='" + "New Title" + "';", driver.findElement(By.id("note-title")));
		((JavascriptExecutor) driver).executeScript("arguments[0].value='" + "New Note description" + "';", driver.findElement(By.id("note-description")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("saveNoteButton")));

		driver.get(baseURL+"/home");
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("nav-notes-tab"))));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();",driver.findElement(By.id("nav-notes-tab")));

		assertEquals("New Title",driver.findElement(By.id("noteTitleId")).getAttribute("innerHTML"));
		assertEquals("New Note description",driver.findElement(By.id("noteTextId")).getAttribute("innerHTML"));
	}

	@Test
	public void testNoteCreationAndDelete() {
		driver.get(baseURL+"/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(firstname,lastname,username,password);

		driver.get(baseURL+"/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username,password);

		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("nav-notes-tab"))));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();",driver.findElement(By.id("nav-notes-tab")));

		((JavascriptExecutor) driver).executeScript("arguments[0].click();",driver.findElement(By.id("newNoteButton")));
		((JavascriptExecutor) driver).executeScript("arguments[0].value='" + "Title" + "';", driver.findElement(By.id("note-title")));
		((JavascriptExecutor) driver).executeScript("arguments[0].value='" + "Note description" + "';", driver.findElement(By.id("note-description")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("saveNoteButton")));


		driver.get(baseURL+"/home");
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("nav-notes-tab"))));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();",driver.findElement(By.id("nav-notes-tab")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();",driver.findElement(By.id("deleteButton")));

		driver.get(baseURL+"/home");
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("nav-notes-tab"))));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();",driver.findElement(By.id("nav-notes-tab")));
		assertNotEquals("Title", driver.findElement(By.id("note-title")).getText());
	}

	@Test
	public void createCredentialAndDelete() {
		driver.get(baseURL+"/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(firstname,lastname,username,password);

		driver.get(baseURL+"/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username,password);

		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("nav-notes-tab"))));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();",driver.findElement(By.id("nav-credentials-tab")));

		((JavascriptExecutor) driver).executeScript("arguments[0].click();",driver.findElement(By.id("newCredentialButton")));
		((JavascriptExecutor) driver).executeScript("arguments[0].value='" + "http://www.google.de" + "';", driver.findElement(By.id("credential-url")));
		((JavascriptExecutor) driver).executeScript("arguments[0].value='" + username + "';", driver.findElement(By.id("credential-username")));
		((JavascriptExecutor) driver).executeScript("arguments[0].value='" + password + "';", driver.findElement(By.id("credential-password")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("credentialSubmit")));

		driver.get(baseURL+"/home");
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("nav-credentials-tab"))));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();",driver.findElement(By.id("nav-credentials-tab")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();",driver.findElement(By.id("deleteCredentialButton")));

		driver.get(baseURL+"/home");
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("nav-credentials-tab"))));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();",driver.findElement(By.id("nav-credentials-tab")));
		assertNotEquals("http://www.google.de", driver.findElement(By.id("credential-url")).getText());

	}

	@Test
	public void testCreateCredentialPasswordAndEdit() {
		driver.get(baseURL+"/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(firstname,lastname,username,password);

		driver.get(baseURL+"/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username,password);

		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("nav-notes-tab"))));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();",driver.findElement(By.id("nav-credentials-tab")));

		((JavascriptExecutor) driver).executeScript("arguments[0].click();",driver.findElement(By.id("newCredentialButton")));
		((JavascriptExecutor) driver).executeScript("arguments[0].value='" + "http://www.google.de" + "';", driver.findElement(By.id("credential-url")));
		((JavascriptExecutor) driver).executeScript("arguments[0].value='" + username + "';", driver.findElement(By.id("credential-username")));
		((JavascriptExecutor) driver).executeScript("arguments[0].value='" + password + "';", driver.findElement(By.id("credential-password")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("credentialSubmit")));


		driver.get(baseURL+"/home");
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("nav-credentials-tab"))));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();",driver.findElement(By.id("nav-credentials-tab")));

		String encryptedPWD = driver.findElement(By.id("credPassword")).getAttribute("innerHTML");
		assertNotEquals(password,encryptedPWD);

		((JavascriptExecutor) driver).executeScript("arguments[0].click();",driver.findElement(By.id("editCredentialButton")));
	}

	@Test
	public void testCreateCredentialPassword() {
		driver.get(baseURL + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(firstname, lastname, username, password);

		driver.get(baseURL + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("nav-notes-tab"))));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();",driver.findElement(By.id("nav-credentials-tab")));


		((JavascriptExecutor) driver).executeScript("arguments[0].click();",driver.findElement(By.id("newCredentialButton")));
		((JavascriptExecutor) driver).executeScript("arguments[0].value='" + "http://www.google.de" + "';", driver.findElement(By.id("credential-url")));
		((JavascriptExecutor) driver).executeScript("arguments[0].value='" + username + "';", driver.findElement(By.id("credential-username")));
		((JavascriptExecutor) driver).executeScript("arguments[0].value='" + password + "';", driver.findElement(By.id("credential-password")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("credentialSubmit")));

		driver.get(baseURL + "/home");
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("nav-credentials-tab"))));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();",driver.findElement(By.id("nav-credentials-tab")));

		String encryptedPWD = driver.findElement(By.id("credPassword")).getAttribute("innerHTML");
		assertNotEquals(password, encryptedPWD);

		((JavascriptExecutor) driver).executeScript("arguments[0].click();",driver.findElement(By.id("editCredentialButton")));
		((JavascriptExecutor) driver).executeScript("arguments[0].value='" + "http://www.google.com" + "';", driver.findElement(By.id("credential-url")));
		((JavascriptExecutor) driver).executeScript("arguments[0].value='" + username + "';", driver.findElement(By.id("credential-username")));
		((JavascriptExecutor) driver).executeScript("arguments[0].value='" + "newBadPassword" + "';", driver.findElement(By.id("credential-password")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("credentialSubmit")));

		driver.get(baseURL+"/home");
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("nav-credentials-tab"))));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();",driver.findElement(By.id("nav-credentials-tab")));
		assertNotEquals(encryptedPWD,driver.findElement(By.id("credPassword")).getAttribute("innerHTML"));

	}
	@Test
	public void testReRegister() {
		driver.get(baseURL+"/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(firstname,lastname,username,password);
		driver.get(baseURL+"/signup");
		signupPage.signup(firstname,lastname,username,password);
		assertEquals("The username already exists.",driver.findElement(By.id("error-msg")).getText());
	}
}
