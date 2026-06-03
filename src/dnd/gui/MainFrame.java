package dnd.gui;

import javax.swing.*;
import java.awt.*;

import dnd.battle.*;

@SuppressWarnings("serial")
public class MainFrame extends JFrame{

	private CardLayout cardLayout = new CardLayout();
	private JPanel mainPanel = new JPanel(cardLayout);
	
	private CreationPanel creationPanel;
	private BattlePanel battlePanel;
	
	
	public MainFrame() {
		setTitle("D&D Battle Simulator");
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		creationPanel = new CreationPanel(this);
		battlePanel = new BattlePanel(this);
		
		mainPanel.add(creationPanel, "CHARACTER");
		mainPanel.add(battlePanel, "BATTLE");
		
		add(mainPanel);
		setVisible(true);
	}
	
	
	public void showBattle(Battle battle) {
		battlePanel.startBattle(battle);
		cardLayout.show(mainPanel, "BATTLE");
	}
}
