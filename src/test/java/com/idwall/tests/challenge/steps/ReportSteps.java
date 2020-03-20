package com.idwall.tests.challenge.steps;

import com.idwall.tests.challenge.ReportsRequestsManager;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;
import java.util.Map;


public class ReportSteps {

    private final static String URL_BASE = "https://api-v2.idwall.co";
    private ReportsRequestsManager reportsRequestsManager = new ReportsRequestsManager();
    private Response response;
    private String reportId;

    @Given("a POST request to endpoint {string} with empty data are sent")
    public void a_POST_request_to_endpoint_relatorios(String endpoint) {
        String body = reportsRequestsManager.getFinalBody("consultaPessoaDefault", "",  "",  "");
        response = reportsRequestsManager.getPostResponse(URL_BASE.concat(endpoint), body);
    }

    @Given("a POST request to endpoint {string} with {string}, {string} , {string} and {string} parameters")
    public void aPOSTRequestToEndpointWithMatrizBirthdayNameAndCpfParameters(String endpoint, String matriz, String birthday, String name, String cpf) {
        String body = reportsRequestsManager.getFinalBody(matriz, birthday, name, cpf);
        response = reportsRequestsManager.getPostResponse(URL_BASE.concat(endpoint), body);
    }


    @When("the report id is returned")
    public void theReportIdIsReturned() {
        Map<String, String> resultMap = response.getBody().jsonPath().getMap("result");
        reportId = resultMap.get("numero");
    }

    @And("a GET request to endpoint {string} with the report id is sent")
    public void aGETRequestToEndpointWithTheReportIdIsSent(String endpoint) {
        String finalEndpoint = URL_BASE.concat(endpoint.replace("{id}", reportId));
        response = reportsRequestsManager.getReportStatusFromGetRequest(finalEndpoint);
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

    @Then("the API returns {int}, {string}, {string}, {string}")
    public void the_API_returns(int code, String status, String message, String result) {
        int actualCode = response.getBody().jsonPath().get("status_code");
        Map<String, String> resultMap = response.getBody().jsonPath().getMap("result");
        String actualStatus = resultMap.get("status");
        String actualMessage = resultMap.get("mensagem");
        String actualResult = resultMap.get("resultado");
        Assert.assertEquals(code, actualCode );
        Assert.assertEquals(status, actualStatus);
        Assert.assertEquals(message, actualMessage);
        Assert.assertEquals(result, actualResult);
    }

}
