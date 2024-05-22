package algorithms;

import exceptions.InputDataException;
import exceptions.InputFormatException;
import utilities.Reading;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.table.DefaultTableModel;

/**
 * 
 * @author ondrejpazourek
 * 
 * Represents a class containing algorithms for crate movement.
 */
public class Algorithms {

    private List<Stack<Character>> stacks = new ArrayList<>();
    private List<String> input;
    public final int blankIndex;
    private final int numberOfInstructions;
    public int currentInstruction;

	
	/**
     * Represents an instruction for moving crates.
     */
    private record Instruction(int count, int from, int to) {}
	
    private static final Pattern INSTRUCTION_PATTERN = Pattern.compile("^move (\\d+) from (\\d+) to (\\d+)$");

    /**
     * Constructs an instance of the Algorithms class with the specified input and number of instructions.
     *
     * @param input               List of input lines
     * @param numberOfInstructions Number of instructions in the input
     * @throws InputFormatException If there is a format issue with the input
     * @throws InputDataException   If there is an issue with the input data
     */
    public Algorithms(List<String> input, int numberOfInstructions) throws InputFormatException, InputDataException {
        this.input = input;
        this.numberOfInstructions = numberOfInstructions;
        this.blankIndex = Reading.findBlankIndex(input);
        this.stacks = initializeStacks();
        this.currentInstruction = blankIndex + 1;
    }

    /**
     * Constructs an instance of the Algorithms class with the specified input.
     *
     * @param input List of input lines
     * @throws InputFormatException If there is a format issue with the input
     * @throws InputDataException   If there is an issue with the input data
     */
    public Algorithms(List<String> input) throws InputFormatException, InputDataException {
        this.input = input;
        this.numberOfInstructions = countTotalInstructions();
        this.blankIndex = Reading.findBlankIndex(input);
        this.stacks = initializeStacks();
        this.currentInstruction = blankIndex + 1;
    }

    /**
     * Initializes the stacks based on the input.
     *
     * @return List of initialized stacks
     * @throws InputFormatException If there is a format issue with the input
     * @throws InputDataException   If there is an issue with the input data
     */
    private List<Stack<Character>> initializeStacks() throws InputFormatException, InputDataException {
        if (this.blankIndex == -1) {
            throw new InputFormatException("Blank line missing in input.");
        }

        String stackLabels = input.get(this.blankIndex - 1);

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
     * @throws InputFormatException If there is a format issue with the instruction
     * @throws InputDataException   If there is an issue with the input data
     */
    public void processAllInstructions() throws InputFormatException, InputDataException {
        for (int i = blankIndex + 1; i < input.size(); ++i) {
            executeNextInstruction(input.get(i));
        }
    }

    /**
     * Performs the next step in the algorithm.
     *
     * @return True if a step was performed, false if no more steps are available
     * @throws InputFormatException If there is a format issue with the instruction
     * @throws InputDataException   If there is an issue with the input data
     */
    public boolean performOneStep() throws InputFormatException, InputDataException {
        if (currentInstruction < input.size()) {
            executeNextInstruction(input.get(currentInstruction));
            currentInstruction++;
            return true;
        } else {
            return false; // No more steps to perform
        }
    }

    /**
     * Performs the next 10 steps in the algorithm.
     *
     * @return True if all 10 steps were performed, false if fewer than 10 steps were performed
     * @throws InputFormatException If there is a format issue with any instruction
     * @throws InputDataException   If there is an issue with the input data
     */
    public boolean performTenSteps() throws InputFormatException, InputDataException {
        int stepsPerformed = 0;

        for (int i = 0; i < 10; i++) {
            if (performOneStep()) {
                stepsPerformed++;
            } else {
                break; // No more steps to perform
            }
        }

        return stepsPerformed == 10;
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
     * @return The total number of instructions
     */
    public int countTotalInstructions() {
        int totalInstructions = 0;
        for (String line : input) {
            if (line.startsWith("move")) {
                totalInstructions++;
            }
        }
        return totalInstructions;
    }

    /**
     * Gets the label of the stack at the specified index.
     *
     * @param index The index of the stack
     * @return The label of the stack
     */
    public String getStackLabel(int index) {
        return String.valueOf(index + 1); // Assuming the labels start from 1
    }

    /**
     * Retrieves the data array and column names for creating a table model.
     *
     * @return An array containing the data array and the column names array
     */
    public Object[] getDataArrayAndColumnNames() {
        int maxStackSize = 0;
        for (Stack<Character> stack : stacks) {
            maxStackSize = Math.max(maxStackSize, stack.size());
        }

        Object[][] data = new Object[maxStackSize][stacks.size()];
        String[] columnNames = new String[stacks.size()];

        int columnIndex = 0;
        for (Stack<Character> stack : stacks) {
            String stackLabel = getStackLabel(columnIndex);
            columnNames[columnIndex] = stackLabel;

            int rowIndex = maxStackSize - 1;
            for (Character item : stack) {
                data[rowIndex--][columnIndex] = item;
            }
            columnIndex++;
        }

        return new Object[]{data, columnNames};
    }

    /**
     * Resets the algorithm to its initial state. Sets the current instruction
     * pointer to the instruction right after the blank line.
     */
    public void reset(DefaultTableModel model) {
        // Set the instruction pointer to the position right after the blank line
        currentInstruction = blankIndex + 1;
		reverseModelToStacks(model);
    }
	
	/**
	 * Converts a DefaultTableModel back to a list of stacks.
	 * 
	 * This method clears the existing stacks and repopulates them using the
	 * data from the provided DefaultTableModel. Each column in the model
	 * represents a stack, and each row in a column represents an item in that
	 * stack. The items in the model are expected to be of type Character.
	 *
	 * @param model The DefaultTableModel containing the stack data. Each column
	 * represents a stack, and each row represents an item in the stack.
	 */
	public void reverseModelToStacks(DefaultTableModel model) {
		// Clear existing stacks
		stacks.clear();

		// Get the number of columns (stacks) and rows (stack height) in the model
		int columnCount = model.getColumnCount();
		int rowCount = model.getRowCount();

		// Iterate over each column to create a stack
		for (int col = 0; col < columnCount; col++) {
			Stack<Character> stack = new Stack<>();

			// Iterate from the bottom row to the top row of the current column to build the stack
			for (int row = rowCount - 1; row >= 0; row--) {
				Object value = model.getValueAt(row, col);

				// If the value is not null and is of type Character, push it onto the stack
				if (value != null && value instanceof Character) {
					stack.push((Character) value);
				}
			}

			// Add the stack to the list of stacks
			stacks.add(stack);
		}
	}

}
