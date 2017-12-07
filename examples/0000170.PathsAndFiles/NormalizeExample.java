import java.nio.*;
import java.nio.file.*;

class NormalizeExample {
	
	public static void main(String[] args) {
		
		//Note: Empty absolute path "/" or "c:\" has nameCount = 0; Empty relative path "." has nameCount = 1. One element relative path "temp" has the nameCount = 1, too
		
		Path path;

		path = Paths.get(".");
		System.out.println(path);
		System.out.println(path.normalize());
		System.out.println(path.getNameCount());
		System.out.println(path.normalize().getNameCount());
		
		path = Paths.get("test");
		System.out.println(path);
		System.out.println(path.normalize());
		System.out.println(path.getNameCount());
		System.out.println(path.normalize().getNameCount());
		
		path = Paths.get("/");
		System.out.println(path);
		System.out.println(path.normalize());
		System.out.println(path.getNameCount());
		System.out.println(path.normalize().getNameCount());
	}
	
}
