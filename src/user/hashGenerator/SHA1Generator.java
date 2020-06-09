package user.hashGenerator;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA1Generator extends HashGenerator {
    @Override
    protected String generateHash(String string) throws HashGeneratorException {
        String sha1;

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(string.getBytes(StandardCharsets.UTF_8), 0, string.length());
            sha1 = DatatypeConverter.printHexBinary(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new HashGeneratorException(e);
        }

        return sha1;
    }
}
