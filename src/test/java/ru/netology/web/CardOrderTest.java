package ru.netology.web;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardOrderTest {
    @Test
    void shouldSendForm() {
        open("http://localhost:9999");

        SelenideElement name = $("[data-test-id=name] input");
        SelenideElement phone = $("[data-test-id=phone] input");
        SelenideElement agreement = $("[data-test-id=agreement]");
        SelenideElement submit = $("form.form button.button");

        name.setValue("Василий");
        phone.setValue("+79270000000");
        agreement.click();
        submit.click();

        $("[data-test-id=order-success]").shouldHave(partialText("Ваша заявка успешно отправлена!"));
    }

    @Test
    void shouldFailOnInvalidName() {
        open("http://localhost:9999");

        SelenideElement name = $("[data-test-id=name]");
        SelenideElement phone = $("[data-test-id=phone]");
        SelenideElement agreement = $("[data-test-id=agreement]");
        SelenideElement submit = $("form.form button.button");

        name.$("input").setValue("Vasily");
        phone.$("input").setValue("+79270000000");
        agreement.click();
        submit.click();

        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldFailOnEmptyName() {
        open("http://localhost:9999");

        SelenideElement phone = $("[data-test-id=phone]");
        SelenideElement agreement = $("[data-test-id=agreement]");
        SelenideElement submit = $("form.form button.button");

        phone.$("input").setValue("+79270000000");
        agreement.click();
        submit.click();

        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldFailOnInvalidPhone() {
        open("http://localhost:9999");

        SelenideElement name = $("[data-test-id=name]");
        SelenideElement phone = $("[data-test-id=phone]");
        SelenideElement agreement = $("[data-test-id=agreement]");
        SelenideElement submit = $("form.form button.button");

        name.$("input").setValue("Василий");
        phone.$("input").setValue("89270000000");
        agreement.click();
        submit.click();

        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldFailOnEmptyPhone() {
        open("http://localhost:9999");

        SelenideElement name = $("[data-test-id=name]");
        SelenideElement agreement = $("[data-test-id=agreement]");
        SelenideElement submit = $("form.form button.button");

        name.$("input").setValue("Василий");
        agreement.click();
        submit.click();

        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldFailOnUncheckedAgreement() {
        open("http://localhost:9999");

        SelenideElement name = $("[data-test-id=name]");
        SelenideElement phone = $("[data-test-id=phone]");
        SelenideElement agreement = $("[data-test-id=agreement]");
        SelenideElement submit = $("form.form button.button");

        name.$("input").setValue("Василий");
        phone.$("input").setValue("+79270000000");
        submit.click();

        agreement.shouldHave(cssClass("input_invalid"));
    }
}