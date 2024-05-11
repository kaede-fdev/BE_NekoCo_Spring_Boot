package VJames.Development.NingnenCo_BE.Infrastructure.Core.DefautStorage;

import VJames.Development.NingnenCo_BE.Application.DefautStorage.IPasswordRule;

public class PassRule implements IPasswordRule {
    private final String PASS_RULE;

    public PassRule() {
        PASS_RULE = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&.])[A-Za-z\\d@$!%*?&.]{8,}$";
    }

    @Override
    public String getPattern() {
        return PASS_RULE;
    }
}
