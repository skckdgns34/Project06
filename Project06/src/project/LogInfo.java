package project;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class LogInfo {
	private SimpleStringProperty id;
	private SimpleStringProperty password;
	private SimpleIntegerProperty age;
	LogInfo(){
		
	}
	LogInfo(String id){
		this.id = new SimpleStringProperty(id);
	}
	LogInfo(String id, String password){
		this.id = new SimpleStringProperty(id);
		this.password = new SimpleStringProperty(password);
	}
	LogInfo(String id, String password, int age){
		this.id = new SimpleStringProperty(id);
		this.password = new SimpleStringProperty(password);
		this.age = new SimpleIntegerProperty(age);

	}
	//id
	public void setId(String id) {
		this.id.set(id);
	}
	public String getId() {
		return this.id.get();
	}
	public SimpleStringProperty idProperty() {
		return this.id;
	}
	//password
	public void setPassword(String password) {
		this.password.set(password);
	}
	public String getPassword() {
		return this.password.get();
	}
	public SimpleStringProperty passwordProperty() {
		return this.password;
	}
	
	//age
	public void setAge(int age) {
		this.age.set(age);
	}
	public int getAge() {
		return this.age.get();
	}
	public SimpleIntegerProperty ageProperty() {
		return this.age;
	}
}
