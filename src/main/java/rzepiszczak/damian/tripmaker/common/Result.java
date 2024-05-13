package rzepiszczak.damian.tripmaker.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Result {

    private enum Code {
        SUCCESS, FAILURE
    }

    private Code code;

    public static Result success() {
        Result result = new Result();
        result.code = Code.SUCCESS;
        return result;
    }

    public static Result failure() {
        Result result = new Result();
        result.code = Code.FAILURE;
        return result;
    }

    public boolean isSuccessful() {
        return code == Code.SUCCESS;
    }

    public boolean isFailure() {
        return code == Code.FAILURE;
    }
}
