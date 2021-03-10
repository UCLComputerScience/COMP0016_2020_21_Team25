from typing import Any, Text, Dict, List
from rasa_sdk import Action, Tracker
from rasa_sdk.executor import CollectingDispatcher
from rasa_sdk.events import SlotSet

import askbob.plugin
import requests


@askbob.plugin.action("concierge_food", "fetch_recipe_by_search")
class ActionConciergePlaceCall(Action):

    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:

        recipe_search = next(tracker.get_latest_entity_values("recipe_search_term"), None)
        
        r = requests.get(url="http://localhost:8080/recipe", params={
            "QUERY":recipe_search}).json()


        data_package={
            "Service_Type": "API_CALL",
            "Service":"recipe-by-search",
            "Response": r["message"]
        }
        print(r["metadata"]["recipe-id"])
        print(recipe_search)
        dispatcher.utter_message(json_message= data_package)


        return [SlotSet("recipe_id", r["metadata"]["recipe-id"])]

@askbob.plugin.action("concierge_food", "fetch_recipe_by_ingredient")
class ActionConciergePlaceCall(Action):

    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:

        recipe_ingredient = next(tracker.get_latest_entity_values("recipe_ingredient"), None)
        
        r = requests.get(url="http://localhost:8080/ingredient", params={
            "INGREDIENTS":recipe_ingredient}).json()


        data_package={
            "Service_Type": "API_CALL",
            "Service":"recipe-by-ingredient",
            "Response": r["message"]
        }
        print(r["metadata"]["recipe-id"])
        print(recipe_ingredient)
        dispatcher.utter_message(json_message= data_package)


        return [SlotSet("recipe_id", r["metadata"]["image"])]

@askbob.plugin.action("concierge_food", "fetch_random_recipe")
class ActionConciergePlaceCall(Action):

    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:

        r = requests.get(url="http://localhost:8080/random-recipe").json()


        data_package={
            "Service_Type": "API_CALL",
            "Service":"random_recipe",
            "Response": r["message"]
        }
        print(r["metadata"]["recipe-id"])

        dispatcher.utter_message(json_message= data_package)


        return [SlotSet("recipe_id", r["metadata"]["recipe-id"])]

@askbob.plugin.action("concierge_food", "fetch_read_recipe")
class ActionConciergePlaceCall(Action):

    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:

        recipe_id=tracker.get_slot("recipe_id") 
        if not recipe_id:

            data_package={
                "Service_Type": "API_CALL",
                "Service":"random_recipe",
                "Response": "I do not know which recipe you are referring to",
                "Steps":[]
            }

        else:
            r = requests.get(url="http://localhost:8080/recipe-instructions",params={
            "ID":recipe_id}).json()

            data_package={
                "Service_Type": "API_CALL",
                "Service":"random_recipe",
                "Response": r["message"],
                "Steps":r["metadata"]["steps"]
            }

        dispatcher.utter_message(json_message= data_package)
        return [SlotSet("recipe_id", None)]