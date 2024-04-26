package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

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
	
	private int categoryID;
	private int locationID;
	
	

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
		File assetFile = new File(dirf,"Asset.csv");
		ArrayList<String> assetCSVContent = new ArrayList<>();
		int emptyLineIndex = -1;
		int assetID;
		String newAsset;
		
		if(assetFile.exists()) {
			//read through lines of current asset file and add them to the assetCSVContent ArrayList
			try(BufferedReader reader = new BufferedReader(new FileReader(assetFile))) {
				String line;
				while((line = reader.readLine()) != null) {
					assetCSVContent.add(line);
			}
			} catch(FileNotFoundException e) {
				System.out.println(e.getMessage());
			} catch(IOException e) {
				System.out.println(e.getMessage());
			}
		}
	
		//parse through the array list and search for an empty line. immediately break the for loop if one is found.
		//empty lines are a result of the delete asset calls.
		for(int i = 1; i < assetCSVContent.size(); i++) {
			if(assetCSVContent.get(i).isEmpty()) {
				emptyLineIndex = i;
				break;
			}
		}
		
		//if no empty line was found from the previous for loop, the new asset will receive an ID corresponding to the last line of the csv file.
		if(emptyLineIndex == -1) {
			assetID = assetCSVContent.size();
			newAsset = assetID + "," + name + "," + getCategoryID(category) + "," + getLocationID(location) + "," + purchaseDate + "," + description + "," + purchasedValue + "," + warrantyExpDate;
		//otherwise the correctID corresponds to where the empty line is located.
		}else {
			assetID = emptyLineIndex;
			newAsset = assetID + "," + name + "," + getCategoryID(category) + "," + getLocationID(location) + "," + purchaseDate + "," + description + "," + purchasedValue + "," + warrantyExpDate;
		}
		
		//if emptyLineIndex != -1, that means an empty line was found and so we fill in the empty line with the new asset
		if(emptyLineIndex != -1) {
			assetCSVContent.set(emptyLineIndex, newAsset);
		//otherwise append the asset to the end of the file as there is no empty line to fill.
		}else {
			assetCSVContent.add(newAsset);
		}
		
		//Overwrite the Asset.csv file with the assetCSVContent array list.
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(assetFile, false))) {
			for(String line : assetCSVContent) {
				writer.write(line);
				writer.newLine();
			}
		}catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		catch(IOException e) {
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
	
	//get category ID from the category name
	private int getCategoryID(String categoryName) {
		
		File dirf = new File("data/");
		File categoryFile = new File(dirf, "Category.csv");
		
		if(categoryFile.exists()) {
			try(BufferedReader reader = new BufferedReader(new FileReader(categoryFile))){
				String line;
				reader.readLine();
				//while line isn't empty
				while((line = reader.readLine()) != null) {
					//create a string called category which creates different sections with the comma delimiter
					String[] category = line.split(",");
					//if section 1 (the category's name) matches the category name argument, the program has found the matching ID number
					if(category[1].equals(categoryName)) {
						categoryID = Integer.parseInt(category[0]);
					}
				}
			}catch(FileNotFoundException e) {
					System.out.println(e.getMessage());
			}
			catch(IOException e) {
					System.out.println(e.getMessage());
			}
		}
		
		System.out.println("categoryID: " + categoryID);
		return categoryID;
		
	}
	
	//get location ID from the location name
	private int getLocationID(String locationName) {
		
		File dirf = new File("data/");
		File locationFile = new File(dirf, "Location.csv");
		
		if(locationFile.exists()) {
			try(BufferedReader reader = new BufferedReader(new FileReader(locationFile))){
				String line;
				reader.readLine();
				while((line = reader.readLine()) != null) {
					String[] location = line.split(",");
					if(location[1].equals(locationName)) {
						locationID = Integer.parseInt(location[0]);
					}
				}
			}catch(FileNotFoundException e) {
					System.out.println(e.getMessage());
			}
			catch(IOException e) {
					System.out.println(e.getMessage());
			}
		}
		
		System.out.println("locationID: " + locationID);
		return locationID;
		
	}

}
