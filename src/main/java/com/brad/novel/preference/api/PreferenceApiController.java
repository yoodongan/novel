package com.brad.novel.preference.api;

import com.brad.novel.common.error.ResponseCode;
import com.brad.novel.common.response.DataResponse;
import com.brad.novel.preference.dto.BestPreferenceDto;
import com.brad.novel.preference.dto.ResultPreferenceDto;
import com.brad.novel.preference.entity.Preference;
import com.brad.novel.preference.service.PreferenceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Tag(name = "PreferenceApiController", description = "유저 별 선호한 작품 목록 조회, 베스트 선호 작품 조회(1시간 간격으로 갱신)")
@RequiredArgsConstructor
public class PreferenceApiController {
    private final PreferenceService preferenceService;

    /* 유저 별 선호한 작품 목록 조회 */
    @GetMapping("/preference/{memberId}")
    public DataResponse getPreference(@PathVariable Long memberId, @RequestParam Integer likeNumber) {
        List<Preference> preferences = preferenceService.getPreferenceNovel(memberId, likeNumber);
        List<ResultPreferenceDto> results = preferences.stream()
                .map(p -> ResultPreferenceDto.of(p.getNovel()))
                .collect(Collectors.toList());
        return new DataResponse(ResponseCode.SUCCESS_202, results);
    }

    /* 1시간 간격으로 갱신, 베스트 선호 작품 조회 */
    @GetMapping("/preference/preferPerHour")
    public DataResponse getBestPreferencePerHour() {
        List<Object[]> bestPreferencesPerHour = preferenceService.getBestPreferencePerHour();
        List<BestPreferenceDto> bestPreferenceDtos = new ArrayList<>();
        for (Object[] objects : bestPreferencesPerHour) {
            bestPreferenceDtos.add(new BestPreferenceDto((Long) objects[0], (Long) objects[1]));
        }
        return new DataResponse(ResponseCode.SUCCESS_202, bestPreferenceDtos);
    }
}
