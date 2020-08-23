package org.codejudge.sb.controller;

import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.codejudge.sb.model.GraphNode;
import org.codejudge.sb.model.GraphResult;
import org.codejudge.sb.service.Neo4jGraphImpl;
import org.neo4j.graphdb.Node;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class AppController {

    private Neo4jGraphImpl neo4jGraph;

    private Neo4jGraphImpl getNeoInstance(){
        if(this.neo4jGraph == null){
            this.neo4jGraph = new Neo4jGraphImpl();
        }
        return this.neo4jGraph;
    }

    @ApiOperation("This is the hello world api")
    @GetMapping("/")
    public String hello() {
        return "Hello World!!";
    }

    @GetMapping("/get-nodes-for-method-declaration")
    public Map<String, List<Map<String, String>>>getAllNodeForMethodDeclaraion() {
        this.neo4jGraph = this.getNeoInstance();
        String cypherQuery = "";
        List<GraphResult> results = this.neo4jGraph.getResult(cypherQuery);
        Map<String, List<Map<String, String>> > res = new HashMap<>();
        res.put("result", this.neo4jGraph.serialze(results));
        return res;
    }

    @GetMapping("/get-nodes-for-class-interface")
    public Map<String, List<Map<String, String>>> getNodesForClassandInterface() {
        this.neo4jGraph = this.getNeoInstance();
        String cypherQuery = "";
        List<GraphResult> results = this.neo4jGraph.getResult(cypherQuery);
        Map<String, List<Map<String, String>> > res = new HashMap<>();
        res.put("result", this.neo4jGraph.serialze(results));
        return res;
    }

    @GetMapping("/package-of-a-given-classes")
    public Map<String, List<Map<String, String>>> getPackageOfAGivenClass() {
        this.neo4jGraph = this.getNeoInstance();
        String cypherQuery = "";
        List<GraphResult> results = this.neo4jGraph.getResult(cypherQuery);
        Map<String, List<Map<String, String>> > res = new HashMap<>();
        res.put("result", this.neo4jGraph.serialze(results));
        return res;
    }

    @GetMapping("/get-all-classes-of-a-package")
    public Map<String, List<Map<String, String>>> getAllClassesOfAPackage() {
        this.neo4jGraph = this.getNeoInstance();
        String cypherQuery = "";
        List<GraphResult> results = this.neo4jGraph.getResult(cypherQuery);
        Map<String, List<Map<String, String>> > res = new HashMap<>();
        res.put("result", this.neo4jGraph.serialze(results));
        return res;
    }

    @GetMapping("/get-methods-class-both-inherited-and-declared")
    public Map<String, List<Map<String, String>>> getMethodInheritAndDeclared() {
        this.neo4jGraph = this.getNeoInstance();
        String cypherQuery = "";
        List<GraphResult> results = this.neo4jGraph.getResult(cypherQuery);
        Map<String, List<Map<String, String>> > res = new HashMap<>();
        res.put("result", this.neo4jGraph.serialze(results));
        return res;
    }


    @GetMapping("/get-transitive-closure-of-types")
    public Map<String, List<Map<String, String>>> getTransitiveClosureOfTypes() {
        this.neo4jGraph = this.getNeoInstance();
        String cypherQuery = "";
        List<GraphResult> results = this.neo4jGraph.getResult(cypherQuery);
        Map<String, List<Map<String, String>> > res = new HashMap<>();
        res.put("result", this.neo4jGraph.serialze(results));
        return res;
    }

    @GetMapping("/get-null-literal-value")
    public Map<String, List<Map<String, String>>> getNullLiteralValue() {
        this.neo4jGraph = this.getNeoInstance();
        String cypherQuery = "";
        List<GraphResult> results = this.neo4jGraph.getResult(cypherQuery);
        Map<String, List<Map<String, String>> > res = new HashMap<>();
        res.put("result", this.neo4jGraph.serialze(results));
        return res;
    }

    @GetMapping("/get-if-statements-where-null")
    public Map<String, List<Map<String, String>>> getIfStatementWhereNull() {
        this.neo4jGraph = this.getNeoInstance();
        String cypherQuery = "";
        List<GraphResult> results = this.neo4jGraph.getResult(cypherQuery);
        Map<String, List<Map<String, String>> > res = new HashMap<>();
        res.put("result", this.neo4jGraph.serialze(results));
        return res;
    }


    @GetMapping("/get-cyclomatic-complexity-class")
    public Map<String, List<Map<String, String>>> getCyclomaticComplexityClass() {
        this.neo4jGraph = this.getNeoInstance();
        String cypherQuery = "";
        List<GraphResult> results = this.neo4jGraph.getResult(cypherQuery);
        Map<String, List<Map<String, String>> > res = new HashMap<>();
        res.put("result", this.neo4jGraph.serialze(results));
        return res;
    }


    @GetMapping("/get-all-methods-with-complexity")
    public Map<String, List<Map<String, String>>> getAllMethodsWithComplexity() {
        this.neo4jGraph = this.getNeoInstance();
        String cypherQuery = "";
        List<GraphResult> results = this.neo4jGraph.getResult(cypherQuery);
        Map<String, List<Map<String, String>> > res = new HashMap<>();
        res.put("result", this.neo4jGraph.serialze(results));
        return res;
    }


    @GetMapping("/get-all-methods-with4-params")
    public Map<String, List<Map<String, String>>> getAllMethodWithParams() {
        this.neo4jGraph = this.getNeoInstance();
        String cypherQuery = "";
        List<GraphResult> results = this.neo4jGraph.getResult(cypherQuery);
        Map<String, List<Map<String, String>> > res = new HashMap<>();
        res.put("result", this.neo4jGraph.serialze(results));
        return res;
    }

    @GetMapping("/get-methods-with-50-lines")
    public Map<String, List<Map<String, String>>> getMethodWith50Lines() {
        this.neo4jGraph = this.getNeoInstance();
        String cypherQuery = "";
        List<GraphResult> results = this.neo4jGraph.getResult(cypherQuery);
        Map<String, List<Map<String, String>> > res = new HashMap<>();
        res.put("result", this.neo4jGraph.serialze(results));
        return res;
    }



}
