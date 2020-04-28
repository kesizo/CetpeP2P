package com.kesizo.cetpe.front.controller.client;

import com.kesizo.cetpe.front.controller.dtos.LearningProcess;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "LearningProcessClient", url = "${cetpe.backend.restapi.url}")
public interface LearningProcessClient {

    @GetMapping("/api/cetpe/lprocess")
    public List<LearningProcess> getLearningProcess();

    @PostMapping("/api/cetpe/lprocess")
    public LearningProcess create(@RequestBody Map<String, Object> body);

    @DeleteMapping("/api/cetpe/lprocess/{id}")
    public boolean delete(@PathVariable String id);

    @PutMapping("/api/cetpe/lprocess/{id}")
    public LearningProcess update(@PathVariable String id, @RequestBody Map<String, Object> body);

    @PutMapping("/api/cetpe/lprocess/usergroup/add/{id}")
    public LearningProcess updateAddUserGroup(@PathVariable String id, @RequestBody Map<String, Object> body);

    @PutMapping("/api/cetpe/lprocess/usergroup/remove/{id}")
    public LearningProcess updateRemoveUserGroup(@PathVariable String id, @RequestBody Map<String, Object> body);

    @GetMapping("/api/cetpe/lprocess/{id}")
    public LearningProcess getLearningProcessById(@PathVariable String id);

    @GetMapping("/api/cetpe/lprocess/supervisor/{username}")
    public List<LearningProcess> getLearningProcessBySupervisorUsername(@PathVariable String username);
}

