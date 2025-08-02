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
    public void findMakers() {
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

    @Test
    public void findMaker() {
        given()
                .contentType(ContentType.JSON)
                .get("makers/{makerId}", 1L)
                .then()
                .status(HttpStatus.OK)
                .apply(document(
                        "find-maker",
                        pathParameters(parameterWithName("makerId")
                                .description("영화인의 아이디")
                ),
                        responseFields(
                                fieldWithPath("result")
                                        .type(JsonFieldType.STRING)
                                        .description("성공 여부 (예: SUCCESS 혹은 ERROR)"),
                                fieldWithPath("data.id")
                                        .type(JsonFieldType.NUMBER)
                                        .description("영화인의 아이디"),
                                fieldWithPath("data.userId")
                                        .type(JsonFieldType.NUMBER)
                                                .description("영화인의 사용자 아이디"),
                                fieldWithPath("data.name")
                                        .type(JsonFieldType.STRING)
                                        .description("영화인의 이름"),
                                fieldWithPath("data.image")
                                        .type(JsonFieldType.STRING)
                                        .description("영화인의 이미지"),
                                fieldWithPath("data.qnaCnt")
                                        .type(JsonFieldType.NUMBER)
                                        .description("영화인과의 대화 개수"),
                                fieldWithPath("data.filmography")
                                        .type(JsonFieldType.ARRAY)
                                        .description("영화인의 필모그래피"),
                                fieldWithPath("data.filmography[].id").type(JsonFieldType.NUMBER).description("영화의 아이디"),
                                fieldWithPath("data.filmography[].name").type(JsonFieldType.STRING).description("영화 제목"),
                                fieldWithPath("data.filmography[].poster").type(JsonFieldType.STRING).description("영화 포스터"),
                                fieldWithPath("data.filmography[].stillCut").type(JsonFieldType.STRING).description("영화 스틸컷"),
                                fieldWithPath("data.filmography[].pubDate").type(JsonFieldType.STRING).description("영화 개봉일")
                        )));
    }

    @Test
    public void modifyMaker() {
        given()
                .contentType(ContentType.JSON)
                .body(new ModifyMakerInfoRequest("영화인명2", "image2.jpg"))
                .patch("/makers/{makerId}", 1L)
                .then()
                .status(HttpStatus.OK)
                .apply(document(
                        "modify-maker",
                        pathParameters(
                                parameterWithName("makerId")
                                        .description("영화인의 아이디")),
                        requestFields(
                                fieldWithPath("name")
                                        .type(JsonFieldType.STRING)
                                        .description("영화인의 이름"),
                                fieldWithPath("image")
                                        .type(JsonFieldType.STRING)
                                        .description("영화인의 이미지")
                ),
                        responseFields(
                                fieldWithPath("result")
                                        .type(JsonFieldType.STRING)
                                        .description("성공 여부 (예: SUCCESS 혹은 ERROR)")
                        )));
    }

    @Test
    public void findQnas() {
        given()
                .contentType(ContentType.JSON)
                .queryParam("cursorId", 1L)
                .queryParam("pageSize", 5)
                .get("/{makerId}/qna", 1L)
                .then()
                .status(HttpStatus.OK)
                .apply(document(
                        "find-qnas",
                        pathParameters(
                                parameterWithName("makerId")
                                        .description("영화인의 아이디")),
                        responseFields(
                                fieldWithPath("result")
                                        .type(JsonFieldType.STRING)
                                        .description("성공 여부 (예: SUCCESS 혹은 ERROR)"),
                                fieldWithPath("data.[].id")
                                        .type(JsonFieldType.NUMBER)
                                        .description("영화인의 아이디"),
                                fieldWithPath("data.[].title")
                                        .type(JsonFieldType.STRING)
                                        .description("QnA 게시물의 제목"),
                                fieldWithPath("data.[].writerName")
                                        .type(JsonFieldType.STRING)
                                        .description("QnA 게시물 작성자"),
                                fieldWithPath("data.[].content")
                                        .type(JsonFieldType.STRING)
                                        .description("게시물 내용"),
                                fieldWithPath("data.[].createdAt")
                                        .type(JsonFieldType.STRING)
                                        .description("게시물 작성일"),
                                fieldWithPath("data.[].updatedAt")
                                        .type(JsonFieldType.STRING)
                                        .description("게시물 수정일"),
                                fieldWithPath("data.[].comments")
                                        .type(JsonFieldType.ARRAY)
                                        .optional()
                                        .description("게시물 덧글"),
                                fieldWithPath("data.[].comments[].id")
                                        .type(JsonFieldType.NUMBER)
                                        .description("덧글 id"),
                                fieldWithPath("data.[].comments[].writerName")
                                        .type(JsonFieldType.STRING)
                                        .description("덧글 작성자"),
                                fieldWithPath("data.[].comments[].writerImage")
                                        .type(JsonFieldType.STRING)
                                        .description("덧글 작성자 이미지"),
                                fieldWithPath("data.[].comments[].content")
                                        .type(JsonFieldType.STRING)
                                        .description("덧글 내용"),
                                fieldWithPath("data.[].comments[].createdAt")
                                        .type(JsonFieldType.STRING)
                                        .description("덧글 작성일"),
                                fieldWithPath("data.[].comments[].updatedAt")
                                        .type(JsonFieldType.STRING)
                                        .description("덧글 수정일"),
                                fieldWithPath("data.[].comments[].replies")
                                        .type(JsonFieldType.ARRAY)
                                        .optional()
                                        .description("대댓글, 대댓글목록은 comments[]구성과 같음"),
                                fieldWithPath("data.[].comments[].replies[].id")
                                        .type(JsonFieldType.NUMBER)
                                        .description("대댓글 id"),
                                fieldWithPath("data.[].comments[].replies[].writerName")
                                        .type(JsonFieldType.STRING)
                                        .description("대댓글 작성자"),
                                fieldWithPath("data.[].comments[].replies[].writerImage")
                                        .type(JsonFieldType.STRING)
                                        .description("대댓글 작성자 이미지"),
                                fieldWithPath("data.[].comments[].replies[].content")
                                        .type(JsonFieldType.STRING)
                                        .description("대댓글 내용"),
                                fieldWithPath("data.[].comments[].replies[].createdAt")
                                        .type(JsonFieldType.STRING)
                                        .description("대댓글 작성일"),
                                fieldWithPath("data.[].comments[].replies[].updatedAt")
                                        .type(JsonFieldType.STRING)
                                        .description("대댓글 수정일"),
                                fieldWithPath("data.[].comments[].replies[].replies")
                                        .type(JsonFieldType.ARRAY)
                                        .optional()
                                        .description("대대댓글 목록 (빈 배열)")
                        )
                        )
                );

    }

    @Test
    public void appendQna() {
        given()
                .contentType(ContentType.JSON)
                .body(new AppendQnaRequest(1L, "QnA title", "QnA content"))
                .post("/{makerId}/qna", 1L)
                .then()
                .status(HttpStatus.OK)
                .apply(document(
                        "append-qna",
                        pathParameters(
                                parameterWithName("makerId")
                                        .description("영화인의 아이디")),
                        requestFields(
                                fieldWithPath("id")
                                        .type(JsonFieldType.NUMBER)
                                        .description("QnA 게시물 아이디"),
                                fieldWithPath("title")
                                        .type(JsonFieldType.STRING)
                                        .description("QnA 게시물 제목"),
                                fieldWithPath("content")
                                        .type(JsonFieldType.STRING)
                                        .description("QnA 게시물 내용")
                        ),
                        responseFields(
                                fieldWithPath("result")
                                        .type(JsonFieldType.STRING)
                                        .description("성공 여부 (예: SUCCESS 혹은 ERROR)"),
                                fieldWithPath("data.id")
                                        .type(JsonFieldType.NUMBER)
                                        .description("생성된 QnA 게시물의 id")
                        )
                ));
    }

    @Test
    public void modifyQna() {
        given()
                .contentType(ContentType.JSON)
                .body(new ModifyQnaRequest("QnA Title", "QnA content"))
                .put("/{qnaId}", 1L)
                .then()
                .status(HttpStatus.OK)
                .apply(document(
                        "modify-qna",
                        pathParameters(
                                parameterWithName("qnaId")
                                        .description("QnA 게시물 아이디")),
                        requestFields(
                                fieldWithPath("title")
                                        .type(JsonFieldType.STRING)
                                        .description("QnA 게시물 제목"),
                                fieldWithPath("content")
                                        .type(JsonFieldType.STRING)
                                        .description("QnA 게시물 내용")),
                        responseFields(
                                fieldWithPath("result")
                                        .type(JsonFieldType.STRING)
                                        .description("성공 여부 (예: SUCCESS 혹은 ERROR)"))
                ));
    }

    @Test
    public void deleteQna() {
        given()
                .contentType(ContentType.JSON)
                .delete("/{qnaId}", 1L)
                .then()
                .status(HttpStatus.OK)
                .apply(document(
                        "delete-qna",
                        pathParameters(
                                parameterWithName("qnaId")
                                        .description("QnA 게시물 아이디")
                        ),
                        responseFields(
                                fieldWithPath("result")
                                        .type(JsonFieldType.STRING)
                                        .description("성공 여부 (예: SUCCESS 혹은 ERROR)"))
                ));
    }

    @Test
    public void appendComment() {
        given()
                .contentType(ContentType.JSON)
                .body(new AppendCommentRequest("comment content", 1L))
                .post("/{qnaId}/comments", 1L)
                .then()
                .status(HttpStatus.OK)
                .apply(document(
                        "append-comment",
                        pathParameters(
                                parameterWithName("qnaId")
                                        .description("QnA 게시물의 Id")),
                        requestFields(
                                fieldWithPath("content")
                                        .type(JsonFieldType.STRING)
                                        .description("댓글의 내용"),
                                fieldWithPath("parentId")
                                        .type(JsonFieldType.NUMBER)
                                        .description("댓글의 Id")
                                        .optional()
                        ),
                        responseFields(
                                fieldWithPath("result")
                                        .type(JsonFieldType.STRING)
                                        .description("성공 여부 (예: SUCCESS 혹은 ERROR)"),
                                fieldWithPath("data.id")
                                        .type(JsonFieldType.NUMBER)
                                        .description("생성된 댓글의 id")
                        )
                ));
    }
}