package fr.univ_lille1.fil.coo.plugins.filter;

import java.io.File;
import java.io.FilenameFilter;

public class DefaultFilter implements FilenameFilter {

	@Override
	public boolean accept(File dir, String name) {
		return fileExtensionIsClass(name);
	}
	
	public boolean fileExtensionIsClass(String name){
		return name.toLowerCase().endsWith(".class");
	}
		
	
	public boolean classInPackagePlugin(Class<?> c){
		// c.getPackage()
		return false;
	}
	
	public boolean inherFromPlugin(Class<?> c){
		//Plugin.class.isssignedFrom(class)
		return false;
	}
	
	public boolean ClassContructorsNoParameters(Class<?> c){
		//c.getConstructor | constructor.getParameterTypes()
		return false;
	}
	
	
}
