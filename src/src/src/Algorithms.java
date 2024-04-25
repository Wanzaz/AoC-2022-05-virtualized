package src;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author ondrejpazourek
 */
public class Algorithms {

    // Regular expression pattern to match instruction lines
    private static final Pattern INSTRUCTION_PATTERN = Pattern.compile("^move (\\d+) from (\\d+) to (\\d+)$");

/**
     * Executes the Day 05-1 algorithm.
     *
     * @param userInputFile Path to the input file
     * @return The result of the algorithm
     * @throws IOException          If an I/O error occurs
     * @throws InputFormatException If there is a format issue with the input
     * @throws InputDataException   If there is an issue with the input data
     */
    public static String day_05_1(String userInputFile) throws IOException, InputFormatException, InputDataException {
        List<String> input = Utilities.readFileAsListOfStrings(userInputFile);
        int blankIndex = Utilities.findBlankIndex(input);
		if (blankIndex == -1) {
			throw new InputFormatException("Blank line missing in input.");
		}
		
        List<Stack<Character>> stacks = initializeStacks(input, blankIndex);

        processInstructions(input, blankIndex, stacks);

        return getResult(stacks);
    }

    /**
     * Initializes the stacks based on the input.
     *
     * @param input      List of input lines
     * @param blankIndex Index of the blank line in the input
     * @return List of initialized stacks
     * @throws InputFormatException If there is a format issue with the input
     */
	private static List<Stack<Character>> initializeStacks(List<String> input, int blankIndex) throws InputFormatException, InputDataException {
		List<Stack<Character>> stacks = new ArrayList<>();
		String stackLabels = input.get(blankIndex - 1);

		// Iterate over stack labels
		for (int i = 0; i < stackLabels.length(); ++i) {
			char label = stackLabels.charAt(i);
			if (label == ' ') {
				continue; // Skip spaces
			}

			Stack<Character> currentStack = new Stack<>();
			// Iterate over lines to populate the stack
			for (int lineIndex = blankIndex - 2; lineIndex >= 0; --lineIndex) {
				String line = input.get(lineIndex);
				if (i < line.length()) {
					char crate = line.charAt(i);
					if (crate == ' ') {
						break; // Empty space, stop adding to current stack
					}
					if (!String.valueOf(crate).matches("[A-Z]")) {
						throw new InputFormatException("Invalid crate label in  : " + crate + " in stack " + label);
					}
					if (currentStack.contains(crate)) {
						throw new InputFormatException("Duplicate crate found: " + crate + " in stack " + label);
					}
					currentStack.push(crate);
				}
			}
			if (currentStack.isEmpty()) {
				throw new InputDataException("Empty stack detected.");
			}
			stacks.add(currentStack);
		}
		return stacks;
	}


    /**
     * Processes the instructions and moves crates between stacks.
     *
     * @param input      List of input lines
     * @param blankIndex Index of the blank line in the input
     * @param stacks     List of stacks representing crates
     * @throws InputFormatException If there is a format issue with the input
     * @throws InputDataException   If there is an issue with the input data
     */
    private static void processInstructions(List<String> input, int blankIndex, List<Stack<Character>> stacks) throws InputFormatException, InputDataException {
        for (int i = blankIndex + 1; i < input.size(); ++i) {
            Matcher matcher = INSTRUCTION_PATTERN.matcher(input.get(i));
            if (matcher.find()) {
                int count = Integer.parseInt(matcher.group(1));
                int from = Integer.parseInt(matcher.group(2)) - 1;
                int to = Integer.parseInt(matcher.group(3)) - 1;

                if (count <= 0 || from < 0 || to < 0) {
                    throw new InputFormatException("Invalid instruction format at line " + (i + 1));
                }
                if (from >= stacks.size() || to >= stacks.size()) {
                    throw new InputDataException("Invalid stack index in the instruction at line " + (i + 1));
                }
                if (stacks.get(from).isEmpty()) {
                    throw new InputDataException("Cannot move crates from an empty stack at line " + (i + 1));
                }
                if (count > stacks.get(from).size()) {
                    throw new InputDataException("Not enough crates to move in stack " + (from + 1) + " at line " + (i + 1));
                }

                for (int j = 1; j <= count; ++j) {
                    char crate = stacks.get(from).pop();
                    stacks.get(to).push(crate);
                }
            }
        }
    }

    /**
     * Constructs the result by accessing the top crate of each stack.
     *
     * @param stacks List of stacks representing crates
     * @return The result string
     */
    private static String getResult(List<Stack<Character>> stacks) {
        StringBuilder result = new StringBuilder();
        for (Stack<Character> currentStack : stacks) {
            result.append(currentStack.peek());
        }
        return result.toString();
    }
}