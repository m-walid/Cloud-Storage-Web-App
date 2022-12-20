package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.dtos.SignupDto;
import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationTests {

    @LocalServerPort
    private int port;

    private static WebDriver webDriver;

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        webDriver = new ChromeDriver();
    }

    @Test
    public void testUnauthorizedUser() {
        webDriver.get("http://localhost:" + this.port + "/home");
        Assertions.assertTrue(webDriver.getCurrentUrl().contains("login"));
    }

    @Test
    public void testEntireAuthenticationFlow(){
        // Signup
        webDriver.get("http://localhost:" + this.port + "/signup");
        SignupPage signupPage = new SignupPage(webDriver);
        signupPage.signup(new SignupDto("testUser", "password", "firstname", "lastname"));
        Assertions.assertTrue(signupPage.checkIfOnLoginPage());
        // Login
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.login("testUser", "password");
        Assertions.assertTrue(loginPage.checkIfOnHomePage());
        // Logout
        HomePage homePage = new HomePage(webDriver);
        homePage.logout();
        Assertions.assertTrue(homePage.checkIfOnLoginPage());
        // check if unauthorized
        webDriver.get("http://localhost:" + this.port + "/home");
        Assertions.assertTrue(webDriver.getCurrentUrl().contains("login"));
    }

    @AfterAll
    public static void afterAll() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}
