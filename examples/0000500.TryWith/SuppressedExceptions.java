public class SuppressedExceptions {


	static class DangerousAutoCloseable implements AutoCloseable {
		public void close() throws Exception {
			throw new Exception("Dangerous");
		}
	}
	
	static class HarmlessAutoCloseable implements AutoCloseable {
		public void close() {
			throw new RuntimeException("Harmless");
		}
	}
	

	public static void main(String[] args) {
		
		//Note which exception is thrown and how many suppressed exceptions it contains depending on the scenario.
		
		try(DangerousAutoCloseable a = new DangerousAutoCloseable();HarmlessAutoCloseable b = new HarmlessAutoCloseable()) {
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getSuppressed().length);
		}
		
		try(HarmlessAutoCloseable b = new HarmlessAutoCloseable();DangerousAutoCloseable a = new DangerousAutoCloseable()) {
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getSuppressed().length);
		}
		
		try(HarmlessAutoCloseable b = new HarmlessAutoCloseable();DangerousAutoCloseable a = new DangerousAutoCloseable()) {
			throw new RuntimeException("Main");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getSuppressed().length);
		}
		
		
		
	
	}

}