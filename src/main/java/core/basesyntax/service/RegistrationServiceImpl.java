package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {

    private static final int MIN_AGE = 18;
    private static final int MIN_SYMBOLS_LENGTH = 6;
    private final StorageDao storageDao;

    public RegistrationServiceImpl(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public User register(User user) {
        if (user.getLogin() == null || user.getLogin().length() < MIN_SYMBOLS_LENGTH) {
            throw new RegistrationException("Login must be at least 6 characters");
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_SYMBOLS_LENGTH) {
            throw new RegistrationException("Password must be at least 6 characters");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Age must be at least 18");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("Login is already taken");
        }
        return storageDao.add(user);
    }
}
