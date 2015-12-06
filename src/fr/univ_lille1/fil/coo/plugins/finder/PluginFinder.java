package fr.univ_lille1.fil.coo.plugins.finder;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import fr.univ_lille1.fil.coo.plugins.filter.DefaultFilter;

public class PluginFinder {
	protected File directory;
	protected FilenameFilter filter;
	protected Set<File> files;
	
	public PluginFinder(File directory){
		this(directory, new DefaultFilter());
	}
	
	public PluginFinder(File directory, FilenameFilter filter){
		this.directory = directory;
		this.filter = filter;
		this.files = listFiles();
	}
	
	public Set<File> listFiles(){
		File[] files = directory.listFiles(filter);
		if(files != null){
			return new HashSet<File>(Arrays.asList(files));
		}
		return new HashSet<File>();
	}
	
}
