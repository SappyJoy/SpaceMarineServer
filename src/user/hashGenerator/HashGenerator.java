package user.hashGenerator;

public abstract class HashGenerator {
    protected final String salt;
    protected final String pepper;

    protected HashGenerator() {
        salt = "PG2y7zDJsP";
        pepper = "FzjNUcmZSR";
    }

    protected abstract String generateHash(String string) throws HashGeneratorException;

    public final String generateHashWithSalt(String string) throws HashGeneratorException {
        String stringWithSalt = string + salt;
        return generateHash(stringWithSalt);
    }

    public final String generateHashWithPepperAndSalt(String string) throws HashGeneratorException {
        String stringWithPepperAndSalt = pepper + string + salt;
        return generateHash(stringWithPepperAndSalt);
    }
}
