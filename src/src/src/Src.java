package src;

import java.io.IOException;

/**
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
            e.printStackTrace();
        }
    }

	/**
	 *
	 * @param userInputFile
	 * @return
	 * @throws IOException
	 */
	public static String performAlgorithm(String userInputFile) throws IOException {
        // Call the algorithm function from Algorithms class
        return Algorithms.day_05_1(userInputFile);
    }
	
}
