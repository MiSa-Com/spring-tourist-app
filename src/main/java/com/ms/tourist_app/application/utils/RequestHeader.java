package com.ms.tourist_app.application.utils;

import com.ms.tourist_app.application.constants.AppConst;
import com.ms.tourist_app.application.constants.AppStr;
import com.ms.tourist_app.config.exception.BadRequestException;
import com.nimbusds.jwt.SignedJWT;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Component("WebTransferRequestHeader")
public class RequestHeader {
    private final HttpServletRequest httpServletRequest;

    public RequestHeader(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    /**
     * @return String
     */
    public String getUsername() {
        String bearerToken = httpServletRequest.getHeader(AppStr.Auth.authorization);
        if (StringUtils.isEmpty(bearerToken)) {
            return AppStr.AuthorityConstant.anonymousUser;
        }
        String token = bearerToken.substring(AppConst.Auth.bearerSubstring);
        try {
            SignedJWT decodedJWT = SignedJWT.parse(token);
            Map<String, Object> payload = decodedJWT.getPayload().toJSONObject();
            return (String) payload.get(AppStr.AuthorityConstant.claimUUID);
        } catch (Exception e) {
            throw new BadRequestException("Test");
        }
    }

    /**
     * @return String
     */
    public Long getId() {
        String bearerToken = httpServletRequest.getHeader(AppStr.Auth.authorization);
        if (StringUtils.isEmpty(bearerToken)) {
            return null;
        }
        String token = bearerToken.substring(AppConst.Auth.bearerSubstring);
        try {
            SignedJWT decodedJWT = SignedJWT.parse(token);
            Map<String, Object> payload = decodedJWT.getPayload().toJSONObject();
            return Long.parseLong(payload.get(AppStr.AuthorityConstant.claimId).toString());
        } catch (Exception e) {
            throw new BadRequestException("Test");
        }
    }

}
