package application.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class EditAssetController
{
	@FXML AnchorPane mainPane;
	@FXML TextField assetNameInput;
	@FXML ChoiceBox<String> catDropdownList;
	@FXML ChoiceBox<String> locDropdownList;
	@FXML DatePicker assetPurchaseDateInput;
	@FXML TextArea assetDescriptionInput;
	@FXML TextField assetPurchasedValueInput;
	@FXML DatePicker assetWarrantyExpDateInput;
	@FXML Label alertMessage;

	UserSearchPick userSearchPick = UserSearchPick.getInstance();
	
	private String categoryName;
	private String locationName;
	private int assetID;
	private int categoryID;
	private int locationID;
	private String tempName;
	
	
	
	@FXML public void showManageAssetsOp() {
		URL url = getClass().getClassLoader().getResource("view/ManageAssets.fxml");

		try {

			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);

			// Clear the home page content area and replace it with the manage category page
			mainPane.getChildren().clear();
			mainPane.getChildren().add(pane1);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	@FXML public void initialize() 
	{
		populateDropdownList(catDropdownList,"data/Category.csv",1);
		populateDropdownList(locDropdownList,"data/Location.csv",1);
		
		
		setInitialVals();
	}
	
	@FXML public void saveEditAssetOp()
	{
		String assetName = assetNameInput.getText(); 
		String assetCategory; 
		String assetLocation; 
		String assetPurchaseDate; 
		String assetDescription = assetDescriptionInput.getText(); 
		String assetPurchasedValue = assetPurchasedValueInput.getText(); 
		String assetWarrantyExpDate; 
		
		if(assetPurchaseDateInput.getValue() != null) 
		{
			assetPurchaseDate = assetPurchaseDateInput.getValue().toString();
		}
		else 
		{
			assetPurchaseDate = "N/A";
		}
		
		if(assetWarrantyExpDateInput.getValue() != null) 
		{
			assetWarrantyExpDate = assetWarrantyExpDateInput.getValue().toString();
		}
		else 
		{
			assetWarrantyExpDate = "N/A";
		}
		
		if(catDropdownList.getValue() != null) 
		{
			assetCategory = catDropdownList.getValue();
		}
		else 
		{
			assetCategory = "";
		}
		
		if(locDropdownList.getValue() != null) 
		{
			assetLocation = locDropdownList.getValue();
		}
		else 
		{
			assetLocation = "";
		}
		
		if(assetDescription.isEmpty())
		{
			assetDescription = "N/A";
		}
		
		if(assetPurchasedValue.isEmpty()) 
		{
			assetPurchasedValue= "N/A";
		}
		
		
		if(!assetName.isEmpty() && !assetCategory.isEmpty() && !assetLocation.isEmpty()) 
		{
			assetNameInput.setText("");
			catDropdownList.setValue(null);
			locDropdownList.setValue(null);
			assetPurchaseDateInput.setValue(null);
			assetDescriptionInput.setText("");
			assetPurchasedValueInput.setText("");
			assetWarrantyExpDateInput.setValue(null);
			displaySuccess();
			storeToFile(assetName, assetCategory, assetLocation, assetPurchaseDate, assetDescription, assetPurchasedValue, assetWarrantyExpDate);
			URL url = getClass().getClassLoader().getResource("view/ManageAssets.fxml");

			try {

				AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);

				// Clear the home page content area and replace it with the manage category page
				mainPane.getChildren().clear();
				mainPane.getChildren().add(pane1);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else 
		{
			displayError();
		}
	}
	
	private void populateDropdownList(ChoiceBox<String> dropdownList, String csvPath, int headerIndex) 
	{
		try(BufferedReader reader = new BufferedReader(new FileReader(csvPath)))
		{
			String line;
			reader.readLine();
			while((line = reader.readLine()) != null) 
			{
				String[] columns = line.split(",");
				dropdownList.getItems().add(columns[headerIndex].trim());	
			}
		}
		catch(FileNotFoundException e) 
		{
			System.out.println(e.getMessage());
		} 
		catch(IOException e) 
		{
			System.out.println(e.getMessage());
		}
	}
	
	private LocalDate dateFormatter(String date)
	{
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    LocalDate formatedDate = LocalDate.parse(date, format);
	    return formatedDate;
	}
	
	private void setInitialVals()
	{
		String searchedAssetName = userSearchPick.getSearchPick();
		File dirf = new File("data/");
		File assetFile = new File(dirf, "Asset.csv");
		
		if(assetFile.exists()) 
		{
			try(BufferedReader reader = new BufferedReader(new FileReader(assetFile)))
			{
				String readLine;
				reader.readLine();	// skip the first line in the csv file
				while((readLine = reader.readLine()) != null) 
				{
					String[] lineSplit= readLine.split(",");
					if(lineSplit.length > 1) {
						tempName = lineSplit[1].trim();
					}
					if (searchedAssetName.equals(tempName))
					{
						assetID = Integer.parseInt(lineSplit[0]);
						assetNameInput.setText(lineSplit[1].trim());
						catDropdownList.setValue(getCategoryName(Integer.parseInt(lineSplit[2].trim())));
						locDropdownList.setValue(getLocationName(Integer.parseInt(lineSplit[3].trim())));
						if (!lineSplit[4].trim().equals("N/A"))
						{
							assetPurchaseDateInput.setValue(dateFormatter(lineSplit[4].trim()));
						}
						assetDescriptionInput.setText(lineSplit[5].trim());
						assetPurchasedValueInput.setText(lineSplit[6].trim());
						if (!lineSplit[7].trim().equals("N/A"))
						{
							assetWarrantyExpDateInput.setValue(dateFormatter(lineSplit[7].trim()));
						}
					}
				}
			}
			catch(FileNotFoundException e) 
			{
				System.out.println(e.getMessage());
			}
			catch(IOException e) 
			{
				System.out.println(e.getMessage());
			}
		}
	}
	
	private void storeToFile(String assetName, String assetCategory, String assetLocation, String assetPurchaseDate, String assetDescription, String assetPurchasedValue,  String assetWarrantyExpDate)
	{
		
		File dirf = new File("data/");
		File assetFile = new File(dirf, "Asset.csv");
		File tempFile = new File(dirf, "temp.csv");
		
		try (BufferedReader reader = new BufferedReader(new FileReader(assetFile)); BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile)))
		{
			String readLine = reader.readLine();
			writer.write(readLine);
			
			while ((readLine = reader.readLine()) != null) 
			{
				String[] asset = readLine.split(",");
				
				if (Integer.parseInt(asset[0]) == assetID)
				{
					String editedAsset = "\n" + asset[0] + "," + assetName + "," + getCategoryID(assetCategory) + "," + getLocationID(assetLocation) + "," + assetPurchaseDate + "," + assetDescription + "," + assetPurchasedValue + "," + assetWarrantyExpDate;
					writer.write(editedAsset);
					setInitialVals();
				}
				else
				{
					writer.write("\n" + readLine);
				}
			}
		}
		catch (FileNotFoundException e) 
		{
			System.out.println(e.getMessage());
		} 
		catch(IOException e) 
		{
			System.out.println(e.getMessage());
		}
		
		tempFile.renameTo(assetFile);
		tempFile.delete();
		setInitialVals();
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
		alertMessage.setText("Asset edit saved succesfully!");
		alertMessage.setTextFill(Color.RED);
	}
	
	//get category name from the category ID
	private String getCategoryName(int categoryID) {
		
		File dirf = new File("data/");
		File categoryFile = new File(dirf, "Category.csv");
		
		if(categoryFile.exists()) {
			try(BufferedReader reader = new BufferedReader(new FileReader(categoryFile))){
				String line;
				reader.readLine();
				//while line isn't empty
				while((line = reader.readLine()) != null) {
					//create a category string that has different sections based on the comma delimiter
					String[] category = line.split(",");
					//if this category's ID matches the ID passed into the method, the matching category name has been found
					if(Integer.parseInt(category[0]) == categoryID) {
						categoryName = category[1];
					}
				}
			}catch(FileNotFoundException e) {
					System.out.println(e.getMessage());
			}
			catch(IOException e) {
					System.out.println(e.getMessage());
			}
		}
		
		return categoryName;
		
	}
	
	//get location name from the location ID
	private String getLocationName(int locationID) {
		
		File dirf = new File("data/");
		File locationFile = new File(dirf, "Location.csv");
		
		if(locationFile.exists()) {
			try(BufferedReader reader = new BufferedReader(new FileReader(locationFile))){
				String line;
				reader.readLine();
				while((line = reader.readLine()) != null) {
					String[] location = line.split(",");
					if(Integer.parseInt(location[0]) == locationID) {
						locationName = location[1];
					}
				}
			}catch(FileNotFoundException e) {
					System.out.println(e.getMessage());
			}
			catch(IOException e) {
					System.out.println(e.getMessage());
			}
		}
		
		return locationName;
		
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
					//create a string named category that creates different sections using the "," delimiter
					String[] category = line.split(",");
					//if section 1 of the category string equals the category name that was passed into the method, the matching ID has been found.
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
		
		return locationID;
		
	}
	
	

	
}
