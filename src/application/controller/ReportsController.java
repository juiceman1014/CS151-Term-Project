package application.controller;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class ReportsController {

	@FXML
	AnchorPane mainPane;
	
	UserSearchPick userReportPick = UserSearchPick.getInstance();
	
	@FXML
	public void initialize() {
		userReportPick.setSource("reportsPage");
	}

	@FXML
	public void showHomeOp() {

		URL url = getClass().getClassLoader().getResource("view/HomeContent.fxml");

		try {

			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);

			// Clear the define asset content and replace it with the home content
			mainPane.getChildren().clear();
			mainPane.getChildren().add(pane1);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	@FXML
	public void showLocationReportsOp() {
		URL url = getClass().getClassLoader().getResource("view/LocationReport.fxml");

		try {

			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);

			// Clear the define asset content and replace it with the home content
			mainPane.getChildren().clear();
			mainPane.getChildren().add(pane1);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void showCategoryReportsOp() {
		URL url = getClass().getClassLoader().getResource("view/CategoryReport.fxml");

		try {

			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);

			// Clear the define asset content and replace it with the home content
			mainPane.getChildren().clear();
			mainPane.getChildren().add(pane1);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
