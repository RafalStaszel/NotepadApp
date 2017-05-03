package com.kaos.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class NotepadApp extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextArea textArea;
	private File currentFile;
	private File lastFolder;

	public static void main(String[] args) {
		NotepadApp notepadApp = new NotepadApp();
		notepadApp.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		centreWindow(notepadApp);
		notepadApp.setVisible(true);
	}

	public NotepadApp() {
		this.setTitle("Untitled - NotepadApp");
		this.setSize(1000, 600);
		addWindowListener(new ConfirmOnClose());
		initializeGUI();

	}

	class ConfirmOnClose extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			int confirm = JOptionPane.showConfirmDialog(e.getWindow(), "Are you sure you want to exit?");
			if (confirm == 0) {
				e.getWindow().dispose();
				System.exit(0);
			}
		}
	}

	public static void centreWindow(Window frame) {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
		frame.setLocation(x, y);
	}

	private void initializeGUI() {
		NotepadMenu notepadMenu = new NotepadMenu();
		this.setJMenuBar(notepadMenu.createMenuBar());
		JPanel content = (JPanel) this.getContentPane();
		content.setLayout(new BorderLayout());
		textArea = new JTextArea();
		JScrollPane scrollingArea = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
		JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		content.add(scrollingArea, BorderLayout.CENTER);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		notepadMenu.setTextArea(textArea);
		notepadMenu.setApp(this);
	}

	public File getCurrentFile() {
		return currentFile;
	}

	public void setCurrentFile(File currentFile) {
		this.currentFile = currentFile;
	}

	public File getLastFolder() {
		return lastFolder;
	}

	public void setLastFolder(File lastFolder) {
		this.lastFolder = lastFolder;
	}

}
