package br.com.software.springbootapp.config;


import br.com.software.springbootapp.enums.EnumAmbiente;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ConfiguracaoPersonlizada {

    private EnumAmbiente ambiente;

}
