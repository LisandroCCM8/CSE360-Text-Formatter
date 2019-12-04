/*
	Processes formatting commands
*/

import java.util.*;
import java.io.*;
//import org.apache.commons.lang3.StringUtils;	// needed to center align
// End needed

public class Formatter{

	// Formatting defaults
	private int lineLen = 80;
	private int align = 0;	// 0 = left, 1 = right, 2 = centered
	private boolean eqSpacing = false;
	private boolean textWrap = false;
	private boolean singleSpacing = true;	// false = double-spacing
	private boolean title = false;
	private int paragraph = 0;
	private int blankLn = 0;
	private boolean columns = false;	// true = 2 columns, false = 1 column
	
	// File input/reading
	private int tracker = 0;	// keeps track of current place in inputFileAL
	private ArrayList<String> inputFileAL = new ArrayList<String>();
	protected ArrayList<String> errorLog = new ArrayList<String>();
	protected File outputFile = new File("Output.txt");
	
	// Default constructor
	public Formatter(ArrayList<String> inputAL) {
		for (int i = 0; i < inputAL.size(); i++) {
			inputFileAL.add(inputAL.get(i));
		}
	}
			
	// setFormattingVals() - Uses switch case to determine which formatting
	//	defaults to change in accordance with command located in given
	//	element in the inputFileAL
	public void setFormattingVals() {
		do {
			// Checking if String is a formatting command
			if (inputFileAL.get(tracker).charAt(0) == '-'){
				String command = inputFileAL.get(tracker);
				String commandSub = command.substring(1, (command.length() - 1));
							
				// Checking which formatting command should be adjusted
				switch(commandSub.charAt(0)) {
				// Line length
				case 'n':
					int newVal;
					try {
						newVal = Integer.parseInt(commandSub.substring(1, (commandSub.length() - 1)));
					}catch(NumberFormatException e) {
						newVal = 80;	// default value
						errorLog.add("Invalid line length: Input line " + (tracker + 1));
					}
					
					if (newVal <= 0 || newVal > 80) {
						newVal = 80;
						errorLog.add("Invalid line length: Integer line " + (tracker + 1));
					}
					lineLen = newVal;
					break;
				// Right alignment
				case 'r':
					align = 1;
					break;
				// Left alignment
				case 'l':
					align = 0;
					break;
				// Center alignment
				case 'c':
					align = 2;
					break;
				// Equally-spaced words
				case 'e':
					eqSpacing = true;
					break;
				// Text wrapping - on or off
				case 'w':
					if (commandSub.charAt(1) == '+') {
						textWrap = true;
					}else {
						textWrap = false;
					}
					break;
				// Single-spacing
				case 's':
					singleSpacing = true;
					break;
				// Double-spacing
				case 'd':
					singleSpacing = false;
					break;
				// Title
				case 't':
					title = true;
					break;
				// Paragraph
				case 'p':
					if (align == 0) {
						try {
							newVal = Integer.parseInt(commandSub.substring(1, (commandSub.length() - 1)));
						}catch(NumberFormatException e) {
							newVal = 0;	// default value
							errorLog.add("Invalid paragraph indent: Input line " + (tracker + 1));
						}
						
						if (newVal <= 0 || newVal > 80) {
							newVal = 0;
							errorLog.add("Invalid paragraph indent: Integer line " + (tracker + 1));
						}
					}else {	// Cannot indent non-left aligned paragraphs
						newVal = 0; // default value
						errorLog.add("Cannot indent paragraph on non-left aligned text: Line " + (tracker + 1));
					}
					paragraph = newVal;
					break;
				// Blank lines
				case 'b':
					try {
						newVal = Integer.parseInt(commandSub.substring(1, (commandSub.length() - 1)));
					}catch(NumberFormatException e) {
						newVal = 0;	// default value
						errorLog.add("Invalid blank line: Input line " + (tracker + 1));
					}
					
					if (newVal < 0) {
						newVal = 0;
						errorLog.add("Invalid blank line: Integer line: " + (tracker + 1));
					}
					blankLn = newVal;
					break;
				// Set columns
				case 'a':
					try {
						newVal = Integer.parseInt(commandSub.substring(1, (commandSub.length() - 1)));
					}catch(NumberFormatException e) {
						newVal = 1;	// default value
						errorLog.add("Invalid column number: Input line " + (tracker + 1));
					}
					if (newVal == 2) {
						columns = true;
					}else if (newVal == 1) {
						columns = false;
					}else {
						columns = false;
						errorLog.add("Invalid column number: Integer line " + (tracker + 1));
					}
					break;
				// Invalid command
				default:
					errorLog.add("Invalid formatting command: Line " + (tracker + 1));
					break;
				}
				
				tracker++;
				
			}else {}	// String is not a formatting command
	
		}while(inputFileAL.get(tracker).charAt(0) == '-');
		
	}// End of setFormattingVals()
	
	// formatText() - Reviews values of formatting variables and applies to
	//	text in inputFileAL; writes to output file; should be called after 
	//	setFormattingVals() or else only default settings will be used
	public void formatText() throws IOException {
		
		FileWriter writer = new FileWriter(outputFile);
		
		// Formatting non-command text	- ***SERIES OF NESTED IF-ELSE STMTS?
		do {
			// Blank lines
			if (blankLn != 0) {
				for(int i = 0; i < blankLn; i++) {
					writer.write("\n");
				}
			}
			
			// Spacing
			if (singleSpacing == true) {	// double-spacing
				// Line length
				if (lineLen != 80) {
					// Title - this should only work once per method call
					if (title == true) {
						// need to center
						// need to add dashes on next line
						
						title = false;	// Turning off so only 1st line is titled
					}
				}else {
					// Title
				
					// Columns
				
					// Text wrapping
				
					// Alignment
				
					// Paragraph
				
					// Equal spacing
				}
				
				writer.write("\n");	// Adding extra blank line
			}else {							//else -> single-spacing
				// Line length
				
				// Title
				
				// Columns
				
				// Text wrapping
				
				// Alignment
				
				// Paragraph
				
				// Equal spacing
			}
			
			tracker++;
			
		}while(inputFileAL.get(tracker) != null && inputFileAL.get(tracker).charAt(0) != '-');
		
		if (inputFileAL.get(tracker).charAt(0) == '-') {
			setFormattingVals();
		}
		
		writer.close();
		
		//***Finished writing to output file?
		//***Create fileReader object in PrimPane?
	}// End of formatText()
	
}// End of Formatter class