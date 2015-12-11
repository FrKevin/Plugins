package fr.univ_lille1.fil.coo.plugins.filter;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Constructor;

import plugins.Plugin;


/**
 * This class represent the classic filter used to verify files
 */
public class DefaultFilter implements FilenameFilter {
	protected final String NAME_OF_PACKAGE = "plugins";
	protected ClassLoader classLoader;
	
	/**
	 * Constructor of the {@link DefaultFilter} class
	 * @param classLoader The {@link ClassLoader} to use to load class files
	 */
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
	
	/**
	 * Convert the full of the file to the name with the package like include syntax
	 * @param name The name to convert
	 * @return The name converted
	 */
	protected String toClassName(String name){
		String classname = name.replaceFirst("\\.class$", "");
		return NAME_OF_PACKAGE + "." + classname;
	}
	
	/**
	 * Return the class object of the name of the binary in parameters
	 * @param className The name of the binary
	 * @return The class corresponding to the paramater
	 */
	protected Class<?> getClass(String className){
		try { 
			
			Class<?> c = classLoader.loadClass(className);
			return c;
		} catch (ClassNotFoundException e) {
			return null;
		}
	}
	
	/**
	 * Verify that the binary have ".class" as extension name
	 * @param name The name of the binary to verify
	 * @return True if the name ends with ".class" false in other cases
	 */
	protected boolean fileExtensionIsClass(String name){
		return name.endsWith(".class");
	}
		
	/**
	 * Verify that the binary is on the right package
	 * @param name The name of the binary to verify
	 * @return True if it's in the right package false in other cases
	 */
	protected boolean classInPackagePlugin(Class<?> c){
		Package p = c.getPackage();
		if(p == null ) {
			return false;
		}
		return c.getPackage().getName().equals(NAME_OF_PACKAGE);
	}
	
	/**
	 * Verify that the class inherit from {@link Plugin}s
	 * @param c The class to verify
	 * @return True if the class inherit from {@link Plugin}s false in the other cases
	 */
	protected boolean inherFromPlugin(Class<?> c){
		return Plugin.class.isAssignableFrom(c);
	}
	
	/**
	 * Verify that the class contains a constructor with no paramaters
	 * @param c The class to verify
	 * @return True if the class class have a constructor with no parameters false in the other cases
	 */
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
