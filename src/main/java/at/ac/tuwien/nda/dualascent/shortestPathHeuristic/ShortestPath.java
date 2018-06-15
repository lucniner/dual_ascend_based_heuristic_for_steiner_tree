package at.ac.tuwien.nda.dualascent.shortestPathHeuristic;

import at.ac.tuwien.nda.dualascent.SteinerTree.ProblemInstance;
import at.ac.tuwien.nda.dualascent.SteinerTree.SolutionInstance;
import at.ac.tuwien.nda.dualascent.util.Arc;
import at.ac.tuwien.nda.dualascent.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ShortestPath {
  private final Logger logger = LoggerFactory.getLogger(ShortestPath.class);

  private final HashMap<Integer, List<Arc>> graphArcs;
  private final ProblemInstance problemInstance;
  private Optional<Integer> upperBound = Optional.empty();
  private Optional<Integer> currentBestTerminal = Optional.empty();

  // Knoten zu Pair von Vorgänger und die Gesamtdistanz
  private HashMap<Integer, Pair<Optional<Integer>, Optional<Integer>>> dijkstra;
  private HashSet<Integer> alreadyChecked;

  public ShortestPath(final ProblemInstance problemInstance) {
    this.problemInstance = problemInstance;
    this.graphArcs = this.problemInstance.getGraph();
    this.dijkstra = new HashMap<>();
    this.alreadyChecked = new HashSet<>();
  }

  public SolutionInstance solve() {
    final int rootTerminal =  problemInstance.getTerminals().get(0);
    final List<Integer> remainingTerminals = problemInstance.getTerminals();
    remainingTerminals.remove(0);

    SolutionInstance solutionInstance = new SolutionInstance(rootTerminal);
    solutionInstance.addNode(rootTerminal);

    int counter = 0;
    while (!remainingTerminals.isEmpty()) {
      //logger.info("------------------------------");
      //logger.info("Iteration for new Terminal " + counter++);

      dijkstra = new HashMap<>();
      problemInstance.getGraph().keySet().stream().filter(key -> key != rootTerminal).forEach(key ->
              dijkstra.put(key, new Pair(Optional.empty(), Optional.empty()))
      );
      solutionInstance.getSteinerTree().stream().forEach(key ->
              dijkstra.put(key, new Pair(Optional.empty(), Optional.of(0)))
      );
      dijkstra.put(rootTerminal, new Pair<>(Optional.empty(), Optional.of(0)));


      upperBound  = Optional.empty();
      currentBestTerminal = Optional.empty();
      alreadyChecked = new HashSet<>();

      while (true) {
        if (upperBound.isPresent()) {
          if (getMinNotCheckedNode().get() > upperBound.get()) {
            remainingTerminals.remove(getIndex(currentBestTerminal.get(), remainingTerminals));
            solutionInstance.addNode(currentBestTerminal.get());
            //logger.info("Add terminal: " + currentBestTerminal.get());

            int curr = currentBestTerminal.get();
            while(true) {
              solutionInstance.addNode(curr);
              if (dijkstra.get(curr).getKey().isPresent()) {
                for (Arc arc : graphArcs.get(dijkstra.get(curr).getKey().get())) {
                  if (arc.getTo() == curr) {
                    //logger.info("Add arc " + arc.getFrom() + "-" + arc.getTo());
                    solutionInstance.addArc(arc.getFrom(), arc, arc.getWeight());
                    arc.setWeight(0);

                    for (Arc arc2 : graphArcs.get(curr)) {
                      if (arc2.getTo() == arc.getFrom()) {
                        //logger.info("Set 0 to arc " + arc2.getFrom() + "-" + arc2.getTo());
                        arc2.setWeight(0);
                      }
                    }
                  }
                }
              }
              if (!dijkstra.get(curr).getKey().isPresent()) {
                break;
              }
              curr = dijkstra.get(curr).getKey().get();
            }
            break;
          }
        }

        int currentNode = getMinNotCheckedNode().get();
        //logger.info("Current terminal: " + currentNode);
        //logger.info("Evaluate neighbors: " + graphArcs.get(currentNode));
        if (remainingTerminals.size() == 1) {
          upperBound = dijkstra.get(remainingTerminals.get(0)).getValue();
          currentBestTerminal = Optional.of(remainingTerminals.get(0));
        }

        alreadyChecked.add(currentNode);
        for (Arc neighbour : graphArcs.get(currentNode)) {
          if (alreadyChecked.contains(neighbour.getTo())) {
            continue;
          }
          //logger.info("Check neighbor '" + neighbour.getTo() + "' of current terminal " + currentNode);
          if (!dijkstra.get(neighbour.getTo()).getValue().isPresent()) {
            dijkstra.put(neighbour.getTo(),
                    new Pair(
                            Optional.of(currentNode),
                            Optional.of(neighbour.getWeight() + dijkstra.get(currentNode).getValue().get())
                    )
            );
          } else {
            if (dijkstra.get(neighbour.getTo()).getValue().get() > (neighbour.getWeight() + dijkstra.get(currentNode).getValue().get())) {
              dijkstra.put(neighbour.getTo(),
                      new Pair(
                              Optional.of(currentNode),
                              Optional.of(neighbour.getWeight() + dijkstra.get(currentNode).getValue().get())
                      )
              );
            }
          }

//          for (Map.Entry<Integer, Pair<Optional<Integer>, Optional<Integer>>> entry : dijkstra.entrySet()) {
//            if ( entry.getKey() == 5 || entry.getKey() == 49) {
//              logger.info("Node: " + entry.getKey() + ", Prev: " + entry.getValue().getKey() + ", Dist: " + entry.getValue().getValue());
//            }
//          }

          if (remainingTerminals.contains(neighbour.getTo())) {
            if (upperBound.isPresent()) {
              if (dijkstra.get(neighbour.getTo()).getValue().get() < upperBound.get()) {
                upperBound = Optional.of(dijkstra.get(neighbour.getTo()).getValue().get());
                currentBestTerminal = Optional.of(neighbour.getTo());
              }
            } else {
              upperBound = Optional.of(dijkstra.get(neighbour.getTo()).getValue().get());
              currentBestTerminal = Optional.of(neighbour.getTo());
            }
          }
        }
      }
    }

    return solutionInstance;
  }

  private Optional<Integer> getMinNotCheckedNode() {
    Optional<Integer> currentBestNode = Optional.empty();
    Optional<Integer> weight = Optional.empty();

    for (Map.Entry<Integer, Pair<Optional<Integer>, Optional<Integer>>> node : dijkstra.entrySet()) {

      if (alreadyChecked.contains(node.getKey())) {
        continue;
      }

      if (!node.getValue().getValue().isPresent()) {
        continue;
      }

      if ((!currentBestNode.isPresent())) {
          currentBestNode = Optional.of(node.getKey());
          weight = Optional.of(node.getValue().getValue().get());
      } else {
        if (weight.get() > node.getValue().getValue().get()) {
          currentBestNode = Optional.of(node.getKey());
          weight = Optional.of(node.getValue().getValue().get());
        }
      }
    }
    return currentBestNode;
  }

  private int getIndex(Integer node, List<Integer> remainingTerminals) {
    int count = 0;
    for (final Integer element : remainingTerminals) {
      if (element.equals(node)) {
        return count;
      }
      count++;
    }
    throw new RuntimeException("element '" + node + "' not in list " + remainingTerminals);
  }
}
