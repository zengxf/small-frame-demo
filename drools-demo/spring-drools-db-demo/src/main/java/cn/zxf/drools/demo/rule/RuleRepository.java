package cn.zxf.drools.demo.rule;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RuleRepository extends JpaRepository<Rule, Long> {
    Rule findByRuleKey( String ruleKey );
}