package afanasjew.experiments;

import java.util.HashMap;
import java.util.Map;

class Person {
	public String name;
	public String lastName;
	public String city;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
	
}

public class HashCodes {

	public static void main(String[] args) {
		Map<Person, Object> m = new HashMap<Person, Object>();
		
		Person p = new Person();
		p.name = "Anton";
		p.lastName = "Afanasjew";
		p.city = "Munich";
		
		m.put(p, new Object()); 

		System.out.println(m.get(p)); //Found
		
		p.city = "Warsaw";
		
		System.out.println(m.get(p)); //Found
		
		p.name = "Michal";
		p.lastName = "Skaryszewski";
		
		System.out.println(m.get(p)); //Not found
		
	}
	
}
