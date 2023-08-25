package com.brad.novel.mychapter.service;

import com.brad.novel.chapter.entity.Chapter;
import com.brad.novel.common.error.ResponseCode;
import com.brad.novel.common.exception.NovelServiceException;
import com.brad.novel.mychapter.dto.response.MyChapterReadResponseDto;
import com.brad.novel.mychapter.entity.MyChapter;
import com.brad.novel.mychapter.repository.MyChapterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MyChapterService {
    private final MyChapterRepository myChapterRepository;

    public MyChapterReadResponseDto readMyChapter(Long memberId, Long chapterId) {
        MyChapter myChapter = myChapterRepository.findByMemberIdAndChapterId(memberId, chapterId)
                .orElseThrow(() -> new NovelServiceException(ResponseCode.NOT_FOUND_MY_CHAPTER));

        Chapter chapter = myChapter.getChapter();
        if(myChapter.isPaid()) {
            chapter.increaseViewCount();
            myChapter.changePayStatus();
        }
        return MyChapterReadResponseDto.from(myChapter);
    }
}
