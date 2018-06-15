package at.ac.tuwien.nda.dualascent;

import at.ac.tuwien.nda.dualascent.SteinerTree.ProblemInstance;
import at.ac.tuwien.nda.dualascent.SteinerTree.SolutionInstance;
import at.ac.tuwien.nda.dualascent.SteinerTree.SolutionVerifier;
import at.ac.tuwien.nda.dualascent.dualascend.DualAscend;
import at.ac.tuwien.nda.dualascent.exceptions.SteinerTreeLoadingException;
import at.ac.tuwien.nda.dualascent.reader.ProblemReader;
import at.ac.tuwien.nda.dualascent.shortestPathHeuristic.ShortestPath;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * This Java source file was generated by the Gradle 'init' task.
 */
public class App {
  private static final Logger logger = LoggerFactory.getLogger(App.class);

  public static void main(String[] args) {
    CommandLine cmd = parseArguments(args);

    String fileName = App.class.getClassLoader().getResource("slideExample.stp").getPath();

    List<File> files = new ArrayList<>();

    if (cmd.hasOption("directory")) {
      File directory = new File(cmd.getOptionValue("directory"));
      if (!directory.isDirectory()) {
        logger.error("Specified path is not a directory");
        return;
      }
      File[] fileArray = directory.listFiles();
      files.addAll(Arrays.asList(fileArray));
    } else if (cmd.hasOption("file")) {
      File file = new File(cmd.getOptionValue("file"));
      if (!file.isFile()) {
        logger.error("Specified file does not exist");
        return;
      }
      files.add(file);
    } else {
      files.add(new File(fileName));
    }

    for (File file : files) {
      ProblemInstance instance;
      try {
        instance = ProblemReader.loadInstance(file);
      } catch (IOException | SteinerTreeLoadingException e) {
        logger.error(e.getLocalizedMessage());
        return;
      }

//      SolutionInstance solutionInstance = new DualAscend(instance).solve();
//      ProblemInstance instance2 = solutionInstance.convertToProblemInstance();
      SolutionInstance solutionInstance2 = new ShortestPath(instance).solve();
      SolutionVerifier solutionVerifier = new SolutionVerifier(instance, solutionInstance2);


      if (solutionVerifier.verifySolution()) {
        logger.info("Valid solution for instance '"+file.getName()+"' was created with sum: " + solutionInstance2.getDistanceSum()); // sum must also be calculated
      }
    }
  }

  private static CommandLine parseArguments(String[] args) {
    CommandLine cmd = null;

    Options options = new Options();
    Option help = new Option( "h", "help", false, "print this message" );
    Option file = new Option("f", "file", true, "load instance from given file");
    Option directory = new Option("d", "directory", true, "load all instances from given directory");

    options.addOption(help);
    options.addOption(file);
    options.addOption(directory);

    try {
      cmd = new DefaultParser().parse(options, args, false);
    } catch (ParseException e) {
      System.out.println(e.getMessage() + "\n");
      new HelpFormatter().printHelp("java -jar <name>", options, true);
      System.exit(0);
    }

    if (cmd.hasOption("help")) {
      new HelpFormatter().printHelp("java -jar <name>", options, true);
      System.exit(0);
    }

    return cmd;
  }
}
