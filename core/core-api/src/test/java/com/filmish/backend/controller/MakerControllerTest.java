package com.filmish.backend.controller;

import com.filmish.backend.test.api.RestDocsTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

class MakerControllerTest extends RestDocsTest {

    MakerController controller;

    @BeforeEach
    public void setup(){
        controller = new MakerController();
        mockMvc = mockController(controller);
    }

    @Test
    void findMakers() {
        given()
                .contentType(ContentType.JSON)
                .queryParam("cursorId", 1L)
                .queryParam("pageSize", 5)
                .get("/makers")
                .then()
                .status(HttpStatus.OK)
                .apply(document(
                        "find-makers",
                        queryParameters(parameterWithName("cursorId")
                                .optional()
                                .description("페이지네이션 커서, 이전 페이지의 마지막 프로젝트 ID를 입력.첫 페이지 요청 시 생략하거나 빈값으로 요청)"),
                                parameterWithName("pageSize")
                                        .description("페이지 크기")),
                        responseFields(
                                fieldWithPath("result")
                                        .type(JsonFieldType.STRING)
                                        .description("성공 여부 (예: SUCCESS 혹은 ERROR)"),
                                fieldWithPath("data.[].id").type(JsonFieldType.NUMBER).description("영화인의 아이디"),
                                fieldWithPath("data.[].name").type(JsonFieldType.STRING).description("영화인의 이름"),
                                fieldWithPath("data.[].email").type(JsonFieldType.STRING).description("영화인의 이메일"),
                                fieldWithPath("data.[].role").type(JsonFieldType.STRING).description("영화인의 역할"),
                                fieldWithPath("data.[].image").type(JsonFieldType.STRING).description("영화인의 이미지"),
                                fieldWithPath("data.[].qnaCnt").type(JsonFieldType.NUMBER).description("영화인과의 대화 개수"),
                                fieldWithPath("data.[].indieCnt").type(JsonFieldType.NUMBER).description("영화인의 독립영화 작품 수"),
                                fieldWithPath("data.[].movieTitle").type(JsonFieldType.STRING).description("영화인의 최근 작품명")
                        )
                ));
    }
}