package project;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class LogInfo {
	
	private SimpleStringProperty id;
	private SimpleStringProperty password;
	
	LogInfo(){
		
	}
	LogInfo(String id){
		this.id = new SimpleStringProperty(id);
	}
	LogInfo(String id, String password){
		this.id = new SimpleStringProperty(id);
		this.password = new SimpleStringProperty(password);
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

}
