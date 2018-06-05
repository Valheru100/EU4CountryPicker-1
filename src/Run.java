import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class Run {
	
	private static ArrayList<ArrayList<String>> listOfGroups = new ArrayList<ArrayList<String>>();
	private static ArrayList<PlayerChoices> listOfPlayers = new ArrayList<PlayerChoices>();
	private static ArrayList<String> group1 =  new ArrayList<String>();
	private static ArrayList<String> group2 =  new ArrayList<String>();
	private static ArrayList<String> group3 =  new ArrayList<String>();
	private static ArrayList<String> group4 =  new ArrayList<String>();
	private static ArrayList<String> group5 =  new ArrayList<String>();
	
	//Hashmaps
	private static Map<String, Integer> choiceNumbers = new HashMap<String, Integer>();
	private static Map<String, Integer> sortedChoice = new HashMap<String, Integer>();
	
	public static void main(String[] args) throws IOException {
		init();
		//printPlayersAndChoices();
		printGroups(); 
		for(int i = 0; i < 5; i++) 
		{
			choosePlayers(findMostWantedGroup());
			//printPlayersAndChoices();
		}
		dealGroups();
	}	
	
	private static void dealGroups() 
	{
		System.out.println("------------------");
		for(PlayerChoices pl: listOfPlayers) 
		{
			int finalGroup = pl.getFinalGroup();
			Random rand = new Random();
			int num;
			switch(finalGroup)
			{
				case 1:
					num = rand.nextInt(group1.size());
					System.out.println(pl.getName() + " gets " + group1.get(num));
					group1.remove(num);
					break;
				case 2:
					num = rand.nextInt(group2.size());
					System.out.println(pl.getName() + " gets " + group2.get(num));
					group2.remove(num);
					break;
				case 3:
					num = rand.nextInt(group3.size());
					System.out.println(pl.getName() + " gets " + group3.get(num));
					group3.remove(num);
					break;
				case 4:
					num = rand.nextInt(group4.size());
					System.out.println(pl.getName() + " gets " + group4.get(num));
					group4.remove(num);
					break;
				case 5:
					num = rand.nextInt(group5.size());
					System.out.println(pl.getName() + " gets " + group5.get(num));
					group5.remove(num);
					break;
			}
		}
	}
	
	private static String findMostWantedGroup() 
	{
		int choice1 = 0;
		int choice2 = 0;
		int choice3 = 0;
		int choice4 = 0;
		int choice5 = 0;
		
		//For each player in the list of players
		for(PlayerChoices pl : listOfPlayers)
		{
			//check first if the player hasn't already been given a group
			if(pl.getFinalGroup() == 999) 
			{
				//iterate over each of their inputs
				for(Iterator<String> st = pl.getPlayerChoiceList().iterator(); st.hasNext(); ) 
				{
					String input = st.next();
					if(input.equals("X")) 
					{
						pl.setOffset(pl.getOffset() + 1);
					} 
					else if(pl.getMostWantedGroup() == 999) 
					{
						pl.setMostWantedGroup(Integer.parseInt(input));
						switch(input) 
						{
							case "1":
								choice1++;
								break;
							case "2":
								choice2++;
								break;
							case "3":
								choice3++;
								break;
							case "4":
								choice4++;
								break;
							case "5":
								choice5++;
								break;
						}
					}
				}
			}
		}
		String mostWantedGroup = makeHashMap(choice1, choice2, choice3, choice4, choice5);
		return mostWantedGroup;
	}
	
	private static void choosePlayers(String mostWantedGroup)
	{
		//for each player, if their most wanted group is the one we are currently doing
		//add them to the list to be randomly chosen from
		ArrayList<String> players =  new ArrayList<String>();
		for(PlayerChoices pl : listOfPlayers) 
		{
			if(pl.getMostWantedGroup() == Integer.parseInt(mostWantedGroup)) 
			{
				for(int i = 0; i < pl.getOffset(); i++) 
				{
					players.add(pl.getName());
				} 
			}
		}
		System.out.println(players);
		
		//Now we select up to 3 players now from the eligible players list
		Random rand = new Random();
		String random = "";
		
		//get the distinct number of people in the list, if more than
		//3 set back to 3 (as that's the max in a group)
		int listSize = (int) players.stream().distinct().count();
		if(listSize > 3) 
		{
			listSize = 3;
		}
		
		for(int i = 0; i < listSize; i++) 
		{
			random = players.get(rand.nextInt(listSize));
			System.out.println(i + ". Pick : " + random);
			
			//find this player inside of the playerChoices list and set his final choice to the value of "random"
			for(PlayerChoices player : listOfPlayers) 
			{
				if(player.getName().equals(random)) 
				{
					player.setFinalGroup(Integer.parseInt(mostWantedGroup));
				}
			}
			
			//remove all instances of the chosen 
			//player from the list, so they can't get picked again
			for (Iterator<String> it = players.iterator(); it.hasNext();) 
			{
				String st = it.next();
				if(st.equals(random)) 
				{
					it.remove();
				}
			}
		}
		
		//now we set all instances of the value to X with each players choices
		//and reset some other variables
		for(PlayerChoices pl : listOfPlayers) 
		{
			for(int i = 0; i < 5; i++)
			{
				String st = pl.getPlayerChoiceList().get(i);
				if(st.equals(mostWantedGroup)) 
				{
					pl.getPlayerChoiceList().set(i, "X");
				}
			}
			//reset player variables for the next round
			pl.setMostWantedGroup(999);
			pl.setOffset(1);
		}
		
		//Clear both hashsets for the next round
		choiceNumbers.clear();
		sortedChoice.clear();
		
	}
	
	private static String makeHashMap(int choice1, int choice2, int choice3, int choice4, int choice5)
	{
		choiceNumbers.put("1", choice1);
		choiceNumbers.put("2", choice2);
		choiceNumbers.put("3", choice3);
		choiceNumbers.put("4", choice4);
		choiceNumbers.put("5", choice5);
		sortedChoice = sortByComparator(choiceNumbers, false);
		System.out.println("--Sorted Hash Map--");
		printMap(sortedChoice);
		System.out.println("------------------");
		
		//Give us the most wanted group
		String mostWantedChoice = sortedChoice.keySet().stream().findFirst().get();
		System.out.println("Most Wanted Choice : " + mostWantedChoice);
		return mostWantedChoice;
	}
	
	private static void init() throws IOException 
	{
		listOfGroups.add(group1);
		listOfGroups.add(group2);
		listOfGroups.add(group3);
		listOfGroups.add(group4);
		listOfGroups.add(group5);
		readInGroups();
		readInPlayerChoices();
	}
	
	private static void readInGroups() throws IOException
	{
		File countryGroupsFile = new File("E:\\Eclipse\\workspace\\EU4CountryPicker\\groups.txt");
		@SuppressWarnings("resource")
		BufferedReader countryGrpBR = new BufferedReader(new FileReader(countryGroupsFile));
		
		String st;
		int grpNum = 0;
		while ((st = countryGrpBR.readLine()) != null) 
		{
			if(st.equals("---")) 
			{
				grpNum++;
			} 
			else 
			{
				listOfGroups.get(grpNum).add(st);	
			}
		} 
	}
	
	private static void printGroups() 
	{
		//Print out all groups
		int i = 0;
		for(ArrayList<String> groups : listOfGroups)
		{
			System.out.println("Group : " + i);
			for(String country : groups)
			{
				System.out.println("  " + country);
			}
			i++;
		}
		System.out.println("------------------");
	}
	
	private static void readInPlayerChoices() throws IOException
	{
		File playersFile = new File("E:\\Eclipse\\workspace\\EU4CountryPicker\\players.txt");
		@SuppressWarnings("resource")
		BufferedReader playerBR = new BufferedReader(new FileReader(playersFile));
		
		String st;
		String[] parts;
		
		while ((st = playerBR.readLine()) != null) 
		{
			parts = st.split(",");
			listOfPlayers.add(new PlayerChoices(parts[0],parts[1],parts[2],parts[3],parts[4],parts[5]));
		}
	}
	
	private static void printPlayersAndChoices() 
	{
		for(PlayerChoices pChose : listOfPlayers) 
		{
			System.out.println("Name : " + pChose.getName());
			System.out.println("Most Wanted Group : " + pChose.getMostWantedGroup());
			System.out.println("Final Group() : " + pChose.getFinalGroup());
			System.out.println("Offset : " + pChose.getOffset());
			System.out.println("1st Choice : " + pChose.getPlayerChoices().get(0));
			System.out.println("2nd Choice : " + pChose.getPlayerChoices().get(1));
			System.out.println("3rd Choice : " + pChose.getPlayerChoices().get(2));
			System.out.println("4th Choice : " + pChose.getPlayerChoices().get(3));
			System.out.println("5th Choice : " + pChose.getPlayerChoices().get(4));
			System.out.println("----------------");
		}
		System.out.println("----------------------------------------------------------------");
	}

	private static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap, final boolean order)
    {

        List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Entry<String, Integer>>()
        {
            public int compare(Entry<String, Integer> o1,
                    Entry<String, Integer> o2)
            {
                if (order)
                {
                    return o1.getValue().compareTo(o2.getValue());
                }
                else
                {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Entry<String, Integer> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
	
    public static void printMap(Map<String, Integer> map)
    {
        for (Entry<String, Integer> entry : map.entrySet())
        {
            System.out.println("Key : " + entry.getKey() + " Value : "+ entry.getValue());
        }
    }

}


