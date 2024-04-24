package src;
import java.io.IOException;
import java.util.*;

/**
 *
 * @author ondrejpazourek
 */
public class Algorithms {
    public static String day_05_1(String userInputFile) throws IOException {
		// Split the user input into individual lines
		List<String> input = Utilities.readFileAsListOfStrings(userInputFile);

		// Find the index of the empty line in the input
		int blankIndex = -1;
		for (int i = 0; i < input.size(); ++i) {
			if (input.get(i).isEmpty()) { // Check for blank line
				blankIndex = i;
				break;
			}
		}

		// Setup initial state of stacks based on the input
		List<Stack<Character>> stacks = new ArrayList<>();
		String stackLabels = input.get(blankIndex - 1);
		for (int i = 0; i < stackLabels.length(); ++i) {
			if (stackLabels.charAt(i) == ' ') {
				continue; // Skip spaces
			}

			// Create a new stack for each label
			Stack<Character> currentStack = new Stack<>();
			for (int lineIndex = blankIndex - 2; lineIndex >= 0; --lineIndex) {
				// Traverse lines above the blank line and push crates onto the stack
				String line = input.get(lineIndex);
				char crate = line.charAt(i);
				if (crate == ' ') {
					break; // Exit loop if crate is empty
				}
				currentStack.push(crate);
			}
			stacks.add(currentStack); // Add the constructed stack to the list
		}

		// Process moves based on the input instructions
		for (int i = blankIndex + 1; i < input.size(); ++i) {
			// Split the instruction line into tokens
			String[] tokens = input.get(i).split(" ");
			int amount = Integer.parseInt(tokens[1]);
			int from = Integer.parseInt(tokens[3]) - 1; // Convert 1-based index to 0-based index
			int to = Integer.parseInt(tokens[5]) - 1; // Convert 1-based index to 0-based index

			// Move the specified number of crates from one stack to another
			for (int j = 1; j <= amount; ++j) {
				char crate = stacks.get(from).pop(); // Remove crate from source stack
				stacks.get(to).push(crate); // Push crate onto destination stack
			}
		}

		// Access the top element of each stack and append it to the result
		StringBuilder result = new StringBuilder();
		for (Stack<Character> currentStack : stacks) {
			result.append(currentStack.peek()); // Append the top crate of each stack
		}

		return result.toString(); // Return the final result
	}

}
