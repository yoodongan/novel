package com.brad.novel.chapter.service;

import com.brad.novel.chapter.dto.ChapterRequestDto;
import com.brad.novel.chapter.entity.Chapter;
import com.brad.novel.chapter.exception.ChapterAlreadyException;
import com.brad.novel.chapter.repository.ChapterRepository;
import com.brad.novel.novel.entity.Novel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChapterService {
    private final ChapterRepository chapterRepository;
    private final ModelMapper modelMapper;

    public Long writeNovel(Novel novel, ChapterRequestDto requestDto) {
        requestDto.setNovel(novel);
        Chapter chapter = modelMapper.map(requestDto, Chapter.class);

        Optional<Chapter> oChapter = chapterRepository.findBySubject(chapter.getSubject());
        if(oChapter.isPresent()) {
            throw new ChapterAlreadyException("이미 등록된 제목입니다!");
        } else {
            chapterRepository.save(chapter);
        }
        return chapter.getId();
    }

    public Chapter findBySubject(String subject) {
        return chapterRepository.findBySubject(subject).orElseThrow(() -> new ChapterAlreadyException("이미 등록된 챕터명입니다!"));
    }
}
