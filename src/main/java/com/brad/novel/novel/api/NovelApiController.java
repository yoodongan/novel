package com.brad.novel.novel.api;

import com.brad.novel.common.error.ResponseCode;
import com.brad.novel.common.response.DataResponse;
import com.brad.novel.novel.dto.NovelBestPreferenceDto;
import com.brad.novel.novel.dto.NovelDetailResponseDto;
import com.brad.novel.novel.dto.request.NovelModifyRequestDto;
import com.brad.novel.novel.dto.request.NovelRegisterRequestDto;
import com.brad.novel.novel.dto.response.NovelRegisterResponseDto;
import com.brad.novel.novel.entity.Novel;
import com.brad.novel.novel.service.NovelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Tag(name = "NovelApiController", description = "작가 회원 - 소설 등록")
@RequestMapping(value = "/api", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class NovelApiController {
    private final NovelService novelService;

    @PostMapping(value = "/novels", consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "등록", security = @SecurityRequirement(name = "bearerAuth"))
    public DataResponse registerNovel(@Parameter(hidden = true) @AuthenticationPrincipal User user, @RequestBody NovelRegisterRequestDto requestDto) {
        Long novelId = novelService.registerNovel(requestDto, user.getUsername());
        Novel findNovel = novelService.findById(novelId);
        NovelRegisterResponseDto responseDto = NovelRegisterResponseDto.from(findNovel);
        return DataResponse.success(ResponseCode.SUCCESS_201, responseDto);
    }

    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    @PutMapping("/novels/{novelId}")
    public DataResponse modifyNovel(@PathVariable Long novelId, @RequestBody NovelModifyRequestDto requestDto, Principal principal) {
        novelService.modifyNovel(novelId, requestDto);
        return DataResponse.success(ResponseCode.SUCCESS_201, String.format("%d번 소설이 수정되었습니다.", novelId));
    }

    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    @DeleteMapping("/novels/{novelId}")
    public DataResponse deleteNovel(@PathVariable Long novelId) {
        novelService.deleteNovel(novelId);
        return DataResponse.success(ResponseCode.SUCCESS_201, String.format("%d번 소설이 삭제되었습니다.", novelId));
    }


    @PreAuthorize("hasRole('ROLE_MEMBER')")
    @GetMapping(value = "/novels/{novelId}", consumes = ALL_VALUE)
    public DataResponse findNovelDetails(@CookieValue(value = "recentCh", defaultValue = "0") String recentCh,
                                         @PathVariable("novelId") Long novelId) { // 가장 최근 읽은 회차를 쿠키에 포함
        Novel novel = novelService.findById(novelId);
        NovelDetailResponseDto novelDetailResponseDto = NovelDetailResponseDto.of(novel, Integer.parseInt(recentCh));
        return DataResponse.success(ResponseCode.SUCCESS_201, novelDetailResponseDto);
    }

    @GetMapping("/novels/preferPerHour")
    public DataResponse findAllOrderByPreference() {
        List<Novel> novels = novelService.findByBestPreferencePerHour();
        List<NovelBestPreferenceDto> responseDto = novels.stream()
                .map(NovelBestPreferenceDto::of)
                .collect(Collectors.toList());
        return DataResponse.success(ResponseCode.SUCCESS_201, responseDto);
    }

}
