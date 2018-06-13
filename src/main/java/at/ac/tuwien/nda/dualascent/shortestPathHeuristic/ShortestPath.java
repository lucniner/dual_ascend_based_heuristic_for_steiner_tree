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

import java.util.*;
import java.util.stream.Collectors;

public class ShortestPath {

  private final Random random = new Random();
  private final List<Edge> graphEdges;
  private final ProblemInstance problemInstance;
  private HipsterGraph<Integer, Integer> graph;
  private int lowerBound = 0;

  public ShortestPath(final ProblemInstance problemInstance) {
    this.problemInstance = problemInstance;
    this.graphEdges = this.problemInstance.getEdges();
  }

  public SolutionInstance solve() {


    final List<Edge> currentGraph = new ArrayList<>();
    final int rootIndex = getRandom(0, problemInstance.getTerminalNumber() - 1);
    final int rootTerminal = problemInstance.getTerminals().get(rootIndex);
    System.out.println("root terminal: " + rootTerminal);

    final List<Integer> terminals = problemInstance.getTerminals();
    terminals.remove(rootIndex);

    while (!terminals.isEmpty()) {
      final int activeIndex = getRandom(0, terminals.size() - 1);
      final int activeTerminal = terminals.get(activeIndex);

      this.constructGraph(currentGraph);

      SearchProblem problem =
              GraphSearchProblem.startingFrom(activeTerminal).in(graph).takeCostsFromEdges().build();
      final Algorithm.SearchResult result = Hipster.createDijkstra(problem).search(rootTerminal);

      if (!result.getOptimalPaths().isEmpty()) {
        if (result.getOptimalPaths().size() == 1) {
          List<Integer> l = (List<Integer>) result.getOptimalPaths().get(0);
          if (l.contains(rootTerminal)) {
            terminals.remove(activeIndex);
            continue;
          }
        }

      }


      final OptionalInt delta =
              getConnectedEdgesToNode(activeTerminal).stream().mapToInt(Edge::getWeight).min();

      for (final Edge edge : getConnectedEdgesToNode(activeTerminal)) {
        if (delta.isPresent() && edge.getWeight() > 0) {
          final int newWeight = edge.getWeight() - delta.getAsInt();
          if (newWeight < 0) {
            throw new RuntimeException("this should not happen - fix it");
          }

          final int edgeIndex = getIndex(edge);
          this.graphEdges.get(edgeIndex).setWeight(newWeight);
          if (newWeight == 0) {
            currentGraph.add(edge);
          }
        }
      }

      if (delta.isPresent()) {
        lowerBound += delta.getAsInt();
      }
    }

    System.out.println(lowerBound);
    return null;
  }

  private void constructGraph(final List<Edge> edges) {
    final GraphBuilder<Integer, Integer> builder = GraphBuilder.create();
    for (final Edge edge : edges) {
      builder.connect(edge.getTo()).to(edge.getFrom()).withEdge(edge.getWeight());
    }
    graph = builder.createUndirectedGraph();
  }

  private void printSolutionGraphs(final List<List<Integer>> paths) {
    paths.forEach(path -> System.out.println(Arrays.toString(path.toArray())));
  }

  private int getRandom(final int min, final int max) {
    return random.nextInt(max - min + 1) + min;
  }

  private Set<Edge> getConnectedEdgesToNode(final int node) {
    return this.graphEdges
            .stream()
            .filter(e -> e.getWeight() > 0 && (e.getFrom() == node || e.getTo() == node))
            .collect(Collectors.toSet());
  }

  private int getIndex(final Edge edge) {
    int count = 0;
    for (final Edge e : this.graphEdges) {
      if (e.equals(edge)) {
        return count;
      }
      count++;
    }
    return count;
  }
}
