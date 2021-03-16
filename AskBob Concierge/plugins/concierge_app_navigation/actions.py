from typing import Any, Text, Dict, List
from rasa_sdk import Action, Tracker
from rasa_sdk.executor import CollectingDispatcher

import askbob.plugin
import requests


@askbob.plugin.action("concierge_app_navigation", "goto_app_destination")
class ActionConciergePlaceCall(Action):

    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:

        application = next(tracker.get_latest_entity_values("app_destination"), None)
        response=application
        if not application:
            data_package={
                "Service_Type":"APP_SERVICE",
                "Service": "Navigate App",
                "Page": "Sorry, I don't know where that page is"
            }
        else:
            data_package={
                "Service_Type":"APP_SERVICE",
                "Service": "Navigate App",
                "Page": response.lower()
            }

        dispatcher.utter_message(json_message= data_package)


        return []


