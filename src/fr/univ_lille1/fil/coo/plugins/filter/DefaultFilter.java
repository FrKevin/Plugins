package fr.univ_lille1.fil.coo.plugins.filter;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Constructor;

import plugins.Plugin;



public class DefaultFilter implements FilenameFilter {
	protected final String NAME_OF_PACKAGE = "plugins";
	
	@Override
	public boolean accept(File dir, String name) {
		if(!fileExtensionIsClass(name)){
			return false;
		}
		Class<?> c = getClass(toClassName(name));
		if(c == null ){
			System.out.println("c == null ");
			return false;
		}
		System.out.println("prout class c toString  "+ c.toString());
		return  classInPackagePlugin(c) && classContructorsNoParameters(c) && inherFromPlugin(c);
	}
	
	protected String toClassName(String name){
		System.out.println("toClassName() 1 "+ name );
		String classname = name.replaceFirst("\\.class$", "");
		System.out.println("toClassName() 2 "+ classname );
		return NAME_OF_PACKAGE + "." + classname;
	}
	
	
	protected Class<?> getClass(String className){
		try { 
			System.out.println("getClass() " + className);
			Class<?> c = Class.forName(className);
			System.out.println(c);
			return c;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	protected boolean fileExtensionIsClass(String name){
		return name.endsWith(".class");
	}
		
	
	protected boolean classInPackagePlugin(Class<?> c){
		System.out.println("prout classInPackagePlugin()");
		System.out.println(" c= "+ c);
		Package p = c.getPackage();
		if(p == null ) {
			return false;
		}
		System.out.println("prout classInPackagePlugin() 2 "+ p);
		return c.getPackage().getName().equals(NAME_OF_PACKAGE);
	}
	
	protected boolean inherFromPlugin(Class<?> c){
		return Plugin.class.isAssignableFrom(c);
	}
	
	protected boolean classContructorsNoParameters(Class<?> c){
		System.out.println("prout classContructorsNoParameters()");
		Constructor<?>[] cos = c.getConstructors();
		for(Constructor<?> constructor : cos){
			if (constructor.getParameterCount() == 0){
				return true;
			}
		}
		return false;
	}
	
	
}
