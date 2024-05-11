package application.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class DisplayAssetController {
	
	@FXML AnchorPane mainPane;
	@FXML Label assetName;
	@FXML Label assetCategory;
	@FXML Label assetLocation;
	@FXML Label assetPurchaseDate;
	@FXML Label assetDescription;
	@FXML Label assetPurchasedValue;
	@FXML Label assetExpiration;

	UserSearchPick userSearchPick = UserSearchPick.getInstance();
	
	private String categoryName;
	private String locationName;
	private String tempName;
	
	public void showExpiredWarrantyAssetsOp() {
		
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
	
	public void showLocationReportOp() {
		URL url = getClass().getClassLoader().getResource("view/LocationReport.fxml");

		try {

			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);

			// Clear the home page content area and replace it with the expired warranty assets page
			mainPane.getChildren().clear();
			mainPane.getChildren().add(pane1);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showCategoryReportOp() {
		URL url = getClass().getClassLoader().getResource("view/CategoryReport.fxml");

		try {

			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);

			// Clear the home page content area and replace it with the expired warranty assets page
			mainPane.getChildren().clear();
			mainPane.getChildren().add(pane1);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML public void returnLocOp()
	{
		String source = userSearchPick.getSource();
		
		if(source.equals("expired"))
		{
			showExpiredWarrantyAssetsOp();
		}else if(source.equals("category-report")) 
		{
			showCategoryReportOp();
		}
		else if(source.equals("location-report")) 
		{
			showLocationReportOp();
		}
		
	}
	
	
	@FXML public void initialize() 
	{
		setInitialVals();
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
						if (searchedAssetName.equals(tempName))
						{
							assetName.setText(lineSplit[1].trim());
							assetCategory.setText(getCategoryName(Integer.parseInt(lineSplit[2].trim())));
							assetLocation.setText(getLocationName(Integer.parseInt(lineSplit[3].trim())));
							assetPurchaseDate.setText(lineSplit[4].trim());
							assetDescription.setText(lineSplit[5].trim());
							assetPurchasedValue.setText(lineSplit[6].trim());
							assetExpiration.setText(lineSplit[7].trim());
							
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
						if(category.length > 1 && Integer.parseInt(category[0]) == categoryID) {
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
						if(location.length > 1 && Integer.parseInt(location[0]) == locationID) {
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

}
