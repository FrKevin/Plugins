package fr.univ_lille1.fil.coo.plugins;

import java.io.File;
import java.util.Timer;

import fr.univ_lille1.fil.coo.plugins.finder.PluginFinder;
import fr.univ_lille1.fil.coo.plugins.gui.Window;
import fr.univ_lille1.fil.coo.plugins.listener.PluginAddedLogger;

public class Main {

	public static void main(String[] args) {
		File classPathDirectory = new File("droping");
		File pluginDirectory = new File(classPathDirectory, "plugins");
		
		PluginFinder finder = new PluginFinder(classPathDirectory, pluginDirectory);
		Window frame = new Window();
		finder.addListener(frame);
		finder.addListener(new PluginAddedLogger());
		new Timer().schedule(finder, 100, 2000);
	}
	
}
