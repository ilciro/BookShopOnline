package laptop.controller;

import laptop.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerHomePageAfterLoginTest {
    private final User u= User.getInstance();
    @Test
    void logout() {

        u.setNome("admin");
       assertTrue( ControllerHomePageAfterLogin.logout());
    }
}