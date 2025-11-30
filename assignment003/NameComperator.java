package assignment003;

import java.util.Comparator;

public class NameComperator implements Comparator<City>{

	@Override
	public int compare(City o1,City o2) {
		return o1.name.compareTo(o2.name);
	}

}
