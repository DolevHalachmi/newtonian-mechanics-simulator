package io.github.dolevhalachmi.engine;

public class Parameters {
    private final double gravity = 9.81;

    private final double weight;   // N
    private final double mass;    // kg
    private final double muK;       // kinetic friction coefficient
    private final double angleRad;  // radians

    // simulation controls
    private final double dt;        // seconds
    private final double maxTime;   // seconds
    private final double slopeLength; // meters along the slope

    public Parameters(double weight, double muK, double angleDeg, double slope_len) {
        this(weight, muK, angleDeg, 0.01, 10.0, slope_len);
    }

    public Parameters(double weight, double muK, double angleDeg,
                      double dt, double maxTime, double slopeLength) {
        this.weight = weight;
        this.muK = muK;
        this.angleRad = Math.toRadians(angleDeg);

        this.mass = weight / gravity;

        if (dt <= 0) throw new IllegalArgumentException("dt must be > 0");
        if (maxTime <= 0) throw new IllegalArgumentException("maxTime must be > 0");
        if (slopeLength <= 0) throw new IllegalArgumentException("slopeLength must be > 0");

        this.dt = dt;
        this.maxTime = maxTime;
        this.slopeLength = slopeLength;
    }

    public double getGravity() { return gravity; }
    public double getWeight() { return weight; }
    public double getMass() { return mass; }
    public double getMuK() { return muK; }
    public double getAngleRad() { return angleRad; }

    public double getDt() { return dt; }
    public double getMaxTime() { return maxTime; }
    public double getSlopeLength() { return slopeLength; }
}
