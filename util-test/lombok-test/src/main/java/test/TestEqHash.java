package test;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.LinkedHashSet;

/**
 * <br/>
 * Created by ZXFeng on 2022/5/6.
 */
public class TestEqHash {

    public static void main(String[] args) {
        LinkedHashSet<Project> projects = new LinkedHashSet<>();

        Project project = new Project();
        project.setId("001");
        project.setName("test");
        projects.add(project);

        Project project1 = new Project();
        project1.setId("001");
        project1.setName("test2");
        projects.add(project1);

        System.out.println("-------------");
        System.out.println(projects);
        System.out.println("-------------");
    }

    @Data
    @EqualsAndHashCode(onlyExplicitlyIncluded = true)
    public static class Project {
        @EqualsAndHashCode.Include // 只对 ID 进行比较和 Hash，可反编译查看验证
        private String id;
        private String name;
    }

}
