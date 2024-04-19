package application.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.control.TextField;

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

	@FXML
	private void deleteAsset() {
		String assetName = searchAssetInput.getText();
		String csvFile = "data/Asset.csv";
		String tempCsv = "data/temp.csv";

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile));
				BufferedWriter bw = new BufferedWriter(new FileWriter(tempCsv))) {
			String line;

			while ((line = br.readLine()) != null) {
				String[] asset = line.split(",");
				// Write to the temp file if the asset is not the one you want to delete.
				if (!asset[1].equals(assetName)) {
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
			displaySuccess();
		} catch (IOException e) {
			System.out.println("Error replacing original file: " + e.getMessage());
		}

		// Delete temp.csv file
		File tempFile = new File(tempCsv);
		if (!tempFile.delete()) {
			System.out.println("Error deleting temp file.");
		}
	}

	private void displayError() {
		// Clear current alert label then display an error message
		alertMessage.setText("");
		alertMessage.setText("Fields marked with an * must be filled!");
		alertMessage.setTextFill(Color.RED);
	}

	private void displaySuccess() {
		// Clear current label then display a success message
		alertMessage.setText("");
		alertMessage.setText("Asset deleted succesfully!");
		alertMessage.setTextFill(Color.RED);
	}

}
