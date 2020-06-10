package project;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Grade {
	private SimpleStringProperty month;
	private SimpleIntegerProperty korean;
	private SimpleIntegerProperty english;
	private SimpleIntegerProperty math;

	public Grade(String month, int korean, int english, int math) {
		this.month = new SimpleStringProperty(month);
		this.korean = new SimpleIntegerProperty(korean);
		this.english = new SimpleIntegerProperty(english);
		this.math = new SimpleIntegerProperty(math);//
	}
	
	public void setMonth(String month) {
		this.month.set(month);
	}
	public String getMonth() {
		return this.month.get();
	}
	public SimpleStringProperty MonthProperty() {
		return this.month;
	}
	
	public void setKorean(int korean) {
		this.korean.set(korean);
	}
	public int getKorean() {
		return this.korean.get();
	}
	public SimpleIntegerProperty koreanProperty() {
		return this.korean;
	}
	
	public void setMath(int math) {
		this.math.set(math);
	}
	public int getMath() {
		return this.math.get();
	}
	public SimpleIntegerProperty mathProperty() {
		return this.math;
	}
	
	public void setEnglish(int english) {
		this.english.set(english);
		
	}
	public int getEnglish() {
		return this.english.get();
	}
	public SimpleIntegerProperty englishProperty() {
		return this.english;
	}
}

