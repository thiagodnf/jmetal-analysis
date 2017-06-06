package thiagodnf.jmetal.analysis.tasks;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uma.jmetal.qualityindicator.impl.Epsilon;
import org.uma.jmetal.qualityindicator.impl.ErrorRatio;
import org.uma.jmetal.qualityindicator.impl.GenerationalDistance;
import org.uma.jmetal.qualityindicator.impl.InvertedGenerationalDistance;
import org.uma.jmetal.qualityindicator.impl.InvertedGenerationalDistancePlus;
import org.uma.jmetal.qualityindicator.impl.Spread;
import org.uma.jmetal.qualityindicator.impl.hypervolume.PISAHypervolume;
import org.uma.jmetal.util.front.Front;
import org.uma.jmetal.util.front.imp.ArrayFront;
import org.uma.jmetal.util.front.util.FrontNormalizer;
import org.uma.jmetal.util.front.util.FrontUtils;
import org.uma.jmetal.util.point.util.PointSolution;

import thiagodnf.jmetal.analysis.beans.SolutionSet;
import thiagodnf.jmetal.analysis.dumbs.DumbSolution;
import thiagodnf.jmetal.analysis.utils.ConvertUtils;

public class GenerateQualityIndicators {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GenerateQualityIndicators.class);
	
	public static SolutionSet generate(SolutionSet pfInSolutionSet, SolutionSet solutionSet){
		
		LOGGER.info("Converting to a list of dumb solutions");

		List<DumbSolution> paretoFront = ConvertUtils.toListOfSolutions(pfInSolutionSet);
		List<DumbSolution> population = ConvertUtils.toListOfSolutions(solutionSet);

		LOGGER.info("Normalizing the front");
		
		Front referenceFront = new ArrayFront(paretoFront);
	    FrontNormalizer frontNormalizer = new FrontNormalizer(referenceFront) ;

	    Front normalizedReferenceFront = frontNormalizer.normalize(referenceFront) ;
	    Front normalizedFront = frontNormalizer.normalize(new ArrayFront(population)) ;
	    List<PointSolution> normalizedPopulation = FrontUtils.convertFrontToSolutionList(normalizedFront) ;

	    LOGGER.info("Calculating the quality indicators for normalized values");
	    
	    solutionSet.getIndicators().put("n_hypervolume", new PISAHypervolume<PointSolution>(normalizedReferenceFront).evaluate(normalizedPopulation));
	    solutionSet.getIndicators().put("n_epsilon",new Epsilon<PointSolution>(normalizedReferenceFront).evaluate(normalizedPopulation));
	    solutionSet.getIndicators().put("n_gd", new GenerationalDistance<PointSolution>(normalizedReferenceFront).evaluate(normalizedPopulation));
	    solutionSet.getIndicators().put("n_igd", new InvertedGenerationalDistance<PointSolution>(normalizedReferenceFront).evaluate(normalizedPopulation));
	    solutionSet.getIndicators().put("n_igd_plus", new InvertedGenerationalDistancePlus<PointSolution>(normalizedReferenceFront).evaluate(normalizedPopulation));
	    solutionSet.getIndicators().put("n_spread", new Spread<PointSolution>(normalizedReferenceFront).evaluate(normalizedPopulation));
	    
	    LOGGER.info("Calculating the quality indicators for default values");
	    
	    solutionSet.getIndicators().put("hypervolume", new PISAHypervolume<DumbSolution>(referenceFront).evaluate(population));
	    solutionSet.getIndicators().put("epsilon", new Epsilon<DumbSolution>(referenceFront).evaluate(population));
	    solutionSet.getIndicators().put("gd", new GenerationalDistance<DumbSolution>(referenceFront).evaluate(population));
	    solutionSet.getIndicators().put("igd", new InvertedGenerationalDistance<DumbSolution>(referenceFront).evaluate(population));
	    solutionSet.getIndicators().put("igd_plus", new InvertedGenerationalDistancePlus<DumbSolution>(referenceFront).evaluate(population));
	    solutionSet.getIndicators().put("spread", new Spread<DumbSolution>(referenceFront).evaluate(population));
	    solutionSet.getIndicators().put("error_ratio", new ErrorRatio<List<DumbSolution>>(referenceFront).evaluate(population));

	    return solutionSet;
	}
}
