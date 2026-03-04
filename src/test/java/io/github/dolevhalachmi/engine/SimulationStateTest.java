package io.github.dolevhalachmi.engine;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class SimulationStateTest {

    @Test
    void stepUpdatesTimeVelocityAndPositionUsingSemiImplicitEuler() {
        Simulation_state_impl s = new Simulation_state_impl();

        double a = 2.0;   // m/s^2
        double dt = 0.5;  // s

        // v1 = 0 + a*dt = 1.0
        // x1 = 0 + v1*dt = 0.5
        // t1 = 0 + dt = 0.5
        s.step(a, dt);

        assertEquals(0.5, s.getTime(), 1e-12);
        assertEquals(1.0, s.getVelocity(), 1e-12);
        assertEquals(0.5, s.getPosition(), 1e-12);

        // second step:
        // v2 = 1.0 + 2*0.5 = 2.0
        // x2 = 0.5 + 2.0*0.5 = 1.5
        // t2 = 1.0
        s.step(a, dt);

        assertEquals(1.0, s.getTime(), 1e-12);
        assertEquals(2.0, s.getVelocity(), 1e-12);
        assertEquals(1.5, s.getPosition(), 1e-12);
    }
}
