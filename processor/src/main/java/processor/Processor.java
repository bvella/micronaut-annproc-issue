package processor;

import static javax.lang.model.SourceVersion.RELEASE_8;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;

@SupportedAnnotationTypes("*")
@SupportedSourceVersion(RELEASE_8)
public class Processor extends AbstractProcessor {
    
    private boolean executed = false;
    
    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
        if (executed) {
            return false;
        }
        
        try {
            final String output = determineOutputPath();
            final File outputDir = new File(output);
    
            final String name;
            switch (outputDir.getName()) {
                case "main":
                case "classes":
                    name = "Main";
                    break;
                case "test":
                case "test-classes":
                    name = "Test";
                    break;
                default:
                    throw new IllegalStateException("Unknown builder for output " + outputDir);
            }
        
            final JavaFileObject gen = processingEnv
                .getFiler()
                .createSourceFile("gen." + name + "Gen");
            try (final Writer w = gen.openWriter()) {
                w.write(
                    "package gen;\n\n"
                );
                w.write("public interface " + name + "Gen {}");
            }

            final JavaFileObject issue = processingEnv
                .getFiler()
                .createSourceFile("issue." + name + "Issue");
            try (final Writer w = issue.openWriter()) {
                w.write(
                    "package issue;\n\n"
                );
                w.write("public interface " + name + "Issue {}");
            }
        } catch (final IOException e) {
            throw new IllegalStateException(e);
        }
        
        executed = true;
        return false;
    }

    private String determineOutputPath() throws IOException {
        // go write a file so as to figure out where we're running
        final FileObject resource = processingEnv
            .getFiler()
            .createResource(StandardLocation.CLASS_OUTPUT, "", "tmp" + System.currentTimeMillis(), (Element[]) null);
        try {
            return new File(resource.toUri()).getCanonicalFile().getParent();
        } finally {
            resource.delete();
        }
    }
    
}
