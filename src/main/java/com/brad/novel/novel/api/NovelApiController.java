package com.brad.novel.novel.api;

import com.brad.novel.chapter.service.ChapterService;
import com.brad.novel.common.error.ResponseCode;
import com.brad.novel.common.response.DataResponse;
import com.brad.novel.member.service.MemberService;
import com.brad.novel.novel.dto.NovelBestPreferenceDto;
import com.brad.novel.novel.dto.NovelDetailResponseDto;
import com.brad.novel.novel.dto.NovelRequestDto;
import com.brad.novel.novel.dto.NovelResponseDto;
import com.brad.novel.novel.entity.Novel;
import com.brad.novel.novel.service.NovelService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Tag(name = "NovelApiController", description = "작가 회원 - 소설 등록")
@RequestMapping(produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class NovelApiController {
    private final NovelService novelService;
    private final MemberService memberService;
    private final ChapterService chapterService;

    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    @PostMapping("/novels")
    public DataResponse save(@RequestBody NovelRequestDto novelRequestDto, Principal principal) {
        Long novelId = novelService.save(novelRequestDto, principal.getName());
        Novel findNovel = novelService.findById(novelId);
        NovelResponseDto novelResponseDto = NovelResponseDto.of(findNovel);
        return DataResponse.success(ResponseCode.SUCCESS_201, novelResponseDto);
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
