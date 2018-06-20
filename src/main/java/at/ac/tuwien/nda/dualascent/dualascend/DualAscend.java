package at.ac.tuwien.nda.dualascent.dualascend;

import at.ac.tuwien.nda.dualascent.SteinerTree.ProblemInstance;
import at.ac.tuwien.nda.dualascent.SteinerTree.SolutionInstance;
import at.ac.tuwien.nda.dualascent.util.Arc;
import at.ac.tuwien.nda.dualascent.util.Pair;
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

    PriorityQueue<Pair<Integer, HashSet<Integer>>> priorityQueue = new PriorityQueue(remainingTerminals.size(), new Comparator<Pair<Integer, HashSet<Integer>>>() {
      @Override
      public int compare(Pair<Integer, HashSet<Integer>> o1, Pair<Integer, HashSet<Integer>> o2) {
        return o1.getValue().size() - o2.getValue().size();
      }
    });

    for (Integer node : remainingTerminals) {
      HashSet<Integer> set = new HashSet();
      set.add(node);
      priorityQueue.add(new Pair(node, set));
    }

    while (!remainingTerminals.isEmpty()) {
      //final int activeTerminal = remainingTerminals.get(0);
      //HashSet<Integer> nodesContained = reverseBFS(activeTerminal);
      //if (nodesContained.contains(rootTerminal)) {
      //  remainingTerminals.remove(0);
      //  continue;
      //}

      boolean appropriateTerminalFound = false;
      Pair<Integer, HashSet<Integer>> activeTerminal = priorityQueue.poll();
      while (!appropriateTerminalFound) {
        activeTerminal = new Pair(activeTerminal.getKey(), reverseBFS(activeTerminal.getKey()));
        if (priorityQueue.size() == 0) {
          appropriateTerminalFound = true;
        } else if (activeTerminal.getValue().size() > priorityQueue.peek().getValue().size()) {
          priorityQueue.add(activeTerminal);
          activeTerminal = priorityQueue.poll();
        } else {
          appropriateTerminalFound = true;
        }
      }

      if (activeTerminal.getValue().contains(rootTerminal)) {
        remainingTerminals.remove((Integer)activeTerminal.getKey());
        continue;
      }
      priorityQueue.add(activeTerminal);

      Optional<Integer> delta = Optional.empty();
      for (Integer node : activeTerminal.getValue()) {
        for (Arc arc : graphArcsByToNode.get(node)) {
          if (!activeTerminal.getValue().contains(arc.getFrom())) {
            if (!delta.isPresent()) {
              delta = Optional.of(arc.getWeight());
            } else if (arc.getWeight() < delta.get()) {
              delta = Optional.of(arc.getWeight());
            }
          }
        }
      }

      for (Integer node : activeTerminal.getValue()) {
        for (Arc arc : graphArcsByToNode.get(node)) {
          if (!activeTerminal.getValue().contains(arc.getFrom())) {
            arc.setWeight(Math.max(arc.getWeight() - delta.get(), 0));
            if (arc.getWeight() == 0) {
              currentGraph.add(arc);
              solutionInstance.addArc(arc.getFrom(), arc.getTo(), problemInstance.getWeight(arc.getFrom(), arc.getTo()).get());
            }
          }
        }
      }

     if (delta.isPresent()) {
       lowerBound += delta.get();
     }
    }

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