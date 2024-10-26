package com.ruleengine.ast.repository;

import com.ruleengine.ast.model.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleRepository extends JpaRepository<Rule, Long> {
    // Custom query methods can be defined here if needed
}

