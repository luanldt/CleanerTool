package helper;

import java.io.File;
import java.io.FilenameFilter;

public class CustomFileNameFilter implements FilenameFilter {

	private String[] extensions;

	public CustomFileNameFilter() {
	}

	public CustomFileNameFilter(String... extensions) {
		this.extensions = extensions;
	}

	@Override
	public boolean accept(File dir, String name) {
		for (String extension : extensions) {
			if (name.toLowerCase().endsWith(extension)) {
				return true;
			}
		}
		return false;
	}

}
