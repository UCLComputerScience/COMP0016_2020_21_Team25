from typing import Any, Text, Dict, List
from rasa_sdk import Action, Tracker
from rasa_sdk.executor import CollectingDispatcher

import askbob.plugin
import requests


@askbob.plugin.action("concierge_finance", "fetch_stocks")
class ActionConciergeFetchStocks(Action):

    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:

        
        stock= next(tracker.get_latest_entity_values("ORG"), None)
        print(stock)
        r = requests.get(url="http://localhost:8080/stocks", params={
            "SYMBOL": stock,
        }).json()
        

        data_package={
            "Service_Type": "API_CALL",
            "Service": "stocks",
            "Response": r["message"]
        }

        dispatcher.utter_message(json_message= data_package)


        return []


@askbob.plugin.action("concierge_finance", "fetch_charity")
class ActionConciergeFetchCharity(Action):

    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:

        search_term = next(tracker.get_latest_entity_values("charity_search_term"), None)


        r = requests.get(url="http://localhost:8080/charity-search", params={
            "QUERY": search_term,
            
        }).json()
        
        data_package={
            "Service_Type": "API_CALL",
            "Service": "charity-search",
            "Response": r["message"]
        }

        dispatcher.utter_message(json_message= data_package)


        return []

@askbob.plugin.action("concierge_finance", "fetch_charity_city")
class ActionConciergeFetchCharityCity(Action):

    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:

        city = next(tracker.get_latest_entity_values("GPE"), None)


        r = requests.get(url="http://localhost:8080/charity-by-city", params={
            "CITY": city,
            
        }).json()
        
        data_package={
            "Service_Type": "API_CALL",
            "Service": "charity-by-city",
            "Response": r["message"]
        }

        dispatcher.utter_message(json_message= data_package)


        return []

