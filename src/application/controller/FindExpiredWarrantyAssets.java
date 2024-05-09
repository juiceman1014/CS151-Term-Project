package application.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

public class FindExpiredWarrantyAssets
{
	public boolean isExpiredWarranty() {
	    
	    String csvFile = "data/Asset.csv";
	    LocalDate today = LocalDate.now();
		System.out.println("Today's date is " + today);
		LocalDate warrantyDate = today;

	    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
	        String line;
	        br.readLine();

	        while ((line = br.readLine()) != null) {
	        	
	            String[] asset = line.split(",");
	            
	            if(asset.length > 1 && !asset[7].equals("N/A")) {
		            warrantyDate = LocalDate.parse(asset[7]);
		            System.out.println(asset[1]+": " + warrantyDate);
		        }
	       
	            if (asset.length > 1 && warrantyDate.isBefore(today)) {
	                return true; 
	            }
	        }
	        
	        return false;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	    
	}
}
