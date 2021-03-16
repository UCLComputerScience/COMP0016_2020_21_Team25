import requests

port="8000"

class TestStocks:
    def test_get_stocks_phrase_1(self):
        data_package= {"message":"What is the stock value of IBM",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        stock_response= (requests.get("http://localhost:8080/stocks?SYMBOL=IBM").json())


        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Stocks"
        assert response_message["Response"]==stock_response["message"]

    def test_get_stocks_phrase_2(self):
        data_package= {"message":"Tell me the current stock value of AAPL",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        stock_response= (requests.get("http://localhost:8080/stocks?SYMBOL=AAPL").json())


        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Stocks"
        assert response_message["Response"]==stock_response["message"]

    def test_get_stocks_phrase_3(self):
        data_package= {"message":"Could you tell me the stock value of NFLX",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        stock_response= (requests.get("http://localhost:8080/stocks?SYMBOL=NFLX").json())


        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Stocks"
        assert response_message["Response"]==stock_response["message"]

    def test_get_stocks_phrase_4(self):
        data_package= {"message":"Could you give me the stock value of GME",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        stock_response= (requests.get("http://localhost:8080/stocks?SYMBOL=GME").json())


        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Stocks"
        assert response_message["Response"]==stock_response["message"]

    def test_get_stocks_failure_phrase_1(self):
        data_package= {"message":"What is the stock value",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]


        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Stocks"
        assert response_message["Response"]=="Sorry, I don't know that organisation"

    def test_get_stocks_failure_phrase_2(self):
        data_package= {"message":"Give me the stock value of",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]


        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Stocks"
        assert response_message["Response"]=="Sorry, I don't know that organisation"

    def test_get_stocks_failure_phrase_3(self):
        data_package= {"message":"Could you tell me the stock value of Tesla",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        stock_response= (requests.get("http://localhost:8080/stocks?SYMBOL=Tesla").json())

        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Stocks"
        assert response_message["Response"]==stock_response["message"]

#--------------------------CHARITIES----------------------------------------------------

class TestCharitySearch:
    def test_get_charity_by_search_phrase_1(self):
        data_package= {"message":"Tell me some information about the charity Oxfam",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        charity_response= (requests.get("http://localhost:8080/charity-search?QUERY=Oxfam").json())


        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Charity"
        assert response_message["Response"]==charity_response["message"]

    def test_get_charity_by_search_phrase_2(self):
        data_package= {"message":"Give me a summary about the charity Woodland Trust",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        charity_response= (requests.get("http://localhost:8080/charity-search?QUERY=Woodland Trust").json())


        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Charity"
        assert response_message["Response"]==charity_response["message"]

    def test_get_charity_by_search_phrase_3(self):
        data_package= {"message":"What does the charity WWF do",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        charity_response= (requests.get("http://localhost:8080/charity-search?QUERY=WWF").json())


        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Charity"
        assert response_message["Response"]==charity_response["message"]

    def test_get_charity_by_search_phrase_3(self):
        data_package= {"message":"Give me some information about the charity Samaritans",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        charity_response= (requests.get("http://localhost:8080/charity-search?QUERY=Samaritans").json())


        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Charity"
        assert response_message["Response"]==charity_response["message"]

    def test_get_charity_by_search_phrase_4(self):
        data_package= {"message":"Give me a quick summary about the charity Comic Relief",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        charity_response= (requests.get("http://localhost:8080/charity-search?QUERY=Comic Relief").json())


        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Charity"
        assert response_message["Response"]==charity_response["message"]

    def test_get_charity_by_search_phrase_5(self):
        data_package= {"message":"What does the charity Marie Curie do",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        charity_response= (requests.get("http://localhost:8080/charity-search?QUERY=Marie Curie").json())


        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Charity"
        assert response_message["Response"]==charity_response["message"]

    def test_get_charity_by_search_phrase_6(self):
        data_package= {"message":"Tell me a short summary about the charity Dogs Trust",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        charity_response= (requests.get("http://localhost:8080/charity-search?QUERY=Dogs Trust").json())


        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Charity"
        assert response_message["Response"]==charity_response["message"]

class TestCharityLocation:
    def test_get_charity_by_location_phrase_1(self):
        data_package= {"message":"Give me the name of charity based in London",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        charity_response= (requests.get("http://localhost:8080/charity-by-city?CITY=London").json())


        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Charity"
        assert response_message["Response"]==charity_response["message"]

    def test_get_charity_by_location_phrase_2(self):
        data_package= {"message":"Tell me the name of a charity based in Paris",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        charity_response= (requests.get("http://localhost:8080/charity-by-city?CITY=Paris").json())


        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Charity"
        assert response_message["Response"]==charity_response["message"]

    def test_get_charity_by_location_phrase_3(self):
        data_package= {"message":"Are there any charities based in Berlin",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        charity_response= (requests.get("http://localhost:8080/charity-by-city?CITY=Berlin").json())


        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Charity"
        assert response_message["Response"]==charity_response["message"]

    def test_get_charity_by_location_phrase_4(self):
        data_package= {"message":"What is the name of a charity which is based in the city of Dublin",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        charity_response= (requests.get("http://localhost:8080/charity-by-city?CITY=Dublin").json())


        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Charity"
        assert response_message["Response"]==charity_response["message"]

    def test_get_charity_by_location_failure_phrase_1(self):
        data_package= {"message":"Tell me the name of a charity based in",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Charity"
        assert response_message["Response"]=="Sorry, I don't know that location"

    def test_get_charity_by_location_failure_phrase_1(self):
        data_package= {"message":"Give me the name of a charity based in rgijnakfna",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Charity"
        assert response_message["Response"]=="Sorry, I don't know that location"

