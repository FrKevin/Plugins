package fr.univ_lille1.fil.coo.plugins.listener;

import java.util.List;

import plugins.Plugin;

/**
 * This interface represent the base of a {@link PluginListener}
 */
public interface PluginListener{
	/**
	 * Performed actions if the {@link Plugin}s has changed
	 */
	public void pluginHasChanged(List<Plugin> p);
}
