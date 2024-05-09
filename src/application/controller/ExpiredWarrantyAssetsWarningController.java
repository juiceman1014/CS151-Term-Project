package application.controller;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class ExpiredWarrantyAssetsWarningController 
{
	@FXML AnchorPane mainPane;
	
	@FXML public void showHomeOp() {
		
		URL url = getClass().getClassLoader().getResource("view/HomeContent.fxml");
		
		try {
			
			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);
			
			//Clear the define category content and replace it with the home content
			mainPane.getChildren().clear();
			mainPane.getChildren().add(pane1);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@FXML public void showExpiredWarrantyAssetsOp() {
		
		URL url = getClass().getClassLoader().getResource("view/ExpiredWarrantyAssets.fxml");

		try {

			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);

			// Clear the home page content area and replace it with the expired warranty assets page
			mainPane.getChildren().clear();
			mainPane.getChildren().add(pane1);

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
