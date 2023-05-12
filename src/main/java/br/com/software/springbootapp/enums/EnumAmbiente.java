package br.com.software.springbootapp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public enum EnumAmbiente {

    PRODUCAO(1, "Produção"),
    TEST(2, "Teste");

    private final Integer codigo;
    private final String descricao;

    public boolean isHomologacao() {
        return this == TEST;
    }

    public boolean isProducao() {
        return this == PRODUCAO;
    }

    public static EnumAmbiente valueOfCodigo(final Integer codigo) {
        for (EnumAmbiente ambiente : EnumAmbiente.values()) {
            if (ambiente.getCodigo().equals(codigo)) {
                return ambiente;
            }
        }
        return null;
    }

}
