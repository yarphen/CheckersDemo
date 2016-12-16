package com.sheremet.checkers.client;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import checkers.pojo.board.Board;
import checkers.pojo.board.StepCollector;
import checkers.pojo.checker.Checker;
import checkers.pojo.checker.Position;
import checkers.pojo.step.Step;

public class UserSolver implements Solver{
	private static final long TIME_LIMIT = 1000*60;
	private BoardRenderer renderer;
	private StepCollector collector = new StepCollector();
	public UserSolver(BoardRenderer boardRenderer) {
		renderer = boardRenderer;
	}
	@Override
	public Step solve(Board board) {
		List<Step> listResult = collector.getSteps(board);
		while(true){
			Position clickPosition = renderer.getNextClickPosition();
			Checker checker = board.get(clickPosition);
			if (checker!=null){
				Map<Position, Step> mapOfFinalPositions = collectFinalPositionsToMap(listResult, clickPosition);
				renderer.render(mapOfFinalPositions.keySet());
				clickPosition = renderer.getNextClickPosition();
				if (mapOfFinalPositions.containsKey(clickPosition)){
					return mapOfFinalPositions.get(clickPosition);
				}
			}
			renderer.render(board);
		}
	}
	private Map<Position, Step> collectFinalPositionsToMap(List<Step> listResult, Position start) {
		Map<Position, Step> mapResult = new HashMap<Position,Step>();
		listResult.stream()
		.filter(item->item.getSteps().get(0).getFrom().isSame(start))
		.forEach(step->{
			mapResult.put(step.getSteps().get(step.getSteps().size()-1).getTo(), step);
		});
		return mapResult;
	}

}
