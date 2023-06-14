package pawel.borkowski.GithubRestApiv3Project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pawel.borkowski.GithubRestApiv3Project.controller.model.BranchInfoModel;
import pawel.borkowski.GithubRestApiv3Project.controller.model.RepositoryInfoModel;
import pawel.borkowski.GithubRestApiv3Project.service.exception.ExceptionResponse;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class GithubApiService {
    public ResponseEntity<Object> makeGitHubApiRequest(String url, HttpHeaders headers) {
        WebClient webClient = WebClient.builder().defaultHeaders(httpHeaders -> httpHeaders.addAll(headers)).build();
        return webClient
                .method(HttpMethod.GET)
                .uri(url)
                .retrieve()
                .toEntity(Object.class)
                .block();
    }

    public ExceptionResponse getErrorResponse(HttpStatus status, String message) {
        return ExceptionResponse.builder()
                .message(message)
                .status(status.value())
                .build();
    }

    public List<RepositoryInfoModel> extractRepositoryInfo(Object responseBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<?> repositories = objectMapper.convertValue(responseBody, List.class);

        List<RepositoryInfoModel> repositoryInfos = new ArrayList<>();
        for (Object repository : repositories) {
            LinkedHashMap<?, ?> repositoryMap = (LinkedHashMap<?, ?>) repository;
            boolean isFork = (boolean) repositoryMap.get("fork");
            if (!isFork) {
                String repositoryName = repositoryMap.get("name").toString();
                String ownerLogin = ((LinkedHashMap<?, ?>) repositoryMap.get("owner")).get("login").toString();

                String branchesUrl = repositoryMap.get("branches_url").toString().replace("{/branch}", "");
                ResponseEntity<Object> branchesResponse = makeGitHubApiRequest(branchesUrl, new HttpHeaders());
                if (branchesResponse.getStatusCode() == HttpStatus.OK) {
                    List<?> branches = objectMapper.convertValue(branchesResponse.getBody(), List.class);
                    List<BranchInfoModel> branchInfos = new ArrayList<>();
                    for (Object branch : branches) {
                        LinkedHashMap<?, ?> branchMap = (LinkedHashMap<?, ?>) branch;
                        String branchName = branchMap.get("name").toString();
                        String lastCommitSha = ((LinkedHashMap<?, ?>) branchMap.get("commit")).get("sha").toString();
                        branchInfos.add(new BranchInfoModel(branchName, lastCommitSha));
                    }
                    repositoryInfos.add(new RepositoryInfoModel(repositoryName, ownerLogin, branchInfos));
                }
            }
        }
        return repositoryInfos;
    }
}
