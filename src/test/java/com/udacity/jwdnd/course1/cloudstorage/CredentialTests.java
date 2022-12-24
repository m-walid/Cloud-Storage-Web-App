package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.dtos.SignupDto;
import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialTests {
    @LocalServerPort
    private int port;

    private static WebDriver driver;

    private HomePage homePage;

    @BeforeAll
    public void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("http://localhost:" + port + "/signup");
        SignupPage signupPage = new SignupPage(driver);
        signupPage.signup(new SignupDto("testUserCredentials", "password", "firstname", "lastname"));
        driver.quit();
    }

    @BeforeEach
    public void beforeEach() {
        driver = new ChromeDriver();
        driver.get("http://localhost:" + port + "/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("testUserCredentials", "password");
        homePage = new HomePage(driver);
    }

    @Test
    public void testCreateCredentials() {
        for (int i = 0; i < 3; i++) {
            homePage.createCredential("testUrl" + i, "testUsername" + i, "testPassword" + i);
            Assertions.assertTrue(homePage.checkIfCredentialExists("testUrl" + i, "testUsername" + i));
            Assertions.assertTrue(homePage.checkIfPasswordIsEncrypted("testUrl" + i, "testUsername" + i, "testPassword" + i));
        }
    }

    @Test
    public void testViewAndEditCredentials() {
        for (int i = 0; i < 3; i++) {
            homePage.createCredential("testUrl" + i, "testUsername" + i, "testPassword" + i);
            Assertions.assertTrue(homePage.checkIfPasswordIsDecrypted("testUrl" + i, "testUsername" + i, "testPassword" + i));
            homePage.editCredential("testUrlEdited" + i, "testUsernameEdited" + i, "testPasswordEdited" + i);
            Assertions.assertTrue(homePage.checkIfCredentialExists("testUrlEdited" + i, "testUsernameEdited" + i));
        }

    }

    @Test
    public void testDeleteCredentials() {
        for (int i = 0; i < 3; i++) {
            homePage.createCredential("testUrl" + i, "testUsername" + i, "testPassword" + i);
            homePage.deleteCredential();
            Assertions.assertFalse(homePage.checkIfCredentialExists("testUrl" + i, "testUsername" + i));
        }

    }

    @AfterEach
    public void afterEach() {
        //delete all credentials
        homePage.deleteAllCredentials();
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterAll
    public void afterAll() {
        if (driver != null) {
            driver.quit();
        }
    }


}
