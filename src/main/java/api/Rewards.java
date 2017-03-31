package api;

import config.RewardsConfig;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.model.*;
import util.JsonFileUtil;

/**
 * Created by skoong on 2/27/17.
 */
@API
public class Rewards implements APIResource{

    public void init(MockServerClient mockServer) {

        // Zero Rewards - Base Case
        mockServer.when(
                HttpRequest.request()
                        .withPath("/TDSM/REST/ExecuteTag")
                        .withMethod("POST")
                        .withBody(JsonBody.json(getJsonObject(RewardsConfig.ZERO_REWARDS_ENGLISH_WALLET, "en")))
        ).respond(
                HttpResponse.response()
                        .withStatusCode(200)
                        .withHeader(new Header("Content-Type", "application/json; charset=utf-8"))
                        .withBody(JsonFileUtil.loadFile("main/resources/rewards/zero_rewards.json"))
        );

        // One Reward - EN
        // has 2 seconds delay to display loading symbol
        mockServer.when(
                HttpRequest.request()
                        .withPath("/TDSM/REST/ExecuteTag")
                        .withMethod("POST")
                        .withBody(JsonBody.json(getJsonObject(RewardsConfig.SINGLE_REWARD_ENGLISH_WALLET, "en")))
        ).respond(
                HttpResponse.response()
                        .withStatusCode(200)
                        .withHeader(new Header("Content-Type", "application/json; charset=utf-8"))
                        .withBody(JsonFileUtil.loadFile("main/resources/rewards/single_reward_en.json"))
        );

        // One Reward - FR (Cannot find one for FR)
        mockServer.when(
                HttpRequest.request()
                        .withPath("/TDSM/REST/ExecuteTag")
                        .withMethod("POST")
                        .withBody(JsonBody.json(getJsonObject(RewardsConfig.SINGLE_REWARD_FRENCH_WALLET, "fr")))
        ).respond(
                HttpResponse.response()
                        .withStatusCode(200)
                        .withHeader(new Header("Content-Type", "application/json; charset=utf-8"))
                        .withBody(JsonFileUtil.loadFile("main/resources/rewards/single_reward_fr.json"))
        );

        // Multiple Rewards
        mockServer.when(
                HttpRequest.request()
                        .withPath("/TDSM/REST/ExecuteTag")
                        .withMethod("POST")
                        .withBody(JsonBody.json(getJsonObject(RewardsConfig.MULTIPLE_REWARDS_ENGLISH_WALLET, "en")))
        ).respond(
                HttpResponse.response()
                        .withStatusCode(200)
                        .withHeader(new Header("Content-Type", "application/json; charset=utf-8"))
                        .withBody(JsonFileUtil.loadFile("main/resources/rewards/multiple_rewards.json"))
        );

        // Single Choice Reward
        mockServer.when(
                HttpRequest.request()
                        .withPath("/TDSM/REST/ExecuteTag")
                        .withMethod("POST")
                        .withBody(JsonBody.json(getJsonObject(RewardsConfig.SINGLE_REWARD_CHOICE_ENGLISH_WALLET, "en")))
        ).respond(
                HttpResponse.response()
                        .withStatusCode(200)
                        .withHeader(new Header("Content-Type", "application/json; charset=utf-8"))
                        .withBody(JsonFileUtil.loadFile("main/resources/rewards/single_reward_with_choice.json"))
        );


        // Multiple Choice Rewards
        mockServer.when(
                HttpRequest.request()
                        .withPath("/TDSM/REST/ExecuteTag")
                        .withMethod("POST")
                        .withBody(JsonBody.json(getJsonObject(RewardsConfig.MULTIPLE_REWARDS_CHOICE_ENGLISH_WALLET, "en")))
        ).respond(
                HttpResponse.response()
                        .withStatusCode(200)
                        .withHeader(new Header("Content-Type", "application/json; charset=utf-8"))
                        .withBody(JsonFileUtil.loadFile("main/resources/rewards/multiple_rewards_with_choice.json"))
        );

        // Singe reward timeout
        mockServer.when(
                HttpRequest.request()
                        .withPath("/TDSM/REST/ExecuteTag")
                        .withMethod("POST")
                        .withBody(JsonBody.json(getJsonObject(RewardsConfig.SINGLE_REWARD_ENGLISH_TIMEOUT_WALLET, "en")))
        ).respond(
                HttpResponse.response()
                        .withStatusCode(200)
                        .withHeader(new Header("Content-Type", "application/json; charset=utf-8"))
                        .withBody(JsonFileUtil.loadFile("main/resources/rewards/single_reward_en.json"))
        );

        // Zero Rewards - Base Case
        mockServer.when(
                HttpRequest.request()
                        .withPath("/TDSM/REST/ExecuteTag")
                        .withMethod("POST")
                        .withBody(RegexBody.regex("(?s).*GetEarnedRewards.*"))
        ).respond(
                HttpResponse.response()
                        .withStatusCode(200)
                        .withHeader(new Header("Content-Type", "application/json; charset=utf-8"))
                        .withBody(JsonFileUtil.loadFile("main/resources/rewards/zero_rewards.json"))
        );

    }

    private String getJsonObject(String walletNumber, String languageCode) {
        JSONObject tags = new JSONObject();
        JSONObject expectedTags = new JSONObject();
        JSONArray tag = new JSONArray();

        JSONObject innerTag = new JSONObject();
        try {
            innerTag.put("tagName", "GetEarnedRewards");

            JSONObject params = new JSONObject();
            JSONArray param = new JSONArray();

            JSONObject parameter1 = new JSONObject();
            parameter1.put("paramName", "WalletNumber");
            parameter1.put("paramValue", walletNumber);

            JSONObject parameter2 = new JSONObject();
            parameter2.put("paramName", "LanguageCode");
            parameter2.put("paramValue", languageCode);

            param.put(0, parameter1);
            param.put(1, parameter2);

            params.put("param", param);
            innerTag.put("params", params);

            tag.put(0, innerTag);

            expectedTags.put("tag", tag);

            tags.put("tags", expectedTags);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tags.toString();
    }
}
