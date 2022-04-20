package gameTree;

import java.util.ArrayList;

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
	
	public static void main(String[] args) {
		CState start = new CState(3, 3, 0, 0, true);
		
		System.out.println("Step 1: " + start);
		
		ArrayList<CState> stepTwo = start.next();
		
		System.out.println("Step 2: " + stepTwo);
		
		for(CState cs : stepTwo) {
			System.out.println("Step 3: " + cs.next());
		}
		
		
	}
	
}
