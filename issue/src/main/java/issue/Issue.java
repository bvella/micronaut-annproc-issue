package issue;

import javax.inject.Singleton;

@Singleton
public class Issue {
    
    private final gen.MainGen gen;
    private final MainIssue issue;
    
    public Issue(final gen.MainGen gen, final MainIssue issue) {
        this.gen = gen;
        this.issue = issue;
    }
    
    public void test() {
        System.out.println(gen);
        System.out.println(issue);
    }
    
}
