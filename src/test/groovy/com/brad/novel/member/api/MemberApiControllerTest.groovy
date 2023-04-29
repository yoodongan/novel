package com.brad.novel.member.api


import com.brad.novel.member.dto.MemberJoinRequestDto
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
class MemberApiControllerTest extends Specification {
    @Autowired
    MockMvc mockMvc

    static ObjectMapper objectMapper;

    static HttpHeaders httpHeaders;

    def "회원가입 정상 케이스"() {
        setup:
        objectMapper = new ObjectMapper()
        httpHeaders = new HttpHeaders()
        httpHeaders.setContentType(MediaType.APPLICATION_JSON)

        def request = new MemberJoinRequestDto("nameA", "1234");

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.post("/members/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))

        then:
        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
        response.andExpect(MockMvcResultMatchers.jsonPath('$.responseCode').value("SUCCESS_201"))
    }
}
