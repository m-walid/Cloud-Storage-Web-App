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
public class NoteTests {
    @LocalServerPort
    private int port;
    private static WebDriver webDriver;
    private HomePage homePage;

    @BeforeAll
    public void beforeAll() {
        WebDriverManager.chromedriver().setup();
        webDriver = new ChromeDriver();
        webDriver.get("http://localhost:" + port + "/signup");
        SignupPage signupPage = new SignupPage(webDriver);
        signupPage.signup(new SignupDto("testUserNotes", "password", "firstname", "lastname"));
        webDriver.quit();
    }

    @BeforeEach
    public void beforeEach() {
        webDriver = new ChromeDriver();
        webDriver.get("http://localhost:" + port + "/login");
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.login("testUserNotes", "password");
        homePage = new HomePage(webDriver);
    }
    @Test
    public void testCreateNote() {
        homePage.createNote("testNote", "testNoteDescription");
        Assertions.assertTrue(homePage.checkIfNoteExists("testNote", "testNoteDescription"));

    }

    @Test
    public void testEditNote() {
        homePage.createNote("testNote", "testNoteDescription");
        homePage.editNote("testNoteEdited", "testNoteDescriptionEdited");
        Assertions.assertTrue(homePage.checkIfNoteExists("testNoteEdited", "testNoteDescriptionEdited"));
    }

    @Test
    public void testDeleteNote() {
        homePage.createNote("testNote", "testNoteDescription");
        homePage.deleteNote();
        Assertions.assertFalse(homePage.checkIfNoteExists("testNote", "testNoteDescription"));
    }

    @AfterEach
    public void afterEach() {
      homePage.deleteAllNotes();
        if(webDriver != null) {
            webDriver.quit();
        }
    }

    @AfterAll
    public void afterAll() {
        if(webDriver != null) {
            webDriver.quit();
        }
    }


}
