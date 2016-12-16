package com.sheremet.checkers.client;

import java.awt.Color;
import java.util.Set;

import checkers.pojo.board.Board;
import checkers.pojo.checker.Checker;
import checkers.pojo.checker.CheckerType;
import checkers.pojo.checker.Position;
import edu.princeton.cs.introcs.StdDraw;
/**
 * Created by mykhaylo sheremet on 11.12.2016.
 */
public class BoardRenderer{

	private static final int CELLS = 8;
	private static final long SLEEP_TIME = 0;

	public BoardRenderer() {
		StdDraw.setXscale(0, 8);
		StdDraw.setYscale(0, 8);
		drawBoard();
	}

	private void drawBoard() {
		for(int i=0; i<CELLS; i++){
			for(int j=0; j<CELLS; j++){
				if ((i+j)%2==0){
					StdDraw.setPenColor(Color.BLACK);
				}else{
					StdDraw.setPenColor(Color.WHITE);
				}
				StdDraw.filledRectangle(i+0.5, j+0.5, 0.5, 0.5);
			}	
		}
	}

	public void render(Board board) {
		drawBoard();
		board.getCheckers().forEach(this::renderChecker);
	}
	private void renderChecker(Checker checker){
		Position position = checker.getPosition();
		double x = position.getLetter().getValue()-0.5;
		double y = position.getNumber().getValue()-0.5;
		StdDraw.setPenColor(Color.WHITE);
		switch (checker.getColor()) {
		case BLACK:
			StdDraw.circle(x, y, 0.5);
			break;
		case WHITE:
			StdDraw.filledCircle(x, y, 0.5);
			StdDraw.setPenColor(Color.BLACK);
			break;
		}
		if (checker.getType()==CheckerType.QUEEN){
			StdDraw.filledCircle(x, y, 0.2);
		}
	}

	public void showMsg(String msg) {
		StdDraw.setPenColor(Color.DARK_GRAY);
		StdDraw.filledRectangle(4, 4, 4, 1);
		StdDraw.setPenColor(Color.WHITE);
		StdDraw.text(4, 4, msg);
	}

	public void render(Set<Position> availableSteps) {
		availableSteps.forEach(this::renderAvailableStep);
	}
	private void renderAvailableStep(Position step) {
		double x = step.getLetter().getValue()-0.5;
		double y = step.getNumber().getValue()-0.5;
		StdDraw.setPenColor(Color.RED);
		StdDraw.circle(x, y, 0.1);
	}
	public Position getNextClickPosition() {
		while(true){
			while(!StdDraw.mousePressed()){
				try {Thread.sleep(SLEEP_TIME);} catch (InterruptedException e) {}
			}
			double x = StdDraw.mouseX();
			double y = StdDraw.mouseY();
			Position p =  new Position((int)(x+1), (int)(y+1));
			if (p.getLetter()!=null&&p.getNumber()!=null){
				while(StdDraw.mousePressed()){
					try {Thread.sleep(SLEEP_TIME);} catch (InterruptedException e) {}
				}
				return p;
			}
		}
	}
}
