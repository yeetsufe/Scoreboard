/* Name: Jim Wu
 * Date: 10/23/2021
 * Description: This object stores the rank, score, name, and power of each Player into a single instance. This object also counts the total number
 * of players created and contains a .compareTo() method based on each Player's rank for a Comparator interface.
 */
public class Player implements Comparable <Player>{
	//Static variables:
	private static int num; //Total number of player objects created
	//Instance variables:
	private int score;
	private int rank;
	private String name;
	private String power;
	//Setters:
	public Player(int score, String name, String power) {
		this.score = score;
		this.name = name;
		this.power = power;
		num++;
	}
	public Player(int score, String name, String power, int rank) {
		this.score = score;
		this.name = name;
		this.power = power;
		this.rank = rank;
	}
	public Player(String s, boolean b) {
		if(b) {
			this.name = s;
		}
		else {
			this.power = s;
		}
	}
	public void assignRank(int rank) {
		this.rank = rank;
	}
	//Getters:
	public int getNum() {
		return num;
	}
	public String toString() {
		return String.format("Name: %s%nPower: %s%nScore: %d%nRanking: %d out of %d",name,power,score,rank,num);
	}
	public int getScore() {
		return this.score;
	}
	public String getName() {
		return this.name;
	}
	public String getPower() {
		return this.power;
	}
	public int getRank() {
		return this.rank;
	}
	public boolean equals(Player player) {
		return this.power.equals(player.power) && this.score == player.score;
	}
	/* Description: This method compares two players based on their rankings on the leaderboard.
	 * Parameters:
	 * Player player - The Player object in which the current instance's ranking is compared to.
	 * Return Type:
	 * int - The method returns 0 if the rankings are exactly equal, a negative number if the current instance's ranking is below the given player's
	 * ranking, and a positive number if the current instance's ranking is above the given player's ranking.
	 */
	public int compareTo(Player player) {
		if(player.score - this.score == 0) { //if the score is the same, then the players are sorted alphabetically
			return this.name.compareTo(player.name);
		}
		else {
			return player.score - this.score;
		}
	}
}
