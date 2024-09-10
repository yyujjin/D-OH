package com.DOH.DOH.controller.contest;

import com.DOH.DOH.dto.contest.ContestUploadDTO;
import com.DOH.DOH.service.contest.ContestUploadService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Slf4j
@Controller
public class PayController {
    @Autowired
    private ContestUploadService contestUploadService;

    // 결제 정보 확인 후 DB 저장
    @RequestMapping("/contest/payment/submit")
    @ResponseBody
    public String submitPayment(HttpSession session) {
        try {
            URL url = new URL("https://open-api.kakaopay.com/online/v1/payment/ready");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "SECRET_KEY DEV8E303BF2492F14CCAD2E38BCD33EB62927846");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            connection.setDoOutput(true);
            String parameters = "cid=TC0ONETIME&partner_order_id=partner_order_id" +
                    "&partner_user_id=partner_user_id" +
                    "&item_name=engitem" +
                    "&quantity=1&total_amount=2200" +
                    "&vat_amount=200&tax_free_amount=0" +
                    "&approval_url=http://localhost/contest/payment/submit" +
                    "&fail_url=http://localhost/fail" +
                    "&cancel_url=http://localhost/cancel";
            // 요청 데이터 전송
            OutputStream outputStream = connection.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeBytes(parameters);
            dataOutputStream.flush();
            dataOutputStream.close();

            // 응답 코드 확인
            int responseCode = connection.getResponseCode();

            InputStream inputStream;
            if (responseCode == 200) {
                inputStream = connection.getInputStream();  // 성공 시 응답
            } else {
                inputStream = connection.getErrorStream();  // 실패 시 응답
            }

            // 응답 읽기
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                response.append(line);
            }
            bufferedReader.close();

            return response.toString();  // 응답 반환

        } catch (MalformedURLException e) {
            log.error("URL 형식 오류", e);
        } catch (IOException e) {
            log.error("IO 오류", e);
        }
        return "결제 처리 중 오류가 발생했습니다.";  // 오류 발생 시 반환
    }
}





