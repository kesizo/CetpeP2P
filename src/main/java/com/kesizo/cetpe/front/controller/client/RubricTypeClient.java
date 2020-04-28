package com.kesizo.cetpe.front.controller.client;

import com.kesizo.cetpe.front.controller.dtos.RubricType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


@FeignClient(name = "RubricTypeClient", url = "${cetpe.backend.restapi.url}")
public interface RubricTypeClient {

    @RequestMapping(value = "/api/cetpe/rubric/types", method = RequestMethod.GET)
    public List<RubricType> cetpeRubricTypesIndex();

    //Operations with the blogs. Retrieve (GET), Update (PUT), Remove (DELETE)
    @GetMapping("/api/cetpe/rubric/types/{id}")
    public RubricType rubricTypeById(@PathVariable String id);
}

