package GroupActivity;

public class ProblemOne {
    public static void main(String[] args){
    	int[][] A = {{5, 3, -1}, {-2, 4, 0}};
    	int[][] B = {{3, -3, 6}, {2, -4, 0}};
//    	int[][] A = {{0, 0, 0}, {0, 0, 0}};
//    	int[][] B = {{0, 0, 0}, {0, 0, 0}};
//    	int[][] A = {{1, 1, 1}, {1, 1, 1}};
//    	int[][] B = {{1, 1, 1}, {1, 1, 1}};
    	
    	System.out.print(solve(A, B));

    }
    
    public static int solve(int[][] A, int[][] B) {
    	int m = A.length;
    	int n = A[0].length;
    	
    	boolean[] opM = new boolean[m];
    	boolean[] opN = new boolean[n];
    	
    	int op=m+n;
    	
    	for(int i=0; i<m; i++) {
    		for(int j=0; j<n; j++) {
    			if(opM[i] && opN[j]) {
    				continue;
    			}
    			int sum = A[i][j] + B[i][j];
    			if(sum != 0) {
    				if(!opM[i]) {
    					op--;
        				opM[i] = true;
//        				System.out.println(i + " " + j + " M");
    				}
    				if(!opN[j]) {
    					op--;
        				opN[j] = true;
//        				System.out.println(i + " " + j + " N");
    				}
    			}
    		}
    	}
    	
    	
    	
    	return op;
    }
    
}
