package classLoader;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;


public class JavaClassLoader extends ClassLoader {
	
	
	private File classesDirectory;
	private Path classesPath;

	public JavaClassLoader(){

	}
	
	/**
	 * 
	 * @param typeOfOperation instruction or directive
	 * @param className
	 * @return
	 */
	public Class loadOperationClass(String typeOfOperation, String className) {
		classesDirectory = new File("operations\\" + typeOfOperation);
		classesPath = classesDirectory.toPath();
		
		try {
			URLClassLoader loader = new URLClassLoader(new URL[] {classesDirectory.toURL()});
			Class desiredClass = loader.loadClass(classesPath.toString().
					replace('\\', '.')+'.'+ className);
			return desiredClass;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public Class loadAddressingClass(String className) {
		classesDirectory = new File("addressing");
		classesPath = classesDirectory.toPath();
		
		try {
			URLClassLoader loader = new URLClassLoader(new URL[] {classesDirectory.toURL()});
			Class desiredClass = loader.loadClass(classesPath.toString().
					replace('\\', '.')+'.'+ className);
			return desiredClass;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}