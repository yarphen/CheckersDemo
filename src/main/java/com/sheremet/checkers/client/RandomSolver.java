package com.sheremet.checkers.client;

import java.util.List;
import java.util.Random;

import checkers.pojo.board.Board;
import checkers.pojo.board.StepCollector;
import checkers.pojo.step.Step;

public class RandomSolver implements Solver {
	private StepCollector stepCollector = new StepCollector();
	private Random r = new Random();
	@Override
	public Step solve(Board board) {
		List<Step> steps = stepCollector.getSteps(board);
		int index = r.nextInt(steps.size());
		return steps.get(index);
	}

}
