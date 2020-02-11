package policy;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
public class Policy {

    private String number;

    private String status;

    private List<Object> objects;

    private BigDecimal premium;

}
