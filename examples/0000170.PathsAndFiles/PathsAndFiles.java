import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;

public class PathsAndFiles {

	public static void main(String[] args) {
		
		//Creation of paths (They are not actual files, only representations)
		Path p = Paths.get("tmp");
		System.out.println(p);
		p = Paths.get("c:\\tmp");
		System.out.println(p);
		p = Paths.get("c:", "tmp", "videos", "interstellar.mov");
		System.out.println(p);
		
		
		Path pp1 = Paths.get("dir1");
		Path pp2 = Paths.get("dir2/dir3");
		
		
		//Creation of actual files and dirs on disk.
		
		try {
		
			if (!Files.exists(pp1)) {
				Files.createDirectory(pp1);
			}
			
			/*
			if (!Files.exists(pp2)) {   //NoSuchFileException. This will not create anything as dir2, as parent dir, does not exist.
				Files.createDirectory(pp2);
			}
			*/
			
			
			if (!Files.exists(pp2)) {   //This will create the dir
				Files.createDirectories(pp2);
			}
			
		
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		
		//Path getName(int) returns a path, not a string
		System.out.println(Paths.get("c:/temp/bratwurst").getName(1).toAbsolutePath());
		
		
		
	}


}