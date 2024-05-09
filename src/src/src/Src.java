package src;

import exceptions.InputDataException;
import exceptions.InputFormatException;
import java.io.IOException;

/**
 * Main class to run the algorithm.
 *
 * @author ondrejpazourek
 */
public class Src {

    public static void main(String[] args) throws IllegalArgumentException {
        if (args.length != 1) {
            System.err.println("Usage: java Main <input_file>");
            throw new IllegalArgumentException("Invalid number of arguments");
        }

        String userInputFile = args[0];
        
        // Regular expression to match "data.txt", "name/data.txt", "dir/name/data.txt", etc.
        String regex = "(?:\\w+/)*\\w+\\.txt$";
        
        if (!userInputFile.matches(regex)) {
            throw new IllegalArgumentException("Input file must be in the format nameOfFile.txt or nested in directory like this data/nameOfFile.txt");
        }

        try {
            String result = performAlgorithm(userInputFile);
            System.out.println("Result: " + result);
        } catch (IOException e) {
            System.err.println("An error occurred while reading the input file: " + e.getMessage());
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

