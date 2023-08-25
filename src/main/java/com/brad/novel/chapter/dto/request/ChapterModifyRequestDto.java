package com.brad.novel.chapter.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChapterModifyRequestDto {
    private String subject;

    private String shorts;  // 줄거리

    private String content;  // 실제 내용

    private Integer chapterSequence;

    private Integer pageCount;  // 해당 챕터의 총 페이지 수

    private String imagePath;

    private Integer ticketPrice;   // 구매하는데 필요한 소장권 수
}
