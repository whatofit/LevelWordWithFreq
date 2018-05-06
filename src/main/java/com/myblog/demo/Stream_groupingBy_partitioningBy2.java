package com.myblog.demo;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Stream_groupingBy_partitioningBy2 {

	public static void main(String[] args) {
//		List<Person> list = Arrays.asList(new Person("Tom", "male"), new Person("Jerry", "male"));
//		Map<String, List<Person>> grouped = new HashMap<>();
//		List<Person> maleGroup = new ArrayList<>();
//		List<Person> femaleGroup = new ArrayList<>();
//		for (Person person : list) {
//			if (person.gender.equals("male")) {
//				maleGroup.add(person);
//			} else {
//				femaleGroup.add(person);
//			}
//		}
//		grouped.put("male", maleGroup);
//		grouped.put("female", femaleGroup);
//		System.out.println(grouped);
		
		List<Person> list = Arrays.asList(new Person("Tom", "male"), new Person("Jerry", "male"));
		
		Map<String, List<Person>> grouped = list.stream().collect(Collectors.groupingBy(person -> person.gender));
//		Map<String, List<Person>> grouped = list.stream().collect(Collectors.groupingBy(Person::getGender));
//		{male=[Tom, Jerry]}
		System.out.println(grouped);
		
//		Map<String, List<Person>> grouped = list.stream().collect(Collectors.groupingBy(person -> "male".equals(person.gender) ? "male" : "female"));
////		{male=[Tom, Jerry]}
//		System.out.println(grouped);
		
//		Map<Boolean, List<Person>> grouped = list.stream().collect(Collectors.partitioningBy(t -> "male".equals(t.name)));
//		//{false=[Tom, Jerry], true=[]}
//		System.out.println(grouped);
	}

}

class Person {
	public final String name;
	public final String gender;

	public Person(String name, String gender) {
		this.name = name;
		this.gender = gender;
	}

	
	public String getName() {
		return name;
	}


	public String getGender() {
		return gender;
	}


	@Override
	public String toString() {
		return name;
	}
}