package com.kesizo.cetpe.front.controller.client;

import com.kesizo.cetpe.front.controller.dtos.LearningSupervisor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@FeignClient(name = "SupervisorClient", url = "${cetpe.backend.restapi.url}")
public interface SupervisorClient {

    @RequestMapping(value = "/api/cetpe/lsupervisor/username/{username}", method = RequestMethod.GET)
    public LearningSupervisor learningSupervisorsByUsername(@PathVariable String username);


    @PostMapping("/api/cetpe/lsupervisor")
    public LearningSupervisor create(@RequestBody Map<String, Object> body);
}

