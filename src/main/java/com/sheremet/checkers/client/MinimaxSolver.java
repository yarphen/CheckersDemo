package com.sheremet.checkers.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import checkers.pojo.board.Board;
import checkers.pojo.board.StepCollector;
import checkers.pojo.checker.CheckerColor;
import checkers.pojo.step.Step;

public class MinimaxSolver implements Solver {

	private static final long LIMIT = 5000000000L;
	private static final long SEARCHLIMIT = 300;
	private final Comparator<Map.Entry<Step, Board>> ASCENDING = new Comparator<Map.Entry<Step,Board>>() {
		@Override
		public int compare(Entry<Step, Board> o1, Entry<Step, Board> o2) {
			int rate1 = euristics.rateBoard(o1.getValue());
			int rate2 = euristics.rateBoard(o2.getValue());
			return Integer.compare(rate1, rate2);
		}
	};
	private final Comparator<Map.Entry<Step, Board>> DESCENDING = new Comparator<Map.Entry<Step,Board>>() {
		@Override
		public int compare(Entry<Step, Board> o1, Entry<Step, Board> o2) {
			return -ASCENDING.compare(o1, o2);
		}
	};
	private StepCollector stepCollector = new StepCollector();
	private Euristics euristics = new Euristics();
	private int depth;
	private int solveCounter;
	private int maxdepth;
	public MinimaxSolver(int depth) {
		this.depth = depth;
	}
	@Override
	public Step solve(Board board) {
		solveCounter = 0;
		maxdepth = 0;
		long startTime = System.currentTimeMillis();
		Step step = null;
		List<Step> foundSteps = findMiniMaxSolution(board, LIMIT).getValue();
		if (!foundSteps.isEmpty()){
			step = foundSteps.get(0);
		}
		System.out.println("Time:"+(System.currentTimeMillis()-startTime));
		System.out.println("SolveCounter:"+solveCounter);
		System.out.println("maxdepth:"+maxdepth);
		solveCounter = 0;
		maxdepth = 0;
		return step;
	}
	private Map.Entry<Integer, List<Step>> findMiniMaxSolution(
			Board board,
			long limit) {
		long startTime =  System.nanoTime();
		long endTime = startTime + limit;
		List<Step> currentLevelSteps = stepCollector.getSteps(board);
		int gotVariantsCount = currentLevelSteps.size();
		if (currentLevelSteps.isEmpty()){
			if (board.get(board.getTurnColor()).isEmpty()){
				if (board.getTurnColor()==CheckerColor.BLACK){
					return new MyEntry<Integer, List<Step>>(100, new LinkedList<Step>());
				}else{
					return new MyEntry<Integer, List<Step>>(-100, new LinkedList<Step>());
				}
			}else{
				return new MyEntry<Integer, List<Step>>(0, new LinkedList<Step>());
			}
		}
		Entry<Integer,List<Step>> miniMax = null;
		Step step = null;
		boolean timedOut = false;
		List<Map.Entry<Step, Board>> listOfResultEntries = new ArrayList<Map.Entry<Step, Board>>();
		currentLevelSteps.forEach(step1->{
			Board newBoard = board.clone();
			newBoard.apply(step1);
			listOfResultEntries.add(new MyEntry<Step, Board>(step1,newBoard));
		});
		Comparator<Map.Entry<Step, Board>> comparator = null;
		comparator = (board.getTurnColor()==CheckerColor.WHITE)?DESCENDING:ASCENDING;
		Collections.sort(listOfResultEntries, comparator);
		for(Map.Entry<Step, Board> stepEntry:listOfResultEntries){
			if (!timedOut&&System.nanoTime()>=endTime){
				timedOut = true;				}
			Board newBoard = stepEntry.getValue();
			if (miniMax==null){
				step = stepEntry.getKey();
				if (!timedOut){
					miniMax = findMiniMaxSolution(newBoard,  
							(int)((endTime-System.nanoTime())/
									gotVariantsCount));
				}else{
					miniMax = new MyEntry<Integer, List<Step>>(
							euristics.rateBoard(newBoard),
							new LinkedList<Step>());
					solveCounter++;
				}

			}else{
				Entry<Integer,List<Step>> alter;
				if (!timedOut){
					alter = findMiniMaxSolution(newBoard, 
							(int)((endTime-System.nanoTime())/
									gotVariantsCount));
				}else{
					alter = new MyEntry<Integer, List<Step>>(
							euristics.rateBoard(newBoard),
							new LinkedList<Step>());
					solveCounter++;
				}
				if (board.getTurnColor()==CheckerColor.BLACK){
					if (alter.getKey() < miniMax.getKey()){
						miniMax = alter;
						step = stepEntry.getKey();
					}
				}else{
					if (alter.getKey() > miniMax.getKey()){
						miniMax = alter;
						step = stepEntry.getKey();
					}
				}
			}

		}
		miniMax.getValue().add(0,step);
		return miniMax;
	}
	private String indent(int i) {
		char[] chars = new char[i];
		Arrays.fill(chars, '\t');
		String text = new String(chars);
		return text;
	}
}

