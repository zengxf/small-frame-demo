package cn.zxf.test.base.stream;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by zengxf on 2018/9/18.
 */
public class TestStream {

    public static void main(String[] arr) {
        IntStream.rangeClosed(1, 10)
                .dropWhile(i -> i < 5)
                .forEach(System.out::println);
        System.out.println("-----------");

        IntStream.rangeClosed(1, 10)
                .takeWhile(i -> i < 5)
                .forEach(System.out::println);
        System.out.println("-----------");

        Map<Integer, String> map = new HashMap<>();
        map.put(1, "One");
        map.put(2, "Two");
        map.put(3, null);
        map.put(4, "four");
        Set<String> nonNullValues = map.entrySet()
                .stream()
                .flatMap(e -> Stream.ofNullable(e.getValue()))
//                .map(e -> e.getValue())
                .collect(Collectors.toSet());
        System.out.println(nonNullValues);
        System.out.println("-----------");

        List<Integer> list = Stream.iterate(1, n -> n <= 10, n -> n + 1)
                .collect(Collectors.toList());
        System.out.println("Integers from 1 to 10: " + list);
        System.out.println("-----------");

        Map<String, List<Employee>> empGroupedByDeptWithSalaryGt1900 = Employee.employees()
                .stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.filtering(e -> e.salary > 1900.00, Collectors.toList())) // 避免没有的不出来
                );
        System.out.println(empGroupedByDeptWithSalaryGt1900);
        System.out.println("-----------");

        Map<String, Set<List<String>>> langByDept = Employee.employees()
                .stream()
                .collect(Collectors.groupingBy(Employee::getDepartment,
                        Collectors.mapping(Employee::getSpokenLanguages, Collectors.toSet())));
        System.out.println(langByDept);
        System.out.println("-----------");

        Map<String, Set<String>> langByDept2 = Employee.employees()
                .stream()
                .collect(Collectors.groupingBy(Employee::getDepartment,
                        Collectors.flatMapping(e -> e.getSpokenLanguages().stream(), Collectors.toSet()))); // 扁平化
        System.out.println(langByDept2);
    }

    static class Employee {
        String name;
        String department;
        double salary;
        List<String> spokenLanguages;

        public Employee(String name, String department, double salary, List<String> spokenLanguages) {
            this.name = name;
            this.department = department;
            this.salary = salary;
            this.spokenLanguages = spokenLanguages;
        }

        public String getDepartment() {
            return department;
        }

        public List<String> getSpokenLanguages() {
            return spokenLanguages;
        }

        @Override
        public String toString() {
            return "[" + name + ", " + department + ", " + salary + ", " + spokenLanguages + "]";
        }

        public static List<Employee> employees() {
            return List.of(
                    new Employee("John", "Sales", 1000.89, List.of("English", "French")),
                    new Employee("Wally", "Sales", 900.89, List.of("Spanish", "Wu")),
                    new Employee("Ken", "Sales", 1900.00, List.of("English", "French")),
                    new Employee("Li", "HR", 1950.89, List.of("Wu", "Lao")),
                    new Employee("Manuel", "IT", 2001.99, List.of("English", "German")),
                    new Employee("Tony", "IT", 1700.89, List.of("English"))
            );
        }
    }

}
