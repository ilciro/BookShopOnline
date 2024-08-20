package controller.dao;

import laptop.controller.ControllerHomePageAfterLogin;
import laptop.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ControllerHomePageAfterLoginTest {
    private final User u= User.getInstance();
    @Test
    void logout() {

        u.setNome("admin");
       assertTrue( ControllerHomePageAfterLogin.logout());
    }
}