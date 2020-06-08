package exam2;

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
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class BookController implements Initializable{
	@FXML Button btnAdd,btnChart;
	@FXML TableView<Book> tableview;
	ObservableList<Book> info;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		info = FXCollections.observableArrayList();

		//추가
		btnAdd.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				buttonAddAction(event);
			}
		});
		
		//차트
		btnChart.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				buttonChartAction(event);
			}
		});
		TableColumn<Book, ?> tcBookCode = tableview.getColumns().get(0);
		tcBookCode.setCellValueFactory(new PropertyValueFactory("bookCode"));
		
		TableColumn<Book, ?> tcName = tableview.getColumns().get(1);
		tcName.setCellValueFactory(new PropertyValueFactory("name"));
		
		TableColumn<Book, ?> tcAuthor = tableview.getColumns().get(2);
		tcAuthor.setCellValueFactory(new PropertyValueFactory("author"));
		
		TableColumn<Book, ?> tcPrice = tableview.getColumns().get(3);
		tcPrice.setCellValueFactory(new PropertyValueFactory("price"));
		
		TableColumn<Book, ?> tcQuantity = tableview.getColumns().get(4);
		tcQuantity.setCellValueFactory(new PropertyValueFactory("quantity"));
		
		
		
		tableview.setItems(info);
		System.out.println("입력3");
		tableview.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() == 2) {
					Stage addStage = new Stage(StageStyle.UTILITY);
					addStage.initModality(Modality.WINDOW_MODAL);
					addStage.initOwner(btnAdd.getScene().getWindow());
					
					try {
						Parent parent = FXMLLoader.load(getClass().getResource("UpdateBook.fxml"));
						Scene scene = new Scene(parent);
						addStage.setScene(scene);
						addStage.setResizable(false); 
						addStage.show();

						TextField upBookCode = (TextField)parent.lookup("#upBookCode");
						TextField upName = (TextField)parent.lookup("#upName");
						TextField upAuthor = (TextField)parent.lookup("#upAuthor");
						TextField upPrice = (TextField)parent.lookup("#upPrice");
						TextField upQuantity = (TextField)parent.lookup("#upQuantity");
						upBookCode.setText(tableview.getSelectionModel().getSelectedItem().getBookCode());
						upName.setText(tableview.getSelectionModel().getSelectedItem().getName());
						upAuthor.setText(tableview.getSelectionModel().getSelectedItem().getAuthor());
						upPrice.setText(String.format("%s", tableview.getSelectionModel().getSelectedItem().getPrice()));
						upQuantity.setText(String.format("%s",tableview.getSelectionModel().getSelectedItem().getQuantity()));

						Book book = new Book(
								upBookCode.getText(),
								upName.getText(),
								upAuthor.getText(),
								Integer.parseInt(upPrice.getText()),
								Integer.parseInt(upQuantity.getText())
								
						);
						Button updateBtn = (Button)parent.lookup("#updateBtn");
						updateBtn.setOnAction(new EventHandler<ActionEvent>(){
							@Override
							public void handle(ActionEvent event) {
								tableview.getSelectionModel().getSelectedItem().setBookCode(upBookCode.getText());
								tableview.getSelectionModel().getSelectedItem().setName(upName.getText());
								tableview.getSelectionModel().getSelectedItem().setAuthor(upAuthor.getText());
								tableview.getSelectionModel().getSelectedItem().setPrice(Integer.parseInt(upPrice.getText()));
								tableview.getSelectionModel().getSelectedItem().setQuantity(Integer.parseInt(upQuantity.getText()));
								addStage.close();
							}
						});
						Button updateCancel = (Button)parent.lookup("#updateCancel");
						updateCancel.setOnAction(new EventHandler<ActionEvent>(){
							@Override
							public void handle(ActionEvent event) {
								addStage.close();
							}
						});

					} catch (IOException e) {
						e.printStackTrace();
					}
					
				} else {
					return;
				}
			}
		});
		
	}
	
	public void buttonAddAction(ActionEvent ae) {
		Stage addStage = new Stage(StageStyle.UTILITY);
		addStage.initModality(Modality.WINDOW_MODAL);
		addStage.initOwner(btnAdd.getScene().getWindow()); //해당 id가 있는 window에 새로만드는 윈도우를 뿌리겠다는 거
		
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("AddBook.fxml"));
			Scene scene = new Scene(parent);
			addStage.setScene(scene);
			addStage.setResizable(false); //윈도우 크기 변경 불가능하게 하는거.
			addStage.show();
			
			Button btnBookAdd = (Button)parent.lookup("#btnBookAdd"); //fx:id말고 그냥 id로 선언되어있는 거 땡겨오는 법
			Button btnBookCancel = (Button)parent.lookup("#btnBookCancel");
			btnBookCancel.setOnAction(e-> addStage.close());
			
			btnBookAdd.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					TextField txtBookCode = (TextField)parent.lookup("#txtBookCode");
					TextField txtName = (TextField)parent.lookup("#txtName");
					TextField txtAuthor = (TextField)parent.lookup("#txtAuthor");
					TextField txtPrice = (TextField)parent.lookup("#txtPrice");
					TextField txtQuantity = (TextField)parent.lookup("#txtQuantity");

					Book book = new Book(
							txtBookCode.getText(),
							txtName.getText(),
							txtAuthor.getText(),
							Integer.parseInt(txtPrice.getText()),
							Integer.parseInt(txtQuantity.getText())
					);
					info.add(book);
					addStage.close();
				}
			});
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	} //end of buttonAddAction
	
	public void buttonChartAction(ActionEvent ae) {
		Stage chartStage = new Stage(StageStyle.UTILITY);
		chartStage.initModality(Modality.WINDOW_MODAL);
		chartStage.initOwner(btnAdd.getScene().getWindow());
		
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("BookChart.fxml"));
			BarChart barChart = (BarChart)parent.lookup("#barChart");
			
			//단가
			XYChart.Series<String,Integer> seriesPrice = new XYChart.Series<String, Integer>();
			ObservableList<XYChart.Data<String, Integer>> datasPrice = FXCollections.observableArrayList();
			for(int i=0; i<info.size(); i++) {
				datasPrice.add(new XYChart.Data(info.get(i).getName(), info.get(i).getPrice())
				);
				//"이름", 국어점수 <- 이런모양 원함
			}
			seriesPrice.setData(datasPrice);
			seriesPrice.setName("단가");
			//판매량
			XYChart.Series<String,Integer> seriesQuantity = new XYChart.Series<String, Integer>();
			ObservableList<XYChart.Data<String, Integer>> datasQuantity = FXCollections.observableArrayList();
			for(int i=0; i<info.size(); i++) {
				datasQuantity.add(new XYChart.Data(info.get(i).getName(), info.get(i).getQuantity())
				);
				//"이름", 국어점수 <- 이런모양 원함
			}
			seriesQuantity.setData(datasQuantity);
			seriesQuantity.setName("판매량");
			
			barChart.setData(FXCollections.observableArrayList(seriesPrice,seriesQuantity));
			Scene scene = new Scene(parent);
			
			chartStage.setScene(scene);
			chartStage.setResizable(false);
			chartStage.show();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
