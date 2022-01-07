import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;

// consulted a pseudocode that I found online for a template to solve this question:
// http://cseweb.ucsd.edu/~kube/cls/100/Lectures/lec12.netflow/lec12-12.html

public class FordFulkerson {

	public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
		ArrayList<Integer> fpath = new ArrayList<Integer>();
		fpath.add(source);

		ArrayList<Integer> seen = new ArrayList<Integer>();

		fpath = dfsRecursive(source, destination, graph, fpath, seen);

		if(fpath.size() != 1) {
			return fpath;

		} else {
			fpath.remove(source);
			return fpath;
		}
	}

	private static boolean inSeen (Integer a, ArrayList<Integer> seen) {
		if(seen.contains(a)) {
			return true;
		} else {
			return false;
		}
	}

	private static ArrayList<Integer> dfsRecursive(Integer source, Integer destination, WGraph graph, ArrayList<Integer> path , ArrayList<Integer> seen){

		if (source == destination) {
			return path ;
		}

		seen.add(source);

		ArrayList<Edge> nodeEdges = graph.getEdges();
		ArrayList<Integer> adj = new ArrayList<Integer>();

		for (Edge edge : nodeEdges) {

			if (edge.weight != 0) {

				if (edge.nodes[0] == source) {
					adj.add(edge.nodes[1]);

				}
			}
		}
		for( Integer a : adj) {

			if (!inSeen(a, seen)) {
				path.add(a);

				path = dfsRecursive(a, destination, graph, path, seen);

				if (path.contains(destination)) {
					return path;
				}

				seen.remove(a);
				path.remove(a);

			}
		}
		return path;
	}

	public static String fordfulkerson(WGraph graph){
		String answer="";
		int maxFlow = 0;

		WGraph finalGr = new WGraph(graph);
		WGraph residual_graph = new WGraph(graph);
		ArrayList<Integer> path;

		for (Edge edge: graph.getEdges()) {

			Edge residualEdge = residual_graph.getEdge(edge.nodes[0], edge.nodes[1]);
			residualEdge.weight = 1;

			if ( residual_graph.getEdge(edge.nodes[1], edge.nodes[0]) == null){

				// initial: set weights in flow graph to zero
				residual_graph.addEdge(new Edge(residualEdge.nodes[1], edge.nodes[0], 0));

			}
		}

		path = pathDFS(residual_graph.getSource(), residual_graph.getDestination(), residual_graph);

		for( Edge e : finalGr.getEdges()) {
			e.weight = 0;
		}

		// while not empty, wont ever be negative
		while (!(path.size() == 0)) {

			 int bottleNeck = Integer.MAX_VALUE;
			 int flow = 0;
			 int lenOfPath = path.size() - 1;

			 // get the edges in the residual graph
			 ArrayList<Edge> allEdges = residual_graph.getEdges();

			 for ( int i = 0; i < lenOfPath ; i++ ) {

			 	if (graph.getEdge(path.get(i), path.get(i+1)) == null) {

					flow = finalGr.getEdge(path.get(i+1), path.get(i)).weight;

			 	} else {

					flow = graph.getEdge(path.get(i), path.get(i+1)).weight - finalGr.getEdge(path.get(i), path.get(i+1)).weight;

			 	}

			 	// find largest bottleneck
			 	if (flow < bottleNeck) {

					bottleNeck = flow;

			 	}
			 }

			 maxFlow += bottleNeck;

			 //update weight if fwd or bkwd edge respectively

			 for (int i = 0; i < lenOfPath; i++) {

				 if (finalGr.getEdge(path.get(i), path.get(i + 1)) == null) {

					 finalGr.getEdge(path.get(i+1), path.get(i)).weight -= bottleNeck;

				 } else {

					 finalGr.getEdge(path.get(i), path.get(i + 1)).weight += bottleNeck;

				 }
			 }

			 // compute edge weights in solution graph
			 for (Edge e: allEdges) {
			 	int first = e.nodes[0];
			 	int last = e.nodes[1];

			 	if (finalGr.getEdge(first,last) == null) {

					if (finalGr.getEdge(last, first).weight <= 0) {
						e.weight = 0;

					} else {
						e.weight = 1;
					}

				} else {

					if (graph.getEdge(first,last).weight - finalGr.getEdge(first,last).weight == 0) {
						e.weight = 0;

					} else {
						e.weight = 1;
					}
			 	}
			 }
			 path = pathDFS(residual_graph.getSource(), residual_graph.getDestination(), residual_graph);
		}
		graph = finalGr;


		answer += maxFlow + "\n" + graph.toString();
		return answer;
	}

	 public static void main(String[] args){
		String file = "ff2.txt";
		File f = new File(file);
		WGraph g = new WGraph(file);
	    System.out.println(fordfulkerson(g));
	 }
}



