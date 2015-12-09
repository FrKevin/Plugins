package fr.univ_lille1.fil.coo.plugins;

import java.io.File;
import java.util.Timer;

import fr.univ_lille1.fil.coo.plugins.finder.PluginFinder;
import fr.univ_lille1.fil.coo.plugins.gui.Window;

public class Main {

	public static void main(String[] args) {
		PluginFinder finder = new PluginFinder(new File("droping/plugins"));
		Window frame = new Window();
		finder.addListener(frame);
		
		new Timer().schedule(finder, 1000, 5000);
	}
	
}
