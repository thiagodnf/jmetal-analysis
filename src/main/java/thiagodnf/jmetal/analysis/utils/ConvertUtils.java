package thiagodnf.jmetal.analysis.utils;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.solution.Solution;

import thiagodnf.jmetal.analysis.beans.SolutionSet;
import thiagodnf.jmetal.analysis.dumbs.DumbProblem;
import thiagodnf.jmetal.analysis.dumbs.DumbSolution;

public class ConvertUtils {

	public static SolutionSet toSolutionSet(List<? extends Solution<?>> population) {

		SolutionSet solutionSet = new SolutionSet();

		for (Solution<?> solution : population) {

			List<Double> objectives = new ArrayList<>();
			List<String> variables = new ArrayList<>();

			for (int i = 0; i < solution.getNumberOfObjectives(); i++) {
				objectives.add(solution.getObjective(i));
			}

			for (int i = 0; i < solution.getNumberOfVariables(); i++) {
				variables.add(solution.getVariableValueString(i));
			}

			solutionSet.getSolutions().add(new thiagodnf.jmetal.analysis.beans.Solution(objectives, variables));
		}

		return solutionSet;
	}
	
	public static List<DumbSolution> toListOfSolutions(SolutionSet solutionSet) {
		
		checkNotNull(solutionSet, "The solutionSet object cannot be null");
		
		List<DumbSolution> listOfSolutions = new ArrayList<>();

		int numberOfObjectives = SolutionSetUtils.getNumberOfObjectives(solutionSet);
		int numberOfVariables = SolutionSetUtils.getNumberOfVariables(solutionSet);
		
		DumbProblem problem = new DumbProblem(numberOfObjectives, numberOfVariables);

		for (thiagodnf.jmetal.analysis.beans.Solution solution : solutionSet.getSolutions()) {

			DumbSolution dumbSolution = new DumbSolution(problem);

			for (int i = 0; i < solution.getObjectives().size(); i++) {
				dumbSolution.setObjective(i, solution.getObjectives().get(i));
			}

			for (int i = 0; i < solution.getVariables().size(); i++) {
				dumbSolution.setVariableValue(i, solution.getVariables().get(i));
			}

			listOfSolutions.add(dumbSolution);
		}

		return listOfSolutions;
	}
	
	/**
	 * Convert a list of solutions formatted in string to a list of {@link DumbSolution}. To use
	 * this you need to format each solution like (1,2) and separate it by ";". Example:
	 * <pre>
	 * {@code DumbProblem problem = new DumbProblem(2, 1);}
	 * {@code List<DumbSolution> list = ConvertUtils.toListOfSolutions(problem,"(1,2);(4,5)"); }
	 * <pre>
	 * 
	 * @param problem The problem used to generate a {@link DumbSolution} 
	 * @param string formatted-string solutions 
	 * @return a list of {@link DumbSolution}
	 */
	public static List<DumbSolution> toListOfSolutions(DumbProblem problem, String string) {

		checkNotNull(problem, "The problem cannot be null");
		checkNotNull(string, "The formatted-string solution cannot be null");
		
		List<DumbSolution> listOfDumbSolutions = new ArrayList<>();

		String[] split = string.split(";");

		for (String s : split) {

			DumbSolution solution = new DumbSolution(problem);

			String[] objectives = s.replace("(", "").replace(")", "").split(",");

			for (int i = 0; i < objectives.length; i++) {
				solution.setObjective(i, Double.valueOf(objectives[i]));
			}

			solution.setVariableValue(0, "00000");

			listOfDumbSolutions.add(solution);
		}

		return listOfDumbSolutions;
	}

}
