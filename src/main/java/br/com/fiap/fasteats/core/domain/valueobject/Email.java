package br.com.fiap.fasteats.core.domain.valueobject;

import br.com.fiap.fasteats.core.domain.exception.RegraNegocioException;

import java.util.regex.Pattern;

public record Email(String valor) {
    private static final Pattern PADRAO_EMAIL = Pattern.compile(
            "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,6}$"
    );

    public Email {
        if (Boolean.FALSE.equals(validarEmail(valor))) throw new RegraNegocioException("Email inválido");
    }

    public static Boolean validarEmail(String email) {
        return PADRAO_EMAIL.matcher(email).matches();
    }
}
