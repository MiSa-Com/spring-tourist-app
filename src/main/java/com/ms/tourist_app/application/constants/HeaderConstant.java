package com.ms.tourist_app.application.constants;

public class HeaderConstant {

  public static final String X_ORGANIZATION_ID = "X-Organization-Id";
  public static final String X_ORGANIZATION_ID_DESCRIPTION = "企業ID (複数組織に所属するユーザーの場合は閲覧選択している組織ID)";
  public static final String AUTHORIZATION = "Authorization";
  public static final String AUTHORIZATION_DESCRIPTION = "「Bearer｛jwtトーケン｝」の形式";
  private HeaderConstant() {
  }

}
