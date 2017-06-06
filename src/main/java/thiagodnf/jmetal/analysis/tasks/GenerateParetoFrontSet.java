package thiagodnf.jmetal.analysis.tasks;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uma.jmetal.util.SolutionListUtils;

import thiagodnf.jmetal.analysis.beans.SolutionSet;
import thiagodnf.jmetal.analysis.dumbs.DumbSolution;
import thiagodnf.jmetal.analysis.utils.ConvertUtils;
import thiagodnf.jmetal.analysis.utils.SolutionSetUtils;

public class GenerateParetoFrontSet {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GenerateParetoFrontSet.class);
	
	public static SolutionSet generate(List<SolutionSet> listOfSolutionSets){
		
		checkNotNull(listOfSolutionSets, "The listOfSolutionSets object cannot be null");
		checkArgument(listOfSolutionSets.size() != 0, "The listOfSolutionSets has not solutions");
		
		LOGGER.info("Converting for a list of dumb solutions");
		
		List<DumbSolution> listOfSolutions = new ArrayList<>();

		for (SolutionSet solutionSet : listOfSolutionSets) {
			listOfSolutions.addAll(ConvertUtils.toListOfSolutions(solutionSet));
		}
		
		LOGGER.info("Removing the repeated solutions");
		
		List<DumbSolution> unrepeatedSolutions = SolutionSetUtils.removeRepeatedSolutions(listOfSolutions);
		
		LOGGER.info("Generating the pareto-front set");
		
		List<DumbSolution> paretoFront = SolutionListUtils.getNondominatedSolutions(unrepeatedSolutions);		

		LOGGER.info("Converting for a list of dumb solutions for a solution set instance");

		return ConvertUtils.toSolutionSet(paretoFront);
	}

	
}
