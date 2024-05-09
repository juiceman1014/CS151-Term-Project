package application.controller;

public class UserSearchPick {
	
	private static UserSearchPick userSearchPick = new UserSearchPick();
	
	private String searchPick;
	private String source;
	
	public static UserSearchPick getInstance() {
		return userSearchPick;
	}
	
	
	//setter for search pick
	public void setSearchPick(String searchPick) {
		this.searchPick = searchPick;
	}
	
	public void setSource(String source)
	{
		this.source = source;
	}
	
	//getter for search pick
	public String getSearchPick() {
		return this.searchPick;
	}
	
	public String getSource()
	{
		return this.source;
	}
	
	

}
