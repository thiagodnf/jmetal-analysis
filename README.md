# JMetal Analysis

A tool for analysing the JMetal Framework's results

**Under Development**

## Screenshots

![alt tag](https://raw.githubusercontent.com/thiagodnf/jmetal-analysis/master/src/main/resources/assets/img1.png)
![alt tag](https://raw.githubusercontent.com/thiagodnf/jmetal-analysis/master/src/main/resources/assets/img2.png)
![alt tag](https://raw.githubusercontent.com/thiagodnf/jmetal-analysis/master/src/main/resources/assets/img3.png)
![alt tag](https://raw.githubusercontent.com/thiagodnf/jmetal-analysis/master/src/main/resources/assets/img4.png)

How to send solutions

Sender sender = new Sender();

		sender.setName(key);
		sender.setProjectId("593570c35cf2791c10a8c522");
		sender.setServerUrl("http://localhost:8080/api/solution-set/save");
		sender.setExecutionTime(estimatedTime);
		sender.setPopulation(population);

		sender.send();

