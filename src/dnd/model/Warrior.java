package dnd.model;

import java.util.ArrayList;

import dnd.battle.*;
import dnd.gui.BattleCallback;

public class Warrior extends Character {

	
	private int STR;
	
	
	public Warrior(String name, int maxHP, int AC, int STR) {
		super(name, maxHP, AC);
		this.STR = STR;
	}
	
	
	public int getAttackModifier() { return STR; }
	
	
	public void takeTurn(Battle battle, BattleCallback callback) {
		ArrayList<Monster> monsters = battle.getLivingMonsters();
		
		int choice = callback.getTargetChoice(monsters);
		Monster target = monsters.get(choice);
		
		int hitCheck = Dice.roll(20) + STR;
		if (hitCheck >= target.getAC()) {
			int dmg = Dice.roll(1, 8) + STR;
			target.takeDamage(dmg);
			
			callback.log(name + " strikes " + target.getName() + " for " + dmg + " damage!");
			if (!target.isAlive()) callback.onDeath(target.getName());
		}
		else callback.log(name + " missed " + target.getName() + "!");
	}
}
