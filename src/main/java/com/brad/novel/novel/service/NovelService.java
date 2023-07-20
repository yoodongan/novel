package com.brad.novel.novel.service;

import com.brad.novel.member.entity.Member;
import com.brad.novel.member.repository.MemberRepository;
import com.brad.novel.novel.dto.NovelRequestDto;
import com.brad.novel.novel.entity.Novel;
import com.brad.novel.novel.exception.NovelFoundException;
import com.brad.novel.novel.repository.NovelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NovelService {
    private final NovelRepository novelRepository;
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    public Long save(NovelRequestDto novelRequestDto, String name) {
        Member member = memberRepository.findByName(name).get();
        novelRequestDto.setAuthorName(member.getNickname());
        novelRequestDto.setLastCh(0); // 첫 소설 만들었을 때는, 마지막 챕터가 0이다.

        Novel novel = modelMapper.map(novelRequestDto, Novel.class);
        log.info(novel.getSubject());
        novelRepository.save(novel);
        return novel.getId();
    }
    /*
    public NovelResponseDto findByEx(Long id) {
        Novel novel = novelRepository.findById(id).orElseThrow(() -> new NovelFoundException("찾는 소설이 없습니다!"));
        NovelResponseDto novelResponseDto = NovelResponseDto.builder()
                .id(novel.getId())
                .subject(novel.getSubject())
                .genre(novel.getGenre())
                .authorName(novel.getAuthorName())
                .description(novel.getDescription())
                .build();
        return novelResponseDto;
    }
    */
    public Novel findById(Long id) {  // novel 정보를 가져올 때는, 가장 최근 읽은 회차도 포함해야 함.
        return novelRepository.findById(id).orElseThrow(() -> new NovelFoundException("찾는 소설이 없습니다!"));
    }
}
