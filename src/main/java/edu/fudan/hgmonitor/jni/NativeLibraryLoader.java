package edu.fudan.hgmonitor.jni;

public class NativeLibraryLoader {

	public static void loadNativeLibraries(String lib) {
		if (lib.endsWith(":")) {
			lib = lib.substring(0, lib.length() - 1);
		}
		String[] libs = lib.split(":");
		for (int i = 0; i < libs.length; i++) {
			System.load(libs[i]);
		}	
	}
}
