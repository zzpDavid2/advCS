package GroupActivity;

public class ProblemTwo {
    public static void main(String[] args){
    	boolean[] z = {true, true, false, true, false, false, true};
//    	boolean[] z = {true, false, false, false, false, false, false};
//    	boolean[] z = {true, true, true, true, true, true, false};
    	
    	System.out.println(solve(z));

    }
    
    public static int solve(boolean[] z) {
    	int n = z.length;
    	
    	int[] left = new int[n];
    	int[] right = new int[n];
    	
    	boolean solvable = false;
    	
    	int d = n+1;
    	
    	for(int i=0; i<n; i++) {
    		if(z[i]==false) {
    			d=0;
    			//Only need to do this in one loop
    			if(!solvable) {
    				solvable = true;
    			}
    		}
    		
    		left[i] = d;
    		
    		if(d!=n+1) {
    			d++;
    		}
        	System.out.print(left[i] + " ");
    	}
    	
    	if(!solvable) {
    		return -1;
    	}
    	
    	System.out.println();
    	
    	d=n+1;
    	
    	for(int i=n-1; i>=0; i--) {
    		if(z[i]==false) {
    			d=0;
    		}
    		
    		right[i] = d;
    		
    		if(d!=n+1) {
    			d++;
    		}
        	System.out.print(right[i] + " ");
    	}
    	
    	int[] result = new int[n];
    	int op=0;
    	for(int i=0; i<n; i++) {
    		result[i] = Math.min(left[i], right[i]);
    		op = Math.max(op, result[i]) ;
    	}	
    	System.out.println();
    	return op;
    }
}
