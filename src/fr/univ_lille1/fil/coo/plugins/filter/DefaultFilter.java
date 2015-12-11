package fr.univ_lille1.fil.coo.plugins.filter;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Constructor;

import plugins.Plugin;



public class DefaultFilter implements FilenameFilter {
	protected final String NAME_OF_PACKAGE = "plugins";
	protected ClassLoader classLoader;
	
	public DefaultFilter(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	@Override
	public boolean accept(File dir, String name) {
		if(!fileExtensionIsClass(name)){
			return false;
		}
		Class<?> c = getClass(toClassName(name));
		if(c == null ){
			return false;
		}
		return  classInPackagePlugin(c) && classContructorsNoParameters(c) && inherFromPlugin(c);
	}
	
	protected String toClassName(String name){
		String classname = name.replaceFirst("\\.class$", "");
		return NAME_OF_PACKAGE + "." + classname;
	}
	
	
	protected Class<?> getClass(String className){
		try { 
			
			Class<?> c = classLoader.loadClass(className);
			return c;
		} catch (ClassNotFoundException e) {
			return null;
		}
	}
	
	protected boolean fileExtensionIsClass(String name){
		return name.endsWith(".class");
	}
		
	
	protected boolean classInPackagePlugin(Class<?> c){
		Package p = c.getPackage();
		if(p == null ) {
			return false;
		}
		return c.getPackage().getName().equals(NAME_OF_PACKAGE);
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
