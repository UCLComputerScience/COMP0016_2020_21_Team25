import requests
import pytest
port="8000"

class TestWeather:
    def test_get_weather_default_phrase_1(self):
        data_package= {"message":"Tell me what the weather is like",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        weather_response= (requests.get("http://localhost:8080/current-weather").json())

        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Weather"
        assert response_message["Response"]==weather_response["message"]


    def test_get_weather_default_phrase_2(self):
        data_package= {"message":"Give me the weather",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        weather_response= (requests.get("http://localhost:8080/current-weather").json())

        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Weather"
        assert response_message["Response"]==weather_response["message"]



    def test_get_weather_default_phrase_3(self):
        data_package= {"message":"How cold is it outside",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        weather_response= (requests.get("http://localhost:8080/current-weather").json())

        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Weather"
        assert response_message["Response"]==weather_response["message"]


    def test_get_weather_with_location_phrase_1(self):
        data_package= {"message":"Tell me what the weather is like in Camden",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        weather_response= (requests.get("http://localhost:8080/current-weather?CITY_NAME=Camden").json())
        
        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Weather"
        assert response_message["Response"]==weather_response["message"]

    def test_get_weather_with_location_phrase_2(self):
        data_package= {"message":"What is the temperature in Liverpool",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        weather_response= (requests.get("http://localhost:8080/current-weather?CITY_NAME=Liverpool").json())
        
        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Weather"
        assert response_message["Response"]==weather_response["message"]

    def test_get_weather_with_location_phrase_3(self):
        data_package= {"message":"How hot is it in Rome",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        weather_response= (requests.get("http://localhost:8080/current-weather?CITY_NAME=Rome").json())
        
        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Weather"
        assert response_message["Response"]==weather_response["message"]




class TestAirQuality:
    def test_get_air_quality_default_phrase_1(self):
        data_package= {"message":"What is the air quality like",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        air_quality_response= (requests.get("http://localhost:8080/air-quality").json())

        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Air Quality"
        assert response_message["Response"]==air_quality_response["message"]


    def test_get_air_quality_default_phrase_2(self):
        data_package= {"message":"Give me the air quality",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        air_quality_response= (requests.get("http://localhost:8080/air-quality").json())

        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Air Quality"
        assert response_message["Response"]==air_quality_response["message"]



    def test_get_air_quality_default_phrase_3(self):
        data_package= {"message":"What is the current air quality",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        air_quality_response= (requests.get("http://localhost:8080/air-quality").json())

        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Air Quality"
        assert response_message["Response"]==air_quality_response["message"]


    def test_get_air_quality_with_location_phrase_1(self):
        data_package= {"message":"Tell me what the air quality is like in New York",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        air_quality_response= (requests.get("http://localhost:8080/air-quality?CITY_NAME=New York").json())
        
        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Air Quality"
        assert response_message["Response"]==air_quality_response["message"]

    def test_get_air_quality_with_location_phrase_2(self):
        data_package= {"message":"Give me the air quality in Manchester",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        air_quality_response= (requests.get("http://localhost:8080/air-quality?CITY_NAME=Manchester").json())
        
        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Air Quality"
        assert response_message["Response"]==air_quality_response["message"]

    def test_get_air_quality_with_location_phrase_3(self):
        data_package= {"message":"Could you tell me what the air quality is in Detroit",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        air_quality_response= (requests.get("http://localhost:8080/air-quality?CITY_NAME=Detroit").json())
        
        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Air Quality"
        assert response_message["Response"]==air_quality_response["message"]


class TestDictionary:
    def test_get_dictionary_phrase_1(self):
        data_package= {"message":"What is the definition of the word topple",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        air_quality_response= (requests.get("http://localhost:8080/dictionary/?WORD=topple&INCLUDE_SYNONYMS=false").json())

        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Dictionary & Thesaurus"
        assert response_message["Response"]==air_quality_response["message"]


    def test_get_dictionary_phrase_2(self):
        data_package= {"message":"What does friendship mean",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        dictionary_response= (requests.get("http://localhost:8080/dictionary?WORD=friendship&INCLUDE_SYNONYMS=false").json())

        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Dictionary & Thesaurus"
        assert response_message["Response"]==dictionary_response["message"]


    def test_get_dictionary_phrase_3(self):
        data_package= {"message":"Give me the definition of the word struggle ",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        dictionary_response= (requests.get("http://localhost:8080/dictionary?WORD=struggle&INCLUDE_SYNONYMS=false").json())

        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Dictionary & Thesaurus"
        assert response_message["Response"]==dictionary_response["message"]

    def test_get_dictionary_failure_phrase_1(self):
        data_package= {"message":"Could you tell me the meaning of the word sanshete ",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        dictionary_response= (requests.get("http://localhost:8080/dictionary?WORD=sanshete&INCLUDE_SYNONYMS=false").json())

        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Dictionary & Thesaurus"
        assert response_message["Response"]==dictionary_response["message"]

    def test_get_dictionary_failure_phrase_2(self):
        data_package= {"message":"Tell me the meaning of the word haxiskp ",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        dictionary_response= (requests.get("http://localhost:8080/dictionary?WORD=haxiskp&INCLUDE_SYNONYMS=false").json())

        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Dictionary & Thesaurus"
        assert response_message["Response"]==dictionary_response["message"]


class TestThesaurus:
    def test_get_thesaurus_phrase_1(self):
        data_package= {"message":"What are some synonyms of the word division",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        dictionary_response= (requests.get("http://localhost:8080/dictionary?WORD=division&SYNONYMS_ONLY=true").json())
        
        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Dictionary & Thesaurus"
        assert response_message["Response"]==dictionary_response["message"]

    def test_get_thesaurus_phrase_2(self):
        data_package= {"message":"Tell me synonyms of the word sunshine",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        dictionary_response= (requests.get("http://localhost:8080/dictionary?WORD=sunshine&SYNONYMS_ONLY=true").json())
        
        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Dictionary & Thesaurus"
        assert response_message["Response"]==dictionary_response["message"]

    def test_get_thesaurus_phrase_3(self):
        data_package= {"message":"Give me synonyms of the word evaluate",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        dictionary_response= (requests.get("http://localhost:8080/dictionary?WORD=evaluate&SYNONYMS_ONLY=true").json())
        
        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Dictionary & Thesaurus"
        assert response_message["Response"]==dictionary_response["message"]

    def test_get_thesaurus_failure_phrase_1(self):
        data_package= {"message":"Tell me synonyms of the word fasrnter",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        dictionary_response= (requests.get("http://localhost:8080/dictionary?WORD=fasrnter&SYNONYMS_ONLY=true").json())
        
        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Dictionary & Thesaurus"
        assert response_message["Response"]==dictionary_response["message"]

    def test_get_thesaurus_failure_phrase_2(self):
        data_package= {"message":"Could you tell me synonyms of ahsbdaf",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        dictionary_response= (requests.get("http://localhost:8080/dictionary?WORD=ahsbdaf&SYNONYMS_ONLY=true").json())
        
        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Dictionary & Thesaurus"
        assert response_message["Response"]==dictionary_response["message"]