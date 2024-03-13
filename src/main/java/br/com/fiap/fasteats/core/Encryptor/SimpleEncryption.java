package br.com.fiap.fasteats.core.Encryptor;

import org.jasypt.util.text.BasicTextEncryptor;

public class SimpleEncryption {

    private static String CHAVE = "D3CB53F3-5D90-445A-85EC-BE30954708D2";

    public static String Encrypt(String texto){
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword(CHAVE);
        return  textEncryptor.encrypt(texto);
    }

    public static String Decrypt(String myEncryptedText){
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword(CHAVE);
        return  textEncryptor.decrypt(myEncryptedText);
    }

}
