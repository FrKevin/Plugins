package fr.univ_lille1.fil.coo.plugins.filter;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Constructor;

import com.sun.webkit.plugin.Plugin;

public class DefaultFilter implements FilenameFilter {

	@Override
	public boolean accept(File dir, String name) {
		if(!fileExtensionIsClass(name)){
			return false;
		}
		Class<?> c = null;
		try {
			c = Class.forName(toClassName(name));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return  classInPackagePlugin(c) && classContructorsNoParameters(c) && inherFromPlugin(c);
	}
	
	protected String toClassName(String name){
		String[] s = name.split("\\.");
		s = s[0].split("/");
		return "plugins." + s[s.length-1];
	}
	
	protected boolean fileExtensionIsClass(String name){
		return name.toLowerCase().endsWith(".class");
	}
		
	
	protected boolean classInPackagePlugin(Class<?> c){
		return c.getPackage().getName().equals("plugins");
	}
	
	protected boolean inherFromPlugin(Class<?> c){
		return Plugin.class.isAssignableFrom(c);
	}
	
	protected boolean classContructorsNoParameters(Class<?> c){
		Constructor<?>[] cos = c.getConstructors();
		for(Constructor<?> constructor : cos){
			if (constructor.getParameterCount() == 0){
				return true;
			}
		}
		return false;
	}
	
	
}
