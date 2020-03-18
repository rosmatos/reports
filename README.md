
# Running tests using docker or docker-compose
This project also can be run using docker or docker-compose, besides the traditional way using maven command.

## Requirements 
1. You will need install and setup docker and/or docker-compose. 
> Please see https://docs.docker.com/get-started/ and https://docs.docker.com/compose/install/ for instructions.
2. An environment variable IDWALL_TOKEN must be exported (MAC or Linux) or created (Windows) with your authentication token value.

For example, in MAC or Linux:
```
export IDWALL_TOKEN="YOUR_TOKEN_HERE"
```

## How to run 
Once you have installed and setup docker and/or docker-compose, access the project directory from a terminal 
and you will can run the tests in two ways: 

1. Using docker-compose: 

First, confirm that your docker-compose.yml file is ok, running the following command:

```
$ docker-compose config
```

This command will show you the docker-compose.yml file content. If you have created the IDWALL_TOKEN environment variable
correctly, you must see your authorization token in terminal.

Now, you can execute your tests:

```
$ docker-compose up
```

2. Or using docker container:

```
$ docker run -it --rm --name reports -v "$PWD":"$PWD" -v "$HOME"/.m2:/root/.m2 -w $PWD maven:3 mvn test  -DidwallToken="${IDWALL_TOKEN}"
```
## Report
To see the test report, you just need open the target/cucumber/cucumber-report.html in a browser.