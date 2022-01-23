package com.samabcde.puzzlesolver.component;

record Position(int x, int y) {

	@Override
	public String toString() {
		return "y:" + y + ",x:" + x;
	}
}