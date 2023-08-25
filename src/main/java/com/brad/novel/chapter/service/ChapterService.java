package com.brad.novel.chapter.service;

import com.brad.novel.chapter.dto.request.ChapterModifyRequestDto;
import com.brad.novel.chapter.dto.request.ChapterRegisterRequestDto;
import com.brad.novel.chapter.entity.Chapter;
import com.brad.novel.chapter.exception.ChapterAlreadyException;
import com.brad.novel.chapter.repository.ChapterRepository;
import com.brad.novel.common.error.ResponseCode;
import com.brad.novel.common.exception.NovelServiceException;
import com.brad.novel.global.redis.RedisLockRepository;
import com.brad.novel.novel.entity.Novel;
import com.brad.novel.novel.repository.NovelRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChapterService {
    private final ChapterRepository chapterRepository;
    private final ModelMapper modelMapper;
    private final NovelRepository novelRepository;
    private final RedisLockRepository redisLockRepository;

    public Long registerChapter(Long novelId, ChapterRegisterRequestDto requestDto) {
        Novel novel = novelRepository.findById(novelId).orElseThrow(
                () -> new NovelServiceException(ResponseCode.NOT_FOUND_NOVEL));
        novel.updateDate();

        requestDto.setNovel(novel);
        Chapter chapter = modelMapper.map(requestDto, Chapter.class);

        if(chapterRepository.existsByNovelAndSubject(novel, requestDto.getSubject())) {
            throw new NovelServiceException(ResponseCode.ALREADY_EXIST_CHAPTER_SUBJECT);
        }
        chapterRepository.save(chapter);
        return chapter.getId();
    }

    public Long modifyChapter(Long novelId, Long chapterId, ChapterModifyRequestDto requestDto) {
        Novel novel = novelRepository.findById(novelId).orElseThrow(
                () -> new NovelServiceException(ResponseCode.NOT_FOUND_NOVEL));
        novel.updateDate();

        Chapter chapter = chapterRepository.findById(chapterId)
                .orElseThrow(() -> new NovelServiceException(ResponseCode.NOT_FOUND_CHAPTER));
        chapter.modifyChapter(requestDto);
        return chapterId;
    }

    public void deleteChapter(Long chapterId) {
        Chapter chapter = chapterRepository.findById(chapterId)
                .orElseThrow(() -> new NovelServiceException(ResponseCode.NOT_FOUND_CHAPTER));
        chapterRepository.delete(chapter);
    }


    @Transactional(readOnly = true)
    public Chapter findBySubject(String subject) {
        return chapterRepository.findBySubject(subject).orElseThrow(() -> new ChapterAlreadyException("이미 등록된 챕터명입니다!"));
    }

    @Transactional(readOnly = true)
    public List<Chapter> findAllChaptersByNovel(Novel novel) {
        return chapterRepository.findAllByNovel(novel);
    }

    @Transactional(readOnly = true)
    public Chapter findById(Long chapterId) {
        return chapterRepository.findById(chapterId).orElseThrow(() -> new NovelServiceException(ResponseCode.NOT_FOUND_CHAPTER));
    }
}
