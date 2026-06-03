package dnd.battle;

import java.util.ArrayList;
import java.util.Random;

import dnd.gui.BattleCallback;
import dnd.model.*;
import dnd.model.Character;	//	otherwise Character is ambiguous 

public class Battle {

	private ArrayList<Combatant> combatants = new ArrayList<>();
	private ArrayList<Character> characters = new ArrayList<>();
	private ArrayList<Monster> monsters = new ArrayList<>();
	private Random rd = new Random();
	
	
	//	generic helper method for extracting living beings
	private <T extends Combatant> ArrayList<T> getLiving(ArrayList<T> list) {
		ArrayList<T> alive = new ArrayList<>();
		for (T e : list)
			if (e.isAlive()) alive.add(e);
		return alive;
	}
	
	//	wildcard helper method for determining if all beings are dead
	private boolean allDead(ArrayList<? extends Combatant> list) {
		for (Combatant e : list)
			if (e.isAlive()) return false;
		return true;
	}
	
	private boolean isOver() {
		return allDead(characters) || allDead(monsters);
	}
	
	
	//	party health update
	public ArrayList<Combatant> getParty() { return combatants; }
	
	public void addCharacter(Character c) {
		combatants.add(c);
		characters.add(c);
	}
	
	public void addMonster(Monster m) {
		combatants.add(m);
		monsters.add(m);
	}
	
	public void rollInitiative() {
		for (Combatant e : combatants) {
			e.rollInitiative();
		}
	}
	
	public void sortByInitiative() { combatants.sort((e1, e2) -> e2.getInit() - e1.getInit()); }
	
	public ArrayList<Monster> getLivingMonsters() { return getLiving(monsters); }
	
	public Character getRandomLivingCharacter() {
		ArrayList<Character> alive = getLiving(characters);
		return alive.get(rd.nextInt(alive.size()));
	}
	
	public Monster getRandomLivingMonster() {
		ArrayList<Monster> alive = getLiving(monsters);
		return alive.get(rd.nextInt(alive.size()));
	}
	
	public void run(BattleCallback callback) {
		rollInitiative();
		sortByInitiative();
		
		for (Combatant e : combatants)
			callback.showInitiative(e.getName(), e.getInit());
		
		while(!isOver()) {
			for (Combatant e : combatants)
				if (isOver()) break;
				else if (e.isAlive()) {
					callback.log("\n== " + e.getName() + "'s turn! ==");
					e.takeTurn(this, callback);
				}
		}
		
		if(allDead(monsters)) callback.showResult("Victory!");
		else callback.showResult("Defeat!");
	}
}
