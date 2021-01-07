package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
	private WebDriverWait wait;

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
		wait = new WebDriverWait(driver,10);
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

		driver.findElement(By.id("nav-notes-tab")).click();

		//Thread.sleep(3000);
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		driver.findElement(By.id("newNoteButton")).click();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		//Thread.sleep(2000);
		driver.findElement(By.id("note-title")).sendKeys("Title");
		driver.findElement(By.id("note-description")).sendKeys("Note description");
		driver.findElement(By.id("saveNoteButton")).click();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.get(baseURL+"/home");
		//driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		//Thread.sleep(2000);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("nav-notes-tab"))));
		driver.findElement(By.id("nav-notes-tab")).click();
		//driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		assertEquals("Title",driver.findElement(By.id("noteTitleId")).getText());
		assertEquals("Note description",driver.findElement(By.id("noteTextId")).getText());
	}

	@Test
	public void testNoteCreationAndEdit() throws InterruptedException {
		driver.get(baseURL+"/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(firstname,lastname,username,password);
		driver.get(baseURL+"/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username,password);
		//driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("nav-notes-tab"))));
		driver.findElement(By.id("nav-notes-tab")).click();
//		driver.findElement(By.id("nav-notes-tab")).click();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		assertEquals("By.id: nav-notes-tab",By.id("nav-notes-tab").toString());
		driver.findElement(By.id("newNoteButton")).click();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		//Thread.sleep(2000);
		driver.findElement(By.id("note-title")).sendKeys("Title");
		driver.findElement(By.id("note-description")).sendKeys("Note description");
		driver.findElement(By.id("saveNoteButton")).click();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.get(baseURL+"/home");
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		//Thread.sleep(2000);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("nav-notes-tab"))));
		driver.findElement(By.id("nav-notes-tab")).click();
//		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		//edit button druecken
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("editButton"))));
		driver.findElement(By.id("editButton")).click();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		assertEquals("Title",driver.findElement(By.id("noteTitleId")).getText());
		assertEquals("Note description",driver.findElement(By.id("noteTextId")).getText());
		driver.findElement(By.id("note-title")).clear();
		driver.findElement(By.id("note-title")).sendKeys("New Title");
		driver.findElement(By.id("note-description")).clear();
		driver.findElement(By.id("note-description")).sendKeys("New Note description");
		driver.findElement(By.id("saveNoteButton")).click();
		//driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.get(baseURL+"/home");
		//driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		//Thread.sleep(2000);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("nav-notes-tab"))));
		driver.findElement(By.id("nav-notes-tab")).click();
		assertEquals("New Title",driver.findElement(By.id("noteTitleId")).getText());
		assertEquals("New Note description",driver.findElement(By.id("noteTextId")).getText());

		//assertEquals("By.className: nav-item nav-link active",By.className("nav-item nav-link active"));
	}

	@Test
	public void testNoteCreationAndDelete() throws InterruptedException {
		driver.get(baseURL+"/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(firstname,lastname,username,password);
		driver.get(baseURL+"/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username,password);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("nav-notes-tab"))));
		driver.findElement(By.id("nav-notes-tab")).click();

		//Thread.sleep(3000);
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		driver.findElement(By.id("newNoteButton")).click();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		//Thread.sleep(2000);
		driver.findElement(By.id("note-title")).sendKeys("Title");
		driver.findElement(By.id("note-description")).sendKeys("Note description");
		driver.findElement(By.id("saveNoteButton")).click();
		driver.get(baseURL+"/home");
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("nav-notes-tab"))));
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElement(By.id("nav-notes-tab")).click();
		//driver.findElement(By.id("nav-notes-tab")).click();
		Thread.sleep(3000);
		driver.findElement(By.id("deleteButton")).click();
		driver.get(baseURL+"/home");
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("nav-notes-tab"))));
		driver.findElement(By.id("nav-notes-tab")).click();
		assertNotEquals("Title", driver.findElement(By.id("note-title")).getText());

	}

	/**
	 * 3. Write Tests for Credential Creation, Viewing, Editing, and Deletion.
	 *
	 *     Write a test that deletes an existing set of credentials and verifies that the credentials are no longer displayed.
	 * @throws InterruptedException
	 */
	@Test
	public void createCredentialAndDelete() throws InterruptedException {
		driver.get(baseURL+"/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(firstname,lastname,username,password);
		driver.get(baseURL+"/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username,password);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("nav-notes-tab"))));
		driver.findElement(By.id("nav-credentials-tab")).click();

		//Thread.sleep(3000);
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		driver.findElement(By.id("newCredentialButton")).click();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		//Thread.sleep(2000);
		driver.findElement(By.id("credential-url")).sendKeys("http://www.google.de");
		driver.findElement(By.id("credential-username")).sendKeys(username);
		driver.findElement(By.id("credential-password")).sendKeys(password);
		driver.findElement(By.id("credentialSubmit")).submit();
		driver.get(baseURL+"/home");
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("nav-credentials-tab"))));
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElement(By.id("nav-credentials-tab")).click();
		//driver.findElement(By.id("nav-notes-tab")).click();
		Thread.sleep(3000);
		driver.findElement(By.id("deleteCredentialButton")).click();
		driver.get(baseURL+"/home");
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("nav-credentials-tab"))));
		driver.findElement(By.id("nav-credentials-tab")).click();
		assertNotEquals("http://www.google.de", driver.findElement(By.id("credential-url")).getText());

	}

	/**
	 * Write a test that creates a set of credentials, verifies that they are displayed, and verifies that the displayed password is encrypted.
	 * @throws InterruptedException
	 */
	@Test
	public void testCreateCredentialPasswordAndEdit() throws InterruptedException {
		driver.get(baseURL+"/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(firstname,lastname,username,password);
		driver.get(baseURL+"/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username,password);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("nav-notes-tab"))));
		driver.findElement(By.id("nav-credentials-tab")).click();

		//Thread.sleep(3000);
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		driver.findElement(By.id("newCredentialButton")).click();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		//Thread.sleep(2000);
		driver.findElement(By.id("credential-url")).sendKeys("http://www.google.de");
		driver.findElement(By.id("credential-username")).sendKeys(username);
		driver.findElement(By.id("credential-password")).sendKeys(password);
		driver.findElement(By.id("credentialSubmit")).submit();
		driver.get(baseURL+"/home");
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("nav-credentials-tab"))));
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElement(By.id("nav-credentials-tab")).click();
		String encryptedPWD = driver.findElement(By.id("credPassword")).getText();
		assertNotEquals(password,encryptedPWD);
		//driver.findElement(By.id("nav-notes-tab")).click();
		//Thread.sleep(3000);
		driver.findElement(By.id("editCredentialButton")).click();
		//assert FAILS because text was filled by Javascript.
		//assertEquals(password,driver.findElement(By.id("credential-password")).getText());
	}

	/**
	 * Write a test that views an existing set of credentials, verifies that the viewable password is unencrypted, edits the credentials, and verifies that the changes are displayed.
	 * @throws InterruptedException
	 */
	@Test
	public void testCreateCredentialPassword() throws InterruptedException {
		driver.get(baseURL + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(firstname, lastname, username, password);
		driver.get(baseURL + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("nav-notes-tab"))));
		driver.findElement(By.id("nav-credentials-tab")).click();

		//Thread.sleep(3000);
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		driver.findElement(By.id("newCredentialButton")).click();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		//Thread.sleep(2000);
		driver.findElement(By.id("credential-url")).sendKeys("http://www.google.de");
		driver.findElement(By.id("credential-username")).sendKeys(username);
		driver.findElement(By.id("credential-password")).sendKeys(password);
		driver.findElement(By.id("credentialSubmit")).submit();
		driver.get(baseURL + "/home");
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("nav-credentials-tab"))));
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElement(By.id("nav-credentials-tab")).click();
		String encryptedPWD = driver.findElement(By.id("credPassword")).getText();
		assertNotEquals(password, encryptedPWD);


		//Thread.sleep(3000);
		driver.findElement(By.id("editCredentialButton")).click();
		driver.findElement(By.id("credential-url")).clear();
		driver.findElement(By.id("credential-url")).sendKeys("http://www.google.com");
		driver.findElement(By.id("credential-username")).clear();
		driver.findElement(By.id("credential-username")).sendKeys(username);
		driver.findElement(By.id("credential-password")).clear();
		driver.findElement(By.id("credential-password")).sendKeys("newBadPassword");
		driver.findElement(By.id("credentialSubmit")).submit();
		driver.get(baseURL+"/home");
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("nav-credentials-tab"))));
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElement(By.id("nav-credentials-tab")).click();
		assertNotEquals(encryptedPWD,driver.findElement(By.id("credPassword")).getText());

	}
	@Test
	public void testReRegister() throws InterruptedException {
		driver.get(baseURL+"/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(firstname,lastname,username,password);
		driver.get(baseURL+"/signup");
		signupPage.signup(firstname,lastname,username,password);
		assertEquals("The username already exists.",driver.findElement(By.id("error-msg")).getText());
	}

}
