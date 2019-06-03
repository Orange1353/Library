package ru.eltech.sapr.web.app.dao;

import ru.eltech.sapr.web.app.model.Phone;
import ru.eltech.sapr.web.app.model.PhoneType;

import java.util.List;

public interface PhoneDao {
    List<Phone> GetAllPhoneForUser(long userId);
    Phone getById(long id, long userId);
    Phone createPhone(String number, PhoneType type, long userId);
    boolean deletePhone(long phoneId, long userId);
    void updatePhone(Phone phone, long userId);
}
