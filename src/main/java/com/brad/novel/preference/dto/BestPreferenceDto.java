package com.brad.novel.preference.dto;

import lombok.Data;

@Data
public class BestPreferenceDto {
    private Long novelId;
    private Long likeNumber;

    public BestPreferenceDto(Long novelId, Long likeNumber) {
        this.novelId = novelId;
        this.likeNumber = likeNumber;
    }
}
