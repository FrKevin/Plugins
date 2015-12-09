package fr.univ_lille1.fil.coo.plugins;

import java.io.File;

import fr.univ_lille1.fil.coo.plugins.finder.PluginFinder;
import fr.univ_lille1.fil.coo.plugins.gui.Window;

public class Main {

	public static void main(String[] args) {
		PluginFinder finder = new PluginFinder(new File("droping/plugins"));
		System.out.println(finder.listFiles());
		//System.out.println(new plugins.ToLowercasePlugin());
		Window frame = new Window();
	}
	
}
