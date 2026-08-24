package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.*;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private RegistrationService registrationService;
    private StorageDao storageDao = new StorageDaoImpl();
    private User user;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl(storageDao);
        user = new User();
        user.setLogin("johnathan");
        user.setPassword("password");
        user.setAge(21);
    }

    @Test
    void register_shortLogin_notOk() {
        user.setLogin("abc");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        user.setPassword("abcd");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_youngAge_notOk() {
        user.setAge(17);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_validData_Ok() {
        User registered = registrationService.register(user);
        assertNotNull(registered);
        assertEquals(user.getLogin(), registered.getLogin());
    }

    @Test
    void register_loginNotNull_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_passwordNotNull_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_passwordNotEmpty_notOk() {
        user.setPassword("");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_loginNotEmpty_notOk() {
        user.setLogin("");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_ageWrong_notOk() {
        user.setAge(-18);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_edgeAge_Ok() {
        user.setAge(18);
        registrationService.register(user);
        assertNotNull(storageDao.get(user.getLogin()));
    }

    @Test
    void register_alreadyExists_notOk() {
        registrationService.register(user);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_shortPasswordEdge_notOk() {
        user.setPassword("abcde");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }
    @Test
    void register_validPassword_Ok() {
        user.setPassword("password");
        User registered = registrationService.register(user);
        assertNotNull(registered);
        assertEquals(user.getLogin(), registered.getLogin());
    }
    @Test
    void register_validLogin_Ok() {
        user.setLogin("validLog");
        User registered = registrationService.register(user);
        assertNotNull(registered);
        assertEquals(user.getLogin(), registered.getLogin());
    }
}
