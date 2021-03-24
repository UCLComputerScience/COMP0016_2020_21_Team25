from typing import Any, Text, Dict, List
from rasa_sdk import Action, Tracker
from rasa_sdk.executor import CollectingDispatcher

import askbob.plugin
import requests


@askbob.plugin.action("concierge_communications", "place_call")
class ActionConciergePlaceCall(Action):

    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:

        person = next(tracker.get_latest_entity_values("PERSON"), None)
        if not person:
            data_package={
                "Service_Type":"APP_SERVICE",
                "Service": "Call Contact",
                "Response":"Sorry, I don't know who that is, please try again"
            }
        else:
            data_package={
                "Service_Type":"APP_SERVICE",
                "Service": "Call Contact",
                "Response":"Calling "+person,
                "Contact": person
            }

        dispatcher.utter_message(json_message= data_package)


        return []

@askbob.plugin.action("concierge_communications", "place_message")
class ActionConciergePlaceMessage(Action):

    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:

        person = next(tracker.get_latest_entity_values("PERSON"), None)

        if not person:
            data_package={
                "Service_Type":"APP_SERVICE",
                "Service": "SMS Contact",
                "Response":"Sorry, I don't know who that is, please try again"
            }
        else:
            data_package={
                "Service_Type":"APP_SERVICE",
                "Service": "SMS Contact",
                "Response":"Messaging "+person,
                "Contact": person
            }
        dispatcher.utter_message(json_message= data_package)


        return []


