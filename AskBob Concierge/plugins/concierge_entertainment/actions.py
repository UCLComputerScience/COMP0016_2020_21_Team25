from typing import Any, Text, Dict, List
from rasa_sdk import Action, Tracker
from rasa_sdk.events import SlotSet
from rasa_sdk.executor import CollectingDispatcher

import askbob.plugin
import requests

host="serviceapis"
port="8080"

@askbob.plugin.action("concierge_entertainment", "fetch_joke")
class ActionConciergeFetchJoke(Action):

    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:

        joke_term = next(tracker.get_latest_entity_values("joke_category"), None)
        print(joke_term)
        r = requests.get(url="http://"+host+":"+port+"/joke", params={
            "TERM":joke_term}).json()
        
        data_package={
            "Service_Type": "API_CALL",
            "Service": "Jokes",
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
        r = requests.get(url="http://"+host+":"+port+"/news", params={
            "QUERY": search_term,
            "LANGUAGE": "en"
        }).json()
        
        data_package={
            "Service_Type": "API_CALL",
            "Service": "News",
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
        if not search_term:
            data_package={
            "Service_Type": "API_CALL",
            "Service": "Books",
            "Response": "Sorry, I don't know that book"
            }
            dispatcher.utter_message(json_message= data_package)
            return[]
        else:
            r = requests.get(url="http://"+host+":"+port+"/book", params={
                "QUERY": search_term,
                "LANGUAGES": "en"
            }).json()
            data_package={
                "Service_Type": "API_CALL",
                "Service": "Books",
                "Response": r["message"]
            }
        dispatcher.utter_message(json_message= data_package)

        if len(r["metadata"])!=0:
            return [SlotSet("book_link", r["metadata"]["text/html; charset=utf-8"])]
        else:
            return[]

@askbob.plugin.action("concierge_entertainment", "fetch_book_category")
class ActionConciergeFetchBookCategory(Action):

    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:

        category = next(tracker.get_latest_entity_values("book_category"), None)


        if not category:
            data_package={
            "Service_Type": "API_CALL",
            "Service": "Books",
            "Response": "Sorry, I don't know that book"
            }
            dispatcher.utter_message(json_message= data_package)

            return[]
        else:
            r = requests.get(url="http://"+host+":"+port+"/book", params={
                "TOPIC": category,
                "LANGUAGES": "en"
            }).json()
            
            data_package={
                "Service_Type": "API_CALL",
                "Service": "Books",
                "Response": r["message"]
            }

            dispatcher.utter_message(json_message= data_package)

        if len(r["metadata"])!=0:
            return [SlotSet("book_link", r["metadata"]["text/html; charset=utf-8"])]
        else:
            return[]

@askbob.plugin.action("concierge_entertainment", "fetch_read_book")
class ActionConciergeFetchBookCategory(Action):

    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:

        book_link=tracker.get_slot("book_link") 
        if not book_link:
            response="Sorry, I do not know which book you are referring to"
        
        else:
            response=book_link
        
        data_package={
                "Service_Type": "API_CALL",
                "Service":"Books",
                "Response": response
            }

        dispatcher.utter_message(json_message= data_package)
        return [SlotSet("book_link", None)]
