package pawel.borkowski.GithubRestApiv3Project.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pawel.borkowski.GithubRestApiv3Project.controller.model.RepositoryInfoModel;
import pawel.borkowski.GithubRestApiv3Project.service.GithubApiService;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class GithubApiController {
    private static final String GITHUB_API_URL = "https://api.github.com";
    private final GithubApiService githubApiService;

    public GithubApiController(GithubApiService githubApiService) {
        this.githubApiService = githubApiService;
    }

    @GetMapping(value = "/repositories/{username}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRepositories(@PathVariable String username) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));


        ResponseEntity<Object> userResponse = githubApiService.makeGitHubApiRequest(GITHUB_API_URL + "/users/" + username, headers);

        if (userResponse.getStatusCode() != HttpStatus.OK) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(githubApiService.getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred"));
        }

        String repositoriesUrl = ((LinkedHashMap<?, ?>) Objects.requireNonNull(userResponse.getBody())).get("repos_url").toString();
        ResponseEntity<Object> repositoriesResponse = githubApiService.makeGitHubApiRequest(repositoriesUrl, headers);

        if (repositoriesResponse.getStatusCode() == HttpStatus.OK) {
            List<RepositoryInfoModel> repositoryInfos = githubApiService.extractRepositoryInfo(repositoriesResponse.getBody());
            return ResponseEntity.ok(repositoryInfos);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(githubApiService.getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred"));
        }
    }
}
