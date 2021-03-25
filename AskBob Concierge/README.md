# AskBob Concierge Plugin

Within this directory you can find the source code needed to integrate IBM FISE Concierge functions within the AskBob virtual assitant service. 
This service provides the NLP and request fulfilment features utilised by the IBM FISE Concierge app. 

The AskBob service can be run on a separate server like device as a restful API and can be accessed via local host.

## Deployment
It is recommended to launch the AskBob server by utilising the docker-compose file found at the root of the repo as that will also launch 
Concierge Service API server which the AskBob server is dependent upon for most plugins.


To start the AskBob server independently you must first run the following to build the image:
```docker build -t askbob .```
Then run the image using this command (note: while it is recomended to run the it on port 8000 this can be changed):
```docker run -it --rm -p 8000:8000 askbob```

If you wish to run the AskBob server manually then you will need to install the AskBob Service.
For its purpouses within the IBM FISE Concierge App the AskBob Service the Concierge App only requires AskBob to be run as an API server and therefore
AskBobs speech to text services do not need to be downloaded. 

For more information about how to setup AskBob manually as an API server see this for further instructions:

[AskBob](https://github.com/UCL-COMP0016-2020-Team-39/AskBob)

As mentioned above the AskBob server running Concierge functions is dependent on the Concierge Service API server also running. In the case that these
are both being run manually (e.g not via docker) one most also change the value of the variable host with the plugins action.py to localhost. 

Note: This is not the intended way to run the AskBob server.


## Placing Requests

To place a request to the AskBob Concierge service one must place a POST request to the folowing URL

(http://localhost:8000/query)                    
Within the body of the request must be contained the following fields
| Key             | Value                                 |
| --------------- | --------------------------------------|
| Message         | The users request in natural language |
| Sender          | concierge                             | 

## Data Format
The AskBob Concierge Plugin returns the relevant data needed by the app to fulfill the request as a JSON with the following structure.
```
{
    "query": ".....",
    "messages": [
        {
            "custom": {
                "Service_Type": ".....",
                "Service": ".....",
                "Response": "....."
            }
        }
    ]
}
```
## Testing

To execute the tests for the AskBob Concierge Services first start the AskBob server and ensure that it is running on port 8000.

Then from the AskBob Concierge directory run the following command

```pytest tests/```

Note: 
There is likely to be a small number of tests that fail however, this number should not be greater than 5% of all tests cases. 
This is caused by discrepancies in the way the model is trained and the cases failed will vary between different instances of the AskBob server.
