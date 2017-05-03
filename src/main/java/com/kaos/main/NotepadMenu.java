package com.kaos.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

public class NotepadMenu implements ActionListener {

	private NotepadApp app;
	private JTextArea textArea;

	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
		textArea.setComponentPopupMenu(createPopupMenu());
	}

	public void setApp(NotepadApp app) {
		this.app = app;

	}

	public JMenuBar createMenuBar() {
		JMenuBar notepadMenuBar = new JMenuBar();
		notepadMenuBar.add(createFileMenu());
		notepadMenuBar.add(createEditMenu());
		notepadMenuBar.add(createFormatMenu());
		return notepadMenuBar;
	}

	private JMenu createFileMenu() {
		JMenu menu = new JMenu("File");
		JMenuItem menuItem;
		menuItem = new JMenuItem("New");
		menuItem.setActionCommand("new");
		menuItem.addActionListener(this);
		menuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
		menu.add(menuItem);
		menuItem = new JMenuItem("Open");
		menuItem.setActionCommand("open");
		menuItem.addActionListener(this);
		menuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
		menu.add(menuItem);
		menuItem = new JMenuItem("Save");
		menuItem.setActionCommand("save");
		menuItem.addActionListener(this);
		menuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
		menu.add(menuItem);
		menuItem = new JMenuItem("Save As");
		menuItem.setActionCommand("saveas");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menu.addSeparator();
		menuItem = new JMenuItem("Exit");
		menuItem.setActionCommand("exit");
		menuItem.addActionListener(this);
		menuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl X"));
		menu.add(menuItem);
		return menu;
	}

	private JMenu createEditMenu() {
		JMenu menu = new JMenu("Edit");
		JMenuItem menuItem;
		menuItem = new JMenuItem("Copy");
		menuItem.setActionCommand("copy");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Paste");
		menuItem.setActionCommand("paste");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Select All");
		menuItem.setActionCommand("selectall");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		return menu;
	}

	private JMenu createFormatMenu() {
		JMenu menu = new JMenu("Window");
		JMenuItem menuItem;
		menuItem = new JMenuItem("Metal");
		menuItem.setActionCommand("metal");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Nimbus");
		menuItem.setActionCommand("nimbus");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Windows");
		menuItem.setActionCommand("windows");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		return menu;
	}

	private JPopupMenu createPopupMenu() {
		JPopupMenu popupMenu = new JPopupMenu("pop-up");
		JMenuItem menuItem;
		menuItem = new JMenuItem("Copy");
		menuItem.setActionCommand("copyPopup");
		menuItem.addActionListener(this);
		popupMenu.add(menuItem);
		menuItem = new JMenuItem("Paste");
		menuItem.setActionCommand("pastePopup");
		menuItem.addActionListener(this);
		popupMenu.add(menuItem);
		menuItem = new JMenuItem("Reverse");
		menuItem.setActionCommand("reverse");
		menuItem.addActionListener(this);
		popupMenu.add(menuItem);

		return popupMenu;
	}

	public void actionPerformed(ActionEvent evt) {
		String actCommand = evt.getActionCommand();
		if (actCommand.equalsIgnoreCase("new")) {
			newFile();
		} else if (actCommand.equalsIgnoreCase("open")) {
			openFile();
		} else if (actCommand.equalsIgnoreCase("save")) {
			saveFile();
		} else if (actCommand.equalsIgnoreCase("saveas")) {
			saveAsFile(null);
		} else if (actCommand.equalsIgnoreCase("exit")) {
			closeWindow();
		} else if (actCommand.equalsIgnoreCase("copy")) {
			textArea.copy();
		} else if (actCommand.equalsIgnoreCase("paste")) {
			textArea.paste();
		} else if (actCommand.equalsIgnoreCase("selectall")) {
			textArea.selectAll();
		} else if (actCommand.equalsIgnoreCase("copyPopup")) {
			textArea.copy();
		} else if (actCommand.equalsIgnoreCase("pastePopup")) {
			textArea.paste();
		} else if (actCommand.equalsIgnoreCase("reverse")) {
			reverseString(textArea.getSelectedText());
		} else if (actCommand.equalsIgnoreCase("metal")) {
			setLookFeel("metal");
		} else if (actCommand.equalsIgnoreCase("nimbus")) {
			setLookFeel("nimbus");
		} else if (actCommand.equalsIgnoreCase("windows")) {
			setLookFeel("windows");
		}

	}

	private void closeWindow() {
		app.dispatchEvent(new WindowEvent(app, WindowEvent.WINDOW_CLOSING));
	}

	private void newFile() {
		File currentFile = app.getCurrentFile();
		if (currentFile != null) {
			app.setLastFolder(currentFile.getParentFile());
			System.out.println("Setting last folder: " + app.getLastFolder());
			app.setCurrentFile(null);
			textArea.setText("");
			app.setTitle("Untitled - NotepadApp");
		}
	}

	private void openFile() {
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt", "props", "java");
		File file = showFileChooser("Open File", filter, "Open");
		if (file != null) {
			FileReader fileReader;
			BufferedReader buffReader;

			try {
				fileReader = new FileReader(file);
				buffReader = new BufferedReader(fileReader);
				textArea.read(buffReader, "Reading File");
				app.setCurrentFile(file);
				app.setLastFolder(file.getParentFile());
				app.setTitle(file.getName() + " - NotepadApp");
			} catch (IOException ioex) {
				System.out.println("Error in reading file: " + file);
				System.out.println(ioex.getMessage());
				ioex.printStackTrace();
			} finally {
				fileReader = null;
				buffReader = null;
			}
		}

	}

	private void reverseString(String string) {
		if (string == null)
			textArea.append("\nselect text to reverse");
		else {
			StringBuilder builder = new StringBuilder(string);
			String result = builder.reverse().toString();
			textArea.append("\n" + result);
		}
	}

	private void saveFile() {
		saveAsFile(app.getCurrentFile());
		app.setTitle(app.getCurrentFile() + " - NotepadApp");
	}

	private void saveAsFile(File file) {
		if (file == null) {
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt", "props", "java");
			file = showFileChooser("Save File", filter, "Save");
		}
		if (file != null) {
			try {
				FileWriter fileWriter = new FileWriter(file);
				BufferedWriter buffWriter = new BufferedWriter(fileWriter);
				textArea.write(buffWriter);
				app.setCurrentFile(file);
				app.setLastFolder(file.getParentFile());
			} catch (IOException ioex) {
				System.out.println("Error in writing to file: " + file);
				System.out.println(ioex.getMessage());
				ioex.printStackTrace();
			}
		}
	}

	private File showFileChooser(String dialogTile, FileNameExtensionFilter filter, String operation) {
		File file = null;
		JFileChooser fc = new JFileChooser(app.getLastFolder());
		int returnVal = 0;
		if (operation.equalsIgnoreCase("open")) {
			fc = new JFileChooser(app.getLastFolder());
			fc.setFileFilter(filter);
			returnVal = fc.showDialog(app, "Open File");
		} else if (operation.equalsIgnoreCase("save")) {
			fc = new JFileChooser(app.getLastFolder());
			fc.setFileFilter(filter);
			returnVal = fc.showSaveDialog(app);
		}
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile();
		}
		return file;
	}

	private void setLookFeel(String kolor) {

		if (kolor.equals("metal")) {
			try {
				UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e) {

				e.printStackTrace();
			}
			SwingUtilities.updateComponentTreeUI(app);
		} else if (kolor.equals("nimbus")) {
			try {
				UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e) {

				e.printStackTrace();
			}
			SwingUtilities.updateComponentTreeUI(app);
		} else if (kolor.equals("windows")) {
			try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e) {

				e.printStackTrace();
			}
			SwingUtilities.updateComponentTreeUI(app);
		}

	}

}
