package thiagodnf.jmetal.analysis.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import thiagodnf.jmetal.analysis.beans.SolutionSet;

public interface SolutionSetRepository extends MongoRepository<SolutionSet, String> {

	public List<SolutionSet> findByProjectId(String projectId);
	
	public SolutionSet findByName(String name);
	
	public SolutionSet findById(String id);
	
	public SolutionSet findByIsParetoFront(boolean isParetoFront);
}
