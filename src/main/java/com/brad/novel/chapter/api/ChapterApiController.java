package com.brad.novel.chapter.api;

import com.brad.novel.chapter.dto.request.ChapterModifyRequestDto;
import com.brad.novel.chapter.dto.request.ChapterRegisterRequestDto;
import com.brad.novel.chapter.service.ChapterService;
import com.brad.novel.common.error.ResponseCode;
import com.brad.novel.common.response.DataResponse;
import com.brad.novel.novel.service.NovelService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Tag(name = "ChapterApiController", description = "에피소드 등록, 전체 에피소드 조회, 단일 에피소드 주문")
@RequestMapping(value = "/api", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ChapterApiController {
    private final ChapterService chapterService;
    private final NovelService novelService;

    @PostMapping("/novels/{novelId}/chapters")
    public DataResponse registerChapter(@PathVariable Long novelId, @RequestBody ChapterRegisterRequestDto requestDto) {
        Long chapterId = chapterService.registerChapter(novelId, requestDto);
        return new DataResponse(ResponseCode.SUCCESS_201, "Chapter 가 성공적으로 저장되었습니다.");
    }
    @PostMapping("/novels/{novelId}/chapters/{chapterId}")
    public DataResponse modifyChapter(@PathVariable Long novelId, @PathVariable Long chapterId, @RequestBody ChapterModifyRequestDto requestDto) {
        chapterService.modifyChapter(novelId, chapterId, requestDto);
        return new DataResponse(ResponseCode.SUCCESS_201, "Chapter 가 성공적으로 수정되었습니다.");
    }
    @PostMapping("/novels/{novelId}/chapters/{chapterId}")
    public DataResponse deleteChapter(@PathVariable Long novelId, @PathVariable Long chapterId) {
        chapterService.deleteChapter(chapterId);
        return new DataResponse(ResponseCode.SUCCESS_201, "Chapter 가 성공적으로 삭제되었습니다.");
    }

    /*
    @GetMapping("/novels/{novelId}/chapters")
    public DataResponse findAllChapter(@PathVariable Long novelId) {
        Novel findNovel = novelService.findById(novelId);
        List<Chapter> allChapters = chapterService.findAllChaptersByNovel(findNovel);
        List<ChapterShortResponseDto> chapterShorts = allChapters.stream()
                .map(ChapterShortResponseDto::new)
                .collect(Collectors.toList());
        return new DataResponse(ResponseCode.SUCCESS_202, chapterShorts);
    }
     */

}
