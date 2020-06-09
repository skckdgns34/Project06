package com.yedam.javafx;

import java.awt.Window;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
	@FXML Button updateBtn, cancelBtn, btn, charbtn;
	Connection conn;
	PreparedStatement pstmt = null;
	ObservableList<Grade> grade;
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		 grade = getGradeList();
		
		TableColumn<Grade, ?> tcMonth = tableView.getColumns().get(0);
		tcMonth.setCellValueFactory(new PropertyValueFactory("month"));
		
		TableColumn<Grade, ?> tckorean = tableView.getColumns().get(1);
		tckorean.setCellValueFactory(new PropertyValueFactory("korean"));
		
		TableColumn<Grade, ?> tcMath = tableView.getColumns().get(2);
		tcMath.setCellValueFactory(new PropertyValueFactory("english"));
		
		TableColumn<Grade, ?> tcEnglish = tableView.getColumns().get(3);
		tcEnglish.setCellValueFactory(new PropertyValueFactory("math"));
		
		tableView.setItems(grade);

		
		charbtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				buttonChartAction(arg0);

			}
		});
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



	public void buttonChartAction(ActionEvent ae) {
		Stage chartStage = new Stage(StageStyle.UTILITY);
		chartStage.initModality(Modality.WINDOW_MODAL);
		chartStage.initOwner(btn.getScene().getWindow());

		try {
			Parent parent = FXMLLoader.load(getClass().getResource("Chart.fxml"));
			BarChart barChart = (BarChart) parent.lookup("#barChart");

			XYChart.Series<String, Integer> serieskorean = new XYChart.Series<String, Integer>();
			ObservableList<XYChart.Data<String, Integer>> dataskorean = FXCollections.observableArrayList();
			for (int i = 0; i < grade.size(); i++) {
				dataskorean.add(new XYChart.Data(grade.get(i).getName(), grade.get(i).getKorean()));
				// "이름",국어점수
			}
			serieskorean.setData(dataskorean);
			serieskorean.setName("국어");

			XYChart.Series<String, Integer> seriesMath = new XYChart.Series<String, Integer>();
			ObservableList<XYChart.Data<String, Integer>> datasMath = FXCollections.observableArrayList();
			for (int i = 0; i < grade.size(); i++) {
				datasMath.add(new XYChart.Data(grade.get(i).getName(), grade.get(i).getMath()));
				// "이름",국어점수
			}
			seriesMath.setData(datasMath);
			seriesMath.setName("수학");


			XYChart.Series<String, Integer> seriesEnglish = new XYChart.Series<String, Integer>();
			ObservableList<XYChart.Data<String, Integer>> datasEnglish = FXCollections.observableArrayList();
			for (int i = 0; i < grade.size(); i++) {
				datasEnglish.add(new XYChart.Data(grade.get(i).getName(), grade.get(i).getEnglish()));
				// "이름",영어점수
			}
			seriesEnglish.setData(datasEnglish);
			seriesEnglish.setName("영어");

			barChart.setData(FXCollections.observableArrayList(serieskorean, seriesMath, seriesEnglish));

			Scene scene = new Scene(parent);
			chartStage.setScene(scene);
			chartStage.show();
			chartStage.setResizable(false);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
