package ro.ubb.lab11.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WizardsPetsDTO extends BaseDTO<Long>{
    private String wizardName;
    private Integer noPets;
}
