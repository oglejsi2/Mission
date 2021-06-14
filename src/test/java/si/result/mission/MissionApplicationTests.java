package si.result.mission;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MissionApplicationTests {

	@Test
	void contextLoads() {
	}
        
        @Test
        void checkExecUrls() {
            ExecutorCallPages locExecutorCallPagees = new ExecutorCallPages(4, 1);
            if (locExecutorCallPagees.getCntErr()>0) {
                fail("Failed to execute at list one call");
            }
            
        }

}
