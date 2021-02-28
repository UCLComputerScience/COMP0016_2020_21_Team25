from typing import Any, Text, Dict, List
from rasa_sdk import Action, Tracker
from rasa_sdk.executor import CollectingDispatcher

import askbob.plugin
import requests


@askbob.plugin.action("concierge_apps", "open_app")
class ActionConciergePlaceCall(Action):

    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:

        application = next(tracker.get_latest_entity_values("application"), None)
        response=application
        if not application:
            response="Sorry, I can't open this app"
       
        data_package={
            "Service_Type": "OPEN_APP",
            "Application": response.lower()
        }

        dispatcher.utter_message(json_message= data_package)


        return []


