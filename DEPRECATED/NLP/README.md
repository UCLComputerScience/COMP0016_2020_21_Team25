# Concierge Natural Language Processing 

The NLP package contains the natural language processing components utilised by the Concierge App. 

## Description
The NLP package is utilised by the Concierge app to perform natural language processing on the users command to extract the relevant entities needed to
complete their request which are then formatted appropriately such that they can be received by the Services-API package. 
This package provides the relavant tools required to create and train and a Named Entity Recognition Model suited for the purpouse outlined above as
well as the tools needed to host this as an API.

## NER Model Trainer
The NER Model is written in Python and utilises the Spacy library. This model is trained to recognise entities relating to the services offered by the 
Concierge app and to extract any entities required by that service which are present in the users command. 

## Entity Extractor
The entity extraction is performed by utilising the previously trained model and applying it to the users command to extract the relevant entities.
These entities are then stored within a dictionary.

## Entity Extractor API
The entity extractor is hosted as an API by utilising the Python library Flask. This API accepts the text of a users given command and returns a JSON 
file of the relevant entities which is then passed to the Services-API package.

## Running
To the run the code the relavent libraries must first be installed. This can be achieved by running the following:
```
pip install requirements.txt
```

Once the installation is complete the API can be run by using the following:
```
python ConciergeEntityExtracterAPI.py
```

This will use the pretrained model contained within the repository but if you desire to make changes to the model it will have to be retrained first.
This can be done using:
```
python ModelTrainer.py
```

# ❗This package has since been deprecated in favour of utilising the AskBob plugin
