package com.dattp.order.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Log4j2
public class AuthService extends com.dattp.order.service.Service {
  public Map<String, Object> verify(String accessToken) {
    Map<String, Object> detail = jwtService.getDetail(accessToken);
    log.debug("=====================> verify: detail = \n{}", detail);

//    AuthResponseDTO tokenOld = tokenStorage.get((Long) detail.get("id"));
//    if (!tokenOld.getAccessToken().equals(accessToken)) throw new BadRequestException("Token invalid");

    return detail;
  }
}