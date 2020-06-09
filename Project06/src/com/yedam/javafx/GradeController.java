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

public class GradeController implements Initializable {
	@FXML
	TableView<Grade> tableView;
	@FXML
	Button btnAdd, btnChart;

	ObservableList<Grade> scores;

	Stage primaryStage;

	void setPrimaryStage(Stage PrimaryStage) {
		this.primaryStage = primaryStage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		scores = FXCollections.observableArrayList();

		btnAdd.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				buttonAddAction(arg0);

			}
		});
		btnChart.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				buttonChartAction(arg0);

			}
		});

		TableColumn<Grade, ?> tcMonth = tableView.getColumns().get(0);
		tcMonth.setCellValueFactory(new PropertyValueFactory("month"));
		TableColumn<Grade, ?> tcKorean = tableView.getColumns().get(1);
		tcKorean.setCellValueFactory(new PropertyValueFactory("korean"));
		TableColumn<Grade, ?> tcMath = tableView.getColumns().get(2);
		tcMath.setCellValueFactory(new PropertyValueFactory("math"));
		TableColumn<Grade, ?> tcEnglish = tableView.getColumns().get(3);
		tcEnglish.setCellValueFactory(new PropertyValueFactory("english"));

		tableView.setItems(scores);
	}

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

			Button btnFormAdd = (Button) parent.lookup("#btnFormAdd");
			btnFormAdd.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					TextField txtMonth = (TextField) parent.lookup("#txtMonth");
					TextField txtKorean = (TextField) parent.lookup("#txtKorean");
					TextField txtMath = (TextField) parent.lookup("#txtMath");
					TextField txtEnglish = (TextField) parent.lookup("#txtEnglish");
					Grade student = new Grade(txtMonth.getText(), Integer.parseInt(txtKorean.getText()),
							Integer.parseInt(txtMath.getText()), Integer.parseInt(txtEnglish.getText()));
					scores.add(grade);
					addStage.close();
				}
			});

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void buttonChartAction(ActionEvent ae) {
		Stage chartStage = new Stage(StageStyle.UTILITY);
		chartStage.initModality(Modality.WINDOW_MODAL);
		chartStage.initOwner(btnAdd.getScene().getWindow());

		try {
			Parent parent = FXMLLoader.load(getClass().getResource("ScoreChart.fxml"));
			BarChart barChart = (BarChart) parent.lookup("#barChart");

			XYChart.Series<String, Integer> serieskorean = new XYChart.Series<String, Integer>();
			ObservableList<XYChart.Data<String, Integer>> dataskorean = FXCollections.observableArrayList();
			for (int i = 0; i < scores.size(); i++) {
				dataskorean.add(new XYChart.Data(scores.get(i).getName(), scores.get(i).getKorean()));
				// "이름",국어점수
			}
			serieskorean.setData(dataskorean);
			serieskorean.setName("국어");

			XYChart.Series<String, Integer> seriesMath = new XYChart.Series<String, Integer>();
			ObservableList<XYChart.Data<String, Integer>> datasMath = FXCollections.observableArrayList();
			for (int i = 0; i < scores.size(); i++) {
				datasMath.add(new XYChart.Data(scores.get(i).getName(), scores.get(i).getMath()));
				// "이름",국어점수
			}
			seriesMath.setData(datasMath);
			seriesMath.setName("수학");


			XYChart.Series<String, Integer> seriesEnglish = new XYChart.Series<String, Integer>();
			ObservableList<XYChart.Data<String, Integer>> datasEnglish = FXCollections.observableArrayList();
			for (int i = 0; i < scores.size(); i++) {
				datasEnglish.add(new XYChart.Data(scores.get(i).getName(), scores.get(i).getEnglish()));
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
