package canderson2740ex3i;

import javax.swing.DefaultListModel;

public class DriverExam {
	
	private char [] answers;
	private char [] responses;
	private final double requiredPct = 0.77;

		public DriverExam(char[] answers) {
			
			this.answers = new char[answers.length];
			for (int i = 0; i < answers.length; i++) {
				this.answers[i] = answers[i];
			
			}
		}
		
		public DriverExam(DefaultListModel answers) {
			
			this.answers = new char[answers.getSize()];
			for (int i = 0; i < answers.getSize(); i++) {
				String a = (String) answers.get(i);
				this.answers[i] = a.charAt(0);
			
			}
		}
	
		public void setResponses(DefaultListModel responses) {
			
			this.responses = new char[responses.getSize()];
			for(int i = 0; i < responses.getSize(); i++) {
				String r = (String) responses.get(i);
				this.responses[i] = r.charAt(0);
			
			}
		}
		
		public DefaultListModel getAnswers() {
			
			DefaultListModel answersListModel = new DefaultListModel();
			for (int i = 0; i < answers.length; i++) {
				String a = Character.toString(answers[i]);
				answersListModel.addElement(a);
			
			}
			
			return answersListModel;
		}
		
		public int validate() {
		
			int invalidIndex = -1;
			int i = 0;
			while (i < answers.length && invalidIndex == -1) {
				if (responses[i] != 'A' &&
						responses[i] != 'B' &&
						responses[i] != 'C' &&
						responses[i] != 'D')
					invalidIndex = i;
				else i++;
			
			}
			
			return invalidIndex;
		}
		
		public boolean passed() {
			
			if (totalCorrect() >= answers.length * requiredPct)
			return true;
			
			else {
			
				return false;
			
			}
		}
		
		public int totalCorrect() {
		
			int correct = 0;
			
			for (int i = 0; i < responses.length; i++) {
			
				if (responses[i] == answers[i])
			 	  correct++;
			 	
			}
			
			return correct;
		}
		
		public int totalIncorrect() {
		
			int incorrect = 0;
			
			for (int i = 0; i < responses.length; i++) {
			
				if (responses[i] != answers[i])
			 	
					incorrect++;
			 	 
			}
			
			return incorrect;
		}
		
		public int[] questionsMissed() {
			
			int[] missed = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			int m = 0;
			
			for (int i = 0; i < responses.length; i++){
			
				if (responses[i] != answers[i]) {
				
					missed[m] = i + 1;
			  		m++;
			  	
				}
			 }
			 
			return missed;
		}
}

