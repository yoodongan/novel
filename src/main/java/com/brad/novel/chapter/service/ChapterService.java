package com.brad.novel.chapter.service;

import com.brad.novel.chapter.dto.ChapterOrderRequestDto;
import com.brad.novel.chapter.dto.ChapterOrderResponseDto;
import com.brad.novel.chapter.dto.ChapterRequestDto;
import com.brad.novel.chapter.dto.ChapterShortResponseDto;
import com.brad.novel.chapter.entity.Chapter;
import com.brad.novel.chapter.exception.ChapterAlreadyException;
import com.brad.novel.chapter.repository.ChapterRepository;
import com.brad.novel.common.response.DataResponse;
import com.brad.novel.global.redis.RedisLockRepository;
import com.brad.novel.novel.entity.Novel;
import com.brad.novel.point.entity.Point;
import com.brad.novel.point.exception.NoPointException;
import com.brad.novel.point.service.PointService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChapterService {
    private final ChapterRepository chapterRepository;
    private final ModelMapper modelMapper;
    private final RedisLockRepository redisLockRepository;
    private final PointService pointService;

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

    public DataResponse findAllChapter(Novel novel) {
        List<Chapter> findChapters = chapterRepository.findAllByNovel(novel);
        List<ChapterShortResponseDto> chapterShorts = findChapters.stream()
                .map(ChapterShortResponseDto::new)
                .collect(Collectors.toList());

        return DataResponse.success(chapterShorts);
    }

    @Transactional
    public DataResponse orderOneChapter(final Long memberId, ChapterOrderRequestDto orderDto) throws InterruptedException {
        while (!redisLockRepository.lock(memberId)) {
            Thread.sleep(100);  // 락을 획득하지 못했다면, 대기했다가 또 락 요청.
        }
        try {
            Point restPoint = pointService.findByMemberId(memberId);
            if(restPoint.getAmount() - orderDto.getPrice() < 0) {
                throw new NoPointException("잔여 포인트가 부족합니다!");
            } else {
                restPoint.updatePoint(orderDto.getPrice());
            }
            ChapterOrderResponseDto chapterOrderResponseDto = new ChapterOrderResponseDto(orderDto);
            return DataResponse.success(chapterOrderResponseDto);
        } finally {
            redisLockRepository.unlock(memberId);
            // 락 해제
        }
    }


}
