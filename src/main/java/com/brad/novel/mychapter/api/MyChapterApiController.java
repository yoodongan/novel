package com.brad.novel.mychapter.api;

import com.brad.novel.chapter.entity.Chapter;
import com.brad.novel.chapter.repository.ChapterRepository;
import com.brad.novel.common.error.ResponseCode;
import com.brad.novel.common.exception.NovelServiceException;
import com.brad.novel.common.response.DataResponse;
import com.brad.novel.mychapter.dto.response.MyChapterReadResponseDto;
import com.brad.novel.mychapter.service.MyChapterService;
import com.brad.novel.preference.service.PreferenceService;
import com.brad.novel.security.dto.MemberContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyChapterApiController {
    private final MyChapterService myChapterService;
    private final ChapterRepository chapterRepository;
    private final PreferenceService preferenceService;

    @GetMapping("/novels/{novelId}/chapters/{chapterId}")
    public DataResponse readMyChapter(@PathVariable Long novelId,
                                      @PathVariable Long chapterId,
                                      @AuthenticationPrincipal MemberContext memberContext) {
        MyChapterReadResponseDto myChapterReadResponseDto = myChapterService.readMyChapter(memberContext.getId(), chapterId);

        Chapter chapter = chapterRepository.findById(chapterId)
                .orElseThrow(() -> new NovelServiceException(ResponseCode.NOT_FOUND_CHAPTER));
        preferenceService.updateLastRecentChapter(memberContext.getMember(), novelId, chapter.getChapterSequence());

        return DataResponse.success(ResponseCode.SUCCESS_201, myChapterReadResponseDto);
    }

    @PostMapping("/novels/{novelId}/chapters/{chapterId}")
    public DataResponse buyChapter(@PathVariable Long novelId,
                                   @PathVariable Long chapterId,
                                   @AuthenticationPrincipal MemberContext memberContext) {
        myChapterService.buyChapter(memberContext.getMember(), chapterId);
        return DataResponse.success(ResponseCode.SUCCESS_201, "Chapter 구매에 성공했습니다.");
    }
}