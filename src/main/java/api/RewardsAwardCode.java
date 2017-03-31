package api;

import config.RewardsConfig;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.model.Header;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.RegexBody;
import util.JsonFileUtil;

import java.util.concurrent.TimeUnit;

/**
 * Created by vmasalau on 3/3/17.
 */
@API
public class RewardsAwardCode implements APIResource {

    public void init(MockServerClient mockServer) {

        // AwardCode timeout
        mockServer.when(
                HttpRequest.request()
                        .withPath("/TDSM/REST/ExecuteTag")
                        .withMethod("POST")
                        .withBody(RegexBody.regex(".*GetRewardCode.*paramValue\":\""+ RewardsConfig.SINGLE_REWARD_ENGLISH_TIMEOUT_WALLET+"\".*paramValue\":\"en\".*"))
        ).respond(
                HttpResponse.response()
                        .withStatusCode(200)
                        .withHeader(new Header("Content-Type", "application/json; charset=utf-8"))
                        .withBody(JsonFileUtil.loadFile("main/resources/rewards/awards/reward_award_en.json"))
                        .withDelay(TimeUnit.SECONDS, 10)
        );

        // AwardCode english + 1 seconds delay to verify loading symbol
        mockServer.when(
                HttpRequest.request()
                        .withPath("/TDSM/REST/ExecuteTag")
                        .withMethod("POST")
                        .withBody(RegexBody.regex(".*GetRewardCode.*paramValue\":\""+ RewardsConfig.SINGLE_REWARD_ENGLISH_WALLET+"\".*paramValue\":\"en\".*"))
        ).respond(
                HttpResponse.response()
                        .withStatusCode(200)
                        .withHeader(new Header("Content-Type", "application/json; charset=utf-8"))
                        .withBody(JsonFileUtil.loadFile("main/resources/rewards/awards/reward_award_en.json"))
                        .withDelay(TimeUnit.SECONDS, 3)
        );

        // AwardCode english + 1 seconds delay to verify loading symbol
        mockServer.when(
                HttpRequest.request()
                        .withPath("/TDSM/REST/ExecuteTag")
                        .withMethod("POST")
                        .withBody(RegexBody.regex(".*GetRewardCode.*paramValue\":\""+ RewardsConfig.MULTIPLE_REWARDS_ENGLISH_WALLET+"\".*paramValue\":\"en\".*"))
        ).respond(
                HttpResponse.response()
                        .withStatusCode(200)
                        .withHeader(new Header("Content-Type", "application/json; charset=utf-8"))
                        .withBody(JsonFileUtil.loadFile("main/resources/rewards/awards/reward_award_en.json"))
                        .withDelay(TimeUnit.SECONDS, 3)
        );

        // AwardCode French
        mockServer.when(
                HttpRequest.request()
                        .withPath("/TDSM/REST/ExecuteTag")
                        .withMethod("POST")
                        .withBody(RegexBody.regex("(?s).*GetRewardCode.*paramValue\":\"fr\".*"))
        ).respond(
                HttpResponse.response()
                        .withStatusCode(200)
                        .withHeader(new Header("Content-Type", "application/json; charset=utf-8"))
                        .withBody(JsonFileUtil.loadFile("main/resources/rewards/awards/reward_award_fr.json"))
        );

        // GetRewardOptions English
        mockServer.when(
                HttpRequest.request()
                        .withPath("/TDSM/REST/ExecuteTag")
                        .withMethod("POST")
                        .withBody(RegexBody.regex(".*GetRewardOptions.*paramValue\":\"en\".*"))
        ).respond(
                HttpResponse.response()
                        .withStatusCode(200)
                        .withHeader(new Header("Content-Type", "application/json; charset=utf-8"))
                        .withBody(JsonFileUtil.loadFile("main/resources/rewards/options/reward_option_en.json"))
        );

        // GetRewardOptions French
        mockServer.when(
                HttpRequest.request()
                        .withPath("/TDSM/REST/ExecuteTag")
                        .withMethod("POST")
                        .withBody(RegexBody.regex("(?s).*GetRewardOptions.*paramValue\":\"fr\".*"))
        ).respond(
                HttpResponse.response()
                        .withStatusCode(200)
                        .withHeader(new Header("Content-Type", "application/json; charset=utf-8"))
                        .withBody(JsonFileUtil.loadFile("main/resources/rewards/options/reward_option_fr.json"))
        );
    }
}
