package ru.netology;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;


public class CardDeliveryTest {

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }


    @Test
    void shouldSentFormPopularName() {
        String planningDate = generateDate(5);
        open("http://localhost:9999");
        $("[placeholder=\"Город\"]").setValue("Санкт-Петербург");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[name=\"name\"]").setValue("Иванов Иван");
        $("[name=\"phone\"]").setValue("+79111111111");
        $("[data-test-id=\"agreement\"]").click();
        $x("//span[text()=\"Забронировать\"]").click();
        $x("//div[@class=\"notification__title\"]").shouldBe(visible, Duration.ofSeconds(15));//задержка
        $("[data-test-id=notification] .notification__content").should(exactText("Встреча успешно забронирована на " + planningDate));
    }


}
