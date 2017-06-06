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

import thiagodnf.jmetal.analysis.beans.SolutionSet;
import thiagodnf.jmetal.analysis.repositories.SolutionSetRepository;
import thiagodnf.jmetal.analysis.response.Response;

@Controller
public class SolutionSetController extends WebMvcConfigurerAdapter {

	private final Logger LOGGER = LoggerFactory.getLogger(SolutionSetController.class);

	@Autowired
	private SolutionSetRepository solutionSetRepository;
	
	@GetMapping("/solution-set/{id}")
	public String index(@PathVariable(value="id") String id, Model model) {
		
		LOGGER.info("Finding the solution set with id " + id);

		SolutionSet solutionSet = solutionSetRepository.findOne(id);

		if (solutionSet == null) {
			throw new IllegalArgumentException("The solution set was not found");
		}

		model.addAttribute("solutionSet", solutionSet);
		
		LOGGER.info("Opening the page");

		return "solution-set";
	}
	
	@PostMapping("/solution-set/remove")
	public @ResponseBody ResponseEntity<?> remove(@RequestBody String[] ids) {

		LOGGER.info("Removing " + ids.length + " solution sets");

		for (String id : ids) {

			LOGGER.info("Finding solution set: " + id);

			SolutionSet solutionSet = solutionSetRepository.findOne(id);
			
			if (solutionSet == null) {
				throw new IllegalArgumentException("The solution set was not found. Have you already removed it?");
			}

			LOGGER.info("Found. Removing the solution set");

			solutionSetRepository.delete(solutionSet);

			LOGGER.info("The solution set was removed");
		}

		LOGGER.info("The solution sets were successfully removed");

		return Response.success("The solution set were successfully removed");
	}
	
	@PostMapping("/solution-set/get")	
	public @ResponseBody List<SolutionSet> get(@RequestBody String[] solutionSetIds) {
		
		if (solutionSetIds.length == 0) {
			throw new IllegalArgumentException("You have to select at least a row");
		}
		
		LOGGER.info("Getting " + solutionSetIds.length + " solution sets");
		
		List<SolutionSet> listOfSolutionSets = new ArrayList<>();
		
		for (String solutionSetId : solutionSetIds) {
			
			LOGGER.info("Finding solutionSetId " + solutionSetId);
			
			SolutionSet solutionSet = solutionSetRepository.findOne(solutionSetId);
			
			if (solutionSet == null) {
				throw new IllegalArgumentException("The solutionSet was not found. Have you already removed it?");
			}
			
			listOfSolutionSets.add(solutionSet);
		}

		LOGGER.info("Returning the found solution sets");
		
		return listOfSolutionSets;
	}
}
