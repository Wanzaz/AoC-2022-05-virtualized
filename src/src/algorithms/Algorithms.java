package algorithms;

import exceptions.InputDataException;
import exceptions.InputFormatException;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import utilities.Reading;

/**
 *
 * @author ondrejpazourek
 */
public class Algorithms {

    private List<Stack<Character>> stacks = new ArrayList<>();
	private final int blankIndex;
    private final int numberOfInstructions;

	// Regular expression pattern to match instruction lines
    private static final Pattern INSTRUCTION_PATTERN = Pattern.compile("^move (\\d+) from (\\d+) to (\\d+)$");
	
	// Record representing an instruction
	private record Instruction(int count, int from, int to) {}

    /**
     * Constructs an instance of the Algorithms class.
     *
     * @param input               List of input lines
     * @param numberOfInstructions Number of instructions in the input
     * @throws InputFormatException If there is a format issue with the input
     * @throws InputDataException   If there is an issue with the input data
     */
    public Algorithms(List<String> input, int numberOfInstructions) throws InputFormatException, InputDataException {
        this.numberOfInstructions = numberOfInstructions;
		this.blankIndex = Reading.findBlankIndex(input);
        this.stacks = initializeStacks(input);
    }

    /**
     * Initializes the stacks based on the input.
     *
     * @param input List of input lines
     * @return List of initialized stacks
     * @throws InputFormatException If there is a format issue with the input
     * @throws InputDataException   If there is an issue with the input data
     */
    private List<Stack<Character>> initializeStacks(List<String> input) throws InputFormatException, InputDataException {
        // Find the index of the blank line
		if (this.blankIndex == -1) {
			throw new InputFormatException("Blank line missing in input.");
		}

        // Get stack labels from the input
        String stackLabels = input.get(this.blankIndex - 1);

        // Iterate over stack labels and initialize stacks
        for (int i = 0; i < stackLabels.length(); i++) {
            char label = stackLabels.charAt(i);
            if (label == ' ') {
                continue;
            }
            Stack<Character> currentStack = new Stack<>();
            for (int j = this.blankIndex - 2; j >= 0; j--) {
                String line = input.get(j);
                if (i < line.length()) {
                    char crate = line.charAt(i);
                    if (crate == ' ') {
                        break;
                    }
                    if (!String.valueOf(crate).matches("[A-Z]")) {
                        throw new InputFormatException("Invalid crate label: " + crate + " in stack" + label);
                    }
                    if (currentStack.contains(crate)) {
                        throw new InputDataException("Duplicate crate found: " + crate + " in stack" + label);
                    }
                    currentStack.push(crate);
                }
            }
            if (currentStack.isEmpty()) {
                throw new InputDataException("Empty stack detected.");
            }
            this.stacks.add(currentStack);
        }

        return this.stacks;
    }

    /**
     * Executes the next instruction.
     *
     * @param instruction The instruction to execute
     * @throws InputFormatException If there is a format issue with the instruction
     * @throws InputDataException   If there is an issue with the input data
     */
	public void executeNextInstruction(String instruction) throws InputFormatException, InputDataException {
		Matcher matcher = INSTRUCTION_PATTERN.matcher(instruction);
		if (matcher.find()) {
			Instruction instructionRecord = new Instruction(
				Integer.parseInt(matcher.group(1)),
				Integer.parseInt(matcher.group(2)) - 1,
				Integer.parseInt(matcher.group(3)) - 1
			);

			if (instructionRecord.count() <= 0 || instructionRecord.from() < 0 || instructionRecord.to() < 0
				|| instructionRecord.from() >= stacks.size() || instructionRecord.to() >= stacks.size()) {
				throw new InputFormatException("Invalid instruction format.");
			}
			if (stacks.get(instructionRecord.from()).isEmpty()) {
				throw new InputDataException("Cannot move crates from an empty stack.");
			}
			if (instructionRecord.count() > stacks.get(instructionRecord.from()).size()) {
				throw new InputDataException("Not enough crates to move in stack " + (instructionRecord.from() + 1));
			}

			for (int i = 0; i < instructionRecord.count(); i++) {
				char crate = stacks.get(instructionRecord.from()).pop();
				stacks.get(instructionRecord.to()).push(crate);
			}
		} else {
			throw new InputFormatException("Invalid instruction format.");
		}
	}
	
	/**
	 * Processes all instructions starting from the line after the blank line.
	 *
	 * @param instructions The list of instructions to process
	 * @throws InputFormatException If there is a format issue with the
	 * instruction
	 * @throws InputDataException If there is an issue with the input data
	 */
	public void processAllInstructions(List<String> input) throws InputFormatException, InputDataException {
		for (int i = blankIndex + 1; i < input.size(); ++i) {
			executeNextInstruction(input.get(i));
		}
	}
	


    /**
     * Gets the current stacks.
     *
     * @return The list of stacks representing crates
     */
    public List<Stack<Character>> getStacks() {
        return stacks;
    }

    /**
     * Gets the number of instructions.
     *
     * @return The number of instructions
     */
    public int getNumberOfInstructions() {
        return numberOfInstructions;
    }

    /**
     * Constructs the result by accessing the top crate of each stack.
     *
     * @return The result string
     */
    public String getResult() {
        StringBuilder result = new StringBuilder();
        for (Stack<Character> stack : stacks) {
            result.append(stack.peek());
        }
        return result.toString();
    }
	
	/**
	 * Counts the total number of instructions.
	 *
	 * @param input List of input lines
	 * @return The total number of instructions
	 */
	public static int countTotalInstructions(List<String> input) {
		int totalInstructions = 0;
		for (String line : input) {
			if (line.startsWith("move")) {
				totalInstructions++;
			}
		}
		return totalInstructions;
	}

}
