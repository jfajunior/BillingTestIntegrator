package br.com.gvt.integrator.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;




/**
 * FileSystemUtils helper class.
 * @author José Júnior
 *         GVT - 2014.08
 * 
 */
public class FileSystemUtils {
	private static final Logger _logger = Logger.getLogger(FileSystemUtils.class);
	
	
	
	
	/**
	 * Reads a file from the filesystem and loads it to a Java Object.
	 * 
	 * @param filename
	 *            The filename with the object data.
	 * @return The object read from the file.
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static Object loadObjectFromFile(String filename) throws ClassNotFoundException, IOException {
		// Reading data...
		Object object = null;
		ObjectInputStream ois = null;
		try {
			File file = new File(filename);
			System.out.println("Path: " + file.getAbsolutePath());
			ois = new ObjectInputStream(new FileInputStream(filename));
			object = ois.readObject();
		}
		catch (ClassNotFoundException | IOException e) {
			_logger.error("Error while trying to read the object from the filesystem: " + e.getMessage());
			throw e;
		}
		finally {
			if (ois != null) {
				try {
					ois.close();
				}
				catch (Exception e) {
					// Do nothing!
				}
			}
		}
		return object;
	}
	
	
	
	
	/**
	 * Saves an object to a file on the filesystem.
	 * 
	 * @param filename
	 *            The file name where to save the Java object.
	 * @param object
	 *            The object to be saved on the filesystem.
	 * @throws IOException
	 */
	public static void save(String filename, Object object) throws IOException {
		// Writing data...
		ObjectOutputStream oos = null;
		try {
			File file = new File(filename);
			System.out.println("Path: " + file.getAbsolutePath());
			oos = new ObjectOutputStream(new FileOutputStream(file));
			System.out.println("Nome do ficheiro: " + filename);
			oos.writeObject(object);
		}
		catch (FileNotFoundException e) {
			_logger.error("File not found: " + filename + "\nMessage: " + e.getMessage());
			throw e;
		}
		catch (IOException e) {
			_logger.error("Erro while trying to save an object to the filesystem: " + e.getMessage());
			throw e;
		}
		finally {
			if (oos != null) {
				try {
					oos.close();
				}
				catch (Exception e) {
					// Do nothing!
				}
			}
		}
	}
}
