package controller;

import laptop.controller.ControllerHomePageAfterLoginSE;
import laptop.exception.LogoutException;
import laptop.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ControllerHomePageAfterLoginSETest {
    private final User u=User.getInstance();

    @Test
    void logoutS() throws LogoutException {
        u.setNome("zerocalcare");
        assertTrue(ControllerHomePageAfterLoginSE.logout());
    }

    @Test
    void logoutE() throws LogoutException {
        u.setNome("Bao Publishing");
        assertTrue(ControllerHomePageAfterLoginSE.logout());

    }
}