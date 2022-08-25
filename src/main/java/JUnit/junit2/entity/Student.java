package JUnit.junit2.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Student {
    int sid;
    String name;
    String sex;
}
