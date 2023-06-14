package pawel.borkowski.GithubRestApiv3Project.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RepositoryInfoModel {
    private String repositoryName;
    private String ownerLogin;
    private List<BranchInfoModel> branches;
}
