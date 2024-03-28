package application.controller;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class MainController {

	@FXML HBox mainBox;
	@FXML AnchorPane contentPage;

	@FXML public void showDefineNewCategoryOp() {
		
		URL url = getClass().getClassLoader().getResource("view/DefineNewCategory.fxml");
		
		try {
			
			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);
			
			//clear the content area and replace it with the define new category page
			contentPage.getChildren().clear();
			contentPage.getChildren().add(pane1);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@FXML public void showDefineNewLocationOp() {
		
		URL url = getClass().getClassLoader().getResource("view/DefineNewLocation.fxml");
		
		try {
			
			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);
			
			//clear the content area and replace it with the define new category page
			contentPage.getChildren().clear();
			contentPage.getChildren().add(pane1);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
