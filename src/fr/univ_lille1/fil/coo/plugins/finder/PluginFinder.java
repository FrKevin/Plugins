package fr.univ_lille1.fil.coo.plugins.finder;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimerTask;

import fr.univ_lille1.fil.coo.plugins.filter.DefaultFilter;
import fr.univ_lille1.fil.coo.plugins.listener.PluginListener;
import plugins.Plugin;

public class PluginFinder extends TimerTask{
	protected File directory;
	protected FilenameFilter filter;
	
	protected List<PluginListener> listeners;
	
	public PluginFinder(File directory){
		this(directory, new DefaultFilter());
	}
	
	public PluginFinder(File directory, FilenameFilter filter){
		this.directory = directory;
		this.filter = filter;
		this.listeners = new ArrayList<>();
	}
	
	public Set<File> listFiles(){
		File[] files = directory.listFiles(filter);
		if(files != null){
			return new HashSet<File>(Arrays.asList(files));
		}
		return new HashSet<File>();
	}
	
	public void  addListener(PluginListener listener){
		this.listeners.add(listener);
	}
	
	public void removeListener(PluginListener listener){
		this.listeners.remove(listener);
	}

	@Override
	public void run() {
		Set<File> files = listFiles();
		List<Plugin> allPlugins = null; //TODO convertir file into plugin
		for(PluginListener listener: listeners){
			listener.pluginHasChanged(allPlugins);
		}
	}
}
