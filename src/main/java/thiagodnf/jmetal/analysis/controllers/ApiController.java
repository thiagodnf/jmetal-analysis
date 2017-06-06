package thiagodnf.jmetal.analysis.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import thiagodnf.jmetal.analysis.beans.SolutionSet;
import thiagodnf.jmetal.analysis.repositories.SolutionSetRepository;

@RestController
public class ApiController {

	private final Logger LOGGER = LoggerFactory.getLogger(ApiController.class);

	@Autowired
	private SolutionSetRepository repository;

	@PostMapping(value = "/api/solution-set/save")
	public void saveSolutionSet(@RequestBody SolutionSet solutionSet) {

		LOGGER.info("Saving the solutionSet instance");

		repository.save(solutionSet);

		LOGGER.info("Saved");
	}

	@GetMapping(value = "/api/solution-set/search/{projectId}")
	public List<SolutionSet> getSolutionSet(@PathVariable(value = "projectId") String projectId) {

		LOGGER.info("Retriveing the solution sets with projectId=" + projectId);

		List<SolutionSet> solutionSets = repository.findByProjectId(projectId);

		LOGGER.info("Sending the solution sets to the user");

		return solutionSets;
	}
}
