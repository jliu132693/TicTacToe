
"""
The fundamental User data type.
"""


from google.appengine.ext import db
from utils import *


class User(db.Model):
    userName = db.StringProperty(required=True)
    userPw = db.StringProperty(required=True)
    userPoints = db.IntegerProperty(required=True)
    userID = db.IntegerProperty(required=True)
    userEmail = db.StringProperty()


    @classmethod
    def register(cls, name, pw, email=None):
        # NOTE: Initial userID is created by iterating through all users and
        # finding a number that is currently not used. However, this
        # implementation is ridiculous and should be improved.

        idAttempts = 0
        userId = 0  # TODO: improve upon id generation
        MAX_DESIRED_ID = 10000

        # TODO: improve upon this naive unique id creation
        while User.all().filter('userID =', int(userId)).get():
            userId = (userId + 1) % MAX_DESIRED_ID
            idAttempts += 1

            # if idAttempts exceeds MAX_DESIRED_ID, then no more decks can be created
            if idAttempts > MAX_DESIRED_ID:
                raise Exception

        return User(userName=name, userPw=pw, userEmail=email, userPoints=0, userID=userId)

    @classmethod
    def login(cls, name, pw):
        user = User.byName(name)  # get_by_id() is GAE method

        if user and user.userPw == pw:
            return user

    @classmethod
    def byName(cls, name):
        # similar query to "select * from User where User.name = name"
        return User.all().filter('userName =', name).get()

    @classmethod
    def byId(cls, uid):
        return User.get_by_id(uid)  # GAE method

    def render(self):
        return renderStr("singleUser.html", user=self)