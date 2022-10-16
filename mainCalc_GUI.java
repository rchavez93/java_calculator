import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Window.Type;
import java.awt.Component;
import java.awt.Insets;
import javax.swing.ButtonGroup;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class mainCalc_GUI extends JFrame {

	private JPanel contentPane;
	private JTextField textTotal;
	private JTextField textNext;

	int stageLevel = 0;
	String isStage = null;
	
	boolean startEquation = false;
	boolean doEquation = false;
	boolean deletePrev = false;
	char lastEquation = '\0';
	String totalNum, nextNum, newNum;

	mainCalc calc = new mainCalc();
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainCalc_GUI frame = new mainCalc_GUI();
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
	public mainCalc_GUI() {
		setTitle("Simple Calculator v1.0");
		setResizable(false);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 520, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textTotal = new JTextField();
		textTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		textTotal.setFont(new Font("Tahoma", Font.BOLD, 11));
		textTotal.setBounds(20, 221, 270, 31);
		contentPane.add(textTotal);
		textTotal.setColumns(10);
		textTotal.setText(""); // default 0 value
		textTotal.setEditable(false);

		textNext = new JTextField();
		textNext.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				int charTyped = e.getKeyCode();
				System.out.println("Key was pressed -> "+charTyped);
				tryOperation(charTyped);

			}
			@Override
			public void keyTyped(KeyEvent e) {		// when a char is typed, perform a check for nextText
				
				checkTypedChar();
				
			}
		});
		textNext.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {		// re-grab focus to nextText to continue typing
				textNext.grabFocus();
			}
		});
		textNext.setMargin(new Insets(2, 5, 2, 5));
		textNext.setHorizontalAlignment(SwingConstants.RIGHT);
		textNext.setBounds(20, 22, 270, 43);
		contentPane.add(textNext);
		textNext.setText(""); // default 0 value
		textNext.setColumns(10);

		JButton btnMult = new JButton("X");
		buttonGroup.add(btnMult);
		btnMult.setFont(new Font("Tahoma", Font.BOLD, 17));
		btnMult.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		// clicking X button

				tryEquation('x');

			}
		});
		btnMult.setBounds(20, 79, 125, 25);
		contentPane.add(btnMult);

		JButton btnDiv = new JButton("%");
		buttonGroup.add(btnDiv);
		btnDiv.setFont(new Font("Tahoma", Font.BOLD, 17));
		btnDiv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		// clicking / button

				tryEquation('d');

			}
		});
		btnDiv.setBounds(165, 79, 125, 25);
		contentPane.add(btnDiv);

		JButton btnMin = new JButton("-");
		buttonGroup.add(btnMin);
		btnMin.setFont(new Font("Stencil", Font.BOLD, 17));
		btnMin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		// clicking - button

				tryEquation('s');

			}
		});
		btnMin.setBounds(20, 113, 125, 25);
		contentPane.add(btnMin);

		JButton btnAdd = new JButton("+");
		buttonGroup.add(btnAdd);
		btnAdd.setFont(new Font("Tahoma", Font.BOLD, 17));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		// clicking + button

				tryEquation('a');

			}
		});
		btnAdd.setBounds(165, 115, 125, 25);
		contentPane.add(btnAdd);

		JButton btnEqual = new JButton("=");
		buttonGroup.add(btnEqual);
		btnEqual.setFont(new Font("Tahoma", Font.BOLD, 17));
		btnEqual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		// clicking = button, more unique

				debugInfo(1);
				
				if (doEquation) {
					
					doMath(lastEquation); // equals
				} else {
					
					textTotal.setText(textNext.getText()); // set value as first set of numbers
					textNext.setText(""); // default 0 value
				}
				
				debugInfo(2);
			}
		});
		btnEqual.setBounds(165, 151, 125, 50);
		contentPane.add(btnEqual);

		JButton btnClear = new JButton("CLEAR");
		buttonGroup.add(btnClear);
		btnClear.setFont(new Font("Tahoma", Font.BOLD, 17));
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { // clicking clear button, basically a reset

				resetCalc();
			}
		});
		btnClear.setBounds(20, 149, 125, 50);
		contentPane.add(btnClear);
		
		JButton btnOne = new JButton("1");
		btnOne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		// clicking 1 button
				
				textNext.setText(textNext.getText() + "" +"1");
			}
		});
		btnOne.setFont(new Font("Tahoma", Font.BOLD, 24));
		btnOne.setBounds(319, 22, 50, 50);
		contentPane.add(btnOne);
		
		JButton btnTwo = new JButton("2");
		btnTwo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		// clicking 2 button
				
				textNext.setText(textNext.getText() + "" +"2");
			}
		});
		btnTwo.setFont(new Font("Tahoma", Font.BOLD, 24));
		btnTwo.setBounds(379, 22, 50, 50);
		contentPane.add(btnTwo);

		JButton btnThree = new JButton("3");
		btnThree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		// clicking 3 button
				
				textNext.setText(textNext.getText() + "" +"3");
			}
		});
		btnThree.setFont(new Font("Tahoma", Font.BOLD, 24));
		btnThree.setBounds(439, 22, 50, 50);
		contentPane.add(btnThree);
		
		JButton btnFour = new JButton("4");
		btnFour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		// clicking 4 button
				
				textNext.setText(textNext.getText() + "" +"4");
			}
		});
		btnFour.setFont(new Font("Tahoma", Font.BOLD, 24));
		btnFour.setBounds(319, 79, 50, 50);
		contentPane.add(btnFour);
		
		JButton btnFive = new JButton("5");
		btnFive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		// clicking 5 button
				
				textNext.setText(textNext.getText() + "" +"5");
			}
		});
		btnFive.setFont(new Font("Tahoma", Font.BOLD, 24));
		btnFive.setBounds(379, 79, 50, 50);
		contentPane.add(btnFive);
		
		JButton btnSix = new JButton("6");
		btnSix.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		// clicking 6 button
				
				textNext.setText(textNext.getText() + "" +"6");
			}
		});
		btnSix.setFont(new Font("Tahoma", Font.BOLD, 24));
		btnSix.setBounds(439, 79, 50, 50);
		contentPane.add(btnSix);
		
		JButton btnSeven = new JButton("7");
		btnSeven.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		// clicking 7 button
				
				textNext.setText(textNext.getText() + "" +"7");
			}
		});
		btnSeven.setFont(new Font("Tahoma", Font.BOLD, 24));
		btnSeven.setBounds(319, 140, 50, 50);
		contentPane.add(btnSeven);
		
		JButton btnEight = new JButton("8");
		btnEight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		// clicking 8 button
				
				textNext.setText(textNext.getText() + "" +"8");
			}
		});
		btnEight.setFont(new Font("Tahoma", Font.BOLD, 24));
		btnEight.setBounds(379, 140, 50, 50);
		contentPane.add(btnEight);
		
		JButton btnNine = new JButton("9");
		btnNine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		// clicking 9 button
				
				textNext.setText(textNext.getText() + "" +"9");
			}
		});
		btnNine.setFont(new Font("Tahoma", Font.BOLD, 24));
		btnNine.setBounds(439, 140, 50, 50);
		contentPane.add(btnNine);
		
		JButton btnZero = new JButton("0");
		btnZero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		// clicking 0 button
				
				textNext.setText(textNext.getText() + "" +"0");
			}
		});
		btnZero.setFont(new Font("Tahoma", Font.BOLD, 24));
		btnZero.setBounds(379, 201, 50, 50);
		contentPane.add(btnZero);
		
		JButton btnDecimal = new JButton(".");
		btnDecimal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		// clicking decimal button
				
				
				
			}
		});
		btnDecimal.setFont(new Font("Tahoma", Font.BOLD, 24));
		btnDecimal.setBounds(439, 201, 50, 50);
		contentPane.add(btnDecimal);
		
		JButton btnBackspace = new JButton("<-");
		btnBackspace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		// clicking backspace button
				
				
				
			}
		});
		btnBackspace.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnBackspace.setBounds(319, 202, 50, 50);
		contentPane.add(btnBackspace);
	}
	
	public void resetCalc() {		// reset key values for calculator, a soft reset
		
		//debugInfo(1);
		
		textTotal.setText("");
		textNext.setText("");
		lastEquation = '\0';
		startEquation = false;
		doEquation = false;
		System.out.println("\nRESET\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		
		//debugInfo(2);
	}
	
	public void checkTypedChar() {		// after key press, check if we need to fix text appearing for nextText

		if (deletePrev && textNext.getText().length() > 0) {

			System.out.println("textNext was "+textNext.getText());
			textNext.setText(textNext.getText().substring(0, textNext.getText().length() - 1));
			System.out.println("textNext now is "+textNext.getText());
			deletePrev = false;
		}
		
	}
	
	public void tryEquation(char operator) {		// see if equation should be done or wait for second set of numbers	
		
		debugInfo(1);
		
		if (doEquation) {
			doMath(lastEquation);		// subtraction
			lastEquation = operator;
		} else {
			textTotal.setText(textNext.getText());		// set value as first set of numbers
			textNext.setText("");		// default 0 value
			lastEquation = operator;
			doEquation = true;
		}
		debugInfo(2);
		
	}

	private void doMath(char z) {		// do the equation, with two sets of numbers

		if (textNext.getText() != "" || textNext.getText() != null) {
			
			try {

				Double temp_total = Double.valueOf(textTotal.getText());
				Double temp_next = Double.valueOf(textNext.getText());

				switch (z) {

				// Case switch according to equation being done

				case 'm':		// multiply

					newNum = calc.multiplyNum(temp_total, temp_next);
					textTotal.setText(newNum);
					break;

				case 'd':		// divide

					newNum = calc.divisionNum(temp_total, temp_next);
					textTotal.setText(newNum);
					break;

				case 'a':		// add

					newNum = calc.additionNum(temp_total, temp_next);
					textTotal.setText(newNum);
					break;

				case 's':		// subtract

					newNum = calc.subtractNum(temp_total, temp_next);
					textTotal.setText(newNum);
					break;

				case 'e':		// equals

					textTotal.setText(newNum);
					doEquation = false;
					break;

				default:

					System.out.println("doMath error -> Reached default, should never do so.");
					break;

				}

				textNext.setText("");
			} catch (NumberFormatException e) {
				System.out.println("doMath error -> "+e);
			}

		} else {
			System.out.println("Either textNext was blank or null! doMath did nothing.");
		}
		System.out.println("doMath completed");
	}
	
	private void tryOperation(int charTyped) {		// check for when keypad/keys are pressed instead of button

		//System.out.println("textNext was "+textNext.getText());
		
		if (charTyped == 45 || charTyped == 109) {		// for subtraction
			
			tryEquation('s');
			deletePrev = true;
		}
		else if (charTyped == 107) {		// for addition, 61 cannot be used
			
			tryEquation('a');
			deletePrev = true;
		}
		else if (charTyped == 47 || charTyped == 111) {		// for division
			
			tryEquation('d');
			deletePrev = true;
		}
		else if (charTyped == 106) {		// for multiplication, 56 cannot be used
			
			tryEquation('m');
			deletePrev = true;
		}
		else if (charTyped == 10) {		// for enter
			
			tryEquation('e');
			deletePrev = true;
		}
		else if (charTyped == 46 || charTyped == 110) {		// for decimal
			
			System.out.println("Decimal was pressed");
		}
		else if ((charTyped >= 48 && charTyped <= 57) || 
				(charTyped >= 96 && charTyped <= 105)) {		// for 0-9 numbers and numpad
			
			System.out.println("Number was pressed -> "+textNext.getText());
			
		} else {
			System.out.println("Non-operator or non-number pressed, deleting it...");
			textNext.setText(textNext.getText().substring(0, textNext.getText().length() - 1));
		}
		//System.out.println("textNext now is "+textNext.getText());
	}
	
	public boolean validNum(String x) {		// check textNext has a valid number or not before parsing w/ operator
		
		double temp;

		try {
			
			temp = Double.parseDouble(x);
			
			
		} catch (NumberFormatException e) {

			System.out.println("validNum error -> "+e);		// print error if not a real number
			return false;
		}
		
		return true;
	}
	
	public void debugInfo(int stageLevel) {		// raw data debugging
		if (stageLevel == 1) {
			isStage = "BEFORE BUTTON PRESS";
		}
		else if (stageLevel == 2) {
			isStage = "AFTER BUTTON PRESS";
		} else {
			isStage = "error";
		}
		System.out.println("\n----------DEBUG INFO----------");
		System.out.println("STAGE = "+isStage);
		System.out.println("lastEquation = "+lastEquation);
		System.out.println("doEquation = "+doEquation);
		System.out.println("nextText = "+textNext.getText());
		System.out.println("textTotal = "+textTotal.getText());
		System.out.println("----------DEBUG INFO----------\n");
	}
}
