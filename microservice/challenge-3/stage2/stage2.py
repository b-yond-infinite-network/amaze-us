from boosterservice.boosterservice import booster
from flask import Flask, jsonify
import os
from flask_cors import CORS

app = Flask(__name__)
CORS(app)


@app.route('/checkstage2', methods=['GET'])
def check_stage2():
    return jsonify({'state': 'Not Released'})

@app.route('/releasestage2', methods=['GET'])
def release_stage2():
    return jsonify({'state': 'Stage2 Released'})

@app.route('/checkbooster', methods=['GET'])
def check_booster():
    booster.connect(os.environ.get('BOOSTER_URL'))
    return jsonify({'state': booster.checkFuel()})

@app.errorhandler(404)
def page_not_found(e):
    """Return a custom 404 error."""
    return 'Sorry, Nothing at this URL.', 404
    
@app.errorhandler(500)
def application_error(e):
    """Return a custom 500 error."""
    return 'Sorry, unexpected error: {}'.format(e), 500

if __name__ == '__main__':
	    app.run(debug=True,host='0.0.0.0')