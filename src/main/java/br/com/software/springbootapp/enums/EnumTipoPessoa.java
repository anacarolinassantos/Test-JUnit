package br.com.software.springbootapp.enums;

import br.com.software.springbootapp.helpper.PersistableEnum;
import lombok.Getter;

@Getter
public enum EnumTipoPessoa implements PersistableEnum<String> {

    FISICA(1, "F", "Pessoa Física"),
    JURIDICA(2, "J", "Pessoa Física");

    private final Integer codigo;
    private final String sigla;
    private final String nome;

    EnumTipoPessoa(final Integer codigo, final String sigla, final String nome) {
        this.codigo = codigo;
        this.sigla = sigla;
        this.nome = nome;
    }

    public static EnumTipoPessoa getBySigla(final String sigla) {
        for (EnumTipoPessoa value : values()) {
            if (value.getNome().equals(sigla)) {
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
