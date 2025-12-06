package br.unitins.ecommerce.converterjpa;

import br.unitins.ecommerce.model.produto.TipoPulseira;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TipoPulseiraConverter implements AttributeConverter<TipoPulseira, String> {
    
    @Override
    public String convertToDatabaseColumn(TipoPulseira tipoPulseira) {

        return tipoPulseira == null ? null : tipoPulseira.toString();
    }

    @Override
    public TipoPulseira convertToEntityAttribute(String label) {

        return label == null ? null : TipoPulseira.valueOf(label);
    }
}
