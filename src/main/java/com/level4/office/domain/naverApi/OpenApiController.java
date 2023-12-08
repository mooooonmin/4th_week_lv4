package com.level4.office.domain.naverApi;

import com.level4.office.global.exception.CustomException;
import com.level4.office.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/open")
public class OpenApiController {

    private final NaverSearchApi naver;

    @ResponseBody
    @GetMapping("/naver/news")
    public ResponseEntity<JSONObject> getPlace(@RequestParam("query") String query) {
        JSONParser parser = new JSONParser();
        JSONObject jsonObj;

        try {
            jsonObj = (JSONObject) parser.parse(naver.search(query));
        } catch (ParseException e) {
            throw new CustomException(ErrorCode.FAIL_TO_JSON);
        }

        return ResponseEntity.ok(jsonObj);
    }
}
