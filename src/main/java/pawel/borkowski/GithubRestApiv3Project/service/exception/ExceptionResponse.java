package pawel.borkowski.GithubRestApiv3Project.service.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExceptionResponse {
    private String message;
    private int status;
}
