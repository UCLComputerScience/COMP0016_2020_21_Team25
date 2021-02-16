import random
import warnings
from pathlib import Path
import spacy
from spacy.util import minibatch, compounding


class EntityExtraction:
    def __init__(self,modelDir):
        self.modelDir=modelDir
        self.nlp=self.loadModel()

    def loadModel(self):
        #loads model and checks loading was succesful
        nlp = spacy.load(self.modelDir)
        ner = nlp.get_pipe("ner")
        move_names = list(ner.move_names)
        assert nlp.get_pipe("ner").move_names == move_names
        return nlp

    def entityExtraction(self,command):
        #runs model on given phrase
        commandText = self.nlp(command)
        entities=commandText.ents
        commandType=CommandPackage()
        #creates appropriate API package
        for ent in entities:
            if ent.label_=="COMMAND" and ent.text=="weather":
                commandType.weatherDict(entities)
            elif ent.label_=="COMMAND" and ent.text=="forecast":
                commandType.forecastDict(entities)
            elif ent.label_=="COMMAND" and ent.text=="air":
                commandType.airQualityDict(entities)
            elif ent.label_=="COMMAND" and ent.text=="joke":
                commandType.jokeDict(entities)
            elif ent.label_=="COMMAND" and ent.text=="stock":
                commandType.stockDict(entities)
            elif ent.label_=="COMMAND" and ent.text=="definition":
                commandType.definitionDict(entities)
            elif ent.label_=="COMMAND" and ent.text=="synonyms":
                commandType.thesaurusDict(entities)
        return commandType

class CommandPackage:
    def __init__(self):
        self.commandDict=dict()
    #Creates command package with all the information required by a given API 
    #default values to be resolved in the API calls
    def weatherDict(self,entities):
        self.commandDict={"COMMAND":"default",
                          "CITY":"default",
                          "COUNTRY_CODE":"default"}
        for ent in entities:
            if ent.label_=="COMMAND":
                self.commandDict.update({"COMMAND":ent.text})
            elif ent.label_=="CITY":
                self.commandDict.update({"COMMAND":ent.text})
            elif ent.label_=="COUNTRY_CODE":
                self.commandDict.update({"COMMAND":ent.text})

    def airQualityDict(self,entities):
        self.commandDict={"COMMAND":"default",
                          "CITY":"default",
                          "COUNTRY_CODE":"default"}
        for ent in entities:
            if ent.label_=="COMMAND":
                self.commandDict.update({"COMMAND":ent.text})
            elif ent.label_=="CITY":
                self.commandDict.update({"CITY":ent.text})
            elif ent.label_=="COUNTRY_CODE":
                self.commandDict.update({"COUNTY_CODE":ent.text})

    def forecastDict(self,entities):
        self.commandDict={"COMMAND":"default",
                          "CITY":"default",
                          "COUNTRY_CODE":"default",
                          "DAYS":"default"}
        for ent in entities:
            if ent.label_=="COMMAND":
                self.commandDict.update({"COMMAND":ent.text})
            elif ent.label_=="CITY":
                self.commandDict.update({"CITY":ent.text})
            elif ent.label_=="COUNTRY_CODE":
                self.commandDict.update({"COUNTRY_CODE":ent.text})
            elif ent.label_=="DAYS":
                self.commandDict.update({"DAYS":ent.text})

    def jokeDict(self,entities):
        self.commandDict={"COMMAND":"default",
                          "CATEGORY":"default"}
        for ent in entities:
            if ent.label_=="COMMAND":
                self.commandDict.update({"COMMAND":ent.text})
            elif ent.label_=="CATEGORY":
                self.commandDict.update({"CATEGORY":ent.text})

    def stockDict(self,entities):
        self.commandDict={"COMMAND":"default",
                          "FUNCTION":"default",
                          "SYMBOL":"default",
                          "INTERVAL":"default"}
        for ent in entities:
            if ent.label_=="COMMAND":
                self.commandDict.update({"COMMAND":ent.text})
            elif ent.label_=="FUNCTION":
                self.commandDict.update({"FUNCTION":ent.text})
            elif ent.label_=="SYMBOL":
                self.commandDict.update({"SYMBOL":ent.text})
            elif ent.label_=="INTERVAL":
                self.commandDict.update({"INTERVAL":ent.text})

    def definitionDict(self,entities):
        self.commandDict={"COMMAND":"default",
                          "WORD":"default",
                          "OPTION":"default",}
        for ent in entities:
            if ent.label_=="COMMAND":
                self.commandDict.update({"COMMAND":ent.text})
            elif ent.label_=="WORD":
                self.commandDict.update({"WORD":ent.text})
            elif ent.label_=="OPTION":
                self.commandDict.update({"OPTION":ent.text})

    def theasurusDict(self,entities):
        self.commandDict={"COMMAND":"default",
                          "WORD":"default",}
        for ent in entities:
            if ent.label_=="COMMAND":
                self.commandDict.update({"COMMAND":ent.text})
            elif ent.label_=="WORD":
                self.commandDict.update({"WORD":ent.text})
            

    

Conceirge= EntityExtraction("./src/NER_Model")

def runNER(sentence):
    command=Conceirge.entityExtraction(sentence)
    return command.commandDict

