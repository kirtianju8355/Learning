package com.ruleengine.ast.service;

import com.ruleengine.ast.model.Node;
import com.ruleengine.ast.model.Rule;
import com.ruleengine.ast.repository.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RuleService {

    @Autowired
    private RuleRepository ruleRepository;

    public Rule createRule(String ruleString) {
        // Create and save the rule to the database
        Rule rule = new Rule(ruleString);
        rule = ruleRepository.save(rule); // Persist and return rule with generated ID
        return rule;
    }
    
    private Node parseRule(String ruleString) {
        // Logic to parse the ruleString into AST (placeholder)
    	Node rootNode = new Node();
        rootNode.setType("operator");
        rootNode.setValue(ruleString);
        return rootNode;
    }

    public Node combineRules(List<String> rules) {
        Node combinedRoot = new Node();
        combinedRoot.setType("operator");
        combinedRoot.setValue("AND");
        // Logic to combine rules into a single AST
        return combinedRoot;
    }

    public boolean evaluateRule(Long id, Map<String, Object> data) {
        // Logic to retrieve the rule by ID and evaluate it
        Rule rule = ruleRepository.findById(id).orElseThrow(() -> new RuntimeException("Rule not found"));
        Node ast = parseRule(rule.getRuleString()); // Or retrieve AST if stored
        // Logic to evaluate the AST against the provided data (placeholder)
        return true; // Placeholder for actual evaluation logic
    }
}


