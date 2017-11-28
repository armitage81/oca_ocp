import java.nio.file.*;
import java.nio.charset.*;
import java.io.*;
import java.util.*;
import java.util.stream.*;
import java.util.function.*;

public class PathsAndFilesAdvanced {

	public static int counter = 0;

	public static void main(String[] args) {
		
	m("Creating a path and a reader from the file.", () -> {

		try {
			Path path = FileSystems.getDefault().getPath("logs", "access.log");
			BufferedReader reader = Files.newBufferedReader(path, Charset.forName("UTF-8"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	
	});
	
	m("endsWith", () -> {

		Path path = FileSystems.getDefault().getPath("logs", "access.log");
		Path path2 = FileSystems.getDefault().getPath("access.log");
		System.out.println(path.endsWith(path2));
		System.out.println(path2.endsWith(path));
		
		path = Paths.get("c:\\temp\\anton.txt");
		path2 = Paths.get("temp\\anton.txt");
		
		System.out.println(path.endsWith(path2));
		System.out.println(path2.endsWith(path));
	
	});
	
	m("getParent, getFileName, getRoot", () -> {

		
		Path path = Paths.get("c:\\temp\\anton.txt");
		Path path2 = Paths.get("temp\\anton.txt");
		
		System.out.println(path.getParent());
		System.out.println(path.getFileName());
		System.out.println(path.getRoot());
		
		System.out.println(path2.getParent());
		System.out.println(path2.getFileName());
		System.out.println(path2.getRoot());
	
	});
	
	m("isAbsolute", () -> {

		Path path = Paths.get("c:\\temp\\anton.txt");
		Path path2 = Paths.get("temp\\anton.txt");
		
		System.out.println(path.isAbsolute());
		System.out.println(path2.isAbsolute());
	
	});
	
	m("getNameCount", () -> {

		Path path = Paths.get("c:\\temp\\anton.txt");
		Path path2 = Paths.get("temp\\anton.txt");
		Path path3 = Paths.get("c:\\");
		
		System.out.println(path.getNameCount()); //Root does not count
		System.out.println(path2.getNameCount());
		System.out.println(path3.getNameCount());
	
	});
	
	m("normalize", () -> {

		Path path = Paths.get("c:\\temp\\anton.txt");
		Path path2 = Paths.get("temp\\anton.txt");
		Path path3 = Paths.get("c:\\");
		
		Path path4 = Paths.get("c:\\temp\\.\\.\\test.txt");
		Path path5 = Paths.get("c:\\temp\\..\\temp2\\test.txt");
		
		System.out.println(path + " " + path.normalize());
		System.out.println(path2 + " " + path2.normalize());
		System.out.println(path3 + " " + path3.normalize());
		System.out.println(path4 + " " + path4.normalize());
		System.out.println(path5 + " " + path5.normalize());
	
	});
	
	
	m("relativize", () -> {

		List<Path> l = Stream.<String>of(
			"c:\\",
			"c:\\temp",
			"c:\\temp\\logs",
			"c:\\temp\\logs\\1.log",
			"c:\\temp\\logs\\..\\logs\\.\\1.log",
			"temp",
			"temp\\logs",
			"temp\\logs\\1.log",
			"temp\\logs\\..\\logs\\.\\1.log",
			"unrelated",
			"c:\\anotherunrelated"
		).map(s -> Paths.get(s)).collect(Collectors.toList());
		
		Supplier<Stream<Path>> s = () -> l.stream();
		
		System.out.println("Relativize will throw an exception for all cases of mixing relative and absolute paths");
		
		s.get().forEach(element -> {
			s.get().forEach(element2 -> {
				System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				try {
					System.out.println(element + " relativize " + element2);
					System.out.println("'" + element.relativize(element2) + "'");
				} catch (Exception e) {
					System.out.println(e.getClass().getName() + " " + e.getMessage());
				}
				System.out.println("--------------------------------------------------------");
			});
		});
	
	});
	
     

		
		
	}
	
	public static void m(String descr, Runnable r) {
		System.out.print(++counter + " - ");
		System.out.println(descr);
		r.run();
		System.out.println();
	}


}