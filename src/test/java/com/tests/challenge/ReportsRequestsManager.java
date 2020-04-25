package com.tests.challenge;
import io.restassured.response.Response;
import org.awaitility.Awaitility;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import static io.restassured.RestAssured.given;

public class ReportsRequestsManager {

    /**
     *
     * @param matriz body parameter
     * @param birthday body parameter
     * @param name body parameter
     * @param cpf body parameter
     * @return body that will be used in api request
     */

    public String getFinalBody(String matriz, String birthday, String name, String cpf){
        return  "{\n" +
                "    \"matriz\": \"" + matriz + "\",\n" +
                "    \"parametros\": {\n" +
                "        \"cpf_data_de_nascimento\": \"" + birthday + "\",\n" +
                "        \"cpf_nome\": \"" + name + "\",\n" +
                "        \"cpf_numero\": \"" + cpf + "\"\n" +
                "    }\n" +
                "}";
    }

    /**
     *
     * @param endpoint final endpoint to api request
     * @param body body that must be sent in request
     * @return response object from POST request to endpoint parameter
     */
    public Response getPostResponse(String endpoint, String body){
        return given().contentType("application/json")
                .header("Authorization", System.getProperty("idwallToken"))
                .body(body)
                .post(endpoint)
                .then()
                .extract()
                .response();
    }

    /**
     *
     * @param endpoint final endpoint to api request
     * @return response object from GET request to endpoint parameter
     */
    public Response getReportStatusFromGetRequest(String endpoint){

        Awaitility.await().
                atMost(20, TimeUnit.MINUTES).
                with().
                pollInterval(30, TimeUnit.SECONDS).
                until(() -> Objects.requireNonNull(this.getReportStatus(endpoint)).equalsIgnoreCase("CONCLUIDO"));

        return given().contentType("application/json")
                .header("Authorization", System.getProperty("idwallToken"))
                .get(endpoint)
                .then()
                .extract()
                .response();
    }

    /**
     *
     * @param endpoint final endpoint to api request
     * @return report status: PROCESSANDO | CONCLUIDO
     */
    private String getReportStatus(String endpoint) {
        return given().contentType("application/json")
                .header("Authorization", System.getProperty("idwallToken"))
                .get(endpoint)
                .then()
                .extract()
                .path("result.status");
    }
}
