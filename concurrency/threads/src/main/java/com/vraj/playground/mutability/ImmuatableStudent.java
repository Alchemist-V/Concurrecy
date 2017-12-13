/**
 * 
 */
package com.vraj.playground.mutability;

/**
 * Attaining immutability can be very useful in a multithreaded environemnt,
 * Here what we should keep in mind when trying to achieve immutable behavior.
 * 
 * <pre>
 * 1. Make your class final, so that no other classes can extend it.
 * 
 * 2. Make all your fields final, so that they’re initialized only once inside
 * the constructor and never modified afterward.
 * 
 * 3. Don’t expose setter methods.
 * 
 * 4. When exposing methods which modify the state of the class, you must always
 * return a new instance of the class.
 * 
 * 5. If the class holds a mutable object: 
 * 
 * 		5.1 Inside the constructor, make sure to use a clone copy of the passed 
 * argument and never set your mutable field to the real instance passed through 
 * constructor, this is to prevent the clients who pass the object from modifying
 * it afterwards. 
 * 		5.2 Make sure to always return a clone copy of the field and never
 * return the real object instance.
 * 
 * </pre>
 * 
 * @author vrajori
 *
 */
public final class ImmuatableStudent {

	private final int id;
	private final String name;
	private final Age age;

	public ImmuatableStudent(int id, String name, Age age) {
		super();
		this.id = id;
		this.name = name;

		// ensuring immutablity.
		Age cloneAge = new Age();
		cloneAge.setDay(this.age.getDay());
		cloneAge.setDay(this.age.getMonth());
		cloneAge.setDay(this.age.getYear());
		this.age = cloneAge;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Age getAge() {
		Age cloneAge = new Age();
		cloneAge.setDay(this.age.getDay());
		cloneAge.setDay(this.age.getMonth());
		cloneAge.setDay(this.age.getYear());
		return cloneAge;
	}

}
