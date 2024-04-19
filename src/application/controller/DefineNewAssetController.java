package application.controller;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.control.ChoiceBox;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;

public class DefineNewAssetController {
	
	@FXML AnchorPane mainPane;
	@FXML ChoiceBox<String> catDropdownList;
	@FXML ChoiceBox<String> locDropdownList;
	@FXML TextField assetNameInput;
	@FXML DatePicker assetPurchaseDateInput;
	@FXML DatePicker assetWarrantyExpDateInput;
	@FXML TextArea assetDescriptionInput;
	@FXML TextField assetPurchasedValueInput;
	@FXML Label alertMessage;
	
	

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
	
	@FXML public void initialize() {
		populateDropdownList(catDropdownList,"data/Category.csv",1);
		populateDropdownList(locDropdownList,"data/Location.csv",1);
	}
	
	private void populateDropdownList(ChoiceBox<String> dropdownList, String csvPath, int headerIndex) {
		try(BufferedReader reader = new BufferedReader(new FileReader(csvPath))){
			String line;
			reader.readLine();
			while((line = reader.readLine()) != null) {
				String[] columns = line.split(",");
				dropdownList.getItems().add(columns[headerIndex].trim());	
			}
		} catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@FXML public void saveAssetOp() {
		
		String assetName = assetNameInput.getText(); 
		String assetCategory; 
		String assetLocation; 
		String assetPurchaseDate; 
		String assetDescription = assetDescriptionInput.getText(); 
		String assetPurchasedValue = assetPurchasedValueInput.getText(); 
		String assetWarrantyExpDate; 
		
		if(assetPurchaseDateInput.getValue() != null) {
			assetPurchaseDate = assetPurchaseDateInput.getValue().toString();
		}else {
			assetPurchaseDate = "N/A";
		}
		
		if(assetWarrantyExpDateInput.getValue() != null) {
			assetWarrantyExpDate = assetWarrantyExpDateInput.getValue().toString();
		}else {
			assetWarrantyExpDate = "N/A";
		}
		
		if(catDropdownList.getValue() != null) {
			assetCategory = catDropdownList.getValue();
		}else {
			assetCategory = "";
		}
		
		if(locDropdownList.getValue() != null) {
			assetLocation = locDropdownList.getValue();
		}else {
			assetLocation = "";
		}
		
		if(assetDescription.isEmpty()){
			assetDescription = "N/A";
		}
		
		if(assetPurchasedValue.isEmpty()) {
			assetPurchasedValue= "N/A";
		}
		
		
		if(!assetName.isEmpty() && !assetCategory.isEmpty() && !assetLocation.isEmpty()) {
			assetNameInput.setText("");
			catDropdownList.setValue(null);
			locDropdownList.setValue(null);
			assetPurchaseDateInput.setValue(null);
			assetDescriptionInput.setText("");
			assetPurchasedValueInput.setText("");
			assetWarrantyExpDateInput.setValue(null);
			displaySuccess();
			storeToFile(assetName, assetCategory, assetLocation, assetPurchaseDate, assetDescription, assetPurchasedValue, assetWarrantyExpDate);
		}else {
			displayError();
		}
		
	}
	
	private void storeToFile(String name, String category, String location, String purchaseDate, String description, String purchasedValue, String warrantyExpDate) {
		File dirf = new File("data/");
		File categoryFile = new File(dirf, "Asset.csv");
		
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(categoryFile,true))){
			
			/*Initialize assetID to 0, but increase it by 1 whenever the reader finds a non-null line
			in the Asset.csv file.*/
			int assetID = 0;
			if(categoryFile.exists()) {
				try(BufferedReader reader = new BufferedReader(new FileReader(categoryFile))){
					while(reader.readLine() != null) {
						assetID++;
					}
				}catch(FileNotFoundException e) {
					System.out.println(e.getMessage());
				}
				catch(IOException e) {
					System.out.println(e.getMessage());
				}
			}
			
			//Create string representing the new asset
			String newAsset = "\n" + assetID + "," + name + "," + category + "," + location + "," + purchaseDate + "," + description + "," + purchasedValue + "," + warrantyExpDate;
			
			//Write the new asset to the Asset.csv file
			writer.write(newAsset);
			
			//Debugging
			System.out.println("Succesful save");
			
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void displayError() {
		//Clear current alert label and display an error message
		alertMessage.setText("");
		alertMessage.setText("Fields marked with an * must be filled!");
		alertMessage.setTextFill(Color.RED);
	}
	
	private void displaySuccess() {
		//Clear current alert label and display a success message
		alertMessage.setText("");
		alertMessage.setText("Category saved succesfully!");
		alertMessage.setTextFill(Color.RED);
	}

}
