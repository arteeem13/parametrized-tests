package com.andreev.ozon;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

@Nested
@DisplayName("Параметризованные тесты для сайта Ozon")
public class WebOzonTests {

    private static final String URL_OZON = "https://www.ozon.ru/";

    @BeforeAll
    public static void beforeTests(){
        Configuration.browser = "FIREFOX";
        step("Открываем главную страницу" + URL_OZON, () -> {
            open(URL_OZON);
        });
    }

    @ValueSource(strings = {
            "Одежда и обувь",
            "Электроника",
            "Дом и сад",
    })
    @ParameterizedTest(name = "Есть заголовок раздела {0} в меню категорий на странице " + URL_OZON)
    void checkCategoriesInMenuWithValueSource(String searchQuery) {
        step("Есть категория " + searchQuery + " в меню");
        $("ul[data-widget='horizontalMenu']").shouldHave(Condition.text(searchQuery));
    }

    @CsvSource({
            "KZT, ₸"
//            "RUB, ₽",
//            "USD, $",
    })
    @ParameterizedTest(name = "Отображается {1} для валюты {0} в карточке товара")
    void checkCurrencyIconInProductCardWithCsvSource(String currency, String icon) {
        step("Выбираем город из РФ", () -> {
            $("div[data-widget='addressBookBarWeb']").click();
            $(byText("Изменить")).click();
            $(byText("Москва")).click();
        });
        step("Кликаем по кнопке выбора валюты", () ->{
            $("div[data-widget='selectedCurrency']").click();
        });
        step("Выбираем валюту " + currency, () ->{
            $("div[role='listbox']").click();
            $("input[name='filter']").setValue(currency).pressEnter();
        });
        step("Есть " + icon + " в описании товаров", () -> {
            $("div[data-widget='skuLine']").shouldHave(Condition.text(icon));
        });
    }
}
