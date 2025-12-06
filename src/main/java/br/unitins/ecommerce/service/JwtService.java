package br.unitins.ecommerce.service;

import br.unitins.ecommerce.model.usuario.Usuario;

public interface JwtService {
    public String generateJwt(Usuario usuario);
}
