package at.ac.tuwien.nda.dualascent.shortestPathHeuristic;

import at.ac.tuwien.nda.dualascent.SteinerTree.ProblemInstance;
import at.ac.tuwien.nda.dualascent.SteinerTree.SolutionInstance;
import at.ac.tuwien.nda.dualascent.util.Edge;
import es.usc.citius.hipster.algorithm.Algorithm;
import es.usc.citius.hipster.algorithm.Hipster;
import es.usc.citius.hipster.graph.GraphBuilder;
import es.usc.citius.hipster.graph.GraphSearchProblem;
import es.usc.citius.hipster.graph.HipsterGraph;
import es.usc.citius.hipster.model.problem.SearchProblem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShortestPath {

  private final ProblemInstance problemInstance;
  private final List<Integer> terminalSolution = new ArrayList<>();
  private HipsterGraph<Integer, Integer> graph;

  public ShortestPath(final ProblemInstance problemInstance) {
    this.problemInstance = problemInstance;
  }

  public SolutionInstance solve() {

    this.constructGraph();

    final List<Integer> terminals = problemInstance.getTerminals();
    for (final Integer startingTerminal : terminals) {
      terminalSolution.add(startingTerminal);
      for (final Integer terminal : terminals) {
        if (!terminalSolution.contains(terminal)) {
          final SearchProblem problem =
                  GraphSearchProblem.startingFrom(startingTerminal)
                          .in(graph)
                          .takeCostsFromEdges()
                          .build();
          final Algorithm.SearchResult result = Hipster.createDijkstra(problem).search(terminal);
          printSolutionGraphs(result.getOptimalPaths());
          terminalSolution.add(terminal);
        }
      }
    }
    return null;
  }

  private void constructGraph() {
    final GraphBuilder<Integer, Integer> builder = GraphBuilder.create();
    for (final Edge edge : problemInstance.getEdges()) {
      builder.connect(edge.getTo()).to(edge.getFrom()).withEdge(edge.getWeight());
    }
    graph = builder.createUndirectedGraph();
  }

  private void printSolutionGraphs(final List<List<Integer>> paths) {
    paths.forEach(path -> System.out.println(Arrays.toString(path.toArray())));
  }
}
