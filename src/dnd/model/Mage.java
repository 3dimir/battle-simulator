package dnd.model;

import java.util.ArrayList;

import dnd.battle.*;
import dnd.gui.BattleCallback;

public class Mage extends Character {

	private int INT;
	private int fireballSlots = 2;
	private int magicMissileSlots = 3;
	
	
	public Mage(String name, int maxHP, int AC, int INT) {
		super(name, maxHP, AC);
		this.INT = INT;
	}
	
	
	public int getAttackModifier() { return INT; }
	
	
	public void takeTurn(Battle battle, BattleCallback callback) {
		ArrayList<Monster> monsters = battle.getLivingMonsters();
		
		int choice = callback.getTargetChoice(monsters);
		Monster target = monsters.get(choice);
		
		while (true) {
			String[] actions = {"Fireball " + fireballSlots + "/2",
								"Magic Missile " + magicMissileSlots + "/3",
								"Basic Attack"};
			int dmg = 0;
			boolean validAction = false;
			
			switch(callback.getActionChoice(actions)) {
			case 0:
				if (fireballSlots > 0) {
					dmg = Dice.roll(2, 10) + INT;
					fireballSlots--;
					validAction = true;
				} else callback.log("No fireballs left!");
				break;
			case 1:
				if (magicMissileSlots != 0) {
					dmg = Dice.roll(3, 4) + INT;
					magicMissileSlots--;
					validAction = true;
				} else callback.log("No magic missiles left!");
				break;
			case 2:
				int hitCheck = Dice.roll(20) + INT;
				if (hitCheck >= target.getAC())
					dmg = Dice.roll(1, 6) + INT;
				validAction = true;
				break;
			default:
				callback.log("Invalid choice!");
			}
			
			if (validAction) {
				if (dmg > 0) {
					target.takeDamage(dmg);
					callback.log(name + " deals " + dmg + " damage to " + target.getName() + "!");
					if (!target.isAlive()) callback.onDeath(target.getName());
				} else callback.log(name + " missed " + target.getName() + "!");
				break;
			}
		}
	}
}
