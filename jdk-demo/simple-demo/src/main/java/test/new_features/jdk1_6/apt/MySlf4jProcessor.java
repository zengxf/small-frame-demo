package test.new_features.jdk1_6.apt;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;

public class MySlf4jProcessor extends AbstractProcessor {

    @Override
    public boolean process( Set<? extends TypeElement> annotations, RoundEnvironment roundEnv ) {
	System.out.println( "进入 ..." );
	return true;
    }

}
