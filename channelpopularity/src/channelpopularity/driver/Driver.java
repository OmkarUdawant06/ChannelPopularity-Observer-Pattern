package channelpopularity.driver;

import channelpopularity.context.Context;
import channelpopularity.exception.ContextException;
import channelpopularity.state.StateName;
import channelpopularity.state.factory.SimpleStateFactory;
import channelpopularity.util.FileProcessor;
import channelpopularity.util.Results;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author John Doe
 *
 */
public class Driver{
	private static final int REQUIRED_NUMBER_OF_CMDLINE_ARGS = 2;

	public static void main(String[] args) throws IOException, NullPointerException {

		/*
		 * As the build.xml specifies the arguments as input,output or metrics, in case the
		 * argument value is not given java takes the default value specified in
		 * build.xml. To avoid that, below condition is used
		 */
		if ((args.length != 2) || (args[0].equals("${input}")) || (args[1].equals("${output}"))) {
			System.err.printf("Error: Incorrect number of arguments. Program accepts %d arguments.", REQUIRED_NUMBER_OF_CMDLINE_ARGS);
			System.exit(0);
		}

		FileProcessor openFp = new FileProcessor(args[0]);
		Results writeFp = new Results(args[1]);

		SimpleStateFactory simpleFactory = new SimpleStateFactory();
		List<StateName> stateName = new ArrayList<StateName>();
		try{
			String words, operation, input = "", videoName;
			StringBuilder outputFile = new StringBuilder();

			/*
			* Checks if file is empty */
			if (openFp.poll().length() == 0){
				throw new NullPointerException();
			} else {
				openFp = new FileProcessor(args[0]);

				stateName.add(StateName.UNPOPULAR);
				stateName.add(StateName.MILDLY_POPULAR);
				stateName.add(StateName.HIGHLY_POPULAR);
				stateName.add(StateName.ULTRA_POPULAR);

				Context context = new Context(simpleFactory,stateName);

				while ((words = openFp.poll()) != null) {
					if (words.isEmpty()){
						throw new ContextException("Parsed line is Empty!");
					}

					/*
					* Break lines from poll() method
					* into appropriate variables to parse */
					if (words.contains("METRICS__")) {
						operation = words.substring(0, words.indexOf("_"));
						videoName = words.substring(words.indexOf("_") + 2, words.lastIndexOf(":") - 1);
						input = words.substring(words.lastIndexOf("[") + 1, words.lastIndexOf("]"));
					} else if (words.contains("AD_REQUEST__")) {
						operation = words.substring(0, words.indexOf("__"));
						videoName = words.substring(words.indexOf("__") + 2, words.lastIndexOf(":"));
						input = words.substring(words.indexOf("=") + 1);
					} else {
						operation = words.substring(0, words.indexOf(":"));
						videoName = words.substring(words.lastIndexOf(":") + 1);
					}

					/*
					* Check what operation to perform */
					switch (operation){
						case "ADD_VIDEO":
							outputFile.append(context.addVideo(videoName));
							break;
						case "REMOVE_VIDEO":
							outputFile.append(context.removeVideo(videoName));
							break;
						case "METRICS":
							outputFile.append(context.metricsVideo(videoName, input));
							break;
						case "AD_REQUEST":
							outputFile.append(context.adRequest(videoName, input));
							break;
						default:
							throw new ContextException("Invalid input Line found! Check syntax!");
					}
					outputFile.append("\n");
				}

				/*
				 * Write to output file */
				writeFp.printToConsole(outputFile);
				writeFp.addOutputToFile(outputFile);
			}
		} catch (IOException | NullPointerException | ContextException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		} finally{
			openFp.close();
			writeFp.closeFile();
		}
	}
}
