package com.brad.novel.member.api

import com.brad.novel.member.dto.request.MemberJoinRequestDto
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class MemberApiControllerTest extends Specification {
    @Autowired
    private MockMvc mvc

    static ObjectMapper objectMapper;
    static HttpHeaders httpHeaders;

    def "회원가입 정상 케이스"() {
        setup:
        objectMapper = new ObjectMapper()
        httpHeaders = new HttpHeaders()
        httpHeaders.setContentType(MediaType.APPLICATION_JSON)

        def request = new MemberJoinRequestDto("nameA", "1234");

        when:
        def resultActions = mvc.perform(post("/members/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))

        then:
        resultActions.andExpect(status().is2xxSuccessful())
        resultActions.andExpect(jsonPath('$.responseCode').value("SUCCESS_201"))
    }

    def "JWT를 활용한 로그인 기능 테스트"() {
        when:
        def resultActions = mvc.perform(post("/members/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                        "name": "userABC",
                        "password": "12345"
                        }
                        """.stripIndent())
        )
        then:
        resultActions
            .andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath('$.responseCode').value("SUCCESS_201"))
            .andExpect(jsonPath('$.data.name').value("userABC"))
            .andDo(print())

        /* Header 가 비어있지 않은지까지 체크.*/
        def mvcResult = resultActions.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        def authentication = response.getHeader("Authentication")

        expect:
        authentication.isEmpty() == false  // 비어 있으면 안됨.
    }
}
