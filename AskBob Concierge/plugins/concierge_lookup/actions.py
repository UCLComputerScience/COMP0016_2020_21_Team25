from typing import Any, Text, Dict, List
from rasa_sdk import Action, Tracker
from rasa_sdk.executor import CollectingDispatcher

import askbob.plugin
import requests


@askbob.plugin.action("concierge_lookup", "place_request_amazon_search")
class ActionConciergePlaceCall(Action):

    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:

        search_term = next(tracker.get_latest_entity_values("amazon_search_term"), None)
        response=search_term
        if not search_term:
            response="Sorry, I can't open this app"
       
        data_package={
            "Service_Type": "AMAZON_SEARCH",
            "Application": response.lower()
        }

        dispatcher.utter_message(json_message= data_package)


        return []


@askbob.plugin.action("concierge_lookup", "place_request_yell_search")
class ActionConciergePlaceCall(Action):

    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:

        search_term = next(tracker.get_latest_entity_values("yell_search_term"), None)
        response=search_term
        if not search_term:
            response="Sorry, I can't open this app"
       
        data_package={
            "Service_Type": "YELL_SEARCH",
            "Application": response.lower()
        }

        dispatcher.utter_message(json_message= data_package)


        return []
