package com.brad.novel.mychapter.service;

import com.brad.novel.chapter.entity.Chapter;
import com.brad.novel.chapter.repository.ChapterRepository;
import com.brad.novel.common.error.ResponseCode;
import com.brad.novel.common.exception.NovelServiceException;
import com.brad.novel.member.entity.Member;
import com.brad.novel.mychapter.dto.response.MyChapterReadResponseDto;
import com.brad.novel.mychapter.entity.MyChapter;
import com.brad.novel.mychapter.repository.MyChapterRepository;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

import static com.brad.novel.common.error.ResponseCode.*;

@Service
@RequiredArgsConstructor
@Transactional
public class MyChapterService {
    private final MyChapterRepository myChapterRepository;
    private final RedissonClient redissonClient;
    private final ChapterRepository chapterRepository;

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

    public void buyChapter(Member member, Long chapterId) {
        String lockName = "chapter buyer : " + member.getId();
        RLock lock = redissonClient.getLock(lockName);
        boolean isLocked = false;
        try {
            isLocked = lock.tryLock(2, 6, TimeUnit.SECONDS);
            if (!isLocked) throw new NovelServiceException(NOT_AVAILABLE_LOCK);

            /* 이미 구매한 Chapter 는 구매할 수 없다. */
            if(myChapterRepository.existsByMemberAndChapterId(member, chapterId)) {
                throw new NovelServiceException(DUPLICATED_MY_CHAPTER);
            }

            Chapter chapter = chapterRepository.findById(chapterId)
                    .orElseThrow(() -> new NovelServiceException(NOT_FOUND_CHAPTER));

            boolean canBuy = canBuyChapterWithTicket(member, chapter);
            if(canBuy) member.useTicket(chapter.getTicketPrice());

            MyChapter myChapter = MyChapter.createMyChapter(member, chapter);
            /* 소장권 거래내역 기록 */

            myChapterRepository.save(myChapter);

        } catch (InterruptedException e) {

        } finally {
            if (isLocked) { // 락을 획득했을 때만 unlock 수행
                lock.unlock();
            }
        }
    }

    private boolean canBuyChapterWithTicket(Member member, Chapter chapter) {
        if (member.getRestTicket() < chapter.getTicketPrice()) {
            throw new NovelServiceException(LACK_COUNT_TICKET);
        }
        return true;
    }
}
