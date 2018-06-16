package at.ac.tuwien.nda.dualascent.visualization;

import at.ac.tuwien.nda.dualascent.SteinerTree.ProblemInstance;
import at.ac.tuwien.nda.dualascent.SteinerTree.SolutionInstance;
import at.ac.tuwien.nda.dualascent.util.Arc;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SteinerTreeVisualizer {

  private final ProblemInstance problem;
  private final SolutionInstance solution;

  public SteinerTreeVisualizer(final ProblemInstance problem, final SolutionInstance solution) {
    this.problem = problem;
    this.solution = solution;
  }

  public void createGraph() {
    final List<Integer> created = new ArrayList<>();
    Graph graph = new MultiGraph("graph");

    for (final Map.Entry<Integer, List<Arc>> e : problem.getGraph().entrySet()) {


      for (final Arc arc : e.getValue()) {


        if (!created.contains(arc.getTo())) {
          created.add(arc.getTo());
          Node to = graph.addNode(String.valueOf(arc.getTo()));
          to.addAttribute("ui.label", String.valueOf(arc.getTo()));
          if (problem.getTerminals().contains(arc.getTo())) {
            to.addAttribute("ui.style", "fill-color: blue;size: 22px;");
          } else {
            to.addAttribute("ui.style", "fill-color: black;size: 22px;");
          }
        }

        if (!created.contains(arc.getFrom())) {
          created.add(arc.getFrom());
          Node from = graph.addNode(String.valueOf(arc.getFrom()));
          from.addAttribute("ui.label", String.valueOf(arc.getFrom()));

          if (problem.getTerminals().contains(arc.getFrom())) {
            from.addAttribute("ui.style", "fill-color: blue;size: 22px;");
          } else {
            from.addAttribute("ui.style", "fill-color: black;size: 22px;");
          }

        }

        Node e1 = graph.getNode(String.valueOf(arc.getTo()));
        Node e3 = graph.getNode(String.valueOf(arc.getFrom()));
        Edge edge = graph.addEdge(UUID.randomUUID().toString(), e1, e3);
        if (solution.getSteinerTree().contains(arc.getTo()) && solution.getSteinerTree().contains(arc.getFrom())) {
          edge.addAttribute("ui.style", "fill-color: black;size: 4px;");
        } else {
          edge.addAttribute("ui.style", "fill-color: gray;size: 2px;");
        }

      }
    }

    graph.display(true);
  }
}
