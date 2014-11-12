package com.arcing.naviaux.music.library;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

import com.arcing.naviaux.music.window.components.AllMusic;

public class Library {
	public static int selectedSong = 0;
	static ArrayList<String> libraryNames = new ArrayList<String>();
	static ArrayList<String> libraryFiles = new ArrayList<String>();
	public static void createLibrary() {
		try {
			File libraryContainmentUnit = new File(System.getProperty("user.home").replaceAll("\\\\", "/") + "/osuMPlayer/library.txt");
			BufferedReader libraryReader = new BufferedReader(new FileReader(libraryContainmentUnit));
			String value = "";
			while ((value = libraryReader.readLine()) != null) {
				System.out.println("Found Song: " + value);
				libraryNames.add(value.split(">")[0]);
				libraryFiles.add(value.split(">")[1]);
			}
			libraryReader.close();
		} catch (IOException e) {
			
		}
	}

	public static String[] getLibraryNames() {
		return libraryNames.toArray(new String[libraryNames.size()]);
	}
	public static String[] getLibraryFiles() {
		return libraryFiles.toArray(new String[libraryNames.size()]);
	}
	
	public static void addFolderToLibrary() {
		JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView());
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.showOpenDialog(chooser);
		File selected = chooser.getSelectedFile();
		if (selected != null)
			if (selected.isDirectory()) {
				File[] listing = addFolderHelper(selected);
				for (File song : listing) {
					if (song.getAbsolutePath().endsWith(".wav")) {
						libraryNames.add(song.getName().replace(".wav", ""));
						libraryFiles.add(song.getAbsolutePath());
					} else if (song.getAbsolutePath().endsWith(".mp3")) {
						libraryNames.add(song.getName().replace(".mp3", ""));
						libraryFiles.add(song.getAbsolutePath());
					}
				}
			}
		saveLibrary();
		reloadLibrary();
	}
	private static File[] addFolderHelper(File folder) {
		File[] returnable = new File[0];
		for (File select : folder.listFiles()) {
			if (select.isDirectory()) {
				returnable = merge (returnable, addFolderHelper(select));
			} else {
				File[] temp = {select};
				returnable = merge (returnable, temp);
			}
		}
		return returnable;
	}
	private static File[] merge(File[] array1, File[] array2) {
		if (array1 == null || array2 == null) 
			return array1 == null ? array1 : array2;
		
		File[] array = new File[array1.length + array2.length];
		int count = 0;
		for (File item : array1) {
			array[count] = item;
			count++;
		}
		for (File item : array2) {
			array[count] = item;
			count++;
		}
		
		return array;
	}
	
	public static void addSongToLibrary() {
		JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView());
		chooser.setMultiSelectionEnabled(true);
		chooser.showOpenDialog(chooser);
		File[] selected = chooser.getSelectedFiles();
		for (File song : selected) {
			if (song.getAbsolutePath().endsWith(".wav")) {
				libraryNames.add(song.getName().replace(".wav", ""));
				libraryFiles.add(song.getAbsolutePath());
			} else if (song.getAbsolutePath().endsWith(".mp3")) {
				libraryNames.add(song.getName().replace(".mp3", ""));
				libraryFiles.add(song.getAbsolutePath());
			}
		}
		saveLibrary();
		reloadLibrary();
	}
	
	public static void saveLibrary() {
		File library = new File(System.getProperty("user.home").replaceAll("\\\\", "/") + "/osuMPlayer/library.txt");
		try {
			new File(library.getParent()).mkdirs();
			library.createNewFile();
			BufferedWriter writer = new BufferedWriter(new FileWriter(library));
			for (int i = 0; i < libraryNames.size(); i++) {
				writer.append(libraryNames.get(i) + ">" + libraryFiles.get(i) + "\n");
			}
			writer.close();
		} catch (IOException e) { e.printStackTrace(); }
		
	}
	public static void reloadLibrary() {
		AllMusic.updateLibrary();
	}
}
