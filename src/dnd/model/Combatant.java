package dnd.model;

import dnd.battle.*;
import dnd.gui.BattleCallback;

public abstract class Combatant {

	protected String name;
	protected int HP;
	protected int maxHP;
	protected int AC;
	protected int initiative;
	
	
	public Combatant(String name, int maxHP, int AC) {
		this.name = name;
		this.maxHP = maxHP;
		this.HP = maxHP;
		this.AC = AC;
	}
	
	
	public String getName() { return name; }
	public int getAC() { return AC; }
	public int getHP() { return HP; }
	public int getMaxHP() { return maxHP; }
	public int getInit() { return initiative; }
	
	
	public abstract void takeTurn(Battle battle, BattleCallback callback);
	public void takeDamage(int dmg) { if (isAlive()) HP = Math.max(0, HP - dmg); }
	public boolean isAlive() { return HP > 0; }
	public void rollInitiative() { this.initiative = Dice.roll(20); }
}
