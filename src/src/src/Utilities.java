package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utilities {
    public static List<String> splitStringByNewline(String inputString) {
        List<String> result = new ArrayList<>();
        Scanner scanner = new Scanner(inputString);

        while (scanner.hasNextLine()) {
            result.add(scanner.nextLine());
        }
        
        scanner.close();
        return result;
    }

}
