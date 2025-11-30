package assignment003;
import java.util.Comparator;

public class AirportComperator implements Comparator<City> {

	@Override
	public int compare(City o1, City o2) {
		return Integer.compare(o1.noOfAirports,o2.noOfAirports);
	}

}
