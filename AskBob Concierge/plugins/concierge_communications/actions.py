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
        response=person
        if not person:
            response="Sorry, I can't call this person"
       
        data_package={
            "Service_Type": "CALL_CONTACT",
            "Contact": response
        }

        dispatcher.utter_message(json_message= data_package)


        return []

@askbob.plugin.action("concierge_communications", "place_message")
class ActionConciergePlaceMessage(Action):

    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:

        person = next(tracker.get_latest_entity_values("PERSON"), None)

        response=person
        if not person:
            response="Sorry, I can't call this person"

       
        data_package={
            "Service_Type": "SMS_CONTACT",
            "Contact": response
        }

        dispatcher.utter_message(json_message= data_package)


        return []


