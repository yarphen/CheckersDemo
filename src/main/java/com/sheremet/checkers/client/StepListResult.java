package com.sheremet.checkers.client;

import java.util.List;
import java.util.Map.Entry;

import checkers.pojo.board.Board;
import checkers.pojo.step.Step;

public class StepListResult {
	private final boolean finished;
	private final List<Entry<Step,Board>> list;
	public StepListResult(boolean finished, List<Entry<Step, Board>> list) {
		super();
		this.finished = finished;
		this.list = list;
	}
	public boolean isFinished() {
		return finished;
	}
	public List<Entry<Step, Board>> getList() {
		return list;
	}
	
}
