package ro.ubb.lab11.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CastedSpellsDTO {
    List<CastedSpellDTO> castedSpells;
}
