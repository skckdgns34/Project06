package project;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AdminController implements Initializable{
	@FXML ListView listView;
	@FXML TableView<Grade> tableView;
	@FXML Button btnUpdate;
	Connection conn;
	PreparedStatement pstmt = null;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<String> userId = getUserList();
		
		
		TableColumn<Grade, ?> tcMonth = tableView.getColumns().get(0);
		tcMonth.setCellValueFactory(new PropertyValueFactory("month"));

		TableColumn<Grade, ?> tckorean = tableView.getColumns().get(1);
		tckorean.setCellValueFactory(new PropertyValueFactory("korean"));

		TableColumn<Grade, ?> tcMath = tableView.getColumns().get(2);
		tcMath.setCellValueFactory(new PropertyValueFactory("english"));

		TableColumn<Grade, ?> tcEnglish = tableView.getColumns().get(3);
		tcEnglish.setCellValueFactory(new PropertyValueFactory("math"));
		
		
		listView.setItems(userId);
		listView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				
				ObservableList<Grade> grade = getGradeList(listView.getSelectionModel().getSelectedItem().toString());
				tableView.setItems(grade);
				
				
			}
		});
		
		btnUpdate.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
					buttonAddAction(event);
				
				
			}
		});
	}
	
	//수정버튼 눌렀을때.
	public void buttonAddAction(ActionEvent ae) {
		Stage addStage = new Stage(StageStyle.UTILITY);
		addStage.initModality(Modality.WINDOW_MODAL);
		addStage.initOwner(btnUpdate.getScene().getWindow()); //해당 id가 있는 window에 새로만드는 윈도우를 뿌리겠다는 거
		
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("AddForm.fxml"));
			Scene scene = new Scene(parent);
			addStage.setScene(scene);
			addStage.setResizable(false); //윈도우 크기 변경 불가능하게 하는거.
			addStage.show();		
			
			ComboBox comboMonth = (ComboBox)parent.lookup("#comboMonth");
			TextField txtKorean = (TextField)parent.lookup("#txtKorean");
			TextField txtMath = (TextField)parent.lookup("#txtMath");
			TextField txtEnglish = (TextField)parent.lookup("#txtEnglish");
			
			Button btnFormAdd = (Button)parent.lookup("#btnFormAdd"); //fx:id말고 그냥 id로 선언되어있는 거 땡겨오는 법
			Button btnFormCancel = (Button)parent.lookup("#btnFormCancel");
			btnFormCancel.setOnAction(e-> addStage.close());
			btnFormAdd.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					updateGrade(listView.getSelectionModel().getSelectedItem().toString(),
							comboMonth.getValue().toString(), txtKorean.getText(),
							txtMath.getText(), txtEnglish.getText());
					addStage.close();
				}
			});
			

			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//성적 추가
	public void updateGrade(String id, String month, String korean, String english, String math) {
		conn = getConnect();
		String sql = "insert into grade values(?,?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, month);
			pstmt.setString(3, korean);
			pstmt.setString(4, english);
			pstmt.setString(5, math);

			int r = pstmt.executeUpdate();
			System.out.println(r + "건 변경됨");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//사용자별 성적표
	public ObservableList<Grade> getGradeList(String id) {
		ObservableList<Grade> list = FXCollections.observableArrayList();
		conn = getConnect();
	
		String sql = "select month, korean, english, math from grade where users = ? order by 1";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Grade grade = new Grade(rs.getString("month"), rs.getInt("korean"), rs.getInt("english"),
						rs.getInt("math"));
				list.add(grade);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	//connect
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

	
	//id 목록
	public ObservableList<String> getUserList() {
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
