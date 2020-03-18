package com.idwall.tests.challenge.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class ReportSteps {

    private final static String URL_BASE = "https://api-v2.idwall.co";
    private String finalEndpoint;
    private Response response;

    @Given("a POST request to endpoint {string}")
    public void a_POST_request_to_endpoint_relatorios(String endpoint) {
        this.finalEndpoint = URL_BASE.concat(endpoint);
    }

    @When("empty data are sent")
    public void empty_data_are_sent() {
        String body = getFinalBody("consultaPessoaDefault", "",  "",  "");
        response = getPostResponse(body);
    }

    @Then("the API returns {string} error with {int} code and {string} message.")
    public void the_API_returns_error_with_code_and_message(String error, int code, String message) {
        String actualError = response.getBody().jsonPath().get("error");
        String actualMessage = response.getBody().jsonPath().get("message");
        int actualStatusCode = response.getBody().jsonPath().get("status_code");
        Assert.assertEquals(error, actualError);
        Assert.assertEquals(message, actualMessage);
        Assert.assertEquals(code, actualStatusCode);
    }

    @When("the {string}, {string} , {string} , {string} parameters are sent")
    public void the_matriz_birthday_name_cpf_parameters_are_sent(String matriz, String birthday, String name, String cpf) {
        String body = getFinalBody(matriz, birthday, name, cpf);
        response = getPostResponse(body);
    }

    @Then("the API returns {int}, {string}, {string}, {string}")
    public void the_API_returns(int code, String status, String message, String result) {
        int actualCode = response.getBody().jsonPath().get("status_code");
        Map<String, String> resultMap = response.getBody().jsonPath().getMap("result");
        String actualStatus = resultMap.get("status");
        String actualMessage = resultMap.get("mensagem");
        String actualResult = resultMap.get("resultado");
        Assert.assertEquals(actualCode, code);
        Assert.assertEquals(actualStatus, status);
        Assert.assertEquals(actualMessage, message);
        Assert.assertEquals(actualResult, result);
    }


    /**
     * Auxiliar method - can be moved to another general class for code reuse.
     * @param matriz
     * @param birthday
     * @param name
     * @param cpf
     * @return
     */

    private String getFinalBody(String matriz, String birthday, String name, String cpf){
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
     * Auxiliar method - can be moved to another general class for code reuse.
     * @param body
     * @return
     */
    private Response getPostResponse(String body){
        System.out.println(System.getProperty("idwallToken"));
        return given().contentType("application/json")
                .header("Authorization", System.getProperty("idwallToken"))
                .body(body)
                .post(this.finalEndpoint)
                .then()
                .extract()
                .response();
    }
}
