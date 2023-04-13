package com.brad.novel.preference.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BestPreferenceDto {
    private Long novelId;
    private Long likeNumber;

    public BestPreferenceDto(Long novelId, Long likeNumber) {
        this.novelId = novelId;
        this.likeNumber = likeNumber;
    }
}
