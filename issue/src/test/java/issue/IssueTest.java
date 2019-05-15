package issue;

import io.micronaut.test.annotation.MicronautTest;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;

@MicronautTest
public class IssueTest {
    
    @Inject Issue issue;
    
    @Test
    public void test() {
        issue.test();    
    }
    
}
