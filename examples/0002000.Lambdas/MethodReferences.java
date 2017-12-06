import java.nio.file.*;
import java.nio.charset.*;
import java.io.*;
import java.util.*;
import java.util.stream.*;
import java.util.function.*;
import java.nio.file.*;

public class MethodReferences {

	public static int counter = 0;

	/*
	Here are common functional interfaces that can be replaced by method references.
	*/
	
	static interface A1 {void m();}
	static interface B1 {void m(int a);}
	static interface C1 {void m(int a, int b);}
	static interface D1 {void m(int a, int b, int c);}
	static interface E1 {void m(int a, String b, boolean c, Object d);}
	
	static interface A2 {int m();}
	static interface B2 {int m(int a);}
	static interface C2 {int m(int a, int b);}
	static interface D2 {int m(int a, int b, int c);}
	static interface E2 {int m(int a, String b, boolean c, Object d);}
	
	static interface A3 {String m();}
	static interface B3 {String m(int a);}
	static interface C3 {String m(int a, int b);}
	static interface D3 {String m(int a, int b, int c);}
	static interface E3 {String m(int a, String b, boolean c, Object d);}
	
	static interface A4<T> {T m();}
	static interface B4<T> {T m(int a);}
	static interface C4<T> {T m(int a, int b);}
	static interface D4<T> {T m(int a, int b, int c);}
	static interface E4<T> {T m(int a, String b, boolean c, T d);}
	
	
	static interface A5 {String m(InstanceImplementor inst);}
	static interface B5 {String m(InstanceImplementor inst, int a);}
	static interface C5 {String m(InstanceImplementor inst, int a, int b);}
	static interface D5 {String m(InstanceImplementor inst, int a, int b, int c);}
	static interface E5 {String m(InstanceImplementor inst, int a, String b, boolean c, Object d);}
	
	
	/*
	Here are examples of static methods that can fill functional interface parameters.
	*/
	
	static class StaticImplementor {
		
		public static void staticRefMethodA1() {System.out.println("staticRefMethodA1()");}
		public static void staticRefMethodB1(int a) {System.out.println("staticRefMethodB1(int a)");}
		public static void staticRefMethodC1(int a, int b) {System.out.println("staticRefMethodC1(int a, int b)");}
		public static void staticRefMethodD1(int a, int b, int c) {System.out.println("staticRefMethodD1(int a, int b, int c)");}
		public static void staticRefMethodE1(int a, String b, boolean c, Object d) {System.out.println("staticRefMethodE1(int a, String b, boolean c, Object d)");}
		
		public static int staticRefMethodA2() {System.out.println("staticRefMethodA2()"); return 0;}
		public static int staticRefMethodB2(int a) {System.out.println("staticRefMethodB2(int a)"); return 0;}
		public static int staticRefMethodC2(int a, int b) {System.out.println("staticRefMethodC2(int a, int b)"); return 0;}
		public static int staticRefMethodD2(int a, int b, int c) {System.out.println("staticRefMethodD2(int a, int b, int c)"); return 0;}
		public static int staticRefMethodE2(int a, String b, boolean c, Object d) {System.out.println("staticRefMethodE2(int a, String b, boolean c, Object d)"); return 0;}
		
		public static String staticRefMethodA3() {System.out.println("staticRefMethodA3()"); return "retVal";}
		public static String staticRefMethodB3(int a) {System.out.println("staticRefMethodB3(int a)"); return "retVal";}
		public static String staticRefMethodC3(int a, int b) {System.out.println("staticRefMethodC3(int a, int b)"); return "retVal";}
		public static String staticRefMethodD3(int a, int b, int c) {System.out.println("staticRefMethodD3(int a, int b, int c)"); return "retVal";}
		public static String staticRefMethodE3(int a, String b, boolean c, Object d) {System.out.println("staticRefMethodE3(int a, String b, boolean c, Object d)"); return "retVal";}
		
		public static Exception staticRefMethodA4() {System.out.println("staticRefMethodA4()"); return new IOException();}
		public static Exception staticRefMethodB4(int a) {System.out.println("staticRefMethodB4(int a)"); return new IOException();}
		public static Exception staticRefMethodC4(int a, int b) {System.out.println("staticRefMethodC4(int a, int b)"); return new IOException();}
		public static Exception staticRefMethodD4(int a, int b, int c) {System.out.println("staticRefMethodD4(int a, int b, int c)"); return new IOException();}
		public static Exception staticRefMethodE4(int a, String b, boolean c, Object d) {System.out.println("staticRefMethodE4(int a, String b, boolean c, Object d)"); return new IOException();}
		
	}
	
	/*
	Here are examples of instance methods that can fill functional interface parameters.
	*/
	static class InstanceImplementor {
		
		public void instRefMethodA1() {System.out.println("instRefMethodA1()");}
		public void instRefMethodB1(int a) {System.out.println("instRefMethodB1(int a)");}
		public void instRefMethodC1(int a, int b) {System.out.println("instRefMethodC1(int a, int b)");}
		public void instRefMethodD1(int a, int b, int c) {System.out.println("instRefMethodD1(int a, int b, int c)");}
		public void instRefMethodE1(int a, String b, boolean c, Object d) {System.out.println("instRefMethodE1(int a, String b, boolean c, Object d)");}
		
		public int instRefMethodA2() {System.out.println("instRefMethodA2()"); return 0;}
		public int instRefMethodB2(int a) {System.out.println("instRefMethodB2(int a)"); return 0;}
		public int instRefMethodC2(int a, int b) {System.out.println("instRefMethodC2(int a, int b)"); return 0;}
		public int instRefMethodD2(int a, int b, int c) {System.out.println("instRefMethodD2(int a, int b, int c)"); return 0;}
		public int instRefMethodE2(int a, String b, boolean c, Object d) {System.out.println("instRefMethodE2(int a, String b, boolean c, Object d)"); return 0;}
		
		public String instRefMethodA3() {System.out.println("instRefMethodA3()"); return "retVal";}
		public String instRefMethodB3(int a) {System.out.println("instRefMethodB3(int a)"); return "retVal";}
		public String instRefMethodC3(int a, int b) {System.out.println("instRefMethodC3(int a, int b)"); return "retVal";}
		public String instRefMethodD3(int a, int b, int c) {System.out.println("instRefMethodD3(int a, int b, int c)"); return "retVal";}
		public String instRefMethodE3(int a, String b, boolean c, Object d) {System.out.println("instRefMethodE3(int a, String b, boolean c, Object d)"); return "retVal";}
		
		public Exception instRefMethodA4() {System.out.println("instRefMethodA()"); return new IOException();}
		public Exception instRefMethodB4(int a) {System.out.println("instRefMethodB4(int a)"); return new IOException();}
		public Exception instRefMethodC4(int a, int b) {System.out.println("instRefMethodC4(int a, int b)"); return new IOException();}
		public Exception instRefMethodD4(int a, int b, int c) {System.out.println("instRefMethodD4(int a, int b, int c)"); return new IOException();}
		public Exception instRefMethodE4(int a, String b, boolean c, Object d) {System.out.println("instRefMethodE4(int a, String b, boolean c, Object d)"); return new IOException();}
		
		public String instRefMethodA5() {System.out.println("instRefMethodA5()"); return "retVal";}
		public String instRefMethodB5(int a) {System.out.println("instRefMethodB5(int a)"); return "retVal";}
		public String instRefMethodC5(int a, int b) {System.out.println("instRefMethodC5(int a, int b)"); return "retVal";}
		public String instRefMethodD5(int a, int b, int c) {System.out.println("instRefMethodD5(int a, int b, int c)"); return "retVal";}
		public String instRefMethodE5(int a, String b, boolean c, Object d) {System.out.println("instRefMethodE5(int a, String b, boolean c, Object d)"); return "retVal";}
		
	}
	
	
	
	static class Caller {
		
		public void call(A1 calledOne) {calledOne.m();}
		public void call(B1 calledOne) {calledOne.m(1);}
		public void call(C1 calledOne) {calledOne.m(1, 2);}
		public void call(D1 calledOne) {calledOne.m(1, 2, 3);}
		public void call(E1 calledOne) {calledOne.m(1, "arg", true, new Object());}
		
		public int call(A2 calledOne) {int n = calledOne.m(); return n;}
		public int call(B2 calledOne) {int n = calledOne.m(1); return n;}
		public int call(C2 calledOne) {int n = calledOne.m(1, 2); return n;}
		public int call(D2 calledOne) {int n = calledOne.m(1, 2, 3); return n;}
		public int call(E2 calledOne) {int n = calledOne.m(1, "arg", true, new Object()); return n;}
		
		public String call(A3 calledOne) {String s = calledOne.m(); return s;}
		public String call(B3 calledOne) {String s = calledOne.m(1); return s;}
		public String call(C3 calledOne) {String s = calledOne.m(1, 2); return s;}
		public String call(D3 calledOne) {String s = calledOne.m(1, 2, 3); return s;}
		public String call(E3 calledOne) {String s = calledOne.m(1, "arg", true, new Object()); return s;}
		
		public <T> T call(A4<T> calledOne) {T t = calledOne.m(); return t;}
		public <T> T call(B4<T> calledOne) {T t = calledOne.m(1); return t;}
		public <T> T call(C4<T> calledOne) {T t = calledOne.m(1, 2); return t;}
		public <T> T call(D4<T> calledOne) {T t = calledOne.m(1, 2, 3); return t;}
		public <T> T call(E4<T> calledOne) {T t = calledOne.m(1, "arg", true, null); return t;}

		public String call(InstanceImplementor impl, A5 calledOne) {String s = calledOne.m(impl); return s;}
		public String call(InstanceImplementor impl, B5 calledOne) {String s = calledOne.m(impl, 1); return s;}
		public String call(InstanceImplementor impl, C5 calledOne) {String s = calledOne.m(impl, 1, 2); return s;}
		public String call(InstanceImplementor impl, D5 calledOne) {String s = calledOne.m(impl, 1, 2, 3); return s;}
		public String call(InstanceImplementor impl, E5 calledOne) {String s = calledOne.m(impl, 1, "arg", true, new Object()); return s;}
		
		
		
		
		
	}
	
	
	public static void main(String[] args) throws Exception {
						
		m("Calling method references", () -> {

			try {

				Caller c = new Caller();
				int n;
				String s;
				Exception e;
				InstanceImplementor inst = new InstanceImplementor();
				
				System.out.println("---------------------------");
				
				//Calling static method references
				
				c.call((A1)StaticImplementor::staticRefMethodA1);				
				c.call((B1)StaticImplementor::staticRefMethodB1);
				c.call((C1)StaticImplementor::staticRefMethodC1);
				c.call((D1)StaticImplementor::staticRefMethodD1);
				c.call((E1)StaticImplementor::staticRefMethodE1);
				
				
				n = c.call(StaticImplementor::staticRefMethodA2);
				n = c.call(StaticImplementor::staticRefMethodB2);
				n = c.call(StaticImplementor::staticRefMethodC2);
				n = c.call(StaticImplementor::staticRefMethodD2);
				n = c.call(StaticImplementor::staticRefMethodE2);
				
				
				s = c.call(StaticImplementor::staticRefMethodA3);
				s = c.call(StaticImplementor::staticRefMethodB3);
				s = c.call(StaticImplementor::staticRefMethodC3);
				s = c.call(StaticImplementor::staticRefMethodD3);
				s = c.call(StaticImplementor::staticRefMethodE3);
				
				
				e = c.call(StaticImplementor::staticRefMethodA4);
				e = c.call(StaticImplementor::staticRefMethodB4);
				e = c.call(StaticImplementor::staticRefMethodC4);
				e = c.call(StaticImplementor::staticRefMethodD4);
				e = c.call((E4<Exception>)StaticImplementor::staticRefMethodE4); //Without the cast, this would be ambiguous with call(E1). Why? Why not with E2 and E3
				
				
				System.out.println("---------------------------");
				
				//Calling instance method references
				
				c.call(inst::instRefMethodA1);
				c.call(inst::instRefMethodB1);
				c.call(inst::instRefMethodC1);
				c.call(inst::instRefMethodD1);
				c.call(inst::instRefMethodE1);
				
				n = c.call(inst::instRefMethodA2);
				n = c.call(inst::instRefMethodB2);
				n = c.call(inst::instRefMethodC2);
				n = c.call(inst::instRefMethodD2);
				n = c.call(inst::instRefMethodE2);
				
				s = c.call(inst::instRefMethodA3);
				s = c.call(inst::instRefMethodB3);
				s = c.call(inst::instRefMethodC3);
				s = c.call(inst::instRefMethodD3);
				s = c.call(inst::instRefMethodE3);
				
				e = c.call(inst::instRefMethodA4);
				e = c.call(inst::instRefMethodB4);
				e = c.call(inst::instRefMethodC4);
				e = c.call(inst::instRefMethodD4);
				e = c.call((E4<Exception>)inst::instRefMethodE4); //Without the cast, this would be ambiguous with call(E1). Why? Why not with E2 and E3
				
				
				System.out.println("---------------------------");
				
				//Calling instance method references on unknown object
				
				s = c.call(inst, InstanceImplementor::instRefMethodA5);
				s = c.call(inst, InstanceImplementor::instRefMethodB5);
				s = c.call(inst, InstanceImplementor::instRefMethodC5);
				s = c.call(inst, InstanceImplementor::instRefMethodD5);
				s = c.call(inst, InstanceImplementor::instRefMethodE5);
				
				System.out.println("---------------------------");
				
				//Calling constructor references with their equivalent lambdas. 
				
				System.out.println(c.call((B2)v -> new Integer(v)));
				System.out.println(c.call((B2)Integer::new));
				
				System.out.println(c.call((A4<String>)() -> new String()));
				System.out.println(c.call((A4<String>)String::new));
				
				System.out.println(c.call((A4<Exception>)() -> new Exception()));
				System.out.println(c.call((A4<Exception>)Exception::new));
				
				
				System.out.println(c.call((A4<ArrayList>)() -> new ArrayList()));
				System.out.println(c.call((A4<ArrayList>)ArrayList::new));
				
				
			} catch(Exception e) {
				throw new RuntimeException(e);
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
