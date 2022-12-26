package com.andreev.enumSourceAndMethodSource;

import com.andreev.configurationsForTests.WebAttachments;
import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

@Nested
@DisplayName("Параметризованные тесты для сайта banki.ru с помощью EnumSource и MethodSource")
public class WebBankiRuTests {
    private static final String URL = "https://www.banki.ru/";
    private static final String URL_CURRENCY_CONVERT = "https://www.banki.ru/products/currency/converter/";

    @DisplayName("Проверка заголовков в меню категорий на главной странице")
    @EnumSource(MenuItem.class)
    @ParameterizedTest(name = "Есть заголовок раздела {0} в меню категорий на странице " + URL)
    void checkItemsInMenuWithEnumSource(MenuItem menuItem) {
        open(URL);
        switchToMenuItem(menuItem);
    }

    private WebBankiRuTests switchToMenuItem(MenuItem menuItem) {
        $(".main-menu").shouldHave(Condition.text(menuItem.getDesc()));
        return this;
    }

    private static Stream<Arguments> checkСaseText() {
        return Stream.of(
                Arguments.of("BRL","бразильского реала"),
                Arguments.of("AZN","азербайджанского маната"),
                Arguments.of("TMT","нового туркменского маната"),
                Arguments.of("GBP","фунта стерлингов Соединенного королевства")
        );
    }

    @DisplayName("Проверки текста на графиком на странице конвертации валют")
    @MethodSource()
    @ParameterizedTest(name = "Есть текст над графиком «{1}, за 1 {0}» на странице конвертации валют при выборе {0}")
    void checkСaseText(String currency, String textCurrency) {
        open(URL_CURRENCY_CONVERT);
        step("Кликаем по списку выбора валюты", () -> {
            $(".select-input-field__selected-value").click();
        });
        step("Вводим в поисковую строку " + currency, () -> {
            $(".ui-select__input").setValue(currency);
        });
        step("Выбираем валюту " + currency, () -> {
            $(".ui-select__scrollable").$(byText(currency)).click();
        });
        step("Текст над графиком в нужном наклонении " + textCurrency + ", за 1 " + currency, () -> {
            $("section[data-test='currency-dynamic']").$(".widget__title").$(".title__addl")
                    .shouldHave(Condition.text(textCurrency + ", за 1 " + currency));
            WebAttachments.takeScreenshot();
        });
    }
}
