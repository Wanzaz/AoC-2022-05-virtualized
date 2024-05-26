package gui;

import exceptions.InputDataException;
import exceptions.InputFormatException;
import java.io.IOException;
import algorithms.Algorithms;
import algorithms.PureAlgorithms;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Component;
import java.awt.EventQueue;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import utilities.Reading;
import utilities.AlgorithmTimer;
import static utilities.AlgorithmTimer.ITERATIONS;

/**
 *
 * @author ondrejpazourek
 */
public class mainwindow extends javax.swing.JFrame {

	private Algorithms algorithms;
	private DefaultTableModel startingModel;
	/**
	 * Creates new form mainwindow
	 */
	private static String userInputFile;
	
	public mainwindow() {
		initComponents();
	}
	
	/**
	 * Perform the algorithm.
	 *
	 * @param userInputFile Path to the input file
	 * @return The result of the algorithm
	 * @throws IOException If an I/O error occurs
	 * @throws InputFormatException If there is a format issue with the input
	 * @throws InputDataException If there is an issue with the input data
	 */
	public String performAlgorithm(String userInputFile) throws IOException, InputFormatException, InputDataException {
		try {
			List<String> input = Reading.readFileAsListOfStrings(userInputFile);
			// Call the algorithm function from Algorithms class
			algorithms.processAllInstructions();
			return algorithms.getResult();
		} catch (IOException | InputFormatException | InputDataException e) {
			throw e;
		}
	}
	
	/**
	 * Updates the GUI based on the current state of the stacks.
	 *
	 * This method retrieves the current state of the stacks from the algorithms
	 * instance and updates the JTable component to reflect this state. It
	 * assumes you have a JTable or similar component to display the stacks.
	 */
	private void updateGuiWithCurrentStacks() {
		// Assuming you have a JTable or similar component to display the stacks
		Object[] dataArrayAndColumnNames = algorithms.getDataArrayAndColumnNames();
		Object[][] data = (Object[][]) dataArrayAndColumnNames[0];
		String[] columnNames = (String[]) dataArrayAndColumnNames[1];

		DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
		jTable1.setModel(tableModel);
	}

	/**
	 * Resets the GUI to its initial state using the starting model.
	 *
	 * This method resets the JTable component to its initial state based on the
	 * starting model and sets the current instruction pointer in the algorithms
	 * instance to the instruction right after the blank line.
	 */
	private void resetGuiWithCurrentStacks() {
		jTable1.setModel(this.startingModel);
		this.algorithms.currentInstruction = algorithms.blankIndex + 1;
	}

	/**
	 * Displays an "About" dialog providing information about the application
	 * and descriptions of the buttons and their functionalities.
	 */
	private void showAboutDialog() {
		// Create the content of the dialog
		String aboutMessage = "Advent of Code day 5 Virtualization\n\n"
			+ "This application demonstrates virtualization algorithms "
			+ "for Advent of Code day 5 problem.\n\n"
			+ "Buttons and Features:\n"
			+ "- Run All Steps: This button performs all steps in the file provided.\n"
			+ "- Run 10 Steps: This button performs 10 steps of the algorithm.\n"
			+ "- Run 1 Step: This button performs one step of the algorithm.\n"
			+ "- Run All Steps Back: This button performs all steps backward.\n"
			+ "- Time Algorithms: This button compares the execution time of the app and internet solutios of this algorithm. (it can take a quite a while so wait!!!)\n\n"
			+ "Developed by Ondrej Pazourek";

		// Display the dialog
		JOptionPane.showMessageDialog(this, aboutMessage, "About", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public class VariableRowHeightRenderer extends DefaultTableCellRenderer {

		public VariableRowHeightRenderer() {
			super();
			setHorizontalAlignment(JLabel.CENTER); // Set horizontal alignment to center
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

			// Ensure we are treating the value as a string if it's a Character
			if (value instanceof Character) {
				value = value.toString();
			}

			// Call the superclass method with the possibly modified value
			Component component = super.getTableCellRendererComponent(
				table, value, isSelected, hasFocus, row, column
			);

			// Additional rendering logic (if any)
			// For example, setting the row height based on the content:
			if (value instanceof String) {
				String stringValue = (String) value;
				int newHeight = getRowHeightBasedOnContent(stringValue);
				table.setRowHeight(row, newHeight);
			}

			return component;
		}

		private int getRowHeightBasedOnContent(String content) {
			// Implement your logic to determine row height based on content
			// For example, this could be a simple length check or something more complex
			return Math.max(16, content.length() * 2);  // Example logic
		}
	}


	class StringTableModel extends AbstractTableModel {

		private final String[] columnNames = {"Variable Dimension"};

		@Override
		public int getRowCount() {
			return 3;
		}

		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return "aa";
		}

		@Override
		public String getColumnName(int column) {
			return columnNames[column];
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			return String.class;
		}
	}

	
	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        TimeAlgorithms = new javax.swing.JButton();
        Run10Steps = new javax.swing.JButton();
        Run1Step = new javax.swing.JButton();
        RunAllStepsBack = new javax.swing.JButton();
        RunAllSteps = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable(new StringTableModel());
        jMenuBar1 = new javax.swing.JMenuBar();
        HelpMenu = new javax.swing.JMenu();
        AboutItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Advent of Code day 5 Virtualization");
        setBounds(new java.awt.Rectangle(0, 32, 1000, 700));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(1000, 700));

        jPanel1.setLayout(new java.awt.GridLayout());

        TimeAlgorithms.setFont(new java.awt.Font("Helvetica Neue", 0, 17)); // NOI18N
        TimeAlgorithms.setText("Time Algorithms");
        TimeAlgorithms.setToolTipText("");
        TimeAlgorithms.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TimeAlgorithmsMouseClicked(evt);
            }
        });
        jPanel1.add(TimeAlgorithms);

        Run10Steps.setFont(new java.awt.Font("Helvetica Neue", 0, 17)); // NOI18N
        Run10Steps.setText("Run 10 Steps");
        Run10Steps.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Run10StepsMouseClicked(evt);
            }
        });
        jPanel1.add(Run10Steps);

        Run1Step.setFont(new java.awt.Font("Helvetica Neue", 0, 17)); // NOI18N
        Run1Step.setText("Run 1 Step");
        Run1Step.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Run1StepMouseClicked(evt);
            }
        });
        jPanel1.add(Run1Step);

        RunAllStepsBack.setFont(new java.awt.Font("Helvetica Neue", 0, 17)); // NOI18N
        RunAllStepsBack.setText("Run All Steps Back");
        RunAllStepsBack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RunAllStepsBackMouseClicked(evt);
            }
        });
        jPanel1.add(RunAllStepsBack);
        RunAllStepsBack.getAccessibleContext().setAccessibleName("10 Steps");

        RunAllSteps.setFont(new java.awt.Font("Helvetica Neue", 0, 17)); // NOI18N
        RunAllSteps.setText("Run All Steps");
        RunAllSteps.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RunAllStepsMouseClicked(evt);
            }
        });
        jPanel1.add(RunAllSteps);

        jTable1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTable1.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        try {
            List<String> input = Reading.readFileAsListOfStrings(userInputFile);
            this.algorithms = new Algorithms(input);
        } catch (IOException | InputFormatException | InputDataException e) {
            JOptionPane.showMessageDialog(this, "Error initializing algorithms: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Get the data array and column names from the Algorithms class
        Object[] dataArrayAndColumnNames = algorithms.getDataArrayAndColumnNames();
        Object[][] data = (Object[][]) dataArrayAndColumnNames[0]; // Cast the first element to Object[][]
        String[] columnNames = (String[]) dataArrayAndColumnNames[1]; // Cast the second element to String[]

        // Use data and columnNames to create the table model
        this.startingModel = new DefaultTableModel(data, columnNames);

        // Set the model to the jTable1
        jTable1.setModel(this.startingModel);
        jTable1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTable1.setEnabled(false);
        jTable1.setMixingCutoutShape(null);
        jTable1.setRowSelectionAllowed(false);
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable1.setShowGrid(true);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int columnIndex = 0; columnIndex < jTable1.getColumnCount(); columnIndex++) {
            jTable1.getColumnModel().getColumn(columnIndex).setCellRenderer(new VariableRowHeightRenderer());
        }
        jScrollPane1.setViewportView(jTable1);
        jTable1.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jScrollPane1)
                .addGap(50, 50, 50))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
                .addGap(22, 22, 22))
        );

        HelpMenu.setText("Help");

        AboutItem.setText("About");
        AboutItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AboutItemActionPerformed(evt);
            }
        });
        HelpMenu.add(AboutItem);

        jMenuBar1.add(HelpMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 938, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

	/**
	 * Handles the mouse click event for performing one step of the algorithm.
	 * 
	 * This method ensures that the algorithms instance is initialized, reads
	 * the input file if necessary, and performs one step of the algorithm. It
	 * also updates the GUI to reflect the changes and shows appropriate
	 * messages if there are no more steps to perform or if an error occurs
	 * during instruction processing.
	 *
	 * @param evt The mouse event that triggered this action.
	 */
    private void Run1StepMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Run1StepMouseClicked
		// TODO add your handling code here:                                   
		// Ensure algorithms instance is initialized
		if (this.algorithms == null) {
			// Assuming userInputFile is the path to the input file
			List<String> input;
			try {
				input = Reading.readFileAsListOfStrings(userInputFile);
				this.algorithms = new Algorithms(input);
			} catch (InputFormatException | InputDataException | IOException e) {
				// Handle initialization errors (e.g., show a message dialog)
				JOptionPane.showMessageDialog(this, "Error initializing algorithms: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}

		// Perform one step
		try {
			boolean stepPerformed = this.algorithms.performOneStep();

			// Update the GUI to reflect the changes
			updateGuiWithCurrentStacks();

			if (!stepPerformed) {
				JOptionPane.showMessageDialog(this, "No more steps to perform.", "Info", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (InputFormatException | InputDataException e) {
			// Handle instruction processing errors (e.g., show a message dialog)
			JOptionPane.showMessageDialog(this, "Error processing instruction: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}

    }//GEN-LAST:event_Run1StepMouseClicked
	
	/**
	 * Handles the mouse click event for resetting the algorithm to its initial
	 * state.
	 *
	 * This method ensures that the algorithms instance is initialized, reads
	 * the input file if necessary, and resets the algorithm state using the
	 * starting model. It also updates the GUI to reflect the initial state.
	 *
	 * @param evt The mouse event that triggered this action.
	 */
    private void RunAllStepsBackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RunAllStepsBackMouseClicked
		// TODO add your handling code here:                                    
		// Ensure algorithms instance is initialized
		if (this.algorithms == null) {
			// Assuming userInputFile is the path to the input file
			List<String> input;
			try {
				input = Reading.readFileAsListOfStrings(userInputFile);
				this.algorithms = new Algorithms(input);
			} catch (InputFormatException | InputDataException | IOException e) {
				// Handle initialization errors (e.g., show a message dialog)
				JOptionPane.showMessageDialog(this, "Error initializing algorithms: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}

		// Reset the algorithm state (implement this method in your Algorithms class)
		this.algorithms.reset(this.startingModel);

		// Reset the GUI to reflect the initial state
		resetGuiWithCurrentStacks();
    }//GEN-LAST:event_RunAllStepsBackMouseClicked
	
	/**
	 * Handles the mouse click event for Run All Steps (jButton3). Ensures the
	 * algorithms instance is initialized and performs all steps of the
	 * algorithm. Updates the GUI accordingly and displays appropriate message
	 * dialogs for different scenarios.
	 *
	 * @param evt The MouseEvent triggered by clicking the button
	 */
    private void RunAllStepsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RunAllStepsMouseClicked
		// TODO add your handling code here:                                                                       
		// Ensure the algorithms instance is initialized
		if (this.algorithms == null) {
			// Assuming userInputFile is the path to the input file
			List<String> input;
			try {
				// Read the input file as a list of strings
				input = Reading.readFileAsListOfStrings(userInputFile);
				// Initialize the algorithms instance with the input data
				this.algorithms = new Algorithms(input);
			} catch (InputFormatException | InputDataException | IOException e) {
				// Handle initialization errors (e.g., show a message dialog)
				JOptionPane.showMessageDialog(this, "Error initializing algorithms: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				return; // Exit the method if initialization fails
			}
		}

		// Perform all steps
		try {
			while (this.algorithms.performOneStep()) {
				// Keep performing steps until there are no more steps to perform
			}

			// Update the GUI to reflect the changes after performing all steps
			updateGuiWithCurrentStacks();

			// Show a message dialog indicating all steps were performed
			JOptionPane.showMessageDialog(this, "Performed all steps.", "Info", JOptionPane.INFORMATION_MESSAGE);
		} catch (InputFormatException | InputDataException e) {
			// Handle instruction processing errors (e.g., show a message dialog)
			JOptionPane.showMessageDialog(this, "Error processing instruction: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
    }//GEN-LAST:event_RunAllStepsMouseClicked
	
	/**
	 * Handles the mouse click event for Run 10 Steps (jButton4). Ensures the algorithms
	 * instance is initialized and performs ten steps of the algorithm. Updates
	 * the GUI accordingly and displays appropriate message dialogs for
	 * different scenarios.
	 *
	 * @param evt The MouseEvent triggered by clicking the button
	 */
    private void Run10StepsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Run10StepsMouseClicked
		// TODO add your handling code here:
		// Ensure algorithms instance is initialized
		if (this.algorithms == null) {
			// Assuming userInputFile is the path to the input file
			List<String> input;
			try {
				input = Reading.readFileAsListOfStrings(userInputFile);
				this.algorithms = new Algorithms(input);
			} catch (InputFormatException | InputDataException | IOException e) {
				// Handle initialization errors (e.g., show a message dialog)
				JOptionPane.showMessageDialog(this, "Error initializing algorithms: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}

		try {
			Reading.readFileAsListOfStrings(userInputFile);
			boolean allStepsPerformed = this.algorithms.performTenSteps();

			// Update the GUI to reflect the changes
			updateGuiWithCurrentStacks();

			if (allStepsPerformed) {
				JOptionPane.showMessageDialog(this, "Performed 10 steps.", "Info", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(this, "Performed fewer than 10 steps. No more steps to perform.", "Info", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (IOException e) {
			// Handle file reading errors (e.g., show a message dialog)
			JOptionPane.showMessageDialog(this, "Error reading input file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		} catch (InputFormatException | InputDataException e) {
			// Handle instruction processing errors (e.g., show a message dialog)
			JOptionPane.showMessageDialog(this, "Error processing instruction: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
    }//GEN-LAST:event_Run10StepsMouseClicked
	
	/**
	 * Displays a dialog comparing the execution times of your solution and the
	 * internet solution.
	 */
	private void showTimeComparisonDialog() {
		// Gather execution time information for your solution
		double yourSolutionTime = 0.0;
		try {
			yourSolutionTime = AlgorithmTimer.timeAlgorithm(userInputFile);
		} catch (IOException | InputFormatException | InputDataException e) {
			JOptionPane.showMessageDialog(this, "Error processing instruction: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}

		// Gather execution time information for the internet solution
		double internetSolutionTime = 0.0;
		try {
			internetSolutionTime = AlgorithmTimer.timeInternetSolutionAlgorithm(userInputFile);
		} catch (IOException | InputFormatException | InputDataException e) {
			JOptionPane.showMessageDialog(this, "Error processing instruction: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}

		// Prepare the message for the dialog
		String comparisonMessage = "Comparison of Execution Times:\n\n"
			+ "App Solution: " + String.format("%.6f", yourSolutionTime) + " ms\n"
			+ "Internet So.: " + String.format("%.6f", internetSolutionTime) + " ms\n\n";

		// Display the dialog
		JOptionPane.showMessageDialog(this, comparisonMessage, "Time Comparison", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Handles the mouse click event for the "Time Algorithms" button. Calls the
	 * method to display the comparison dialog of execution times.
	 *
	 * @param evt The MouseEvent triggered by clicking the button
	 */
    private void TimeAlgorithmsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TimeAlgorithmsMouseClicked
		// TODO add your handling code here:
		showTimeComparisonDialog();
    }//GEN-LAST:event_TimeAlgorithmsMouseClicked

    private void AboutItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AboutItemActionPerformed
        // TODO add your handling code here:
		showAboutDialog();
    }//GEN-LAST:event_AboutItemActionPerformed

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		FlatLightLaf.setup();

		
		if (args.length != 1) {
            System.err.println("Usage: java Main <input_file>");
            throw new IllegalArgumentException("Invalid number of arguments");
        }

        userInputFile = args[0];
        
        // Regular expression to match "data.txt", "name/data.txt", "dir/name/data.txt", etc.
        String regex = "(?:\\w+/)*\\w+\\.txt$";
        
        if (!userInputFile.matches(regex)) {
            throw new IllegalArgumentException("Input file must be in the format nameOfFile.txt or nested in directory like this data/nameOfFile.txt");
        }
		
		
		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new mainwindow().setVisible(true);
			}
		});
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem AboutItem;
    private javax.swing.JMenu HelpMenu;
    private javax.swing.JButton Run10Steps;
    private javax.swing.JButton Run1Step;
    private javax.swing.JButton RunAllSteps;
    private javax.swing.JButton RunAllStepsBack;
    private javax.swing.JButton TimeAlgorithms;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
