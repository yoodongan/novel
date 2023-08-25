package com.brad.novel.mychapter.dto.response;

import com.brad.novel.mychapter.entity.MyChapter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MyChapterReadResponseDto {
    private String subject;

    private String shorts;  // 줄거리

    private String content;  // 실제 내용

    private Integer currentPage; // 현재 몇 페이지인지 알려줘야 한다.

    public static MyChapterReadResponseDto from(MyChapter myChapter) {
        return MyChapterReadResponseDto.builder()
                .subject(myChapter.getChapter().getSubject())
                .shorts(myChapter.getChapter().getShorts())
                .currentPage(myChapter.getCurrentPage())
                .build();
    }
}
