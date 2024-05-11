package application.controller;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.control.ListView;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

public class CategoryReportController {

	@FXML
	AnchorPane mainPane;
	@FXML
	ListView<String> assetsList;
	@FXML
	ChoiceBox<String> catDropdownList;
	@FXML
	private Label alertMessage;
	@FXML
	private ListView<String> categoryResultsList;

	UserSearchPick userAssetPick = UserSearchPick.getInstance();
	private int categoryID;
	private String locationName;

	@FXML
	public void showHomeOp() {
		URL url = getClass().getClassLoader().getResource("view/HomeContent.fxml");

		try {

			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);

			// Clear the define asset content and replace it with the home content
			mainPane.getChildren().clear();
			mainPane.getChildren().add(pane1);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void showReportsPageOp() {
		URL url = getClass().getClassLoader().getResource("view/Reports.fxml");

		try {

			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);

			// Clear the category report content and replace it with the main report page content
			mainPane.getChildren().clear();
			mainPane.getChildren().add(pane1);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void showDisplayAssetOp() {
		URL url = getClass().getClassLoader().getResource("view/DisplayAsset.fxml");

		try {
			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);

			// Clear the define location content and replace it with home content
			mainPane.getChildren().clear();
			mainPane.getChildren().add(pane1);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void initialize() {
		populateDropdownList(catDropdownList, "data/Category.csv", 1);
	}

	// populates the Category drop down
	private void populateDropdownList(ChoiceBox<String> dropdownList, String csvPath, int headerIndex) {
		try (BufferedReader reader = new BufferedReader(new FileReader(csvPath))) {
			String line;
			reader.readLine();
			while ((line = reader.readLine()) != null) {
				String[] columns = line.split(",");
				if (columns.length > 1) {
					dropdownList.getItems().add(columns[headerIndex].trim());
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	// displays list with category chosen from drop down list
	@FXML
	public void searchCategoryAssets() {
		alertMessage.setText("");
		String selectedCategory;
		if (catDropdownList.getValue() != null) {
			selectedCategory = catDropdownList.getValue();
		} else {
			selectedCategory = "";
		}
		String searchQuery = getCategoryID(selectedCategory) + "";
		String csvFile = "data/Asset.csv";
		List<String> searchResults = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
			String line;
			br.readLine();

			while ((line = br.readLine()) != null) {
				String[] asset = line.split(",");

				if (asset.length > 1 && asset[2].contains(searchQuery)) {
					searchResults.add( asset[1] + " - Location: " + getLocationName(asset[3])
							+ " - Expiration: " + asset[7]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			alertMessage.setText("Error: Unable to read the asset file.");
			alertMessage.setTextFill(Color.RED);
		}
		// display search result
		if (searchResults.isEmpty()) {
			categoryResultsList.getItems().clear(); // since there's no assets clear the list view.
			alertMessage.setText("No assets found.");
			alertMessage.setTextFill(Color.RED);
		} else {
			categoryResultsList.getItems().clear(); // Clear previous results
			categoryResultsList.getItems().addAll(searchResults);
		}
	}

	// get category ID from the category name
	private int getCategoryID(String categoryName) {

		File dirf = new File("data/");
		File categoryFile = new File(dirf, "Category.csv");

		if (categoryFile.exists()) {
			try (BufferedReader reader = new BufferedReader(new FileReader(categoryFile))) {
				String line;
				reader.readLine();
				// while line isn't empty
				while ((line = reader.readLine()) != null) {
					// create a string called category which creates different sections with the
					// comma delimiter
					String[] category = line.split(",");
					// if section 1 (the category's name) matches the category name argument, the
					// program has found the matching ID number
					if (category.length > 1 && category[1].equals(categoryName)) {
						categoryID = Integer.parseInt(category[0]);
					}
				}
			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}

		return categoryID;

	}

	//saves the selected Asset
	@FXML
	public void saveAssetPick() {

		String assetPick = categoryResultsList.getSelectionModel().getSelectedItem();

		if (assetPick != null) {

			// find the 1st index of the dash
			int dashIndex = assetPick.indexOf("-");

			if (dashIndex != -1) {
				// only keep the content of the string before the dash
				assetPick = assetPick.substring(0, dashIndex-1);
				System.out.println(assetPick);
			}

			// save the asset the user clicked on
			userAssetPick.setSearchPick(assetPick);
			userAssetPick.setSource("category-report");
			System.out.println(userAssetPick.getSearchPick());
			showDisplayAssetOp();
		}
	}

	//Gets the location name based on id
	private String getLocationName(String locationID) {
		File dirf = new File("data/");
		File locationFile = new File(dirf, "Location.csv");

		if (locationFile.exists()) {
			try (BufferedReader reader = new BufferedReader(new FileReader(locationFile))) {
				String line;
				reader.readLine();
				// while line isn't empty
				while ((line = reader.readLine()) != null) {
					// create a string called location which creates different sections with the
					// comma delimiter
					String[] location = line.split(",");
					// if section 0 (the location's id) matches the location id argument, the
					// program has found the matching name
					if (location.length > 1 && location[0].equals(locationID)) {
						locationName = location[1];
					}
				}
			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
		return locationName;

	}

	@FXML public void returnLocOp() {
		showReportsPageOp();
	}

}
