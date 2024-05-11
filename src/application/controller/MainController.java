package application.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class MainController {
	
	@FXML HBox mainBox;
	@FXML AnchorPane contentPage;
	
	@FXML public void initialize()
	{
		boolean result = isExpiredWarranty();
		
		if (result)
		{
			showExpiredWarrantyAssetsWarning();
		}
	}

	@FXML public void showDefineNewCategoryOp() {
		
		URL url = getClass().getClassLoader().getResource("view/DefineNewCategory.fxml");
		
		try {
			
			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);
			
			//Clear the home page content area and replace it with the define new category page
			contentPage.getChildren().clear();
			contentPage.getChildren().add(pane1);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@FXML public void showDefineNewLocationOp() {
		
		URL url = getClass().getClassLoader().getResource("view/DefineNewLocation.fxml");
		
		try {
			
			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);
			
			//Clear the home page content area and replace it with the define new location page
			contentPage.getChildren().clear();
			contentPage.getChildren().add(pane1);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	@FXML public void showDefineNewAssetOp() {
		
		URL url = getClass().getClassLoader().getResource("view/DefineNewAsset.fxml");
		
		try {
			
			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);
			
			//Clear the home page content area and replace it with the define new location page
			contentPage.getChildren().clear();
			contentPage.getChildren().add(pane1);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@FXML
	public void showManageAssetsOp() {
		URL url = getClass().getClassLoader().getResource("view/ManageAssets.fxml");

		try {

			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);

			// Clear the home page content area and replace it with the manage category page
			contentPage.getChildren().clear();
			contentPage.getChildren().add(pane1);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML public void showExpiredWarrantyAssetsOp() {
		
		URL url = getClass().getClassLoader().getResource("view/ExpiredWarrantyAssets.fxml");

		try {

			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);

			// Clear the home page content area and replace it with the expired warranty assets page
			contentPage.getChildren().clear();
			contentPage.getChildren().add(pane1);

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@FXML public void showReportsOp() {

		URL url = getClass().getClassLoader().getResource("view/Reports.fxml");

		try {

			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);

			// Clear the home page content area and replace it with the expired warranty assets page
			contentPage.getChildren().clear();
			contentPage.getChildren().add(pane1);

		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	public void showExpiredWarrantyAssetsWarning()
	{
		URL url = getClass().getClassLoader().getResource("view/ExpiredWarrantyAssetsWarning.fxml");

		try {

			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);

			// Clear the home page content area and replace it with the expired warranty assets warning page
			contentPage.getChildren().clear();
			contentPage.getChildren().add(pane1);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private boolean isExpiredWarranty() {
	    
	    String csvFile = "data/Asset.csv";
	    LocalDate today = LocalDate.now();
		System.out.println("Today's date is " + today);
		LocalDate warrantyDate = today;

	    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
	        String line;
	        br.readLine();

	        while ((line = br.readLine()) != null) {
	        	
	            String[] asset = line.split(",");
	            
	            if(asset.length > 1 && !asset[7].equals("N/A")) {
		            warrantyDate = LocalDate.parse(asset[7]);
		            System.out.println(asset[1]+": " + warrantyDate);
		            if (asset.length > 1 && warrantyDate.isBefore(today)) {
		                return true; 
		            }
		        }
	           
	        }
	        
	        return false;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	    
	}
	
}
