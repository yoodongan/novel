package com.brad.novel.preference.api;

import com.brad.novel.common.response.DataResponse;
import com.brad.novel.preference.service.PreferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PreferenceApiController {
    private final PreferenceService preferenceService;

    /* 유저 별 선호한 작품 목록 조회 */
    @GetMapping("/preference/{memberId}")
    public DataResponse getPreference(@PathVariable Long memberId, @RequestParam Long likeNumber) {
        return preferenceService.getPreferenceNovel(memberId, likeNumber);
    }

    /* 1시간 간격으로 갱신, 베스트 선호 작품 조회 */
    @GetMapping("/preference/preferPerHour")
    public DataResponse getBestPreferencePerHour() {
        return preferenceService.getBestPreferencePerHour();
    }
}
