public class ExceptionHandlingInTryWiths {


	static class DangerousAutoCloseable implements AutoCloseable {
		public void close() throws Exception {
			throw new Exception();
		}
	}
	
	static class HarmlessAutoCloseable implements AutoCloseable {
		public void close() {
			throw new RuntimeException();
		}
	}
	

	public static void main(String[] args) {

		
		//Note: This compiles just fine because HarmlessAutoCloseable does not declare an exception (even if it could according to the inherited method signature)
		try(HarmlessAutoCloseable a = new HarmlessAutoCloseable()) {

		}
		
		//Note: This compiles only because the exception is caught. DangerousAutoCloseable::close() declares an Exception like its parent class, so it needs to be caught or declared.
		try(DangerousAutoCloseable a = new DangerousAutoCloseable()) {
			
		} catch (Exception e) {
		
		}
		
		
		
	
	}

}