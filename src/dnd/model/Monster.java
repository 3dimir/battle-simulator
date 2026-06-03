package dnd.model;

import dnd.battle.*;
import dnd.gui.BattleCallback;

public class Monster extends Combatant {

	private int STR;
	
	
	public Monster(String name, int maxHP, int AC, int STR) {
		super(name, maxHP, AC);
		this.STR = STR;
	}
	
	
	public int getAttackModifier() { return STR; }
	
	
	public void takeTurn(Battle battle, BattleCallback callback) {
		Combatant target = battle.getRandomLivingCharacter();
		
		int hitCheck = Dice.roll(20) + STR;
		if (hitCheck >= target.getAC()) {
			int dmg = Dice.roll(1, 6) + STR;
			target.takeDamage(dmg);
			
			callback.log(getName() + " bashed " + target.getName() + " for " + dmg + " damage!");
			if (!target.isAlive()) callback.onDeath(target.getName());
		}
		else callback.log(name + " missed " + target.getName() + "!");
	}
}
