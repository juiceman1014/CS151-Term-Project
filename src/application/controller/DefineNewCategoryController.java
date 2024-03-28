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
	@FXML TextField inputField;
	@FXML Label alertMessage;

	@FXML public void showHomeOp() {
		
		URL url = getClass().getClassLoader().getResource("view/HomeContent.fxml");
		
		try {
			
			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);
			
			//clear the content area and replace it with the home content
			mainPane.getChildren().clear();
			mainPane.getChildren().add(pane1);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@FXML public void saveCategoryOp() {
		String categoryName = inputField.getText();
		if(!categoryName.isEmpty()) {
			inputField.setText("");
			alertMessage.setText("");
			displaySuccess();
			storeToFile(categoryName);
		}else {
			displayError();
			System.out.println("empty input");
		}
	}
	
	private void storeToFile(String categoryName) {
		File dirf = new File("/Users/justintran/eclipse-workspace/CS151TermProject/data/");
		File categoryFile = new File(dirf, "Category.csv");
		
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(categoryFile,true))){
			
			
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
						
			String newCategory = "\n" + categoryID + "," + categoryName;
			
			writer.write(newCategory);
			
			System.out.println("Succesful save");
			
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void displayError() {
		alertMessage.setText("");
		alertMessage.setText("Fields marked with an * must be filled!");
	}
	
	private void displaySuccess() {
		alertMessage.setText("");
		alertMessage.setText("Category saved succesfully!");
	}

	
}
