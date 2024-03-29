package at.ac.tuwien.nda.dualascent.reader;

import at.ac.tuwien.nda.dualascent.SteinerTree.ProblemInstance;
import at.ac.tuwien.nda.dualascent.exceptions.SteinerTreeLoadingException;
import at.ac.tuwien.nda.dualascent.util.Arc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProblemReader {
  private static final Logger logger = LoggerFactory.getLogger(ProblemReader.class);

  public static ProblemInstance loadInstance(File file) throws IOException, SteinerTreeLoadingException {
    ProblemInstance instance = new ProblemInstance();

    try (BufferedReader reader =
               new BufferedReader(
                       new InputStreamReader(
                               new FileInputStream(file)))) {


      String currentLine = reader.readLine();
      while(!currentLine.startsWith("EOF")) {
        if (currentLine.startsWith("SECTION Comment")
                || currentLine.startsWith("Section Comment")) {
          parseComment(reader, instance);
        } else if (currentLine.startsWith("SECTION Graph")
                || currentLine.startsWith("Section Graph")) {
          parseGraph(reader, instance);
        } else if (currentLine.startsWith("SECTION Terminals")
                || currentLine.startsWith("Section Terminals")) {
          parseTerminals(reader, instance);
        } else if (currentLine.startsWith("SECTION Coordinates")
                || currentLine.startsWith("Section Coordinates")) {
          parseCoordinates(reader, instance);
        }

        currentLine = reader.readLine();
        if (currentLine == null) {
          throw new SteinerTreeLoadingException("EOF was missing in file");
        }
      }
    }

    return instance;
  }

  private static void parseComment(BufferedReader bf, ProblemInstance instance) throws IOException, SteinerTreeLoadingException {
    String line = bf.readLine();
    while(!(line.startsWith("END") || line.startsWith("End"))) {
      line = bf.readLine();
    }
  }

  private static void parseGraph(BufferedReader bf, ProblemInstance instance) throws IOException, SteinerTreeLoadingException {
    String line = bf.readLine();
    while(!(line.startsWith("END") || line.startsWith("End"))) {
      String[] words = line.split(" ");
      if ("Nodes".equals(words[0])) {
        //instance.setNodeNumber(Integer.parseInt(words[1]));
      } else if ("Edges".equals(words[0])) {
        //instance.setEdgeNumber(Integer.parseInt(words[1]));
        instance.setDirected(false);
      } else if ("E".equals(words[0])) {
        instance.addArc(new Arc(Integer.parseInt(words[1]), Integer.parseInt(words[2]), Integer.parseInt(words[3])));
        instance.addArc(new Arc(Integer.parseInt(words[2]), Integer.parseInt(words[1]), Integer.parseInt(words[3])));
      } else if ("Arcs".equals(words[0])) {
        instance.setDirected(true);
      } else if ("A".equals(words[0])) {
        instance.addArc(new Arc(Integer.parseInt(words[1]), Integer.parseInt(words[2]), Integer.parseInt(words[3])));
      } else {
        throw new SteinerTreeLoadingException("Unexpected token reading graph section: " + words[0]);
      }
      line = bf.readLine();
    }
  }

  private static void parseTerminals(BufferedReader bf, ProblemInstance instance) throws IOException, SteinerTreeLoadingException {
    ArrayList<Integer> terminals = new ArrayList<>();
    String line = bf.readLine();
    while(!(line.startsWith("END") || line.startsWith("End"))) {
      String[] words = line.split(" ");
      if ("Terminals".equals(words[0])) {
        //instance.setTerminalNumber(Integer.parseInt(words[1]));
      } else if ("T".equals(words[0])) {
        terminals.add(Integer.parseInt(words[1]));
      }
      line = bf.readLine();
    }
    instance.setTerminals(terminals);
  }

  private static void parseCoordinates(BufferedReader bf, ProblemInstance instance) throws IOException, SteinerTreeLoadingException {
    String line = bf.readLine();
    while(!(line.startsWith("END") || line.startsWith("End"))) {
      line = bf.readLine();
    }
  }
}
