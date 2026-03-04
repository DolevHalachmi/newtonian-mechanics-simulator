package io.github.dolevhalachmi.engine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParametersTest {

    @Test
    void massIsWeightDividedByGravity() {
        Parameters p = new Parameters(98.1, 0.0, 30.0, 0.01, 10.0, 5.0);
        assertEquals(10.0, p.getMass(), 1e-9);
    }

    @Test
    void angleDegIsConvertedToRadians() {
        Parameters p = new Parameters(10.0, 0.0, 90.0, 0.01, 10.0, 5.0);
        assertEquals(Math.PI / 2.0, p.getAngleRad(), 1e-12);
    }

    @Test
    void rejectsInvalidSimulationControls() {
        assertThrows(IllegalArgumentException.class,
                () -> new Parameters(10.0, 0.0, 30.0, 0.0, 10.0, 1.0));
        assertThrows(IllegalArgumentException.class,
                () -> new Parameters(10.0, 0.0, 30.0, 0.01, 0.0, 1.0));
        assertThrows(IllegalArgumentException.class,
                () -> new Parameters(10.0, 0.0, 30.0, 0.01, 10.0, 0.0));
    }
}
