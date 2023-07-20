package com.brad.novel.chapter.api;

import com.brad.novel.chapter.dto.*;
import com.brad.novel.chapter.entity.Chapter;
import com.brad.novel.chapter.service.ChapterService;
import com.brad.novel.common.error.ResponseCode;
import com.brad.novel.common.response.DataResponse;
import com.brad.novel.novel.entity.Novel;
import com.brad.novel.novel.service.NovelService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Tag(name = "ChapterApiController", description = "에피소드 등록, 전체 에피소드 조회, 단일 에피소드 주문")
@RequestMapping(produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ChapterApiController {
    private final ChapterService chapterService;
    private final NovelService novelService;


    @PostMapping("/novels/{novelId}/chapters")
    public DataResponse writeChapter(@PathVariable Long novelId, @RequestBody ChapterSaveRequestDto chapterSaveRequestDto) {
        Novel findNovel = novelService.findById(novelId);
        Long chapterId = chapterService.writeNovel(findNovel, chapterSaveRequestDto);
        Chapter findChapter = chapterService.findById(chapterId);
        ChapterSaveResponseDto chapterSaveResponseDto = ChapterSaveResponseDto.of(findChapter);
        return new DataResponse(ResponseCode.SUCCESS_201, chapterSaveResponseDto);
    }

    @GetMapping("/novels/{novelId}/chapters")
    public DataResponse getAllChapter(@PathVariable Long novelId) {
        Novel findNovel = novelService.findById(novelId);
        List<Chapter> allChapters = chapterService.findAllChaptersByNovel(findNovel);
        List<ChapterShortResponseDto> chapterShorts = allChapters.stream()
                .map(ChapterShortResponseDto::new)
                .collect(Collectors.toList());
        return new DataResponse(ResponseCode.SUCCESS_202, chapterShorts);
    }

    @PostMapping("/members/{memberId}/pay/{chapterId}")
    public DataResponse orderOneChapter(@PathVariable Long memberId, @PathVariable Long chapterId, @RequestBody ChapterOrderRequestDto requestDto) throws InterruptedException {
        chapterService.orderOneChapter(memberId, requestDto);

        return new DataResponse();

    }

    @PreAuthorize("hasRole('ROLE_MEMBER')")
    @GetMapping("/novels/{novelId}/chapters/{chapterId}")
    public DataResponse getDetailsChapter(@PathVariable Long novelId,
                                          @PathVariable Long chapterId,
                                          HttpServletResponse response) {
        Novel novel = novelService.findById(novelId);
        Chapter chapter = chapterService.findById(chapterId);
        Integer chapterSeq = chapter.getChapterSeq();
        Cookie cookie = new Cookie("recentCh", String.valueOf(chapterSeq));
        response.addCookie(cookie);
        ChapterDetailResponseDto chapterDetailResponseDto = ChapterDetailResponseDto.of(chapter, novel.getSubject());
        return new DataResponse(ResponseCode.SUCCESS_201, chapterDetailResponseDto);
    }


}
