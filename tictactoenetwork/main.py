#!/usr/bin/env python
#
# Copyright 2007 Google Inc.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
import webapp2

from login import *
from register import *
from user import *

from google.appengine.ext import db


class MainPage(Handler):
    def get(self):
        self.render('main.html')


class PlayPage(Handler):
    def get(self):
        username = self.request.get("username")
        password = self.request.get("password")


        if username and password:
            # if no cookies were present (such as an app)
            # AND if username and password are present
            # then log the user in here
            user = User.login(username, password)
            user.userPoints += 1
            user.put()

        self.render('play.html')


    def post(self):
        uid = self.request.cookies.get('userId')

        if uid:
            currentUser = User.get_by_id(int(uid))
            currentUser.userPoints += int(self.request.get('pointChange'))
            currentUser.put()


class TopScoresPage(Handler):
    def get(self):

        specialFormat = self.request.get('specialFormat')
        topScorers = User.all().order("-userPoints").run(limit=10)

        if specialFormat:

            formattedScores = ""

            for scorer in topScorers:
                # bar signals split between name and points; + signals next user
                formattedScores += scorer.userName + "|" + str(scorer.userPoints) + "+"

            self.response.out.write(formattedScores)

        else:

            self.render('topscores.html', topScorers=topScorers)


class NotFoundPage(Handler):
    def get(self):
        self.render('invalid.html')


class LogoutPage(Handler):
    def get(self):
        self.logout()
        self.redirect('/')


app = webapp2.WSGIApplication([
        ('/', MainPage),
        ('/play', PlayPage),
        ('/topscores', TopScoresPage),
        ('/login', LoginPage),
        ('/logout', LogoutPage),
        ('/register', RegisterPage),
        ('/.*', NotFoundPage)
        ], debug=True)
