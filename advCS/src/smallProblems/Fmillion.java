package smallProblems;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

public class Fmillion {
	
	public static void main(String[] args) throws IOException {
		int percision = 10;
		
		boolean done = false;
		
		BigInteger Fmillion = BigInteger.ZERO;
		
		PrintWriter out = new PrintWriter("Fmillion");
		
		while(!done) {
			MathContext mc = new MathContext(percision, RoundingMode.HALF_DOWN);
			
			
			BigDecimal rootFive = new BigDecimal(5);
			rootFive = rootFive.sqrt(mc);
			
			BigDecimal a = BigDecimal.ONE.divide(rootFive, mc);
			
			 int p = 100;
			
			BigDecimal r = rootFive.add(BigDecimal.ONE);
			r = r.divide(BigDecimal.valueOf(2.0), mc);
			
			out.println(r);
			
			BigDecimal b = r.pow(p);
			
			out.println(b);
			
			BigDecimal op = a.multiply(b, mc);
			
			out.println(op);
			
			if(Fmillion.equals(op.toBigInteger())) {
				done = true;
				break;
			}
			
			Fmillion = op.toBigInteger();
			
			percision *= 2;
			
			out.println(Fmillion + " " + percision);

		}
		
		out.println("The millionth number in the Fibonacci sequence is " + Fmillion);
		
		out.close();
	}
			
}
