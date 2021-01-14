import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.prefs.Preferences;

import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class MyFrame extends JFrame implements ItemListener, ActionListener{
	private static final long serialVersionUID = 1L;
	static AnagramGenerator generator = new AnagramGenerator();
	JPanel contentPane;
	JPanel anagramHeadPanel = new JPanel();
	JTextPane outputField = new JTextPane();
	JLabel promptLabel = new JLabel("Enter a word");
	JLabel anagramHeader = new JLabel("New label");
	JButton anagramButton = new JButton("Anagramize!");
	JTextField inputTextField = new JTextField();
	JScrollPane scrollPane = new JScrollPane();
	
	
	
	Preferences pref = Preferences.userRoot();
	Color backgroundColor = Color.decode(pref.get("BACKGROUND_COLOR", "#FFFFFF"));
	Color outputColor = Color.decode(pref.get("OUTPUT_COLOR", "#FFFFFF"));
	Color textColor = Color.decode(pref.get("TEXT_COLOR", "#000000"));
	
	JMenuBar menuBar = new JMenuBar();
	JCheckBoxMenuItem upperCasePrefButton = new JCheckBoxMenuItem("Uppercase output");
	JRadioButtonMenuItem whiteBackgroundButton = new JRadioButtonMenuItem("White");
	JRadioButtonMenuItem greenBackgroundButton = new JRadioButtonMenuItem("Green");
	JRadioButtonMenuItem blueBackgroundButton = new JRadioButtonMenuItem("Blue");
	JRadioButtonMenuItem whiteOutputButton = new JRadioButtonMenuItem("White");
	JRadioButtonMenuItem greenOutputButton = new JRadioButtonMenuItem("Green");
	JRadioButtonMenuItem blueOutputButton = new JRadioButtonMenuItem("Blue");
	JRadioButtonMenuItem blackTextButton = new JRadioButtonMenuItem("Black");
	JRadioButtonMenuItem whiteTextButton = new JRadioButtonMenuItem("White");
	JRadioButtonMenuItem greenTextButton = new JRadioButtonMenuItem("Green");
	JRadioButtonMenuItem blueTextButton = new JRadioButtonMenuItem("Blue");
	
	/**
	 * Launch the application.
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// Initialize dictionary
		generator.root = Dictionary.createDictionary("src/dictionary.csv");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyFrame frame = new MyFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MyFrame() {
		

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 545, 442);
		
		// Create content pane
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(backgroundColor);
		setContentPane(contentPane);
		
		// Add menu bar and options tab
		setJMenuBar(menuBar);
		menuBar.setOpaque(true);
		
		JMenu optionMenu = new JMenu("Options");
		menuBar.add(optionMenu);
		upperCasePrefButton.setSelected(pref.getBoolean("UPPERCASE_OUTPUT", false));
		optionMenu.add(upperCasePrefButton);
		
		//Add background color selection menu and buttons
		JMenu backgroundColorPrefSelect = new JMenu("Background color");
		optionMenu.add(backgroundColorPrefSelect);
		backgroundColorPrefSelect.add(whiteBackgroundButton);
		backgroundColorPrefSelect.add(greenBackgroundButton);
		backgroundColorPrefSelect.add(blueBackgroundButton);
		ButtonGroup backgroundButtons = new ButtonGroup();
		backgroundButtons.add(whiteBackgroundButton);
		backgroundButtons.add(greenBackgroundButton);
		backgroundButtons.add(blueBackgroundButton);
		whiteBackgroundButton.setSelected(pref.getBoolean("WHITE_BG", true));
		greenBackgroundButton.setSelected(pref.getBoolean("GREEN_BG", false));
		blueBackgroundButton.setSelected(pref.getBoolean("BLUE_BG", false));

		// Add output color selection menu and buttons
		JMenu outputColorPrefSelect = new JMenu("Output field color");
		optionMenu.add(outputColorPrefSelect);
		outputColorPrefSelect.add(whiteOutputButton);
		outputColorPrefSelect.add(greenOutputButton);
		outputColorPrefSelect.add(blueOutputButton);
		ButtonGroup outputButtons = new ButtonGroup();
		outputButtons.add(whiteOutputButton);
		outputButtons.add(greenOutputButton);
		outputButtons.add(blueOutputButton);
		whiteOutputButton.setSelected(pref.getBoolean("WHITE_OUTPUT", true));
		greenOutputButton.setSelected(pref.getBoolean("GREEN_OUTPUT", false));
		blueOutputButton.setSelected(pref.getBoolean("BLUE_OUTPUT", false));
		
		// Add text color selection menu and buttons
		JMenu textColorPrefSelect = new JMenu("Text color");
		optionMenu.add(textColorPrefSelect);
		textColorPrefSelect.add(blackTextButton);
		textColorPrefSelect.add(whiteTextButton);
		textColorPrefSelect.add(greenTextButton);
		textColorPrefSelect.add(blueTextButton);
		ButtonGroup textButtons = new ButtonGroup();
		textButtons.add(blackTextButton);
		textButtons.add(whiteTextButton);
		textButtons.add(greenTextButton);
		textButtons.add(blueTextButton);
		textButtons.add(blueTextButton);
		blackTextButton.setSelected(pref.getBoolean("BLACK_TEXT", true));
		whiteTextButton.setSelected(pref.getBoolean("WHITE_TEXT", false));
		greenTextButton.setSelected(pref.getBoolean("GREEN_TEXT", false));
		blueTextButton.setSelected(pref.getBoolean("BLUE_TEXT", false));

		
		
		inputTextField.setFont(new Font("Gujarati MT", Font.PLAIN, 13));
		inputTextField.setBackground(outputColor);
		inputTextField.setForeground(textColor);
		inputTextField.setColumns(10);
		
		
		
		
		promptLabel.setFont(new Font("Bodoni 72 Smallcaps", Font.BOLD, 30));
		promptLabel.setForeground(textColor);
		promptLabel.setBackground(outputColor);
		promptLabel.setOpaque(true);
		promptLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		
		
		
		// Initialize group layout
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(61)
					.addComponent(inputTextField, GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
					.addGap(59))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(anagramHeadPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE))
					.addGap(13))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(143)
					.addComponent(anagramButton, GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
					.addGap(144))
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addGap(178)
					.addComponent(promptLabel, GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
					.addGap(174))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(46)
					.addComponent(promptLabel, GroupLayout.PREFERRED_SIZE, 33, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(inputTextField, GroupLayout.PREFERRED_SIZE, 34, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(anagramButton, GroupLayout.PREFERRED_SIZE, 50, Short.MAX_VALUE)
					.addGap(32)
					.addComponent(anagramHeadPanel, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))
		);
		anagramHeader.setBackground(outputColor);
		anagramHeadPanel.add(anagramHeader);
		anagramHeadPanel.setBackground(backgroundColor);
		anagramHeader.setForeground(textColor);
		anagramHeader.setHorizontalAlignment(SwingConstants.CENTER);
		anagramHeader.setVisible(false);
		anagramHeader.setOpaque(true);
		
		anagramButton.setFont(new Font("Luminari", Font.PLAIN, 15));
		anagramButton.setForeground(Color.BLACK);
		anagramButton.setOpaque(false);
		
		outputField.setBackground(outputColor);
		outputField.setForeground(textColor);
		scrollPane.setViewportView(outputField);
		scrollPane.setBackground(backgroundColor);
		contentPane.setLayout(gl_contentPane);
		
		// Center outputField text pane
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		StyledDocument doc = outputField.getStyledDocument();
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		
		// Anagramize button clicked
		anagramButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String userWord = inputTextField.getText(); // Accept user input
				String allAnagrams = ""; // Final output string
				String totalTime; // Time taken to search for anagrams
				
				// Find anagrams for userWord
				generator.anagramize(userWord);
				anagramHeader.setVisible(true);
				anagramHeader.setText("Anagrams of " + userWord + ":");
				anagramHeader.revalidate();
				
				if(generator.results.size() == 0) {
					allAnagrams = "No anagrams of " + userWord + " found\n\n";
					outputField.setText(allAnagrams);
					return;
				}
				// Display total time taken
				totalTime = (generator.time + "ms");
				allAnagrams += (generator.results.size() + " anagrams found in " + totalTime + "\n\n");
				allAnagrams += parseWords();
				
				// Display output string
				outputField.setText(allAnagrams);
			}
		});

		// Menu buttons clicked
		
		upperCasePrefButton.addItemListener(this); 
		
		whiteBackgroundButton.addActionListener(this);
		greenBackgroundButton.addActionListener(this);
		blueBackgroundButton.addActionListener(this);
		
		whiteOutputButton.addActionListener(this);
		greenOutputButton.addActionListener(this);
		blueOutputButton.addActionListener(this);
		
		blackTextButton.addActionListener(this);
		whiteTextButton.addActionListener(this);
		greenTextButton.addActionListener(this);
		blueTextButton.addActionListener(this);
		
		
		
	}
	
	public void itemStateChanged(ItemEvent e) {
		Object source = e.getItemSelectable();
		if(source == upperCasePrefButton) {
			if(pref.getBoolean("UPPERCASE_OUTPUT", false)) {
				upperCasePrefButton.setSelected(false);
				pref.putBoolean("UPPERCASE_OUTPUT", false);
			}
			else {
				upperCasePrefButton.setSelected(true);
				pref.putBoolean("UPPERCASE_OUTPUT", true);
			}
		}
	}
	
	public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source == whiteBackgroundButton) {
        	pref.put("BACKGROUND_COLOR", "#FFFFFF");
        	pref.putBoolean("WHITE_BG", true);
        	pref.putBoolean("GREEN_BG", false);
        	pref.putBoolean("BLUE_BG", false);
        	backgroundColor = Color.WHITE;
        	contentPane.setBackground(backgroundColor);
        	anagramHeadPanel.setBackground(backgroundColor);
        	scrollPane.setBackground(backgroundColor);
        }
        else if(source == blueBackgroundButton) {
        	pref.put("BACKGROUND_COLOR", "#0000FF");
        	pref.putBoolean("WHITE_BG", false);
        	pref.putBoolean("GREEN_BG", false);
        	pref.putBoolean("BLUE_BG", true);
        	backgroundColor = Color.BLUE;
        	contentPane.setBackground(backgroundColor);
        	anagramHeadPanel.setBackground(backgroundColor);
        	scrollPane.setBackground(backgroundColor);
        }
        else if(source == greenBackgroundButton) {
        	pref.put("BACKGROUND_COLOR", "#00FF00");
        	pref.putBoolean("WHITE_BG", false);
        	pref.putBoolean("GREEN_BG", true);
        	pref.putBoolean("BLUE_BG", false);
        	backgroundColor = Color.GREEN;
        	contentPane.setBackground(backgroundColor);
        	anagramHeadPanel.setBackground(backgroundColor);
        	scrollPane.setBackground(backgroundColor);
        }
        else if(source == whiteOutputButton) {
        	pref.put("OUTPUT_COLOR", "#FFFFFF");
        	pref.putBoolean("WHITE_OUTPUT", true);
        	pref.putBoolean("GREEN_OUTPUT", false);
        	pref.putBoolean("BLUE_OUTPUT", false);
        	outputColor = Color.WHITE;
        	outputField.setOpaque(true);
        	outputField.setBackground(outputColor);
        	promptLabel.setBackground(outputColor);
    		anagramHeader.setBackground(outputColor);
    		inputTextField.setBackground(outputColor);
        	promptLabel.setBackground(backgroundColor);
        }
        else if(source == greenOutputButton) {
        	pref.put("OUTPUT_COLOR", "#00FF00");
        	pref.putBoolean("WHITE_OUTPUT", false);
        	pref.putBoolean("GREEN_OUTPUT", true);
        	pref.putBoolean("BLUE_OUTPUT", false);
        	outputColor = Color.GREEN;
        	outputField.setBackground(outputColor);
        	promptLabel.setBackground(outputColor);
    		anagramHeader.setBackground(outputColor);
    		inputTextField.setBackground(outputColor);
        	promptLabel.setBackground(backgroundColor);
        }
        else if(source == blueOutputButton) {
        	pref.put("OUTPUT_COLOR", "#0000FF");
        	pref.putBoolean("WHITE_OUTPUT", false);
        	pref.putBoolean("GREEN_OUTPUT", false);
        	pref.putBoolean("BLUE_OUTPUT", true);
        	outputColor = Color.BLUE;
        	outputField.setBackground(outputColor);
        	promptLabel.setBackground(outputColor);
    		anagramHeader.setBackground(outputColor);
    		inputTextField.setBackground(outputColor);
        	promptLabel.setBackground(backgroundColor);
        }
        else if(source == blackTextButton) {
        	pref.put("TEXT_COLOR", "#000000");
        	pref.putBoolean("BLACK_TEXT",true);
        	pref.putBoolean("WHITE_OUTPUT", false);
        	pref.putBoolean("GREEN_OUTPUT", false);
        	pref.putBoolean("BLUE_OUTPUT", false);
        	textColor = Color.BLACK;
        	promptLabel.setForeground(textColor);
        	anagramHeader.setForeground(textColor);
        	outputField.setForeground(textColor);
        	inputTextField.setForeground(textColor);
        }
        else if(source == whiteTextButton) {
        	pref.put("TEXT_COLOR", "#FFFFFF");
        	pref.putBoolean("BLACK_TEXT",false);
        	pref.putBoolean("WHITE_OUTPUT", true);
        	pref.putBoolean("GREEN_OUTPUT", false);
        	pref.putBoolean("BLUE_OUTPUT", false);
        	textColor = Color.WHITE;
        	promptLabel.setForeground(textColor);
        	anagramHeader.setForeground(textColor);
        	outputField.setForeground(textColor);
        	inputTextField.setForeground(textColor);
        }
        else if(source == greenTextButton) {
        	pref.put("TEXT_COLOR", "#00FF00");
        	pref.putBoolean("BLACK_TEXT",false);
        	pref.putBoolean("WHITE_OUTPUT", false);
        	pref.putBoolean("GREEN_OUTPUT", true);
        	pref.putBoolean("BLUE_OUTPUT", false);
        	textColor = Color.GREEN;
        	promptLabel.setForeground(textColor);
        	anagramHeader.setForeground(textColor);
        	outputField.setForeground(textColor);
        	inputTextField.setForeground(textColor);
        }
        else if(source == blueTextButton) {
        	pref.put("TEXT_COLOR", "#0000FF");
        	pref.putBoolean("BLACK_TEXT",false);
        	pref.putBoolean("WHITE_OUTPUT", false);
        	pref.putBoolean("GREEN_OUTPUT", false);
        	pref.putBoolean("BLUE_OUTPUT", true);
        	textColor = Color.BLUE;
        	promptLabel.setForeground(textColor);
        	anagramHeader.setForeground(textColor);
        	outputField.setForeground(textColor);
        	inputTextField.setForeground(textColor);
        }
    }
	
	public String parseWords() {
		Preferences p = Preferences.userRoot();
		String output = "";
		for(int i = generator.organizedWords.size() - 1; i >= 0; i--) {
			// If list of words with length == i are found, add to output string
			if(generator.organizedWords.get(i) != null) {
				Collections.sort(generator.organizedWords.get(i));
				output += (i + " Letter Words - " 
						+ generator.organizedWords.get(i).size() + " Found:" 
						+ "\n");
				if(p.getBoolean("UPPERCASE_OUTPUT", false)) {
					for(int j = 0; j < generator.organizedWords.get(i).size(); j++ ) {
						output += (generator.organizedWords.get(i).get(j).toUpperCase() + ", ");
					}
					output+= "\n\n";
				}	
				else {
					for(int j = 0; j < generator.organizedWords.get(i).size(); j++ ) {
						output += (generator.organizedWords.get(i).get(j) + ", ");
					}
					output+= "\n\n";
				}
			}
		}
		return output;
	}
}
