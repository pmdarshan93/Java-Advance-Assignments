package assignment003;

import java.util.Comparator;

public class AreaComperator implements Comparator<City>{

	public int compare(City o1,City o2) {
		return Double.compare(o1.area.calculateArea(),o2.area.calculateArea());
	}

}
