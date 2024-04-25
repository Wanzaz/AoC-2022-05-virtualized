package src;

import java.io.IOException;

/**
 * Main class to run the algorithm.
 *
 * @author ondrejpazourek
 */
public class Src {

    public static void main(String[] args) {
        try {
            String userInputFile = "data/original_data.txt";
            String result = performAlgorithm(userInputFile);
            System.out.println("Result: " + result);
        } catch (IOException e) {
        } catch (InputFormatException | InputDataException e) {
            System.err.println("An error occurred while processing the input data: " + e.getMessage());
        }
    }

    /**
     * Perform the algorithm.
     *
     * @param userInputFile Path to the input file
     * @return The result of the algorithm
     * @throws IOException          If an I/O error occurs
     * @throws InputFormatException If there is a format issue with the input
     * @throws InputDataException   If there is an issue with the input data
     */
    public static String performAlgorithm(String userInputFile) throws IOException, InputFormatException, InputDataException {
        // Call the algorithm function from Algorithms class
        return Algorithms.day_05_1(userInputFile);
    }
}

