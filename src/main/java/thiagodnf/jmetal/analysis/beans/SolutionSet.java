package thiagodnf.jmetal.analysis.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SolutionSet {

	@Id
	protected String id;

	protected String name;

	protected String projectId;
	
	protected String creationTime;

	protected Map<String, Double> indicators;

	protected List<Solution> solutions;
	
	protected boolean isParetoFront;

	public SolutionSet() {
		this.solutions = new ArrayList<>();
		this.indicators = new HashMap<>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Solution> getSolutions() {
		return solutions;
	}

	public void setSolutions(List<Solution> solutions) {
		this.solutions = solutions;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public Map<String, Double> getIndicators() {
		return indicators;
	}

	public void setIndicators(Map<String, Double> indicators) {
		this.indicators = indicators;
	}

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

	public boolean isParetoFront() {
		return isParetoFront;
	}

	public void setParetoFront(boolean isParetoFront) {
		this.isParetoFront = isParetoFront;
	}

	@Override
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			return "";
		}
	}
}
