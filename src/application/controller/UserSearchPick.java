package application.controller;

public class UserSearchPick {
	
	private static UserSearchPick userSearchPick = new UserSearchPick();
	
	private String searchPick;
	
	public static UserSearchPick getInstance() {
		return userSearchPick;
	}
	
	
	//setter for search pick
	public void setSearchPick(String searchPick) {
		this.searchPick = searchPick;
	}
	
	//getter for search pick
	public String getSearchPick() {
		return this.searchPick;
	}
	
	

}
