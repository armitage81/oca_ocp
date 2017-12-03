import java.nio.file.*;
import java.nio.charset.*;
import java.io.*;
import java.util.*;
import java.util.stream.*;
import java.util.function.*;
import java.nio.file.*;
import java.nio.file.attribute.*;



public class FilesExamples {

	public static int counter = 0;

	public static void main(String[] args) throws Exception {
				
		m("copy and delete", () -> {

			Path path = Paths.get("files/a.txt");
			Path path2 = Paths.get("files/a_copy.txt");
			
			Path path3 = Paths.get("files/b.txt");
			Path path4 = Paths.get("files/b_copy.txt");
			
			Path path5 = Paths.get("files/c.txt");
			Path path6 = Paths.get("files/c_copy.txt");

			
			
			//Copy with input stream
			try(FileInputStream fis = new FileInputStream("files/a.txt")) {
				System.out.println("Copying a.txt to a_copy.txt using InputStream and path");
				Files.copy(fis, path2, StandardCopyOption.REPLACE_EXISTING); //StandardCopyOption.ATOMIC_MOVE is not supported by this file system
			} catch (IOException e) {
				System.out.println("Probably thrown because a subsequent copy operation cannot override existing file");
				e.printStackTrace();
			}
			
			//Copy with two paths
			try {
				System.out.println("Copying b.txt to b_copy.txt using two paths");
				Files.copy(path3, path4, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				System.out.println("Probably thrown because a subsequent copy operation cannot override existing file");
				e.printStackTrace();
			}
			
			
			//Copy with output stream (Note, deletion cannot happen in the try block as the stream is already open)
			try {
				Files.deleteIfExists(path6);
			} catch(IOException e) {
				e.printStackTrace();
			}
			try(FileOutputStream fos = new FileOutputStream("files/c_copy.txt")) {
				System.out.println("Copying c.txt to c_copy.txt using path and output stream");
				Files.copy(path5, fos); //No Copy Options possible here!
			} catch (IOException e) {
				System.out.println("Probably thrown because a subsequent copy operation cannot override existing file");
				e.printStackTrace();
			}
			
		
		});
		
		m("creating directories and files", () -> {

			Path path = Paths.get("files/dir1/dir2/dir3");
			
			Path path2 = Paths.get("files/singleDir");
			Path path3 = Paths.get("files/singleDir/singleFile.txt");

			try {
				System.out.println(String.format("Creating directories %s. Note how existing directories don't cause any trouble.", path));
				Files.createDirectories(path);
				System.out.println(String.format("Creating a single directory %s. Note how an existing directory will cause trouble.", path2));
				Files.deleteIfExists(path3);
				Files.deleteIfExists(path2);
				System.out.println(String.format("Creating a single file %s.", path3));
				Files.createDirectory(path2);
				Files.createFile(path3);
			} catch(IOException e) {
				throw new RuntimeException(e);
			}
			
			
			
		
		});
		
		m("Checking if files exist", () -> {

			Path path = Paths.get(".");
			Path path2 = Paths.get("DoEsNoTeXiSt");
			System.out.println(String.format("File or dir under path %s %s", path.toAbsolutePath(), Files.exists(path) ? "Exists" : "Does not exist"));
			System.out.println(String.format("File or dir under path %s %s", path2.toAbsolutePath(), Files.exists(path2) ? "Exists" : "Does not exist"));
			
		});
		
		m("isDirectory, isRegularFile, isSymbolicLink", () -> {

			//Note how isDiectory, isRegularFile etc. handle symbolic links. They returns the results of the link target, except NOFOLLOW_LINKS is added.
		
			System.out.println("---------------------------------");
			Path path = Paths.get("files2", "dir");
			Path path2 = Paths.get("files2", "file");
			Path path3 = Paths.get("files2", "dirsymlink");
			Path path4 = Paths.get("files2", "filesymlink");
			
			System.out.println("---------------------------------");
			System.out.println(Files.isDirectory(path));
			System.out.println(Files.isDirectory(path2));
			//System.out.println(Files.isDirectory(path3));
			//System.out.println(Files.isDirectory(path4));
			
			System.out.println("---------------------------------");
			System.out.println(Files.isRegularFile(path));
			System.out.println(Files.isRegularFile(path2));
			//System.out.println(Files.isRegularFile(path3));
			//System.out.println(Files.isRegularFile(path4));
			
			System.out.println("---------------------------------");
			System.out.println(Files.isSymbolicLink(path));
			System.out.println(Files.isSymbolicLink(path2));
			//System.out.println(Files.isSymbolicLink(path3));
			//System.out.println(Files.isSymbolicLink(path4));
			
			System.out.println("---------------------------------");
			System.out.println(Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS));
			System.out.println(Files.isDirectory(path2, LinkOption.NOFOLLOW_LINKS));
			//System.out.println(Files.isDirectory(path3, LinkOption.NOFOLLOW_LINKS));
			//System.out.println(Files.isDirectory(path4, LinkOption.NOFOLLOW_LINKS));
			
			System.out.println("---------------------------------");
			System.out.println(Files.isRegularFile(path, LinkOption.NOFOLLOW_LINKS));
			System.out.println(Files.isRegularFile(path2, LinkOption.NOFOLLOW_LINKS));
			//System.out.println(Files.isRegularFile(path3, LinkOption.NOFOLLOW_LINKS));
			//System.out.println(Files.isRegularFile(path4, LinkOption.NOFOLLOW_LINKS));
			
			System.out.println("---------------------------------");
			System.out.println("isSymbolikLink() has no NOFOLLOW_LINKS option");
			
		});
		
		
		m("isSamePath", () -> {
			try {
				//if two paths are equal, returns true (even if the files don't exist)
				System.out.println(Files.isSameFile(Paths.get("/DoesNotExist"), Paths.get("/DoesNotExist")));
								
				//Paths are not equal if they are only logically equal. In this case, it checks for the file existence and checks whether both paths refer to the same file
				//If one of the files does not exist, it throws an exception
				System.out.println(Files.isSameFile(Paths.get("files2/file"), Paths.get("files2/../files2/./file")));
				System.out.println(Files.isSameFile(Paths.get("files2/file"), Paths.get("doesnotexist")));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	
		m("size", () -> {
			//Size returns the real file size and throws an exception
			try {
				System.out.println(Files.size(Paths.get("c:/size/does/matter")));
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				System.out.println(Files.size(Paths.get("files/a.txt")));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		});
		
		m("walkFileTree", () -> {
		
			//Note: Depth, IOException, SimpleFileVisitor implements FileVisitor, not a functional interface, FileVisitResult.CONTINUE
		
			try {
				Files.walkFileTree(
					Paths.get("c:/temp"),
					new HashSet<FileVisitOption>(),
					5,
					new SimpleFileVisitor<Path>() {
						public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
							System.out.println(file);
							return FileVisitResult.CONTINUE;
						}
					}
					
				);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				System.out.println(Files.size(Paths.get("files/a.txt")));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		});
		
		
		m("read and write attributes", () -> {
		
			//Note two ways to retrieve file attributes. First is read only, second read write
					
			try {
					//static <A extends BasicFileAttributes> A 	readAttributes(Path path, Class<A> type, LinkOption... options)
					//static <V extends FileAttributeView> V 	getFileAttributeView(Path path, Class<V> type, LinkOption... options)
					
					//Note: BasicFileAttributes and BasicFileAttributeView. readAttributes and getAttributeView.
					
					BasicFileAttributes attr = Files.readAttributes(Paths.get("files2/file"), BasicFileAttributes.class);
					System.out.println(attr.isRegularFile());
					System.out.println(attr.isSymbolicLink());
					attr = Files.readAttributes(Paths.get("files2/filesymlink"), BasicFileAttributes.class);
					System.out.println(attr.isRegularFile());
					System.out.println(attr.isSymbolicLink());
					
					BasicFileAttributeView attrView = Files.getFileAttributeView(Paths.get("files2/file"), BasicFileAttributeView.class);
					attr = attrView.readAttributes();
					attrView.setTimes(null, null, null);
					
					
				
			} catch (IOException e) {
				e.printStackTrace();
			}
						
		});
		
		m("getOwner", () -> {
					
			try {
				UserPrincipal up = Files.getOwner(Paths.get("files2/file"));
				System.out.println(up.getName());
			} catch (IOException e) {
				e.printStackTrace();
			}
						
		});
		
		m("get attributes", () -> {
					
			try {

				System.out.println(Files.isExecutable(Paths.get("files2/file")));
				System.out.println(Files.isHidden(Paths.get("files2/file")));
				System.out.println(Files.isReadable(Paths.get("files2/file")));
				System.out.println(Files.isWritable(Paths.get("files2/file")));
			
			} catch (IOException e) {
				e.printStackTrace();
			}
						
		});
		
		
		m("move", () -> {
			
			//Note: Works with files, dirs and not empty dirs
			
			try {

				Path path = Paths.get("files2/movable");
				Path path2 = Paths.get("files2/movable2");
				
				if (Files.exists(path)) {
					Files.move(path, path2);
				} else if (Files.exists(path2)) {
					Files.move(path2, path);
				}
				
				Path path3 = Paths.get("files2/movableDir");
				Path path4 = Paths.get("files2/movableDir2");
				
				if (Files.exists(path3)) {
					Files.move(path3, path4);
				} else if (Files.exists(path4)) {
					Files.move(path4, path3);
				}
				
				Path path5 = Paths.get("files2/movableNonEmptyDir");
				Path path6 = Paths.get("files2/movableNonEmptyDir2");
				
				if (Files.exists(path5)) {
					Files.move(path5, path6);
				} else if (Files.exists(path6)) {
					Files.move(path6, path5);
				}
			
			} catch (IOException e) {
				e.printStackTrace();
			}
						
		});
		
		m("directoryStream", () -> {
		
			//Note: DirectoryStream is not a stream. It implements Iterable so forEach is possible
			//It iterates only through direct children of the given path
			try(DirectoryStream<Path> ds = Files.newDirectoryStream(Paths.get("c:/temp"));) {
				ds.forEach(System.out::println);
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		});
		
	
	}
	

	
	
	public static void m(String descr, Runnable r) throws Exception {
		System.out.print(++counter + " - ");
		System.out.println(descr);
		r.run();
		System.out.println();
	}


}
