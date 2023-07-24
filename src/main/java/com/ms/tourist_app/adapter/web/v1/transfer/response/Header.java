package com.ms.tourist_app.adapter.web.v1.transfer.response;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component("WebV1TransferResponseHeader")
public class Header implements ResponseHeader {

  /**
   * @return HttpHeaders
   */
  @Override
  public HttpHeaders getHeader() {
    return buildHeaderCommon();
  }

  private HttpHeaders buildHeaderCommon() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Expires", "Mon, 26 Jul 1997 07:00:00 GMT");
    headers.add("Cache-Control", "no-cache=\"set-cookie\"");
    headers.add("Content-type", "application/json; charset=utf-8");
    headers.add("X-Content-Type-Options", "nosniff");
    headers.add("X-Robots-Tag", "noindex, nofollow");
    return headers;
  }

}
