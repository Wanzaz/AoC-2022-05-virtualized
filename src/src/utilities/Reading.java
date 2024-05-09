package utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ondrejpazourek
 */
public class Reading {
	
	/**
	 * Reads the content of a file and returns it as a list of strings.
     *
  	 * @param filePath Path to the input file
	 * @return List of strings containing the content of the file
     * @throws IOException If an I/O error occurs
	 */
	public static List<String> readFileAsListOfStrings(String filePath) throws IOException {
		List<String> content = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				content.add(line);
			}
		}
		return content;
	}

	/**
	 * Finds the index of the first blank line in a list of strings.
	 *
	 * @param input List of strings to search for a blank line
	 * @return Index of the first blank line, or -1 if not found
	 */
	public static int findBlankIndex(List<String> input) {
		int blankIndex = -1;
		for (int i = 0; i < input.size(); ++i) {
			if (input.get(i).isEmpty()) {
				blankIndex = i;
				break;
			}
		}
		return blankIndex;
	}
}
