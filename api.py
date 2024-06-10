import time
from flask import *
from flask_cors import CORS

app = Flask(__name__)
CORS(app)

@app.route("/", methods=['GET', 'POST'])
def main():
    if request.method == 'POST':
        print("processing.....")
        time.sleep(2)
        file = request.files.get("file", None)
        jd = request.form.get("jd", None)

        if file and jd:
            return {
                "score" : 99.99
            }
        
        else:
            return {
                "score" : 00.00
            }
        
    time.sleep(2)
    return {
        "score" : 99.99
    }

if __name__ == '__main__':
    app.run(debug=True)
