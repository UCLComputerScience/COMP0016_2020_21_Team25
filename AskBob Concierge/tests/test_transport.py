import requests

port="8000"
class TestNearestTransport:
    def test_get_nearest_transport_phrase_1(self):
        data_package= {"message":"Tell me where I can find the nearest train station",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Transport"
        assert response_message["Response"]=="Finding nearest train station"
        assert response_message["Transport Type"]=="train_station"

    def test_get_nearest_transport_phrase_2(self):
        data_package= {"message":"Where can I find the closest bus stop",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Transport"
        assert response_message["Response"]=="Finding nearest bus stop"
        assert response_message["Transport Type"]=="bus_stop"

    def test_get_nearest_transport_phrase_3(self):
        data_package= {"message":"Could you give me the location of the train station",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Transport"
        assert response_message["Response"]=="Finding nearest train station"
        assert response_message["Transport Type"]=="train_station"

    def test_get_nearest_transport_phrase_4(self):
        data_package= {"message":"Where can I get the bus",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Transport"
        assert response_message["Response"]=="Finding nearest bus stop"
        assert response_message["Transport Type"]=="bus_stop"

    def test_get_nearest_transport_failure_phrase_1(self):
        data_package= {"message":"Where can I find the closest taxi",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Transport"
        assert response_message["Response"]=="Please specify a valid transport type and try again"

    def test_get_nearest_transport_failure_phrase_2(self):
        data_package= {"message":"Where can I get a flight",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Transport"
        assert response_message["Response"]=="Please specify a valid transport type and try again"
class TestTransportSearch:
    def test_get_transport_by_search_phrase_1(self):
        data_package= {"message":"Tell me where I can find Euston train station",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]


        transport_response= (requests.get("http://localhost:8080/transport-search?QUERY=Euston&TRANSPORT=train_station").json())
        
        

        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Transport"
        assert response_message["Response"]=={"Message":transport_response["message"],
                                            "Latitude":transport_response["metadata"]["latitude"],
                                            "Longitude":transport_response["metadata"]["longitude"]}
        
    def test_get_transport_by_search_phrase_2(self):
        data_package= {"message":"Give me the location of Canary Wharf bus station",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]


        transport_response= (requests.get("http://localhost:8080/transport-search?QUERY=Canary Wharf&TRANSPORT=bus_stop").json())
        
        

        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Transport"
        assert response_message["Response"]=={"Message":transport_response["message"],
                                            "Latitude":transport_response["metadata"]["latitude"],
                                            "Longitude":transport_response["metadata"]["longitude"]}

        
    def test_get_transport_by_search_phrase_3(self):
        data_package= {"message":"Please show me where I can find Kensington bus station",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]


        transport_response= (requests.get("http://localhost:8080/transport-search?QUERY=Kensington&TRANSPORT=bus_stop").json())
        
        

        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Transport"
        assert response_message["Response"]=={"Message":transport_response["message"],
                                            "Latitude":transport_response["metadata"]["latitude"],
                                            "Longitude":transport_response["metadata"]["longitude"]}
        
        
    def test_get_transport_by_search_phrase_4(self):
        data_package= {"message":"Could you give me the location of Waterloo train station",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]


        transport_response= (requests.get("http://localhost:8080/transport-search?QUERY=Waterloo&TRANSPORT=train_station").json())
        
        

        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Transport"
        assert response_message["Response"]=={"Message":transport_response["message"],
                                            "Latitude":transport_response["metadata"]["latitude"],
                                            "Longitude":transport_response["metadata"]["longitude"]}
        

    def test_get_transport_by_search_failure_phrase_1(self):
        data_package= {"message":"Where can I find London airport",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Transport"
        assert response_message["Response"]=="Unable to to find that station or mode of transport, please try again"

    def test_get_transport_by_search_failure_phrase_2(self):
        data_package= {"message":"Where can I find jenakfnak train station",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]

        assert response_message["text"]=="I didn't quite catch that! Please could you rephrase?"