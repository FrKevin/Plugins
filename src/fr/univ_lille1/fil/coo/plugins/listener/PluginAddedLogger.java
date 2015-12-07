package fr.univ_lille1.fil.coo.plugins.listener;

import java.util.List;

import fr.univ_lille1.fil.coo.plugins.Plugin;

public class PluginAddedLogger implements PluginListener {

	@Override
	public void pluginHasChenged(List<Plugin> p) {
		System.out.println("logger");
	}
}
