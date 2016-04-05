package form;

public class ProfileForm {
	private String name;
	private int visitedGames;
	private int unvisitedGames;
	private int quantityOFGames;
	
	private int [] sportSkill=new int[5];
	//Ця штука просто має бути
	private ProfileForm () {}
	
	public ProfileForm(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public int getVisitedGames(){
		return visitedGames;
	}
	
	public int getUnvisitedGames(){
		return unvisitedGames;
	}
	
	public int getQuantityOfGames(){
		return quantityOFGames;
	}
	
	public int getLatitude(){
		return unvisitedGames;
	}
	
	public int getLongitude(){
		return quantityOFGames;
	}
	
	public int[] getSportSkill(){
		return sportSkill;
	}
	
}
