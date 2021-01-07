import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.io.FileNotFoundException;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class MyFrame extends JFrame {

	private JPanel contentPane;
	public static AnagramGenerator generator = new AnagramGenerator();
	private JTextField inputTextField;

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
		setBounds(100, 100, 502, 327);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblEnterAWord = new JLabel("Enter a word");
		lblEnterAWord.setHorizontalAlignment(SwingConstants.CENTER);
		
		inputTextField = new JTextField();
		inputTextField.setColumns(10);
		
		JButton btnNewButton = new JButton("Anagramize!");
		
		JScrollPane scrollPane = new JScrollPane();
		
		JLabel anagramHeader = new JLabel("New label");
		anagramHeader.setHorizontalAlignment(SwingConstants.CENTER);
		
		// Initialize group layout
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblEnterAWord, GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(60)
					.addComponent(inputTextField, GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
					.addGap(60))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE)
					.addGap(13))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(anagramHeader, GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(146)
					.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
					.addGap(141))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblEnterAWord, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(inputTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(anagramHeader, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
					.addGap(4)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE))
		);
		
		JTextPane outputField = new JTextPane();
		outputField.setEditable(false);
		scrollPane.setViewportView(outputField);
		contentPane.setLayout(gl_contentPane);
		anagramHeader.setVisible(false);
		
		// Center outputField text pane
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		StyledDocument doc = outputField.getStyledDocument();
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		
		// Anagramize button clicked
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String userWord = inputTextField.getText(); // Accept user input
				String allAnagrams = ""; // Final output string
				String totalTime; // Time taken to search for anagrams
				
				// Find anagrams for userWord
				generator.anagramize(userWord);
				anagramHeader.setVisible(true);
				anagramHeader.setText("Anagrams of " + userWord + ":");
				
				// Parse time
				if(generator.time < 1000) {
					totalTime = (generator.time + "ms");
				}
				else if(generator.time < 60000) {
					totalTime = (generator.time / 1000 + "s");
				}
				else {
					totalTime = (generator.time / 60000 + "m");
				}
				allAnagrams += (generator.results.size() + " anagrams found in " + totalTime + "\n\n");
				
				// Generate output string
				// Iterate through all possible sizes of words
				for(int i = generator.organizedWords.size() - 1; i >= 0; i--) {
					// If list of words with length == i are found, add to output string
					if(generator.organizedWords.get(i) != null) {
						allAnagrams += (i + " Letter Words - " 
										+ generator.organizedWords.get(i).size() + " Found:" 
										+ "\n" + generator.organizedWords.get(i) + "\n\n");
					}
				}
				// Display output string
				outputField.setText(allAnagrams);
			}
		});
	}
}
