package dnd.gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import dnd.battle.Battle;
import dnd.model.Monster;

@SuppressWarnings("serial")
public class BattlePanel extends JPanel implements BattleCallback {
	
	private JTextArea logArea;
	private JTextArea initiativeArea;
	private JPanel actionPanel;
	private CountDownLatch latch;
	private int selectedValue;
	

	public BattlePanel(MainFrame frame) {
		setLayout(new BorderLayout());
		
		logArea = new JTextArea();
		logArea.setEditable(false);
		
		initiativeArea = new JTextArea();
		initiativeArea.setEditable(false);
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				new JScrollPane(logArea), 
				new JScrollPane(initiativeArea));
		splitPane.setDividerLocation(300);
		add(splitPane, BorderLayout.CENTER);
		
		actionPanel = new JPanel();
		add(actionPanel, BorderLayout.SOUTH);
	}
	
	
	public void startBattle(Battle battle) {
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			protected Void doInBackground() {
				battle.run(BattlePanel.this);
				return null;
			}
		};
		worker.execute();
	}
	
	@Override
	public int getTargetChoice(ArrayList<Monster> targets) {
		latch = new CountDownLatch(1);
		SwingUtilities.invokeLater(() -> {
			actionPanel.removeAll();
			actionPanel.setLayout(new FlowLayout());
			
			for (int i = 0; i < targets.size(); i++) {
				Monster m = targets.get(i);
				JButton btn = new JButton(m.getName() + " HP: " + m.getHP() + "/" + m.getMaxHP());
				
				final int index = i;
				
				btn.addActionListener(e -> {
					selectedValue = index;
					latch.countDown();
				});
				actionPanel.add(btn);
			}
			actionPanel.revalidate();
			actionPanel.repaint();
		});
		
		try { latch.await(); } catch (InterruptedException e) { e.printStackTrace(); }
		return selectedValue;
	}
	
	@Override
	public int getActionChoice(String[] actions) {
		latch = new CountDownLatch(1);
		SwingUtilities.invokeLater(() -> {
			actionPanel.removeAll();
			actionPanel.setLayout(new FlowLayout());
			
			for (int i = 0; i < actions.length; i++) {
				JButton btn = new JButton(actions[i]);
				
				final int index = i;
				
				btn.addActionListener(e -> {
					selectedValue = index;
					latch.countDown();
				});
				actionPanel.add(btn);
			}
			actionPanel.revalidate();
			actionPanel.repaint();
		});
		
		try { latch.await(); } catch (InterruptedException e) { e.printStackTrace(); }
		return selectedValue;
	}
	
	@Override
	public void log(String message) {
		SwingUtilities.invokeLater(() -> {
			logArea.append(message + "\n");
			logArea.setCaretPosition(logArea.getDocument().getLength());
		});
	}
	
	@Override
	public void showResult(String message) {
		SwingUtilities.invokeLater(() -> {
			JOptionPane.showMessageDialog(this, message);
			System.exit(0);
		});
	}
	
	@Override
	public void showInitiative(String name, int initiative) {
		SwingUtilities.invokeLater(() -> {
			initiativeArea.append(name + "(" + initiative + ")\n");
		});
	}
	
	@Override
	public void onDeath(String name) {
		SwingUtilities.invokeLater(() -> {
			logArea.append(name + " has been slain!\n");
		});
	}
}
