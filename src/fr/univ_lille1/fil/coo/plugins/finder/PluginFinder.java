package fr.univ_lille1.fil.coo.plugins.finder;

import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
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
	protected File searchDirectory;
	protected FilenameFilter filter;
	
	protected List<PluginListener> listeners;
	protected Set<File> files = new HashSet<>();
	protected boolean hasChanged;
	
	protected ClassLoader classLoader;
	
	@SuppressWarnings("deprecation")
	public PluginFinder(File classPathDirectory, File searchDirectory){
		this.searchDirectory = searchDirectory;
		this.listeners = new ArrayList<>();
		
		try {
			classLoader = new URLClassLoader(new URL[] {classPathDirectory.toURL()});
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
		this.filter = new DefaultFilter(classLoader);
		
		if (!searchDirectory.exists()) {
			searchDirectory.mkdirs();
		}
		else if (!searchDirectory.isDirectory()) {
			throw new IllegalArgumentException("File '"+searchDirectory+"' is not a directory");
		}
		System.out.println("Please add some plugin class into '"+searchDirectory+"' directory");
		System.out.println("Be carefull : plugin classes have to be declared in package 'plugins'");
	}
	
	public Set<File> listFiles(){
		File[] files = searchDirectory.listFiles(filter);
		if(files != null){
			return new HashSet<File>(Arrays.asList(files));
		}
		return new HashSet<File>();
	}
	
	public List<Plugin> toListPlugin(Set<File> files) {
		List<Plugin> plugins = new ArrayList<>();
		for(File f : files) {
			String clearName = f.getName().replaceFirst("\\.class$", "");
			Plugin plugin = null;
			try {
				plugin = (Plugin) classLoader.loadClass("plugins." + clearName).newInstance();
				plugins.add(plugin);
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {}
		}
		return plugins;
	}
	
	public void addListener(PluginListener listener){
		this.listeners.add(listener);
	}
	
	public void removeListener(PluginListener listener){
		this.listeners.remove(listener);
	}
	
	protected void setFiles(Set<File> newFiles){
		if( !files.equals(newFiles)) {
			files =  newFiles;
			hasChanged = true;
		}
	}
	
	protected boolean hasChangedFiles(){
		return hasChanged;
	}
	
	protected void notifyPluginListener(){
		List<Plugin> allPlugins = toListPlugin(files); 
		for(PluginListener listener: listeners){
			listener.pluginHasChanged(allPlugins);
		}
		hasChanged = false;
	}
	
	@Override
	public void run() {
		setFiles(listFiles());
		if(hasChangedFiles()){
			notifyPluginListener();
		}
	}
}
