import requests

port="8000"
class TestJokes:
    def test_get_joke_default_phrase_1(self):
        data_package= {"message":"Tell me a joke",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Jokes"

    def test_get_joke_default_phrase_2(self):
        data_package= {"message":"Tell me a pun",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Jokes"

    def test_get_joke_default_phrase_3(self):
        data_package= {"message":"Give me a joke",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Jokes"

    def test_get_joke_category_phrase_1(self):
        data_package= {"message":"Tell me a knock knock joke",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Jokes"

    def test_get_joke_category_phrase_2(self):
        data_package= {"message":"Give me a food joke",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Jokes"

    def test_get_joke_category_phrase_3(self):
        data_package= {"message":"Could you tell me a computer joke",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Jokes"

#------------------------------NEWS--------------------------------------
class TestNews:
    def test_get_news_phrase_1(self):
        data_package= {"message":"Tell me recent news about Boris Johnson",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        news_response= (requests.get("http://localhost:8080/news?QUERY=Boris Johnson").json())
        
        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="News"
        assert response_message["Response"]==news_response["message"]

    def test_get_news_phrase_2(self):
        data_package= {"message":"Give me news about Pandas",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        news_response= (requests.get("http://localhost:8080/news?QUERY=Pandas").json())
        
        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="News"
        assert response_message["Response"]==news_response["message"]

    def test_get_news_phrase_3(self):
        data_package= {"message":"Could you give me recent news about Amazon",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        news_response= (requests.get("http://localhost:8080/news?QUERY=Amazon").json())
        
        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="News"
        assert response_message["Response"]==news_response["message"]

    def test_get_news_phrase_4(self):
        data_package= {"message":"Please tell me news about American Politics",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        news_response= (requests.get("http://localhost:8080/news?QUERY=American Politics").json())
        
        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="News"
        assert response_message["Response"]==news_response["message"]

    def test_get_news_failure_phrase_1(self):
        data_package= {"message":"Tell me news about asdajemd",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        news_response= (requests.get("http://localhost:8080/news?QUERY=asdajemd").json())
        
        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="News"
        assert response_message["Response"]==news_response["message"]

    def test_get_news_failure_phrase_2(self):
        data_package= {"message":"Give me some recent news about akdokamq",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        news_response= (requests.get("http://localhost:8080/news?QUERY=akdokamq").json())
        
        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="News"
        assert response_message["Response"]==news_response["message"]

#-------------------------------Books--------------------------------------------
class TestBookSearch:
    def test_get_books_by_search_phrase_1(self):
        data_package= {"message":"Tell me about the book Frankenstein",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        book_response= (requests.get("http://localhost:8080/book?QUERY=Frankenstein").json())
        
        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Books"
        assert response_message["Response"]==book_response["message"]

    def test_get_books_by_search_phrase_2(self):
        data_package= {"message":"Read me the book Dracula",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        book_response= (requests.get("http://localhost:8080/book?QUERY=Dracula").json())
        
        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Books"
        assert response_message["Response"]==book_response["message"]

    def test_get_books_by_search_phrase_3(self):
        data_package= {"message":"Could you read A Christmas Carol",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        book_response= (requests.get("http://localhost:8080/book?QUERY=A Christmas Carol").json())
        
        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Books"
        assert response_message["Response"]==book_response["message"]

    def test_get_books_by_search_phrase_4(self):
        data_package= {"message":"Who wrote Romeo and Juliet",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        book_response= (requests.get("http://localhost:8080/book?QUERY=Romeo and Juliet").json())
        
        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Books"
        assert response_message["Response"]==book_response["message"]

    def test_get_books_by_search_failure_phrase_1(self):
        data_package= {"message":"Could you read me adjaenfjna",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]
        
        assert response_message["text"]=="I didn't quite catch that! Please could you rephrase?"

    def test_get_books_by_search_failure_phrase_2(self):
        data_package= {"message":"Who wrote the book Harry Potter",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]
        
        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Books"
        assert response_message["Response"]=="Sorry, I don't know that book"

class TestBookCategory:
    def test_get_books_by_category_phrase_1(self):
        data_package= {"message":"Tell me about a horror story",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        book_response= (requests.get("http://localhost:8080/book?TOPIC=horror").json())
        
        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Books"
        assert response_message["Response"]==book_response["message"]

    def test_get_books_by_category_phrase_2(self):
        data_package= {"message":"Read me a mystery novel",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        book_response= (requests.get("http://localhost:8080/book?TOPIC=mystery").json())
        
        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Books"
        assert response_message["Response"]==book_response["message"]

    def test_get_books_by_category_phrase_3(self):
        data_package= {"message":"What is a good classic book",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        book_response= (requests.get("http://localhost:8080/book?TOPIC=classic").json())
        
        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Books"
        assert response_message["Response"]==book_response["message"]

    def test_get_books_by_category_phrase_4(self):
        data_package= {"message":"Give me a a fantasy book",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]

        book_response= (requests.get("http://localhost:8080/book?TOPIC=fantasy").json())
        
        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Books"
        assert response_message["Response"]==book_response["message"]

    def test_get_books_by_category_failure_phrase_1(self):
        data_package= {"message":"Please read me a ajdiasf book",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]
        
        assert response_message["text"]=="I didn't quite catch that! Please could you rephrase?"

    def test_get_books_by_category_failure_phrase_2(self):
        data_package= {"message":"What is a good scifi book",
                    "sender":"concierge"}
        response=requests.post("http://localhost:"+port+"/query",data_package)
        response_message=response.json()["messages"][0]["custom"]
        
        assert response_message["Service_Type"] =="API_CALL"
        assert response_message["Service"]=="Books"
        assert response_message["Response"]=="I'm sorry, I could not find any books matching the search criteria."

class TestBookRead:
    def test_get_reading_books_by_search_phrase_1(self):
        data_package= {"message":"Tell me about the book The Odyssey",
                    "sender":"concierge"}
        response_search=requests.post("http://localhost:"+port+"/query",data_package)
        data_package= {"message":"Could you read that book out",
                    "sender":"concierge"}
        response_read=requests.post("http://localhost:"+port+"/query",data_package)
        response_read_message=response_read.json()["messages"][0]["custom"]

        book_response= (requests.get("http://localhost:8080/book?QUERY=The Odyssey").json())
        
        assert response_read_message["Service_Type"] =="API_CALL"
        assert response_read_message["Service"]=="Books"
        assert response_read_message["Response"]==book_response["metadata"]["text/html; charset=utf-8"]

    def test_get_reading_books_by_search_phrase_2(self):
        data_package= {"message":"Read me the book Moby Dick",
                    "sender":"concierge"}
        response_search=requests.post("http://localhost:"+port+"/query",data_package)
        data_package= {"message":"Open the book",
                    "sender":"concierge"}
        response_read=requests.post("http://localhost:"+port+"/query",data_package)
        response_read_message=response_read.json()["messages"][0]["custom"]

        book_response= (requests.get("http://localhost:8080/book?QUERY=Moby Dick").json())
        
        assert response_read_message["Service_Type"] =="API_CALL"
        assert response_read_message["Service"]=="Books"
        assert response_read_message["Response"]==book_response["metadata"]["text/html; charset=utf-8"]

    def test_get_reading_books_by_category_phrase_1(self):
        data_package= {"message":"Could you read me a science fiction book",
                    "sender":"concierge"}
        response_search=requests.post("http://localhost:"+port+"/query",data_package)
        data_package= {"message":"Give me the book",
                    "sender":"concierge"}
        response_read=requests.post("http://localhost:"+port+"/query",data_package)
        response_read_message=response_read.json()["messages"][0]["custom"]

        book_response= (requests.get("http://localhost:8080/book?TOPIC=science fiction").json())
        
        assert response_read_message["Service_Type"] =="API_CALL"
        assert response_read_message["Service"]=="Books"
        assert response_read_message["Response"]==book_response["metadata"]["text/html; charset=utf-8"]

    def test_get_reading_books_by_category_phrase_2(self):
        data_package= {"message":"Please read a fantasy book",
                    "sender":"concierge"}
        response_search=requests.post("http://localhost:"+port+"/query",data_package)
        data_package= {"message":"I would like to read the book",
                    "sender":"concierge"}
        response_read=requests.post("http://localhost:"+port+"/query",data_package)
        response_read_message=response_read.json()["messages"][0]["custom"]

        book_response= (requests.get("http://localhost:8080/book?TOPIC=fantasy").json())
        
        assert response_read_message["Service_Type"] =="API_CALL"
        assert response_read_message["Service"]=="Books"
        assert response_read_message["Response"]==book_response["metadata"]["text/html; charset=utf-8"]

    def test_get_reading_books_failure_phrase_1(self):
        data_package= {"message":"Please read that book",
                    "sender":"concierge"}
        response_read=requests.post("http://localhost:"+port+"/query",data_package)

        response_read_message=response_read.json()["messages"][0]["custom"]

        assert response_read_message["Service_Type"] =="API_CALL"
        assert response_read_message["Service"]=="Books"
        assert response_read_message["Response"]=="Sorry, I do not know which book you are referring to"

    def test_get_reading_books_failure_phrase_2(self):
        data_package= {"message":"Please read a asjdnaskjfn book",
                    "sender":"concierge"}
        response_search=requests.post("http://localhost:"+port+"/query",data_package)
        data_package= {"message":"Could you read the book out",
                    "sender":"concierge"}
        response_read=requests.post("http://localhost:"+port+"/query",data_package)
        response_read_message=response_read.json()["messages"][0]["custom"]

        book_response= (requests.get("http://localhost:8080/book?TOPIC=asjdnaskjfn").json())
        
        assert response_read_message["Service_Type"] =="API_CALL"
        assert response_read_message["Service"]=="Books"
        assert response_read_message["Response"]=="Sorry, I do not know which book you are referring to"

