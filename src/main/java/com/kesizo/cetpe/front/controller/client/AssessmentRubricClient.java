package com.kesizo.cetpe.front.controller.client;

import com.kesizo.cetpe.front.controller.dtos.AssessmentRubric;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "AssessmentRubricClient", url = "${cetpe.backend.restapi.url}")
public interface AssessmentRubricClient {

    @RequestMapping(value = "/api/cetpe/lprocess/rubric/{id}", method = RequestMethod.GET)
    public AssessmentRubric assessmentRubricById(@PathVariable String id);

    @GetMapping("/api/cetpe/lprocess/rubrics/by/lprocess/{id}")
    public List<AssessmentRubric> rubricsByLearningProcessId(@PathVariable String id);

    @PostMapping("/api/cetpe/lprocess/rubric")
    public AssessmentRubric create(@RequestBody Map<String, Object> body);

    @PutMapping("/api/cetpe/lprocess/rubric/{id}")
    public AssessmentRubric update(@PathVariable String id, @RequestBody Map<String, Object> body);

    @DeleteMapping("/api/cetpe/lprocess/rubric/{id}")
    public boolean delete(@PathVariable String id);
}

