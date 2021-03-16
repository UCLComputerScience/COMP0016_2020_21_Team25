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
 
 
        search_term = next(tracker.get_latest_entity_values("shopping_search_term"), None)
        shop= next(tracker.get_latest_entity_values("shop_term"), None)     

       
        if not search_term:
            data_package={
            "Service_Type":"LOOKUP",
            "Service":"Shop Search",
            "Response":"Sorry, I was unable to search for this item, please try again" 
            }
        else:
            if not shop:
                shop="AMAZON"

            data_package={
                "Service_Type":"LOOKUP",
                "Service":"Shop Search",
                "Response":"Searching "+shop.upper()+" for "+search_term.upper(),
                "Shop":shop.upper(),
                "Search Term": search_term.upper()
            }

        dispatcher.utter_message(json_message= data_package)


        return []


@askbob.plugin.action("concierge_lookup", "place_request_yell_search")
class ActionConciergePlaceCall(Action):

    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:

        search_term = next(tracker.get_latest_entity_values("yell_search_term"), None)
        
        if not search_term:
            data_package={
            "Service_Type":"LOOKUP",
            "Service":"Yell Search",
            "Response":"Sorry, I was unable to search for this item, please try again" 
            }
        else:

            data_package={
                "Service_Type":"LOOKUP",
                "Service":"Yell Search",
                "Response":"Searching YELL for "+search_term.upper(),
                "Search Term": search_term.upper()
            }

        dispatcher.utter_message(json_message= data_package)


        return []
