package risk;

import java.math.BigDecimal;

public interface Risk {

    BigDecimal getPremium(BigDecimal sumInsured);

}
