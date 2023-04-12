package com.brad.novel.preference.dto;

import com.brad.novel.novel.dto.NovelResponseDto;
import lombok.Data;

import java.util.List;

@Data
public class ResultPreferenceDto {
    private List<NovelResponseDto> novels;

    public ResultPreferenceDto(List<NovelResponseDto> novels) {
        this.novels = novels;
    }
}
