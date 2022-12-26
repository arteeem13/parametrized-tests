package com.andreev.enumSourceAndMethodSource;

public enum MenuItem {
    DEPOSITS("Вклады"),
    CREDITS("Кредиты"),
    CARDS("Карты"),
    INVESTS("Инвестиции"),
    BUSINESS("Бизнес"),
    NEWS("Новости");

    private String desc;

    MenuItem(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
