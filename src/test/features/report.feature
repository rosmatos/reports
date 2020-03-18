Feature: I, as a idwall report api provider, need provide differents types of reports and informations with different
  font combinations and validations to my clients.

  Scenario: API must return Bad Request error when receives empty data
    Given a POST request to endpoint "/relatorios"
    When empty data are sent
    Then the API returns "Bad Request" error with 400 code and "É necessário enviar ao menos um parâmetro para criação do relatório." message.

  Scenario Outline: API must return INVALID value for result parameter when receives inconsistent data
    Given a POST request to endpoint "/relatorios"
    When the <matriz>, <birthday> , <name> , <cpf> parameters are sent
    Then the API returns <code>, <status>, <message>, <result>
    Examples:
      | matriz                  | birthday      | name                | cpf           | code  | status      | result    | message |
      | "consultaPessoaDefault" | "28/09/1988"  | "Gabriel Oliveira"  | "07614917677" | 200   | "CONCLUIDO" | "INVALID" | "Inválido. [ERROR] Não foi possível validar: Data de nascimento informada está divergente da constante na base de dados da Secretaria da Receita Federal do Brasil." |
      | "consultaPessoaDefault" | "25/05/1987"  | "Gabriel Oliveira"  | "07614917677" | 200   | "CONCLUIDO" | "INVALID" | "Inválido. [INVALID] Nome diferente do cadastrado na Receita Federal." |

  Scenario Outline: API must return VALID value for result parameter and CONCLUIDO value for status parameter when receives consistent data
    Given a POST request to endpoint "/relatorios"
    When the <matriz>, <birthday> , <name> , <cpf> parameters are sent
    Then the API returns <code>, <status>, <message>, <result>
    Examples:
      | matriz                  | birthday      | name                | cpf           | code  | status      | result    | message   |
      | "consultaPessoaDefault" | "25/09/1988"  | "Gabriel Oliveira"  | "07614917677" | 200   | "CONCLUIDO" | "VALID"   | "Válido." |
      | "consultaPessoaDefault" | "25/09/1988"  | "Gabriel Oliveira"  | "07614917677" | 200   | "CONCLUIDO" | "VALID"   | "Válido." |