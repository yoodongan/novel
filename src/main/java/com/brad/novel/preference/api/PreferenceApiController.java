package com.brad.novel.preference.api;

import com.brad.novel.common.response.DataResponse;
import com.brad.novel.preference.service.PreferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PreferenceApiController {
    private final PreferenceService preferenceService;

    @GetMapping("/preference/preferPerHour")
    public DataResponse getBestPreferencePerHour() {
        return preferenceService.getBestPreferencePerHour();
    }
}
