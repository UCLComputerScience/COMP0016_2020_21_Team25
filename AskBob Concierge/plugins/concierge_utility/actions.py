from typing import Any, Text, Dict, List
from rasa_sdk import Action, Tracker
from rasa_sdk.executor import CollectingDispatcher

import askbob.plugin
import requests


@askbob.plugin.action("concierge_utility", "fetch_weather")
class ActionConciergeFetchWeather(Action):

    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:
        
        city = next(tracker.get_latest_entity_values("GPE"), None)


        r = requests.get(url="http://0.0.0.0:8080/current-weather", params={
            "CITY_NAME": city,
            
        }).json()
        data_package={
            "Service_Type": "API_CALL",
            "Service": "current_weather",
            "Response": r["message"]
        }
        dispatcher.utter_message(json_message= data_package)

        return []

@askbob.plugin.action("concierge_utility", "fetch_air_quality")
class ActionConciergeFetchAirQuality(Action):

    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:
        
        city = next(tracker.get_latest_entity_values("GPE"), None)

        r = requests.get(url="http://0.0.0.0:8080/air-quality", params={
            "CITY_NAME": city,
        }).json()
        
        data_package={
            "Service_Type": "API_CALL",
            "Service": "air-quality",
            "Response": r["message"]
        }

        dispatcher.utter_message(json_message= data_package)

        return []


@askbob.plugin.action("concierge_utility", "fetch_definition")
class ActionConciergeFetchDefinitions(Action):

    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:

        word = next(tracker.get_latest_entity_values("search_word"), None)
        print(word)
        r = requests.get(url="http://0.0.0.0:8080/dictionary", params={
            "WORD": word,
            "INCLUDE_SYNONYMS": "false"
        }).json()
        
        data_package={
            "Service_Type": "API_CALL",
            "Service": "dictionary",
            "Response": r["message"]
        }

        dispatcher.utter_message(json_message= data_package)


@askbob.plugin.action("concierge_utility", "fetch_synonym")
class ActionConciergeFetchSynonyms(Action):

    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:

        synonym = next(tracker.get_latest_entity_values("search_word"), None)
        print(synonym)
        r = requests.get(url="http://0.0.0.0:8080/dictionary", params={
            "WORD": synonym,
            "INCLUDE_SYNONYMS": "true",
            "SYNONYMS_ONLY": "true"
        }).json()
        
        data_package={
            "Service_Type": "API_CALL",
            "Service": "dictionary",
            "Response": r["message"]
        }

        dispatcher.utter_message(json_message= data_package)

