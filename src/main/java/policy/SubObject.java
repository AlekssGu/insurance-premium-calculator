package policy;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class SubObject {

    private String name;

    private BigDecimal sumInsured;

    private String riskType;

}
