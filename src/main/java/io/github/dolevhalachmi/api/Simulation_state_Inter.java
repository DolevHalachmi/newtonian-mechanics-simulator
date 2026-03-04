package io.github.dolevhalachmi.api;

public interface Simulation_state_Inter {


    
    /*
     * the fomula being implemented  
     *
     * v_{n+1} = v_n + a * dt
     * x_{n+1} = x_n + v_{n+1} * dt
    */

    void step(double acceleration, double dt);
}
