package io.github.dolevhalachmi.engine;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvExporterTest {

    @TempDir
    Path tempDir;

    @Test
    void writesHeaderAndInitialRowEvenWhenAccelerationNonPositive() throws IOException {
        // Pick parameters where gravityAlong <= frictionForce so acceleration <= 0
        // Example: very low angle, high muK.
        Parameters p = new Parameters(
                10.0,   // weight N
                10.0,   // muK
                1.0,    // angle degrees
                0.01,
                10.0,
                1.0
        );

        Path out = tempDir.resolve("data.csv");
        new Csv_Exporter().run(p, out.toString());

        List<String> lines = Files.readAllLines(out);
        assertEquals(2, lines.size(), "Should contain header + one data row");

        assertTrue(lines.get(0).startsWith("time,position,velocity,acceleration"),
                "Header should be present");
    }

    @Test
    void frictionlessAccelerationMatches_gSinTheta_AndThetaWrittenInRadians() throws IOException {
        double angleDeg = 30.0;
        Parameters p = new Parameters(
                10.0,  // weight N (mass cancels out in acceleration)
                0.0,   // muK
                angleDeg,
                0.1,   // dt
                1.0,   // maxTime
                1.0    // slopeLength
        );

        Path out = tempDir.resolve("data.csv");
        new Csv_Exporter().run(p, out.toString());

        List<String> lines = Files.readAllLines(out);
        assertTrue(lines.size() >= 2, "Expected at least header + initial row");

        // Parse first data row (initial row)
        String[] cols = lines.get(1).split(",");
        assertEquals(8, cols.length, "Expected 8 CSV columns");

        double acceleration = Double.parseDouble(cols[3]);
        double thetaRadWritten = Double.parseDouble(cols[7]);

        double expectedAcc = p.getGravity() * Math.sin(Math.toRadians(angleDeg));
        assertEquals(expectedAcc, acceleration, 1e-9);

        assertEquals(Math.toRadians(angleDeg), thetaRadWritten, 1e-12);
    }
}
