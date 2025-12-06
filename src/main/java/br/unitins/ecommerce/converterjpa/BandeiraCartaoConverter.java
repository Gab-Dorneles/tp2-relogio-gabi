package br.unitins.ecommerce.converterjpa;

import br.unitins.ecommerce.model.pedido.pagamento.BandeiraCartao;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BandeiraCartaoConverter implements AttributeConverter<BandeiraCartao, String> {

    @Override
    public String convertToDatabaseColumn(BandeiraCartao bandeiraCartao) {
        
        return bandeiraCartao == null ? null : bandeiraCartao.toString();
    }

    @Override
    public BandeiraCartao convertToEntityAttribute(String label) {
        
        return label == null ? null : BandeiraCartao.valueOf(label);
    }
    
}
