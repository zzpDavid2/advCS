package maze;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.TimeUnit;

public class Maze {
	boolean[][] maze;
	int width;
	int height;
	int startHeight;
	int endHeight;
	int[][] neighbors = new int [][] {{0,1},{0,-1},{1,0},{-1,0}};
	
	DrawMaze dm;
	
	public static void main(String[] args) throws InterruptedException {
		Maze m = new Maze(30,30);
		m.generate();
	}
	
	public Maze(int w, int h) {
		width = w;
		height = h;
		maze = new boolean[h][w];
		startHeight = (int) (Math.random()*height);
		dm = new DrawMaze(maze);
	}
	
	public boolean[][] generate() throws InterruptedException{

		boolean ended = false;

		//create boarder
		for(int i=0;i<width; i++) {
			maze[0][i] = true;
			maze[height-1][i] = true;
		}
		for(int i=0;i<height; i++) {
			maze[i][0] = true;
			maze[i][width-1] = true;
		}
		
//		Thread.sleep(500);
		
		dm.update(maze);
		printMaze();
		
		Deque<int[]> dq = new ArrayDeque<int[]>(); 
		
		int[] start = new int[] {1, startHeight};
		
		dq.add(start);
		
		while(!dq.isEmpty()) {
			
			int[] current = dq.pop();
			int x = current[0];
			int y = current[1];
			
			if(x == width - 1) {
				ended = true;
				endHeight = y;
				if(ended) {
					break;
				}
			}
			
			if(maze[y][x]) {
				continue;
			}
			
			System.out.println(x + " " + y);
			
			maze[y][x] = true;
			
			dm.update(maze);
			
			int direction = (int) (Math.random()*4);
			if(direction > 2) direction = 2;
			
			int[] chosen = new int[] {x+neighbors[direction][0], y+ neighbors[direction][1]};
			dq.add(chosen);
			
			//decide if splits
			if(Math.random()*5 == 0) {
				for(int i=0; i<3;i++) {
					if(Math.random() < 1.0/4) continue;
					int[] next = new int[] {x+neighbors[i][0], y+ neighbors[i][1]};
					dq.add(next);
					
				}
			}
			
			if(dq.isEmpty()) break;
		}
		
		//create boarder
		for(int i=0;i<width; i++) {
			maze[0][i] = false;
			maze[height-1][i] = false;
		}
		for(int i=0;i<height; i++) {
			maze[i][0] = false;
			maze[i][width-1] = false;
		}
		
		maze[startHeight][0] = true;
		maze[endHeight][width-1] = true;
		
		if(!ended) {
			maze = new boolean[height][width];
			dm.update(maze);
			return generate();
		}
		
		return maze;
	}
	
	public void printMaze() {
		for(int i=0; i<10;i++) {
			for(int j=0; j<10; j++) {
				System.out.print(maze[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
