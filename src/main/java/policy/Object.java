package policy;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class Object {

    private String name;

    private List<SubObject> subObjects;

}
