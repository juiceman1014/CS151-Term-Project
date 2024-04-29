package application.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;

import java.io.FileReader;
import java.io.FileWriter;

import javafx.scene.control.Label;

public class ManageAssetsController {

	@FXML
	AnchorPane mainPane;
	@FXML
	TextField searchAssetInput;
	@FXML
	Label alertMessage;
	@FXML
	private ListView<String> searchResultsList;
	
	UserSearchPick userSearchPick = UserSearchPick.getInstance();
	

	@FXML
	public void showHomeOp() {

		URL url = getClass().getClassLoader().getResource("view/HomeContent.fxml");

		try {

			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);

			// Clear the define category content and replace it with the home content
			mainPane.getChildren().clear();
			mainPane.getChildren().add(pane1);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	@FXML
	private void showEditAssetOp()
	{
		URL url = getClass().getClassLoader().getResource("view/EditAsset.fxml");
		
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
	
	@FXML public void initialize() {
		userSearchPick.setSearchPick("");
	}
	

	@FXML
	private void deleteAsset() {
		String assetName = searchAssetInput.getText();
		if(assetName.isEmpty()) {
			displayDeletionError();
			return;
		}
		String csvFile = "data/Asset.csv";
		String tempCsv = "data/temp.csv";

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile));
				BufferedWriter bw = new BufferedWriter(new FileWriter(tempCsv))) {
			String line;

			while ((line = br.readLine()) != null) {
				String[] asset = line.split(",");
				//If asset is the one you want to delete, replace it with an empty line
				if (asset.length > 1 && asset[1].equals(assetName)) {
					bw.newLine();
				// Write to the temp file if the asset is not the one you want to delete.
				}else {
					bw.write(line);
					bw.newLine();
				}
				// Creates a new CSV file without the asset you want to remove.
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			//copy the content in temp.csv to Asset.csv
			Files.copy(Paths.get(tempCsv), Paths.get(csvFile), StandardCopyOption.REPLACE_EXISTING);
			System.out.println("File replaced successfully.");
			displayDeletionSuccess();
		} catch (IOException e) {
			System.out.println("Error replacing original file: " + e.getMessage());
		}

		// Delete temp.csv file
		File tempFile = new File(tempCsv);
		if (!tempFile.delete()) {
			System.out.println("Error deleting temp file.");
		}
		
		URL url = getClass().getClassLoader().getResource("view/ManageAssets.fxml");

		try {

			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);

			// Clear the edit asset page content area and replace it with the manage category page
			mainPane.getChildren().clear();
			mainPane.getChildren().add(pane1);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void searchAssets() {
	    String searchQuery = searchAssetInput.getText().toLowerCase();
	    String csvFile = "data/Asset.csv";
	    List<String> searchResults = new ArrayList<>();

	    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
	        String line;
	        br.readLine();

	        while ((line = br.readLine()) != null) {
	            String[] asset = line.split(",");
	       
	            if (asset.length > 1 && asset[1].toLowerCase().contains(searchQuery)) {
	                searchResults.add(asset[1]); 
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	        alertMessage.setText("Error: Unable to read the asset file.");
	        alertMessage.setTextFill(Color.RED);
	    }

	    //display search result

	    if (searchResults.isEmpty()) {
	        alertMessage.setText("No assets found.");
	        alertMessage.setTextFill(Color.RED);
	    } else {
	    	searchResultsList.getItems().clear(); // Clear previous results
	        searchResultsList.getItems().addAll(searchResults);
	    }
	}
	
	//save the clicked asset in the list into the search bar
	@FXML public void saveSearchPick() {
		String searchPick = searchResultsList.getSelectionModel().getSelectedItem();
		if(searchPick != null) {
			searchAssetInput.setText(searchPick);
			userSearchPick.setSearchPick(searchPick);
		}
	}
	
	private void displayDeletionError() {
		// Clear current alert label then display an error message
		alertMessage.setText("");
		alertMessage.setText("No assets were deleted!");
		alertMessage.setTextFill(Color.RED);
	}
	
	private void displayDeletionSuccess() {
		// Clear current label then display a success message
		alertMessage.setText("");
		alertMessage.setText("Asset deleted succesfully!");
		alertMessage.setTextFill(Color.RED);
	}

	

}
