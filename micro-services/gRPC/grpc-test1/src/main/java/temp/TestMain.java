package temp;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalTime;

@Slf4j
public class TestMain {

    public static void main(String[] args) {
        log.info("test: {}", LocalTime.now());
    }

}
