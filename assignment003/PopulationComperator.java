package assignment003;
import java.util.Comparator;


class PopulationComperator implements Comparator<City>{

	@Override
	public int compare(City o1,City o2) {
		return Long.compare(o1.population, o2.population);
	}

	
}
