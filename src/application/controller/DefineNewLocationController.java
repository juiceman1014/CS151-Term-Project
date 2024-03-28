package application.controller;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class DefineNewLocationController {

	@FXML AnchorPane mainPane;

	@FXML public void showHomeOp() {
		
		URL url = getClass().getClassLoader().getResource("view/HomeContent.fxml");
		
		try {
			
			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);
			
			//clear the content area and replace it with the define new category page
			mainPane.getChildren().clear();
			mainPane.getChildren().add(pane1);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
