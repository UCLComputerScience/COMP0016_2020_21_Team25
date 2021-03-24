import requests

port="8000"
class TestGetApp:
    def test_get_app_phrase_1(self):
        data_package= {"message":"Can you open facebook",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="APP_SERVICE"
        assert response_message["Service"]=="Open App"
        assert response_message["Application"]=="FACEBOOK"

    def test_get_app_phrase_2(self):
        data_package= {"message":"Open Snapchat",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="APP_SERVICE"
        assert response_message["Service"]=="Open App"
        assert response_message["Application"]=="SNAPCHAT"

    def test_get_app_phrase_3(self):
        data_package= {"message":"I would like to open tiktok",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="APP_SERVICE"
        assert response_message["Service"]=="Open App"
        assert response_message["Application"]=="TIKTOK"

    def test_get_app_phrase_4(self):
        data_package= {"message":"Can you please open the settings application",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="APP_SERVICE"
        assert response_message["Service"]=="Open App"
        assert response_message["Application"]=="SETTINGS"

    def test_get_app_phrase_5(self):
        data_package= {"message":"Please open the messages app",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="APP_SERVICE"
        assert response_message["Service"]=="Open App"
        assert response_message["Application"]=="MESSAGES"

    def test_get_app_phrase_6(self):
        data_package= {"message":"Can you please open Candy Crush Saga",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="APP_SERVICE"
        assert response_message["Service"]=="Open App"
        assert response_message["Application"]=="CANDY CRUSH SAGA"

    def test_get_app_phrase_7(self):
        data_package= {"message":"I would like to open the app twitter",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="APP_SERVICE"
        assert response_message["Service"]=="Open App"
        assert response_message["Application"]=="TWITTER"

    def test_get_app_phrase_8(self):
        data_package= {"message":"Please open the app instagram",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="APP_SERVICE"
        assert response_message["Service"]=="Open App"
        assert response_message["Application"]=="INSTAGRAM"