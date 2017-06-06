package thiagodnf.jmetal.analysis.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import thiagodnf.jmetal.analysis.beans.Project;
import thiagodnf.jmetal.analysis.beans.SolutionSet;
import thiagodnf.jmetal.analysis.repositories.ProjectRepository;
import thiagodnf.jmetal.analysis.repositories.SolutionSetRepository;
import thiagodnf.jmetal.analysis.response.Response;

@Controller
public class ProjectController extends WebMvcConfigurerAdapter {

	private final Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);

	@Autowired
	private SolutionSetRepository solutionSetRepository;
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@GetMapping("/project/{projectId}")
	public String index(@PathVariable(value="projectId") String projectId, Model model) {
		
		LOGGER.info("Verifing if the project " + projectId+" exists");
		
		Project project = projectRepository.findOne(projectId);

		if (project == null) {
			throw new IllegalArgumentException("The project was not found");
		} else {
			LOGGER.info("The project was found");
		}

		LOGGER.info("Finding the solution set for projectId " + projectId);

		List<SolutionSet> listOfSolutionSet = solutionSetRepository.findByProjectId(projectId);

		LOGGER.info("Found " + listOfSolutionSet.size() + " solution sets");

		model.addAttribute("listOfSolutionSet", listOfSolutionSet);
		model.addAttribute("project", project);
		
		LOGGER.info("Opening the page");

		return "project";
	}
	
	@PostMapping("/project/get")	
	public @ResponseBody List<Project> get(@RequestBody String[] ids) {
		
		if (ids.length == 0) {
			throw new IllegalArgumentException("You have to select at least a row");
		}
		
		LOGGER.info("Getting " + ids.length + " projects");
		
		List<Project> projects = new ArrayList<>();
		
		for (String id : ids) {
			
			LOGGER.info("Finding projectId: " + id);
			
			Project project = projectRepository.findOne(id);
			
			if (project == null) {
				throw new IllegalArgumentException("The project was not found. Have you already removed it?");
			}
			
			projects.add(project);
		}

		LOGGER.info("Returning the found projects");
		
		return projects;
	}

	@PostMapping("/project/save")
	public @ResponseBody ResponseEntity<?> save(@RequestBody Project project) {
		
		LOGGER.info("Removing " + project);
		
		// The user wants to save a new project
		if (project.getId().isEmpty()) {
			project.setId(null);
		}

		projectRepository.save(project);
		
		LOGGER.info("The project was successfully saved");
		
		return Response.success("The project was successfully saved");
	}
	
	@PostMapping("/project/remove")
	public @ResponseBody ResponseEntity<?> remove(@RequestBody String[] projectIds) {

		LOGGER.info("Removing " + projectIds.length + " projects");

		for (String projectId : projectIds) {

			LOGGER.info("Finding projectId " + projectId);

			Project project = projectRepository.findOne(projectId);

			if (project == null) {
				throw new IllegalArgumentException("The project was not found. Have you already removed it?");
			}

			LOGGER.info("Found. Removing the project");

			projectRepository.delete(project);

			LOGGER.info("The project was removed. Finding the solution-set related to this project");

			List<SolutionSet> listOfSolutionSet = solutionSetRepository.findByProjectId(projectId);

			LOGGER.info("Found " + listOfSolutionSet.size() + " solution sets. Removing them");

			for (SolutionSet solutionSet : listOfSolutionSet) {
				solutionSetRepository.delete(solutionSet);
			}

			LOGGER.info("The solution sets were removed");
		}

		LOGGER.info("The projects were successfully removed");

		return Response.success("The projects were successfully removed");
	}
}
