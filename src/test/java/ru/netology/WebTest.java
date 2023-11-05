package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class WebTest {

    public String date(String pattern,  int addedDays) {
        String date = (LocalDate.now().plusDays(addedDays)).format(DateTimeFormatter.ofPattern(pattern));
        return date;
    }


    @BeforeEach
    void initEach() {
        open("http://localhost:9999");
    }

    // позитивные тесты
    // первое задание
    @Test
    void correctValueTest() {
        $("[data-test-id=city] input").setValue("Екатеринбург");
        Random random = new Random();
        int addedDays = random.nextInt(11)+3;
        String date = date("dd.MM.yyyy", (addedDays));
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Иванов-Сидоров Петр");
        $("[data-test-id=phone] input").setValue("+79270000000");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $(".notification__title").shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Успешно!"));
        $(".notification__content").shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Встреча успешно забронирована на " + date));
    }

    // второе задание
    @Test
    void dorpDownValueTest() {
        $("[data-test-id=city] input").setValue("Екатеринбург");
        Random random = new Random();
        int addedDays = random.nextInt(11)+3;
        String date = date("dd.MM.yyyy", (addedDays));
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Иванов-Сидоров Петр");
        $("[data-test-id=phone] input").setValue("+79270000000");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $(".notification__title").shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Успешно!"));
        $(".notification__content").shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Встреча успешно забронирована на " + date));
    }

    @Test
    void calendarTest() {
        $("[data-test-id=city] input").setValue("Екатеринбург");
        Random random = new Random();
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        int addedDays = random.nextInt(11)+3;
        LocalDate selectedDate = LocalDate.now().plusDays(addedDays);
        String date = selectedDate.format(pattern);
        $(".input__icon").click();
        if (selectedDate.getMonth().equals(LocalDate.now().getMonth())){
            $$(".calendar__day").findBy(text(String.valueOf(selectedDate.getDayOfMonth()))).click();
        }
        else {
            $("[data-step='1']").click();;
            $$(".calendar__day").findBy(text(String.valueOf(selectedDate.getDayOfMonth()))).click();
        }

        $("[data-test-id=name] input").setValue("Сант-Жермен");
        $("[data-test-id=phone] input").setValue("+79270000002");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $(".notification__title").shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Успешно!"));
        $(".notification__content").shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Встреча успешно забронирована на " + date));
    }

    @Test
    void dropDownCityTest() {
        $("[data-test-id='city'] input").setValue("Ка");
        $$(".menu-item__control").findBy(text("Калуга")).click();
        Random random = new Random();
        int addedDays = random.nextInt(11)+3;
        String date = date("dd.MM.yyyy", (addedDays));
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Сант-Жермен");
        $("[data-test-id=phone] input").setValue("+79270000002");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $(".notification__title").shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Успешно!"));
        $(".notification__content").shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Встреча успешно забронирована на " + date));
    }

    // негативные тесты
    @Test
    void emptyCityTest() {
        $("[data-test-id=city] input").setValue("");
        Random random = new Random();
        int addedDays = random.nextInt(11)+3;
        String date = date("dd.MM.yyyy", (addedDays));
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Иванов-Сидоров Петр");
        $("[data-test-id=phone] input").setValue("+79270000000");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void wrongCityTest() {
        $("[data-test-id=city] input").setValue("Кулибино");
        Random random = new Random();
        int addedDays = random.nextInt(11)+3;
        String date = date("dd.MM.yyyy", (addedDays));
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Иванов-Сидоров Петр");
        $("[data-test-id=phone] input").setValue("+79270000000");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void emptyNameTest() {
        $("[data-test-id=city] input").setValue("Рязань");
        Random random = new Random();
        int addedDays = random.nextInt(11)+3;
        String date = date("dd.MM.yyyy", (addedDays));
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("");
        $("[data-test-id=phone] input").setValue("+79270000000");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void nameWithSymvolsTest() {
        $("[data-test-id=city] input").setValue("Майкоп");
        Random random = new Random();
        int addedDays = random.nextInt(11)+3;
        String date = date("dd.MM.yyyy", (addedDays));
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Иванов%Сидоров%Петр");
        $("[data-test-id=phone] input").setValue("+79270000000");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void emptyPhoneTest() {
        $("[data-test-id=city] input").setValue("Барнаул");
        Random random = new Random();
        int addedDays = random.nextInt(11)+3;
        String date = date("dd.MM.yyyy", (addedDays));
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Маша");
        $("[data-test-id=phone] input").setValue("");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void smallPhoneTest() {
        $("[data-test-id=city] input").setValue("Краснодар");
        Random random = new Random();
        int addedDays = random.nextInt(11)+3;
        String date = date("dd.MM.yyyy", (addedDays));
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Маша");
        $("[data-test-id=phone] input").setValue("+7927000000");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void bigPhoneTest() {
        $("[data-test-id=city] input").setValue("Чита");
        Random random = new Random();
        int addedDays = random.nextInt(11)+3;
        String date = date("dd.MM.yyyy", (addedDays));
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Маша");
        $("[data-test-id=phone] input").setValue("+792700000023");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void phoneWithoutPlusTest() {
        $("[data-test-id=city] input").setValue("Чита");
        Random random = new Random();
        int addedDays = random.nextInt(11)+3;
        String date = date("dd.MM.yyyy", (addedDays));
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Маша");
        $("[data-test-id=phone] input").setValue("792700000020");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void withoutAcceptTest() {
        $("[data-test-id=city] input").setValue("Калуга");
        Random random = new Random();
        int addedDays = random.nextInt(11)+3;
        String date = date("dd.MM.yyyy", (addedDays));
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Сант-Жермен");
        $("[data-test-id=phone] input").setValue("+79270000002");
        $("[data-test-id=agreement] input").shouldNotBe(selected);
        $("button.button").click();
        $("[data-test-id=agreement].input_invalid").shouldBe(visible);
    }
}

