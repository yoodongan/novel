package com.brad.novel.novel.api


import com.brad.novel.novel.dto.NovelRequestDto
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import java.nio.charset.StandardCharsets

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class NovelApiControllerTest extends Specification {
    @Autowired
    private MockMvc mvc;

    static ObjectMapper objectMapper;
    static HttpHeaders httpHeaders;

    @WithUserDetails("authorABC")
    def "소설 저장 API 테스트"() {
        setup:
        objectMapper = new ObjectMapper()
        httpHeaders = new HttpHeaders()
        httpHeaders.setContentType(MediaType.APPLICATION_JSON)

        def request = new NovelRequestDto(null, "제목1", "스릴러", null, null, "상세설명", null)

        when:
        def resultActions = mvc.perform(post("/novels")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))

        then:
        resultActions
            .andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath('$.responseCode').value("SUCCESS_201"))
            .andExpect(jsonPath('$.data.authorName').value("베르나르베르베르")) // 작가명을 자동으로 가져와야 한다.
            .andDo {
                mvcResult ->
                    println(mvcResult.response.contentAsString)
            }
    }

}