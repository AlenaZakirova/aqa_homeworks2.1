import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DebitCardApplication {
    private WebDriver driver;

    @BeforeAll
    public static void stepAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999/");

    }

    @AfterEach
    public void afterEach() {
        driver.quit();
        driver = null;
    }

    @Test

    void checkingDebitCardApplication () throws InterruptedException {

        driver.get("http://localhost:9999/");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Иван Иванов");
        elements.get(1).sendKeys("+79610000000");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();
        var actualTextElement = driver.findElement(By.cssSelector("[data-test-id=order-success]"));
        var actualText = actualTextElement.getText().trim();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", actualText);
        assertTrue(actualTextElement.isDisplayed());
    }

    @Test

    void emptyNameFieldInTheDebitCardApplication () throws InterruptedException {

        driver.get("http://localhost:9999/");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("");
        elements.get(1).sendKeys("+79610000000");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();
        assertEquals("Поле обязательно для заполнения",
                driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim());

    }

    @Test

    void emptyPhoneFieldInTheDebitCardApplication () throws InterruptedException {

        driver.get("http://localhost:9999/");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Иван Иванов");
        elements.get(1).sendKeys(" ");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();
        String text = driver.findElements(By.className("input__sub")).get(1).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());

    }

    @Test

    void checkboxNotSelected() throws InterruptedException {

        driver.get("http://localhost:9999/");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Иван Иванов");
        elements.get(1).sendKeys("+79610000000");
        driver.findElement(By.className("button__content")).click();
        String text = driver.findElements(By.className("input__sub")).get(1).getText();
        assertEquals("На указанный номер моб. тел. будет отправлен смс-код для подтверждения заявки на карту. Проверьте, что номер ваш и введен корректно.", text.trim());

    }

    @Test

    void latNameFieldInDebitCardApplication () throws InterruptedException {

        driver.get("http://localhost:9999/");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Ivan Ivanov");
        elements.get(1).sendKeys("+79610000000");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();
        String text = driver.findElement(By.className("input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());

    }

    @Test

    void nameFieldWithoutSpace () throws InterruptedException {

        driver.get("http://localhost:9999/");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("ИванИванов");
        elements.get(1).sendKeys("+79610000000");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();
        var actualTextElement = driver.findElement(By.cssSelector("[data-test-id=order-success]"));
        var actualText = actualTextElement.getText().trim();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", actualText);
        assertTrue(actualTextElement.isDisplayed());

    }



}
