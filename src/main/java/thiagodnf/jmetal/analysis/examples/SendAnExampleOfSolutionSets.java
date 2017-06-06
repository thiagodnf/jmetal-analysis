package thiagodnf.jmetal.analysis.examples;

import java.util.ArrayList;
import java.util.List;

import thiagodnf.jmetal.analysis.dumbs.DumbProblem;
import thiagodnf.jmetal.analysis.dumbs.DumbSolution;
import thiagodnf.jmetal.analysis.senders.Sender;

public class SendAnExampleOfSolutionSets {

	public static void main(String[] args) throws Exception {
		send("EXAMPLE_1", getListOfSolutions("(2,11);(4,7);(8,5)"));
		send("EXAMPLE_2", getListOfSolutions("(13,2);(20,1)"));
		send("EXAMPLE_3", getListOfSolutions("(8,9);(12,5)"));
		send("EXAMPLE_4", getListOfSolutions("(10,11);(13,8);(18,6)"));
		send("EXAMPLE_5", getListOfSolutions("(15,11)"));
		send("EXAMPLE_6", getListOfSolutions("(20,12)"));
	}

	public static List<DumbSolution> getListOfSolutions(String str) {

		DumbProblem problem = new DumbProblem(2, 1);

		List<DumbSolution> listOfDumbSolutions = new ArrayList<>();

		String[] split = str.split(";");

		for (String s : split) {

			DumbSolution solution = new DumbSolution(problem);

			String[] objectives = s.replace("(", "").replace(")", "").split(",");

			for (int i = 0; i < objectives.length; i++) {
				solution.setObjective(i, Double.valueOf(objectives[i]));
			}

			solution.setVariableValue(0, "00000");

			listOfDumbSolutions.add(solution);
		}

		return listOfDumbSolutions;
	}

	public static void send(String name, List<DumbSolution> listOfDumbSolutions) throws Exception {
		Sender sender = new Sender();

		sender.setName(name);
		sender.setProjectId("593570c35cf2791c10a8c522");
		sender.setServerUrl("http://localhost:8080/api/solution-set/save");
		sender.setExecutionTime(2L);
		sender.setPopulation(listOfDumbSolutions);

		sender.send();
	}
}
