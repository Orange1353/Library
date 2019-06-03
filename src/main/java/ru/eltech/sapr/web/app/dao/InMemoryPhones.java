package ru.eltech.sapr.web.app.dao;

import ru.eltech.sapr.web.app.model.Phone;
import ru.eltech.sapr.web.app.model.PhoneType;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public enum InMemoryPhones implements PhoneDao {
    INSTANCE;

    private AtomicLong idGenerator = new AtomicLong();
    private Map<Long, Map<Long, Phone>> allPhones = new HashMap<>();

    @Override
    public List<Phone> GetAllPhoneForUser(long contactId) {
        //  Collections.emptyMap()  //получение неизменяемой коллекции
        Collection<Phone> phones = allPhones.getOrDefault(contactId, Collections.emptyMap()).values();
        return new ArrayList<>(phones);
    }

    @Override
    public Phone getById(long id, long userId) {
        return allPhones.get(userId).get(id);
    }

    @Override
    public Phone createPhone(String number, PhoneType type, long userId) {
        long phoneId = idGenerator.incrementAndGet();
        Phone newPhone = new Phone(phoneId, number, type);
        allPhones.computeIfAbsent(userId, id-> new LinkedHashMap<>()).put(phoneId, newPhone); // положить новый список по ключу:
        return newPhone;
    }

    @Override
    public boolean deletePhone(long phoneId, long userId) {
        Phone removed = allPhones.getOrDefault(userId, Collections.emptyMap()).remove(phoneId);
        return removed != null;
    }

    @Override
    public void updatePhone(Phone phone, long userId) {
        allPhones.get(userId).put(phone.getId(), phone);
    }
}
