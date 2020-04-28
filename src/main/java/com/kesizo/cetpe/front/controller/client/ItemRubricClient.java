package com.kesizo.cetpe.front.controller.client;

import com.kesizo.cetpe.front.controller.dtos.ItemRubric;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@FeignClient(name = "ItemRubricClient", url = "${cetpe.backend.restapi.url}")
public interface ItemRubricClient {

    @RequestMapping(value = "/api/cetpe/lprocess/rubric/item/{id}", method = RequestMethod.GET)
    public ItemRubric itemRubricById(@PathVariable String id);

    //http://localhost:8083/api/cetpe/lprocess/rubric/item?id_lprocess=1
    @GetMapping("/api/cetpe/lprocess/rubric/item")
    @ResponseBody
    public List<ItemRubric> itemRubricByLearningProcessIdOrRubricId(@RequestParam(required = false) String id_lprocess,
                                                                    @RequestParam(required = false) String id_rubric);

    @PostMapping("/api/cetpe/lprocess/rubric/item")
    public ItemRubric create(@RequestBody Map<String, Object> body);

    @PutMapping("/api/cetpe/lprocess/rubric/item/{id}")
    public ItemRubric update(@PathVariable String id, @RequestBody Map<String, Object> body);

    @DeleteMapping("/api/cetpe/lprocess/rubric/item/{id}")
    public boolean delete(@PathVariable String id);
}
