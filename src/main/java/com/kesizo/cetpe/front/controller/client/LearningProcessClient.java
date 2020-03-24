package com.kesizo.cetpe.front.controller.client;

import com.kesizo.cetpe.front.controller.dtos.LearningProcess;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "LearningProcessClient", url = "${cetpe.backend.restapi.url}")
public interface LearningProcessClient {

    @GetMapping("/api/cetpe/lprocess")
    public List<LearningProcess> getLearningProcess();
}

