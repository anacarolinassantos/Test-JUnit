package br.com.software.springbootapp.enums;

import br.com.software.springbootapp.helpper.PersistableEnum;
import lombok.Getter;

@Getter
public enum EnumSexo implements PersistableEnum<String> {

    MASCULINO(1, "M", "Masculino"),
    FEMININO(2, "F", "Feminino");

    private final Integer codigo;
    private final String sigla;
    private final String nome;

    EnumSexo(final Integer codigo, final String sigla, final String nome) {
        this.codigo = codigo;
        this.sigla = sigla;
        this.nome = nome;
    }

    public static EnumSexo getBySigla(final String sigla) {
        for (EnumSexo value : values()) {
            if (value.getSigla().equals(sigla)) {
                return value;
            }
        }
        return null;
    }

    public static EnumSexo getByNome(final String nome) {
        for (EnumSexo value : values()) {
            if (value.getNome().equals(nome)) {
                return value;
            }
        }
        return null;
    }

    @Override
    public String getValue() {
        return sigla;
    }

}
