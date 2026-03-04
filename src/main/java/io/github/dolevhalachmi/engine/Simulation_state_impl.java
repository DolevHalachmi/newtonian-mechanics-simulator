package io.github.dolevhalachmi.engine;

import io.github.dolevhalachmi.api.Simulation_state_Inter;

public class Simulation_state_impl implements Simulation_state_Inter {
    private double time;       // s
    private double position;   // m (along slope)
    private double velocity;   // m/s

    public Simulation_state_impl() {
        this.time = 0.0;
        this.position = 0.0;
        this.velocity = 0.0;
    }

    public double getTime() { return time; }
    public double getPosition() { return position; }
    public double getVelocity() { return velocity; }

   
    /*
     * v_{n+1} = v_n + a * dt
     * x_{n+1} = x_n + v_{n+1} * dt
    */

    public void step(double acceleration, double dt) {
        velocity = velocity + acceleration * dt;
        position = position + velocity * dt;
        time = time + dt;
    }
}
