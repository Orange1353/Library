package ru.eltech.sapr.web.app.model;

import java.util.Objects;

public class Phone {
    private final long id;

    private final String number;
    private final PhoneType type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phone phone = (Phone) o;
        return id == phone.id &&
                Objects.equals(number, phone.number) &&
                Objects.equals(type, phone.type);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >> 32));
        result = 31 * result + number.hashCode();
        result = 31 * result + type.hashCode();
        return  result;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public PhoneType getType() {
        return type;
    }

    public Phone(long id, String number, PhoneType type) {
        this.id = id;
        this.number = number;
        this.type = type;
    }
}
