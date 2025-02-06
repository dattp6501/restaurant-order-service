package com.dattp.order.config.security;

import com.dattp.order.service.AuthService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Log4j2
public class JWTAuthenticationFilter extends OncePerRequestFilter {
  @Autowired
  @Lazy
  private AuthService authService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    //
    MutableHttpServletRequest mutableRequest = new MutableHttpServletRequest(request);
    mutableRequest.putHeader("timezone", getTimeZone(request.getHeader("language")).toString());
    String accessToken = request.getHeader("access_token");
    if (accessToken == null) {
      try {
        accessToken = Arrays.stream(request.getCookies())
            .filter(c -> c.getName().equals("access_token"))
            .collect(Collectors.toList()).get(0).getValue();
      } catch (Exception e) {
        log.debug("==========>  doFilterInternal:Exception:{}", e.getMessage());
      }//nếu không có access_token thì sẽ vào catch
    }
    if (accessToken == null) {
      filterChain.doFilter(mutableRequest, response);
      return;
    }
    try {
      Map<String, Object> detail = authService.verify(accessToken);
      String[] roles = (String[]) detail.get("roles");

      // chuyen ve dang chuan de xu ly
      Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
      Arrays.stream(roles).forEach(role -> {
        authorities.add(new SimpleGrantedAuthority(role));
      });
      // neu nguoi dung hop le thi set thong tin cho security context
      UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(detail.get("id"), null, authorities);
      usernamePasswordAuthenticationToken.setDetails(detail);
            /*
            {
                "authentication": {
                    "authorities": [
                        {
                            "authority": "ROLE_PRODUCT_ACCESS"
                        }
                    ],
                    "details": null,
                    "authenticated": true,
                    "principal": "anv",
                    "credentials": null,
                    "name": "anv"
                }
            }
            */
      SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);//lưu lại các thông tin quyền của người dùng hiện tại
    } catch (Exception e) {
      log.debug("======> JWTAuthenticationFilter::doFilterInternal::exception::{}", e.getMessage());
    }
    filterChain.doFilter(mutableRequest, response);
  }

  private Long getTimeZone(String language) {
    if (Objects.isNull(language)) return 0L;
    switch (language) {
      case "a":
        return 1L;
      case "vi":
        return 7L;
      default:
        return 0L;
    }
  }
}
