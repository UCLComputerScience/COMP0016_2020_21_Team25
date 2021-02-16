from flask import Flask,request,jsonify
import ConceirgeEntityExtracter

app=Flask(__name__)


@app.route("/<string:command_sentence>")
def ner(command_sentence):
    headers=request.headers
    authenticationKey=headers.get("api-key")

    command=ConceirgeEntityExtracter.runNER(command_sentence)
    response = jsonify(command)
    if authenticationKey=="team25Concierge":
        return response,200
    else:
        return jsonify({"message":"Error:Unauthorised"}),401

    

if __name__=="__main__":
    app.run(debug=True)