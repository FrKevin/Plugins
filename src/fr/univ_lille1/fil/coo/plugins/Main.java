package fr.univ_lille1.fil.coo.plugins;

import java.io.File;
import java.util.Timer;

import javax.swing.UIManager;

import fr.univ_lille1.fil.coo.plugins.finder.PluginFinder;
import fr.univ_lille1.fil.coo.plugins.gui.Window;
import fr.univ_lille1.fil.coo.plugins.listener.PluginAddedLogger;

public class Main {

	public static void main(String[] args) {
		
		try {
			// donne à l'interface graphique le thème associé au système d'exploitation
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) { }
		
		File classPathDirectory = new File("droping");
		File pluginDirectory = new File(classPathDirectory, "plugins");
		
		PluginFinder finder = new PluginFinder(classPathDirectory, pluginDirectory);
		Window frame = new Window();
		finder.addListener(frame);
		finder.addListener(new PluginAddedLogger());
		new Timer().schedule(finder, 100, 2000);
	}
	
}
