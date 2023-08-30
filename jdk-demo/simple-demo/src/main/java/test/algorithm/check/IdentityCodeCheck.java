package test.algorithm.check;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * Created by zengxf on 2019-05-30
 */
@Slf4j
public class IdentityCodeCheck {

    public static void main(String[] args) {
        String code = "430524199201237792";
        if (check(code)) {
            log.info("[{}] ok", code);
        } else {
            log.info("[{}] no", code);
        }
    }

    static boolean check(String code) {
        int check[] = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
        if (code.length() == 18) { // 18
            int total = 0;
            for (int i = 0; i < 17; i++) {
                total = total + Integer.parseInt(code.substring(i, i + 1)) * check[i];
            }
            log.info("total: {}", total);
            int checkCode = total % 11; //
            log.info("check-code: {}", checkCode);
            String[] expectArr = "10X98765432".split("");
            String expect = expectArr[checkCode];
            log.info("expect: {}", expect);
            String last = code.substring(17, 18);
            if (!expect.equals(last)) {
                return false;
            }
            return true;
        }
        return false;
    }

}
