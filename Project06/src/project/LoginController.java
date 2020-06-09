package project;

import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController implements Initializable {
	Connection conn;
	PreparedStatement pstmt = null;
	@FXML TextField txt1, txt2;
	@FXML Button btn1, btn2;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<LogInfo> logIn = getBoardList();
		
		
		//로그인 버튼
		btn1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
					//관리자용 로그인
				if((txt1.getText().contentEquals("admin"))&&(txt2.getText().equals("admin"))){
					System.out.println("관리자임.");
					//관리자용 화면 추가
					Node node = (Node) event.getSource();
					Stage stage = (Stage) node.getScene().getWindow();
					stage.close();
					
					try {
						Parent parent = FXMLLoader.load(getClass().getResource("AdminControl.fxml"));
						Scene scene = new Scene(parent);
						stage.setScene(scene);
						stage.setResizable(false); 
						stage.show();

						
					} catch (IOException e) {
						e.printStackTrace();
					}
					
		
				}
				for(int i=0; i<logIn.size(); i++) {
					//사용자 로그인
					if((txt1.getText().equals(logIn.get(i).getId()))&&(txt2.getText().equals(logIn.get(i).getPassword()))) {
						System.out.println("로그인 됌");
						
						//사용자용 화면 추가.
						Node node = (Node) event.getSource();
						Stage stage = (Stage) node.getScene().getWindow();
						stage.close();
						try {
							Parent parent = FXMLLoader.load(getClass().getResource("GradeControl.fxml"));
							Scene scene = new Scene(parent);
							stage.setScene(scene);
							stage.setResizable(false); 
							stage.show();
						} catch (IOException e) {
							e.printStackTrace();
						}
						
					}else {
						//팝업으로 아이디/비밀번호 틀렸다고 해주기.
						System.out.println("안됌");
					}
				}
			
			}
		});
		
		//회원가입 버튼
		btn2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SignupButtonAction(event);			
			}
		});	
				
	}//end of initialize

	// connect
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
	//로그인
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
	
	
	
	
	//회원 가입
	public void updateInfo(String title, String age, String password) {
		conn = getConnect();
		String sql = "insert into info values(?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, age);
			pstmt.setString(3, password);

			int r = pstmt.executeUpdate();
			System.out.println(r + "건 변경됨");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 화면 바꾸기
	public void Changestage(ActionEvent a) {
		Node node = (Node) a.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.close();
		
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("SignupControl.fxml"));
			Scene scene = new Scene(parent);
			stage.setScene(scene);
			stage.setResizable(false); 
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//회원가입 버튼 클릭
	public void SignupButtonAction(ActionEvent a) {
		
		Node node = (Node) a.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.close();
		
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("SignupControl.fxml"));
			Scene scene = new Scene(parent);
			stage.setScene(scene);
			stage.setResizable(false); 
			stage.show();
			
			Button signupBtn = (Button)parent.lookup("#signupbtn"); 
			Button cancelBtn = (Button)parent.lookup("#cancelbtn");
			cancelBtn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					
					try {
						Parent parent = FXMLLoader.load(getClass().getResource("LoginControl.fxml"));
						Scene scene = new Scene(parent);
						stage.setScene(scene);
						stage.setResizable(false); 
						stage.show();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
			});

			signupBtn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					TextField txtSign = (TextField)parent.lookup("#txtsign");
					TextField txtAge = (TextField)parent.lookup("#txtage");
					PasswordField passwordField = (PasswordField)parent.lookup("#passwordfield");
					updateInfo(txtSign.getText(), txtAge.getText(), passwordField.getText());
					try {
						Parent parent = FXMLLoader.load(getClass().getResource("LoginControl.fxml"));
						Scene scene = new Scene(parent);
						stage.setScene(scene);
						stage.setResizable(false); 
						stage.show();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
