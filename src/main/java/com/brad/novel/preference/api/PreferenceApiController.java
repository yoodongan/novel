package com.brad.novel.preference.api;

import com.brad.novel.common.error.ResponseCode;
import com.brad.novel.common.response.DataResponse;
import com.brad.novel.preference.dto.response.PreferenceNovelsResponseDto;
import com.brad.novel.preference.dto.response.PreferenceRegisterResponseDto;
import com.brad.novel.preference.service.PreferenceService;
import com.brad.novel.security.dto.MemberContext;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Tag(name = "PreferenceApiController", description = "유저 별 선호한 작품 목록 조회, 베스트 선호 작품 조회(1시간 간격으로 갱신)")
@RequestMapping(value = "/api", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class PreferenceApiController {
    private final PreferenceService preferenceService;

    /* 선호 작품 등록 */
    @Transactional
    @PostMapping("/novels/{novelId}/preference")
    public DataResponse registerPreferenceNovel(@AuthenticationPrincipal MemberContext memberContext,
                                                  @PathVariable Long novelId) {
        preferenceService.registerPreferenceNovel(memberContext.getMember(), novelId);
        return new DataResponse(ResponseCode.SUCCESS_201, new PreferenceRegisterResponseDto(memberContext.getUsername(), novelId));
    }

    /* 선호 작품 삭제 */
    @Transactional
    @DeleteMapping("/novels/{novelId}/preference")
    public DataResponse removePreferenceNovel(@AuthenticationPrincipal MemberContext memberContext,
                                                @PathVariable Long novelId) {
        preferenceService.removePreferenceNovel(memberContext.getMember(), novelId);
        return new DataResponse(ResponseCode.SUCCESS_201, "선호 작품 지정이 해제되었습니다.");
    }

    /* 유저 별 선호 작품들 리스팅 */
    @GetMapping("/novels/preference")
    public DataResponse getPreferenceNovelsByMemberId(@AuthenticationPrincipal MemberContext memberContext) {
        List<PreferenceNovelsResponseDto> preferenceNovels = preferenceService.getPreferenceNovelsResponseDtos(memberContext.getMember());
        return DataResponse.success(ResponseCode.SUCCESS_201, preferenceNovels);
    }



    /* 1시간 간격으로 갱신, 베스트 선호 작품 조회 */
//    @GetMapping("/preference/preferPerHour")
//    public DataResponse getBestPreferencePerHour() {
//        List<Object[]> bestPreferencesPerHour = preferenceService.getBestPreferencePerHour();
//        List<BestPreferenceDto> bestPreferenceDtos = new ArrayList<>();
//        for (Object[] objects : bestPreferencesPerHour) {
//            bestPreferenceDtos.add(new BestPreferenceDto((Long) objects[0], (Long) objects[1]));
//        }
//        return new DataResponse(ResponseCode.SUCCESS_202, bestPreferenceDtos);
//    }
}
