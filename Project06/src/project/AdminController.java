package project;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.application.Platform;
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

public class AdminController implements Initializable {
	@FXML
	ListView listView;
	@FXML
	TableView<Grade> tableView;
	@FXML
	Button btnUpdate, btnAdd;
	Connection conn;
	PreparedStatement pstmt = null;
	String userId;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		//사용자 id 목록
		ObservableList<String> userIdList = getUserList();

		
		//테이블뷰 목록에 매칭시켜주기?
		TableColumn<Grade, ?> tcMonth = tableView.getColumns().get(0);
		tcMonth.setCellValueFactory(new PropertyValueFactory("month"));

		TableColumn<Grade, ?> tckorean = tableView.getColumns().get(1);
		tckorean.setCellValueFactory(new PropertyValueFactory("korean"));

		TableColumn<Grade, ?> tcMath = tableView.getColumns().get(2);
		tcMath.setCellValueFactory(new PropertyValueFactory("english"));

		TableColumn<Grade, ?> tcEnglish = tableView.getColumns().get(3);
		tcEnglish.setCellValueFactory(new PropertyValueFactory("math"));

		
		//리스트뷰에 사용자 id목록들 셋팅해주기.
		listView.setItems(userIdList);
		
		//선택된거
		listView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				//리스트뷰에서 선택된 사용자의 성적 목록 보여주기.
				userId = listView.getSelectionModel().getSelectedItem().toString();
				
				//선택된 아이디의 성적목록 보여주는거.
				tableView.setItems(getGradeList(userId));
			}
		});
		
		//추가 버튼 눌렀을때.
		btnAdd.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				
				buttonAddAction(event);
			}
		});
	
	}
	
	// 추가버튼 눌렀을때.
	public void buttonAddAction(ActionEvent ae) {

		Stage addStage = new Stage(StageStyle.UTILITY);
		addStage.initModality(Modality.WINDOW_MODAL);
		addStage.initOwner(btnAdd.getScene().getWindow());

		try {
			Parent parent = FXMLLoader.load(getClass().getResource("AddForm.fxml"));
			Scene scene = new Scene(parent);
			addStage.setScene(scene);
			addStage.setResizable(false);
			addStage.show();

			ComboBox<String> comboMonth = (ComboBox) parent.lookup("#comboMonth");
			Button btnFormAdd = (Button) parent.lookup("#btnFormAdd");
			Button btnFormCancel = (Button) parent.lookup("#btnFormCancel");
			TextField txtKorean = (TextField) parent.lookup("#txtKorean");
			TextField txtMath = (TextField) parent.lookup("#txtMath");
			TextField txtEnglish = (TextField) parent.lookup("#txtEnglish");
			
			
			//combo박스의 선택된 값
//			System.out.println(comboMonth.getSelectionModel().getSelectedItem());
			
			
			//combo박스에 테이블뷰의 월들을 제외한 값들을 보여주기위한 내용들
			ObservableList<String> monthlist = getUserMonthList(userId);	
			ObservableList<String> monthlist2 = FXCollections.observableArrayList("1월", "2월", "3월", "4월", "5월", "6월",
					"7월", "8월", "9월", "10월", "11월", "12월");
			for (int i = 0; i < monthlist.size(); i++) {
				for (int j = 0; j < monthlist2.size(); j++) {
					if (monthlist.get(i).equals(monthlist2.get(j))) {
						monthlist2.remove(j);
					}
				}
			}

			comboMonth.setItems(monthlist2);

			// 추가 버튼
			btnFormAdd.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					addGrade(listView.getSelectionModel().getSelectedItem().toString(),
							comboMonth.getValue().toString(), txtKorean.getText(), txtMath.getText(),
							txtEnglish.getText());
					
					addStage.close();
				}
			});
			
			
			// 성적추가 화면의 취소버튼
			btnFormCancel.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					addStage.close();
				}
			});

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 성적 추가
	public void addGrade(String id, String month, String korean, String english, String math) {
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

	//성적 변경.
	public void updateGrade(String id, String month, String korean, String english, String math) {
		conn = getConnect();
		String sql = "update grade set korean = ?, math = ?, english = ? where users = ? and month = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, korean);
			pstmt.setString(2, english);
			pstmt.setString(3, math);
			pstmt.setString(4, id);
			pstmt.setString(5, month);
			
			int r = pstmt.executeUpdate();
			System.out.println(r + "건 변경됨");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	// 사용자별 성적표
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

	// id 목록
	public ObservableList<String> getUserList() {
		ObservableList<String> list = FXCollections.observableArrayList();
		conn = getConnect();
		String sql = "select id from info";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String id = rs.getString("id");
				if (id.equals("admin"))
					continue;
				list.add(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 사용자의 month 목록
	public ObservableList<String> getUserMonthList(String id) {
		ObservableList<String> userMonthList = FXCollections.observableArrayList();
		conn = getConnect();
		String sql = "select month from grade where users=? order by 1";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			String userMonth;
			while (rs.next()) {
				userMonth = rs.getString("month");
				userMonthList.add(userMonth);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return userMonthList;
	}
	// 사용자의 month에 해당하는 성적들
		public Grade getUserGradeList(String id, String month) {
			Grade grade =null;
			conn = getConnect();
			String sql = "select korean, math, english from grade where users=? and month=? order by 1";
			try {
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				pstmt.setString(2, month);
				ResultSet rs = pstmt.executeQuery();
				grade = new Grade(rs.getInt("korean"), rs.getInt("english"),
						rs.getInt("math"));
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return grade;
		}


}
