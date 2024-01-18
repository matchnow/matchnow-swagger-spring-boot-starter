package com.gswagger.utils;

/**
 * 해당 인터페이스의 기능
 * 1. Swagger UI 문서에 노출될 API 들의 path를 변경시킨다.
 * - "try it out" 버튼 클릭 시 요청 역시 변경된 path가 적용된다.
 * 2. springdoc 의 리소스 접근 path를 변경시킨다.
 * 3. swagger-ui 의 리소스 접근 path를 변경시킨다.
 * 4. swagger-ui 접근 시 리다이렉트 path를 변경시킨다.
 * <p>
 * example : path.replace("/myservice/external", "/ext/myservice").replace("/myservice/internal", "/myservice");
 */
public interface MatchnowSwaggerRedirectPathReplacer {
    String replace(String path);
}
