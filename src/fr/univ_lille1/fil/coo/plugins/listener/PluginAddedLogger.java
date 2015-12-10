package fr.univ_lille1.fil.coo.plugins.listener;

import java.util.List;

import plugins.Plugin;

public class PluginAddedLogger implements PluginListener {

	@Override
	public void pluginHasChanged(List<Plugin> p) {
		System.out.println("update");
	}
}
