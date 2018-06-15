package at.ac.tuwien.nda.dualascent.SteinerTree;

import at.ac.tuwien.nda.dualascent.exceptions.SteinerTreeVerifierException;
import at.ac.tuwien.nda.dualascent.util.Arc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class SolutionVerifier {
  private final Logger logger = LoggerFactory.getLogger(SolutionVerifier.class);

  ProblemInstance problemInstance;
  SolutionInstance solutionInstance;
  HashMap<Integer, List<Arc>> arcs;
  HashSet<Integer> terminalsToVisit;
  HashSet<Integer> alreadyVisitedNodes;

  public SolutionVerifier(ProblemInstance problemInstance, SolutionInstance solutionInstance) {
    this.problemInstance = problemInstance;
    this.solutionInstance = solutionInstance;
    arcs = solutionInstance.getArcs();
    terminalsToVisit = new HashSet<>(problemInstance.getTerminals());
    alreadyVisitedNodes = new HashSet<>();
  }

  public boolean verifySolution() {
    Integer rootTerminal = solutionInstance.getRootTerminal();
    terminalsToVisit.remove(rootTerminal);

    try {
      visitNode(rootTerminal);
    } catch (SteinerTreeVerifierException e) {
      logger.error("Exception occured: " + e);
      return false;
    }

    if (terminalsToVisit.size() > 0) {
      logger.error("Not all terminals visited");
      return false;
    }

    return true;
  }

  private void visitNode(int currentTerminal) throws SteinerTreeVerifierException {
    alreadyVisitedNodes.add(currentTerminal);
    terminalsToVisit.remove(currentTerminal);

    if (arcs.get(currentTerminal) == null) {
      return;
    }

    for (Arc arc : arcs.get(currentTerminal)) {
      if (alreadyVisitedNodes.contains(arc.getTo())) {
        throw new SteinerTreeVerifierException("Circle found in solution trying to reach node " + arc.getTo());
      } else {
        visitNode(arc.getTo());
      }
    }
  }
}
