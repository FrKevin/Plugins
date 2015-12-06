package fr.univ_lille1.fil.coo.plugins;

import java.io.File;

import fr.univ_lille1.fil.coo.plugins.finder.PluginFinder;

public class Main {

	public static void main(String[] args) {
		PluginFinder finder = new PluginFinder(new File("external_class"));
		System.out.println(finder.listFiles());
	}

}
