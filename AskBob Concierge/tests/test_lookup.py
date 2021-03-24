import requests

port="8000"

class TestLookupShop:
    def test_get_lookup_shop_phrase_1(self):
        data_package= {"message":"Search Amazon for baked beans",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="LOOKUP"
        assert response_message["Service"]=="Shop Search"
        assert response_message["Response"]=="Searching AMAZON for BAKED BEANS"
        assert response_message["Shop"]=="AMAZON"
        assert response_message["Search Term"]=="BAKED BEANS"

    def test_get_lookup_shop_phrase_2(self):
        data_package= {"message":"Open Ebay and search for baking trays",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="LOOKUP"
        assert response_message["Service"]=="Shop Search"
        assert response_message["Response"]=="Searching EBAY for BAKING TRAYS"
        assert response_message["Shop"]=="EBAY"
        assert response_message["Search Term"]=="BAKING TRAYS"

    def test_get_lookup_shop_phrase_3(self):
        data_package= {"message":"I want to buy a keyboard",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="LOOKUP"
        assert response_message["Service"]=="Shop Search"
        assert response_message["Response"]=="Searching AMAZON for KEYBOARD"
        assert response_message["Shop"]=="AMAZON"
        assert response_message["Search Term"]=="KEYBOARD"

    def test_get_lookup_shop_phrase_4(self):
        data_package= {"message":"Can you search Argos for stationary",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="LOOKUP"
        assert response_message["Service"]=="Shop Search"
        assert response_message["Response"]=="Searching ARGOS for STATIONARY"
        assert response_message["Shop"]=="ARGOS"
        assert response_message["Search Term"]=="STATIONARY"

    def test_get_lookup_shop_phrase_5(self):
        data_package= {"message":"Purchase biscuits",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="LOOKUP"
        assert response_message["Service"]=="Shop Search"
        assert response_message["Response"]=="Searching AMAZON for BISCUITS"
        assert response_message["Shop"]=="AMAZON"
        assert response_message["Search Term"]=="BISCUITS"

    def test_get_lookup_shop_phrase_6(self):
        data_package= {"message":"Can you open Morissons and search for jam",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="LOOKUP"
        assert response_message["Service"]=="Shop Search"
        assert response_message["Response"]=="Searching MORISSONS for JAM"
        assert response_message["Shop"]=="MORISSONS"
        assert response_message["Search Term"]=="JAM"

    def test_get_lookup_shop_phrase_7(self):
        data_package= {"message":"Please open Waitrose and buy apples",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="LOOKUP"
        assert response_message["Service"]=="Shop Search"
        assert response_message["Response"]=="Searching WAITROSE for APPLES"
        assert response_message["Shop"]=="WAITROSE"
        assert response_message["Search Term"]=="APPLES"

    def test_get_lookup_shop_phrase_7(self):
        data_package= {"message":"Please open Waitrose and buy apples",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="LOOKUP"
        assert response_message["Service"]=="Shop Search"
        assert response_message["Response"]=="Searching WAITROSE for APPLES"
        assert response_message["Shop"]=="WAITROSE"
        assert response_message["Search Term"]=="APPLES"
        
class TestLookupYell:
    def test_get_lookup_yell_phrase_1(self):
        data_package= {"message":"Get me a plumber from yell",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="LOOKUP"
        assert response_message["Service"]=="Yell Search"
        assert response_message["Response"]=="Searching YELL for PLUMBER"
        assert response_message["Search Term"]=="PLUMBER"

    def test_get_lookup_yell_phrase_2(self):
        data_package= {"message":"Can you find me a window cleaner",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="LOOKUP"
        assert response_message["Service"]=="Yell Search"
        assert response_message["Response"]=="Searching YELL for WINDOW CLEANER"
        assert response_message["Search Term"]=="WINDOW CLEANER"

    def test_get_lookup_yell_phrase_3(self):
        data_package= {"message":"I would like to find a nearby florist",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="LOOKUP"
        assert response_message["Service"]=="Yell Search"
        assert response_message["Response"]=="Searching YELL for FLORIST"
        assert response_message["Search Term"]=="FLORIST"

    def test_get_lookup_yell_phrase_4(self):
        data_package= {"message":"Please find me a solicitor",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="LOOKUP"
        assert response_message["Service"]=="Yell Search"
        assert response_message["Response"]=="Searching YELL for SOLICITOR"
        assert response_message["Search Term"]=="SOLICITOR"

    def test_get_lookup_yell_phrase_5(self):
        data_package= {"message":"Please could you find me a nearby vet",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="LOOKUP"
        assert response_message["Service"]=="Yell Search"
        assert response_message["Response"]=="Searching YELL for VET"
        assert response_message["Search Term"]=="VET"

    def test_get_lookup_yell_phrase_6(self):
        data_package= {"message":"Can you get me an electrician",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="LOOKUP"
        assert response_message["Service"]=="Yell Search"
        assert response_message["Response"]=="Searching YELL for ELECTRICIAN"
        assert response_message["Search Term"]=="ELECTRICIAN"

    def test_get_lookup_yell_phrase_7(self):
        data_package= {"message":"Please could you find me an roofing service",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="LOOKUP"
        assert response_message["Service"]=="Yell Search"
        assert response_message["Response"]=="Searching YELL for ROOFING SERVICE"
        assert response_message["Search Term"]=="ROOFING SERVICE"