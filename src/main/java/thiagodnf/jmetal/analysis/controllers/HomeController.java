package thiagodnf.jmetal.analysis.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import thiagodnf.jmetal.analysis.beans.Project;
import thiagodnf.jmetal.analysis.repositories.ProjectRepository;

@Controller
public class HomeController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private ProjectRepository repository;
	
	@GetMapping("/")
	public String index(Model model) {
		
		LOGGER.info("Getting the list of projects available");

		List<Project> listProject = repository.findAll();

		LOGGER.info("Found " + listProject.size() + " projects");

		model.addAttribute("listOfProjects", listProject);

		return "home";
	}
}
