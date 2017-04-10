package math.battle.cheat;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileParser {

	public static DirectoryStream<Path> getStreams(String folderPath) throws IOException {
		return Files.newDirectoryStream(Paths.get(folderPath));
	}

	public static List<String> listFileNames(String folderPath, String fileExtensionFilter) {
		List<String> paths = new ArrayList<String>();
		try (DirectoryStream<Path> stream = getStreams(folderPath)) {
			for (Path entry : stream) {
				if (entry.getFileName().toString().endsWith(fileExtensionFilter))
					paths.add(entry.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return paths;
	}

}
