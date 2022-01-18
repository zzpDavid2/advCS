package maze;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class DavidBot extends Bot {
	
	int[][] maze; // 0 is unknown; 1 is blocked; 2 is empty
	boolean[][] visited;
	int tarX;
	int currX;
	int currY;
	int currDir; // 0 is right; 1 is up; 2 is back;  is down
	int[][] Dirs = new int[][]{{1,0},{0,1},{-1,0},{0,-1}};
	
	int[] DirPrio = new int[] {0,1,3,2};
	
	ArrayList<int[]> history = new ArrayList<int[]>();
	int currT;
	ArrayDeque<Boolean> nextSteps = new ArrayDeque<Boolean>(); //true is move; false is turn left

	public DavidBot(MazeRunner mr, Color c) {	
		super(mr, c);
		currX = 0;
		currY = mr.ROWS;
		tarX = mr.COLS;
		
		maze = new int[mr.COLS][mr.ROWS*2];
		visited = new boolean[mr.COLS][mr.ROWS*2];
		
		currT = 0;
		
		maze[currX][currY] = 1;
		for(int i=0; i<mr.ROWS*2;i++) {
			maze[currX][i] = 1;
		}
		
	}

	@Override
	public void move() {
		visited[currX][currY] = true;
		
		if(nextSteps.isEmpty()) {
//			System.out.println("T = " + currT);
			
			//record the last order
			int[] currLoc = new int[]{currX,currY} ;
			history.add(currT, currLoc);
			//new order
			if(!toNew()) {
				toOld();
			}
		}
		
//		System.out.println(nextSteps);
		
		if(nextSteps.pop()) {
			int nextX = currX + Dirs[currDir][0];
			int nextY = currY + Dirs[currDir][1];
			if(moveForward()) {
				currX = nextX;
				currY = nextY;
				
			}else {
//				System.out.println("Blocked");
				maze[nextX][nextY] = 1;
				currT--;
			}
			
		}else {
			turnLeft();
			currDir ++;
			currDir %=4;
		}

	}
	
	public boolean toNew() {
		for(int i : DirPrio) {
			int tarDir = i; 
			int tarX = currX + Dirs[tarDir][0];
			int tarY = currY + Dirs[tarDir][1];
//			System.out.println(tarDir);
			
			if(visited[tarX][tarY] || maze[tarX][tarY] == 1) {
			}else {
				moveTo(tarDir);
//				System.out.println("new: to " + tarDir);
				currT++;
				return true;
			}
		}
		
		return false;
	}
	
	public void toOld() {
//		System.out.println("old");
		currT--;
		for(int i=0; i<4; i++) {
			int tarDir = (currDir + i)%4; 
			
			int tarX = currX + Dirs[tarDir][0];
			int tarY = currY + Dirs[tarDir][1];
			
			int hisX = history.get(currT)[0];
			int hisY = history.get(currT)[1];
			
//			System.out.println(tarX + " " + tarY);
//			System.out.println(hisX + " " + hisY);
			
			if(tarX == hisX && tarY == hisY) {
				moveTo(tarDir);
			}
		}
	}
	
	public void moveTo(int tarDir) {
		int i = currDir;
		while(i != tarDir) {
			i++;
			i %= 4;
			nextSteps.add(false);
		}
		nextSteps.add(true);
	}

}