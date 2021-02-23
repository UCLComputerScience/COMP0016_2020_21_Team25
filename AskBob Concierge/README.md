# AskBob Concierge Plugin

## Setup

This directory contains the plugins folder required to integrate Concierge functions within the AskBob virtual assitant service.

To utilise the AskBob service with the Concierge plugin the AskBob service must first be downloaded. For its purpouses within the 
Concierge App it does not need to be fully downloaded. The Concierge App only requires AskBob to run as an API server and therefore
AskBobs speech to text services do not need to be downloaded. 

For more information about how to setup AskBob as an API server see:

[AskBob](https://github.com/UCL-COMP0016-2020-Team-39/AskBob)

Once downloaded the existing plugins folder within AskBob must be replaced with the one within this directory. Once completed the AskBob
service is ready to be launched.

**Note:For the time being the AskBob API must be run in conjunction with the Concierge Services API. For AskBob to correctly call
the relevant resources the Concierge Services API must be running on port 8080 (The default port for the Services API)**

## Placing Requests

To place a request to the AskBob Concierge service one must place a POST request to the folowing URL

(http://0.0.0.0:8000/query)                    *Assuming that AskBob is running on the Local Host*
Within the body of the request must be contained the following fields
| Key             | Value                                 |
| --------------- | --------------------------------------|
| Message         | The users request in natural language |
| Sender          | concierge                             | 

## Data Format
The AskBob Concierge Plugin returns data as a JSON with this structure.
```
"messages":[

        {
        
            "custom": {
            
                "Service_Type": "....",
                
                "Service": "....",
                
                "Response": "...."
                
            }
            
        }
        
    ]
}
```
