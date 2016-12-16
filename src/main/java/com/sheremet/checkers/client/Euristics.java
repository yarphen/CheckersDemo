package com.sheremet.checkers.client;

import java.util.stream.Collectors;

import checkers.pojo.board.Board;
import checkers.pojo.checker.Checker;
import checkers.pojo.checker.CheckerColor;

public class Euristics {
	public int rateBoard(Board board){
		double whiteSum = board.get(CheckerColor.WHITE).stream().collect(Collectors.summarizingInt(this::rateChecker)).getSum();
		double blackSum = board.get(CheckerColor.BLACK).stream().collect(Collectors.summarizingInt(this::rateChecker)).getSum();
		double sign = (int) Math.signum(whiteSum-blackSum);
		double min = Math.min(blackSum, whiteSum);
		double max = Math.max(blackSum, whiteSum);
		int rate = (int) ( sign * (1.0 - min / max) * 100.0);
		return rate;
	}
	private int rateChecker(Checker checker){
		switch (checker.getType()) {
		case QUEEN:
			return 20;
		case SIMPLE:
			switch (checker.getColor()) {
			case BLACK:
				return 9-checker.getPosition().getY();
			case WHITE:
				return checker.getPosition().getY();
			}
		default:
			throw new IllegalArgumentException();
		}
	}
}
