package at.ac.tuwien.nda.dualascent.dualascend;

import at.ac.tuwien.nda.dualascent.SteinerTree.ProblemInstance;
import at.ac.tuwien.nda.dualascent.SteinerTree.SolutionInstance;
import at.ac.tuwien.nda.dualascent.util.Arc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class DualAscend {
  private final Logger logger = LoggerFactory.getLogger(DualAscend.class);

  private final HashMap<Integer, List<Arc>> graphArcsByToNode;
  private final ProblemInstance problemInstance;
  private List<Arc> currentGraph;
  private int lowerBound = 0;

  public DualAscend(final ProblemInstance problemInstance) {
    this.problemInstance = problemInstance;
    this.graphArcsByToNode = this.problemInstance.cloneGraphByToNode();
    currentGraph = new ArrayList<>();
  }

  public SolutionInstance solve() {
    final int rootTerminal =  problemInstance.getTerminals().get(0);
    final List<Integer> remainingTerminals = problemInstance.getTerminals();
    remainingTerminals.remove(0);
    SolutionInstance solutionInstance = new SolutionInstance(rootTerminal, problemInstance.getTerminals());

    while (!remainingTerminals.isEmpty()) {
      final int activeTerminal = remainingTerminals.get(0);

      HashSet<Integer> nodesContained = reverseBFS(activeTerminal);
      if (nodesContained.contains(rootTerminal)) {
        remainingTerminals.remove(0);
        continue;
      }

      Optional<Integer> delta = Optional.empty();
      for (Integer node : nodesContained) {
        for (Arc arc : graphArcsByToNode.get(node)) {
          if (!nodesContained.contains(arc.getFrom())) {
            if (!delta.isPresent()) {
              delta = Optional.of(arc.getWeight());
            } else if (arc.getWeight() < delta.get()) {
              delta = Optional.of(arc.getWeight());
            }
          }
        }
      }

      for (Integer node : nodesContained) {
        for (Arc arc : graphArcsByToNode.get(node)) {
          if (!nodesContained.contains(arc.getFrom())) {
            arc.setWeight(Math.max(arc.getWeight() - delta.get(), 0));
            if (arc.getWeight() == 0) {
              currentGraph.add(arc);
              solutionInstance.addArc(arc.getFrom(), arc, problemInstance.getWeight(arc.getFrom(), arc.getTo()).get());
            }
          }
        }
      }

     if (delta.isPresent()) {
       lowerBound += delta.get();
     }
    }

    logger.info("lower bound: " + lowerBound);
    return solutionInstance;
  }

  private HashSet<Integer> reverseBFS(int activeTerminal) {
    HashSet<Integer> nodesContained = new HashSet<>();
    return visitTopNode(activeTerminal, nodesContained);
  }

  private HashSet<Integer> visitTopNode(int currentNode, HashSet<Integer> nodesContained) {
    nodesContained.add(currentNode);

      for (Arc arc : currentGraph) {
        if ((!nodesContained.contains(arc.getFrom()) && (arc.getTo() == currentNode))) {
          visitTopNode(arc.getFrom(), nodesContained);
        }
    }
    return nodesContained;
  }
}