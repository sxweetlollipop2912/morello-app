from flask.blueprints import Blueprint
from flask_restful import Api, Resource, url_for
from flask import g
import functools

api_bp = Blueprint('api', __name__)
api = Api(api_bp)

def login_required(view):
    @functools.wraps(view)
    def wrapped_view(*args, **kwargs):
        if g.get('user') is None:
            return {'task': 'Say "Hello, NoOne!"'}

        return view(*args, **kwargs)

    return wrapped_view

class TodoItem(Resource):
    @login_required
    def get(self, id):
        return {'task': 'Say "Hello, World!"'}

    def put(self, id):
        g.user = id
        return {'task': 'Say "Ok"'}

api.add_resource(TodoItem, '/todos/<int:id>')
