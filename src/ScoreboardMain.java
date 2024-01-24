/* Name: Jim Wu
 * Date: 10/23/2021
 * Description: A program that grabs a file containing the leaderboard of a game, then outputs all players that share a given power in
 * alphabetical order or outputs the player containing a given name depending on the user's choice.
 */
import java.util.*;
import java.io.*;
public class ScoreboardMain{
	public static void main(String[] args) {
		ArrayList <Player> leaderboard = new ArrayList<>();
		Scanner scanner = new Scanner(System.in);
		String input = "";
		Scanner file;
		try {
			file = new Scanner(new File("input.txt"));
			loadFile(leaderboard,file); //loads each player in the file into the ArrayList
		}
		catch(FileNotFoundException e) {
			System.out.println("File Not Found");
		}
		catch(IOException e) {
			System.out.println("IO Exception Occurred.");
		}
		Collections.sort(leaderboard); //Sort ArrayList by score in order to assign ranks
		if(leaderboard.size() != 0) { //If the leaderboard is empty or the file does not exist, ranks are not assigned.
			leaderboard.get(0).assignRank(1);
			for(int i=1;i<leaderboard.size();i++) {
				if (leaderboard.get(i).getScore() == leaderboard.get(i-1).getScore()) { 
					leaderboard.get(i).assignRank(leaderboard.get(i-1).getRank()); //If the score for two adjacent players are the same, they receive the same rank
				}
				else {
					leaderboard.get(i).assignRank(i+1);
				}
			}
		}
		
		while (leaderboard.size() != 0){ //User input is initiated on the condition that the ArrayList is not empty nor does the file not exist.
			System.out.print("Enter 1 to find players by name, enter 2 to list players by power: ");
			input = scanner.nextLine().trim();
			if(input.equals("1")) {
				do {
					System.out.print("Please enter a player's name or enter <exit> to exit to the main menu: ");
					input = scanner.nextLine().trim();
					System.out.println();
					if(!input.equalsIgnoreCase("exit")) {
						chooseSort(indexPlayers(leaderboard,input,true), leaderboard, input, true, scanner); //find and print all players that share a name
					}
				}while(!input.equalsIgnoreCase("exit")); //Return to main menu if input is "exit"
			}
			else if(input.equals("2")) {
				do {
					System.out.print("Please enter a power or enter <exit> to exit to the main menu: ");
					input = scanner.nextLine().trim();
					System.out.println();
					if(!input.equalsIgnoreCase("exit")) {
						chooseSort(indexPlayers(leaderboard,input,false), leaderboard, input, false, scanner); //find and print all players that share a power
					}
				}while(!input.equalsIgnoreCase("exit")); //Return to main menu if input is "exit"
			}
			else {
				System.out.println("Please enter 1 or 2 to search for players.\n");
			}
		}
	}
	
	/* Description: This method performs a binary search of either the shared names or powers of players given the user's input, then returns an
	 * ArrayList containing all players that share a power or name.
	 * Parameters:
	 * boolean sortMethod - indicates how players are searched; true if sorting by name, false if sorting by power
	 * ArrayList <Player> leaderboard - The ArrayList that each player is being searched from
	 * String input - The name or power that is being searched
	 * Return type:
	 * ArrayList <Player> - returns an ArrayList with all Players that share a name or power given the sortMethod variable
	 */
	public static ArrayList <Player> indexPlayers(ArrayList <Player> leaderboard, String input, boolean sortMethod) {
		ArrayList <Player> storePlayer = new ArrayList<>(); //A new ArrayList must be made to store Players with the same items as Players in the leaderboard ArrayList are removed.
		Player power = new Player(input,sortMethod);
		if(sortMethod) {
			Collections.sort(leaderboard,new SortByName()); //Sort players by alphabetical order of their name
		}
		else {
			Collections.sort(leaderboard,new SortByPower()); //Sort players by alphabetical order of their powers
		}
		int index;
		do {
			if(sortMethod) {
				index = Collections.binarySearch(leaderboard, power, new SortByName());
			}
			else {
				index = Collections.binarySearch(leaderboard, power, new SortByPower());
			}
			if(index >= 0) {
				//If a player is found with the given power, all of the Player's information must be inputted into storePlayer 
				//ArrayList as it will be added back into the leaderboard ArrayList later. 
				storePlayer.add(new Player(leaderboard.get(index).getScore(),leaderboard.get(index).getName(),leaderboard.get(index).getPower(),leaderboard.get(index).getRank()));
				leaderboard.remove(index); //Remove the player such that it is not searched for more than once.
			}
		}while(index >= 0); //As long as a Player with the given power exists, remove the player and binary search for the respective power.
		return storePlayer;
	}
	
	/* Description: This method allows the user to choose between outputting all Players that share a name or power by their power, name, or rank.
	 * If there is only one player found, then the method will automatically display that player. If there are no players found, then the method
	 * will state that there are no players that contain the given power or name.
	 * Parameters:
	 * ArrayList <Player> list - The list of players that share a name or a power
	 * ArrayList <Player> leaderboard - The ArrayList containing all of the players in the file.
	 * String input - The indicated power or name being searched
	 * boolean sortMethod - indicates what item each Player in the list ArrayList shares; true is when the Players share a name, false is when the Players share a power.
	 * Scanner scanner - Scanner for user input in the console for choosing between displaying in the order of power, name, or rank.
	 * Return type:
	 * void - This method is only responsible for printing out each player in an ordered manner.
	 */
	public static void chooseSort(ArrayList <Player> list, ArrayList <Player> leaderboard, String input, boolean sortMethod, Scanner scanner) {
		if(list.size() == 0) { //If the ArrayList contains 0 objects, then no players have been found.
			if(sortMethod) {
				System.out.println("Player "+input+" not found.\n");
			}
			else {
				System.out.println("Power "+input+" not found.\n");
			}
		}
		else if(list.size() == 1) { //If there is only one player in the ArrayList, then the player is printed without any user prompt.
			System.out.println(list.get(0));
			System.out.println();
			leaderboard.add(list.get(0)); //Add back the player to the leaderboard ArrayList as it has been removed in the indexPlayers method.
		}
		else {
			do{
				if(sortMethod) { 
					System.out.print("Enter 1 to sort by power, enter 2 to sort by rank: "); //Ask for power/rank when sharing name.
				}
				else {
					System.out.print("Enter 1 to sort by name, enter 2 to sort by rank: "); //Ask for name/rank when sharing power.
				}
				input = scanner.nextLine().trim();
				if(!(input.equals("1")||input.equals("2")))
					System.out.println("Please enter a valid input.");
			}while(!(input.equals("1")||input.equals("2")));
			if(input.equals("1")) {
				if(sortMethod) {
					Collections.sort(list,new SortByPower()); //Print players by alphabetical order of their power.
				}
				else {
					Collections.sort(list,new SortByName()); //Print players by alphabetical order of their name.
				}
			}
			else {
				Collections.sort(list); //Print players by alphabetical order of their rank.
			}
			System.out.println();
			for(int i=0;i<list.size();i++) {
				System.out.println(list.get(i)+"\n"); //Print out each Player in their respective order.
				leaderboard.add(list.get(i)); //Add back the players into the leaderboard ArrayList since they have been removed in the indexPlayers method.
			}
		}
	}
	
	/* Description: This method loads each player from the input file into a Player object with the respective power, name, and score, and inputs
	 * the resulting Player object into an ArrayList.
	 * Parameters:
	 * ArrayList leaderboard - The ArrayList that the Player object is being input into.
	 * Scanner file - The scanner responsible for reading the player's stats for each line in the input file
	 * Return Type: 
	 * void - Since the ArrayList is passed-by-referenced, any changes in the ArrayList is reflected in the actual variable referenced.
	 */
	public static void loadFile(ArrayList <Player> leaderboard, Scanner file) {
		while(file.hasNextLine()) { 
			String player = file.nextLine();
			String name = "";
			StringTokenizer items = new StringTokenizer(player);
			int tokens = items.countTokens();
			if(tokens >= 3) { //if there are less than 3 tokens, some parameters must be missing and thus the line must be skipped.
				try{
					int score = Integer.parseInt(items.nextToken()); //Since the score is a single integer, the first token is assumed.
					if (score>=0) { //if the score is a negative integer, then it is an invalid input.
						for(int i=1;i<tokens-1;i++) { //loops through n-2 of the total tokens since the first and last are reserved for the score and power
							name += " "+items.nextToken();
						}
						if(!name.trim().toLowerCase().equals("exit")) { //"exit" is not a valid name
							leaderboard.add(new Player(score,name.trim(),items.nextToken().trim()));
						}
					}
				}
				catch(NumberFormatException e) { //the first token may not be an integer and thus the line may be skipped
				}
			}
		}
		file.close();
	}
}