package cz.pwc.model;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = "cca3")
@ToString(of = "cca3")
public class Country {

    private String cca3;
    private List<String> borders;
}
