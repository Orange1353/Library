package ru.eltech.sapr.web.app.model;

import java.util.Objects;

public class User {
    private final long id;
    private UserType levelAccess;

    public void setLevelAccess(UserType levelAccess) {
        this.levelAccess = levelAccess;
    }

    private final String firstName;
    private final String lastName;

    private final String login;
    private final int password;

    public User(long id, UserType levelAccess, String firstName, String lastName, String login, int password) {
        this.id = id;
        this.levelAccess = levelAccess;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                password == user.password &&
                levelAccess == user.levelAccess &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(login, user.login);
    }

    public long getId() {
        return id;
    }

    public UserType getLevelAccess() {
        return levelAccess;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLogin() {
        return login;
    }

    public int getPassword() {
        return password;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >> 32));
        result = result * 31 + levelAccess.hashCode();
        result = result * 31 + firstName.hashCode();
        result = result * 31 + lastName.hashCode();
        result = result * 31 + login.hashCode();
        result = result * 31 + password;
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", levelAccess=" + levelAccess +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", login='" + login + '\'' +
                ", password=" + password +
                '}';
    }
}