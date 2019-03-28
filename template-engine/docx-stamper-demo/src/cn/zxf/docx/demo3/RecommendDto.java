package cn.zxf.docx.demo3;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecommendDto {

    private String       positionName;
    private String       companyAlias;
    private String       recommendReason;
    private String       salaryDetail;    // 薪资情况
    private CandidateDto cdd;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CandidateDto {
        private String             name;      // 姓名
        private String             gender;    // 性别
        private String             birthDate; // 生日
        private Integer            age;       // 年龄
        private String             marital;   // 婚姻情况
        private String             degree;    // 最高学历
        private Integer            workRange; // 工作年限
        private String             city;      // 所在城市

        private String             career;    // 工作经历

        private List<EducationDto> educations;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EducationDto {
        private String school;    // 学校名称
        private String major;     // 专业名称
        private String degree;    // 学历水平
        private String startDate; // 开始日期
        private String endDate;   // 结束日期
    }

}
