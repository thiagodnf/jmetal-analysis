package thiagodnf.jmetal.analysis.examples;

import java.util.List;

import thiagodnf.jmetal.analysis.Application;
import thiagodnf.jmetal.analysis.dumbs.DumbProblem;
import thiagodnf.jmetal.analysis.dumbs.DumbSolution;
import thiagodnf.jmetal.analysis.senders.Sender;
import thiagodnf.jmetal.analysis.utils.ConvertUtils;

/**
 * This class sends a set of example of solutions to jmetal-analysis. To use this,
 * it is necessary to run the jmetal-analysis before (to do that, please run the
 * {@link Application} class). You can use this to simulate some points in the jmetal-analysis
 * 
 * @author Thiago N. Ferreira
 * @version 1.0.0
 * @since 2017-06-06
 */
public class SendAnSetOfSolutions {

	public static void main(String[] args) throws Exception {
		
		System.out.println("Starting");
		
		// To create a solution it is necessary to define a problem. In this example
		// the dumb problem has two objectives and a variable
		DumbProblem problem = new DumbProblem(2, 1);
		
		// Send the points to server
		send("EXAMPLE_1", ConvertUtils.toListOfSolutions(problem, "(2,11);(4,7);(8,5)"));
		send("EXAMPLE_2", ConvertUtils.toListOfSolutions(problem, "(13,2);(20,1)"));
		send("EXAMPLE_3", ConvertUtils.toListOfSolutions(problem, "(8,9);(12,5)"));
		send("EXAMPLE_4", ConvertUtils.toListOfSolutions(problem, "(10,11);(13,8);(18,6)"));
		send("EXAMPLE_5", ConvertUtils.toListOfSolutions(problem, "(15,11)"));
		send("EXAMPLE_6", ConvertUtils.toListOfSolutions(problem, "(20,12)"));
		
		System.out.println("Done");
	}

	/**
	 * This method sends a list of {@link DumbSolutions} to server 
	 * 
	 * @param name The name that identifies this list of {@link DumbSolutions}
	 * @param listOfDumbSolutions the list of {@link DumbSolutions} or a.k.a "population"
	 * @throws Exception if some problem occurs when the list is sent
	 */
	public static void send(String name, List<DumbSolution> listOfDumbSolutions) throws Exception {
		
		// Initiate the sender object
		Sender sender = new Sender();

		// Add the general information
		sender.setName(name);
		sender.setProjectId("593570c35cf2791c10a8c522");
		sender.setServerUrl("http://localhost:8080/api/solution-set/save");
		sender.setExecutionTime(2L);
		sender.setPopulation(listOfDumbSolutions);

		// Send to jmetal-analysis to save it on database
		sender.send();
	}
}
