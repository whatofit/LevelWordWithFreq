package com.level.demo;

//http://www.importnew.com/17313.html
//Java 8 Streams API：对Stream分组和分区
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Stream_groupingBy_partitioningBy {

	public static void main(String[] args) {
		List<Employee> employees = getUserList();
		Map<String, List<Employee>> userGroupMap = employees.stream().collect(Collectors.groupingBy(Employee::getCity));
		//{New York=[Charles], Hong Kong=[Dorothy], London=[Alice, Bob]}
		System.out.println(userGroupMap.toString());
		// output(userGroupMap);

		Map<String, Long> numEmployeesByCity = employees.stream().collect(Collectors.groupingBy(Employee::getCity, Collectors.counting()));
		//{New York=1, Hong Kong=1, London=2}
		System.out.println(numEmployeesByCity.toString());
		
		Map<String, Double> avgSalesByCity = employees.stream().collect(Collectors.groupingBy(Employee::getCity, Collectors.averagingInt(Employee::getSales)));
		//{New York=160.0, Hong Kong=190.0, London=175.0}
		System.out.println(avgSalesByCity.toString());
		
		Map<Boolean, List<Employee>> partitioned = employees.stream().collect(Collectors.partitioningBy(e -> e.getSales() > 150));
		//{false=[Bob], true=[Alice, Charles, Dorothy]}
		System.out.println(partitioned.toString());

		Map<Boolean, Map<String, Long>> result = employees.stream().collect(Collectors.partitioningBy(e -> e.getSales() > 150, Collectors.groupingBy(Employee::getCity, Collectors.counting())));
		//{false={London=1}, true={New York=1, Hong Kong=1, London=1}}
		System.out.println(result.toString());

	}

	public static List<Employee> getUserList() {
		Employee user1 = new Employee("Alice", "London", 200);
		Employee user2 = new Employee("Bob", "London", 150);
		Employee user3 = new Employee("Charles", "New York", 160);
		Employee user4 = new Employee("Dorothy", "Hong Kong", 190);

		List<Employee> list = new ArrayList<Employee>();
		list.add(user1);
		list.add(user2);
		list.add(user3);
		list.add(user4);

		return list;
	}

	// 输出2
	public static void output(Map<String, List<Employee>> map) {
		Set<Entry<String, List<Employee>>> entrySet = map.entrySet();
		Iterator<Entry<String, List<Employee>>> it = entrySet.iterator();
		System.out.print("{");
		for (int i = 0; it.hasNext(); i++) {
			Entry<String, List<Employee>> next = it.next();
			System.out.print((i > 0 ? ", " : "") + next.getKey() + "=[");
			List<Employee> empList = next.getValue();
			for (int j = 0; j < empList.size(); j++) {
				Employee emp = empList.get(j);
				System.out.print((j > 0 ? ", " : "") + emp.getName());
			}
			System.out.print("]");
		}
		System.out.print("}");
	}

}

// http://www.importnew.com/17313.html
// Java 8 Streams API：对Stream分组和分区

// 这篇文章展示了如何使用 Streams API 中的 Collector 及 groupingBy 和 partitioningBy
// 来对流中的元素进行分组和分区。

// 思考一下 Employee 对象流，每个对象对应一个名字、城市和销售数量，如下表所示：
// +----------+------------+-----------------+
// | Name | City | Number of Sales |
// +----------+------------+-----------------+
// | Alice | London | 200 |
// | Bob | London | 150 |
// | Charles | New York | 160 |
// | Dorothy | Hong Kong | 190 |
// +----------+------------+-----------------+

class Employee {
	private String name;
	private String city;
	private int sales;

	public Employee() {
	}

	public Employee(String name, String city, int sales) {
		this.name = name;
		this.city = city;
		this.sales = sales;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getSales() {
		return sales;
	}

	public void setSales(int sales) {
		this.sales = sales;
	}

//	@Override
//	public String toString() {
//		return "name=" + name + ", city=" + (city == null ? "" : city) + ", sales=" + sales;
//	}
	@Override
	public String toString() {
		return name;
	}
}