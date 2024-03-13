package br.com.fiap.fasteats.dataprovider;

import br.com.fiap.fasteats.core.dataprovider.CriptografiaOutputPort;
import lombok.RequiredArgsConstructor;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CriptografiaAdapter implements CriptografiaOutputPort {
    private final BasicTextEncryptor textEncryptor;

    @Autowired
    public CriptografiaAdapter(@Value("${security.encryption.key}") String chaveCriptografia) {
        this.textEncryptor = new BasicTextEncryptor();
        this.textEncryptor.setPassword(chaveCriptografia);
    }

    @Override
    public String criptografar(String valor) {
        return textEncryptor.encrypt(valor);
    }
}
