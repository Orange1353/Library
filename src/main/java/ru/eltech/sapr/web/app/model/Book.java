package ru.eltech.sapr.web.app.model;

import java.util.Objects;

public class Book {
    private final long id;
    private final TypeAccessBook typeAccess;

    private final String title;
    private final String publishing;
    private final String author;
    private final CategoryBook category;
    private final int year;

    public Book(long id, TypeAccessBook typeAccess, String title, String publishing, String author, CategoryBook category, int year) {
        this.id = id;
        this.typeAccess = typeAccess;
        this.title = title;
        this.publishing = publishing;
        this.author = author;
        this.category = category;
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id &&
                Objects.equals(typeAccess, book.typeAccess) &&
                Objects.equals(title, book.title) &&
                Objects.equals(publishing, book.publishing) &&
                Objects.equals(author, book.author) &&
                category == book.category &&
                Objects.equals(year, book.year);
    }

    @Override
    public int hashCode() {
        int result = (int)(id ^ (id >> 32));
        result = 31 * result + typeAccess.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + publishing.hashCode();
        result = 31 * result + author.hashCode();
        result = 31 * result + category.hashCode();
        result = 31 * result + year;
        return result;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPublishing() {
        return publishing;
    }

    public String getAuthor() {
        return author;
    }

    public TypeAccessBook getTypeAccess() {
        return typeAccess;
    }

    public CategoryBook getCategory() {
        return category;
    }

    public int getYear() {
        return year;
    }

}