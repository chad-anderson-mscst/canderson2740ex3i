package canderson2740ex3i;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.ListModel;
import javax.swing.UIManager;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Arrays;

import javax.swing.JTextField;

public class ExamForm extends JFrame {

	private JPanel contentPane;
	private JList responsesList;
	private JLabel resultLabel;
	private JLabel questNumLabel;
	private JTextField inputAnswerTextField;
	private JButton calcCorrectButton;
	private JButton calcIncorrectButton;
	private JButton listIncorrectButton;
	private JButton prevButton;
	private JButton nextButton;
	private DriverExam exam;
	private JButton calcPassButton;
	private DefaultListModel responsesListModel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExamForm frame = new ExamForm();
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
	public ExamForm() {
		setTitle("CAnderson Exercise 3I: Driver Exam");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 341, 335);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblResponses = new JLabel("Responses:");
		lblResponses.setBounds(10, 11, 88, 14);
		contentPane.add(lblResponses);
		
		JList questNumList = new JList();
		questNumList.setBackground(UIManager.getColor("Label.background"));
		questNumList.setModel(new AbstractListModel() {
			String[] values = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		questNumList.setEnabled(false);
		questNumList.setBounds(8, 35, 30, 185);
		contentPane.add(questNumList);
		
		responsesList = new JList();
		responsesList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				do_responsesList_valueChanged(e);
			}
		});
		responsesList.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		responsesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		responsesList.setBounds(45, 32, 30, 185);
		contentPane.add(responsesList);
		
		JLabel lblResult = new JLabel("Result:");
		lblResult.setBounds(97, 11, 70, 14);
		contentPane.add(lblResult);
		
		resultLabel = new JLabel("");
		resultLabel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		resultLabel.setBounds(97, 37, 225, 29);
		contentPane.add(resultLabel);
		
		calcPassButton = new JButton("Pass");
		calcPassButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_calcPassButton_actionPerformed(arg0);
			}
		});
		calcPassButton.setBounds(97, 77, 165, 23);
		contentPane.add(calcPassButton);
		
		calcCorrectButton = new JButton("Correct");
		calcCorrectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				do_calcCorrectButton_actionPerformed(e);
			}
		});
		calcCorrectButton.setBounds(97, 102, 165, 23);
		contentPane.add(calcCorrectButton);
		
		calcIncorrectButton = new JButton("Incorrect");
		calcIncorrectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				do_calcIncorrectButton_actionPerformed(e);
			}
		});
		calcIncorrectButton.setBounds(97, 127, 165, 23);
		contentPane.add(calcIncorrectButton);
		
		listIncorrectButton = new JButton("List Incorrect");
		listIncorrectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				do_listIncorrectButton_actionPerformed(e);
			}
		});
		listIncorrectButton.setBounds(97, 152, 165, 23);
		contentPane.add(listIncorrectButton);
		
		questNumLabel = new JLabel("#0:");
		questNumLabel.setBounds(8, 239, 31, 23);
		contentPane.add(questNumLabel);
		
		inputAnswerTextField = new JTextField();
		inputAnswerTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				do_inputAnswerTextField_focusGained(arg0);
			}
		});
		inputAnswerTextField.setBounds(45, 240, 31, 20);
		contentPane.add(inputAnswerTextField);
		inputAnswerTextField.setColumns(10);
		
		prevButton = new JButton("Prev");
		prevButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				do_prevButton_actionPerformed(e);
			}
		});
		prevButton.setBounds(86, 228, 70, 23);
		contentPane.add(prevButton);
		
		nextButton = new JButton("Next");
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				do_nextButton_actionPerformed(e);
			}
		});
		nextButton.setBounds(86, 252, 70, 23);
		contentPane.add(nextButton);
		
		DriverExamObjMapper mapper = new DriverExamObjMapper("DriverExam.txt");
		this.exam = mapper.getDriverExam();
		this.responsesListModel = exam.getAnswers();
		responsesList.setModel(this.responsesListModel);
	}
	
	protected void do_calcPassButton_actionPerformed(ActionEvent arg0) {
		this.exam.setResponses((DefaultListModel) responsesList.getModel());
		int invalid = this.exam.validate();
		if (invalid >= 0) {
			resultLabel.setText("Invalid response # " + Integer.toString(invalid + 1));
			responsesList.setSelectedIndex(invalid);
		}
		else {
			if (exam.passed()) resultLabel.setText("You passed");
			else resultLabel.setText("You failed");
		}
	}
	
	protected void do_calcCorrectButton_actionPerformed(ActionEvent e) {
		this.exam.setResponses((DefaultListModel) responsesList.getModel());
		int invalid = this.exam.validate();
		if (invalid >= 0) {
			resultLabel.setText("Invalid response # " + Integer.toString(invalid + 1));
			responsesList.setSelectedIndex(invalid);
		}
		else {
			resultLabel.setText("Total correct: " + Integer.toString(exam.totalCorrect()));
		}
	}
	
	protected void do_calcIncorrectButton_actionPerformed(ActionEvent e) {
		this.exam.setResponses((DefaultListModel) responsesList.getModel());
		int invalid = this.exam.validate();
		if (invalid >= 0) {
			resultLabel.setText("Invalid response # " + Integer.toString(invalid + 1));
			responsesList.setSelectedIndex(invalid);
		}
		else {
			resultLabel.setText("Total Incorrect: " + Integer.toString(exam.totalIncorrect()));
		}
	}
	
	protected void do_listIncorrectButton_actionPerformed(ActionEvent e) {
		this.exam.setResponses((DefaultListModel) responsesList.getModel());
		int invalid = this.exam.validate();
		if (invalid >= 0) {
			resultLabel.setText("Invalid response # " + Integer.toString(invalid + 1));
			responsesList.setSelectedIndex(invalid);
		}
		else {
			int[] incorrect;
			incorrect = exam.questionsMissed();
			int i = 0;
			if (i < incorrect.length && incorrect[i] == 0) {
					resultLabel.setText("Great! No incorrect answers.");
				}
				else {
					StringBuilder list = new StringBuilder ("Incorrect #");
					while (i < incorrect.length && incorrect[i] !=0) {
						list.append(incorrect[i]);
						list.append(" ");
						i++;
					}
					resultLabel.setText(list.toString());
					}
				}
			}
		
	protected void do_prevButton_actionPerformed(ActionEvent arg0) {
        this.responsesListModel.setElementAt(
                inputAnswerTextField.getText().toUpperCase(), 
                responsesList.getSelectedIndex());
        responsesList.setSelectedIndex(responsesList.getSelectedIndex() - 1);
        questNumLabel.setText("#" + Integer.toString((responsesList.getSelectedIndex() + 1)));
        inputAnswerTextField.setText((String)responsesList.getSelectedValue());    

        nextButton.setEnabled(true);
        if (responsesList.getSelectedIndex() == 0) 
            prevButton.setEnabled(false);
        inputAnswerTextField.requestFocus();
    }
	
    protected void do_nextButton_actionPerformed(ActionEvent e) {
        this.responsesListModel.setElementAt(
                inputAnswerTextField.getText().toUpperCase(), 
                responsesList.getSelectedIndex());
        responsesList.setSelectedIndex(responsesList.getSelectedIndex() + 1);
        questNumLabel.setText("#" + Integer.toString((responsesList.getSelectedIndex() + 1)));
        inputAnswerTextField.setText((String)responsesList.getSelectedValue());
        
        prevButton.setEnabled(true);
        if (responsesList.getSelectedIndex() == responsesListModel.getSize() - 1)
            nextButton.setEnabled(false);
        inputAnswerTextField.requestFocus();
    }
	
	protected void do_responsesList_valueChanged(ListSelectionEvent arg0) {
        questNumLabel.setText("#" + Integer.toString((responsesList.getSelectedIndex() + 1)));
        inputAnswerTextField.setText((String)responsesList.getSelectedValue());    

        prevButton.setEnabled(true);
        nextButton.setEnabled(true);
        if (responsesList.getSelectedIndex() == responsesListModel.getSize() - 1)
            nextButton.setEnabled(false);
        if (responsesList.getSelectedIndex() == 0) 
            prevButton.setEnabled(false);
        inputAnswerTextField.requestFocus();        
    }
	
	protected void do_inputAnswerTextField_focusGained(FocusEvent arg0) {
		inputAnswerTextField.selectAll();
	}
}
