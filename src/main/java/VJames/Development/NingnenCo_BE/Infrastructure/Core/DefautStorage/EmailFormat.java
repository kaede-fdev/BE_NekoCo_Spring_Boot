package VJames.Development.NingnenCo_BE.Infrastructure.Core.DefautStorage;

import VJames.Development.NingnenCo_BE.Application.DefautStorage.IEmailFormat;

public class EmailFormat implements IEmailFormat {
    private final String EMAIL_FORMAT;

    public EmailFormat() {
        EMAIL_FORMAT = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
    }

    @Override
    public String getPattern() {
        return EMAIL_FORMAT;
    }
}
