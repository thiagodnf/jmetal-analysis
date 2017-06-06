package thiagodnf.jmetal.analysis.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import thiagodnf.jmetal.analysis.beans.Project;

public interface ProjectRepository extends MongoRepository<Project, String> {

//	public Population findByFirstName(String firstName);
//
//	public List<Population> findByLastName(String lastName);
}
