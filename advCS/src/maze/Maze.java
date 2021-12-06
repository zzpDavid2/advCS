package maze;

import java.util.ArrayList;

public class Maze {
	boolean[][] maze; // the 2d boolean array that represent the maze
	
	//user inputs, in this order
	int width;
	int height;
	int boringness; // one in n chance that the maze splits into two ways
	boolean terminateWhenEnded; // Whether to stop the route when it has reached the right
	int animationTime;
	
	int startHeight;
	int endHeight;
	int[][] directions = new int [][] {{0,1},{0,-1},{1,0},{-1,0}};
	ArrayList<int[]> available = new ArrayList<int []>();
	
	boolean ended;
	
	DrawMaze dm;
	
	public static void main(String[] args) throws InterruptedException {
		//tests a 50 * 50 maze that is not too boring with not too fast animation
		Maze m = new Maze(50, 50, 10, true, 5);
		boolean[][] maze = m.generate();
	}
	
	public Maze(int w, int h, int b, boolean t, int a) {
		width = w;
		height = h;
		boringness = b;
		terminateWhenEnded = t;
		animationTime = a;
		
		ended = false;
		
		maze = new boolean[h][w];	
		dm = new DrawMaze(maze); // Start the graphics
	}
	
	public boolean[][] generate() throws InterruptedException{
		
		dm.update(maze);
//		printMaze();
		
		startHeight = (int) (Math.random()*(height-2)+1);
		int[] start = new int[] {1, startHeight};
		
		go(start);	
		
		//poke through the boundary
		maze[startHeight][0] = true;
		maze[endHeight][width-1] = true;
		
		if(!ended) {
			System.out.println("restarted");
			maze = new boolean[height][width];
			dm.update(maze);
			return generate();
		}
//		printMaze();
		
		return maze;
	}
	
	// the recursive function that generate the maze depth first
	private void go(int[] current) throws InterruptedException {
		
		//sleeps to control the speed to the animation
		Thread.sleep(animationTime);
		
		int x = current[0];
		int y = current[1];
		
		// check if is out of bound before we do anything
		if(isOutOfBound(current)) return;
		
		//check if we have reached the right side
		if(x == width - 2) {
			ended = true;
			endHeight = y;
			maze[y][x] = true;
			if(terminateWhenEnded) return;
		}
		
		System.out.println("current: " + x + " " + y);
		System.out.print(isOutOfBound(current) + " " );
		System.out.print(maze[y][x]+ " ");
		System.out.println(willClump(current));
		
		//check if is already went to or will clump;
		if(maze[y][x] || willClump(current)) return;
		
		maze[y][x] = true;
		
		dm.update(maze);
		
		ArrayList<int[]> neighbors = new ArrayList<int[]>();
		
		System.out.println("neighbors: ");
		//find all available neighbors and put into the neighbors array
		for(int i=0; i<directions.length; i++) {
			
			int nextX = x+directions[i][0];
			int nextY = y+directions[i][1];
			
			int[] neighbor = new int[] {nextX, nextY};
			
//			System.out.print(isOutOfBound(neighbor) + " " );
//			System.out.print(maze[nextY][nextX]+ " ");
//			System.out.print(willClump(neighbor, current));
			
			if(isOutOfBound(neighbor) || maze[nextY][nextX] || willClump(neighbor)) continue;
				
//			System.out.println(maze[nextY][nextX]+ " ");
			System.out.println(x+directions[i][0] + " " + (int)(y+directions[i][1]));
			
			neighbors.add(neighbor);
		}
		
		if(neighbors.isEmpty()) return;
		
		//randomly chose one from the neighbors array to goto
		int[] chosen = neighbors.remove((int) (Math.random()*neighbors.size()));
		
		System.out.println("Chosen one: " + chosen[0] + " " + chosen[1]);
		
		go(chosen);
		
		//decide if splits
		for(int[] n : neighbors) {
			// the higher the boringness the less likely it splits
			if((int) (Math.random()*boringness) == 0) {
				go(n);
			}
		}
		
	}
	
	//check if there will be more then 1 tile around a coordinate
	private boolean willClump(int[] tar) {
		
		int count = 0;
		
		for(int i=0; i < 4; i++) {
			
			int x = tar[0] + directions[i][0];
			int y = tar[1] + directions[i][1];
			
			if(maze[y][x]) count ++;
		}
		
		return count > 1;
	}
	
	//checks if a coordinate is out of bound
	private boolean isOutOfBound(int[] n) {
		
		int x = n[0];
		int y = n[1];
		
		if(x<=0 || x >= width-1) return true;
		if(y<=0 || y >= height-1) return true;
		
		return false;
	}
	
	//prints the maze in the console
	public void printMaze() {
		
		for(int i=0; i<maze.length;i++) {
			for(int j=0; j<maze[0].length; j++) {
				System.out.print(maze[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
