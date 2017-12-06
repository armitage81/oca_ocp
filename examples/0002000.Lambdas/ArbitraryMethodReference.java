import java.nio.file.*;
import java.nio.charset.*;
import java.io.*;
import java.util.*;
import java.util.stream.*;
import java.util.function.*;
import java.nio.file.*;

public class ArbitraryMethodReference {

	public static int counter = 0;

	static interface A { StringBuilder m(StringBuilder sb, String s);}
	
	/*
	
	Interface A has a method m that expects two parameters - StringBuilder and String - and returns a StringBuilder.
	One possible implementation would be to add the string to the string builder and return the string builder
	
	This could be implemented in three ways:
	
	1) Anonymous class
	
		class B implements A {
			StringBuilder m(StringBuilder sb, String s) {
				sb.append(s);
				return sb;
			}
		}
	
	2) Lambda
	
		(sb, s) -> {sb.append(s); return sb;}
	
	3) Arbitrary type method reference
	
		StringBuilder::append
		
		
	Note how the three implementations are equivalent. You could say:
	
	A a;
	a = new B();
	a = (sb, s) -> {sb.append(s); return sb;};
	a = StringBuilder::append
	
	Note for the latter: If you have a method reference Type::method and the Type::method is not static and it expects n parameters of type ParamType1, ParamType2, ... ParamTypeN
	then it will fit in the functional interface with the signature m(Type, ParamType1, ParamType2, ... ParamTypeN) (of course, return types must match)
	
	Example:
	
		If you have: 
		
			class V { static boolean m(String s, int i, Object o) {};} 
			class X { boolean m(String s, int i, Object o) {};} 
		
		and interfaces
		
			interface Y {boolean method(X x, String s, int i, Object o)} 
			interface Z {boolean method(String s, int i, Object o)} 
			
		then you can say:
		
		Y y = X::m;
		Z z = V::m;
		
		but you can't say
		
		Y y = V::m;
		Z z = X::m;
	
	
		Usage of method references is the same as with all interfaces.
		
		Y y = X::m;
		Z z = V::m;
		
		y.method(new X(), "", 0, new Object());
		z.method("", 0, new Object());
	
	
	*/
	
	public static void main(String[] args) throws Exception {

		A a = StringBuilder::append;
		
		
		StringBuilder sb = a.m(new StringBuilder(), "Deine Mudda!");
		System.out.println(sb);
		
		
	}
	
	public static void m(String descr, Runnable r) throws Exception {
		System.out.print(++counter + " - ");
		System.out.println(descr);
		r.run();
		System.out.println();
	}


}
