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

import java.io.File;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

    @LocalServerPort
    private int port;

    private WebDriver driver;

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

    /**
     * PLEASE DO NOT DELETE THIS method.
     * Helper method for Udacity-supplied sanity checks.
     **/
    private void doMockSignUp(String firstName, String lastName, String userName, String password) {
        // Create a dummy account for logging in later.

        // Visit the sign-up page.
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        driver.get("http://localhost:" + this.port + "/signup");
        webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

        // Fill out credentials
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
        WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
        inputFirstName.click();
        inputFirstName.sendKeys(firstName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
        WebElement inputLastName = driver.findElement(By.id("inputLastName"));
        inputLastName.click();
        inputLastName.sendKeys(lastName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        WebElement inputUsername = driver.findElement(By.id("inputUsername"));
        inputUsername.click();
        inputUsername.sendKeys(userName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        WebElement inputPassword = driver.findElement(By.id("inputPassword"));
        inputPassword.click();
        inputPassword.sendKeys(password);

        // Attempt to sign up.
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
        WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
        buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
//        Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
        Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());

    }


    /**
     * PLEASE DO NOT DELETE THIS method.
     * Helper method for Udacity-supplied sanity checks.
     **/
    private void doLogIn(String userName, String password) {
        // Log in to our dummy account.
        driver.get("http://localhost:" + this.port + "/login");
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        WebElement loginUserName = driver.findElement(By.id("inputUsername"));
        loginUserName.click();
        loginUserName.sendKeys(userName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        WebElement loginPassword = driver.findElement(By.id("inputPassword"));
        loginPassword.click();
        loginPassword.sendKeys(password);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        webDriverWait.until(ExpectedConditions.titleContains("Home"));

    }

    /**
     * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
     * rest of your code.
     * This test is provided by Udacity to perform some basic sanity testing of
     * your code to ensure that it meets certain rubric criteria.
     * <p>
     * If this test is failing, please ensure that you are handling redirecting users
     * back to the login page after a succesful sign up.
     * Read more about the requirement in the rubric:
     * https://review.udacity.com/#!/rubrics/2724/view
     */
    @Test
    public void testRedirection() {
        // Create a test account
        doMockSignUp("Redirection", "Test", "RT", "123");


        // Check if we have been redirected to the log in page.
        Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
    }

    /**
     * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
     * rest of your code.
     * This test is provided by Udacity to perform some basic sanity testing of
     * your code to ensure that it meets certain rubric criteria.
     * <p>
     * If this test is failing, please ensure that you are handling bad URLs
     * gracefully, for example with a custom error page.
     * <p>
     * Read more about custom error pages at:
     * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
     */
    @Test
    public void testBadUrl() {
        // Create a test account
        doMockSignUp("Bad", "URL", "BadURL", "123");
        doLogIn("BadURL", "123");

        // Try to access a random made-up URL.
        driver.get("http://localhost:" + this.port + "/some-random-page");
        Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
    }


    /**
     * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
     * rest of your code.
     * This test is provided by Udacity to perform some basic sanity testing of
     * your code to ensure that it meets certain rubric criteria.
     * <p>
     * If this test is failing, please ensure that you are handling uploading large files (>1MB),
     * gracefully in your code.
     * <p>
     * Read more about file size limits here:
     * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
     */
    @Test
    public void testLargeUpload() {
        // Create a test account
        doMockSignUp("Large File", "Test", "LFT", "123");
        doLogIn("LFT", "123");

        // Try to upload an arbitrary large file
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        String fileName = "upload5m.zip";

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
        WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
        fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

        WebElement uploadButton = driver.findElement(By.id("uploadButton"));
        uploadButton.click();
        try {
            webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
        } catch (org.openqa.selenium.TimeoutException e) {
            System.out.println("Large File upload failed");
        }
        Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));
    }


    @Test
    public void testUnauthorizedAccess() {

        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());

        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());

        driver.get("http://localhost:" + this.port + "/signup");
        Assertions.assertEquals("http://localhost:" + this.port + "/signup", driver.getCurrentUrl());

    }

    @Test
    public void testRedirectAfterLogOut() {
        // Create a test account
        doMockSignUp("RedirectAfter", "LogOut", "RedirectAfterLogOut", "123");
        doLogIn("RedirectAfterLogOut", "123");
        WebElement logoutButton = driver.findElement(By.id("logout"));
        logoutButton.click();
        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
    }


    private void createNote(String title, String description) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        WebElement notes = driver.findElement(By.id("nav-notes-tab"));
        notes.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addNote")));
        WebElement addNote = driver.findElement(By.id("addNote"));
        addNote.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteTitle")));
        WebElement noteTitle = driver.findElement(By.id("noteTitle"));
        noteTitle.sendKeys(title);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteDescription")));
        WebElement noteDescription = driver.findElement(By.id("noteDescription"));
        noteDescription.sendKeys(description);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteSubmitModal")));
        WebElement noteSubmit = driver.findElement(By.id("noteSubmitModal"));
        noteSubmit.click();

//        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("successButton")));
//        WebElement success = driver.findElement(By.id("successButton"));
//        success.click();

    }


    @Test
    public void testNoteCreation() {
        // Create a test account
        doMockSignUp("Note", "Creation", "NoteCreation", "123");
        doLogIn("NoteCreation", "123");
        createNote("BRING MILK", "Do by tomorrow");

        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
        WebElement success = driver.findElement(By.id("success"));

        Assertions.assertEquals(success.getText(), "Success");
    }

    @Test
    public void testNoteEdit() {
        // Create a test account
        doMockSignUp("Note", "Edit", "NoteEdit", "123");
        doLogIn("NoteEdit", "123");
        createNote("BRING MILK", "Do by tomorrow");

        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("successButton")));
        WebElement success = driver.findElement(By.id("successButton"));
        success.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        WebElement notes = driver.findElement(By.id("nav-notes-tab"));
        notes.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("editNote")));
        WebElement editNote = driver.findElement(By.id("editNote"));
        editNote.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteTitle")));
        WebElement editNoteTitle = driver.findElement(By.id("noteTitle"));
        editNoteTitle.sendKeys(" v2");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteDescription")));
        WebElement editNoteDescription = driver.findElement(By.id("noteDescription"));
        editNoteDescription.sendKeys(" /n Note Edited");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteSubmitModal")));
        WebElement noteSubmit = driver.findElement(By.id("noteSubmitModal"));
        noteSubmit.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
        WebElement editSuccess = driver.findElement(By.id("success"));

        Assertions.assertEquals(editSuccess.getText(), "Success");
    }

    @Test
    public void testNoteDelete() {
        // Create a test account
        doMockSignUp("Note", "Delete", "NoteDelete", "123");
        doLogIn("NoteDelete", "123");
        createNote("BRING MILK", "Do by tomorrow");

        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("successButton")));
        WebElement success = driver.findElement(By.id("successButton"));
        success.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        WebElement notes = driver.findElement(By.id("nav-notes-tab"));
        notes.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("deleteNote")));
        WebElement editNote = driver.findElement(By.id("deleteNote"));
        editNote.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
        WebElement deleteSuccess = driver.findElement(By.id("success"));

        Assertions.assertEquals(deleteSuccess.getText(), "Success");
    }


    private void createCredential(String url, String username, String password) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
        WebElement credentials = driver.findElement(By.id("nav-credentials-tab"));
        credentials.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addCredential")));
        WebElement addCredential = driver.findElement(By.id("addCredential"));
        addCredential.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("url")));
        WebElement URL = driver.findElement(By.id("url"));
        URL.sendKeys(url);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        WebElement USERNAME = driver.findElement(By.id("username"));
        USERNAME.sendKeys(username);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        WebElement PASSWORD = driver.findElement(By.id("password"));
        PASSWORD.sendKeys(password);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialsSubmitModal")));
        WebElement credentialSubmit = driver.findElement(By.id("credentialsSubmitModal"));
        credentialSubmit.click();

//        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("successButton")));
//        WebElement success = driver.findElement(By.id("successButton"));
//        success.click();
    }

    @Test
    public void testCredentialCreation() {
        doMockSignUp("Credential", "Creation", "CredentialCreation", "123");
        doLogIn("CredentialCreation", "123");
        createCredential("www.youtube.com", "Sh", "123");

        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("successButton")));
        WebElement success = driver.findElement(By.id("successButton"));
        success.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
        WebElement credentials = driver.findElement(By.id("nav-credentials-tab"));
        credentials.click();

        WebElement encryptedPassword = driver.findElement(By.id("encryptedPassword"));
        Assertions.assertNotEquals(encryptedPassword.getAttribute("innerHTML"), "123");
    }

    @Test
    public void testCredentialEdit() {
        doMockSignUp("Credential", "Edit", "CredentialEdit", "123");
        doLogIn("CredentialEdit", "123");
        createCredential("www.youtube.com", "Sh", "123");

        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("successButton")));
        WebElement success = driver.findElement(By.id("successButton"));
        success.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
        WebElement credentials = driver.findElement(By.id("nav-credentials-tab"));
        credentials.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialsEdit")));
        WebElement credentialsEdit = driver.findElement(By.id("credentialsEdit"));
        credentialsEdit.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("url")));
        WebElement url = driver.findElement(By.id("url"));
        url.sendKeys(" v2");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        WebElement username = driver.findElement(By.id("username"));
        username.sendKeys(" 27");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("456");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialsSubmitModal")));
        WebElement credentialsSubmit = driver.findElement(By.id("credentialsSubmitModal"));
        credentialsSubmit.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
        WebElement editSuccess = driver.findElement(By.id("success"));

        Assertions.assertEquals(editSuccess.getText(), "Success");
    }

    @Test
    public void testCredentialDelete() {
        // Create a test account
        doMockSignUp("Credential", "Delete", "CredentialDelete", "123");
        doLogIn("CredentialDelete", "123");
        createCredential("www.youtube.com", "Sh", "123");

        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("successButton")));
        WebElement success = driver.findElement(By.id("successButton"));
        success.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
        WebElement credentials = driver.findElement(By.id("nav-credentials-tab"));
        credentials.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("deleteCredentials")));
        WebElement deleteCredentials = driver.findElement(By.id("deleteCredentials"));
        deleteCredentials.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
        WebElement deleteSuccess = driver.findElement(By.id("success"));

        Assertions.assertEquals(deleteSuccess.getText(), "Success");
    }

}
