package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.nio.charset.Charset;
import java.util.Random;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	private SignupPage signupPage;
	private LoginPage loginPage;
	private HomePage homePage;

	public String getRandomString() {
		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 10;
		Random random = new Random();

		String generatedString = random.ints(leftLimit, rightLimit + 1)
				.limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
				.toString();

		return generatedString;
	}

	public void signupAndLogin() {
		String username = this.getRandomString();
		String password = this.getRandomString();
		signupPage = new SignupPage(driver);
		driver.get("http://localhost:" + this.port + "/signup");
		signupPage.signup("Bahaa", "Kiali", username, password);
		driver.get("http://localhost:" + this.port + "/login");
		loginPage = new LoginPage(driver);
		loginPage.login(username, password);
	}

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void getSignupPage() {
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	public void getHomeWithoutLogin() {
		driver.get("http://localhost:" + this.port + "/home");
		System.out.println(driver.getTitle());
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void testSignup() {
		String username = this.getRandomString();
		String password = this.getRandomString();
		signupPage = new SignupPage(driver);
		driver.get("http://localhost:" + this.port + "/signup");
		signupPage.signup("Bahaa", "Kiali", username, password);
		Assertions.assertEquals(signupPage.getSuccessMessage(), "You successfully signed up! Please continue to the login page.");
		driver.get("http://localhost:" + this.port + "/login");
		loginPage = new LoginPage(driver);
		loginPage.login(username, password);
		Assertions.assertEquals("Home", driver.getTitle());
		try {
			Thread.sleep(1000); // wait until Home Html is rendered
			homePage = new HomePage(driver);
			homePage.logout();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void testAddNote() {
		this.signupAndLogin();
		try {
			Thread.sleep(1000); // wait until Home Html is rendered
			homePage = new HomePage(driver);
			homePage.showNoteTab();
			Thread.sleep(1000);
			int currentNotesCount = homePage.getNotesCount();
			homePage.openNoteModal();
			Thread.sleep(1000);
			homePage.addNote("test", "testDesc");
			Thread.sleep(1000);
			homePage.showNoteTab();
			Thread.sleep(1000);
			Assertions.assertEquals(homePage.getNotesCount(), currentNotesCount + 1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testEditNote() {
		this.signupAndLogin();
		try {
			Thread.sleep(1000); // wait until Home Html is rendered
			homePage = new HomePage(driver);
			homePage.showNoteTab();
			Thread.sleep(1000);
			homePage.openNoteModal();
			Thread.sleep(1000);
			homePage.addNote(this.getRandomString(), this.getRandomString());
			Thread.sleep(1000);
			homePage.showNoteTab();
			Thread.sleep(1000);
			homePage.editNote();
			Thread.sleep(1000);
			homePage.addNote(this.getRandomString(), this.getRandomString());
			Thread.sleep(1000);
			homePage.showNoteTab();
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDeleteNote() {
		this.signupAndLogin();
		try {
			Thread.sleep(1000); // wait until Home Html is rendered
			homePage = new HomePage(driver);
			homePage.showNoteTab();
			Thread.sleep(1000);
			homePage.openNoteModal();
			Thread.sleep(1000);
			homePage.addNote(this.getRandomString(), this.getRandomString());
			Thread.sleep(1000);
			homePage.showNoteTab();
			Thread.sleep(1000);
			int currentNotesCount = homePage.getNotesCount();
			Thread.sleep(1000);
			homePage.deleteNote();
			Thread.sleep(1000);
			homePage.showNoteTab();
			Thread.sleep(1000);
			Assertions.assertEquals(homePage.getNotesCount(), currentNotesCount - 1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testAddCredential() {
		this.signupAndLogin();
		try {
			Thread.sleep(1000); // wait until Home Html is rendered
			homePage = new HomePage(driver);
			homePage.showCredentialTab();
			Thread.sleep(1000);
			homePage.openCredentialmodal();
			Thread.sleep(1000);
			int currentNotesCount = homePage.getCredentialsCount();
			homePage.addCredential(this.getRandomString(), this.getRandomString(), this.getRandomString());
			Thread.sleep(1000);
			homePage.showCredentialTab();
			Thread.sleep(1000);
			Assertions.assertEquals(homePage.getCredentialsCount(), currentNotesCount + 1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testEditCredential() {
		this.signupAndLogin();
		try {
			Thread.sleep(1000); // wait until Home Html is rendered
			homePage = new HomePage(driver);
			homePage.showCredentialTab();
			Thread.sleep(1000);
			homePage.openCredentialmodal();
			Thread.sleep(1000);
			homePage.addCredential(this.getRandomString(), this.getRandomString(), this.getRandomString());
			Thread.sleep(1000);
			homePage.showCredentialTab();
			Thread.sleep(1000);
			homePage.editCredential();
			Thread.sleep(1000);
			homePage.addCredential(this.getRandomString(), this.getRandomString(), this.getRandomString());
			Thread.sleep(1000);
			homePage.showCredentialTab();
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDeleteCredential() {
		this.signupAndLogin();
		try {
			Thread.sleep(1000); // wait until Home Html is rendered
			homePage = new HomePage(driver);
			homePage.showCredentialTab();
			Thread.sleep(1000);
			homePage.openCredentialmodal();
			Thread.sleep(1000);
			homePage.addCredential(this.getRandomString(), this.getRandomString(), this.getRandomString());
			Thread.sleep(1000);
			homePage.showCredentialTab();
			Thread.sleep(1000);
			int currentCredentialsCount = homePage.getCredentialsCount();
			Thread.sleep(1000);
			homePage.deleteCredential();
			Thread.sleep(1000);
			homePage.showCredentialTab();
			Thread.sleep(1000);
			Assertions.assertEquals(homePage.getCredentialsCount(), currentCredentialsCount - 1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
