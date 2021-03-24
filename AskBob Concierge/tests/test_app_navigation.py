import requests

port="8000"
class TestNavigateApp:
    def test_get_app_navigation_phrase_1(self):
        data_package= {"message":"Go to the history page",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="APP_SERVICE"
        assert response_message["Service"]=="Navigate App"
        assert response_message["Page"]=="history"

    def test_get_app_navigation_phrase_2(self):
        data_package= {"message":"Can you go to the instructions page",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="APP_SERVICE"
        assert response_message["Service"]=="Navigate App"
        assert response_message["Page"]=="instructions"

    def test_get_app_navigation_phrase_3(self):
        data_package= {"message":"I would like to go to the timers pages",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="APP_SERVICE"
        assert response_message["Service"]=="Navigate App"
        assert response_message["Page"]=="timers"

    def test_get_app_navigation_phrase_4(self):
        data_package= {"message":"Please go to reminders",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="APP_SERVICE"
        assert response_message["Service"]=="Navigate App"
        assert response_message["Page"]=="reminders"

    def test_get_app_navigation_phrase_5(self):
        data_package= {"message":"Can you go the to the alarms",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="APP_SERVICE"
        assert response_message["Service"]=="Navigate App"
        assert response_message["Page"]=="alarms"

    def test_get_app_navigation_failure_phrase_1(self):
        data_package= {"message":"Can you go the to the nonexistent page",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="APP_SERVICE"
        assert response_message["Service"]=="Navigate App"
        assert response_message["Page"]=="Sorry, I don't know where that page is"

    def test_get_app_navigation_failure_phrase_2(self):
        data_package= {"message":"Can you go the to the page",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="APP_SERVICE"
        assert response_message["Service"]=="Navigate App"
        assert response_message["Page"]=="Sorry, I don't know where that page is"