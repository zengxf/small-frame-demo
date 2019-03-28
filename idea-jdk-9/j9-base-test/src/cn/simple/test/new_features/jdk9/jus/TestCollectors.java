package cn.simple.test.new_features.jdk9.jus;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

/**
 * Created by zengxf on 2017/10/9.
 */
public class TestCollectors {

    public static void main(String[] arr) {
//        filtering();
        test_flatMapping();
    }

    static void test_flatMapping() {
        Map<String, Set<List<String>>> langByDept = Employee.employees()
                .stream()
                .collect(groupingBy(Employee::getDepartment,
                        mapping(Employee::getSpokenLanguages, toSet())));
        System.out.println(langByDept);

        Map<String, Set<String>> langByDept2 = Employee.employees()
                .stream()
                .collect(groupingBy(Employee::getDepartment,
                        flatMapping(e -> e.getSpokenLanguages().stream(), toSet())));
        System.out.println(langByDept2);
    }

    static void filtering() {
        // java8
        Map<String, List<Employee>> empGroupedByDept = Employee.employees()
                .stream()
                .filter(e -> e.getSalary() > 1900)
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.toList()));
        System.out.println(empGroupedByDept);

        // 先聚合后过滤
        Map<String, List<Employee>> empGroupedByDeptWithSalaryGt1900 = Employee.employees()
                .stream()
                .collect(Collectors.groupingBy(Employee::getDepartment,
                        Collectors.filtering(e -> e.getSalary() > 1900.00, Collectors.toList())));
        System.out.println(empGroupedByDeptWithSalaryGt1900);
    }

    static class Employee {
        private String name;
        private String department;
        private double salary;
        private List<String> spokenLanguages;

        public Employee(String name, String department, double salary,
                        List<String> spokenLanguages) {
            this.name = name;
            this.department = department;
            this.salary = salary;
            this.spokenLanguages = spokenLanguages;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public double getSalary() {
            return salary;
        }

        public void setSalary(double salary) {
            this.salary = salary;
        }

        public List<String> getSpokenLanguages() {
            return spokenLanguages;
        }

        public void setSpokenLanguages(List<String> spokenLanguages) {
            this.spokenLanguages = spokenLanguages;
        }

        @Override
        public String toString() {
            return "{" + name + ", " + department + ", " + salary + ", " + spokenLanguages + "}";
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
