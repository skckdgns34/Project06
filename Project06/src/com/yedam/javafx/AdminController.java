package com.yedam.javafx;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import project.LogInfo;

public class AdminController implements Initializable {
	@FXML
	ListView<String> listView;
	@FXML
	Connection conn;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<String> list = FXCollections.observableArrayList("Month", "Korean", "English");
		list.add("Month");
		list.add("Korean");
		list.add("English");
		listView.setItems(list);
		
		listView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<number>() {
			public void changed(observableValue<? extends Number> arg0, Number )
		});
	}

	
	//
	public Connection getConnect() {
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, "hr", "hr");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	//
	
	public ObservableList<String> getNameList() {
		ObservableList<String> list = FXCollections.observableArrayList();
		conn = getConnect();
		String sql = "select id from info";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String id = rs.getString("id");
				if(id.equals("admin"))
					continue;
				list.add(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
