package gameTree;

import java.util.ArrayList;
import java.util.HashSet;

public class CState {
	private int leftC, rightC, leftM, rightM;
	
	private static int c, m, b;
	
//	private final static int[][] moves = new int[][]{{1,0}, {0,1}, {1,1}};
	
	// true when boat is on the left, false when boat in on the right
	private boolean boatIsLeft; 
	
	public CState(int lc, int lm, int rc, int rm, boolean b) {
		leftC = lc;
		rightC = rc;
		leftM = lm;
		rightM = rm;
		boatIsLeft = b;
	}
	
	public void setConditions(int c, int m, int b) {
		this.c = c;
		this.m = m;
		this.b = b;
	}
	
	public ArrayList<CState> next() {
		ArrayList<CState> nextStates = new ArrayList<CState>();
		for(int i=0; i<=b; i++) {
			for(int j=0; j<=b-i; j++) {
				// invalid if no one is on the boat
				if(i==0 && j==0) continue;
				if(i>j && j!=0) continue;
//				System.out.println(i + " " + j);
				
				int dir = boatIsLeft ? -1 : 1;
				// delta in lc and lm, the negative number of these will be added to the right
				int dc = i * dir;
				int dm = j * dir;
				// new lc, lm, rc, rm
				int nlc = leftC + dc;
				int nlm = leftM + dm;
				int nrc = rightC - dc;
				int nrm = rightM - dm;
				CState curr = new CState(nlc, nlm, nrc, nrm, !this.boatIsLeft);
//				System.out.println(curr);
				if(curr.isLegal()) nextStates.add(curr);;
			}	
		}
//		for(int[] m : moves) {
//			int dir = boatIsLeft ? -1 : 1;
//			// delta in lc and lm, the negative number of these will be added to the right
//			int dc = m[0] * dir;
//			int dm = m[1] * dir;
//			// new lc, lm, rc, rm
//			int nlc = leftC + dc;
//			int nlm = leftM + dm;
//			int nrc = rightC - dc;
//			int nrm = rightM - dm;
//			CState curr = new CState(nlc, nlm, nrc, nrm, !this.boatIsLeft);
////			System.out.println(curr);
//			if(curr.isLegal()) nextStates.add(curr);;
//		}
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
			System.out.println(states);
			ArrayList<CState> sol = solve(states, visited, depthLeft - 1);
			if(sol != null) {
				return sol;
			}
			states.remove(states.size()-1);

		}
		
		return null;
	}
	
	public static void main(String[] args) {
		CState start = new CState(5, 4, 0, 0, true);
		
//		start.setConditions(3, 3, 2);
		start.setConditions(5, 4, 2);
//		start.setConditions(1, 1, 2);
		
//		System.out.println("Step 1: " + start);
//		
//		ArrayList<CState> stepTwo = start.next();
//		
//		System.out.println("Step 2: " + stepTwo);
//		
//		for(CState cs : stepTwo) {
//			System.out.println("Step 3: " + cs.next());
//		}
		
		System.out.println(new CState(1,1,1,1,true).equals(new CState(1,1,1,1,true)));
		
		System.out.println(start.hashCode());
		
//		System.out.println(new CState(2, 0, 3, 4, false).next());
		
		System.out.println(start.solve(10000));
		
	}
	
	public int hashCode() {
		// prime factor implementation
//		int ret = (int) Math.pow(2, leftC) % Integer.MIN_VALUE;
//		ret = (int) (ret * Math.pow(3, leftM)) % Integer.MIN_VALUE;
//		ret = (int) (ret * Math.pow(5, rightC)) % Integer.MIN_VALUE;
//		ret = (int) (ret * Math.pow(7, rightM)) % Integer.MIN_VALUE;
//		ret = ret * (boatIsLeft ? 0 : 1);
		
		// as digits
		int ret = 1 * (boatIsLeft ? 0 : 1);
		ret += 10 * (leftC % 100);
		ret += 1000 * (leftM % 100);
		ret += 100000 * (rightC % 100);
		ret += 10000000 * (rightM % 100);
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
