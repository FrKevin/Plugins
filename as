[1mdiff --git a/external_class/essai1.class b/external_class/essai1.class[m
[1mdeleted file mode 100644[m
[1mindex e69de29..0000000[m
[1mdiff --git a/external_class/essai2.class b/external_class/essai2.class[m
[1mdeleted file mode 100644[m
[1mindex e69de29..0000000[m
[1mdiff --git a/external_class/essai3.txt b/external_class/essai3.txt[m
[1mdeleted file mode 100644[m
[1mindex e69de29..0000000[m
[1mdiff --git a/src/fr/univ_lille1/fil/coo/plugins/Main.java b/src/fr/univ_lille1/fil/coo/plugins/Main.java[m
[1mindex 5e061d5..98c47df 100644[m
[1m--- a/src/fr/univ_lille1/fil/coo/plugins/Main.java[m
[1m+++ b/src/fr/univ_lille1/fil/coo/plugins/Main.java[m
[36m@@ -11,7 +11,7 @@[m [mpublic class Main {[m
 		PluginFinder finder = new PluginFinder(new File("external_class"));[m
 		System.out.println(finder.listFiles());[m
 		[m
[31m-		Window frame = new Window();[m
[32m+[m		[32m//Window frame = new Window();[m
 	}[m
 	[m
 }[m
[1mdiff --git a/src/fr/univ_lille1/fil/coo/plugins/filter/DefaultFilter.java b/src/fr/univ_lille1/fil/coo/plugins/filter/DefaultFilter.java[m
[1mindex 1850d4d..c1d6b2a 100644[m
[1m--- a/src/fr/univ_lille1/fil/coo/plugins/filter/DefaultFilter.java[m
[1m+++ b/src/fr/univ_lille1/fil/coo/plugins/filter/DefaultFilter.java[m
[36m@@ -7,34 +7,54 @@[m [mimport java.lang.reflect.Constructor;[m
 import com.sun.webkit.plugin.Plugin;[m
 [m
 public class DefaultFilter implements FilenameFilter {[m
[31m-[m
[32m+[m	[32mprotected final String NAME_OF_PACKAGE = "plugins";[m
[32m+[m[41m	[m
 	@Override[m
 	public boolean accept(File dir, String name) {[m
 		if(!fileExtensionIsClass(name)){[m
 			return false;[m
 		}[m
[31m-		Class<?> c = null;[m
[31m-		try {[m
[31m-			c = Class.forName(toClassName(name));[m
[31m-		} catch (ClassNotFoundException e) {[m
[31m-			e.printStackTrace();[m
[32m+[m		[32mClass<?> c = getClass(toClassName(name));[m
[32m+[m		[32mif(c == null ){[m
[32m+[m			[32mSystem.out.println("c == null ");[m
[32m+[m			[32mreturn false;[m
 		}[m
[32m+[m		[32mSystem.out.println("prout class c toString  "+ c.toString());[m
 		return  classInPackagePlugin(c) && classContructorsNoParameters(c) && inherFromPlugin(c);[m
 	}[m
 	[m
 	protected String toClassName(String name){[m
[31m-		String[] s = name.split("\\.");[m
[31m-		s = s[0].split("/");[m
[31m-		return "plugins." + s[s.length-1];[m
[32m+[m		[32mSystem.out.println("toClassName() 1 "+ name );[m
[32m+[m		[32mString classname = name.replaceFirst("\\.class$", "");[m
[32m+[m		[32mSystem.out.println("toClassName() 2 "+ classname );[m
[32m+[m		[32mreturn NAME_OF_PACKAGE + "." + classname;[m
[32m+[m	[32m}[m
[32m+[m[41m	[m
[32m+[m[41m	[m
[32m+[m	[32mprotected Class<?> getClass(String className){[m
[32m+[m		[32mtry {[m
[32m+[m			[32mSystem.out.println("getClass() " + className);[m
[32m+[m			[32mreturn Class.forName(className);[m
[32m+[m		[32m} catch (ClassNotFoundException e) {[m
[32m+[m			[32me.printStackTrace();[m
[32m+[m			[32mreturn null;[m
[32m+[m		[32m}[m
 	}[m
 	[m
 	protected boolean fileExtensionIsClass(String name){[m
[31m-		return name.toLowerCase().endsWith(".class");[m
[32m+[m		[32mreturn name.endsWith(".class");[m
 	}[m
 		[m
 	[m
 	protected boolean classInPackagePlugin(Class<?> c){[m
[31m-		return c.getPackage().getName().equals("plugins");[m
[32m+[m		[32mSystem.out.println("prout classInPackagePlugin()");[m
[32m+[m		[32mSystem.out.println(" c= "+ c);[m
[32m+[m		[32mPackage p = c.getPackage();[m
[32m+[m		[32mif(p == null ) {[m
[32m+[m			[32mreturn false;[m
[32m+[m		[32m}[m
[32m+[m		[32mSystem.out.println("prout classInPackagePlugin() 2 "+ p);[m
[32m+[m		[32mreturn c.getPackage().getName().equals(NAME_OF_PACKAGE);[m
 	}[m
 	[m
 	protected boolean inherFromPlugin(Class<?> c){[m
[36m@@ -42,6 +62,7 @@[m [mpublic class DefaultFilter implements FilenameFilter {[m
 	}[m
 	[m
 	protected boolean classContructorsNoParameters(Class<?> c){[m
[32m+[m		[32mSystem.out.println("prout classContructorsNoParameters()");[m
 		Constructor<?>[] cos = c.getConstructors();[m
 		for(Constructor<?> constructor : cos){[m
 			if (constructor.getParameterCount() == 0){[m
[1mdiff --git a/src/fr/univ_lille1/fil/coo/plugins/finder/PluginFinder.java b/src/fr/univ_lille1/fil/coo/plugins/finder/PluginFinder.java[m
[1mindex 7247f6d..d674120 100644[m
[1m--- a/src/fr/univ_lille1/fil/coo/plugins/finder/PluginFinder.java[m
[1m+++ b/src/fr/univ_lille1/fil/coo/plugins/finder/PluginFinder.java[m
[36m@@ -27,7 +27,7 @@[m [mpublic class PluginFinder implements ActionListener{[m
 	public PluginFinder(File directory, FilenameFilter filter){[m
 		this.directory = directory;[m
 		this.filter = filter;[m
[31m-		this.files = listFiles();[m
[32m+[m		[32m//this.files = listFiles();[m
 		this.listeners = new ArrayList<>();[m
 	}[m
 	[m
