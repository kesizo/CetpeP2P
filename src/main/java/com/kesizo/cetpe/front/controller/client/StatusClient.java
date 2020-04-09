package com.kesizo.cetpe.front.controller.client;

import com.kesizo.cetpe.front.controller.dtos.Status;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


@FeignClient(name = "StatusClient", url = "${cetpe.backend.restapi.url}")
public interface StatusClient {

    @RequestMapping(value = "/api/cetpe/lprocess/status", method = RequestMethod.GET)
    public List<Status> cetpeLearningProcessStatusIndex();


    //Operations with the blogs. Retrieve (GET), Update (PUT), Remove (DELETE)
    @GetMapping("/api/cetpe/lprocess/status/{id}")
    public Status show(@PathVariable String id);
}

