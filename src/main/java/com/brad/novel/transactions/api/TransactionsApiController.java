package com.brad.novel.transactions.api;

import com.brad.novel.common.error.ResponseCode;
import com.brad.novel.common.response.DataResponse;
import com.brad.novel.security.dto.MemberContext;
import com.brad.novel.transactions.dto.PointChargeRequestDto;
import com.brad.novel.transactions.dto.TicketChargeRequestDto;
import com.brad.novel.transactions.service.TransactionsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Tag(name = "TransactionsApiController", description = "포인트 충전 및 소장권 사용 기록")
@RequestMapping(value = "/api/transactions", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TransactionsApiController {
    private final TransactionsService transactionsService;

    @PostMapping(value = "/point/charge", consumes = APPLICATION_JSON_VALUE)
    public DataResponse chargePoint(@AuthenticationPrincipal MemberContext memberContext,
                                        @RequestBody PointChargeRequestDto requestDto) {
        transactionsService.addPoint(memberContext.getMember(), requestDto);
        return new DataResponse(ResponseCode.SUCCESS_201, "포인트가 정상적으로 충전되었습니다.");
    }

    @PostMapping(value = "/ticket/charge", consumes = APPLICATION_JSON_VALUE)
    public DataResponse chargeTicket(@AuthenticationPrincipal MemberContext memberContext,
                                    @RequestBody TicketChargeRequestDto requestDto) {
        transactionsService.addTicket(memberContext.getMember(), requestDto);
        return new DataResponse(ResponseCode.SUCCESS_201, "소장권이 정상적으로 충전되었습니다.");
    }



}
