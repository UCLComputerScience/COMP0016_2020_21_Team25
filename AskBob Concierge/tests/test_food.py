import requests

port="8000"

class TestRecipeSearch:
    def test_get_recipe_by_search_phrase_1(self):
        data_package= {"message":"Tell me recipe for Tacos",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]
        
        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Recipes"

    def test_get_recipe_by_search_phrase_2(self):
        data_package= {"message":"Give me recipes for Carrot Cake",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        
        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Recipes"


    def test_get_recipe_by_search_phrase_3(self):
        data_package= {"message":"I would like recipes for ravioli",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]
        
        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Recipes"

    def test_get_recipe_by_search_failure_phrase_1(self):
        data_package= {"message":"Could you give me recipes for aeakfaenq",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]
        
        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Recipes"
        assert response_message["Response"]=="I'm sorry, I could not find a recipe."

    def test_get_recipe_by_search_failure_phrase_2(self):
        data_package= {"message":"Tell me recipes for",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]
        
        assert response_message["text"] =="I didn't quite catch that! Please could you rephrase?"

class TestRecipeIngredient:
    def test_get_recipe_by_ingredient_phrase_1(self):
        data_package= {"message":"Tell me a recipe using beef",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        
        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Recipes"

    def test_get_recipe_by_ingredient_phrase_1(self):
        data_package= {"message":"Tell me a recipe using beef",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        
        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Recipes"

    def test_get_recipe_by_ingredient_phrase_2(self):
        data_package= {"message":"Could you give me a recipe utilising fish",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        
        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Recipes"

    def test_get_recipe_by_ingredient_phrase_3(self):
        data_package= {"message":"Please give me a recipe which uses noodles",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        
        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Recipes"

    def test_get_recipe_by_ingredient_failure_phrase_1(self):
        data_package= {"message":"Could you give me a recipe using eajrna",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]
        
        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Recipes"
        assert response_message["Response"]=="I'm sorry, I could not find a recipe with those ingredients."

    def test_get_recipe_by_ingredient_failure_phrase_2(self):
        data_package= {"message":"I want a recipe using",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]
        
        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Recipes"
        assert response_message["Response"]=="I'm sorry, I could not find a recipe with those ingredients."

class TestRecipeRandom:
    def test_get_recipe_random_phrase_1(self):
        data_package= {"message":"Tell me a random",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        
        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Recipes"

    def test_get_recipe_random_phrase_2(self):
        data_package= {"message":"Give me an interesting recipe",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        
        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Recipes"

    def test_get_recipe_random_phrase_3(self):
        data_package= {"message":"Provide me with an exciting recipe",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        
        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Recipes"

class TestRecipeRandom:
    def test_get_reading_recipe_phrase_1(self):
        data_package= {"message":"Tell me a recipe for ramen",
                    "sender":"concierge"}
        response_search=requests.post("http://localhost:"+port+"/query",data_package)
        data_package= {"message":"Read out the last recipe",
                    "sender":"concierge"}
        response_read=requests.post("http://localhost:"+port+"/query",data_package)
        response_read_message=response_read.json()["messages"][0]["custom"]

        
        assert response_read_message["Service_Type"] =="API_CALL"
        assert response_read_message["Service"]=="Recipes"
        assert response_read_message["Response"]!="I do not know which recipe you are referring to"
        assert len(response_read_message["Steps"])!=0

    def test_get_reading_recipe_phrase_2(self):
        data_package= {"message":"Please tell me a recipe",
                    "sender":"concierge"}
        response_search=requests.post("http://localhost:"+port+"/query",data_package)
        data_package= {"message":"Read out the instructions for that recipe",
                    "sender":"concierge"}
        response_read=requests.post("http://localhost:"+port+"/query",data_package)
        response_read_message=response_read.json()["messages"][0]["custom"]

        
        assert response_read_message["Service_Type"] =="API_CALL"
        assert response_read_message["Service"]=="Recipes"
        assert response_read_message["Response"]!="I do not know which recipe you are referring to"
        assert len(response_read_message["Steps"])!=0

    def test_get_reading_recipe_failure_phrase_1(self):
        data_package= {"message":"Could you read out the last recipe ",
                    "sender":"concierge"}
        response_read=requests.post("http://localhost:"+port+"/query",data_package)

        response_read_message=response_read.json()["messages"][0]["custom"]

        assert response_read_message["Service_Type"] =="API_CALL"
        assert response_read_message["Service"]=="Recipes"
        assert response_read_message["Response"]=="I do not know which recipe you are referring to"
        assert len(response_read_message["Steps"])==0

    def test_get_reading_books_failure_phrase_2(self):
        data_package= {"message":"Please tell me a recipe for asjdnaskjfn",
                    "sender":"concierge"}
        response_search=requests.post("http://localhost:"+port+"/query",data_package)
        data_package= {"message":"Could you read out the recipe",
                    "sender":"concierge"}
        response_read=requests.post("http://localhost:"+port+"/query",data_package)
        response_read_message=response_read.json()["messages"][0]["custom"]
        
        assert response_read_message["Service_Type"] =="API_CALL"
        assert response_read_message["Service"]=="Recipes"
        assert response_read_message["Response"]=="I do not know which recipe you are referring to"
        assert len(response_read_message["Steps"])==0
