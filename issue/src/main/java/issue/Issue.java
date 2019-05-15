package issue;

import io.micronaut.context.annotation.Factory;
import javax.inject.Singleton;

@Singleton
public class Issue {
    
    private final gen.MainGen gen;
    
    public Issue(final gen.MainGen gen) {
        this.gen = gen;
    }
    
    public void test() {
        System.out.println(gen);
    }
    
}
