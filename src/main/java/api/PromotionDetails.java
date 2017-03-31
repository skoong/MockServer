package api;

import config.PromotionsConfig;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.model.*;
import util.JsonFileUtil;

import static org.mockserver.model.HttpCallback.callback;

/**
 * Created by skoong on 2/27/17.
 */
@API
public class PromotionDetails implements APIResource{

    public void init(MockServerClient mockServer) {

        // Promotion Details English
        for (String promotionID : PromotionsConfig.PROMOTION_DETAILS_ID_EN) {
            String body = JsonFileUtil.loadFile("main/resources/promotions/details/promotion_detail_en.json").replaceFirst("\"colValue.*[0-9].*\n.*colName\".*\"v_PromotionID\"",
                    "\"colValue\": \"" + promotionID + "\",\n" +
                            "                                        \"colName\": \"v_PromotionID\"");

            mockServer.when(
                    HttpRequest.request()
                            .withPath("/TDSM/REST/ExecuteTag")
                            .withMethod("POST")
                            .withBody(RegexBody.regex("(?s).*GetAvailablePromotionDetail.*" +
                                    "paramValue\":\"" + promotionID + ".*" +
                                    "paramValue\":\"en\".*"))
            ).respond(
                    HttpResponse.response()
                            .withStatusCode(200)
                            .withHeader(new Header("Content-Type", "application/json; charset=utf-8"))
                            .withBody(body)
            );
        }

        // Promotion Details French
        for (String promotionID : PromotionsConfig.PROMOTION_DETAILS_ID_FR) {
            String body = JsonFileUtil.loadFile("main/resources/promotions/details/promotion_detail_fr.json").replaceFirst("\"colValue.*[0-9].*\n.*colName\".*\"v_PromotionID\"",
                    "\"colValue\": \"" + promotionID + "\",\n" +
                            "                                        \"colName\": \"v_PromotionID\"");

            mockServer.when(
                    HttpRequest.request()
                            .withPath("/TDSM/REST/ExecuteTag")
                            .withMethod("POST")
                            .withBody(RegexBody.regex("(?s).*GetAvailablePromotionDetail.*" +
                                    "paramValue\":\"" + promotionID +".*" +
                                    "paramValue\":\"fr\".*"))
            ).respond(
                    HttpResponse.response()
                            .withStatusCode(200)
                            .withHeader(new Header("Content-Type", "application/json; charset=utf-8"))
                            .withBody(body)
            );
        }

    }

}
