package htmlparser;

import java.io.File;
import java.io.IOException;

public class Test {
	
	public static void main(String[] args) throws IOException {
		File folder = new File("C:/foods");
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles) {
		    if (file.isFile()) {
		        Runtime r = Runtime.getRuntime();
				  Process p = null;
				  String command = "mongoimport -h ds029456.mlab.com:29456 -d enfesco -c enfesco -u enfesco -p 123456 C:\\foods\\"+file.getName();
				  try {
				   p = r.exec(command);

				  } catch (Exception e){
				   System.out.println("Error executing " + command + e.toString());
				  }
		    }
		    System.out.println("Complated");
		}
		
		
	}

}
