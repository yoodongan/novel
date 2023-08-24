package com.brad.novel.novel.service;

import com.brad.novel.common.error.ResponseCode;
import com.brad.novel.common.exception.NovelServiceException;
import com.brad.novel.global.redis.RedisCacheKey;
import com.brad.novel.member.entity.Member;
import com.brad.novel.member.repository.MemberRepository;
import com.brad.novel.novel.dto.request.NovelModifyRequestDto;
import com.brad.novel.novel.dto.request.NovelRegisterRequestDto;
import com.brad.novel.novel.dto.response.NovelResponseDto;
import com.brad.novel.novel.entity.Genre;
import com.brad.novel.novel.entity.Novel;
import com.brad.novel.novel.repository.NovelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@CacheConfig(cacheNames = {"preference"})
@Transactional
public class NovelService {
    private final NovelRepository novelRepository;
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    /* 베스트 선호 작품 목록 리스팅(캐싱) */
    @Transactional(readOnly = true)
    @Cacheable(value = RedisCacheKey.PREFERENCE_LIST)
    public List<Novel> findByBestPreferencePerHour() {
        return novelRepository.findAllByOrderByLikeScoreDesc();
    }

    /* 소설 등록 */
    public Long registerNovel(NovelRegisterRequestDto requestDto, String name) {
        Member member = memberRepository.findByUsername(name)
                        .orElseThrow(() -> new NovelServiceException(ResponseCode.NOT_FOUND_MEMBER));

        Novel novel = modelMapper.map(requestDto, Novel.class);
        novel.addAuthorName(member.getNickname()); // 작가명을 넣어준다.
        if(requestDto.getImagePath() == null) novel.addDefaultImage("Default Image");
        novelRepository.save(novel);
        return novel.getId();
    }
    /* 소설 수정 */
    public Long modifyNovel(Long novelId, NovelModifyRequestDto requestDto) {
        Novel novel = findById(novelId);
        novel.modifyNovel(requestDto);
        return novel.getId();
    }
    /* 소설 삭제 */
    public Long deleteNovel(Long novelId) {
        Novel novel = findById(novelId);
        novelRepository.delete(novel);
        return novelId;
    }

    public Novel findById(Long novelId) {
        return novelRepository.findById(novelId)
                .orElseThrow(() -> new NovelServiceException(ResponseCode.NOT_FOUND_NOVEL));
    }

    /* 소설 검색 - 소설 제목 또는 작가 이름을 입력한다. */
    public List<NovelResponseDto> findBySearch(String target) {
        List<Novel> novels = novelRepository.findBySubjectContainingOrAuthorNameContaining(target);
        List<NovelResponseDto> novelResponseDtoList = novels.stream()
                .map(NovelResponseDto::from)
                .collect(Collectors.toList());
        return novelResponseDtoList;
    }

    /* 검색할 때, '로맨스'라고 검색하면 enum의 ROMANCE를 가져와야 한다. */
    public List<NovelResponseDto> findByGenre(String koreaGenre) {
        Genre genre = Genre.getGenre(koreaGenre);
        List<Novel> novels = novelRepository.findByGenre(genre);
        List<NovelResponseDto> novelResponseDtoList = novels.stream()
                .map(NovelResponseDto::from)
                .collect(Collectors.toList());
        return novelResponseDtoList;
    }
}
