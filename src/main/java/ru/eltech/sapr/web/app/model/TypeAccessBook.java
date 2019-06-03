package ru.eltech.sapr.web.app.model;

public enum TypeAccessBook {
    IN_STOCK("Green", "Есть в наличии"),
    COMING_SOON("Yellow", "Скоро будет доступна"),
    FROM_USER("Yellow", "У пользователя"),
    RECYCLABLE("Red", "Утилизирована");

    private final String title;
    private final String text;

    TypeAccessBook(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }
}
