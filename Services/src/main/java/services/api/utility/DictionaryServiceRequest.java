package services.api.utility;

import services.api.AbstractServiceRequest;

import java.util.ArrayList;
import java.util.HashMap;

public class DictionaryServiceRequest extends AbstractServiceRequest {
    private final boolean synonymsOnly;
    private final boolean includeSynonyms;
    private final int MAX_DEFINITIONS = 3;
    private final int MAX_SYNONYMS = 3;

    /**
     * @param payload Data needed to fill out the API call parameters.
     */
    public DictionaryServiceRequest(HashMap<String, String> payload) {
        super("https://api.dictionaryapi.dev/api/v2/entries/{LANGUAGE}/{WORD}",
                "Dictionary", "Utility", "", payload);
        synonymsOnly = Boolean.parseBoolean(payload.get("SYNONYMS_ONLY"));
        includeSynonyms = synonymsOnly || Boolean.parseBoolean(payload.get("INCLUDE_SYNONYMS"));
    }

    @Override
    protected String parseOutput(HashMap<String, Object> response) {
        ArrayList<HashMap<String, Object>> results = (ArrayList<HashMap<String, Object>>) response.get("results");
        ArrayList<HashMap<String, Object>> definitionsArray = (ArrayList<HashMap<String, Object>>) results.get(0).get("meanings");
        int size = definitionsArray.size();
        String word = payload.get("WORD");
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < MAX_DEFINITIONS; i++) {
            if (i == size)
                break;
            HashMap<String, Object> definitionData = definitionsArray.get(i);
            ArrayList<HashMap<String, Object>> definitions = (ArrayList<HashMap<String, Object>>) definitionData.get("definitions");
            HashMap<String, Object> definition = definitions.get(0);
            output.append(getDefinition(definition, word, i));
            output.append(getSynonyms(definition, word));
        }
        return output.toString();
    }

    private String getDefinition(HashMap<String, Object> definition, String word, int index) {
        StringBuilder output = new StringBuilder();
        if (!synonymsOnly) {
            if (index > 0) {
                output.append(" ");
            }
            output.append(word);
            output.append(" ");
            output.append((index == 0) ? "is" : "can also be");
            output.append(" defined as ");
            addDefinition(output, definition.get("definition"));
            addExample(output, definition.get("example"));
        }
        return output.toString();
    }

    private void addDefinition(StringBuilder output, Object definition) {
        String definitionStr = (String) definition;
        output.append(definitionStr.toLowerCase());
//        output.append(".");
    }

    private void addExample(StringBuilder output, Object example) {
        String exampleStr = (String) example;
        if (exampleStr.length() > 0) {
            output.append(" Used in a sentence: \"");
            output.append(example);
            output.append("\".");
        }
    }

    private String getSynonyms(HashMap<String, Object> definition, String word) {
        StringBuilder output = new StringBuilder();
        if (includeSynonyms) {
            ArrayList<Object> synonyms = (ArrayList<Object>) definition.get("synonyms");
            if (synonyms.size() > 0) {
                if (!synonymsOnly) {
                    output.append(" ");
                }
                output.append("Synonyms of ");
                output.append(word);
                output.append(" include ");
                addSynonyms(synonyms, output);
                output.append(".");
            }
        }
        return output.toString();
    }

    private void addSynonyms(ArrayList<Object> synonyms, StringBuilder output) {
        for (int j = 0; j < MAX_SYNONYMS; j++) {
            String synonym = (String) synonyms.get(j);
            output.append(synonym);
            if (j < MAX_SYNONYMS - 2) {
                output.append(", ");
            } else if (j == MAX_SYNONYMS - 2) {
                output.append(" and ");
            }
        }
    }

    @Override
    protected String handleErrors(HashMap<String, Object> response) {
        return "The requested action could not be performed. Please try again.";
    }

    @Override
    protected String getErrorCode(HashMap<String, Object> response) {
        return null;
    }

    @Override
    protected HashMap<String, String> populatePayload() {
        HashMap<String, String> servicePayload = new HashMap<>();
        servicePayload.put("WORD", "hello");
        servicePayload.put("LANGUAGE", "en");
        servicePayload.put("SYNONYMS_ONLY", "false");
        servicePayload.put("INCLUDE_SYNONYMS", "false");
        return servicePayload;
    }
}
