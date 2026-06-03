package dnd.gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import dnd.battle.*;
import dnd.model.*;
import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class CreationPanel extends JPanel {
	
	private MainFrame frame;
	private String selectedClass = null;
	
	private JTextField nameField, hpField, acField, statField;
	private JButton warriorBtn, mageBtn, rogueBtn, rollBtn, beginBtn;
	private JLabel statLabel;
	

	public CreationPanel(MainFrame frame) {
		this.frame = frame;
		setLayout(new BorderLayout());
		
		add(buildTitlePanel(), BorderLayout.NORTH);
		add(buildFormPanel(), BorderLayout.CENTER);
		add(buildBeginPanel(), BorderLayout.SOUTH);
	}
	
	
	private JPanel buildTitlePanel() {
		JPanel panel = new JPanel();
		JLabel title = new JLabel("Character Creation");
		
		title.setFont(title.getFont().deriveFont(Font.BOLD));
		panel.add(title);
		return panel;
	}
	
	private JPanel buildFormPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		panel.add(buildClassButtons());
		panel.add(buildFields());
		return panel;
	}
	
	private JPanel buildClassButtons() {
		JPanel panel = new JPanel(new FlowLayout());
		panel.setMaximumSize(new Dimension(600, 50));
		
		warriorBtn = new JButton("Warrior");
		mageBtn = new JButton("Mage");
		rogueBtn = new JButton("Rogue");
		
		warriorBtn.setPreferredSize(new Dimension(120, 40));
		mageBtn.setPreferredSize(new Dimension(120, 40));
		rogueBtn.setPreferredSize(new Dimension(120, 40));
		
		warriorBtn.addActionListener(e -> {
			selectedClass = "Warrior";
			statLabel.setText("STR");
		});
		
		mageBtn.addActionListener(e -> {
			selectedClass = "Mage";
			statLabel.setText("INT");
		});
		
		rogueBtn.addActionListener(e -> {
			selectedClass = "Rogue";
			statLabel.setText("DEX");
		});
		
		panel.add(warriorBtn);
		panel.add(mageBtn);
		panel.add(rogueBtn);
		return panel;
	}
	
	private JPanel buildRow(JLabel text, JComponent field) {
		JPanel row = new JPanel(new BorderLayout(10, 0));
		
		text.setPreferredSize(new Dimension(60, 30));
		
		row.add(text, BorderLayout.WEST);
		row.add(field, BorderLayout.CENTER);
		row.setMaximumSize(new Dimension(400, 35));
		return row;
	}
	
	private JPanel buildFields() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		nameField = new JTextField();
		hpField = new JTextField();
		acField = new JTextField();
		statField = new JTextField();
		statLabel = new JLabel("STAT");
		rollBtn = new JButton("Roll");
		
		rollBtn.addActionListener(e -> statField.setText(String.valueOf(Dice.roll(6))));
		
		JPanel statRow = new JPanel(new BorderLayout());
		statRow.add(statField, BorderLayout.CENTER);
		statRow.add(rollBtn, BorderLayout.EAST);
		statRow.setMaximumSize(new Dimension(400, 35));
		
		panel.add(buildRow(new JLabel("Name"), nameField));
		panel.add(buildRow(new JLabel("HP"), hpField));
		panel.add(buildRow(new JLabel("AC"), acField));
		panel.add(buildRow(statLabel, statRow));
		return panel;
	}
	
	private JPanel buildBeginPanel() {
		JPanel panel = new JPanel();
		beginBtn = new JButton("Into the fray!");
		beginBtn.setPreferredSize(new Dimension(200, 40));
		
		beginBtn.addActionListener(e -> {
			if (selectedClass == null) {
				JOptionPane.showMessageDialog(this, "Please select a class!");
				return;
			}
			
			if (nameField.getText().trim().isEmpty() ||
				hpField.getText().trim().isEmpty() ||
				acField.getText().trim().isEmpty() ||
				statField.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Please fill in all fields!");
				return;
			}
			
			try {
				String name = nameField.getText().trim();
				int hp = Integer.parseInt(hpField.getText().trim());
				int ac = Integer.parseInt(acField.getText().trim());
				int stat = Integer.parseInt(statField.getText().trim());
				
				Battle battle = new Battle();
				
				switch (selectedClass) {
				case "Warrior": battle.addCharacter(new Warrior(name, hp, ac, stat)); break;
				case "Mage": 	battle.addCharacter(new Mage(name, hp, ac, stat)); break;
				case "Rogue": 	battle.addCharacter(new Rogue(name, hp, ac, stat)); break;
				}
				
				File file = new File("resources/battle_setup.txt");
				Scanner sc = new Scanner(file);
				
				while (sc.hasNext()) {
					String line = sc.nextLine().trim();
					if (line.isEmpty()) continue;
					
					String[] parts = line.split(",");
					String n = parts[1];
					int h = Integer.parseInt(parts[2]);
					int a = Integer.parseInt(parts[3]);
					int s = Integer.parseInt(parts[4]);
					
					switch (parts[0]) {
					case "Warrior": battle.addCharacter(new Warrior(n, h, a, s)); break;
					case "Mage": 	battle.addCharacter(new Mage(n, h, a, s)); break;
					case "Rogue": 	battle.addCharacter(new Rogue(n, h, a, s)); break;
					case "Monster": battle.addMonster(new Monster(n, h, a, s)); break;
					}
				}
				
				sc.close();
				frame.showBattle(battle);
				
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "HP, AC, and STAT must be integers!");
			} catch (FileNotFoundException ex) {
				JOptionPane.showMessageDialog(this, "Could not find battle_setup.txt!");
			}
		});
		
		panel.add(beginBtn);
		return panel;
	}
}
