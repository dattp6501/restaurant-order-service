package com.dattp.order.service;

import com.dattp.order.dto.auth.AuthResponseDTO;
import com.dattp.order.exception.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService extends com.dattp.order.service.Service {
  public Map<String, Object> verify(String accessToken) {
    Map<String, Object> detail = jwtService.getDetail(accessToken);

    AuthResponseDTO tokenOld = tokenStorage.get((Long) detail.get("id"));
    if (!tokenOld.getAccessToken().equals(accessToken)) throw new BadRequestException("Token invalid");

    return detail;
  }
}