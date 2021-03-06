from typing import Any, Text, Dict, List
from rasa_sdk import Action, Tracker
from rasa_sdk.executor import CollectingDispatcher

import askbob.plugin
import requests

host="serviceapis"
port="8080"

@askbob.plugin.action("concierge_transport", "fetch_nearest_transport")
class ActionConciergePlaceCall(Action):

    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:

        transport_type=next(tracker.get_latest_entity_values("transport_type"), None)
        

        if not transport_type:
            data_package={
            "Service_Type": "API_CALL",
            "Service":"Transport",
            "Response": "Please specify a valid transport type and try again"
            }
            dispatcher.utter_message(json_message= data_package)
            return[]

        else:
            if transport_type.lower()=="bus" or transport_type.lower()=="bus stop" or transport_type.lower()=="bus station":
                response="Finding nearest bus stop"
                transport_key="bus_stop"
            elif transport_type.lower()=="train" or transport_type.lower()=="train station" or transport_type.lower()=="train stop":
                response="Finding nearest train station"
                transport_key="train_station"
            else:
                data_package={
                "Service_Type": "API_CALL",
                "Service":"Transport",
                "Response": "Please specify a valid transport type and try again"
                }
                dispatcher.utter_message(json_message= data_package)
                return[]

        data_package={
            "Service_Type": "API_CALL",
            "Service":"Transport",
            "Response": response,
            "Transport Type":transport_key
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
            "Service":"Transport",
            "Response": "Unable to to find that station or mode of transport, please try again"
        }
        else:
            if transport_type.lower()=="bus" or transport_type.lower()=="bus stop" or transport_type.lower()=="bus station":
                transport_type="bus_stop"
            elif transport_type.lower()=="train" or transport_type.lower()=="train station" or transport_type.lower()=="train stop":
                transport_type="train_station"
            else:
                data_package={
                "Service_Type": "API_CALL",
                "Service":"Transport",
                "Response": "Unable to to find that station or mode of transport, please try again"
                }
                dispatcher.utter_message(json_message= data_package)
                return[]

            r = requests.get(url="http://"+host+":"+port+"/transport-search", params={
                "QUERY":location, "TRANSPORT": transport_type}).json()

            data_package={
                "Service_Type": "API_CALL",
                "Service":"Transport",
                "Response": {"Message":r["message"],
                            "Latitude":r["metadata"]["latitude"],
                            "Longitude":r["metadata"]["longitude"]}
                
            }
        dispatcher.utter_message(json_message= data_package)
        return []

