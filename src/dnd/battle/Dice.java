package dnd.battle;

import java.util.Random;

public class Dice {
	
	private static final Random rd = new Random();
	
	
	//	single die
	public static int roll(int sides) {
		return rd.nextInt(sides) + 1;
	}
	
	
	//	multiple dice
	public static int roll(int count, int sides) {
		int total = 0;
		for (int i = 0; i < count; i++)
			total += rd.nextInt(sides) + 1;
			
		return total;
	}
}
