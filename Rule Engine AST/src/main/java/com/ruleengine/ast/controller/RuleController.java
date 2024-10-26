package com.ruleengine.ast.controller;

import com.ruleengine.ast.model.Node;
import com.ruleengine.ast.model.Rule;
import com.ruleengine.ast.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rules")
public class RuleController {

    @Autowired
    private RuleService ruleService;

    @PostMapping("/create")
    public ResponseEntity<Rule> createRule(@RequestBody Map<String, String> request) {
        String ruleString = request.get("ruleString");
        Rule rule = ruleService.createRule(ruleString);
        return ResponseEntity.ok(new Rule(ruleString)); // Return created rule or AST as needed
    }

    @PostMapping("/combine")
    public ResponseEntity<Node> combineRules(@RequestBody Map<String, List<String>> request) {
        List<String> rules = request.get("rules");
        Node combinedAst = ruleService.combineRules(rules);
        return ResponseEntity.ok(combinedAst); // Return combined AST
    }

    @PostMapping("/evaluate/{id}")
    public ResponseEntity<Boolean> evaluateRule(@PathVariable Long id, @RequestBody Map<String, Object> data) {
        boolean result = ruleService.evaluateRule(id, data);
        return ResponseEntity.ok(result);
    }
}