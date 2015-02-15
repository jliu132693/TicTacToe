from handler import *


class RegisterPage(Handler):
    def generateNewUser(self):
        user = User.byName(self.username)

        # TODO - notify if username already exists

        if not user:
            user = User.register(self.username, self.password, self.email)
            user.put()  # places user object into the data store
            self.login(user)
            self.redirect('/')
        else:
            self.error(401)
            self.render("register.html", errorValue="Username already exists")

    def get(self):
        self.render('register.html')

    def post(self):
        self.username = self.request.get('username')
        self.password = self.request.get('password')
        self.verify = self.request.get('verify')
        self.email = self.request.get('email')

        if self.username and self.password and self.verify:
            self.generateNewUser()
        else:
            self.error(400)
            self.render("register.html", errorValue="Incomplete or incorrect input.")
