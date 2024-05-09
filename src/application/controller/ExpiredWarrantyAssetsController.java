package application.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.ListView;
import java.time.LocalDate;
import javafx.scene.control.Label;

public class ExpiredWarrantyAssetsController {
	
	@FXML AnchorPane mainPane;
	@FXML ListView<String> assetsList;
	private LocalDate warrantyDate;
	
	UserSearchPick userAssetPick = UserSearchPick.getInstance();
	@FXML Label dateToday;

	@FXML public void showHomeOp() {
		URL url = getClass().getClassLoader().getResource("view/HomeContent.fxml");
		
		try {
			
			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);
			
			//Clear the define asset content and replace it with the home content
			mainPane.getChildren().clear();
			mainPane.getChildren().add(pane1);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void showDisplayAssetOp()
	{
		URL url = getClass().getClassLoader().getResource("view/DisplayAsset.fxml");
		
		try 
		{
			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);
			
			//Clear the define location content and replace it with home content
			mainPane.getChildren().clear();
			mainPane.getChildren().add(pane1);
			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	@FXML public void initialize() 
	{
		displayExpiredAssets();
		displayDateToday();
		
	}
	
	
	@FXML public void displayExpiredAssets() {
	    
	    String csvFile = "data/Asset.csv";
	    List<String> expiredAssets = new ArrayList<>();
	    LocalDate today = LocalDate.now();
		System.out.println("Today's date is " + today);

	    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
	        String line;
	        br.readLine();

	        while ((line = br.readLine()) != null) {
	        	
	            String[] asset = line.split(",");
	            
	            if(asset.length > 1 && !asset[7].equals("N/A")) {
		            warrantyDate = LocalDate.parse(asset[7]);
		            System.out.println(asset[1]+": " + warrantyDate);
		        }
	       
	            if (asset.length > 1 && warrantyDate.isBefore(today)) {
	                expiredAssets.add(asset[1] + ": " + asset[7]); 
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	  
	    }
	    
	    if (expiredAssets.isEmpty()) {
	        System.out.println("No expired assets");
	    } else {
	    	assetsList.getItems().clear(); // Clear previous results
	        assetsList.getItems().addAll(expiredAssets);
	    }
	    
	}

	@FXML public void saveAssetPick() {
		
		String assetPick = assetsList.getSelectionModel().getSelectedItem();
		
		if(assetPick != null) {
			
			//find the index of the colon
			int colonIndex = assetPick.indexOf(":");
			
			if(colonIndex != -1) {
				//only keep the content of the string before the colon
				assetPick = assetPick.substring(0,colonIndex);
				System.out.println(assetPick);
			}
			
			//save the asset the user clicked on
			userAssetPick.setSearchPick(assetPick);
			userAssetPick.setSource("expired");
			System.out.println(userAssetPick.getSearchPick());
			showDisplayAssetOp();
		}
	}
	
	private void displayDateToday() {
		LocalDate today = LocalDate.now();
		dateToday.setText("");
		dateToday.setText("Today's date: " + today.toString());
	}
	

}
