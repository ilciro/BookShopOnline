package laptop.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ControllerRicercaPerTipoTest {
    private final ControllerRicercaPerTipo cRT=new ControllerRicercaPerTipo();

    @ParameterizedTest
    @ValueSource(strings = {"libro", "giornale", "rivista"})
    void setRicerca(String strings) {
        assertTrue(cRT.setRicerca(strings));
    }
}