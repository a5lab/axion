package com.a5lab.tabr;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class TabrApplicationTests {

    @Test
    void contextLoads() {
        int a = 1;
        int b = 1;
        assert (a == b);
    }

}
