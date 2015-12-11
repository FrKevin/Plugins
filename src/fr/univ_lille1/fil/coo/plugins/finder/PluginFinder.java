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

/**
 * This class is used to set the directory where to find {@link Plugin}s
 */
public class PluginFinder extends TimerTask{
	protected File searchDirectory;
	protected FilenameFilter filter;
	
	protected List<PluginListener> listeners;
	protected Set<File> files = new HashSet<>();
	protected boolean hasChanged;
	
	protected ClassLoader classLoader;
	
	/**
	 * Constructor of the {@link PluginFinder} class
	 * @param classPathDirectory The folder where to find the plugin folder
	 * @param searchDirectory The folder where to search
	 */
	@SuppressWarnings("deprecation")
	public PluginFinder(File classPathDirectory, File searchDirectory){
		this.searchDirectory = searchDirectory;
		this.listeners = new ArrayList<>();
		
		
		if (!searchDirectory.exists()) {
			searchDirectory.mkdirs();
		}
		else if (!searchDirectory.isDirectory()) {
			throw new IllegalArgumentException("File '"+searchDirectory+"' is not a directory");
		}
		try {
			classLoader = new URLClassLoader(new URL[] {classPathDirectory.toURL()});
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
		this.filter = new DefaultFilter(classLoader);
		System.out.println("Please add some plugin class into '"+searchDirectory+"' directory");
		System.out.println("Be carefull : plugin classes have to be declared in package 'plugins'");
	}
	
	/**
	 * Generate a Set of the {@link Plugin}s availables in the search directory
	 * @return The files that are corresponding to the filter
	 */
	public Set<File> listFiles(){
		File[] files = searchDirectory.listFiles(filter);
		if(files != null){
			return new HashSet<File>(Arrays.asList(files));
		}
		return new HashSet<File>();
	}
	
	/**
	 * Generate the list of {@link Plugin}s availables
	 * @param files the name of the {@link Plugin}s to instantiate the corresponding classes
	 * @return The list of {@link Plugin}s availables
	 */
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
	
	/**
	 * Add a listener to the finder
	 * @param listener the listener to add
	 */
	public void addListener(PluginListener listener){
		this.listeners.add(listener);
	}
	
	/**
	 * Remove a listener to the finder
	 * @param listener the listener to remove
	 */
	public void removeListener(PluginListener listener){
		this.listeners.remove(listener);
	}
	
	/**
	 * Verify if the files have changed
	 * @param newFiles The new list of files
	 */
	protected void setFiles(Set<File> newFiles){
		if( !files.equals(newFiles)) {
			files =  newFiles;
			hasChanged = true;
		}
	}
	
	/**
	 * Return the state of the folder containing {@link Plugin}s files
	 * @return True if the folder have changer (add or remove plugin) false in other cases
	 */
	protected boolean hasChangedFiles(){
		return hasChanged;
	}
	
	/**
	 * Notify all plugin listeners
	 */
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
