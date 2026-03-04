package io.github.dolevhalachmi.api;

import java.io.IOException;

import io.github.dolevhalachmi.engine.Parameters;

public interface Csv_Exporter_Inter {

    /*
     *   writes a new file with the name @param dataFile
     *   consisting the values: time, position, velocity, acceleration, kinetic_energy,
     *   friction_force, total_force, theta.
    */
    void run(Parameters params, String dataFile) throws IOException;
}
