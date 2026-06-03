package dnd.gui;

import java.util.ArrayList;

import dnd.model.Monster;

public interface BattleCallback {
	
	int getTargetChoice(ArrayList<Monster> targets);
	int getActionChoice(String[] actions);
	void showResult(String message);
	void showInitiative(String name, int initiative);
	void onDeath(String name);
	void log(String message);
}
