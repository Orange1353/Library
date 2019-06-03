package ru.eltech.sapr.web.app.model;

public enum CategoryTest {
    //1 - 20
    FICTION("Проза"),
    EDUCATIONAL_LITERATURE("Обучающая литература"),
    FOREIGN_LANGUAGES("Иностранные языки"),
    ART("Искусство"),
    BUSINESS_ECONOMICS_LAW("Бизнес, экономика, право"),
    COMPUTER_LITERATUREБ("Компьютерная литература"),
    PSYCHOLOGY("Психология"),
    SCIENTIFIC_LITERATURE("Научная литература"),
    HISTORY_POLITICS("История и политика"),
    TRAVEL_CARS("Путешествие, машины"),
    NEWSPAPERS_MAGAZINES("Газеты, журналы");

    private String title;

    CategoryTest(String title) {
        this.title = title;
    }

    public String getTitle() {return title;}

    @Override
    public String toString() {
        return "Сategory{" +
                "title='" + title + '\'' +
                '}';
    }
}
