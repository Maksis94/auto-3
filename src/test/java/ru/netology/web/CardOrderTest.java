package ru.netology.web;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.partialText;
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

        name.shouldHave(cssClass("input_invalid"));
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

        phone.shouldHave(cssClass("input_invalid"));
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

    @Test
    void shouldFailOnAllFieldsEmpty() {
        open("http://localhost:9999");

        SelenideElement name = $("[data-test-id=name]");
        SelenideElement submit = $("form.form button.button");

        submit.click();

        name.shouldHave(cssClass("input_invalid"));
    }
}