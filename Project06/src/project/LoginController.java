package project;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginController implements Initializable {
	Connection conn;
	PreparedStatement pstmt = null;
	@FXML TextField txt1, txt2;
	@FXML Button btn1, btn2;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<LogInfo> logIn = getBoardList();
		
		btn1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				for(int i=0; i<logIn.size(); i++) {
					if((txt1.getText().equals(logIn.get(i).getId()))&&(txt2.getText().equals(logIn.get(i).getPassword()))) {
						System.out.println("로그인 됌");
					}else {
						System.out.println("안됌");
					}
				}
			}
		});
		
	
	}

	// connect
	public Connection getConnect() {
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, "hr", "hrr");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	//로그인 확인
	public ObservableList<LogInfo> getBoardList() {
		ObservableList<LogInfo> list = FXCollections.observableArrayList();
		conn = getConnect();
		String sql = "select id, password from info";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				LogInfo logInfo = new LogInfo(rs.getString("id"), rs.getString("password"));
				list.add(logInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;

	}
	
	
	
	
	
	public void updateBoard(String content, String title) {
		conn = getConnect();
		String sql = "update info set password = ? where title = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, content);
			pstmt.setString(2, title);
			int r = pstmt.executeUpdate();
			System.out.println(r + "건 변경됨");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
