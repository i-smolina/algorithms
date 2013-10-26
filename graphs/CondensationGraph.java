import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

public class CondensationGraph {
	int v, e;
	ArrayList<LinkedList<Integer>> adjList;
	boolean[] used;
	
	public CondensationGraph(int v) {
		this.v = v;
		adjList = new ArrayList<LinkedList<Integer>>();
		for (int i = 0; i < v; i++)
			adjList.add(new LinkedList<Integer>());
	}
	
	public CondensationGraph(int v, int e) {
		this(v);
		this.e = e;
	}
	
	public CondensationGraph(InputStream in) {
		Scanner scanner = new Scanner(in);
		v = scanner.nextInt(); // the number of the vertices
		e = scanner.nextInt(); // the number of the edges
		scanner.nextLine();
		
		adjList = new ArrayList<LinkedList<Integer>>();
		for (int i = 0; i < v; i++)
			adjList.add(new LinkedList<Integer>());
		
		int v1, v2;
		//Random r = new Random(); // for test
		for (int i = 0; i < e; i++) {
			v1 = scanner.nextInt() - 1;
			v2 = scanner.nextInt() - 1;
			/*v1 = r.nextInt(v);
			v2 = r.nextInt(v);*/
			addEdge(v1, v2);
		}
		scanner.close();
	}
	
	void addEdge(int v1, int v2) {
		if (v1 == v2) return; // without loops
		adjList.get(v1).add(v2);
	}
	
	void addUniqEdge(int v1, int v2) {
		if (v1 == v2) return; // without loops
		for (Integer i : adjList.get(v1))
			if (i == v2) return;
		adjList.get(v1).add(v2);
	}
	
	void dfs(Collection<Integer> col) {
		used = new boolean[v];
		Stack<Integer> stack = new Stack<Integer>();
		for (int i = 0; i < v; i++) {
			if (!used[i]) {
				used[i] = true;
				stack.push(i);
				while (!stack.isEmpty()) {
					int v1 = stack.peek();
					int v2 = getAdjUnusedVertex(v1);
					if (v2 == -1) {
						col.add(stack.pop());
					}
					else {
						used[v2] = true;
						stack.push(v2);
					}
				}
			}
		}
	}
	
	private void dfs_visit(int i, Map<Integer, Integer> map, int numCC) {
		used[i] = true;
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(i);
		while (!stack.isEmpty()) {
			int v1 = stack.peek();
			int v2 = getAdjUnusedVertex(v1);
			if (v2 == -1) {
				map.put(stack.pop(), numCC);
			}
			else {
				used[v2] = true;
				stack.push(v2);
			}
		}
	}
	
	int getAdjUnusedVertex(int v1) {
		for (int v2 : adjList.get(v1))
			if (!used[v2]) {
				return v2;
			}
		return -1;
	}
	
	int getCountEdges() {
		int count = 0;
		for (List<Integer> edges : adjList)
			count += edges.size();
		return count;
	}
		
	public CondensationGraph invert() {
		CondensationGraph invertG = new CondensationGraph(v, e);
		for (int v1 = 0; v1 < v; v1++) {
			List<Integer> edgesV = adjList.get(v1);
			for (int v2 : edgesV)
				invertG.addEdge(v2, v1);
		}
		return invertG;
	}
	
	public CondensationGraph condence() {
		CondensationGraph invG = invert();
		Stack<Integer> order = new Stack<Integer>();
		invG.dfs(order);
		
		used = new boolean[v];
		int numCC = 0; // the number of the component
		Map<Integer, Integer> mapV_CC = new HashMap<Integer, Integer>(); // map: from number of the vertex to number of the component
		
		// get strongly connected components and fill the map
		while (!order.isEmpty()) {
			int v = order.pop();
			if (!used[v]) {
				dfs_visit(v, mapV_CC, numCC);
				numCC++;
			}
		}

		// create condensation of the graph
		CondensationGraph condG = new CondensationGraph(numCC);
		int comp1, comp2;
		for (int v1 = 0; v1 < v; v1++) {
			comp1 = mapV_CC.get(v1);
			for (Integer v2 : adjList.get(v1)) {
				comp2 = mapV_CC.get(v2);
				if (comp1 != comp2)
					condG.addUniqEdge(comp1, comp2);
			}
		}
		return condG;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (List<Integer> ls : adjList)
			sb.append(ls);
		return sb.toString();
	}
	
	/*
	 * Дан ориентированный граф G. Граф может содержать кратные ребра и петли. Найти граф H - конденсацию графа G. 
	 * Граф H не должен содержать петель и кратных ребер. 
	 * Выведите число ребер в графе H.
	 * Входной файл: первая строка содержит два числа 1 <= V <= 10^4; 1 <= E <= 10^5
	 * - число вершин и ребер соответственно.
	 * В следующих строках перечислены пары вершин, соединенных ребрами.
	 * Нумерация вершин с единицы.
	 * Выходной файл: число ребер в конденсации графа G.
	 * Например: 
	 * stdin:
	 * 4 4
	 * 2 1
	 * 3 2
	 * 2 3
	 * 4 3
	 * stdout: 2
	 * Time limit: 2 seconds
	 */
	public static void main(String[] args) {
		CondensationGraph G = new CondensationGraph(System.in);
		CondensationGraph condG = G.condence();
		int n = condG.getCountEdges();
		System.out.println(n);
	}
}
