package com.yedam.javafx;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Grade {
	private SimpleStringProperty month;
	private SimpleIntegerProperty korean;
	private SimpleIntegerProperty math;
	private SimpleIntegerProperty english;
	
	public Grade(String month, int korean, int math, int english) {
		this.month = new SimpleStringProperty(month);
		this.korean = new SimpleIntegerProperty(korean);
		this.math = new SimpleIntegerProperty(math);
		this.english = new SimpleIntegerProperty(english);//
	}
	
	public void setName(String month) {
		this.month.set(month);
	}
	public String getName() {
		return this.month.get();
	}
	public SimpleStringProperty nameProperty() {
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

