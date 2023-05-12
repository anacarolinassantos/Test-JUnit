package br.com.software.springbootapp.domain;


import br.com.software.springbootapp.config.EnumColumnType;
import br.com.software.springbootapp.helpper.PersistableEnum;
import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonNodeStringType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Getter;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;


@TypeDef(name = "string-array", typeClass = StringArrayType.class)
@TypeDef(name = "int-array", typeClass = IntArrayType.class)
@TypeDef(name = "json", typeClass = JsonStringType.class)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@TypeDef(name = "jsonb-node", typeClass = JsonNodeBinaryType.class)
@TypeDef(name = "json-node", typeClass = JsonNodeStringType.class)
@TypeDef(name = "enum", typeClass = EnumColumnType.class, defaultForType = PersistableEnum.class)
@Getter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
    @Column(name = "data_cadastro", insertable = true, updatable = false)
    private LocalDateTime dataCadastro;

    @NotNull
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @PreUpdate
    public void onPreUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }

    @PrePersist
    public void onPrePersist() {
        this.dataCadastro = this.dataAtualizacao = LocalDateTime.now();
    }
}