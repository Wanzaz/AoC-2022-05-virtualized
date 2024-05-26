package utilities;

import algorithms.InternetSolutionAlgorithms;
import algorithms.PureAlgorithms;
import exceptions.InputDataException;
import exceptions.InputFormatException;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

/**
 * Utility class for timing algorithm execution.
 */
public class AlgorithmTimer {

    /**
     * The number of iterations to run the algorithm for timing.
     */
    public static final int ITERATIONS = 1000;

    /**
     * Times the execution of the specified algorithm for the given number of iterations.
     *
     * @param userInputFile Path to the input file
     * @return The average execution time in milliseconds
     * @throws IOException          If an I/O error occurs
     * @throws InputFormatException If there is a format issue with the input
     * @throws InputDataException   If there is an issue with the input data
     */
    public static double timeAlgorithm(String userInputFile) throws IOException, InputFormatException, InputDataException {
        long totalDuration = 0;

        for (int i = 0; i < ITERATIONS; i++) {
            PureAlgorithms pureAlgorithms = new PureAlgorithms();

            Instant start = Instant.now();
            pureAlgorithms.day_05_1(userInputFile);
            Instant end = Instant.now();
            totalDuration += Duration.between(start, end).toMillis();
        }

        return totalDuration / (double) ITERATIONS;
    }
	
	/**
	 * Times the execution of the InternetSolutionAlgorithms.day_05_1_internet
	 * method for the given number of iterations.
	 *
	 * @param userInputFile Path to the input file
	 * @return The average execution time in milliseconds
	 * @throws IOException If an I/O error occurs
	 * @throws InputFormatException If there is a format issue with the input
	 * @throws InputDataException If there is an issue with the input data
	 */
	public static double timeInternetSolutionAlgorithm(String userInputFile) throws IOException, InputFormatException, InputDataException {
		long totalDuration = 0;

		for (int i = 0; i < ITERATIONS; i++) {
			InternetSolutionAlgorithms internetAlgorithms = new InternetSolutionAlgorithms();

			Instant start = Instant.now();
			internetAlgorithms.day_05_1_internet(userInputFile);
			Instant end = Instant.now();
			totalDuration += Duration.between(start, end).toMillis();
		}

		return totalDuration / (double) ITERATIONS;
	}
}
