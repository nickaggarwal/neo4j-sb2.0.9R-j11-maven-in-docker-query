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
        String cypherQuery = "MATCH (method:MethodDeclaration)-[r]->(someChild) " +
                "RETURN id(method), properties(method), labels(method), type(r), id(someChild), labels(someChild) " +
                "LIMIT 10;";
        List<GraphResult> results = this.neo4jGraph.getResult(cypherQuery);
        Map<String, List<Map<String, String>> > res = new HashMap<>();
        res.put("result", this.neo4jGraph.serialze(results));
        return res;
    }

    @GetMapping("/get-nodes-for-class-interface")
    public Map<String, List<Map<String, String>>> getNodesForClassandInterface() {
        this.neo4jGraph = this.getNeoInstance();
        String cypherQuery = "MATCH (type:TypeDeclaration)-[:child]->()-[:child]->(name:TerminalNode{symbol:\"IDENTIFIER\"}) " +
                "RETURN type.file, type.startline, type.startcol, type.longname, name.token " +
                "LIMIT 25 ";
        List<GraphResult> results = this.neo4jGraph.getResult(cypherQuery);
        Map<String, List<Map<String, String>> > res = new HashMap<>();
        res.put("result", this.neo4jGraph.serialze(results));
        return res;
    }

    @GetMapping("/package-of-a-given-classes")
    public Map<String, List<Map<String, String>>> getPackageOfAGivenClass() {
        this.neo4jGraph = this.getNeoInstance();
        String cypherQuery = "MATCH (package:PackageDeclaration)-[:parent]->(root)-[:child*]->(type:TypeDeclaration) " +
                "WHERE type.longname =~ \".*(Page|Crawl).*\" " +
                "RETURN package.longname, type.longname " +
                "LIMIT 25 ";
        List<GraphResult> results = this.neo4jGraph.getResult(cypherQuery);
        Map<String, List<Map<String, String>> > res = new HashMap<>();
        res.put("result", this.neo4jGraph.serialze(results));
        return res;
    }

    @GetMapping("/get-all-classes-of-a-package")
    public Map<String, List<Map<String, String>>> getAllClassesOfAPackage() {
        this.neo4jGraph = this.getNeoInstance();
        String cypherQuery = "MATCH (package:PackageDeclaration)-[:parent]->(root)-[:child*]->(type:TypeDeclaration) " +
                "RETURN package.longname, collect(type.longname) as allClassesUnderPackage " +
                "LIMIT 25";
        List<GraphResult> results = this.neo4jGraph.getResult(cypherQuery);
        Map<String, List<Map<String, String>> > res = new HashMap<>();
        res.put("result", this.neo4jGraph.serialze(results));
        return res;
    }

    @GetMapping("/get-methods-class-both-inherited-and-declared")
    public Map<String, List<Map<String, String>>> getMethodInheritAndDeclared() {
        this.neo4jGraph = this.getNeoInstance();
        String cypherQuery = "MATCH (type:TypeDeclaration)-[:supertype*0..]->(superTypeIncludingSelf:TypeDeclaration) " +
                "WITH type, superTypeIncludingSelf " +
                "MATCH (superTypeIncludingSelf)-[:child*]->(method:ClassBodyDeclaration)-[:child*..2]->(:MethodDeclaration) " +
                "RETURN CASE WHEN type.longname = superTypeIncludingSelf.longname THEN \"declared\" ELSE \"inherited\" END AS methodComesFromWhere, " +
                "type.longname, superTypeIncludingSelf.longname, method.longname LIMIT 25;";
        List<GraphResult> results = this.neo4jGraph.getResult(cypherQuery);
        Map<String, List<Map<String, String>> > res = new HashMap<>();
        res.put("result", this.neo4jGraph.serialze(results));
        return res;
    }


    @GetMapping("/get-transitive-closure-of-types")
    public Map<String, List<Map<String, String>>> getTransitiveClosureOfTypes() {
        this.neo4jGraph = this.getNeoInstance();
        String cypherQuery = "MATCH (type:TypeDeclaration)-[:supertype]-(superType:TypeDeclaration) " +
                "RETURN type.longname, collect(DISTINCT superType.longname) ";
        List<GraphResult> results = this.neo4jGraph.getResult(cypherQuery);
        Map<String, List<Map<String, String>> > res = new HashMap<>();
        res.put("result", this.neo4jGraph.serialze(results));
        return res;
    }

    @GetMapping("/get-null-literal-value")
    public Map<String, List<Map<String, String>>> getNullLiteralValue() {
        this.neo4jGraph = this.getNeoInstance();
        String cypherQuery = "MATCH (ifStmt:Statement)-[:child]->(t:TerminalNode{symbol:\"IF\"})," +
                "(ifStmt)-[:child]->(:ParExpression)-[:child]->(ifCondition:Expression)-" +
                "[:child*]->(:Literal)-[:child]->(nullLiteral:TerminalNode{symbol:\"NULL_LITERAL\"}) " +
                "RETURN ifStmt, ifCondition, nullLiteral " +
                "LIMIT 10;";
        List<GraphResult> results = this.neo4jGraph.getResult(cypherQuery);
        Map<String, List<Map<String, String>> > res = new HashMap<>();
        res.put("result", this.neo4jGraph.serialze(results));
        return res;
    }

    @GetMapping("/get-if-statements-where-null")
    public Map<String, List<Map<String, String>>> getIfStatementWhereNull() {
        this.neo4jGraph = this.getNeoInstance();
        String cypherQuery = "MATCH (ifStmt:Statement)-[:child]->(t:TerminalNode{symbol:\"IF\"}) " +
                "WHERE NOT (ifStmt)-[:child]->(:ParExpression)-[:child]->(:Expression)-[:child*]->(:Literal)-[:child]->(:TerminalNode{symbol:\"NULL_LITERAL\"}) " +
                "RETURN ifStmt.file, ifStmt.startline, ifStmt.startcol " +
                "LIMIT 10;";
        List<GraphResult> results = this.neo4jGraph.getResult(cypherQuery);
        Map<String, List<Map<String, String>> > res = new HashMap<>();
        res.put("result", this.neo4jGraph.serialze(results));
        return res;
    }


    @GetMapping("/get-cyclomatic-complexity-class")
    public Map<String, List<Map<String, String>>> getCyclomaticComplexityClass() {
        this.neo4jGraph = this.getNeoInstance();
        String cypherQuery = "MATCH (type:TypeDeclaration)-[:child*]->(method:ClassBodyDeclaration)-[:child*..2]->(:MethodDeclaration) " +
                "RETURN type.longname, collect(method.longname), count(method), sum(method.cyclomatic), collect(method.cyclomatic); ";
        List<GraphResult> results = this.neo4jGraph.getResult(cypherQuery);
        Map<String, List<Map<String, String>> > res = new HashMap<>();
        res.put("result", this.neo4jGraph.serialze(results));
        return res;
    }


    @GetMapping("/get-all-methods-with-complexity")
    public Map<String, List<Map<String, String>>> getAllMethodsWithComplexity() {
        this.neo4jGraph = this.getNeoInstance();
        String cypherQuery = "MATCH (method:MethodDeclaration)-[:child]->(:MethodBody)-[:child*1..10]->(stmt:Statement) " +
                "WITH method, count(stmt) as numStatements " +
                "WHERE numStatements >=10 " +
                "RETURN method.longname, method.file, method.startline, method.endline, numStatements;";
        List<GraphResult> results = this.neo4jGraph.getResult(cypherQuery);
        Map<String, List<Map<String, String>> > res = new HashMap<>();
        res.put("result", this.neo4jGraph.serialze(results));
        return res;
    }


    @GetMapping("/get-all-methods-with4-params")
    public Map<String, List<Map<String, String>>> getAllMethodWithParams() {
        this.neo4jGraph = this.getNeoInstance();
        String cypherQuery = "MATCH (method:MethodDeclaration)-[:child]->(:FormalParameters)-[:child]->(:FormalParameterList)-[:child]->(param) " +
                "WHERE param:FormalParameter OR param:LastFormalParameter " +
                "WITH method.longname as method, count(param) as numParams " +
                "WHERE numParams > 4 " +
                "RETURN method, numParams;";
        List<GraphResult> results = this.neo4jGraph.getResult(cypherQuery);
        Map<String, List<Map<String, String>> > res = new HashMap<>();
        res.put("result", this.neo4jGraph.serialze(results));
        return res;
    }

    @GetMapping("/get-methods-with-50-lines")
    public Map<String, List<Map<String, String>>> getMethodWith50Lines() {
        this.neo4jGraph = this.getNeoInstance();
        String cypherQuery = "MATCH (method:MethodDeclaration) " +
                "WHERE (method.endline - method.startline + 1) > 50 " +
                "RETURN method.longname , method.startline , method.endline ;";
        List<GraphResult> results = this.neo4jGraph.getResult(cypherQuery);
        Map<String, List<Map<String, String>> > res = new HashMap<>();
        res.put("result", this.neo4jGraph.serialze(results));
        return res;
    }



}
