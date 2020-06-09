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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class GradeController implements Initializable{
	@FXML TableView<Grade> tableView;
	@FXML Button updateBtn, cancelBtn;
	Connection conn;
	PreparedStatement pstmt = null;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		ObservableList<Grade> grade = getGradeList();
		
		TableColumn<Grade, ?> tcMonth = tableView.getColumns().get(0);
		tcMonth.setCellValueFactory(new PropertyValueFactory("month"));
		
		TableColumn<Grade, ?> tckorean = tableView.getColumns().get(1);
		tckorean.setCellValueFactory(new PropertyValueFactory("korean"));
		
		TableColumn<Grade, ?> tcMath = tableView.getColumns().get(2);
		tcMath.setCellValueFactory(new PropertyValueFactory("english"));
		
		TableColumn<Grade, ?> tcEnglish = tableView.getColumns().get(3);
		tcEnglish.setCellValueFactory(new PropertyValueFactory("math"));
		
		tableView.setItems(grade);

	}
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
	
	//성적 목록
	public ObservableList<Grade> getGradeList() {
		ObservableList<Grade> list = FXCollections.observableArrayList();
		conn = getConnect();
		String sql = "select month, korean, english, math from grade";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Grade grade = new Grade(rs.getString("month"), rs.getInt("korean"),rs.getInt("english"),rs.getInt("math"));
				list.add(grade);
				System.out.println(list.get(0).getEnglish());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
