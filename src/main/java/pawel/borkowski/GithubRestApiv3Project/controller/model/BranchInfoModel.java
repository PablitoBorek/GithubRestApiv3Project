package pawel.borkowski.GithubRestApiv3Project.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BranchInfoModel {
    private String branchName;
    private String lastCommitSha;
}
