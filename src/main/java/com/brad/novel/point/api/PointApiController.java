package com.brad.novel.point.api;

import com.brad.novel.point.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PointApiController {
    private final PointService pointService;


}
