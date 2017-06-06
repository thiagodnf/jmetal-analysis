package thiagodnf.jmetal.analysis.senders;

import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uma.jmetal.solution.Solution;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import thiagodnf.jmetal.analysis.beans.SolutionSet;
import thiagodnf.jmetal.analysis.utils.ConvertUtils;
import thiagodnf.jmetal.analysis.utils.DateUtils;

public class Sender {

	private final Logger LOGGER = LoggerFactory.getLogger(Sender.class);

	protected String projectId;

	protected String name;

	protected String serverUrl;

	protected long executionTime;

	protected List<? extends Solution<?>> population;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public long getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(long executionTime) {
		this.executionTime = executionTime;
	}

	public List<? extends Solution<?>> getPopulation() {
		return population;
	}

	public void setPopulation(List<? extends Solution<?>> population) {
		this.population = population;
	}

	public void send() throws Exception {

		SolutionSet solutionSet = ConvertUtils.toSolutionSet(population);

		solutionSet.setName(name);
		solutionSet.setProjectId(projectId);
		solutionSet.setCreationTime(DateUtils.getDateNow());
		solutionSet.getIndicators().put("executionTime", Double.valueOf(executionTime));

		LOGGER.info("["+name+"] Sending");

		CloseableHttpClient httpClient = HttpClients.createDefault();

		try {
			HttpPost httpPost = new HttpPost(this.serverUrl);

			httpPost.addHeader("content-type", "application/json; charset=utf-8");

			ObjectMapper mapper = new ObjectMapper();

			String json = "";

			try {
				json = mapper.writeValueAsString(solutionSet);
			} catch (JsonProcessingException e) {
				json = "";
			}

			StringEntity params = new StringEntity(json);

			httpPost.setEntity(params);

			CloseableHttpResponse response = httpClient.execute(httpPost);

			try {
				if (response.getStatusLine().getStatusCode() == 200) {
					LOGGER.info("["+name+"] Sent");
				} else {
					LOGGER.info("Something is wrong");
				}
			} finally {
				response.close();
			}
		} finally {
			httpClient.close();
		}
	}

}
