package com.example.demo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/demo")
@RequiredArgsConstructor
public class SampleController {
    private final SamplePojoRepository samplePojoRepo;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public SamplePojo someControllerMethod() throws IOException {
        SamplePojo pojo = new SamplePojo();

        List<Map<String, Object>> testList = new ArrayList<>();

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("someKey", "someValue");
//        map.put("someOtherKey", "someOtherValue");

        testList.add(map);

        pojo.setSomeList(testList);
        return samplePojoRepo.save(pojo);
    }
}