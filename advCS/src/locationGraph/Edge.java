package locationGraph;

public class Edge <T> implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5053584663646773500L;
	Vertex v1, v2;
	T data;
	
	public Edge(Vertex a, Vertex b, T i) {
		v1 = a;
		v2 = b;
		data = i;
	}
	
	public Vertex getOpposit(Vertex v) {
		if(v == v1) {
			return v2;
		}else if (v == v2) {
			return v1;
		}
		return null;
	}
	
	public Vertex getA() {
		return v1;
	}
	
	public Vertex getB() {
		return v2;
	}
	
	public String toString() {
		return v1.toString() + "<-" + data.toString() + "->" + v2.toString();
	}
}
