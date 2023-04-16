package com.brad.novel.point.api;

import com.brad.novel.common.response.DataResponse;
import com.brad.novel.point.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PointApiController {
    private final PointService pointService;

    @PostMapping("/point/{memberId}")
    public DataResponse addPoint(@PathVariable Long memberId, @RequestParam Long amount) {
        return pointService.addPoint(memberId, amount);
    }
}
