import java.nio.file.*;
import java.nio.charset.*;
import java.io.*;
import java.util.*;
import java.util.stream.*;
import java.util.function.*;
import java.nio.file.*;


/*

static Path 	createLink(Path link, Path existing)
static Path 	createSymbolicLink(Path link, Path target, FileAttribute<?>... attrs)
static Path 	createTempDirectory(Path dir, String prefix, FileAttribute<?>... attrs)
static Path 	createTempDirectory(String prefix, FileAttribute<?>... attrs)
static Path 	createTempFile(Path dir, String prefix, String suffix, FileAttribute<?>... attrs)
static Path 	createTempFile(String prefix, String suffix, FileAttribute<?>... attrs)

static Object 	getAttribute(Path path, String attribute, LinkOption... options)
static <V extends FileAttributeView> V 	getFileAttributeView(Path path, Class<V> type, LinkOption... options)

static FileStore 	getFileStore(Path path)
static FileTime 	getLastModifiedTime(Path path, LinkOption... options)

static UserPrincipal 	getOwner(Path path, LinkOption... options)

static Set<PosixFilePermission> 	getPosixFilePermissions(Path path, LinkOption... options)

static boolean 	isDirectory(Path path, LinkOption... options)
static boolean 	isExecutable(Path path)
static boolean 	isHidden(Path path)
static boolean 	isReadable(Path path)
static boolean 	isRegularFile(Path path, LinkOption... options)
static boolean 	isSameFile(Path path, Path path2)
static boolean 	isSymbolicLink(Path path)
static boolean 	isWritable(Path path)
static Path 	move(Path source, Path target, CopyOption... options)

static BufferedReader 	newBufferedReader(Path path, Charset cs)
static BufferedWriter 	newBufferedWriter(Path path, Charset cs, OpenOption... options)
static SeekableByteChannel 	newByteChannel(Path path, OpenOption... options)
static SeekableByteChannel 	newByteChannel(Path path, Set<? extends OpenOption> options, FileAttribute<?>... attrs)
static DirectoryStream<Path> 	newDirectoryStream(Path dir)
static DirectoryStream<Path> 	newDirectoryStream(Path dir, DirectoryStream.Filter<? super Path> filter)
static DirectoryStream<Path> 	newDirectoryStream(Path dir, String glob)
static InputStream 	newInputStream(Path path, OpenOption... options)
static OutputStream 	newOutputStream(Path path, OpenOption... options)
static boolean 	notExists(Path path, LinkOption... options)
static String 	probeContentType(Path path)
static byte[] 	readAllBytes(Path path)
static List<String> 	readAllLines(Path path, Charset cs)
static <A extends BasicFileAttributes> A 	readAttributes(Path path, Class<A> type, LinkOption... options)
static Map<String,Object> 	readAttributes(Path path, String attributes, LinkOption... options)
static Path 	readSymbolicLink(Path link)
static Path 	setAttribute(Path path, String attribute, Object value, LinkOption... options)
static Path 	setLastModifiedTime(Path path, FileTime time)
static Path 	setOwner(Path path, UserPrincipal owner)
static Path 	setPosixFilePermissions(Path path, Set<PosixFilePermission> perms)
static long 	size(Path path)
static Path 	walkFileTree(Path start, FileVisitor<? super Path> visitor)
static Path 	walkFileTree(Path start, Set<FileVisitOption> options, int maxDepth, FileVisitor<? super Path> visitor)
static Path 	write(Path path, byte[] bytes, OpenOption... options)
static Path 	write(Path path, Iterable<? extends CharSequence> lines, Charset cs, OpenOption... options)

*/


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
		
		
	}
	
	public static void m(String descr, Runnable r) throws Exception {
		System.out.print(++counter + " - ");
		System.out.println(descr);
		r.run();
		System.out.println();
	}


}
