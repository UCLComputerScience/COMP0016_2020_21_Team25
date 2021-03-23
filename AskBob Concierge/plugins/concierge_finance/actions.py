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
        if not stock:
            data_package={
            "Service_Type": "API_CALL",
            "Service": "Stocks",
            "Response": "Sorry, I don't know that organisation"
            }
        else:
            r = requests.get(url="http://serviceapis:8080/stocks", params={
                "SYMBOL": stock,
            }).json()
            data_package={
                "Service_Type": "API_CALL",
                "Service": "Stocks",
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


        r = requests.get(url="http://serviceapis:8080/charity-search", params={
            "QUERY": search_term,
            
        }).json()
        
        data_package={
            "Service_Type": "API_CALL",
            "Service": "Charity",
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

        if not city:
            data_package={
            "Service_Type": "API_CALL",
            "Service": "Charity",
            "Response": "Sorry, I don't know that location"
            }

        else:
            r = requests.get(url="http://serviceapis:8080/charity-by-city", params={
                "CITY": city,
                
            }).json()
            
            data_package={
                "Service_Type": "API_CALL",
                "Service": "Charity",
                "Response": r["message"]
            }

        dispatcher.utter_message(json_message= data_package)


        return []

