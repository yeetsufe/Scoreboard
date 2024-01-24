/* Name: Jim Wu
 * Date: 10/23/2021
 * Description: This class sorts an ArrayList using a Comparator interface based on the alphabetical order of the Players' name.
 */
import java.util.*;
public class SortByName implements Comparator <Player>{
	/* Description: This method compares the name of two players alphabetically for the purpose of performing a sort or binary search.
	 * Parameters:
	 * Player a, Player b - Player objects whose names are being compared alphabetically
	 * Return type:
	 * int - returns a negative number if Player a's name comes before Player b's name alphabetically, a positive number if Player b comes before
	 * Player a, and 0 if both names are equivalent.
	 */
	public int compare(Player a, Player b) {
		return a.getName().toLowerCase().compareTo(b.getName().toLowerCase()); //The comparison between each string must be case insensitive.
	}
}