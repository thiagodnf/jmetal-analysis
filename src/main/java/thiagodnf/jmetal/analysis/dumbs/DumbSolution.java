package thiagodnf.jmetal.analysis.dumbs;

import java.util.HashMap;

import org.uma.jmetal.solution.impl.AbstractGenericSolution;

public class DumbSolution extends AbstractGenericSolution<String, DumbProblem> {

	private static final long serialVersionUID = 6406921194725070152L;

	public DumbSolution(DumbProblem problem) {
		super(problem);
	}

	/** 
	 * Copy constructor 
	 */
	public DumbSolution(DumbSolution solution) {
		super(solution.problem);

		for (int i = 0; i < problem.getNumberOfVariables(); i++) {
			setVariableValue(i, solution.getVariableValue(i));
		}

		for (int i = 0; i < problem.getNumberOfObjectives(); i++) {
			setObjective(i, solution.getObjective(i));
		}

		super.attributes = new HashMap<Object, Object>(solution.attributes);
	}

	@Override
	public String getVariableValueString(int index) {
		return getVariableValue(index);
	}

	@Override
	public DumbSolution copy() {
		return new DumbSolution(this);
	}
}
