package application.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;

public class DefineNewLocationController {

	@FXML AnchorPane mainPane;
	@FXML TextField locationNameInputField;
	@FXML TextArea locationDescriptionInputField;
	@FXML Label alertMessage;

	@FXML public void showHomeOp() {
		
		URL url = getClass().getClassLoader().getResource("view/HomeContent.fxml");
		
		try {
			
			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);
			
			//Clear the define location content and replace it with home content
			mainPane.getChildren().clear();
			mainPane.getChildren().add(pane1);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@FXML public void saveLocationOp() {
		//Store user inputs into their respective variables
		String locationName = locationNameInputField.getText();
		String locationDescription = locationDescriptionInputField.getText();
		
		if(locationDescription.isEmpty()) {
			locationDescription = "N/A";
		}
		/*If the location name input is not empty, clear all input fields, print a success message, and
		 * save their location name and their location description into the Location.csv file*/
		if(!locationName.isEmpty()) {
			locationNameInputField.setText("");
			locationDescriptionInputField.setText("");
			alertMessage.setText("");
			displaySuccess();
			storeToFile(locationName, locationDescription);
		//If the location name input is empty, display an error message
		}else {
			displayError();
			System.out.println("empty input");
		}
	}
	
	private void storeToFile(String locationName, String locationDescription) {
		File dirf = new File("data/");
		File locationFile = new File(dirf, "Location.csv");
		
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(locationFile,true))){
			
			/*Initialize locationID to 0, but increase it by 1 whenever the reader finds a non-null line
			in the Location.csv file.*/
			int locationID = 0;
			if(locationFile.exists()) {
				try(BufferedReader reader = new BufferedReader(new FileReader(locationFile))){
					while(reader.readLine() != null) {
						locationID++;
					}
				}catch(FileNotFoundException e) {
					System.out.println(e.getMessage());
				}
				catch(IOException e) {
					System.out.println(e.getMessage());
				}
			}
			
			//Create string representing the new location
			String newLocation = "\n" + locationID + "," + locationName + "," + locationDescription;
			
			//Write the new location to the Location.csv file
			writer.write(newLocation);
			
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
