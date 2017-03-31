package api;

import config.PromotionsConfig;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.model.*;
import util.JsonFileUtil;

import java.util.concurrent.TimeUnit;

/**
 * Created by skoong on 2/21/17.
 */
@API
public class Promotions implements APIResource{

    public void init(MockServerClient mockServer) {

        // Wallet Model
        // 1 Offer English, added multiple wallets support
        for (String wallet : PromotionsConfig.SINGLE_OFFER_ENGLISH_WALLET) {
            mockServer.when(
                    HttpRequest.request()
                            .withPath("/TDSM/REST/ExecuteTag")
                            .withMethod("POST")
                            .withBody(JsonBody.json(getJsonObject(wallet, "en")))
            ).respond(
                    HttpResponse.response()
                            .withStatusCode(200)
                            .withHeader(new Header("Content-Type", "application/json; charset=utf-8"))
                            .withBody(JsonFileUtil.loadFile("main/resources/promotions/single_promotion.json"))
            );
        }

        // 1 Offer French
        mockServer.when(
                HttpRequest.request()
                        .withPath("/TDSM/REST/ExecuteTag")
                        .withMethod("POST")
                        .withBody(JsonBody.json(getJsonObject(PromotionsConfig.SINGLE_OFFER_FRENCH_WALLET, "fr")))
        ).respond(
                HttpResponse.response()
                        .withStatusCode(200)
                        .withHeader(new Header("Content-Type", "application/json; charset=utf-8"))
                        .withBody(JsonFileUtil.loadFile("main/resources/promotions/single_promotion_fr.json"))
        );

        // Multiple Offers
        mockServer.when(
                HttpRequest.request()
                        .withPath("/TDSM/REST/ExecuteTag")
                        .withMethod("POST")
                        .withBody(JsonBody.json(getJsonObject(PromotionsConfig.MULTIPLE_OFFERS_ENGLISH_WALLET, "en")))
        ).respond(
                HttpResponse.response()
                        .withStatusCode(200)
                        .withHeader(new Header("Content-Type", "application/json; charset=utf-8"))
                        .withBody(JsonFileUtil.loadFile("main/resources/promotions/multiple_promotions.json"))
        );

        // Pay to Card
        // 1 Offer English
        mockServer.when(
                HttpRequest.request()
                        .withPath("/TDSM/REST/ExecuteTag")
                        .withMethod("POST")
                        .withBody(JsonBody.json(getJsonObject(PromotionsConfig.SINGLE_OFFER_ENGLISH_P2C, "en")))
        ).respond(
                HttpResponse.response()
                        .withStatusCode(200)
                        .withHeader(new Header("Content-Type", "application/json; charset=utf-8"))
                        .withBody(JsonFileUtil.loadFile("main/resources/promotions/single_promotion.json"))
        );

        // 1 Offer French
        mockServer.when(
                HttpRequest.request()
                        .withPath("/TDSM/REST/ExecuteTag")
                        .withMethod("POST")
                        .withBody(JsonBody.json(getJsonObject(PromotionsConfig.SINGLE_OFFER_FRENCH_P2C, "fr")))
        ).respond(
                HttpResponse.response()
                        .withStatusCode(200)
                        .withHeader(new Header("Content-Type", "application/json; charset=utf-8"))
                        .withBody(JsonFileUtil.loadFile("main/resources/promotions/single_promotion_fr.json"))
        );

        // Multiple Offers
        mockServer.when(
                HttpRequest.request()
                        .withPath("/TDSM/REST/ExecuteTag")
                        .withMethod("POST")
                        .withBody(JsonBody.json(getJsonObject(PromotionsConfig.MULTIPLE_OFFERS_ENGLISH_P2C, "en")))
        ).respond(
                HttpResponse.response()
                        .withStatusCode(200)
                        .withHeader(new Header("Content-Type", "application/json; charset=utf-8"))
                        .withBody(JsonFileUtil.loadFile("main/resources/promotions/multiple_promotions.json"))
        );

        // 1 Offer Timeout
        mockServer.when(
                HttpRequest.request()
                        .withPath("/TDSM/REST/ExecuteTag")
                        .withMethod("POST")
                        .withBody(JsonBody.json(getJsonObject(PromotionsConfig.SINGLE_OFFER_TIMEOUT, "en")))
        ).respond(
                HttpResponse.response()
                        .withStatusCode(200)
                        .withHeader(new Header("Content-Type", "application/json; charset=utf-8"))
                        .withBody(JsonFileUtil.loadFile("main/resources/promotions/single_promotion.json"))
                        .withDelay(TimeUnit.SECONDS, 10)
        );

        // Zero Rewards - Base Case
        mockServer.when(
                HttpRequest.request()
                        .withPath("/TDSM/REST/ExecuteTag")
                        .withMethod("POST")
                        .withBody(RegexBody.regex("(?s).*GetAvailablePromotions.*"))
        ).respond(
                HttpResponse.response()
                        .withStatusCode(200)
                        .withHeader(new Header("Content-Type", "application/json; charset=utf-8"))
                        .withBody(JsonFileUtil.loadFile("main/resources/promotions/zero_promotions.json"))
        );

    }

    private String getJsonObject(String walletNumber, String languageCode) {
        JSONObject tags = new JSONObject();
        JSONObject expectedTags = new JSONObject();
        JSONArray tag = new JSONArray();

        JSONObject innerTag = new JSONObject();
        try {
            innerTag.put("tagName", "GetAvailablePromotions");

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
