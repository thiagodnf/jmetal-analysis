package thiagodnf.jmetal.analysis.beans;

import java.util.List;

public class Solution {
	
	protected List<Double> objectives;
	
	protected List<String> variables;
	
	public Solution(){
		
	}
	
	public Solution(List<Double> objectives, List<String> variables) {
		this.objectives = objectives;
		this.variables = variables;
	}

	public List<Double> getObjectives() {
		return objectives;
	}

	public void setObjectives(List<Double> objectives) {
		this.objectives = objectives;
	}

	public List<String> getVariables() {
		return variables;
	}

	public void setVariables(List<String> variables) {
		this.variables = variables;
	}
}
