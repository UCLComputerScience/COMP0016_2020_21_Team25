from typing import Any, Text, Dict, List
from rasa_sdk import Action, Tracker
from rasa_sdk.executor import CollectingDispatcher

import askbob.plugin
import requests

@askbob.plugin.action("concierge_entertainment", "fetch_joke")
class ActionConciergeFetchJoke(Action):

    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:

        joke_term = next(tracker.get_latest_entity_values("joke_category"), None)
        print(joke_term)
        r = requests.get(url="http://localhost:8080/joke", params={
            "TERM":joke_term}).json()
        
        data_package={
            "Service_Type": "API_CALL",
            "Service": "joke",
            "Response": r["message"]
        }

        dispatcher.utter_message(json_message= data_package)

        return []


@askbob.plugin.action("concierge_entertainment", "fetch_news")
class ActionConciergeFetchNews(Action):

    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:

        search_term = next(tracker.get_latest_entity_values("news_search_term"), None)
        print(search_term)
        r = requests.get(url="http://localhost:8080/news", params={
            "QUERY": search_term,
            "LANGUAGE": "en"
        }).json()
        
        data_package={
            "Service_Type": "API_CALL",
            "Service": "news",
            "Response": r["message"]
        }

        dispatcher.utter_message(json_message= data_package)

        return []

@askbob.plugin.action("concierge_entertainment", "fetch_book")
class ActionConciergeFetchBook(Action):

    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:

        search_term = next(tracker.get_latest_entity_values("WORK_OF_ART"), None)
        print(search_term)
        r = requests.get(url="http://localhost:8080/book", params={
            "QUERY": search_term,
            "LANGUAGES": "en"
        }).json()
        
        data_package={
            "Service_Type": "API_CALL",
            "Service": "book",
            "Response": r["message"]
        }

        dispatcher.utter_message(json_message= data_package)

        return []

@askbob.plugin.action("concierge_entertainment", "fetch_book_category")
class ActionConciergeFetchBookCategory(Action):

    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:

        category = next(tracker.get_latest_entity_values("book_category"), None)
        print(category)
        r = requests.get(url="http://localhost:8080/book", params={
            "TOPIC": category,
            "LANGUAGES": "en"
        }).json()
        
        data_package={
            "Service_Type": "API_CALL",
            "Service": "book",
            "Response": r["message"]
        }

        dispatcher.utter_message(json_message= data_package)

        return []
