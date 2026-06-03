package dnd.model;

import java.util.ArrayList;

import dnd.battle.*;
import dnd.gui.BattleCallback;

public class Rogue extends Character {

	private int DEX;
	
	
	public Rogue(String name, int maxHP, int AC, int DEX) {
		super(name, maxHP, AC);
		this.DEX = DEX;
	}
	
	
	public int getAttackModifier() { return DEX; }
	
	
	public void takeTurn(Battle battle, BattleCallback callback) {
		ArrayList<Monster> monsters = battle.getLivingMonsters();
		
		int choice = callback.getTargetChoice(monsters);
		Monster target = monsters.get(choice);
		
		while (true) {
			String[] actions = {"Sneak Attack", "Basic Attack"};
			int dmg = 0;
			boolean validAction = false;
			
			switch (callback.getActionChoice(actions)) {
			case 0:
				if (target.getHP() == target.getMaxHP() || target.getHP() <= target.getMaxHP() * 0.2) {
					dmg = Dice.roll(2, 6) + DEX;
					validAction = true;
				} else callback.log("Sneak Attack unavailable on this target!");
				break;
			case 1:
				int hitCheck = Dice.roll(20) + DEX;
				if (hitCheck >= target.getAC())
					dmg = Dice.roll(1, 6) + DEX;
				validAction = true;
				break;
			default:
				callback.log("Invalid choice!");
			}
			
			if (validAction) {
				if (dmg > 0) {
					target.takeDamage(dmg);
					callback.log(name + " slashes " + target.getName() + " for " + dmg + " damage!");
					if (!target.isAlive()) callback.onDeath(target.getName());
				} else callback.log(name + " missed " + target.getName() + "!");
				break;
			}
		}
	}
}
