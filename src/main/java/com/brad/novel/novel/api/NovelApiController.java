package com.brad.novel.novel.api;

import com.brad.novel.novel.dto.NovelRequestDto;
import com.brad.novel.novel.dto.NovelResponseDto;
import com.brad.novel.novel.entity.Novel;
import com.brad.novel.novel.service.NovelService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NovelApiController {
    private final NovelService novelService;

    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    public NovelResponseDto save(NovelRequestDto novelRequestDto) {
        Long novelId = novelService.save(novelRequestDto);
        Novel findNovel = novelService.findById(novelId);
        return NovelResponseDto.of(findNovel);
    }

}
