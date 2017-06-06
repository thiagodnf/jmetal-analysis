package thiagodnf.jmetal.analysis.controllers;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import thiagodnf.jmetal.analysis.beans.SolutionSet;
import thiagodnf.jmetal.analysis.repositories.ProjectRepository;
import thiagodnf.jmetal.analysis.repositories.SolutionSetRepository;
import thiagodnf.jmetal.analysis.response.Response;
import thiagodnf.jmetal.analysis.tasks.GenerateParetoFrontSet;
import thiagodnf.jmetal.analysis.tasks.GenerateQualityIndicators;
import thiagodnf.jmetal.analysis.utils.DateUtils;

@RestController
public class TaskController {

	private final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);

	@Autowired
	private SolutionSetRepository solutionSetrepository;
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@PostMapping("/task/generate-pareto-front-set")	
	public @ResponseBody ResponseEntity<?> generateParetoFrontSet(@RequestBody String projectId) {

		checkNotNull(projectId, "The projectId cannot be null");
		checkArgument(!projectId.isEmpty(), "The projectId cannot be empty");
		
		LOGGER.info("Verifing if the projectId " + projectId + " exists");

		if (projectRepository.findOne(projectId) == null) {
			throw new IllegalArgumentException("The project was not found");
		}
		
		LOGGER.info("The projectId was found. Verifying if the Pareto-front set was already generated");

		if (solutionSetrepository.findByIsParetoFront(true) != null) {
			throw new IllegalArgumentException("The Pareto-front set was already generated");
		}

		LOGGER.info("The Pareto-front set was generated. Finding its solution sets");

		List<SolutionSet> listOfSolutionSets = solutionSetrepository.findByProjectId(projectId);
		
		if (listOfSolutionSets.size() == 0) {
			throw new IllegalArgumentException("The projectId does not have any solution sets");
		}

		LOGGER.info("Found " + listOfSolutionSets.size() + " solution sets. Generating the Pareto-front set");

		SolutionSet paretoFront = GenerateParetoFrontSet.generate(listOfSolutionSets);
		
		LOGGER.info("Saving the general information");
		
		paretoFront.setParetoFront(true);
		paretoFront.setProjectId(projectId);
		paretoFront.setName("Pareto-front Set");
		paretoFront.setCreationTime(DateUtils.getDateNow());
		
		LOGGER.info("The Pareto-front set was created. Saving it");

		solutionSetrepository.save(paretoFront);

		LOGGER.info("Saved");
		
		return Response.success("The Pareto-front set was successfully generated");
	}
	
	@PostMapping("/task/generate-quality-indicators")	
	public @ResponseBody ResponseEntity<?> generateQualityIndicators(@RequestBody String projectId) {

		checkNotNull(projectId, "The projectId cannot be null");
		checkArgument(!projectId.isEmpty(), "The projectId cannot be empty");
		
		LOGGER.info("Verifing if the project " + projectId+" exists");
		
		if (projectRepository.findOne(projectId) == null) {
			throw new IllegalArgumentException("The project was not found");
		}

		LOGGER.info("The projectId was found. Finding the Pareto-front of this project");
		
		SolutionSet paretoFront = solutionSetrepository.findByIsParetoFront(true);
		
		if (paretoFront == null) {
			throw new IllegalArgumentException("The Pareto-front of this project was not generated. Please generate it");
		}
		
		LOGGER.info("The Pareto-front was found. Finding the solution sets of this project");

		List<SolutionSet> listOfSolutionSets = solutionSetrepository.findByProjectId(projectId);
		
		if (listOfSolutionSets.size() == 0) {
			throw new IllegalArgumentException("The projectId does not have any solution sets");
		}

		LOGGER.info("Found " + listOfSolutionSets.size() + " solution sets. Calculating the quality indicators");
	
		for (SolutionSet solutionSet : listOfSolutionSets) {
			
			SolutionSet ss = GenerateQualityIndicators.generate(paretoFront, solutionSet);

			solutionSetrepository.save(ss);
		}
		
		return Response.success("The quality indicators were generated");
	}
}
