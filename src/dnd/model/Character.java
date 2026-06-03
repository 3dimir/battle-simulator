package dnd.model;

public abstract class Character extends Combatant {
	
	public Character(String name, int maxHP, int AC) {
		super(name, maxHP, AC);
	}
	
	
	public abstract int getAttackModifier();
}
