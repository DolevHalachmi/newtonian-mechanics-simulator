package io.github.dolevhalachmi.engine;

import java.io.FileWriter;
import java.io.IOException;

import io.github.dolevhalachmi.api.Csv_Exporter_Inter;

public class Csv_Exporter implements Csv_Exporter_Inter {

    @Override
    public void run(Parameters params, String dataFile) throws IOException {

        double gravity = params.getGravity();
        double theta = params.getAngleRad();
        double muK = params.getMuK();
        double mass = params.getMass();

        double dt = params.getDt();
        double maxTime = params.getMaxTime();
        double slopeLength = params.getSlopeLength();

        double gravityAlong = mass * gravity * Math.sin(theta);         // N
        double normal = mass * gravity * Math.cos(theta);               // N
        double frictionForce = muK * normal;                            // N (magnitude)

        double totalForce = gravityAlong - frictionForce;               // N
        double acceleration = totalForce / mass;                        // m/s^2

        Simulation_state_impl state = new Simulation_state_impl();

        try (FileWriter writer = new FileWriter(dataFile)) {
            writer.write("time,position,velocity,acceleration,kinetic_energy,friction_force,total_force,theta\n");

            // Always write initial row
                writer.write(
                state.getTime() + "," +
                state.getPosition() + "," +
                state.getVelocity() + "," +
                acceleration + "," +
                0.0 + "," +
                frictionForce + "," +
                totalForce + "," +
                theta + "\n"
            );

            if (acceleration <= 0) {
                System.out.println("Acceleration <= 0. With these inputs (and no static friction model), the body will not slide from rest.");
                System.out.println("Simulation complete. Data saved to " + dataFile);
                return;
            }

            while (state.getTime() < maxTime && state.getPosition() < slopeLength) {

                state.step(acceleration, dt);

                double velocity = state.getVelocity();                      // m/s
                double kineticEnergy = 0.5 * mass * velocity * velocity;    // J

                writer.write(
                    state.getTime() + "," +
                    state.getPosition() + "," +
                    velocity + "," +
                    acceleration + "," +
                    kineticEnergy + "," +
                    frictionForce + "," +
                    totalForce + "," + 
                    theta + "\n"
                );
            }
        }

        System.out.println("Simulation complete. Data saved to " + dataFile);
    }
}
