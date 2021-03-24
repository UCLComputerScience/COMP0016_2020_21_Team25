import requests

port="8000"

class TestCall:
    def test_get_call_phrase_1(self):
        data_package= {"message":"Can you call Dave",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="APP_SERVICE"
        assert response_message["Service"]=="Call Contact"
        assert response_message["Response"]=="Calling Dave"
        assert response_message["Contact"]=="Dave"

    def test_get_call_phrase_2(self):
        data_package= {"message":"Call Ron Swanson",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="APP_SERVICE"
        assert response_message["Service"]=="Call Contact"
        assert response_message["Response"]=="Calling Ron Swanson"
        assert response_message["Contact"]=="Ron Swanson"

    def test_get_call_phrase_3(self):
        data_package= {"message":"I would like to speak to Dwight Schrute",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="APP_SERVICE"
        assert response_message["Service"]=="Call Contact"
        assert response_message["Response"]=="Calling Dwight Schrute"
        assert response_message["Contact"]=="Dwight Schrute"

    def test_get_call_phrase_4(self):
        data_package= {"message":"Can you please call Steven",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="APP_SERVICE"
        assert response_message["Service"]=="Call Contact"
        assert response_message["Response"]=="Calling Steven"
        assert response_message["Contact"]=="Steven"

    def test_get_call_phrase_5(self):
        data_package= {"message":"Call Bob Ross",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="APP_SERVICE"
        assert response_message["Service"]=="Call Contact"
        assert response_message["Response"]=="Calling Bob Ross"
        assert response_message["Contact"]=="Bob Ross"

    def test_get_call_failure_phrase_1(self):
        data_package= {"message":"Call aejkfbnawkjndkawn",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="APP_SERVICE"
        assert response_message["Service"]=="Call Contact"
        assert response_message["Response"]=="Sorry, I don't know who that is, please try again"

    def test_get_call_failure_phrase_2(self):
        data_package= {"message":"I would like to speak to ",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="APP_SERVICE"
        assert response_message["Service"]=="Call Contact"
        assert response_message["Response"]=="Sorry, I don't know who that is, please try again"

    def test_get_call_failure_phrase_3(self):
        data_package= {"message":"Call the GP  ",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="APP_SERVICE"
        assert response_message["Service"]=="Call Contact"
        assert response_message["Response"]=="Sorry, I don't know who that is, please try again"

class TestMessage:
    def test_get_message_phrase_1(self):
        data_package= {"message":"Can you message Michael",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="APP_SERVICE"
        assert response_message["Service"]=="SMS Contact"
        assert response_message["Response"]=="Messaging Michael"
        assert response_message["Contact"]=="Michael"

    def test_get_message_phrase_2(self):
        data_package= {"message":"SMS Jim",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="APP_SERVICE"
        assert response_message["Service"]=="SMS Contact"
        assert response_message["Response"]=="Messaging Jim"
        assert response_message["Contact"]=="Jim"

    def test_get_message_phrase_3(self):
        data_package= {"message":"Could you send Myrtle Davis an SMS",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="APP_SERVICE"
        assert response_message["Service"]=="SMS Contact"
        assert response_message["Response"]=="Messaging Myrtle Davis"
        assert response_message["Contact"]=="Myrtle Davis"

    def test_get_message_phrase_4(self):
        data_package= {"message":"Please send Nathon Davis a message",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="APP_SERVICE"
        assert response_message["Service"]=="SMS Contact"
        assert response_message["Response"]=="Messaging Nathon Davis"
        assert response_message["Contact"]=="Nathon Davis"

    def test_get_message_phrase_5(self):
        data_package= {"message":"Message Samuel",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="APP_SERVICE"
        assert response_message["Service"]=="SMS Contact"
        assert response_message["Response"]=="Messaging Samuel"
        assert response_message["Contact"]=="Samuel"

    def test_get_message_failure_phrase_1(self):
        data_package= {"message":"SMS ajfaejfaei",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="APP_SERVICE"
        assert response_message["Service"]=="SMS Contact"
        assert response_message["Response"]=="Sorry, I don't know who that is, please try again"

    def test_get_message_failure_phrase_2(self):
        data_package= {"message":"Can you message ",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="APP_SERVICE"
        assert response_message["Service"]=="SMS Contact"
        assert response_message["Response"]=="Sorry, I don't know who that is, please try again"

    def test_get_message_failure_phrase_3(self):
        data_package= {"message":"Can you message the dentist",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="APP_SERVICE"
        assert response_message["Service"]=="SMS Contact"
        assert response_message["Response"]=="Sorry, I don't know who that is, please try again"