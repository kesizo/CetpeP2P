package com.kesizo.cetpe.front.controller.client;

import com.kesizo.cetpe.front.controller.dtos.UserGroup;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@FeignClient(name = "UserGroupClient", url = "${cetpe.backend.restapi.url}")
public interface UserGroupClient {

    @GetMapping("/api/cetpe/group")
    public List<UserGroup> userGroupsIndex();

    @GetMapping("/api/cetpe/group/{id}")
    public UserGroup userGroupById(@PathVariable String id);

    //http://localhost:8083/api/cetpe/lprocess/group?id_lprocess=1
    @GetMapping("/api/cetpe/lprocess/group")
    @ResponseBody
    public List<UserGroup> userGroupsByLearningProcessId(@RequestParam(required = false) String id_lprocess);


    @PostMapping("/api/cetpe/lprocess/{id}/group")
    public UserGroup create(@PathVariable String id,@RequestBody Map<String, Object> body);

    @PutMapping("/api/cetpe/group/{id}")
    public UserGroup update(@PathVariable String id, @RequestBody Map<String, Object> body);

    @PutMapping("/api/cetpe/group/student/add/{id}")
    public UserGroup updateAddLearningStudent(@PathVariable String id, @RequestBody Map<String, Object> body);

    @PutMapping("/api/cetpe/group/student/remove/{id}")
    public boolean updateRemoveLearningStudent(@PathVariable String id, @RequestBody Map<String, Object> body);

    @DeleteMapping("/api/cetpe/group/{id}")
    public boolean delete(@PathVariable String id);

}

