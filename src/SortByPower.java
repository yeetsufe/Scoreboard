/* Name: Jim Wu
 * Date: 10/23/2021
 * Description: This class sorts an ArrayList using a Comparator interface based on the alphabetical order of the Players' power.
 */
import java.util.*;
public class SortByPower implements Comparator <Player>{
	/* Description: This method compares the power of two players alphabetically for the purpose of performing a sort or binary search.
	 * Parameters:
	 * Player a, Player b - Player objects whose powers are being compared alphabetically
	 * Return type:
	 * int - returns a negative number if Player a's power comes before Player b's power alphabetically, a positive number if Player b comes before
	 * Player a, and 0 if both powers are equivalent.
	 */
	public int compare(Player a, Player b) {
		return a.getPower().toLowerCase().compareTo(b.getPower().toLowerCase()); //The comparison between each string must be case insensitive.
	}
}