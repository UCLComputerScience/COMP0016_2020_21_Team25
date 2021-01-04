import random
import warnings
from pathlib import Path
import spacy
from spacy.util import minibatch, compounding


# new entity label
COMMAND_LABEL = "COMMAND"
WEATHER_LABELS=["CITY"]
STOCK_LABELS=[]
JOKE_LABELS=["CATEGORY"]
STOCK_LABELS=["FUNCTION","SYMBOL","INTERVAL"]


TRAIN_DATA = [
    (
        "What is the weather like outside?",
        {"entities": [(12, 19, COMMAND_LABEL)]},
    ),

    (
        "What is the weather like in London?",
        {"entities": [(12, 19, COMMAND_LABEL),(28, 34, WEATHER_LABELS[0])]},
    ),

    (
        "What will the weather be like in Chicago?",
        {"entities": [(14, 21, COMMAND_LABEL),(33, 40, WEATHER_LABELS[0])]}
    ),

    (
        "Tell me what the weather will be like in Oslo",
        {"entities": [(17, 24, COMMAND_LABEL),(41, 45, WEATHER_LABELS[0])]}
    ),

    (
        "Give me the weather for today",
        {"entities": [(12, 19, COMMAND_LABEL)]},
    ),

    (
        "Tell me a joke",
        {"entities": [(10, 14, COMMAND_LABEL)]},
    ),

    (
        "Tell me a dark joke",
        {"entities": [(15, 19, COMMAND_LABEL),(10, 14, JOKE_LABELS[0])]},
    ),

    (
        "Give me a spooky joke",
        {"entities": [(17, 21, COMMAND_LABEL),(10, 16, JOKE_LABELS[0])]},
    ),

    (
        "Give me a Christmas joke",
        {"entities": [(20, 24, COMMAND_LABEL),(10, 19, JOKE_LABELS[0])]},
    ),

    (
        "Tell me a programming joke",
        {"entities": [(22, 26, COMMAND_LABEL),(10, 21, JOKE_LABELS[0])]},
    ),

    (
        "Tell me the stock value of IBM",
        {"entities": [(12, 17, COMMAND_LABEL),(27, 30, STOCK_LABELS[1])]},
    ),

    (
        "Tell me the stock value of Apple",
        {"entities": [(12, 17, COMMAND_LABEL),(27, 32, STOCK_LABELS[1])]},
    ),
    
    (
        "What is the stock value of Google",
        {"entities": [(12, 17, COMMAND_LABEL),(27,33, STOCK_LABELS[1])]},
    ),
    
]


def main(model, new_model_name, output_dir,num_iterations):
    random.seed(0)
    if model is not None:
        nlp = spacy.load(model)  
        
        print("Loaded model '%s'" % model)
    else:
        nlp = spacy.blank("en") 
        print("Created blank 'en' model")

    # Add entity recognizer to model or get it
    
    if "ner" not in nlp.pipe_names:
        ner = nlp.create_pipe("ner")
        nlp.add_pipe(ner)
    else:
        ner = nlp.get_pipe("ner")

    ner.add_label(COMMAND_LABEL) 
    ner.add_label(WEATHER_LABELS[0])
    ner.add_label(JOKE_LABELS[0])
    ner.add_label(STOCK_LABELS[0])
    ner.add_label(STOCK_LABELS[1])
    ner.add_label(STOCK_LABELS[2])
   
    if model is None:
        optimizer = nlp.begin_training()
    else:
        optimizer = nlp.resume_training()
    
    move_names = list(ner.move_names)
    pipe_exceptions = ["ner", "trf_wordpiecer", "trf_tok2vec"]
    other_pipes = [pipe for pipe in nlp.pipe_names if pipe not in pipe_exceptions]
    # only train NER
    with nlp.disable_pipes(*other_pipes), warnings.catch_warnings():
        # show warnings for misaligned entity spans once
        warnings.filterwarnings("once", category=UserWarning, module='spacy')

        sizes = compounding(1.0, 4.0, 1.001)
        for i in range(num_iterations):
            random.shuffle(TRAIN_DATA)
            batches = minibatch(TRAIN_DATA, size=sizes)
            losses = {}
            for batch in batches:
                texts, annotations = zip(*batch)
                nlp.update(texts, annotations, sgd=optimizer, drop=0.35, losses=losses)
            print("Losses", losses)

    # save model to output directory
    if output_dir is not None:
        output_dir = Path(output_dir)
        if not output_dir.exists():
            output_dir.mkdir()
        nlp.meta["name"] = new_model_name  # rename model
        nlp.to_disk(output_dir)
        print("Saved model to", output_dir)


main(None,"Conceirge","./src/NER_Model",30)