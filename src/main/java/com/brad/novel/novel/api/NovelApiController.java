package com.brad.novel.novel.api;

import com.brad.novel.chapter.service.ChapterService;
import com.brad.novel.common.error.ResponseCode;
import com.brad.novel.common.response.DataResponse;
import com.brad.novel.member.service.MemberService;
import com.brad.novel.novel.dto.NovelRequestDto;
import com.brad.novel.novel.dto.NovelResponseDto;
import com.brad.novel.novel.entity.Novel;
import com.brad.novel.novel.service.NovelService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Tag(name = "NovelApiController", description = "작가 회원 - 소설 등록")
@RequestMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
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

}
