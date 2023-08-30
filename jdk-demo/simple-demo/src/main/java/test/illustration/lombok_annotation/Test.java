package test.illustration.lombok_annotation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors( chain = true )
@Setter
@Getter
@RequiredArgsConstructor( staticName = "of" )
public class Test {

}
