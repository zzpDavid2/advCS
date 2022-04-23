package gameTree;

import java.util.ArrayList;
import java.util.HashSet;

public class CState {
	private int leftC, rightC, leftM, rightM;
	
	private final static int[][] moves = new int[][]{{1,0}, {0,1}, {1,1}};
	
	// true when boat is on the left, false when boat in on the right
	private boolean boatIsLeft; 
	
	public CState(int lc, int lm, int rc, int rm, boolean b) {
		leftC = lc;
		rightC = rc;
		leftM = lm;
		rightM = rm;
		boatIsLeft = b;
	}
	
	public ArrayList<CState> next() {
		ArrayList<CState> nextStates = new ArrayList<CState>();
		for(int[] m : moves) {
			int dir = boatIsLeft ? -1 : 1;
			// delta in lc and lm, the negative number of these will be added to the right
			int dc = m[0] * dir;
			int dm = m[1] * dir;
			// new lc, lm, rc, rm
			int nlc = leftC + dc;
			int nlm = leftM + dm;
			int nrc = rightC - dc;
			int nrm = rightM - dm;
			CState curr = new CState(nlc, nlm, nrc, nrm, !this.boatIsLeft);
//			System.out.println(curr);
			if(curr.isLegal()) nextStates.add(curr);;
		}
		return nextStates;
	}
	
	public boolean isEnd() {
		if(rightC == 3 && rightM == 3) return true;
		return false;
	}
	
	public boolean isLegal() {
		if(leftC < 0 || leftM < 0 || rightC< 0 || rightM < 0) return false;
		if(leftC > leftM && leftM != 0) return false;
		if(rightC > rightM && rightM != 0) return false;
		return true;
	}
	
	public String toString() {
		return "C:" + leftC + " M:" + leftM + 
				" |" + (boatIsLeft ? "L" : "R") + "| " + 
				"C:" + rightC + " M:" + rightM;
	}
	
	
	
	public static ArrayList<CState> solve(CState s, int depthLeft){
		ArrayList<CState> states = new ArrayList<CState>();
		states.add(s);
		return solve(states, new HashSet<CState>(), depthLeft);
	}
	
	public ArrayList<CState> solve(int depthLeft){
		ArrayList<CState> states = new ArrayList<CState>();
		states.add(this);
		return solve(states, new HashSet<CState>(), depthLeft);
	}
	
	private static ArrayList<CState> solve(ArrayList<CState> states, HashSet<CState> visited, int depthLeft){
		// get the last element that is the one we are working on
		CState curr = states.get(states.size()-1);
		visited.add(curr);
		if(curr.isEnd()) {
			return states;
		}
		if(depthLeft <= 0) {
			return null;
		}
		for(CState cs : curr.next()) {
			if(visited.contains(cs)) {
				continue;
			}
			states.add(cs);
			ArrayList<CState> sol = solve(states, visited, depthLeft - 1);
			if(sol != null) {
				return sol;
			}
			states.remove(states.size()-1);
		}
		System.out.println(states);
		return null;
	}
	
	public static void main(String[] args) {
		CState start = new CState(3, 3, 0, 0, true);
		
		System.out.println("Step 1: " + start);
		
		ArrayList<CState> stepTwo = start.next();
		
		System.out.println("Step 2: " + stepTwo);
		
		for(CState cs : stepTwo) {
			System.out.println("Step 3: " + cs.next());
		}
		
		System.out.println(start.hashCode());
		
		System.out.println(start.solve(100));
		
	}
	
	public int hashCode() {
		// prime factor implementation
//		int ret = (int) Math.pow(2, leftC) % Integer.MIN_VALUE;
//		ret = (int) (ret * Math.pow(3, leftM)) % Integer.MIN_VALUE;
//		ret = (int) (ret * Math.pow(5, rightC)) % Integer.MIN_VALUE;
//		ret = (int) (ret * Math.pow(7, rightM)) % Integer.MIN_VALUE;
//		ret = ret * (boatIsLeft ? 0 : 1);
		
		// as digits
		int ret = 1 * leftC;
		ret += 100 * leftM;
		ret += 10000 * rightC;
		ret += 1000000 * rightM;
		ret += 100000000 * (boatIsLeft ? 0 : 1);
		return ret;
	}
	
	public boolean equals(Object other) {
		if (!(other instanceof CState)) return false;
		CState state = (CState) other;
		if(this.leftC == state.leftC && this.leftM == state.leftM && 
				this.rightC == state.rightC && this.rightM == state.rightM && 
				this.boatIsLeft && state.boatIsLeft) {
			return true;
		}
		return false;
	}
	
}
