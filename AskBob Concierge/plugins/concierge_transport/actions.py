from typing import Any, Text, Dict, List
from rasa_sdk import Action, Tracker
from rasa_sdk.executor import CollectingDispatcher

import askbob.plugin
import requests

@askbob.plugin.action("concierge_transport", "fetch_nearest_transport")
class ActionConciergePlaceCall(Action):

    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:

        transport_type=next(tracker.get_latest_entity_values("transport_type"), None)

        if not transport_type:
            data_package={
                "Service_Type": "API_CALL",
                "Service":"transport-by-search",
                "Response": "Please specify the transport type and try again"
        }
        else:
            if transport_type.lower()=="bus" or transport_type.lower()=="bus stop" or transport_type.lower()=="bus station":
                transport_type="bus_stop"
            elif transport_type.lower()=="train" or transport_type.lower()=="train station" or transport_type.lower()=="train stop":
                transport_type="train_station"

            data_package={
                "Service_Type": "API_CALL",
                "Service":"nearest-transport",
                "Response": transport_type
            }
        dispatcher.utter_message(json_message= data_package)
        return []



@askbob.plugin.action("concierge_transport", "fetch_transport_by_search")
class ActionConciergePlaceCall(Action):

    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:

        location = next(tracker.get_latest_entity_values("GPE"), None)
        transport_type=next(tracker.get_latest_entity_values("transport_type"), None)

        
        if transport_type==None or location==None:
            data_package={
            "Service_Type": "API_CALL",
            "Service":"transport-by-search",
            "Response": "Unable to to find that station or transport, please try again"
        }
        else:
            if transport_type.lower()=="bus" or transport_type.lower()=="bus stop" or transport_type.lower()=="bus station":
                transport_type="bus_stop"
            elif transport_type.lower()=="train" or transport_type.lower()=="train station" or transport_type.lower()=="train stop":
                transport_type="train_station"

            r = requests.get(url="http://localhost:8080/transport-search", params={
                "QUERY":location, "TRANSPORT": transport_type}).json()

            data_package={
                "Service_Type": "API_CALL",
                "Service":"transport-by-search",
                "Response": {"Message":r["message"],
                            "Latitude":r["metadata"]["latitude"],
                            "Longitude":r["metadata"]["latitude"]}
                
            }
        dispatcher.utter_message(json_message= data_package)
        return []

