package utilities;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class FolderFile {

	// E.g: createFolder("parentFolder/childFolder")
	// E.g: createFolder("C:\\parentFolder\\childFolder")
	public static boolean createFolder(String folderPath){
		File file = new File(folderPath);
		if (!file.exists()) {
			if (file.mkdir()) {
				return true;
			} else {
				return false;
			}
		}else{
			return false;
		}
	}

	// E.g: createMutilFolder("parentFolder/childFolder")
	// E.g: createMutilFolder("C:\\parentFolder\\childFolder")
	public static boolean createMutilFolder(String folderPath){
		File file = new File(folderPath);
		if (!file.exists()) {
			if (file.mkdirs()) {
				return true;
			} else {
				return false;
			}
		}else{
			return false;
		}
	}
	
	// E.g: deleteAFile("C:\\parentFolder\\childFolder\\file.txt")
	public static boolean deleteAFile(String filePath){
		File file = new File(filePath);
		if (file.exists()) {
			if (file.delete()) {
				return true;
			} else {
				return false;
			}
		}else {
			return false;
		}
	}
	
	// E.g: deleteAFolder("./test-reports")
	public static boolean deleteAFolder(String path) {
		try {
			FileUtils.deleteDirectory(new File(path));
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
