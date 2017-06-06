package thiagodnf.jmetal.analysis.utils;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;

import thiagodnf.jmetal.analysis.beans.Solution;
import thiagodnf.jmetal.analysis.beans.SolutionSet;
import thiagodnf.jmetal.analysis.dumbs.DumbSolution;

public class SolutionSetUtils {

	public static int getNumberOfObjectives(SolutionSet solutionSet) {

		checkNotNull(solutionSet, "The solutionSet object cannot be null");
		checkArgument(solutionSet.getSolutions().size() != 0, "The solutionSet has not solutions");

		int numberOfObjectives = -1;

		for (Solution solution : solutionSet.getSolutions()) {

			if (numberOfObjectives == -1) {
				numberOfObjectives = solution.getObjectives().size();
			}

			// All solutions in the population should have the same number of objectives
			if (numberOfObjectives != solution.getObjectives().size()) {
				throw new IllegalArgumentException("The number of objectives should not be different");
			}
		}

		return numberOfObjectives;
	}
	
	public static int getNumberOfVariables(SolutionSet solutionSet) {

		checkNotNull(solutionSet, "The solutionSet object cannot be null");
		checkArgument(solutionSet.getSolutions().size() != 0, "The solutionSet has not solutions");

		int numberOfVariables = -1;

		for (Solution solution : solutionSet.getSolutions()) {

			if (numberOfVariables == -1) {
				numberOfVariables = solution.getVariables().size();
			}

			// All solutions in the population should have the same number of variables
			if (numberOfVariables != solution.getVariables().size()) {
				throw new IllegalArgumentException("The number of variables should not be different");
			}
		}

		return numberOfVariables;
	}
	
	public static List<DumbSolution> removeRepeatedSolutions(List<DumbSolution> population) {
		
		List<DumbSolution> newPopulation = new ArrayList<>();

		for (DumbSolution s : population) {

			if (!contains(newPopulation, s)) {
				newPopulation.add(s.copy());
			}
		}

		return newPopulation;
	}

	public static boolean contains(List<DumbSolution> population, DumbSolution s1) {

		checkNotNull(population, "The population object cannot be null");
		checkNotNull(s1, "The solution s1 object cannot be null");

		for (DumbSolution s2 : population) {

			if (isEqual(s1, s2)) {
				return true;
			}
		}

		return false;
	}

	public static boolean isEqual(DumbSolution s1, DumbSolution s2) {

		checkNotNull(s1, "The solution s1 object cannot be null");
		checkNotNull(s2, "The solution s2 object cannot be null");
		checkArgument(s1.getNumberOfObjectives() == s2.getNumberOfObjectives(),	"the number of objectives should be equal");

		for (int i = 0; i < s1.getNumberOfObjectives(); i++) {

			if (s1.getObjective(i) != s2.getObjective(i)) {
				return false;
			}
		}

		return true;
	}
}
