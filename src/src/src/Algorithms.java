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

    // Record representing an instruction
    private record Instruction(int count, int from, int to) {}

    /**
     * Executes the Day 05-1 algorithm.
     *
     * @param userInputFile Path to the input file
     * @return The result of the algorithm
     * @throws IOException If an I/O error occurs
     */
    public static String day_05_1(String userInputFile) throws IOException {
        List<String> input = Utilities.readFileAsListOfStrings(userInputFile);
        int blankIndex = Utilities.findBlankIndex(input);

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
     */
    private static List<Stack<Character>> initializeStacks(List<String> input, int blankIndex) {
        List<Stack<Character>> stacks = new ArrayList<>();
        String stackLabels = input.get(blankIndex - 1);
        for (int i = 0; i < stackLabels.length(); ++i) {
            if (stackLabels.charAt(i) == ' ') {
                continue;
            }
            Stack<Character> currentStack = new Stack<>();
            for (int lineIndex = blankIndex - 2; lineIndex >= 0; --lineIndex) {
                String line = input.get(lineIndex);
                if (i < line.length()) {
                    char crate = line.charAt(i);
                    if (crate == ' ') {
                        break;
                    }
                    currentStack.push(crate);
                }
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
     */
    private static void processInstructions(List<String> input, int blankIndex, List<Stack<Character>> stacks) {
        for (int i = blankIndex + 1; i < input.size(); ++i) {
            Matcher matcher = INSTRUCTION_PATTERN.matcher(input.get(i));
            if (matcher.find()) {
                Instruction instruction = new Instruction(
                        Integer.parseInt(matcher.group(1)),
                        Integer.parseInt(matcher.group(2)) - 1,
                        Integer.parseInt(matcher.group(3)) - 1
                );

                for (int j = 1; j <= instruction.count(); ++j) {
                    char crate = stacks.get(instruction.from()).pop();
                    stacks.get(instruction.to()).push(crate);
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

