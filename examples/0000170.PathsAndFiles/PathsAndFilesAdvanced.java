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
					try {
						System.out.print(element + ";" + element2 + ";" + element.relativize(element2));
					} catch (Exception e) {
						System.out.print(element + ";" + element2 + ";" + e.getMessage());
					} finally {
						System.out.println();
					}
				});
			});
		
		});
		
		 
		m("resolve", () -> {

			Path path = Paths.get("c:\\temp\\anton.txt");
			Path path2 = Paths.get("temp\\anton.txt");
			
			System.out.println(path.resolve(path2));
			System.out.println(path2.resolve(path));
		
		});
		
		m("iterator loops through all elements of the path except the root", () -> {

			Path path = Paths.get("c:\\temp\\anton.txt");
			Iterator<Path> it = path.iterator();
			while (it.hasNext()) {
				System.out.println(it.next());
			}
			
			System.out.println(path.subpath(0,1));
			System.out.println(path.subpath(1,2));
			System.out.println(path.subpath(0,2));
		
		});
		
		
		m("getFileName, getFileSystem, getRoot", () -> {

			Path path = Paths.get("c:\\temp\\anton.txt");
						
			System.out.println(path.getFileName());
			System.out.println(path.getFileSystem());
			System.out.println(path.getRoot());
			
			path = Paths.get("c:\\temp");
						
			System.out.println(path.getFileName());
			System.out.println(path.getFileSystem());
			System.out.println(path.getRoot());
			
			path = Paths.get("temp\\anton.txt");
						
			System.out.println(path.getFileName());
			System.out.println(path.getFileSystem());
			System.out.println(path.getRoot());
		
		});
		
		m("startsWith, endsWith", () -> {

			Path path = Paths.get("c:\\temp\\anton.txt");
			Path path2 = Paths.get("c:\\temp");
			Path path3 = Paths.get("temp\\anton.txt");
			Path path4 = Paths.get("anton.txt");
						
			Supplier<Stream<Path>> s = () -> Stream.of(path, path2, path3, path4);
						
			System.out.println("path1;path2;path1.startsWith(path2);path1.endsWith(path2)");
			s.get().forEach(element -> {
				s.get().forEach(element2 -> {
					try {
						System.out.print(element + ";" + element2 + ";" + element.startsWith(element2) + ";" + element.endsWith(element2));
					} catch (Exception e) {
						System.out.print(element + ";" + element2 + ";" + e.getMessage());
					} finally {
						System.out.println();
					}
				});
			});
			
		
		});
		
		m("toAbsolute", () -> {

			Path path = Paths.get("logs\\anton.txt");
			Path path2 = Paths.get("c:\\logs\\anton.txt");
						
			System.out.println(path.toAbsolutePath());
			System.out.println(path2.toAbsolutePath());
			
		
		});
		
		
	}
	
	public static void m(String descr, Runnable r) {
		System.out.print(++counter + " - ");
		System.out.println(descr);
		r.run();
		System.out.println();
	}


}
