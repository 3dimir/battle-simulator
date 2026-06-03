package dnd;

import javax.swing.SwingUtilities;

import dnd.gui.*;

public class Main {
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new MainFrame());
	}
}
