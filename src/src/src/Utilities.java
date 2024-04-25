package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Utilities {
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
