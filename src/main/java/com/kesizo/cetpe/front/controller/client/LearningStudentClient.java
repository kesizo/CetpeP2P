package com.kesizo.cetpe.front.controller.client;

import com.kesizo.cetpe.front.controller.dtos.LearningStudent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "LearningStudentClient", url = "${cetpe.backend.restapi.url}")
public interface LearningStudentClient {

    @RequestMapping(value = "/api/cetpe/lstudent", method = RequestMethod.GET)
    public List<LearningStudent> learningStudentsIndex();



}

