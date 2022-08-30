package teamtask;

import java.util.List;

import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;

public class FactoryClass {
  
  @Parameters({"CSVpath"})
  @Factory
	public Object[] factoryMethodCSV(String CSVpath) throws Exception {
		List<String[]> lines = ReadCsvFile.readAllLines(CSVpath);
		lines.remove(0);
		Object[] data = new Object[lines.size()];
		int index = 0;
		for(String[] line : lines) {
			String Route = line[0];
      String Status = line[1];
			data[index] = new TestClassForFactoryCSV(Route,Status);
			index++;
		}
		return data;
	}

  @Parameters({"JSONpath"})
  @Factory
	public Object[] factoryMethodJSON(String JSONpath) throws Exception {
    List<Object[]> lines = ReadJson.parseList(JSONpath);
    Object[] data = new Object[lines.size()];
    int index = 0;
    for(Object[] line: lines){
      String Method = line[0].toString();
      String Route = line[1].toString();
      Object Body = line[2];
      String ExStatus = line[3].toString();
      Object ExBody = line[4];
			data[index] = new TestClassForFactoryJSON(Method,Route,Body,ExStatus,ExBody);
			index++;
    }
    return data;
	}

}
