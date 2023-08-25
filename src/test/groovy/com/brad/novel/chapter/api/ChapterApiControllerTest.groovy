package com.brad.novel.chapter.api

import com.brad.novel.chapter.dto.request.ChapterRegisterRequestDto
import com.brad.novel.novel.service.NovelService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ChapterApiControllerTest extends Specification {
    @Autowired
    MockMvc mockMvc

    static ObjectMapper objectMapper;

    static HttpHeaders httpHeaders;
    
    @Autowired
    private NovelService novelService;

    def "소설 챕터(에피소드) 작성"() {
        setup:
        objectMapper = new ObjectMapper()
        httpHeaders = new HttpHeaders()
        httpHeaders.setContentType(MediaType.APPLICATION_JSON)

        def findNovel = novelService.findById(2L)

        def request = new ChapterRegisterRequestDto(findNovel, "소설 2-1화의 제목입니다.", "소설 2-2화의 줄거리 입니다", "실제 내용입니다.실제 내용입니다.", 10, 20, "default Image", 5);

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.post("/novels/2/chapters")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))

        then:
        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
        response.andExpect(MockMvcResultMatchers.jsonPath('$.responseCode').value("SUCCESS_201"))
    }

}
