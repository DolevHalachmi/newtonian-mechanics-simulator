package io.github.dolevhalachmi.engine;

import java.util.Scanner;

public class Main {

    private static final char mu = '\u00b5';

    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        double weight = read(scanner, "Enter weight (Newtons, N): ", "PositiveDouble");
        double muK = read(scanner, "Enter kinetic friction coefficient " + mu + "k (>= 0): ", "NonNegativeDouble");
        double angleDeg = read(scanner, "Enter slope angle in degrees (0.1-90): ", "Angle");
        double slopeLen = read(scanner, "Enter slope length (0.1-8 M): ", "positive below 8");

        System.out.printf("Weight: %.1f N%n", weight);
        System.out.printf(mu + "k: %.1f%n", muK);
        System.out.printf("Angle: %.1f degrees%n", angleDeg);
        System.out.printf("Length: %.1f M%n", slopeLen);

        scanner.close();

        Parameters params = new Parameters(weight, muK, angleDeg, slopeLen);

        Csv_Exporter saver = new Csv_Exporter();
        saver.run(params, "data.csv");
    }


    private static double read(Scanner scanner, String prompt, String action) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();

            final double value;
            try {
                value = Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            if (value < 0) {
                System.out.println("Invalid input. Please enter a non-negative number.");
                continue;
            }

            if (value == 0 && !action.equals("NonNegativeDouble")) {
                System.out.println("Invalid input. Please enter a positive number.");
                continue;
            }

            if (action.equals("Angle") && (value <= 0 || value > 90)) {
                System.out.println("Invalid input. Please enter a number between 0 (exclusive) and 90 (inclusive).");
                continue;
            }

            if (action.equals("positive below 8") && (value <= 0 || value > 8)) {
                System.out.println("Invalid input. Please enter a number between 0 (exclusive) and 8 (inclusive).");
                continue;
            }

            return value;
        }
    }
}