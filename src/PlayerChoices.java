import java.util.ArrayList;

public class PlayerChoices {
	private ArrayList<String> playerChoiceList = new ArrayList<String>();
	private String name;
	private int mostWantedGroup;
	private int finalGroup;
	private int offset;
	
	public PlayerChoices(String name, String choice1, String choice2, String choice3, String choice4, String choice5)
	{
		this.name = name;
		this.mostWantedGroup = 999;
		this.finalGroup = 999;
		this.offset = 1;	
		playerChoiceList.add(choice1);
		playerChoiceList.add(choice2);
		playerChoiceList.add(choice3);
		playerChoiceList.add(choice4);
		playerChoiceList.add(choice5);
	}

	public ArrayList<String> getPlayerChoices() {
		return playerChoiceList;
	}

	public void setPlayerChoices(ArrayList<String> playerChoiceList) {
		this.playerChoiceList = playerChoiceList;
	}

	public ArrayList<String> getPlayerChoiceList() {
		return playerChoiceList;
	}

	public void setPlayerChoiceList(ArrayList<String> playerChoiceList) {
		this.playerChoiceList = playerChoiceList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMostWantedGroup() {
		return mostWantedGroup;
	}

	public void setMostWantedGroup(int mostWantedGroup) {
		this.mostWantedGroup = mostWantedGroup;
	}

	public int getFinalGroup() {
		return finalGroup;
	}

	public void setFinalGroup(int finalGroup) {
		this.finalGroup = finalGroup;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
}
