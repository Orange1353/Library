package ru.eltech.sapr.web.app.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ru.eltech.sapr.web.ConnectionManager;
import ru.eltech.sapr.web.app.dao.H2PhoneDao;
import ru.eltech.sapr.web.app.dao.H2UsersDao;
import ru.eltech.sapr.web.app.model.Phone;
import ru.eltech.sapr.web.app.model.PhoneType;
import ru.eltech.sapr.web.app.model.User;
import ru.eltech.sapr.web.app.model.UserType;
import ru.eltech.sapr.web.app.service.UserServiceImpl;

import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class UserServiceTest {
    private User adminUser = new User(100, UserType.ADMIN, "admin", "admin", "admin", 789);

    private User editorUser = new User(101, UserType.EDITOR, "editor", "editor", "editor", 789);

    private User user1 = new User(1, UserType.USER, "Ivan", "Ivanov", "Ivan@mail.ru", 522301);
    private User user2 = new User(2, UserType.USER, "Pavel", "Sidorov", "Pavel@mail.ru", 200012);

    private DataSource dataSource;
    private UserServiceImpl service;

    @Before
    public void setUp() throws Exception {
        dataSource = ConnectionManager.createDataSource();
        service = new UserServiceImpl(
                new H2UsersDao(dataSource),
                new H2PhoneDao(dataSource));
    }

    @Ignore
    public void getAllForAdmin() {
        List<User> expected = service.getAll(adminUser);

        List<User> actual = new ArrayList<>();
        actual.add(user1);
        actual.add(user2);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getAllForEditor() {
        try {
            List<User> expected = service.getAll(editorUser);
            fail();
        }
        catch (Exception e)
        {
            Assert.assertEquals(e.toString(), "java.lang.SecurityException");
        }
    }

    @Test
    public void getAllForUser() {
        try {
            List<User> expected = service.getAll(user1);
            fail();
        }

        catch (Exception e)
        {
            Assert.assertEquals(e.toString(), "java.lang.SecurityException");
        }
    }

    @Test
    public void getByIdForAdmin() {
        User user = service.getById(1, adminUser);

        Assert.assertEquals(user1, user);
    }

    @Test
    public void getByIdForEditor() {
        try {
            User user = service.getById(1, editorUser);
            fail();
        }
        catch (Exception e)
        {
            Assert.assertEquals(e.toString(), "java.lang.SecurityException");
        }
    }

    @Test
    public void getByIdForUser() {
        try {
            User user = service.getById(1, user2);
            fail();
        }
        catch (Exception e)
        {
            Assert.assertEquals(e.toString(), "java.lang.SecurityException");
        }
    }

    @Test
    public void create() {
        User user = service.create("Ilya", "Barbarich", "Ilya@mail.ru", "asdgrtgh");
        User actual = new User(user.getId(), UserType.USER, "Ilya", "Barbarich", "Ilya@mail.ru", "asdgrtgh".hashCode());
        Assert.assertEquals(user, actual);
    }

    @Test
    public void update() {
        User user = service.create("Ilya", "Barbarich", "Ilya@mail.ru", "asdgrtgh");
        User actual = new User(user.getId(),  UserType.USER, "updateIlya", "Barbarich", "Ilya@mail.ru", "asdgrtgh".hashCode());

        service.update(actual);
        User updatedUser = service.getById(user.getId(), adminUser);

        Assert.assertEquals(updatedUser, actual);
    }

    @Test
    public void updateUserTypeForAdmin() {
        User user = service.create("Ilya", "Barbarich", "Ilya@mail.ru", "asdgrtgh");
        User curUser = new User(user.getId(),  UserType.USER, "Ilya", "Barbarich", "Ilya@mail.ru", "asdgrtgh".hashCode());

        service.updateUserType(user.getId(), curUser, adminUser, UserType.EDITOR);
        User updatedUser = service.getById(user.getId(), adminUser);

        User actual =new User(user.getId(),  UserType.EDITOR, "Ilya", "Barbarich", "Ilya@mail.ru", "asdgrtgh".hashCode());

        Assert.assertEquals(updatedUser, actual);
    }

    @Test
    public void updateUserTypeForEditor() {
        try {
            User user = service.create("Ilya", "Barbarich", "Ilya@mail.ru", "asdgrtgh");
            User curUser = new User(user.getId(),  UserType.USER, "Ilya", "Barbarich", "Ilya@mail.ru", "asdgrtgh".hashCode());

            service.updateUserType(user.getId(), curUser, editorUser, UserType.EDITOR);
            fail();
        }
        catch (Exception e)
        {
            Assert.assertEquals(e.toString(), "java.lang.SecurityException");
        }
    }

    @Test
    public void updateUserTypeForUser() {
        try {
            User user = service.create("Ilya", "Barbarich", "Ilya@mail.ru", "asdgrtgh");
            User curUser = new User(user.getId(),  UserType.USER, "Ilya", "Barbarich", "Ilya@mail.ru", "asdgrtgh".hashCode());

            service.updateUserType(user.getId(), curUser, user1, UserType.EDITOR);
            fail();
        }
        catch (Exception e)
        {
            Assert.assertEquals(e.toString(), "java.lang.SecurityException");
        }
    }

    @Test
    public void delete() {
        User user = service.create("Ilya", "Barbarich", "Ilya@mail.ru", "asdgrtgh");

        boolean deleted = service.delete(user);
        Assert.assertTrue(deleted);
    }

    @Ignore
    public void getAllPhoneForUser() {
        Phone phone1 = new Phone(1, "+79999999999", PhoneType.MOBILE);
        Phone phone2 = new Phone(2, "+78888888889", PhoneType.MOBILE);
        List<Phone> actual = new ArrayList<>();
        actual.add(phone1);
        actual.add(phone2);

        List<Phone> phones = service.GetAllPhoneForUser(user1);

        Assert.assertEquals(phones, actual);
    }

    @Test
    public void createPhone() {
        Phone phone = service.createPhone(user1, "+8565453216", PhoneType.HOME);
        Phone actual = new Phone(phone.getId(), "+8565453216", PhoneType.HOME);
        Assert.assertEquals(phone, actual);
    }

    @Test
    public void deletePhone() {
        Phone phone = service.createPhone(user2, "+8565453216", PhoneType.HOME);

        boolean deleted = service.deletePhone(user2, phone.getId());
        Assert.assertTrue(deleted);
    }

    @Test
    public void updatePhone() {
        Phone phone = service.createPhone(user2, "+52", PhoneType.HOME);
        Phone actual = new Phone(phone.getId(), "+8565453216", PhoneType.HOME);

        service.updatePhone(user2, actual);
        Phone updatedUser  = service.getByIdPhone(phone.getId(), user2);

        Assert.assertEquals(updatedUser, actual);
    }
}