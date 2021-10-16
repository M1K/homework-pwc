package cz.pwc.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = "cca3")
@ToString(of = "cca3")
@RequiredArgsConstructor
public class Step {

    private final String cca3;
    private final Step from;

    public Step(String cca3) {
        this(cca3, null);
    }
}
