package application.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextField;


import java.io.FileReader;
import java.io.FileWriter;

import javafx.scene.control.Label;


public class DefineNewCategoryController {
	
	@FXML AnchorPane mainPane;
	@FXML TextField categoryNameInputField;
	@FXML Label alertMessage;

	@FXML public void showHomeOp() {
		
		URL url = getClass().getClassLoader().getResource("view/HomeContent.fxml");
		
		try {
			
			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);
			
			//Clear the define category content and replace it with the home content
			mainPane.getChildren().clear();
			mainPane.getChildren().add(pane1);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@FXML public void saveCategoryOp() {
		//Grab the text from the input field and store it into a string
		String categoryName = categoryNameInputField.getText();
		/*If the category was not empty, then clear the input field, display a success message,
		 and call the function to store the string into the Category.csv file*/
		if(!categoryName.isEmpty()) {
			categoryNameInputField.setText("");
			alertMessage.setText("");
			displaySuccess();
			storeToFile(categoryName);
		//If category was empty, print out an error message for the user
		}else {
			displayError();
			//Debugging
			System.out.println("empty input");
		}
	}
	
	private void storeToFile(String categoryName) {
	
		File dirf = new File("data/");
		File categoryFile = new File(dirf, "Category.csv");
		
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(categoryFile,true))){
			
			/*Initialize categoryID to 0, but increase it by 1 whenever the reader finds a non-null line
			in the Category.csv file.*/
			int categoryID = 0;
			if(categoryFile.exists()) {
				try(BufferedReader reader = new BufferedReader(new FileReader(categoryFile))){
					while(reader.readLine() != null) {
						categoryID++;
					}
				}catch(FileNotFoundException e) {
					System.out.println(e.getMessage());
				}
				catch(IOException e) {
					System.out.println(e.getMessage());
				}
			}
			
			//Create string representing the new category.
			String newCategory = "\n" + categoryID + "," + categoryName;
			
			//Write the new category into the Category.csv file
			writer.write(newCategory);
			
			//Debugging
			System.out.println("Succesful save");
			
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void displayError() {
		//Clear current alert label then display an error message
		alertMessage.setText("");
		alertMessage.setText("Fields marked with an * must be filled!");
	}
	
	private void displaySuccess() {
		//Clear current label then display a success message
		alertMessage.setText("");
		alertMessage.setText("Category saved succesfully!");
	}

	
}
