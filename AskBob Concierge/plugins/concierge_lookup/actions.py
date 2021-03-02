from typing import Any, Text, Dict, List
from rasa_sdk import Action, Tracker
from rasa_sdk.executor import CollectingDispatcher

import askbob.plugin
import requests


@askbob.plugin.action("concierge_lookup", "place_request_shop_search")
class ActionConciergePlaceCall(Action):

    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:
        status="ok"

        search_term = next(tracker.get_latest_entity_values("shopping_search_term"), None)
        shop= next(tracker.get_latest_entity_values("shop_term"), None)     

        if not search_term:
            search_term="Amazon"
        elif not shop:
            status='error'
        
        data_package={
            "Service_Type": "SHOP_SEARCH",
            "Status": status,
            "Service": shop.upper(),
            "Application": search_term.lower()
        }

        dispatcher.utter_message(json_message= data_package)


        return []


@askbob.plugin.action("concierge_lookup", "place_request_yell_search")
class ActionConciergePlaceCall(Action):

    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:

        status="ok"
        search_term = next(tracker.get_latest_entity_values("yell_search_term"), None)
        
        if not search_term:
            status='error'
       
        data_package={
            "Service_Type": "YELL_SEARCH",
            "Status": status,
            "Application": search_term.lower()
        }

        dispatcher.utter_message(json_message= data_package)


        return []
